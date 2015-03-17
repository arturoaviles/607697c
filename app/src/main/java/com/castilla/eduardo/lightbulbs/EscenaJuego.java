package com.castilla.eduardo.lightbulbs;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;

/**
 * La escena que se muestra cuando corre la aplicación (Logo del TEC)
 */
public class EscenaJuego extends EscenaBase
{
    private Sprite spriteFondo; //(el fondo de la escena, estático)

    private ButtonSprite btnNivel1;
    private ButtonSprite btnNivel2;
    private ButtonSprite btnNivel3;


    @Override
    public void crearEscena() {
        // Creamos el sprite de manera óptima
        spriteFondo = new Sprite(0,0, admRecursos.regionFondoJuego,admRecursos.vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        // Configuración de la imagen
        spriteFondo.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
        //spriteFondo.setScale(0.7f);

        // Habilita los eventos de touch en ciertas áreas
        setTouchAreaBindingOnActionDownEnabled(true);


        // Crea el fondo de la pantalla

        SpriteBackground fondo = new SpriteBackground(1,0.5f,0,spriteFondo);
        setBackground(fondo);
        setBackgroundEnabled(true);


        // Botón Nivel_1
        btnNivel1 = new ButtonSprite(ControlJuego.ANCHO_CAMARA/2,520,admRecursos.regionBtnNivel1,admRecursos.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionUp()) {

                    // Cambia a la escena de JUGAR
                    admEscenas.crearEscenaNivel1();
                    admEscenas.setEscena(TipoEscena.ESCENA_NIVEL_1);
                    admEscenas.liberarEscenaJuego();
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        registerTouchArea(btnNivel1);
        attachChild(btnNivel1);

        // Botón Nivel_2
        btnNivel2 = new ButtonSprite(ControlJuego.ANCHO_CAMARA/2,400,admRecursos.regionBtnNivel2,admRecursos.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionUp()) {
                    //btnJugar.setPosition(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());

                    // Cambia a la escena de JUGAR
                    admEscenas.crearEscenaNivel2();
                    admEscenas.setEscena(TipoEscena.ESCENA_NIVEL_2);
                    admEscenas.liberarEscenaJuego();
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        registerTouchArea(btnNivel2);
        attachChild(btnNivel2);



        // Botón Nivel_3
        btnNivel3 = new ButtonSprite(ControlJuego.ANCHO_CAMARA/2,280,admRecursos.regionBtnNivel3,admRecursos.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionUp()) {

                    // Cambia a la escena de JUGAR
                    admEscenas.crearEscenaNivel3();
                    admEscenas.setEscena(TipoEscena.ESCENA_NIVEL_3);
                    admEscenas.liberarEscenaJuego();
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        registerTouchArea(btnNivel3);
        attachChild(btnNivel3);
    }


    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_JUEGO;
    }

    @Override
    public void liberarEscena() {
        // Liberar cada recurso usado en esta escena
        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
    }
}
