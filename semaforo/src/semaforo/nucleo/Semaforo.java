package semaforo.nucleo;

import semaforo.estado.EstadoSemaforo;
import semaforo.observador.ObservadorSemaforo;
import semaforo.secuencia.SecuenciaEstados;
import semaforo.tiempo.Reloj;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Contexto del patrón State y sujeto del patrón Observer.
 * Todo lo que necesita (orden de estados, reloj, observadores) le llega desde fuera:
 * no conoce estados concretos, ventanas, sonido ni el reloj del sistema.
 */
public class Semaforo {
    private final SecuenciaEstados secuencia;
    private final Reloj reloj;
    private final List<ObservadorSemaforo> observadores = new CopyOnWriteArrayList<>();
    private volatile boolean activo = true;

    public Semaforo(SecuenciaEstados secuencia, Reloj reloj) {
        this.secuencia = Objects.requireNonNull(secuencia);
        this.reloj = Objects.requireNonNull(reloj);
    }

    public void suscribir(ObservadorSemaforo observador) {
        observadores.add(observador);
    }

    public void detener() {
        activo = false;
    }

    /** Bucle principal: bloquea hasta que se llame a {@link #detener()}. */
    public void iniciar() throws InterruptedException {
        while (activo) {
            EstadoSemaforo estado = secuencia.siguiente();
            observadores.forEach(o -> o.alCambiarEstado(estado));
            ejecutar(estado);
        }
    }

    private void ejecutar(EstadoSemaforo estado) throws InterruptedException {
        long fin = reloj.ahoraMs() + estado.duracion().toMillis();
        boolean luzEncendida = true;
        while (activo && reloj.ahoraMs() < fin) {
            final boolean luz = luzEncendida;
            observadores.forEach(o -> o.alPulso(estado, luz));
            if (estado.parpadea()) {
                luzEncendida = !luzEncendida;
            }
            long restante = fin - reloj.ahoraMs();
            long espera = Math.max(0, Math.min(estado.intervaloPulso().toMillis(), restante));
            reloj.esperar(Duration.ofMillis(espera));
        }
    }
}
