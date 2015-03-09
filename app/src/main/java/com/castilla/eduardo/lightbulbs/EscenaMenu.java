package com.castilla.eduardo.lightbulbs;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;

/**
 * Representa la escena con las opciones del menú principal
 *
 * @author Roberto Martínez Román
 */
public class EscenaMenu extends EscenaBase
{
    // *** Fondo
    private Sprite spriteFondo; //(el fondo de la escena, estático)

    // *** Botones del menú
    private ButtonSprite btnJugar;
    private ButtonSprite btnInstrucciones;
    private ButtonSprite btnAcercaDe;
    private ButtonSprite btnMarcador;



    @Override
    public void crearEscena() {
        // Creamos el sprite de manera óptima
        spriteFondo = new Sprite(0,0, admRecursos.regionFondoMenu,admRecursos.vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };



        // Configuración de la imagen
        spriteFondo.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);

        // Crea el fondo de la pantalla
        SpriteBackground fondo = new SpriteBackground(0,0,0,spriteFondo);
        setBackground(fondo);
        setBackgroundEnabled(true);

        // Habilita los eventos de touch en ciertas áreas
        setTouchAreaBindingOnActionDownEnabled(true);

        // *** Agrega los botones al Menú


        // Botón jugar
        btnJugar = new ButtonSprite(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2,
                admRecursos.regionBtnJugar,admRecursos.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {


                if (pSceneTouchEvent.isActionUp()) {
                    //btnJugar.setPosition(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());

                    // Cambia a la escena de JUGAR
                    admEscenas.crearEscenaJuego();
                    admEscenas.setEscena(TipoEscena.ESCENA_JUEGO);
                    admEscenas.liberarEscenaMenu();
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        registerTouchArea(btnJugar);
        attachChild(btnJugar);

        // Botón Marcador
        btnMarcador = new ButtonSprite(300,535,
                admRecursos.regionBtnMarcador,admRecursos.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionMove()) {
                    //btnMarcador.setPosition(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());
                    // Cambia a la escena de MARCADOR
                    admEscenas.crearEscenaMarcador();
                    admEscenas.setEscena(TipoEscena.ESCENA_MARCADOR);
                    admEscenas.liberarEscenaMenu();
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        registerTouchArea(btnMarcador);
        attachChild(btnMarcador);

        // Botón Instrucciones
        btnInstrucciones = new ButtonSprite(175,535,
                admRecursos.regionBtnInstrucciones,admRecursos.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {
                    // Cambia a la escena de Instrucciones
                    admEscenas.crearEscenaInstrucciones();
                    admEscenas.setEscena(TipoEscena.ESCENA_INSTRUCCIONES);
                    admEscenas.liberarEscenaMenu();
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        registerTouchArea(btnInstrucciones);
        attachChild(btnInstrucciones);

        // Botón AcercaDe
        btnAcercaDe = new ButtonSprite(312,250,
                admRecursos.regionBtnAcercaDe,admRecursos.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionUp()) {
                    // Cambia a la escena de AcercaDe
                    admEscenas.crearEscenaAcercaDe();
                    admEscenas.setEscena(TipoEscena.ESCENA_ACERCA_DE);
                    admEscenas.liberarEscenaMenu();
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }


        };

        registerTouchArea(btnAcercaDe);
        attachChild(btnAcercaDe);
    }



    @Override
    public void onBackKeyPressed() {
        // Salir del juego
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_MENU;
    }

    @Override
    public void liberarEscena() {
        // Liberar cada recurso usado en esta escena
        // FONDO


        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
    }
}

