package com.proyecto1.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Estado de carga del arhcivo de grafos
 * @author sebas
 */
enum FileState {
    Init,
    BeginWearhouse,
    OnLoadProducts,
    OnGraph
};

/**
 * Clase con metodos estaticos para cargar y guardar el archivo de grafos y alamacenes
 * @author sebas
 */
public class GraphFileDialog {

    /**
     * Metodo estatico para cargar el archivo de grafos y almacenes
     */
    public static void loadFileDialog() {
        JFileChooser fileDialog = new JFileChooser("./", FileSystemView.getFileSystemView());
        fileDialog.setAcceptAllFileFilterUsed(false);
        fileDialog.setDialogTitle("Selectciona el archivo de guardado de almacenes");
        fileDialog.addChoosableFileFilter(new FileNameExtensionFilter("Solo archivos .txt", "txt"));
        int res = fileDialog.showOpenDialog(null);

        if (res != JFileChooser.CANCEL_OPTION) {
            try {
                Scanner scanner = new Scanner(fileDialog.getSelectedFile());
                FileState state = FileState.Init;

                Pattern wearhouseIdPattern = Pattern.compile("(Almacen) ([a-zA-Z0-9])+(:)");
                Pattern wearhouseProductPattern = Pattern.compile("([a-zA-Z0-9]+),([0-9]+)(;?)");
                Pattern graphRoutePattern = Pattern.compile("([a-zA-Z0-9]+),([a-zA-Z0-9]+),([0-9]+)");

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    switch (state) {
                        case Init:
                            if (!line.equals("Almacenes;")) {
                                JOptionPane.showMessageDialog(null, "No se puede leer el archivo. Ingrese un archivo valido", "ERROR", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            state = FileState.BeginWearhouse;
                            break;
                        case BeginWearhouse:
                            // Ver si la linea es un almacen o son los grafos
                            // La posicion 2 del match tiene el ID del almacen
                            {
                                Matcher match = wearhouseIdPattern.matcher(line);
                                if (match.matches()) {
                                    String sID = match.group(2);
                                    int id = Integer.parseInt(sID, 16);
                                } else {
                                    // No son grafos?
                                    if (!line.equals("Rutas;")) {
                                        JOptionPane.showMessageDialog(null, "Error leyendo el archivo. Nombre del almacen invalido", "ERROR", JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                    state = FileState.OnGraph;
                                    continue;
                                }
                                state = FileState.OnLoadProducts;
                            }
                            break;
                        case OnLoadProducts:
                            // Obtener los productos
                            {
                                Matcher match = wearhouseProductPattern.matcher(line);
                                if (match.matches()) {
                                    String name = match.group(1);
                                    int amount = Integer.parseInt(match.group(2));
                                    String endChar = match.group(3);

                                    if (endChar.length() != 0) {
                                        state = FileState.BeginWearhouse;
                                    }
                                }
                            }
                            break;
                        case OnGraph:
                            {
                                Matcher match = graphRoutePattern.matcher(line);
                                if (match.matches()) {
                                    String graphNode1 = match.group(1);
                                    String graphNode2 = match.group(2);
                                    String distante = match.group(3);
                                }
                            }
                            break;
                    }
                }
                // Ir al menu principal
                scanner.close();
            } catch (FileNotFoundException e) {
            }
        }
    }

    public static void saveFileDialog() {
    }
}
