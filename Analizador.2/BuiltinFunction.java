import java.util.List;
import java.util.function.Function;

/**
 * Un HydraFunction que delega su ejecución en un Function<List<HydraValue>,HydraValue>
 */
public class BuiltinFunction extends HydraFunction {
    private final Function<List<HydraValue>, HydraValue> impl;

    /**
     * @param name      el nombre de la función (por ej. "sin")
     * @param arity     número de parámetros
     * @param impl      implementación nativa que recibe la lista de argumentos
     */
    public BuiltinFunction(String name, int arity, Function<List<HydraValue>, HydraValue> impl) {
        // Llamo al constructor de HydraFunction para montar firma y body vacío
        super(name,
              // nombres de parámetros genéricos _0, _1, … según arity
              java.util.stream.IntStream.range(0, arity)
                .mapToObj(i -> "_" + i).toList(),
              // tipos los dejamos todos "double" para funciones matemáticas
              java.util.stream.IntStream.range(0, arity)
                .mapToObj(i -> "double").toList(),
              "double",
              /* cuerpo: no se usará */ null,
              /* isStatic */ true,
              /* access */ "public"
        );
        this.impl = impl;
    }

    @Override
    public HydraValue invoke(List<HydraValue> args) {
        // cuando tu executor detecte un BuiltinFunction, llamará a invoke en lugar de visit()
        return impl.apply(args);
    }
}
