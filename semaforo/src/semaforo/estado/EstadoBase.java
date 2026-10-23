package semaforo.estado;

import semaforo.modelo.Color;
import semaforo.modelo.PerfilSonoro;

import java.time.Duration;
import java.util.Objects;

/** Datos comunes de los estados, para no repetir código en cada uno. */
public abstract class EstadoBase implements EstadoSemaforo {
    private final String nombre;
    private final Color color;
    private final Duration duracion;
    private final Duration intervalo;
    private final boolean parpadea;
    private final PerfilSonoro sonido;

    protected EstadoBase(String nombre, Color color, Duration duracion, Duration intervalo,
                         boolean parpadea, PerfilSonoro sonido) {
        this.nombre = Objects.requireNonNull(nombre);
        this.color = Objects.requireNonNull(color);
        this.duracion = Objects.requireNonNull(duracion);
        this.intervalo = Objects.requireNonNull(intervalo);
        this.parpadea = parpadea;
        this.sonido = Objects.requireNonNull(sonido);
        if (duracion.isZero() || duracion.isNegative() || intervalo.isZero() || intervalo.isNegative()) {
            throw new IllegalArgumentException("Duración e intervalo deben ser positivos en " + nombre);
        }
    }

    @Override public String nombre() { return nombre; }
    @Override public Color color() { return color; }
    @Override public Duration duracion() { return duracion; }
    @Override public Duration intervaloPulso() { return intervalo; }
    @Override public boolean parpadea() { return parpadea; }
    @Override public PerfilSonoro sonido() { return sonido; }
}
