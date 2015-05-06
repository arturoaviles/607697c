package com.castilla.eduardo.lightbulbs;

import android.util.Log;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.io.IOException;

/**
 * Carga/Descarga los recurso del juego. Imágenes, Audios
 */
public class AdministradorMusica {
    // Instancia única de la clase
    private static final AdministradorMusica INSTANCE = new AdministradorMusica();

    public Engine engine;
    public ControlJuego actividadJuego;

    // ***** MUSICA *****
    private Music musicaMenu;
    private Music musicaNivel;

    public static AdministradorMusica getInstance() {
        return INSTANCE;
    }

    public static void inicializarAdministrador(Engine engine,ControlJuego control) {
        getInstance().engine = engine;
        getInstance().actividadJuego=control;
    }

    public void cargarMusicaMenu(){
        // ***** Música de fondo del Menu
        try {
            musicaMenu = MusicFactory.createMusicFromAsset(engine.getMusicManager(),
                    actividadJuego, "Sonidos/mainSong_33.mp3");
        }
        catch (IOException e) {
            Log.i("cargarSonidos","No se puede cargar demo.ogg");
        }
        // Reproducir
        musicaMenu.setLooping(true);
        musicaMenu.play();
    }

    public void cargarMusicaNivel1(){
        // ***** Música de fondo del Menu
        try {
            musicaNivel = MusicFactory.createMusicFromAsset(engine.getMusicManager(),
                    actividadJuego, "Sonidos/nivel1_10.mp3");
        }
        catch (IOException e) {
            Log.i("cargarSonidos","No se puede cargar demo.ogg");
        }
        // Reproducir

        musicaNivel.play();
    }

    public void cargarMusicaNivel2(){
        // ***** Música de fondo del Menu
        try {
            musicaNivel = MusicFactory.createMusicFromAsset(engine.getMusicManager(),
                    actividadJuego, "Sonidos/nivel2_24.mp3");
        }
        catch (IOException e) {
            Log.i("cargarSonidos","No se puede cargar demo.ogg");
        }
        // Reproducir

        musicaNivel.play();
    }


    public void cargarMusicaNivel3(){
        // ***** Música de fondo del Menu
        try {
            musicaNivel = MusicFactory.createMusicFromAsset(engine.getMusicManager(),
                    actividadJuego, "Sonidos/nivel3_34.mp3");
        }
        catch (IOException e) {
            Log.i("cargarSonidos","No se puede cargar demo.ogg");
        }
        // Reproducir

        musicaNivel.play();
    }

    public void liberarMusicaNivel(){
        musicaNivel.release();
    }

    public void liberarMusicaMenu(){
        musicaMenu.release();
    }

    public void pararMusicaMenu(){
        musicaMenu.pause();
    }

    public void continuarMusicaMenu(){
        musicaMenu.resume();
    }

    public Music getMusicaMenu() {
        return musicaMenu;
    }

    public Music getMusicaNivel() {
        return musicaNivel;
    }
}
