package model


import java.io.Serializable

data class Registro (

    val ID_TRIVIA: String,
    val USUARIO:String,
    val PUNTEO: Int,
    val TIEMPO: Int,
    val RESPUESTAS: List<CompRegistro>

): Serializable

