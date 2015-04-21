package com.castilla.eduardo.lightbulbs;

import android.util.Log;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
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
    private AnimatedSprite startBox;
    private AnimatedSprite cable1;
    private AnimatedSprite foco1;
    private AnimatedSprite cable2;
    private AnimatedSprite endBox;

    //private TimerHandler myTimer;
    private Sprite bateria;
    private Rectangle rectangle;

    //Marcador
    private EstadoJuego hud;


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


        //final float xSeconds = 1.9f; // meaning 5 and a half second
        //boolean repeat = true; // true to reset the timer after the time passed and execute again
        //myTimer = new TimerHandler(xSeconds, repeat, new ITimerCallback() {
            //public void onTimePassed(TimerHandler pTimerHandler) {

            //}
       //});
        //registerUpdateHandler(myTimer);   // here you register the timerhandler to your scene


        //Agrega el marcador
        agregarEstado();

        // Configuración de la imagen
        spriteFondo.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
        //spriteFondo.setScale(0.7f);

        // Habilita los eventos de touch en ciertas áreas
        setTouchAreaBindingOnActionDownEnabled(true);


        // Crea el fondo de la pantalla
        SpriteBackground fondo = new SpriteBackground(1,0.5f,0,spriteFondo);
        setBackground(fondo);
        setBackgroundEnabled(true);

        // Rectangulo de energia
        rectangle = new Rectangle(50,739,320,30,admRecursos.vbom);
        rectangle.setAnchorCenter(0.0f,0.5f);

        rectangle.setColor(1.0f,1.0f,0.0f);

        attachChild(rectangle);




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


        // *** Agrega bateria
        bateria = new Sprite(215,740,admRecursos.regionBateria,admRecursos.vbom);
        attachChild(bateria);


        // *** Agrega StartBox
        startBox = new AnimatedSprite(240,607,admRecursos.regionStartEndBox,admRecursos.vbom){

            // Aquí el código que ejecuta la caja es presionada
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()) {
                    int i = lista.size()-1;
                    while(!lista.isEmpty()){
                        hud.disminuirMarcador(125);
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
        cable1 = new AnimatedSprite(240,497,admRecursos.regionCableLargo,admRecursos.vbom){

            // Aquí el código que ejecuta el cable1 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()) {

                    if (cable1.getCurrentTileIndex()==1){
                        goingBack(cable1);
                    }else{
                        if (lista.isEmpty()) {
                            hud.aumentarMarcador(100);
                            cable1.setCurrentTileIndex(1);
                            lista.add(cable1);
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

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (foco1.getCurrentTileIndex()==1){
                        goingBack(foco1);
                    }else{
                        if (lista.getLast().equals(cable1)) {
                            hud.aumentarMarcador(100);
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

        // *** Agrega Cable2
        cable2 = new AnimatedSprite(240,300,admRecursos.regionCableLargo,admRecursos.vbom){

            // Aquí el código que ejecuta el cable1 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable2.getCurrentTileIndex()==1){
                        goingBack(cable2);
                    }else{
                        if (lista.getLast().equals(foco1)) {
                            hud.aumentarMarcador(100);
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


                        //Calcular Score
                        Log.d("Longitud de la barra",String.valueOf(rectangle.getWidth()));
                        Log.d("Score Actual",String.valueOf(hud.getMarcador()));
                        hud.multiplicarMarcador((int)rectangle.getWidth()/10);


                        // Carga la escena bien, después de 1 segundo
                        admRecursos.engine.registerUpdateHandler(new TimerHandler(1,
                                new ITimerCallback() {
                                    @Override
                                    public void onTimePassed(TimerHandler pTimerHandler) {
                                        admRecursos.engine.unregisterUpdateHandler(pTimerHandler); // Invalida el timer
                                        admEscenas.crearEscenaFin(1);
                                        hud.setPosition(15,ControlJuego.ALTO_CAMARA/2);
                                        admEscenas.setEscena(TipoEscena.ESCENA_FIN);
                                        admEscenas.liberarEscenaNivel1();
                                    }
                                }));
                    }

                }

                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);

            }
        };
        endBox.setCurrentTileIndex(0);
        registerTouchArea(endBox);
        attachChild(endBox);
    }


    // El ciclo principal de la escena
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

        if (rectangle.getWidth() > 0) {
            rectangle.setWidth(rectangle.getWidth() - 0.3f);
        }else{
            admEscenas.crearEscenaFin(0);
            admRecursos.camara.setHUD(null);    // Quita el HUD
            admEscenas.setEscena(TipoEscena.ESCENA_FIN);
            admEscenas.liberarEscenaNivel1();
        }

    }
    private void agregarEstado() {
    hud = new EstadoJuego(admRecursos.engine,admRecursos.actividadJuego);
    admRecursos.camara.setHUD(hud);
}

    @Override
    public void onBackKeyPressed() {
        admEscenas.crearEscenaJuego();
        admRecursos.camara.setHUD(null);    // Quita el HUD
        admEscenas.setEscena(TipoEscena.ESCENA_JUEGO);
        admEscenas.liberarEscenaNivel1();
    }

    public void goingBack(AnimatedSprite a) {

        while (!lista.getLast().equals(a)) {
            hud.disminuirMarcador(125);
            lista.getLast().setCurrentTileIndex(0);
            lista.removeLast();
        }

        //lista.getLast().setCurrentTileIndex(0);
        //lista.removeLast();
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
