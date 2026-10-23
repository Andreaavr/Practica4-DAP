# Semáforo accesible (Java 16+)

Semáforo con luz y sonido para invidentes. Ciclo: rojo 10 s → ámbar 3 s → verde 7 s → verde parpadeante 3 s → rojo.

## Estructura (una carpeta = una idea)

```
src/semaforo/
├── Main.java          Raíz de composición: elige las piezas y las conecta. Sin reglas.
├── modelo/            Valores simples: Color, PerfilSonoro (tono + duración del pitido).
├── estado/            PATRÓN STATE. Cada estado define su duración, ritmo, parpadeo y sonido.
├── secuencia/         PATRÓN STRATEGY. Decide qué estado viene después (CicloCircular).
├── observador/        PATRÓN OBSERVER. Interfaz que implementan las salidas (ventana, sonido).
├── nucleo/            Semaforo: contexto de State y sujeto de Observer. El motor del ciclo.
├── tiempo/            Reloj (abstracción del tiempo) y RelojSistema (el real).
├── sonido/            Salida sonora: AvisadorSonoro (observador), EmisorSonido (abstracción),
│                      EmisorSonidoJavaSound (tono) y EmisorSonidoAsincrono (PATRÓN DECORATOR).
└── vista/             Salida gráfica: VistaSwing (observador) y
                       ObservadorEnHiloSwing (PATRÓN DECORATOR, gestiona el hilo de Swing).
```

## Cómo funciona (flujo)

1. `Main` crea los estados, el ciclo, el reloj y los observadores, y los pasa a `Semaforo`.
2. `Semaforo` pide el siguiente estado a la `SecuenciaEstados`.
3. Durante la duración del estado, lanza "pulsos" cada `intervaloPulso` y avisa a los observadores.
4. `VistaSwing` dibuja la luz y `AvisadorSonoro` emite el pitido del estado. Ninguno conoce al otro.

## Dependencias (siempre hacia el centro, nunca al revés)

`modelo` ← `estado` ← `secuencia` / `observador` ← `nucleo` ← `Main`
`sonido` y `vista` dependen de `estado`, `modelo` y `observador`; `nucleo` no los conoce.

## Si quiero cambiar...

| Quiero... | Toco... | Código base tocado |
|---|---|---|
| Cambiar un tiempo, ritmo o tono | La clase del estado en `estado/` | Ninguno |
| Añadir un estado (ámbar intermitente) | Clase nueva en `estado/` + añadirla a la lista en `Main` | Ninguno |
| Cambiar el orden o añadir modo nocturno | Clase nueva que implemente `SecuenciaEstados` | Ninguno |
| Añadir una salida (LED, voz, web) | Clase nueva que implemente `ObservadorSemaforo` + suscribirla en `Main` | Ninguno |
| Cambiar la librería de audio | Clase nueva que implemente `EmisorSonido` | Ninguno |
| Probar sin esperar segundos reales | Reloj falso que implemente `Reloj` | Ninguno |
| Añadir un color nuevo | Valor en `modelo/Color` + su tono en el `switch` de `VistaSwing` | Solo esos dos |

