package semaforo.observador;

import semaforo.estado.EstadoSemaforo;

/** Patrón Observer: quien quiera reaccionar al semáforo implementa esta interfaz. */
public interface ObservadorSemaforo {
    default void alCambiarEstado(EstadoSemaforo nuevoEstado) { }
    void alPulso(EstadoSemaforo estado, boolean luzEncendida);
}
