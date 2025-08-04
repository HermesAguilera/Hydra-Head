start

# === Pruebas de Declaraciones y Tipos ===
int a = 5;
float b = 2.5;
string msg = "Hydra";
bool flag = false;
char ch = 'Z';

output -> a;
output -> b;
output -> msg;
output -> flag;
output -> ch;

# === Operaciones Aritméticas ===
output -> a + 3;
output -> a - 2;
output -> a * 2;
output -> a / 2;
output -> a % 2;
output -> a ^ 3;

# === Asignaciones Compuestas ===
a += 1;
output -> a;
a *= 2;
output -> a;

# === Control de Flujo ===
if (a > 5) {
    output -> "a es mayor que 5";
} else {
    output -> "a no es mayor que 5";
}

int i;
for (i = 0; i < 3; i++) {
    output -> i;
}

while (b < 6.5) {
    output -> b;
    b = b + 2.0;
}

do {
    output -> flag;
    flag = true;
} while (!flag);

# === Arreglos ===
int[] nums = {10, 20, 30};
output -> nums[0];
output -> nums[2];

# === Funciones ===
ft doble(int x) -> int {
    return x * 2;
}
output -> doble(7);

# === Excepciones ===
try {
    throw "error";
} catch (string e) {
    output -> "Capturado: " ^ e;
} finally {
    output -> "Fin Excepción";
}

# === Clases y POO ===
class Punto {
    int x;
    int y;
    Punto(int x0, int y0) {
        self.x = x0;
        self.y = y0;
    }
    ft mover(int dx, int dy) -> void {
        self.x = self.x + dx;
        self.y = self.y + dy;
    }
}

Punto p = new Punto(1, 2);
output -> p.x;
output -> p.y;
p.mover(3, 4);
output -> p.x;
output -> p.y;

class Util {
    static ft inc(int v) -> int {
        return v + 1;
    }
}
output -> Util.inc(9);

end
