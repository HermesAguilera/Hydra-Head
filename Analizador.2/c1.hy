import math.sin;
import math.cos;
import math.pow;
import math.sqrt;

start
    double op, a, b, x, y, n;
    bool repetir = true;

    while (repetir) {
        output -> "\n=== Calculadora Math ===";
        output -> "1) sin(x)";
        output -> "2) cos(x)";
        output -> "3) x^y";
        output -> "4) sqrt(x)";
        output -> "5) Salir";
        output -> "Opción:";
        input  -> op;

        switch (op) {
            case 1:
                output -> "Ingrese ángulo en radianes:";
                input  -> a;
                output -> "sin(" + a + ") = " + sin(a);
                break;

            case 2:
                output -> "Ingrese ángulo en radianes:";
                input  -> b;
                output -> "cos(" + b + ") = " + cos(b);
                break;

            case 3:
                output -> "Base x:";
                input  -> x;
                output -> "Exponente y:";
                input  -> y;
                output -> x + "^" + y + " = " + pow(x, y);
                break;

            case 4:
                output -> "Número para raíz:";
                input  -> n;
                output -> "sqrt(" + n + ") = " + sqrt(n);
                break;

            case 5:
                repetir = false;
                break;

            default:
                output -> "Opción inválida.";
                break;
        }
    }

    output -> "¡Hasta luego!";
end