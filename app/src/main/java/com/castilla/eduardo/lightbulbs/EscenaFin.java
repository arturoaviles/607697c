package com.castilla.eduardo.lightbulbs;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;

/**
 * La escena que se muestra cuando corre la aplicación (La escena Fin)
 */
public class EscenaFin extends EscenaBase
{
    private Sprite spriteFondo; //(el fondo de la escena, estático)
    private TiledSprite loHicisteBien;

    @Override
    public void crearEscena() {
        // Creamos el sprite de manera óptima
        spriteFondo = new Sprite(0,0, admRecursos.regionFondoFin,admRecursos.vbom) {
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


        // *** Agrega el SpriteAnimado "lo hciste bien"
        loHicisteBien = new AnimatedSprite(240,398,admRecursos.regionBien,admRecursos.vbom){

            // Aquí el código que ejecuta la caja es presionada
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()) {
                    if (loHicisteBien.getCurrentTileIndex()==0){
                        loHicisteBien.setCurrentTileIndex(1);
                    }else{
                        loHicisteBien.setCurrentTileIndex(0);
                    }

                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        loHicisteBien.setCurrentTileIndex(0);

        registerTouchArea(loHicisteBien);
        attachChild(loHicisteBien);
    }

    @Override
    public void onBackKeyPressed() {
        admEscenas.crearEscenaJuego();
        admRecursos.camara.setHUD(null);    // Quita el HUD
        admEscenas.setEscena(TipoEscena.ESCENA_JUEGO);
        admEscenas.liberarEscenaFin();

    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_FIN;
    }

    @Override
    public void liberarEscena() {
        // Liberar cada recurso usado en esta escena
        spriteFondo.detachSelf();   // Se desconecta de la escena
        spriteFondo.dispose();      // Libera la memoria

        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
    }
}
