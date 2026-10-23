package semaforo.tiempo;


import java.time.Duration;

/** Reloj real basado en el reloj del sistema. */
public class RelojSistema implements Reloj {
    @Override public long ahoraMs() { return System.currentTimeMillis(); }

    @Override
    public void esperar(Duration duracion) throws InterruptedException {
        Thread.sleep(duracion.toMillis());
    }
}
