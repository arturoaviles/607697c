package com.castilla.eduardo.lightbulbs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.util.Log;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
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
 * Reproducir, parar y liberar audio. Tambíen se manejan efectos como la vibracion
 */
public class AdministradorMusica {
    // Instancia única de la clase
    private static final AdministradorMusica INSTANCE = new AdministradorMusica();

    public Engine engine;
    public ControlJuego actividadJuego;

    // ***** MUSICA *****
    private Music musicaMenu;
    private Music musicaNivel;

    private Sound efectoSonido;
    private Sound efectoExplosion;  // Ver cargarSonidos

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

        musicaMenu.setLooping(true);

        reproducirMusicaMenu();
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
        musicaNivel.setLooping(true);
        reproducirMusicaNivel();
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
        musicaNivel.setLooping(true);
        reproducirMusicaNivel();
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
        musicaNivel.setLooping(true);

        reproducirMusicaNivel();
    }

    public void liberarMusicaNivel(){
        musicaNivel.release();
    }

    public void liberarMusicaMenu(){
        musicaMenu.release();
    }

    public void pararMusicaMenu(){
        if(musicaMenu.isPlaying()){
            musicaMenu.pause();
        }
    }

    public void pararMusicaNivel(){
        if(musicaNivel.isPlaying()){
            musicaNivel.pause();
        }


    }

    public void reproducirMusicaMenu(){
        if(leerPreferenciaMusica()){
            if(musicaMenu.isPlaying()){
                musicaMenu.resume();
            }else{
                musicaMenu.play();
            }
        }
    }

    public void reproducirMusicaNivel(){
        if(leerPreferenciaMusica()){
            if(musicaNivel.isPlaying()){
                musicaNivel.resume();
            }else{
                musicaNivel.play();
            }
        }
    }

    public Music getMusicaMenu() {
        return musicaMenu;
    }

    public Music getMusicaNivel() {
        return musicaNivel;
    }

    public void vibrar(int i){
        if(leerPreferenciaEfectos()){
            Vibrator v = (Vibrator)actividadJuego.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(i);
        }
    }


    // Si está en true entonces se escucha la musica

    public boolean leerPreferenciaMusica() {
        SharedPreferences mSharedPrefs = actividadJuego.getSharedPreferences("PrefMusica",Context.MODE_PRIVATE);
        SharedPreferences.Editor mPrefsEditor = mSharedPrefs.edit();
        boolean musicOn = mSharedPrefs.getBoolean("Musica", true);
        return musicOn;
    }

    public void modificarPreferenciaMusica(boolean soundflag) {
        SharedPreferences mSharedPrefs = actividadJuego.getSharedPreferences("PrefMusica",Context.MODE_PRIVATE);
        SharedPreferences.Editor mPrefsEditor = mSharedPrefs.edit();
        mPrefsEditor.putBoolean("Musica", soundflag);
        mPrefsEditor.commit();
    }

    public boolean leerPreferenciaEfectos() {
        SharedPreferences mSharedPrefs = actividadJuego.getSharedPreferences("PrefEfectos",Context.MODE_PRIVATE);
        SharedPreferences.Editor mPrefsEditor = mSharedPrefs.edit();
        boolean eff = mSharedPrefs.getBoolean("Efectos", true);
        return eff;
    }

    public void modificarPreferenciaEfectos(boolean effflag) {
        SharedPreferences mSharedPrefs = actividadJuego.getSharedPreferences("PrefEfectos",Context.MODE_PRIVATE);
        SharedPreferences.Editor mPrefsEditor = mSharedPrefs.edit();
        mPrefsEditor.putBoolean("Efectos", effflag);
        mPrefsEditor.commit();
    }

    private void cargarSonidos() {
        // Efecto de sonido
        try {
            efectoSonido = SoundFactory.createSoundFromAsset(engine.getSoundManager(),
                    actividadJuego, "sonidos/bark.wav");
            efectoExplosion = SoundFactory.createSoundFromAsset(engine.getSoundManager(),
                    actividadJuego, "sonidos/choque.mp3");
        } catch (final IOException e) {
            Log.i("cargarSonidos","No se puede cargar efecto de sonido");
        }
        //efectoExplosion.setVolume(0.5f);    // Volumen de este archivo
        // *** Ver onManagedUpdate, onSceneTouchEvent, onBackKeyPressed, liberarEscena
    }
}
