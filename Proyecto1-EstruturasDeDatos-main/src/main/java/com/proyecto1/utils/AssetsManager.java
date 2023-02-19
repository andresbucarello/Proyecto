package com.proyecto1.utils;

import java.util.ArrayList;

/**
 * Singleton que contiene todos los recursos usados por el programa
 * @author sebas
 */
public class AssetsManager {
    private static AssetsManager instance;
    private ArrayList<MusicAsset> musicAssets;
    private ArrayList<ImageAsset> imageAssets;

    AssetsManager() {
        this.musicAssets = new ArrayList<>();
        this.imageAssets = new ArrayList<>();
    }

    public static AssetsManager getInstance() {
        if (instance == null) {
            synchronized (AssetsManager.class) {
                if (instance == null)
                    instance = new AssetsManager();
            }
        }
        return instance;
    }

    /**
     * @param path Direccion relativa al directorio del proyecto
     * @param name Nombre del recurso
     * @return Retorna el recurso agregado
     */
    public MusicAsset addMusic(String path, String name) {
        try {
            MusicAsset newAsset = new MusicAsset(path, name);
            this.musicAssets.add(newAsset);
            return newAsset;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param path Direccion relativa al directorio del proyecto
     * @param name Nombre del recurso
     * @return Retorna el recurso agregado
     */
    public ImageAsset addImage(String path, String name) {
        ImageAsset newAsset = new ImageAsset(path, name);
        if (newAsset.image.getIconWidth() > 0)
            this.imageAssets.add(newAsset);
        else
            return null;
        return newAsset;
    }

    /**
     * @param name Nombre del recurso a buscar
     * @return Retorna el recurso se si encontro, de lo contrario Null
     */
    public MusicAsset getMusic(String name) {
        for (MusicAsset a : this.musicAssets) {
            if (a.name.equals(name)) return a;
        }
        return null;
    }

    /**
     * @param name Nombre del recurso a buscar
     * @return Retorna el recurso se si encontro, de lo contrario Null
     */
    public ImageAsset getImage(String name) {
        for (ImageAsset a : this.imageAssets) {
            if (a.name.equals(name)) return a;
        }
        return null;
    }
}
