package com.proyecto1.utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

/**
 * @author sebas
 */
public class MusicAsset {
    private Clip clip;
    private AudioInputStream sound;
    String name;

    /**
     * @param path Direccion relativa al directorio del proyecto
     * @param name Nombre del recurso
     * @throws Exception Exepcion provocoda por un error en la carga del archivo de audio
     */
    MusicAsset(String path, String name) throws Exception {
        this.name = name;
        File file = new File(path);
        this.sound = AudioSystem.getAudioInputStream(file);
        this.clip = AudioSystem.getClip();
        clip.open(sound);
    }

    /**
     * @param loop Reproducir en bucle el audio
     */
    public void play(boolean loop) {
        this.clip.start();
        if (loop)
            this.clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * @throws Exception Error al parar el audio
     */
    public void stop() throws Exception {
        this.sound.close();
        this.clip.close();
        this.clip.stop();
    }

    /**
     * @param volume Volumen en decibeles entre 0.0f a 1.0f
     */
    public void setVolume(float volume) {
        if (volume < 0.f || volume > 1.0f) return;
        FloatControl gainControl = (FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
}
