package semaforo.vista;

import semaforo.estado.EstadoSemaforo;
import semaforo.modelo.Color;
import semaforo.observador.ObservadorSemaforo;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Observador que muestra el semáforo en una ventana Swing.
 * No gestiona hilos: se espera que se cree y use desde el hilo de Swing,
 * por ejemplo envuelto en {@link ObservadorEnHiloSwing}.
 */
public class VistaSwing implements ObservadorSemaforo {
    private final PanelSemaforo panel = new PanelSemaforo();
    private final JLabel etiqueta = new JLabel(" ", SwingConstants.CENTER);

    public VistaSwing() {
        JFrame ventana = new JFrame("Semáforo");
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.add(panel, BorderLayout.CENTER);
        ventana.add(etiqueta, BorderLayout.SOUTH);
        ventana.pack();
        ventana.setLocationByPlatform(true);
        ventana.setVisible(true);
    }

    @Override
    public void alPulso(EstadoSemaforo estado, boolean luzEncendida) {
        panel.actualizar(estado.color(), luzEncendida);
        etiqueta.setText(estado.nombre());
    }

    /** Cómo se dibuja cada color es responsabilidad de la vista. */
    private static java.awt.Color aAwt(Color color) {
        return switch (color) {
            case ROJO  -> new java.awt.Color(0xE53935);
            case AMBAR -> new java.awt.Color(0xFFB300);
            case VERDE -> new java.awt.Color(0x43A047);
        };
    }

    /** Dibuja la carcasa y una luz por cada valor de {@link Color}. */
    private static class PanelSemaforo extends JPanel {
        private Color activo;
        private boolean encendida;

        PanelSemaforo() {
            setPreferredSize(new Dimension(220, 460));
            setBackground(new java.awt.Color(235, 235, 235));
        }

        void actualizar(Color activo, boolean encendida) {
            this.activo = activo;
            this.encendida = encendida;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g0) {
            super.paintComponent(g0);
            Graphics2D g = (Graphics2D) g0.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int margen = 20;
            int ancho = getWidth() - 2 * margen, alto = getHeight() - 2 * margen;
            g.setColor(new java.awt.Color(30, 30, 30));
            g.fillRoundRect(margen, margen, ancho, alto, 40, 40);

            Color[] colores = Color.values();
            int hueco = alto / colores.length;
            int d = Math.min(ancho - 30, hueco - 20);
            int cx = margen + ancho / 2;

            for (int i = 0; i < colores.length; i++) {
                java.awt.Color base = aAwt(colores[i]);
                boolean on = encendida && colores[i] == activo;
                int cy = margen + hueco * i + hueco / 2;
                if (on) {   // halo de luz
                    g.setColor(new java.awt.Color(base.getRed(), base.getGreen(), base.getBlue(), 70));
                    g.fillOval(cx - d / 2 - 8, cy - d / 2 - 8, d + 16, d + 16);
                }
                g.setColor(on ? base
                              : new java.awt.Color(base.getRed() / 6, base.getGreen() / 6, base.getBlue() / 6));
                g.fillOval(cx - d / 2, cy - d / 2, d, d);
            }
            g.dispose();
        }
    }
}
