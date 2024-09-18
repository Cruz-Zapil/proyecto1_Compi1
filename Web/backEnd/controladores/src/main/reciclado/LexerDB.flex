package com.app.gramaticas;

import java_cup.runtime.Symbol;


/* Archivo Lexer.flex */

/* Opciones del lexer */
%public
%class LexerDB
%cup
%unicode


%init{ 
    yyline = 1; 
    yychar = 1; 
    
%init} 
 

/* Definición de los símbolos del CUP */
%{
     int longitudToken=0;

  /* Declaración de tokens que serán reconocidos por el analizador sintáctico */
  private Symbol symbol(int type) {
    return new Symbol(type);
  }
%}

/* Definición de patrones y tokens */
%%




// Reset de %caseless para tokens case-sensitive
%notcaseless
 
/* Reglas para crear nuevo usuario  */
"PARAMETROS"  {yychar+=10 ;return symbol(simbol.PARAMETROS); }
"USUARIO"     {yychar+=7  ;return symbol(simbol.USUARIO); }
"NUEVO"       {yychar+=5  ;return symbol(simbol.NUEVO); }
"DATOS"       {yychar+=5  ;return symbol(simbol.DATOS); }
"PASSWORD"    {yychar+=8  ;return symbol(simbol.PASSWORD); }
"NOMBRE"      {yychar+=6  ;return symbol(simbol.NOMBRE); }
"INSTITUCION" {yychar+=11 ;return symbol(simbol.INSTITUCION); }
"FECHA"       {yychar+=5  ;return symbol(simbol.FFECHA); }
"CREACION"    {yychar+=8  ;return symbol(simbol.CREACION); }

/* Reglas para modificar un usuario */
"MODIFICAR"  {yychar+=9  ;return symbol(simbol.MODIFICAR); }
"ANTIGUO"    {yychar+=7  ;return symbol(simbol.ANTIGUO); }

/* Reglas para eliminar un usuario */
"ELIMINAR"  {yychar+=8  ;return symbol(simbol.ELIMINAR); }

/* Reglas para el loggin  */
"LOGIN"     {yychar+=5  ;return symbol(simbol.LOGIN); }

/* Reglas para crear trivia   */
"NUEVA"     {yychar+=5  ;return symbol(simbol.NUEVA); }
"TRIVIA"    {yychar+=6  ;return symbol(simbol.TRIVIA); }
"TIEMPO"    {yychar+=6  ;return symbol(simbol.TIEMPO); }
"PREGUNTA"  {yychar+=8  ;return symbol(simbol.PREGUNTA); }
"TEMA"      {yychar+=4  ;return symbol(simbol.TEMA); }
"ID"        {yychar+=2  ;return symbol(simbol.ID); }

/* Reglas para eliminar una trivia */


/* Reglas para modificar una trivia */


/* Reglas para agregar componentes a una trivia  */

"AGREGAR"    {yychar+=7  ;return symbol(simbol.AGREGAR); }
"COMPONENTE" {yychar+=10 ;return symbol(simbol.COMPONENTE); }
"CLASE"      {yychar+=5  ;return symbol(simbol.CLASE); }
"CAMPO"      {yychar+=5  ;return symbol(simbol.CAMPO); }
"TEXTO"      {yychar+=5  ;return symbol(simbol.TEXTO); }
"VISIBLE"    {yychar+=7  ;return symbol(simbol.VISIBLE); }
"RESPUESTA"  {yychar+=9  ;return symbol(simbol.RESPUESTA); }
"AREA"       {yychar+=4  ;return symbol(simbol.AREA); }
"CHECKBOX"   {yychar+=8  ;return symbol(simbol.CHECKBOX); }
"RADIO"      {yychar+=5  ;return symbol(simbol.RADIO); }
"COMBO"      {yychar+=5  ;return symbol(simbol.COMBO); }
"FICHERO"    {yychar+=7  ;return symbol(simbol.FICHERO); }
"OPCIONES"   {yychar+=8  ;return symbol(simbol.OPCIONES); }
"FILAS"      {yychar+=5  ;return symbol(simbol.FILAS); }
"COLUMNAS"   {yychar+=8  ;return symbol(simbol.COLUMNAS); }
/* Reglas para eliminar componentes de una trivia  */


/* Reglas para modificar componentes de una trivia  */
"INDICE"     {yychar+=6  ;return symbol(simbol.INDICE); }

/* Reglas para el contenido JSON (case sensitive) */
"REGISTRO" {yychar+=7  ;return symbol(simbol.REGISTRO); }
"ESTRUCTURA" {yychar+=9  ;return symbol(simbol.ESTRUCTURA); }



("\"") {yychar+=1  ;return symbol(simbol.COMILLAS); }

/* Reglas para el contenido JSON (case sensitive) */

"{"      {yychar+=1  ;return symbol(simbol.LLAVE_IZQ); }
"}"      {yychar+=1  ;return symbol(simbol.LLAVE_DER); }
"["      {yychar+=1  ;return symbol(simbol.CORCHETE_IZQ); }
"]"      {yychar+=1  ;return symbol(simbol.CORCHETE_DER); }
":"      {yychar+=1  ;return symbol(simbol.DOS_PUNTOS); }
","      {yychar+=1  ;return symbol(simbol.COMA); }
" "      {yychar+=1  ; }
"("      {yychar+=1  ;return symbol(simbol.PARENT_IZQ); }
")"      {yychar+=1  ;return symbol(simbol.PARENT_DER); }

/* Ignorar espacios en blanco */
[ \t\n\r]                          { yychar+=1; yyline++; }



// Configuración para que las etiquetas XML sean insensibles a mayúsculas
%caseless


/* Reglas para las etiquetas XML-like (case insensitive gracias a %ignorecase) */


/* Nombres de parámetros y valores JSON (case sensitive) */

[a-zA-Z|][a-zA-Z0-9|]*  {longitudToken = yytext().length();  yychar+=longitudToken   ;return symbol(sym.OPTIONS); }
\"([^\"]*)\"                      { longitudToken = yytext().length(); yychar+=longitudToken; return symbol(simbol.VALOR_CADENA, yytext()); }
^[_\-$][a-zA-Z0-9_\-$]*$          { longitudToken = yytext().length(); yychar+=longitudToken; return symbol(simbol.IDS, yytext()); }
[0-9]+                            { longitudToken = yytext().length(); yychar+=longitudToken; return symbol(simbol.VALOR_NUMERO, yytext()); }

"db"                          {yychar+=2  ;return symbol(simbol.DB); }
"."                           {yychar+=1  ;return symbol(simbol.PUNTO); }
"trivia"[1-9][0-9]*                      {yychar+=5  ;return symbol(simbol.DBTRIVIA); }
"usuario"[1-9][0-9]*                     {yychar+=6  ;return symbol(simbol.DBUSUARIO); }
"registro"[1-9][0-9]*                    {yychar+=7  ;return symbol(simbol.DBREGISTRO); }




/* Manejo de errores */
[^]   {yychar+=1; System.err.println("Caracter no reconocido: " + yytext()); }
