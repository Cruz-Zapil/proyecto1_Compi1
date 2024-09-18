package com.app.gramaticas;




/* Archivo Lexer.flex */

/* Opciones del lexer */
%%
%public
%class LexerXson
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


// Configuración para que las etiquetas XML sean insensibles a mayúsculas
%caseless


/* Reglas para las etiquetas XML-like (case insensitive gracias a %ignorecase) */
"?"       {yychar+=2; System.out.println(yytext()) ; /*return symbol(sym.INTERROGACION);   */ }
"xson"    {yychar+=4; System.out.println(yytext()) ; /*return symbol(sym.XSON);            */ }
"version" {yychar+=7; System.out.println(yytext()) ; /*return symbol(sym.VERSION);         */ }
"="       {yychar+=1; System.out.println(yytext()) ; /*return symbol(sym.IGUAL);           */ }
"1.0"     {yychar+=3; System.out.println(yytext()) ; /*return symbol(sym.VERSION1);        */ }
"<!"      {yychar+=2; System.out.println(yytext()) ; /*return symbol(sym.INICIO_SOLICITUD);*/ }
"<"       {yychar+=1; System.out.println(yytext()) ; /*return symbol(sym.INICIO_ETIQUETA); */ }
">"       {yychar+=1; System.out.println(yytext()) ; /*return symbol(sym.FIN_ETIQUETA);    */ }
"!>"      {yychar+=2; System.out.println(yytext()) ; /*return symbol(sym.FIN_SOLICITUD);   */ }
"realizar"    {yychar+=8  System.out.println(yytext()) ;  /* return symbol(sym.REALIZAR); */       }
"solicitud"   {yychar+=9  System.out.println(yytext()) ;  /* return symbol(sym.SOLICITUD); */      }
"solicitudes" {yychar+=11 System.out.println(yytext()) ;  /* return symbol(sym.SOLICITUDES); */    }
"_"           {yychar+=1  System.out.println(yytext()) ;  /* return symbol(sym.GUION_BAJO); */     }
"fin"         {yychar+=3  System.out.println(yytext()) ;  /* return symbol(sym.FIN); */            }
"realizada"   {yychar+=9  System.out.println(yytext()) ;  /* return symbol(sym.REALIZADA); */      }
"realizadas"  {yychar+=10 System.out.println(yytext()) ;  /* return symbol(sym.REALIZADAS); */     }


/* Nombres de parámetros y valores JSON (case sensitive) */
\"([^\"]*)\"                      { longitudToken = yytext().length(); yychar+=longitudToken; System.out.println(yytext()); /* return symbol(sym.VALOR_CADENA, yytext());*/ }
^[_\-$][a-zA-Z0-9_\-$]*$          { longitudToken = yytext().length(); yychar+=longitudToken; System.out.println(yytext()); /* return symbol(sym.IDS, yytext());*/ }
[0-9]+                            { longitudToken = yytext().length(); yychar+=longitudToken; System.out.println(yytext()); /* return symbol(sym.VALOR_NUMERO, yytext());*/ }


// Reset de %caseless para tokens case-sensitive
%notcaseless
 
/* Reglas para crear nuevo usuario  */
"PARAMETROS"  {yychar+=10  ; System.out.println(yytext());   /*return symbol(sym.PARAMETROS);*/ }
"USUARIO"     {yychar+=7   ; System.out.println(yytext());  /*return symbol(sym.USUARIO);*/ }
"NUEVO"       {yychar+=5   ; System.out.println(yytext());  /*return symbol(sym.NUEVO);*/ }
"DATOS"       {yychar+=5   ; System.out.println(yytext());  /*return symbol(sym.DATOS);*/ }
"PASSWORD"    {yychar+=8   ; System.out.println(yytext());  /*return symbol(sym.PASSWORD);*/ }
"NOMBRE"      {yychar+=6   ; System.out.println(yytext());  /*return symbol(sym.NOMBRE);*/ }
"INSTITUCION" {yychar+=11  ; System.out.println(yytext());  /*return symbol(sym.INSTITUCION);*/ }
"FECHA"       {yychar+=5   ; System.out.println(yytext());  /*return symbol(sym.FFECHA);*/ }
"CREACION"    {yychar+=8   ; System.out.println(yytext());  /*return symbol(sym.CREACION);*/ }

/* Reglas para modificar un usuario */
"MODIFICAR"   {yychar+=9 ; System.out.println(yytext()) ; /*return symbol(sym.MODIFICAR);*/ }
"ANTIGUO"     {yychar+=7 ; System.out.println(yytext()) ; /*return symbol(sym.ANTIGUO);*/ }

/* Reglas para eliminar un usuario */
"ELIMINAR"    {yychar+=8 ; System.out.println(yytext())  ; /*return symbol(sym.ELIMINAR);*/ }

/* Reglas para el loggin  */
"LOGIN"       {yychar+=5; System.out.println(yytext())  ;/*return symbol(sym.LOGIN);*/ }

/* Reglas para crear trivia   */
"NUEVA"     {yychar+=5  ; System.out.println(yytext()); /*return symbol(sym.NUEVA);*/ }
"TRIVIA"    {yychar+=6  ; System.out.println(yytext()); /*return symbol(sym.TRIVIA);*/ }
"TIEMPO"    {yychar+=6  ; System.out.println(yytext()); /*return symbol(sym.TIEMPO);*/ }
"PREGUNTA"  {yychar+=8  ; System.out.println(yytext()); /*return symbol(sym.PREGUNTA);*/ }
"TEMA"      {yychar+=4  ; System.out.println(yytext()); /*return symbol(sym.TEMA);*/ }
"ID"        {yychar+=2  ; System.out.println(yytext()); /*return symbol(sym.ID);*/ }

/* Reglas para eliminar una trivia */


/* Reglas para modificar una trivia */


/* Reglas para agregar componentes a una trivia  */

"AGREGAR"    {yychar+=7  ; System.out.println(yytext())  ;   /*  return symbol(sym.AGREGAR);  */ }
"COMPONENTE" {yychar+=10 ; System.out.println(yytext())  ;   /*  ;return symbol(sym.COMPONENTE);  */ }
"CLASE"      {yychar+=5  ; System.out.println(yytext())  ;   /*  return symbol(sym.CLASE);  */ }
"CAMPO"      {yychar+=5  ; System.out.println(yytext())  ;   /*  return symbol(sym.CAMPO);  */ }
"TEXTO"      {yychar+=5  ; System.out.println(yytext())  ;   /*  return symbol(sym.TEXTO);  */ }
"VISIBLE"    {yychar+=7  ; System.out.println(yytext())  ;   /*  return symbol(sym.VISIBLE);  */ }
"RESPUESTA"  {yychar+=9  ; System.out.println(yytext())  ;   /*  return symbol(sym.RESPUESTA);  */ }
"AREA"       {yychar+=4  ; System.out.println(yytext())  ;   /*  return symbol(sym.AREA);  */ }
"CHECKBOX"   {yychar+=8  ; System.out.println(yytext())  ;   /*  return symbol(sym.CHECKBOX);  */ }
"RADIO"      {yychar+=5  ; System.out.println(yytext())  ;   /*  return symbol(sym.RADIO);  */ }
"COMBO"      {yychar+=5  ; System.out.println(yytext())  ;   /*  return symbol(sym.COMBO);  */ }
"FICHERO"    {yychar+=7  ; System.out.println(yytext())  ;   /*  return symbol(sym.FICHERO);  */ }
"OPCIONES"   {yychar+=8  ; System.out.println(yytext())  ;   /*  return symbol(sym.OPCIONES);  */ }
"FILAS"      {yychar+=5  ; System.out.println(yytext())  ;   /*  return symbol(sym.FILAS);  */ }
"COLUMNAS"   {yychar+=8  ; System.out.println(yytext())  ;   /*  return symbol(sym.COLUMNAS);  */ }
[a-zA-Z|][a-zA-Z0-9|]*  {longitudToken = yytext().length();  yychar+=longitudToken ;  System.out.println(yytext()); /* return symbol(sym.OPTIONS);*/ }

/* Reglas para eliminar componentes de una trivia  */


/* Reglas para modificar componentes de una trivia  */
"INDICE"     {yychar+=6  ; System.out.println(yytext()); /* return symbol(sym.INDICE);*/ }



("\"") {yychar+=1  ; System.out.println(yytext()); /* return symbol(sym.COMILLAS);*/ }

/* Reglas para el contenido JSON (case sensitive) */

"{"      {yychar+=1 ; System.out.println(yytext())  ; /*  return symbol(sym.LLAVE_IZQ);*/  }
"}"      {yychar+=1 ; System.out.println(yytext())  ; /*  return symbol(sym.LLAVE_DER);*/  }
"["      {yychar+=1 ; System.out.println(yytext())  ; /*  return symbol(sym.CORCHETE_IZQ);*/  }
"]"      {yychar+=1 ; System.out.println(yytext())  ; /*  return symbol(sym.CORCHETE_DER);*/  }
":"      {yychar+=1 ; System.out.println(yytext())  ; /*  return symbol(sym.DOS_PUNTOS);*/  }
","      {yychar+=1 ; System.out.println(yytext())  ; /*  return symbol(sym.COMA);*/  }
" "      {yychar+=1  ; }

/* Ignorar espacios en blanco */
[ \t\n\r]                          { yychar+=1; yyline++; }

/* Manejo de errores */
[^]   {yychar+=1; System.out.println("Caracter no reconocido: " + yytext()); }
