package com.cruzzapil.appmoviltrivia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import model.Trivia

class TriviaDetailFragment : Fragment(R.layout.fragment_trivia_detail) {

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

        // Mostrar el nombre de la trivia
        val triviaNameTextView: TextView = view.findViewById(R.id.triviaNameTextView)
        triviaNameTextView.text = trivia?.NOMBRE

        // Mostrar las preguntas y opciones
        val triviaStructureLayout: LinearLayout = view.findViewById(R.id.triviaStructureLayout)
        trivia?.ESTRUCTURA?.forEach { question ->
            // Crear una vista para la pregunta
            val questionTextView = TextView(context)
            questionTextView.text = question.TEXTO_VISIBLE
            triviaStructureLayout.addView(questionTextView)

            // Dependiendo de la clase de la pregunta, crear el componente adecuado
            when (question.CLASE) {
                "CHECKBOX" -> {
                    // Crear un conjunto de checkboxes para cada opción
                    question.OPCIONES?.split("|")?.forEach { option ->
                        val checkBox = CheckBox(context)
                        checkBox.text = option
                        triviaStructureLayout.addView(checkBox)
                    }
                }
                "RADIO" -> {
                    // Crear un conjunto de RadioButtons dentro de un RadioGroup
                    val radioGroup = RadioGroup(context)
                    question.OPCIONES?.split("|")?.forEach { option ->
                        val radioButton = RadioButton(context)
                        radioButton.text = option
                        radioGroup.addView(radioButton)
                    }
                    triviaStructureLayout.addView(radioGroup)
                }
                "COMBO" -> {
                    // Crear un Spinner (ComboBox) con las opciones
                    val spinner = Spinner(context)
                    val options: List<String> = question.OPCIONES?.split("|") ?: emptyList()
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                    triviaStructureLayout.addView(spinner)
                }
                "CUADRO_TEXTO" -> {
                    // Crear un EditText (campo de texto) y establecer las filas y columnas
                    val editText = EditText(context)
                    editText.hint = "Ingrese su respuesta"
                    editText.setLines(question.FILAS)
                    editText.setEms(question.COLUMNAS)
                    triviaStructureLayout.addView(editText)
                }
                else -> {
                    // Componente desconocido, mostrar mensaje de error o algo similar
                    val errorTextView = TextView(context)
                    errorTextView.text = "Componente no reconocido"
                    triviaStructureLayout.addView(errorTextView)
                }
            }
        }
    }
}
