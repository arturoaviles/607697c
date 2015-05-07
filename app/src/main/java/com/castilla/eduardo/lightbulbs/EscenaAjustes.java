package com.castilla.eduardo.lightbulbs;

import android.util.Log;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;

/**
 * La escena que se muestra cuando corre la aplicación (Logo del TEC)
 */
public class EscenaAjustes extends EscenaBase
{
    private Sprite spriteFondo; //(el fondo de la escena, estático)
    private TiledSprite btnOnOFF_1;
    private TiledSprite btnOnOFF_2;

    @Override
    public void crearEscena() {
        // Creamos el sprite de manera óptima
        spriteFondo = new Sprite(0,0, admRecursos.regionFondoAjustes,admRecursos.vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        // Configuración de la imagen
        spriteFondo.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
        //spriteFondo.setScale(0.7f);

        // Crea el fondo de la pantalla
        SpriteBackground fondo = new SpriteBackground(0,0,0.70f,spriteFondo);
        setBackground(fondo);
        setBackgroundEnabled(true);

        // *** Agrega los botones a la pantalla de Ajustes

        // Botón OnOFF para la música
        btnOnOFF_1 = new AnimatedSprite(327,472,
                admRecursos.regionBtnonOff,admRecursos.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()) {
                    if (btnOnOFF_1.getCurrentTileIndex()==0){
                        btnOnOFF_1.setCurrentTileIndex(1);
                        admMusica.modificarPreferenciaMusica(false);
                        admMusica.pararMusicaMenu();
                    }else{
                        btnOnOFF_1.setCurrentTileIndex(0);
                        admMusica.modificarPreferenciaMusica(true);
                        admMusica.reproducirMusicaMenu();
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        registerTouchArea(btnOnOFF_1);
        attachChild(btnOnOFF_1);

        if(admMusica.leerPreferenciaMusica()){
            btnOnOFF_1.setCurrentTileIndex(0);
        }else{
            btnOnOFF_1.setCurrentTileIndex(1);
        }

        // Botón OnOFF para los efectos
        btnOnOFF_2 = new AnimatedSprite(327,332,
                admRecursos.regionBtnonOff,admRecursos.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()) {
                    if (btnOnOFF_2.getCurrentTileIndex()==0){
                        btnOnOFF_2.setCurrentTileIndex(1);
                        //ControlJuego.efectosOn=true;
                    }else{
                        btnOnOFF_2.setCurrentTileIndex(0);
                        //ControlJuego.efectosOn=false;
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        registerTouchArea(btnOnOFF_2);
        attachChild(btnOnOFF_2);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_AJUSTES;
    }

    @Override
    public void liberarEscena() {
        // Liberar cada recurso usado en esta escena
        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
    }
}
