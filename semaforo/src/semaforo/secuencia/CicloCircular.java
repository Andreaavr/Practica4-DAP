package semaforo.secuencia;

import semaforo.estado.EstadoSemaforo;

import java.util.List;

/** Recorre una lista de estados una y otra vez, en el orden dado. */
public class CicloCircular implements SecuenciaEstados {
    private final List<EstadoSemaforo> estados;
    private int indice = 0;

    public CicloCircular(List<EstadoSemaforo> estados) {
        if (estados == null || estados.isEmpty()) {
            throw new IllegalArgumentException("El ciclo necesita al menos un estado");
        }
        this.estados = List.copyOf(estados);
    }

    @Override
    public EstadoSemaforo siguiente() {
        EstadoSemaforo estado = estados.get(indice);
        indice = (indice + 1) % estados.size();
        return estado;
    }
}
