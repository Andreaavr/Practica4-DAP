package semaforo.vista;

import semaforo.estado.EstadoSemaforo;
import semaforo.observador.ObservadorSemaforo;

import javax.swing.SwingUtilities;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * Patrón Decorator: crea y ejecuta cualquier observador Swing en el hilo de Swing (EDT),
 * de modo que el observador decorado no tenga que preocuparse de los hilos.
 */
public class ObservadorEnHiloSwing implements ObservadorSemaforo {
    private final ObservadorSemaforo delegado;

    /** @param fabrica crea el observador; se invoca en el hilo de Swing. */
    public ObservadorEnHiloSwing(Supplier<? extends ObservadorSemaforo> fabrica) {
        this.delegado = crearEnHiloSwing(fabrica);
    }

    @Override
    public void alCambiarEstado(EstadoSemaforo nuevoEstado) {
        SwingUtilities.invokeLater(() -> delegado.alCambiarEstado(nuevoEstado));
    }

    @Override
    public void alPulso(EstadoSemaforo estado, boolean luzEncendida) {
        SwingUtilities.invokeLater(() -> delegado.alPulso(estado, luzEncendida));
    }

    private static ObservadorSemaforo crearEnHiloSwing(Supplier<? extends ObservadorSemaforo> fabrica) {
        if (SwingUtilities.isEventDispatchThread()) {
            return fabrica.get();
        }
        AtomicReference<ObservadorSemaforo> creado = new AtomicReference<>();
        try {
            SwingUtilities.invokeAndWait(() -> creado.set(fabrica.get()));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrumpido al crear la vista", e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("No se pudo crear la vista", e.getCause());
        }
        return creado.get();
    }
}
