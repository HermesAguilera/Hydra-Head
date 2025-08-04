start

# ==========================================================
#      Casos de Prueba para Nuevas Estructuras
# ==========================================================

### Pruebas para el ciclo 'for' ###

# --- Caso 1: Ciclo for clásico ---
# Valida la sintaxis más común: declaración, condición y actualización.
output -> "Inicia prueba del ciclo for clásico:";
for (int i = 0; i < 5; i++) {
    output -> "Valor de i: " + i;
}
output -> "Fin de prueba del ciclo for clásico.";


# --- Caso 2: Ciclo for con múltiples variables ---
# Valida el uso de 'declaratorList' y 'expressionList' para manejar
# múltiples inicializaciones y actualizaciones.
output -> "Inicia prueba del ciclo for con múltiples variables:";
for (int i = 0, j = 10; i < j; i++, j = j - 1) {
    output -> "i = " + i + ", j = " + j;
}
output -> "Fin de prueba del ciclo for con múltiples variables.";


# --- Caso 3: Ciclo for con variables pre-declaradas ---
# Valida la parte de 'forInit' que usa una 'expressionList' en lugar
# de una declaración completa.
int k;
output -> "Inicia prueba del ciclo for con variable pre-declarada:";
for (k = 3; k > 0; k--) {
    output -> "Valor de k: " + k;
}
output -> "Fin de prueba del ciclo for con variable pre-declarada.";


# --- Caso 4: Ciclo for "infinito" con break ---
# Valida el caso donde la condición y la actualización están vacías.
# También prueba la sentencia 'break' dentro del ciclo.
int contador = 0;
output -> "Inicia prueba del ciclo for infinito:";
for (;;) {
    contador++;
    output -> "Iteración infinita número: " + contador;
    if (contador >= 3) {
        output -> "Saliendo del bucle con break...";
        break;
    }
}
output -> "Fin de prueba del ciclo for infinito.";


# --- Caso 5: Ciclo for con anidación y 'continue' ---
# Valida la correcta interpretación de un 'for' dentro de otro
# y el uso de la sentencia 'continue'.
output -> "Inicia prueba de ciclos for anidados:";
for (int x = 0; x < 3; x++) {
    output -> "Ciclo externo, x = " + x;
    for (int y = 0; y < 3; y++) {
        if (y == 1) {
            output -> "   Saltando y=1 con continue...";
            continue;
        }
        output -> "   Ciclo interno, y = " + y;
    }
}
output -> "Fin de prueba de ciclos for anidados.";


### Pruebas de Interacción ###

class Calculadora {
    ft sumarEnBucle(int limite) {
        int suma = 0;
        # Prueba de un 'for' dentro de un método de clase.
        for(int i = 1; i <= limite; i++) {
            suma = suma + i;
        }
        return suma;
    }
}

Calculadora miCalc = new Calculadora();
int resultado_suma;
resultado_suma = miCalc.sumarEnBucle(5); # Debería ser 1+2+3+4+5 = 15
output -> "El resultado de sumar del 1 al 5 es: " + resultado_suma;


end