start
    # Constantes y variables
    const int MAX = 10;
    int x = 5, y;
    float z;

    # Función que suma dos enteros
    ft sum(int a, int b) -> int {
        return a + b;
    }

    # Función principal
    ft main() {
        y = sum(x, 3);
        z = x * 2.5 + y / 3.0;
        output -> "Resultado: " + z;
    }

    main();

end
