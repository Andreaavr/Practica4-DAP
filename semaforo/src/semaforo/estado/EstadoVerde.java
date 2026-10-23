package semaforo.estado;

import semaforo.modelo.Color;
import semaforo.modelo.PerfilSonoro;

import java.time.Duration;

/** Verde fijo 7 s (de los 10 s de verde). Pitido agudo y pausado. */
public class EstadoVerde extends EstadoBase {
    public EstadoVerde() {
        super("Verde", Color.VERDE, Duration.ofSeconds(7), Duration.ofMillis(700),
              false, new PerfilSonoro(1000, 120));
    }
}
