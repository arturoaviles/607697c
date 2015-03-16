package com.castilla.eduardo.lightbulbs;

import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
        import android.view.MenuItem;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
        import org.andengine.ui.activity.SimpleBaseGameActivity;

        import java.io.IOException;



/**
 * Esta es la clase principal que controla completamente el juego
 * Aquí se implementa el ciclo de vida
 */
public class ControlJuego extends SimpleBaseGameActivity
{
    // Dimensiones de la cámara
    public static final int ANCHO_CAMARA = 480;
    public static final int ALTO_CAMARA = 800;
    // La cámara
    private Camera camara;
    // El administrador de escenas
    private AdministradorEscenas admEscenas;

    @Override
    public EngineOptions onCreateEngineOptions() {
        camara = new Camera(0,0,ANCHO_CAMARA,ALTO_CAMARA);
        return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new FillResolutionPolicy(),camara);
    }

    @Override
    protected void onCreateResources() throws IOException {
        // Pasamos toda la información al administrador de recursos
        AdministradorRecursos.inicializarAdministrador(mEngine,this,
                camara,getVertexBufferObjectManager());
        admEscenas = AdministradorEscenas.getInstance();
    }


    @Override
    protected Scene onCreateScene() {
        // Crea la primer escena que se quiere mostrar
        admEscenas.crearEscenaSplash();
        admEscenas.setEscena(TipoEscena.ESCENA_SPLASH);

        // Programa la carga de la segunda escena, después de cierto tiempo
        mEngine.registerUpdateHandler(new TimerHandler(2,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        mEngine.unregisterUpdateHandler(pTimerHandler); // Invalida el timer
                        // Cambia a la escena del MENU
                        //** 1. Crea la escena del menú
                        //** 2. Pone la escena del menú
                        //** 3. LIBERA la escena de Splash
                        admEscenas.crearEscenaMenu();
                        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
                        admEscenas.liberarEscenaSplash();
                    }
                }));

        return admEscenas.getEscenaActual();
    }

    // Atiende la tecla de BACK
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK) {
            if(admEscenas.getTipoEscenaActual()==TipoEscena.ESCENA_MENU) {
                // Si está en el menú, termina
                finish();


            }else if(admEscenas.getTipoEscenaActual()==TipoEscena.ESCENA_JUEGO){
                admEscenas.crearEscenaFin();
                admEscenas.setEscena(TipoEscena.ESCENA_FIN);
                admEscenas.liberarEscenaJuego();


            }else if(admEscenas.getTipoEscenaActual()==TipoEscena.ESCENA_INSTRUCCIONES){
                admEscenas.crearEscenaMenu();
                admEscenas.setEscena(TipoEscena.ESCENA_MENU);
                admEscenas.liberarEscenaInstrucciones();


            }else if(admEscenas.getTipoEscenaActual()==TipoEscena.ESCENA_MARCADOR){
                admEscenas.crearEscenaMenu();
                admEscenas.setEscena(TipoEscena.ESCENA_MENU);
                admEscenas.liberarEscenaMarcador();


            }else if(admEscenas.getTipoEscenaActual()==TipoEscena.ESCENA_ACERCA_DE){
                admEscenas.crearEscenaMenu();
                admEscenas.setEscena(TipoEscena.ESCENA_MENU);
                admEscenas.liberarEscenaAcercaDe();


            }else if(admEscenas.getTipoEscenaActual()==TipoEscena.ESCENA_AJUSTES){
                admEscenas.crearEscenaMenu();
                admEscenas.setEscena(TipoEscena.ESCENA_MENU);
                admEscenas.liberarEscenaAjustes();


            }else if(admEscenas.getTipoEscenaActual()==TipoEscena.ESCENA_FIN){
             admEscenas.crearEscenaMenu();
                admEscenas.setEscena(TipoEscena.ESCENA_MENU);
                admEscenas.liberarEscenaFin();

            }else if(admEscenas.getTipoEscenaActual()==TipoEscena.ESCENA_NIVEL_1){
                admEscenas.crearEscenaJuego();
                admEscenas.setEscena(TipoEscena.ESCENA_JUEGO);
                admEscenas.liberarEscenaNivel1();

            }else if(admEscenas.getTipoEscenaActual()==TipoEscena.ESCENA_NIVEL_2){
                admEscenas.crearEscenaJuego();
                admEscenas.setEscena(TipoEscena.ESCENA_JUEGO);
                admEscenas.liberarEscenaNivel2();

            }else if(admEscenas.getTipoEscenaActual()==TipoEscena.ESCENA_NIVEL_3){
                admEscenas.crearEscenaJuego();
                admEscenas.setEscena(TipoEscena.ESCENA_JUEGO);
                admEscenas.liberarEscenaNivel3();

            }else{
                // La escena que esté en pantalla maneja el evento
                admEscenas.getEscenaActual().onBackKeyPressed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    // La aplicación sale de memoria
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (admEscenas!=null) {
            System.exit(0);
        }
    }

}

