package com.castilla.eduardo.lightbulbs;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;
import org.andengine.util.math.factorioal.IFactorialProvider;

import java.util.LinkedList;

/**
 * La escena que se muestra cuando corre la aplicación (Logo del TEC)
 */
public class EscenaNivel1 extends EscenaBase
{
    private Sprite spriteFondo; //(el fondo de la escena, estático)


    private ButtonSprite btnPausa;
    public AnimatedSprite startBox;
    public AnimatedSprite cable1;
    public AnimatedSprite foco1;
    public AnimatedSprite cable2;
    public AnimatedSprite endBox;

    private LinkedList<AnimatedSprite> lista = new LinkedList<AnimatedSprite>();

    @Override
    public void crearEscena() {
        // Creamos el sprite de manera óptima
        spriteFondo = new Sprite(0,0, admRecursos.regionFondoNivel,admRecursos.vbom) {
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
        btnPausa = new ButtonSprite(420,750,
                admRecursos.regionBtnPausa,admRecursos.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {


                if (pSceneTouchEvent.isActionUp()) {
                    //btnJugar.setPosition(pSceneTouchEvent.getX(),pSceneTouchEvent.getY());

                    // Cambia a la escena de Pausa
                    //admEscenas.crearEscenaJuego();
                    //admEscenas.setEscena(TipoEscena.ESCENA_JUEGO);
                    //admEscenas.liberarEscenaMenu(); //????????????? Aqui se libera la escena de Nivel 1 al poner pausa?
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };

        registerTouchArea(btnPausa);
        attachChild(btnPausa);



        // *** Agrega StartBox
        startBox = new AnimatedSprite(240,607,admRecursos.regionStartEndBox,admRecursos.vbom){

            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()) {
                    int i = lista.size()-1;
                    while(!lista.isEmpty()){
                        lista.get(i).setCurrentTileIndex(0);
                        lista.remove(i);
                        i--;
                    }

                }

                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);

            }




        };
        startBox.setCurrentTileIndex(1);

        registerTouchArea(startBox);
        attachChild(startBox);

        // *** Agrega Cable1
        cable1 = new AnimatedSprite(240,497,admRecursos.regionCable,admRecursos.vbom){

            // Aquí el código que ejecuta el cable1 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {


                if (pSceneTouchEvent.isActionDown()) {
                    if (cable1.getCurrentTileIndex()==0){
                        cable1.setCurrentTileIndex(1);
                        lista.add(cable1);
                    }else {
                        int i = lista.size() - 1;
                        while (!lista.isEmpty()) {


                            if (lista.get(i).equals(cable1)) {
                                cable1.setCurrentTileIndex(0);
                                break;
                            } else {
                                lista.get(i).setCurrentTileIndex(0);
                                lista.remove(i);
                            }
                            i--;

                        }
                    }

                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable1.setRotation(90);
        cable1.setCurrentTileIndex(0);

        registerTouchArea(cable1);
        attachChild(cable1);

        // *** Agrega Foco1
        foco1 = new AnimatedSprite(240,400,admRecursos.regionFoco,admRecursos.vbom){

            // Aquí el código que ejecuta la foco es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {



                if (pSceneTouchEvent.isActionDown()) {
                    if (foco1.getCurrentTileIndex()==0){
                        foco1.setCurrentTileIndex(1);
                        lista.add(foco1);
                    }else{
                        int i = lista.size()-1;
                        while(!lista.isEmpty()){


                            if (lista.get(i).equals(foco1)){
                                foco1.setCurrentTileIndex(0);
                                break;
                            }else{
                                lista.get(i).setCurrentTileIndex(0);
                                lista.remove(i);
                            }
                            i--;

                        }
                    }

                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        foco1.setCurrentTileIndex(0);

        registerTouchArea(foco1);
        attachChild(foco1);

        // *** Agrega Cable2
        cable2 = new AnimatedSprite(240,300,admRecursos.regionCable,admRecursos.vbom){

            // Aquí el código que ejecuta el cable1 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()) {

                    if (foco1.getCurrentTileIndex()==1) {

                        if (cable2.getCurrentTileIndex() == 0) {

                            cable2.setCurrentTileIndex(1);
                            lista.add(cable2);
                        } else {
                            cable2.setCurrentTileIndex(0);
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable2.setRotation(90);
        cable2.setCurrentTileIndex(0);

        registerTouchArea(cable2);
        attachChild(cable2);

        // *** Agrega EndBox
        endBox = new AnimatedSprite(240,193,admRecursos.regionStartEndBox,admRecursos.vbom){

            // Aquí el código que ejecuta la caja es presionada
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (cable2.getCurrentTileIndex() == 1) {


                    if (pSceneTouchEvent.isActionDown()) {

                        if (cable2.getCurrentTileIndex() == 1) {
                            endBox.setCurrentTileIndex(1);
                        } else {
                            endBox.setCurrentTileIndex(0);
                        }
                    }

                    if (pSceneTouchEvent.isActionUp()) {

                        admEscenas.crearEscenaFin();
                        admEscenas.setEscena(TipoEscena.ESCENA_FIN);
                        admEscenas.liberarEscenaNivel1();
                    }

                }

                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);

            }
        };
        endBox.setCurrentTileIndex(0);

        registerTouchArea(endBox);
        attachChild(endBox);
    }





    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_NIVEL_1;
    }

    @Override
    public void liberarEscena() {
        // Liberar cada recurso usado en esta escena
        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
    }
}
