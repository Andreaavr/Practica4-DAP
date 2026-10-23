package semaforo.tiempo;

import java.time.Duration;

/** Abstracción del tiempo: permite sustituir el reloj real (por ejemplo, en pruebas). */
public interface Reloj {
    long ahoraMs();
    void esperar(Duration duracion) throws InterruptedException;
}
