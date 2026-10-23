package semaforo.secuencia;

import semaforo.estado.EstadoSemaforo;

/** Estrategia que decide qué estado viene a continuación (orden, repetición, modo nocturno...). */
public interface SecuenciaEstados {
    EstadoSemaforo siguiente();
}
