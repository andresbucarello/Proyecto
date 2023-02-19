package com.proyecto1.gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.proyecto1.utils.AssetsManager;
import com.proyecto1.utils.ImageAsset;

/**
 * @author sebas
 */
public class MainPanel extends javax.swing.JPanel {
    JFrame mainFrame;

    /**
     * Crear el panel principal, instancia los componentes del menu principal
     * @param mainFrame es la instancia del JFrame padre
     */
    public MainPanel(JFrame mainFrame) {
        this.mainFrame = mainFrame;

        initComponents();
    }

    /**
     * 
     */
    private void initComponents() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        final ArrayList<JButton> menuBtns = new ArrayList<JButton>();
        JButton loadGraphBtn = new JButton("Cargar archivo de almacenes");
        loadGraphBtn.addActionListener(e -> {
            for (JButton btn : menuBtns)
                btn.setEnabled(false);
            SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    GraphFileDialog.loadFileDialog();
                    return null;
                }
                
                @Override
                protected void done() {
                    for (JButton btn : menuBtns)
                        btn.setEnabled(true);
                }
            };
            sw.execute();
        });

        JButton saveGraphBtn = new JButton("Guardar archivo de almacenes");
        saveGraphBtn.addActionListener(e -> {
        });

        JButton producsStockBtn = new JButton("Stock de productos");
        producsStockBtn.addActionListener(e -> {});

        JButton requestBtn = new JButton("Realizar pedido de producto");
        requestBtn.addActionListener(e -> {});

        JButton addWearhouseBtn = new JButton("Agregar almacen");
        addWearhouseBtn.addActionListener(e -> {});

        JButton addPathBtn = new JButton("Agregar camino a almacen");
        addPathBtn.addActionListener(e -> {});

        JButton manageStockBtn = new JButton("Gestionar stock de almacenes");
        manageStockBtn.addActionListener(e -> {});

        JButton showGraphBtn = new JButton("Mostrar grafico");
        showGraphBtn.addActionListener(e -> {});

        JButton helpBtn = new JButton("???");
        helpBtn.addActionListener(e -> {new HelpDialog();});

        Collections.addAll(menuBtns, loadGraphBtn, saveGraphBtn, producsStockBtn, requestBtn, addWearhouseBtn, addPathBtn, manageStockBtn, showGraphBtn, helpBtn);

        JPanel colsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel leftCol = new JPanel(new GridLayout(3, 0));
        JPanel centerCol = new JPanel(new GridLayout(menuBtns.size(), 0, 0, 10));
        JPanel rightCol = new JPanel(new GridLayout(3, 0));

        ImageAsset catKiss = AssetsManager.getInstance().getImage("cat-kiss");
        if (catKiss != null) {
            for (int i = 0; i < 3; i++) {
                JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                imgPanel.add(new JLabel(catKiss.image));
                leftCol.add(imgPanel);
            }

            for (int i = 0; i < 3; i++) {
                JPanel imgPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                imgPanel.add(new JLabel(catKiss.image));
                rightCol.add(imgPanel);
            }
        }

        for (JButton btn : menuBtns)
            centerCol.add(btn);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.ipadx = 80;
        colsPanel.add(leftCol, c);
        c.gridx = 1;
        c.ipadx = 80;
        colsPanel.add(centerCol, c);
        c.gridx = 2;
        colsPanel.add(rightCol,c);

        this.add(colsPanel);
    }
}
