package com.proyecto1;

import java.util.Enumeration;

import javax.swing.JPanel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.proyecto1.gui.AssetsLoader;
import com.proyecto1.gui.MainPanel;
import com.proyecto1.utils.AssetsManager;
import com.proyecto1.utils.ImageAsset;
import com.proyecto1.utils.MusicAsset;

/**
 * Clase main del programa, esta hereda la clase Jframe para inicializar directamente
 * con un ventana.
 * @author sebas
 */
public class MainFrame extends javax.swing.JFrame {
    final int MIN_WIDTH = 960;
    final int MIN_HEIGHT = 720;

    JPanel mainPanel;

    /**
     * El contructor instancia la clase MainPanel que servira como la base donde
     * instanciar los distintos componentes de las difernete vistas del programa.
     * Ademas de cargar los distintos recursos que necesita el programa
     */
    public MainFrame() {
        new AssetsLoader();

        // reproducir la musica de fondo
        MusicAsset backgroundMusic = AssetsManager.getInstance().getMusic("background-music");
        if (backgroundMusic != null) {
            backgroundMusic.setVolume(0.1f);
            //backgroundMusic.play(true);
        }

        this.mainPanel = new MainPanel(this);
        initComponents();
    }

    private void initComponents() {
        // Setear las dimenciones de la ventana
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setMinimumSize(new java.awt.Dimension(this.MIN_WIDTH, MIN_HEIGHT));
        this.setPreferredSize(new java.awt.Dimension(this.MIN_WIDTH, MIN_HEIGHT));
        this.setSize(new java.awt.Dimension(this.MIN_WIDTH, MIN_HEIGHT));
        this.setResizable(false);

        ImageAsset icon = AssetsManager.getInstance().getImage("amazon-icon");
        if (icon != null) {
            this.setIconImage(icon.image.getImage());
        }

        this.add(mainPanel);

        pack();

        this.setLocationRelativeTo(null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();
            // UIDefaults defaults = UIManager.getLookAndFeelDefaults();
            Enumeration<Object> keys = defaults.keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                if ((key instanceof String) && (((String) key).endsWith(".font"))) {
                    FontUIResource font = (FontUIResource) UIManager.get(key);
                    defaults.put(key, new FontUIResource(font.getFontName(), font.getStyle(), 20));
                }
            }
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
