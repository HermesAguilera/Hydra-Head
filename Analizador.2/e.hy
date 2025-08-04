start
    # 1) Arreglo vacío explícito
    int[] emptyIntArray = {};
    output -> "emptyIntArray.length = " + emptyIntArray.length;

    # 2) Inicialización literal unidimensional
    int[] primes = {2, 3, 5, 7, 11};
    for (int i = 0; i < primes.length; i++) {
        output -> "primes[" + i + "] = " + primes[i];
    }

    # 3) Modificar un elemento
    primes[2] = 13;
    output -> "primes[2] modificado = " + primes[2];

    # 4) Función que suma un arreglo
    ft sumArray(int[] arr) -> int {
        int total = 0;
        for (int j = 0; j < arr.length; j++) {
            total = total + arr[j];
        }
        return total;
    }
    output -> "Suma de primes = " + sumArray(primes);

    # 5) Arreglo bidimensional
    int[][] matrix = {
        {1, 2, 3},
        {4, 5, 6}
    };
    for (int r = 0; r < matrix.length; r++) {
        for (int c = 0; c < matrix[r].length; c++) {
            output -> "matrix[" + r + "][" + c + "] = " + matrix[r][c];
        }
    }

    # 6) Arreglo de cadenas
    string[] days = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
    for (int k = 0; k < days.length; k++) {
        output -> "Día " + k + ": " + days[k];
    }

    # 7) Continue y break en bucle sobre arreglo
    int[] nums = {10, -1, 20, -2, 30, 40};
    for (int n = 0; n < nums.length; n++) {
        if (nums[n] < 0) continue;
        if (nums[n] > 25) break;
        output -> "Procesando nums[" + n + "] = " + nums[n];
    }

    # 8) Arreglo más grande
    int[] big = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
    for (int i = 0; i < big.length; i++) {
        output -> big[i];
    }
end
