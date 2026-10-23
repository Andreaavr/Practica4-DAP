package semaforo.estado;

import semaforo.modelo.Color;
import semaforo.modelo.PerfilSonoro;

import java.time.Duration;

/** Ámbar 3 s. Pitido de tono medio, ritmo intermedio. */
public class EstadoAmbar extends EstadoBase {
    public EstadoAmbar() {
        super("Ámbar", Color.AMBAR, Duration.ofSeconds(3), Duration.ofMillis(500),
              false, new PerfilSonoro(600, 150));
    }
}
