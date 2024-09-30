package com.cruzzapil.appmoviltrivia

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.CompRegistro
import model.Registro
import model.Trivia
import model.UserSession
import socket.SocketLoggin

class TriviaDetailFragment : Fragment(R.layout.fragment_trivia_detail) {

    private lateinit var cronometroTextView: TextView
    private var tiempoRestante: Long = 0 // tiempo en segundos
    private val listaComponentes = mutableListOf<CompRegistro>() // Lista para almacenar las respuestas
    private lateinit var  registro:Registro;
    private lateinit var iDTrivia :String
    var puntuacion = 0
    // job
    private var job: Job?=null
    private lateinit var socket: SocketLoggin ;

    companion object {
        private const val ARG_TRIVIA = "arg_trivia"

        fun newInstance(trivia: Trivia): TriviaDetailFragment {
            val fragment = TriviaDetailFragment()
            val args = Bundle()
            args.putSerializable(ARG_TRIVIA, trivia)
            fragment.arguments = args
            return fragment
        }
    }

    private var trivia: Trivia? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            trivia = it.getSerializable(ARG_TRIVIA) as? Trivia
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cronometroTextView = view.findViewById(R.id.cronometroTextView)

        trivia?.TIEMPO?.let {
            tiempoRestante = it.toLong() * 1000
        } ?: run {
            Toast.makeText(context, "El tiempo no está disponible", Toast.LENGTH_SHORT).show()
        }

        iniciarCronometro(tiempoRestante)

        val triviaNameTextView: TextView = view.findViewById(R.id.triviaNameTextView)
        triviaNameTextView.text = trivia?.NOMBRE
        iDTrivia = trivia?.ID_TRIVIA.toString()
        val triviaStructureLayout: LinearLayout = view.findViewById(R.id.triviaStructureLayout)

        // Agregar preguntas dinámicamente
        trivia?.ESTRUCTURA?.forEach { question ->
            val questionTextView = TextView(context)
            questionTextView.text = question.TEXTO_VISIBLE
            triviaStructureLayout.addView(questionTextView)


            when (question.CLASE) {
                "CHECKBOX" -> {
                    val checkBoxGroup = LinearLayout(context)
                    checkBoxGroup.orientation = LinearLayout.VERTICAL

                    val idComponente = question.ID // Asumiendo que cada pregunta tiene un ID único
                    val respuestaSeleccionada = mutableListOf<String>()

                    question.OPCIONES?.split("|")?.forEach { option ->
                        val checkBox = CheckBox(context)
                        checkBox.text = option

                        checkBox.setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked) {
                                respuestaSeleccionada.add(option)
                            } else {
                                respuestaSeleccionada.remove(option)
                            }
                            val respuestaFinal = respuestaSeleccionada.joinToString("|")

                            // Actualizar la respuesta en listaComponentes
                            val componente = listaComponentes.find { it.ID == idComponente }
                            componente?.REGISTRO = respuestaFinal
                        }
                        checkBoxGroup.addView(checkBox)
                    }
                    triviaStructureLayout.addView(checkBoxGroup)

                    listaComponentes.add(CompRegistro(idComponente, "", question.RESPUESTA))
                }

                "RADIO" -> {
                    val radioGroup = RadioGroup(context)
                    val idComponente = question.ID

                    question.OPCIONES?.split("|")?.forEach { option ->
                        val radioButton = RadioButton(context)
                        radioButton.text = option

                        radioButton.setOnClickListener {
                            val respuestaSeleccionada = radioButton.text.toString()

                            val componente = listaComponentes.find { it.ID == idComponente }
                            componente?.REGISTRO = respuestaSeleccionada
                        }
                        radioGroup.addView(radioButton)
                    }
                    triviaStructureLayout.addView(radioGroup)

                    listaComponentes.add(CompRegistro(idComponente, "", question.RESPUESTA))
                }

                "COMBO" -> {
                    val spinner = Spinner(context)
                    val options: List<String> = question.OPCIONES?.split("|") ?: emptyList()
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter

                    val idComponente = question.ID

                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            val respuestaSeleccionada = options[position]

                            val componente = listaComponentes.find { it.ID == idComponente }
                            componente?.REGISTRO = respuestaSeleccionada
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {}
                    }
                    triviaStructureLayout.addView(spinner)

                    listaComponentes.add(CompRegistro(idComponente, "", question.RESPUESTA))
                }

                "CUADRO_TEXTO", "CAMPO_TEXTO" -> {
                    val editText = EditText(context)
                    editText.hint = "Ingrese su respuesta"
                    editText.setLines(question.FILAS)
                    editText.setEms(question.COLUMNAS)

                    val idComponente = question.ID

                    editText.setOnFocusChangeListener { _, hasFocus ->
                        if (!hasFocus) {
                            val respuestaIngresada = editText.text.toString()

                            val componente = listaComponentes.find { it.ID == idComponente }
                            componente?.REGISTRO = respuestaIngresada
                        }
                    }
                    triviaStructureLayout.addView(editText)

                    listaComponentes.add(CompRegistro(idComponente, "", question.RESPUESTA))
                }

                else -> {
                    val errorTextView = TextView(context)
                    errorTextView.text = "Componente no reconocido"
                    triviaStructureLayout.addView(errorTextView)
                }
            }
        }

        // Agregar el botón después de las preguntas
        val guardarRespuestasButton = Button(context).apply {
            text = "Guardar Respuestas"
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                topMargin = 16 // Margen superior opcional
            }
        }
        triviaStructureLayout.addView(guardarRespuestasButton)

        guardarRespuestasButton.setOnClickListener {
            calcularPuntos()
            val idUser = UserSession.username ?:"Usuario Invitado"
            val tmp = tiempoRestante.toInt()
            registro = Registro(iDTrivia,idUser,puntuacion,tmp,listaComponentes)

            realizarPeticion()
        }
    }

    fun realizarPeticion() {
        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                // Conectar al servidor de sockets
                socket = SocketLoggin("192.168.1.109", 5000)
                socket.connect()

                // Convertir registro en JSON
                val gson = Gson()
                val json = gson.toJson(registro)

                // Realizar la petición
                socket.enviarPeticion("REGISTRO#$json")

                // Mostrar el texto en un Toast (mensaje temporal en pantalla)
                val mensaje = socket.recibirMensaje().toString()
                println("Datos recopilados: $mensaje")
                val partes = mensaje.split("#".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

                // Mostrar el mensaje en la UI
                withContext(Dispatchers.Main) {
                    if (partes.size > 1) {
                        Toast.makeText(requireContext(), "Respuesta del servidor: ${partes[1]}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(requireContext(), "Respuesta del servidor no válida", Toast.LENGTH_LONG).show()
                    }
                }

                socket.closeConnection()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error al conectar al Servidor: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    private fun calcularPuntos() {

        for (componente in listaComponentes) {
            if (componente.REGISTRO == componente.RESPUESTA) {
                puntuacion += 20
                Toast.makeText(context, "Respuesta correcta: ${componente.RESPUESTA}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Respuesta incorrecta", Toast.LENGTH_SHORT).show()
            }
        }
        Toast.makeText(context, "Puntuación total: $puntuacion", Toast.LENGTH_SHORT).show()
    }

    private fun iniciarCronometro(tiempoEnMilisegundos: Long) {
        val countDownTimer = object : CountDownTimer(tiempoEnMilisegundos, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tiempoRestante = millisUntilFinished / 1000
                val minutos = tiempoRestante / 60
                val segundos = tiempoRestante % 60
                cronometroTextView.text = String.format("Tiempo restante: %02d:%02d", minutos, segundos)
            }

            override fun onFinish() {
                cronometroTextView.text = "¡Tiempo agotado!"
            }
        }
        countDownTimer.start()
    }
}
