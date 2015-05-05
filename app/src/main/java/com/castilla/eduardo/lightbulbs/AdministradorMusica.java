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
    private Music musicaFondo;

    public static AdministradorMusica getInstance() {
        return INSTANCE;
    }

    public static void inicializarAdministrador(Engine engine,
                                                ControlJuego control) {

        getInstance().engine = engine;
        getInstance().actividadJuego=control;

    }

    public void cargarMusicaMenu(){
        // ***** Música de fondo del Menu
        try {
            musicaFondo = MusicFactory.createMusicFromAsset(engine.getMusicManager(),
                    actividadJuego, "Sonidos/mainSong.mp3");
        }
        catch (IOException e) {
            Log.i("cargarSonidos","No se puede cargar demo.ogg");
        }
        // Reproducir
        musicaFondo.setLooping(true);
        musicaFondo.play();
    }

    public void cargarMusicaNivel1(){
        // ***** Música de fondo del Menu
        try {
            musicaFondo = MusicFactory.createMusicFromAsset(engine.getMusicManager(),
                    actividadJuego, "Sonidos/mainSong.mp3");
        }
        catch (IOException e) {
            Log.i("cargarSonidos","No se puede cargar demo.ogg");
        }
        // Reproducir
        musicaFondo.setLooping(true);
        musicaFondo.play();
    }



    public void pararMusica(){
        musicaFondo.pause();
    }

    public void continuarMusica(){
        musicaFondo.resume();
    }
}
