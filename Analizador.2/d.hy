start
    # Función de ejemplo con distintos ciclos
    ft loopExamples() {
        int i;

        # 1) while simple
        i = 0;
        while (i < 5) {
            output -> "While: i = " + i;
            i++;
        }

        # 2) do-while
        i = 0;
        do {
            output -> "Do-While: i = " + i;
            i++;
        } while (i < 5);

        # 3) for clásico
        for (i = 0; i < 5; i++) {
            if (i == 2) continue;       # salta la iteración cuando i==2
            output -> "For clásico: i = " + i;
        }

        # 4) for con declaración interna
        for (int j = 1; j <= 3; j++) {
            output -> "For con j = " + j;
        }

        # 5) bucles anidados
        for (int row = 1; row <= 2; row++) {
            for (int col = 1; col <= 3; col++) {
                output -> "Matriz[" + row + "][" + col + "]";
            }
        }

        # 6) break en un while infinito
        i = 0;
        while (true) {
            i++;
            if (i == 3) {
                output -> "Break en i = " + i;
                break;
            }
        }
    }

    # Invocamos la función de ciclos
    loopExamples();
end
