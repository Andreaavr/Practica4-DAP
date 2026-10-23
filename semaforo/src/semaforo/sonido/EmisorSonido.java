package semaforo.sonido;

import semaforo.modelo.PerfilSonoro;

/** Abstracción del dispositivo de sonido (DIP): permite cambiar la implementación. */
public interface EmisorSonido extends AutoCloseable {
    void emitir(PerfilSonoro perfil);
    @Override void close();
}
