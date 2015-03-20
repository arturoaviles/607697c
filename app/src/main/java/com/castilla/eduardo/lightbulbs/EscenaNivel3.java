package com.castilla.eduardo.lightbulbs;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * La escena que se muestra cuando corre la aplicación (Logo del TEC)
 */
public class EscenaNivel3 extends EscenaBase
{

    private Sprite spriteFondo; //(el fondo de la escena, estático)


    private ButtonSprite btnPausa;
    public AnimatedSprite startBox;
    public AnimatedSprite cable1;
    public AnimatedSprite cable2;
    public AnimatedSprite cable3;
    public AnimatedSprite cable4;
    public AnimatedSprite cable5;
    public AnimatedSprite cable6;
    public AnimatedSprite cable7;
    public AnimatedSprite foco1;
    public AnimatedSprite foco2;
    public AnimatedSprite foco3;
    public AnimatedSprite foco4;
    public AnimatedSprite endBox;


    private LinkedList<AnimatedSprite> lista = new LinkedList();



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




        // *** Agrega los botones al Nivel 2

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
        // colisiona con cable1 y con cable4
        startBox = new AnimatedSprite(132,630,admRecursos.regionStartEndBox,admRecursos.vbom){


            // Aquí el código que ejecuta la caja es presionada
            @Override
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
        // colisiona cajaStart y con foco1
        cable1 = new AnimatedSprite(257,620,admRecursos.regionCable,admRecursos.vbom){
            // Aquí el código que ejecuta el cable1 es presionado

            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()) {

                    if (cable1.getCurrentTileIndex()==1){
                        goingBack(cable1);
                    }else{

                        if (lista.isEmpty()||lista.getLast().equals(foco1)) {

                            cable1.setCurrentTileIndex(1);
                            lista.add(cable1);
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        //cable1.setRotation(90);
        cable1.setCurrentTileIndex(0);

        registerTouchArea(cable1);
        attachChild(cable1);

        // *** Agrega Cable2
        // colisiona con foco1 y foco 2
        cable2 = new AnimatedSprite(358,518,admRecursos.regionCable,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable2.getCurrentTileIndex()==1){
                        goingBack(cable2);
                    }else{

                        if (lista.getLast().equals(foco1)||lista.getLast().equals(foco2)) {

                            cable2.setCurrentTileIndex(1);
                            lista.add(cable2);
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

        // *** Agrega Cable3
        // colisiona con foco3 y con foco2
        cable3 = new AnimatedSprite(258,417,admRecursos.regionCable,admRecursos.vbom){

            // Aquí el código que ejecuta el cable3 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable3.getCurrentTileIndex()==1){
                        goingBack(cable3);
                    }else{

                        if (lista.getLast().equals(foco3)||lista.getLast().equals(foco2)) {

                            cable3.setCurrentTileIndex(1);
                            lista.add(cable3);
                        }
                    }

                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        //cable3.setRotation(90);
        cable3.setCurrentTileIndex(0);

        registerTouchArea(cable3);
        attachChild(cable3);

        // *** Agrega Cable4
        // colisiona con cajastart y foco3
        cable4 = new AnimatedSprite(157,518,admRecursos.regionCable,admRecursos.vbom){

            // Aquí el código que ejecuta el cable1 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()) {

                    if (cable4.getCurrentTileIndex()==1){
                        goingBack(cable4);
                    }else{

                        if (lista.isEmpty()||lista.getLast().equals(foco3)) {

                            cable4.setCurrentTileIndex(1);
                            lista.add(cable4);
                        }
                    }

                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable4.setRotation(90);
        cable4.setCurrentTileIndex(0);

        registerTouchArea(cable4);
        attachChild(cable4);

        // *** Agrega Cable5
        // foco 2
        cable5 = new AnimatedSprite(358,316,admRecursos.regionCable,admRecursos.vbom){

            // Aquí el código que ejecuta el cable1 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable5.getCurrentTileIndex()==1){
                        goingBack(cable5);
                    }else{

                        if (lista.getLast().equals(foco2)) {

                            cable5.setCurrentTileIndex(1);
                            lista.add(cable5);
                        }
                    }

                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable5.setRotation(90);
        cable5.setCurrentTileIndex(0);

        registerTouchArea(cable5);
        attachChild(cable5);

        // *** Agrega Cable6
        // colisiona con foco 4
        cable6 = new AnimatedSprite(258,215,admRecursos.regionCable,admRecursos.vbom){

            // Aquí el código que ejecuta el cable1 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable6.getCurrentTileIndex()==1){
                        goingBack(cable6);
                    }else{

                        if (lista.getLast().equals(foco4)) {

                            cable6.setCurrentTileIndex(1);
                            lista.add(cable6);
                        }
                    }

                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        //cable3.setRotation(90);
        cable6.setCurrentTileIndex(0);

        registerTouchArea(cable6);
        attachChild(cable6);

        // *** Agrega Cable7
        // colisiona con foco3 y foco4
        cable7 = new AnimatedSprite(157,318,admRecursos.regionCable,admRecursos.vbom){

            // Aquí el código que ejecuta el cable1 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable7.getCurrentTileIndex()==1){
                        goingBack(cable7);
                    }else{

                        if (lista.getLast().equals(foco3)||lista.getLast().equals(foco4)) {

                            cable7.setCurrentTileIndex(1);
                            lista.add(cable7);
                        }
                    }

                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable7.setRotation(90);
        cable7.setCurrentTileIndex(0);

        registerTouchArea(cable7);
        attachChild(cable7);


        // *** Agrega Foco1
        // colisiona con cable 1 y cable 2
        foco1 = new AnimatedSprite(358,620,admRecursos.regionFoco,admRecursos.vbom){

            // Aquí el código que ejecuta la foco es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (foco1.getCurrentTileIndex()==1){
                        goingBack(foco1);
                    }else{

                        if (lista.getLast().equals(cable1)||lista.getLast().equals(cable2)) {

                            foco1.setCurrentTileIndex(1);
                            lista.add(foco1);
                        }
                    }

                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        foco1.setCurrentTileIndex(0);

        registerTouchArea(foco1);
        attachChild(foco1);

        // *** Agrega Foco2
        // colisiona con cable 2 , cable 3 y cable 5
        foco2 = new AnimatedSprite(358,418,admRecursos.regionFoco,admRecursos.vbom){

            // Aquí el código que ejecuta la foco es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (foco2.getCurrentTileIndex()==1){
                        goingBack(foco2);
                    }else{

                        if (lista.getLast().equals(cable2)||lista.getLast().equals(cable3)||lista.getLast().equals(cable5)) {

                            foco2.setCurrentTileIndex(1);
                            lista.add(foco2);
                        }
                    }

                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        foco2.setCurrentTileIndex(0);

        registerTouchArea(foco2);
        attachChild(foco2);

        // *** Agrega Foco3
        // colisiona con cable 4 cable 3 y cable 7

        foco3 = new AnimatedSprite(157,418,admRecursos.regionFoco,admRecursos.vbom){

            // Aquí el código que ejecuta la foco es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (foco3.getCurrentTileIndex()==1){
                        goingBack(foco3);
                    }else{

                        if (lista.getLast().equals(cable4)||lista.getLast().equals(cable3)||lista.getLast().equals(cable7)) {

                            foco3.setCurrentTileIndex(1);
                            lista.add(foco3);
                        }
                    }

                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        foco3.setCurrentTileIndex(0);

        registerTouchArea(foco3);
        attachChild(foco3);

        // *** Agrega Foco4
        //colisiona con cable 6, cable 7
        foco4 = new AnimatedSprite(157,218,admRecursos.regionFoco,admRecursos.vbom){

            // Aquí el código que ejecuta la foco es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (foco4.getCurrentTileIndex()==1){
                        goingBack(foco4);
                    }else{

                        if (lista.getLast().equals(cable6)||lista.getLast().equals(cable7)) {

                            foco4.setCurrentTileIndex(1);
                            lista.add(foco4);
                        }
                    }

                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        foco4.setCurrentTileIndex(0);

        registerTouchArea(foco4);
        attachChild(foco4);


        // *** Agrega EndBox
        // colisiona con cable 5 y cable 6
        endBox = new AnimatedSprite(382,208,admRecursos.regionStartEndBox,admRecursos.vbom){

            // Aquí el código que ejecuta la caja es presionada
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                boolean allBulbs = true;


                ArrayList<AnimatedSprite> focos = new ArrayList<AnimatedSprite>();

                focos.add(foco1);
                focos.add(foco2);
                focos.add(foco3);
                focos.add(foco4);

                for(int i=0; i<focos.size(); i++){
                    if (focos.get(i).getCurrentTileIndex()==0){
                        allBulbs = false;
                    }
                }

                if(allBulbs) {


                    if (cable5.getCurrentTileIndex() == 1 || cable6.getCurrentTileIndex() == 1) {


                        if (pSceneTouchEvent.isActionDown()) {


                            if (endBox.getCurrentTileIndex() == 0) {
                                endBox.setCurrentTileIndex(1);
                            } else {
                                endBox.setCurrentTileIndex(0);
                            }

                            // Programa la carga de la escena bien, después de 1 segundo
                            admRecursos.engine.registerUpdateHandler(new TimerHandler(1,
                                    new ITimerCallback() {
                                        @Override
                                        public void onTimePassed(TimerHandler pTimerHandler) {
                                            admRecursos.engine.unregisterUpdateHandler(pTimerHandler); // Invalida el timer

                                            admEscenas.crearEscenaFin();
                                            admEscenas.setEscena(TipoEscena.ESCENA_FIN);
                                            admEscenas.liberarEscenaNivel3();
                                        }
                                    }));
                        }
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

    public void goingBack(AnimatedSprite a) {

        while (!lista.getLast().equals(a)) {
            lista.getLast().setCurrentTileIndex(0);
            lista.removeLast();
        }
        lista.getLast().setCurrentTileIndex(0);
        lista.removeLast();
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_NIVEL_3;
    }

    @Override
    public void liberarEscena() {
        // Liberar cada recurso usado en esta escena
        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
    }

}
