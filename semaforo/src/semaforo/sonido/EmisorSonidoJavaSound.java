package semaforo.sonido;

import semaforo.modelo.PerfilSonoro;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/** Reproduce un tono senoidal con javax.sound. Es síncrono: no gestiona hilos. */
public class EmisorSonidoJavaSound implements EmisorSonido {
    private static final float SAMPLE_RATE = 44100f;
    private final SourceDataLine linea;

    public EmisorSonidoJavaSound() throws LineUnavailableException {
        AudioFormat formato = new AudioFormat(SAMPLE_RATE, 16, 1, true, false);
        linea = AudioSystem.getSourceDataLine(formato);
        linea.open(formato);
        linea.start();
    }

    @Override
    public void emitir(PerfilSonoro p) {
        int muestras = (int) (SAMPLE_RATE * p.duracionMs() / 1000);
        byte[] buf = new byte[muestras * 2];
        for (int i = 0; i < muestras; i++) {
            double fade = Math.min(1.0, Math.min(i, muestras - i) / 200.0); // evita "clics"
            short v = (short) (Math.sin(2 * Math.PI * p.frecuenciaHz() * i / SAMPLE_RATE) * 12000 * fade);
            buf[2 * i] = (byte) (v & 0xFF);
            buf[2 * i + 1] = (byte) (v >> 8);
        }
        linea.write(buf, 0, buf.length);
    }

    @Override
    public void close() {
        linea.drain();
        linea.close();
    }
}
