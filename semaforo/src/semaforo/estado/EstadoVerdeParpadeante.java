package semaforo.estado;

import semaforo.modelo.Color;
import semaforo.modelo.PerfilSonoro;

import java.time.Duration;

/** Últimos 3 s del verde: luz y sonido más rápidos. */
public class EstadoVerdeParpadeante extends EstadoBase {
    public EstadoVerdeParpadeante() {
        super("Verde parpadeando", Color.VERDE, Duration.ofSeconds(3), Duration.ofMillis(200),
              true, new PerfilSonoro(1000, 80));
    }
}
