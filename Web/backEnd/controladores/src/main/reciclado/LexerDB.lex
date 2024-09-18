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

("\"") {yychar+=1  ;return symbol(simbol.COMILLAS); }


// Reset de %caseless para tokens case-sensitive
%notcaseless
 
/* Reglas para crear nuevo usuario  */
"PARAMETROS"  {yychar+=10 ;return new Symbol(simbol.PARAMETROS); }
"USUARIO"     {yychar+=7  ;return new Symbol(simbol.USUARIO); }
"NUEVO"       {yychar+=5  ;return new Symbol(simbol.NUEVO); }
"DATOS"       {yychar+=5  ;return new Symbol(simbol.DATOS); }
"PASSWORD"    {yychar+=8  ;return new Symbol(simbol.PASSWORD); }
"NOMBRE"      {yychar+=6  ;return new Symbol(simbol.NOMBRE); }
"INSTITUCION" {yychar+=11 ;return new Symbol(simbol.INSTITUCION); }
"FECHA"       {yychar+=5  ;return new Symbol(simbol.FFECHA); }
"CREACION"    {yychar+=8  ;return new Symbol(simbol.CREACION); }

/* Reglas para modificar un usuario */
"MODIFICAR"  {yychar+=9   ;return new Symbol(simbol.MODIFICAR); }
"ANTIGUO"    {yychar+=7   ;return new Symbol(simbol.ANTIGUO); }

/* Reglas para eliminar un usuario */
"ELIMINAR"  {yychar+=8    ;return new Symbol(simbol.ELIMINAR); }

/* Reglas para el loggin  */
"LOGIN"     {yychar+=5    ;return new Symbol(simbol.LOGIN); }

/* Reglas para crear trivia   */
"NUEVA"     {yychar+=5    ;return new Symbol(simbol.NUEVA); }
"TRIVIA"    {yychar+=6    ;return new Symbol(simbol.TRIVIA); }
"TIEMPO"    {yychar+=6    ;return new Symbol(simbol.TIEMPO); }
"PREGUNTA"  {yychar+=8    ;return new Symbol(simbol.PREGUNTA); }
"TEMA"      {yychar+=4    ;return new Symbol(simbol.TEMA); }
"ID"        {yychar+=2    ;return new Symbol(simbol.ID); }

/* Reglas para eliminar una trivia */


/* Reglas para modificar una trivia */


/* Reglas para agregar componentes a una trivia  */

"AGREGAR"    {yychar+=7  ;return new Symbol(simbol.AGREGAR); }
"COMPONENTE" {yychar+=10 ;return new Symbol(simbol.COMPONENTE); }
"CLASE"      {yychar+=5  ;return new Symbol(simbol.CLASE); }
"CAMPO"      {yychar+=5  ;return new Symbol(simbol.CAMPO); }
"TEXTO"      {yychar+=5  ;return new Symbol(simbol.TEXTO); }
"VISIBLE"    {yychar+=7  ;return new Symbol(simbol.VISIBLE); }
"RESPUESTA"  {yychar+=9  ;return new Symbol(simbol.RESPUESTA); }
"AREA"       {yychar+=4  ;return new Symbol(simbol.AREA); }
"CHECKBOX"   {yychar+=8  ;return new Symbol(simbol.CHECKBOX); }
"RADIO"      {yychar+=5  ;return new Symbol(simbol.RADIO); }
"COMBO"      {yychar+=5  ;return new Symbol(simbol.COMBO); }
"FICHERO"    {yychar+=7  ;return new Symbol(simbol.FICHERO); }
"OPCIONES"   {yychar+=8  ;return new Symbol(simbol.OPCIONES); }
"FILAS"      {yychar+=5  ;return new Symbol(simbol.FILAS); }
"COLUMNAS"   {yychar+=8  ;return new Symbol(simbol.COLUMNAS); }
/* Reglas para eliminar componentes de una trivia  */


/* Reglas para modificar componentes de una trivia  */
"INDICE"     {yychar+=6  ;return new Symbol(simbol.INDICE); }

/* Reglas para el contenido JSON (case sensitive) */
"REGISTRO" {yychar+=7    ;return new Symbol(simbol.REGISTRO); }
"ESTRUCTURA" {yychar+=9  ;return new Symbol(simbol.ESTRUCTURA); }





/* Reglas para el contenido JSON (case sensitive) */

"{"      {yychar+=1  ;return new Symbol(simbol.LLAVE_IZQ); }
"}"      {yychar+=1  ;return new Symbol(simbol.LLAVE_DER); }
"["      {yychar+=1  ;return new Symbol(simbol.CORCHETE_IZQ); }
"]"      {yychar+=1  ;return new Symbol(simbol.CORCHETE_DER); }
":"      {yychar+=1  ;return new Symbol(simbol.DOS_PUNTOS); }
","      {yychar+=1  ;return new Symbol(simbol.COMA); }
" "      {yychar+=1  ; }
"("      {yychar+=1  ;return new Symbol(simbol.PARENT_IZQ); }
")"      {yychar+=1  ;return new Symbol(simbol.PARENT_DER); }

/* Ignorar espacios en blanco */
[ \t\n\r]                          { yychar+=1; yyline++; }



// Configuración para que las etiquetas XML sean insensibles a mayúsculas
%caseless


"db"                          {yychar+=2             ;return new Symbol(simbol.DB); }
"."                           {yychar+=1             ;return new Symbol(simbol.PUNTO); }
"trivia"[1-9][0-9]*                      {yychar+=5  ;return new Symbol(simbol.DBTRIVIA); }
"usuario"[1-9][0-9]*                     {yychar+=6  ;return new Symbol(simbol.DBUSUARIO); }
"registro"[1-9][0-9]*                    {yychar+=7  ;return new Symbol(simbol.DBREGISTRO); }


/* Reglas para las etiquetas XML-like (case insensitive gracias a %ignorecase) */


/* Nombres de parámetros y valores JSON (case sensitive) */

[a-zA-Z|][a-zA-Z0-9|]*                                 {longitudToken = yytext().length();  yychar+=longitudToken; return new Symbol(sym.OPTIONS); }
([a-zA-Z0-9]+[]*[a-zA-Z0-9]+)+                         { longitudToken = yytext().length(); yychar+=longitudToken; return new Symbol(simbol.VALOR_CADENA, yytext()); }
([_]|[$]|[-])+[a-zA-Z0-9]+([_]|[$]|[-])*               { longitudToken = yytext().length(); yychar+=longitudToken; return new Symbol(simbol.IDS, yytext()); }
[0-9]+                                                 { longitudToken = yytext().length(); yychar+=longitudToken; return new Symbol(simbol.VALOR_NUMERO, yytext()); }




/* Manejo de errores */
[^]   {yychar+=1; System.out.println("Caracter no reconocido: " + yytext()); }
