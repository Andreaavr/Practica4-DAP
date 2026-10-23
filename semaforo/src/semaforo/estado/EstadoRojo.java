package semaforo.estado;

import semaforo.modelo.Color;
import semaforo.modelo.PerfilSonoro;

import java.time.Duration;

/** Rojo 10 s. Pitido grave y lento. */
public class EstadoRojo extends EstadoBase {
    public EstadoRojo() {
        super("Rojo", Color.ROJO, Duration.ofSeconds(10), Duration.ofMillis(1000),
              false, new PerfilSonoro(300, 400));
    }
}
