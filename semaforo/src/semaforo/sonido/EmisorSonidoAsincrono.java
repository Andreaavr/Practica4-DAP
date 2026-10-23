package semaforo.sonido;

import semaforo.modelo.PerfilSonoro;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Patrón Decorator: envuelve a cualquier {@link EmisorSonido} y lo ejecuta en su propio hilo,
 * para que reproducir un sonido nunca retrase al semáforo.
 */
public class EmisorSonidoAsincrono implements EmisorSonido {
    private final EmisorSonido delegado;
    private final ExecutorService hilo = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "audio");
        t.setDaemon(true);
        return t;
    });

    public EmisorSonidoAsincrono(EmisorSonido delegado) {
        this.delegado = delegado;
    }

    @Override
    public void emitir(PerfilSonoro perfil) {
        hilo.submit(() -> delegado.emitir(perfil));
    }

    @Override
    public void close() {
        hilo.shutdown();
        try {
            hilo.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        delegado.close();
    }
}
