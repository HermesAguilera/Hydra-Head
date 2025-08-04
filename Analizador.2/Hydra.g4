// Archivo de gramática combinada para el lenguaje Hydra
// Nombre del archivo: Hydra.g4

grammar Hydra;

// ==========================================================
//                  SECCIÓN DEL PARSER
// ==========================================================

// La regla inicial
programa: importList? START cuerpo END EOF;

importList: importStatement+;
importStatement: IMPORT IDENTIFIER DOT IDENTIFIER SEMICOLON;

cuerpo: elementocuerpo*;

elementocuerpo:
      declaracion
    | funcion
    | sentencia
    | definicionClase
    | interfaceDefinicion
    ;

// --- Definición de Interfaces ---
interfaceDefinicion: INTERFACE IDENTIFIER (EXTENDS IDENTIFIER)? LBRACE interfaceMiembro* RBRACE;
interfaceMiembro: funcion;

// --- Definición de Clases (POO) ---
definicionClase: accessModifier? CLASS IDENTIFIER (EXTENDS IDENTIFIER)? (IMPLEMENTS IDENTIFIER (COMMA IDENTIFIER)*)? LBRACE miembro* RBRACE;
miembro: accessModifier? (STATIC)? (declaracion | funcion | constructor);

// --- Constructor ---
constructor: accessModifier? IDENTIFIER LPAREN paramList? RPAREN bloque;

// --- Modificadores de Acceso ---
accessModifier: PUBLIC | PRIVATE | PROTECTED;

// --- Definición de Funciones MEJORADA ---
funcion: 
    accessModifier? (STATIC)? FUNCTION IDENTIFIER LPAREN paramList? RPAREN (GO tipo)? bloque;

paramList: 
    parametro (COMMA parametro)*;

parametro: 
    tipo IDENTIFIER;

// --- Bloques de Código ---
bloque: LBRACE cuerpoGeneral RBRACE;
cuerpoGeneral: (declaracion | sentencia)*;

// --- Declaraciones ---
declaracion: accessModifier? (CONST)? tipo declaratorList SEMICOLON;
declaratorList: declarator (COMMA declarator)*;
declarator: (IDENTIFIER|CONSTID) (ASSIGN expression)?;

tipo:
    INT arrayDimensions?
    | FLOAT arrayDimensions?
    | STRING arrayDimensions?
    | DOUBLE arrayDimensions?
    | BOOL arrayDimensions?
    | CHAR arrayDimensions?
    | IDENTIFIER arrayDimensions?;

arrayDimensions:
    LBRACKET RBRACKET
    | LBRACKET RBRACKET LBRACKET RBRACKET
    | LBRACKET RBRACKET LBRACKET RBRACKET LBRACKET RBRACKET;

// --- Sentencias ---
sentencia:
      bloque
    | ifStatement
    | whileStatement
    | forStatement
    | switchStatement
    | doWhileStatement
    | tryStatement
    | ioStatement
    | RETURN expression? SEMICOLON
    | BREAK SEMICOLON
    | CONTINUE SEMICOLON
    | THROW expression SEMICOLON
    | expression SEMICOLON
    ;

// --- Manejo de Excepciones ---
tryStatement: TRY bloque catchBlock* finallyBlock?;
catchBlock: CATCH LPAREN tipo IDENTIFIER RPAREN bloque;
finallyBlock: FINALLY bloque;

// --- Nuevas Reglas ---
ioStatement: (OUTPUT | INPUT) GO expression SEMICOLON;

// --- Estructuras de Control ---
ifStatement:
    IF LPAREN expression RPAREN sentencia (ELSE sentencia)?;

whileStatement: WHILE LPAREN expression RPAREN sentencia;

doWhileStatement: DO sentencia WHILE LPAREN expression RPAREN SEMICOLON;

switchStatement:
    SWITCH LPAREN expression RPAREN LBRACE caseBlock* defaultBlock? RBRACE;
caseBlock
    : CASE expression COLON (declaracion | sentencia)*
    ;

defaultBlock  
    : DEFAULT COLON (declaracion | sentencia)*
    ;
    
// --- Nueva regla para el ciclo for ---
forStatement:
    FOR LPAREN forInit SEMICOLON expression? SEMICOLON forUpdate RPAREN sentencia
    ;

forInitDeclaracion: (CONST)? tipo declaratorList;

forInit:
      forInitDeclaracion
    | expressionList?
    ;

forUpdate:
      expressionList?
    ;

// --- Expresiones CON INICIALIZACIÓN DE ARREGLOS ---
expression: assignmentExpr;

assignmentExpr: 
    logicalOrExpr ((ASSIGN | PLUS_ASSIGN | MINUS_ASSIGN | MULT_ASSIGN | DIV_ASSIGN | MOD_ASSIGN) assignmentExpr)?;

logicalOrExpr: 
    logicalAndExpr (OR logicalAndExpr)*;

logicalAndExpr: 
    equalityExpr (AND equalityExpr)*;

equalityExpr: 
    relationalExpr ((EQ | NEQ) relationalExpr)*;

relationalExpr: 
    additiveExpr ((GT | GE | LT | LE) additiveExpr)*;

additiveExpr: 
    multiplicativeExpr ((PLUS | MINUS) multiplicativeExpr)*;

multiplicativeExpr: 
    exponentialExpr ((MULT | DIV | MOD) exponentialExpr)*;

exponentialExpr: 
    unaryExpr (EXP exponentialExpr)?;

unaryExpr: 
    (NOT | MINUS) unaryExpr
    | postfixExpr;

postfixExpr:
    primaryExpr ((INCREMENT | DECREMENT) | (DOT IDENTIFIER) | (DOT IDENTIFIER LPAREN expressionList? RPAREN) | (LBRACKET expression RBRACKET))*;

primaryExpr:
    LPAREN expression RPAREN
    | NEW IDENTIFIER LPAREN expressionList? RPAREN
    | IDENTIFIER LPAREN expressionList? RPAREN
    | SELF
    | literal
    | arrayInitializer
    | IDENTIFIER
    | CONSTID;

// ***** NUEVA REGLA PARA INICIALIZACIÓN DE ARREGLOS *****
arrayInitializer:
    LBRACE (expression (COMMA expression)*)? RBRACE;

literal:
      NUMBER
    | REAL
    | STRING_LITERAL
    | CHAR_LITERAL
    | TRUE
    | FALSE
    ;

expressionList: expression (COMMA expression)*;

// ==========================================================
//                  SECCIÓN DEL LEXER
// ==========================================================

// --- Palabras Clave ---
START:      'start';
END:        'end';
CLASS:      'class';
INTERFACE:  'interface';
EXTENDS:    'extends';
IMPLEMENTS: 'implements';
NEW:        'new';
SELF:       'self';
STATIC:     'static';
PUBLIC:     'public';
PRIVATE:    'private';
PROTECTED:  'protected';
TRY:        'try';
CATCH:      'catch';
FINALLY:    'finally';
THROW:      'throw';
THROWS:     'throws';
INT:        'int';
FLOAT:      'float';
STRING:     'string';
DOUBLE:     'double';
BOOL:       'bool';
CHAR:       'char';
INPUT:      'input';
OUTPUT:     'output';
IMPORT:     'import';
IF:         'if';
ELSE:       'else';
WHILE:      'while';
FOR:        'for';
RETURN:     'return';
TRUE:       'true';
FALSE:      'false';
BREAK:      'break';
CONTINUE:   'continue';
CONST:      'const';
FUNCTION:   'ft';
CASE:       'case';
SWITCH:     'switch';
DEFAULT:    'default';
DO:         'do';

// --- Literales y Tipos Compuestos ---
REAL:       [0-9]+ '.' [0-9]+;
NUMBER:     [0-9]+;
STRING_LITERAL: '"' ( ~["\\] | '\\' . )*? '"';
CHAR_LITERAL:   '\'' ( ~['\\] | '\\' . ) '\'';

// --- Operadores ---
EQ:         '==';
NEQ:        '!=';
LE:         '<=';
GE:         '>=';
INCREMENT:  '++';
DECREMENT:  '--';
PLUS_ASSIGN:  '+=';
MINUS_ASSIGN: '-=';
MULT_ASSIGN:  '*=';
DIV_ASSIGN:   '/=';
MOD_ASSIGN:   '%=';
GO:         '->';
LT:         '<';
GT:         '>';
ASSIGN:     '=';
PLUS:       '+';
MINUS:      '-';
MULT:       '*';
DIV:        '/';
EXP:        '^';
MOD:        '%';
AND:        '&&';
OR:         '||';
NOT:        '!';

// --- Símbolos ---
LPAREN:     '(';
RPAREN:     ')';
LBRACE:     '{';
RBRACE:     '}';
LBRACKET:   '[';
RBRACKET:   ']';
SEMICOLON:  ';';
COLON:      ':';
COMMA:      ',';
DOT:        '.';

// --- Identificadores ---
CONSTID:    [A-Z_] [A-Z0-9_]*;
IDENTIFIER: [a-zA-Z_] [a-zA-Z0-9_]*;

// --- Ignorar Tokens ---
COMMENT:    '#' ~[\r\n]* -> skip;
WS:         [ \t\r\n]+ -> skip;   // 