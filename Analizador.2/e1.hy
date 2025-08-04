start
    class Persona {
        string nombre;

        Persona(string n) {
            self.nombre = n;
        }

        ft saludar() {
            return self.nombre;
        }
    }

    ft main() {
        Persona p = new Persona("Ana");
        output -> p.saludar();
    }

    main();
end
