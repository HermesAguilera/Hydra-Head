start
    # Funciones aritméticas
    ft add(int a, int b) -> int { return a + b; }
    ft sub(int a, int b) -> int { return a - b; }
    ft mul(int a, int b) -> int { return a * b; }
    ft div(int a, int b) -> int {
        if (b == 0) throw "División por cero";
        return a / b;
    }

    ft main() {
        int op, x, y, resultado;
        bool salir = false;

        while (!salir) {
            output -> "\n=== Calculadora Hydra ===";
            output -> "1) Sumar";
            output -> "2) Restar";
            output -> "3) Multiplicar";
            output -> "4) Dividir";
            output -> "5) Salir";
            output -> "Seleccione opción:";
            input -> op;

            switch (op) {
                case 1:
                    # Sólo aquí entramos al try…catch si hay throw
                    try {
                        output -> "Ingrese primer operando:";
                        input -> x;
                        output -> "Ingrese segundo operando:";
                        input -> y;
                        resultado = add(x, y);
                        output -> "Resultado: " + resultado;
                    } catch (string err) {
                        output -> "¡Error!: " + err;
                    }
                    break;

                case 2:
                    try {
                        output -> "Ingrese primer operando:";
                        input -> x;
                        output -> "Ingrese segundo operando:";
                        input -> y;
                        resultado = sub(x, y);
                        output -> "Resultado: " + resultado;
                    } catch (string err) {
                        output -> "¡Error!: " + err;
                    }
                    break;

                case 3:
                    try {
                        output -> "Ingrese primer operando:";
                        input -> x;
                        output -> "Ingrese segundo operando:";
                        input -> y;
                        resultado = mul(x, y);
                        output -> "Resultado: " + resultado;
                    } catch (string err) {
                        output -> "¡Error!: " + err;
                    }
                    break;

                case 4:
                    try {
                        output -> "Ingrese primer operando:";
                        input -> x;
                        output -> "Ingrese segundo operando:";
                        input -> y;
                        resultado = div(x, y);
                        output -> "Resultado: " + resultado;
                    } catch (string err) {
                        output -> "¡Error!: " + err;
                    }
                    break;

                case 5:
                    salir = true;
                    break;

                default:
                    output -> "Opción inválida. Intente de nuevo.";
                    # no continue aquí, saldrá del switch y volverá al while
                    break;
            }
        }

        output -> "¡Gracias por usar la calculadora!";
    }

    main();
end
