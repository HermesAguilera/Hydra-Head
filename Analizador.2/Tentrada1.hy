start

# Declarar variables de diferentes tipos
int edad;
string nombre;
float salario;
bool activo;

# Solicitar datos al usuario
output -> "=== ENTRADA DE DATOS ===";
input -> nombre;
input -> edad;
input -> salario;
input -> activo;

# Mostrar los valores ingresados
output -> "";
output -> "=== DATOS INGRESADOS ===";
output -> "Nombre: " + nombre;
output -> "Edad: " + edad;
output -> "Salario: " + salario;
output -> "Activo: " + activo;

# Operaciones con los datos
int nuevaEdad = edad + 1;
output -> nombre + " tendrá " + nuevaEdad + " años el próximo año";

if (activo) {
    output -> nombre + " está activo y gana $" + salario;
} else {
    output -> nombre + " no está activo";
}

end