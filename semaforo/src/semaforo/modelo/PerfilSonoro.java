package semaforo.modelo;

/** Describe el pitido asociado a un estado: tono (Hz) y duración (ms). */
public record PerfilSonoro(int frecuenciaHz, int duracionMs) { }
