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
public class EscenaNivel1 extends EscenaBase
{
    private Sprite spriteFondo; //(el fondo de la escena, estático)


    private ButtonSprite btnPausa;

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




        // *** Agrega los botones al Nivel 1

        // Botón Pausa
        btnPausa = new ButtonSprite(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2,
                admRecursos.regionBtnPausa,admRecursos.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {


                if (pSceneTouchEvent.isActionUp()) {
                    //btnJugar.setPosition(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());

                    // Cambia a la escena de Pausa
                    admEscenas.crearEscenaJuego();
                    admEscenas.setEscena(TipoEscena.ESCENA_JUEGO);
                    admEscenas.liberarEscenaMenu(); //????????????? Aqui se libera la escena de Nivel 1 al poner pausa?
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        registerTouchArea(btnPausa);
        attachChild(btnPausa);


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
