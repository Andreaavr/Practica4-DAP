package semaforo.estado;

import semaforo.modelo.Color;
import semaforo.modelo.PerfilSonoro;

import java.time.Duration;

/**
 * Patrón State: describe cómo se comporta el semáforo mientras está en este estado.
 * El estado NO conoce al siguiente: el orden lo decide una {@link semaforo.secuencia.SecuenciaEstados}.
 */
public interface EstadoSemaforo {
    String nombre();
    Color color();
    Duration duracion();
    /** Tiempo entre pulsos (pitidos y/o parpadeos). */
    Duration intervaloPulso();
    /** Si es true, la luz se enciende y apaga en cada pulso. */
    boolean parpadea();
    PerfilSonoro sonido();
}
