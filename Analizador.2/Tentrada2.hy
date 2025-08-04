start

class Estudiante {
    string nombre;
    int edad;
    double[] notas;

    # Constructor
    ft Estudiante(string n, int e, double[] nts) {
        self.nombre = n;
        self.edad   = e;
        self.notas  = nts;
    }

    # Calcula promedio con bucle for
    ft calcularPromedio() {
        double suma = 0.0;
        for (int i = 0; i < self.notas.length; i++) {
            suma = suma + self.notas[i];
        }
        return suma / self.notas.length;
    }

    # Usa operador relacional
    ft esMayorEdad() {
        return self.edad >= 18;
    }

    # Método estático
    static ft saludo() {
        return "Bienvenido, ¡Hydra POO!";
    }
}

ft main() {
    # crea array literal
    double[] m = { 95.0, 87.5, 100.0 };
    # instancia objeto
    Estudiante juan = new Estudiante("Juan Perez", 20, m);

    # Imprime saludo y resultados
    output -> Estudiante.saludo();
    output -> "Promedio: " + juan.calcularPromedio();
    output -> "Mayor de edad? " + juan.esMayorEdad();

    # Llama a función de math.matematicas
    output -> "Raíz cuadrada de promedio: " 
               + math.sqrt( juan.calcularPromedio() );
}

end
