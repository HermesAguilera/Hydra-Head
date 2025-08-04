start
    # Declaración de función para factorial recursivo
    public ft factorial(int n) -> int {
        if (n == 0) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    # Lectura de un número e impresión del resultado
    int x;
    input -> x;
    output -> "El factorial de " + x + " es:";
    output -> factorial(x);
end
