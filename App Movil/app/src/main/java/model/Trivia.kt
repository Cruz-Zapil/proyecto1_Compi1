package model

import java.io.Serializable

data class Trivia(
    val ID_TRIVIA: String,
    val NOMBRE: String,
    val TIEMPO: Int,
    val USUARIO_CREACION: String,
    val TEMA: String,
    val ESTRUCTURA: List<Componente>,
    val FECHA_CREACION: String

) : Serializable