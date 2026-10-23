package semaforo;

import semaforo.estado.EstadoAmbar;
import semaforo.estado.EstadoRojo;
import semaforo.estado.EstadoVerde;
import semaforo.estado.EstadoVerdeParpadeante;
import semaforo.nucleo.Semaforo;
import semaforo.secuencia.CicloCircular;

import semaforo.sonido.AvisadorSonoro;
import semaforo.sonido.EmisorSonido;
import semaforo.sonido.EmisorSonidoAsincrono;
import semaforo.sonido.EmisorSonidoJavaSound;
import semaforo.tiempo.RelojSistema;
import semaforo.vista.ObservadorEnHiloSwing;
import semaforo.vista.VistaSwing;

import java.util.List;

/** Raíz de composición: elige las piezas y las conecta. Aquí se "configura" el sistema. */
public class Main {
    public static void main(String[] args) throws Exception {
        try (EmisorSonido emisor = new EmisorSonidoAsincrono(new EmisorSonidoJavaSound())) {
            CicloCircular ciclo = new CicloCircular(List.of(
                    new EstadoRojo(), new EstadoAmbar(), new EstadoVerde(), new EstadoVerdeParpadeante()));

            Semaforo semaforo = new Semaforo(ciclo, new RelojSistema());
            semaforo.suscribir(new ObservadorEnHiloSwing(VistaSwing::new));
            semaforo.suscribir(new AvisadorSonoro(emisor));

            Runtime.getRuntime().addShutdownHook(new Thread(semaforo::detener));
            semaforo.iniciar();
        }
    }
}
