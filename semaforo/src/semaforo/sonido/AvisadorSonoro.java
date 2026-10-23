package semaforo.sonido;

import semaforo.estado.EstadoSemaforo;
import semaforo.observador.ObservadorSemaforo;

/** Observador para invidentes: emite un pitido distinto según el estado. */
public class AvisadorSonoro implements ObservadorSemaforo {
    private final EmisorSonido emisor;

    public AvisadorSonoro(EmisorSonido emisor) {
        this.emisor = emisor;
    }

    @Override
    public void alPulso(EstadoSemaforo estado, boolean luzEncendida) {
        if (luzEncendida) {
            emisor.emitir(estado.sonido());
        }
    }
}
