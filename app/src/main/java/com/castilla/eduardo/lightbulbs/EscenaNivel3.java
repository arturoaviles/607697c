package com.castilla.eduardo.lightbulbs;

import android.util.Log;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.util.GLState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * La escena que se muestra cuando corre la aplicación (Logo del TEC)
 */
public class EscenaNivel3 extends EscenaBase
{
    private Sprite spriteFondoNivel; //(el fondo de la escena, estático)


    private ButtonSprite btnPausa;

    public AnimatedSprite startBox0;
    public AnimatedSprite cable1;
    public AnimatedSprite cable2;
    public AnimatedSprite cable3;
    public AnimatedSprite cable4;
    public AnimatedSprite cable6;
    public AnimatedSprite cable8;
    public AnimatedSprite cable10;
    public AnimatedSprite cable11;
    public AnimatedSprite cable12;
    public AnimatedSprite cable13;
    public AnimatedSprite cable14;
    public AnimatedSprite cable15;
    public AnimatedSprite cable17;
    public AnimatedSprite cable19;
    public AnimatedSprite cable21;
    public AnimatedSprite cable22;
    public AnimatedSprite cable23;
    public AnimatedSprite cable27;
    public AnimatedSprite cable28;
    public AnimatedSprite cable29;
    public AnimatedSprite cable31;
    public AnimatedSprite cable33;

    public AnimatedSprite foco5;
    public AnimatedSprite foco7;
    public AnimatedSprite foco9;
    public AnimatedSprite foco30;
    public AnimatedSprite foco16;
    public AnimatedSprite foco18;
    public AnimatedSprite foco20;
    public AnimatedSprite foco24;
    public AnimatedSprite foco25;
    public AnimatedSprite foco26;
    public AnimatedSprite foco32;
    public AnimatedSprite foco34;



    public AnimatedSprite endBox;

    // Timer
    private Sprite bateria;
    private Rectangle rectanguloEnergia;

    // Fondo de la escena Pausa
    private ITexture texturaFondoPausa;
    private ITextureRegion regionFondoPausa;
    private Sprite fondoPausa;
    SpriteBackground fondoP;
    private ButtonSprite btnContinuar;
    private TiledSprite btnOnOFF_1;
    private TiledSprite btnOnOFF_2;

    //Marcador
    private EstadoJuego hudMarcador;

    // Condición de Pausa
    private boolean pausa = false;

    // Condicion de Ganar
    private boolean ganar = false;

    //Lista ligada que guarda los elementos encendidos
    private LinkedList<AnimatedSprite> lista = new LinkedList();

    // *** La Escena que se ve cuando se pausa el juego
    private Scene escenaPausa;

    @Override
    public void crearEscena() {
        // Creamos el sprite de manera óptima
        spriteFondoNivel = new Sprite(0,0, admRecursos.regionFondoNivel,admRecursos.vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };

        // Configuración de la imagen de nivel
        spriteFondoNivel.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
        //spriteFondo.setScale(0.7f);

        // Crea el fondo de la pantalla
        SpriteBackground fondo = new SpriteBackground(1,0.5f,0,spriteFondoNivel);
        setBackground(fondo);
        setBackgroundEnabled(true);

        //Creamos el fondo de la escena de Pausa
        try {
            texturaFondoPausa = new AssetBitmapTexture(admRecursos.actividadJuego.getTextureManager(),
                    admRecursos.actividadJuego.getAssets(), "MenuPausa.png");
            texturaFondoPausa.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        regionFondoPausa = TextureRegionFactory.extractFromTexture(texturaFondoPausa);
        fondoPausa = new Sprite(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2,regionFondoPausa,admRecursos.vbom);
        fondoP = new SpriteBackground(fondoPausa);
        fondoP.setColorEnabled(false);


        // Habilita los eventos de touch en ciertas áreas en la escena nivel
        setTouchAreaBindingOnActionDownEnabled(true);

        // Agrega el rectangulo de energia
        rectanguloEnergia = new Rectangle(50,739,320,30,admRecursos.vbom);
        rectanguloEnergia.setAnchorCenter(0.0f,0.5f);
        rectanguloEnergia.setColor(1.0f,1.0f,0.0f);
        attachChild(rectanguloEnergia);

        //Agrega el marcador
        agregarEstado();

        //Crea la escena de pausa
        crearEscenaPausa();

        // *** Agrega los botones al Nivel 2

        // Botón Pausa
        btnPausa = new ButtonSprite(420,750,
                admRecursos.regionBtnPausa,admRecursos.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionUp()) {
                    pausar();
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        registerTouchArea(btnPausa);
        attachChild(btnPausa);

        // *** Agrega bateria
        bateria = new Sprite(215,740,admRecursos.regionBateria,admRecursos.vbom);
        attachChild(bateria);





        // *** Agrega Cable1
        // colisiona cajaStart0 y con foco5
        cable1 = new AnimatedSprite(113,608,admRecursos.regionCableMediano,admRecursos.vbom){
            // Aquí el código que ejecuta el cable1 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()) {

                    if (cable1.getCurrentTileIndex()==1){
                        goingBack(cable1);
                    }else{
                        if (lista.isEmpty()||lista.getLast().equals(foco5)) {
                            hudMarcador.aumentarMarcador(100);
                            cable1.setCurrentTileIndex(1);
                            lista.add(cable1);
                            if (foco5.getCurrentTileIndex()==0){
                                foco5.setCurrentTileIndex(1);
                                lista.add(foco5);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable1.setRotation(125);
        cable1.setCurrentTileIndex(0);

        registerTouchArea(cable1);
        attachChild(cable1);


        // *** Agrega Cable2
        // colisiona con cajaStart0 y foco 7
        cable2 = new AnimatedSprite(193,608,admRecursos.regionCableMediano,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()) {

                    if (cable2.getCurrentTileIndex()==1){
                        goingBack(cable2);
                    }else{

                        if (lista.isEmpty()||lista.getLast().equals(foco7)) {
                            hudMarcador.aumentarMarcador(100);
                            cable2.setCurrentTileIndex(1);
                            lista.add(cable2);
                            if (foco7.getCurrentTileIndex()==0) {
                                foco7.setCurrentTileIndex(1);
                                lista.add(foco7);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable2.setRotation(55);
        cable2.setCurrentTileIndex(0);
        registerTouchArea(cable2);
        attachChild(cable2);

        // *** Agrega Cable3
        // colisiona con cajaEnd y foco 7
        cable3 = new AnimatedSprite(287,608,admRecursos.regionCableMediano,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable3.getCurrentTileIndex()==1){
                        goingBack(cable3);
                    }else{

                        if (lista.getLast().equals(foco7)) {
                            hudMarcador.aumentarMarcador(100);
                            cable3.setCurrentTileIndex(1);
                            lista.add(cable3);
                            if (foco7.getCurrentTileIndex()==0) {
                                foco7.setCurrentTileIndex(1);
                                lista.add(foco7);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable3.setRotation(125);
        cable3.setCurrentTileIndex(0);
        registerTouchArea(cable3);
        attachChild(cable3);


        // *** Agrega Cable4
        // colisiona con cajaEnd y foco 9
        cable4 = new AnimatedSprite(367,608,admRecursos.regionCableMediano,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable4.getCurrentTileIndex()==1){
                        goingBack(cable4);
                    }else{

                        if (lista.getLast().equals(foco9)) {
                            hudMarcador.aumentarMarcador(100);
                            cable4.setCurrentTileIndex(1);
                            lista.add(cable4);
                            if (foco9.getCurrentTileIndex()==0) {
                                foco9.setCurrentTileIndex(1);
                                lista.add(foco9);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable4.setRotation(55);
        cable4.setCurrentTileIndex(0);
        registerTouchArea(cable4);
        attachChild(cable4);

        // *** Agrega Cable6
        // colisiona con foco 5 y foco 7
        cable6 = new AnimatedSprite(154,545,admRecursos.regionCableMediano,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable6.getCurrentTileIndex()==1){
                        goingBack(cable6);
                    }else{

                        if (lista.getLast().equals(foco5)||lista.getLast().equals(foco7)) {
                            hudMarcador.aumentarMarcador(100);
                            cable6.setCurrentTileIndex(1);
                            lista.add(cable6);
                            if (foco5.getCurrentTileIndex()==0) {
                                foco5.setCurrentTileIndex(1);
                                lista.add(foco5);
                            }
                            if (foco7.getCurrentTileIndex()==0) {
                                foco7.setCurrentTileIndex(1);
                                lista.add(foco7);
                            }

                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        //cable6.setRotation(45);
        cable6.setCurrentTileIndex(0);
        registerTouchArea(cable6);
        attachChild(cable6);
        cable6.setWidth(cable6.getWidth()+10);


        // *** Agrega Cable8
        // colisiona con foco 7 y foco 9
        cable8 = new AnimatedSprite(324,545,admRecursos.regionCableMediano,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable8.getCurrentTileIndex()==1){
                        goingBack(cable8);
                    }else{

                        if (lista.getLast().equals(foco7)||lista.getLast().equals(foco9)) {
                            hudMarcador.aumentarMarcador(100);
                            cable8.setCurrentTileIndex(1);
                            lista.add(cable8);
                            if (foco7.getCurrentTileIndex()==0) {
                                foco7.setCurrentTileIndex(1);
                                lista.add(foco7);
                            }
                            if (foco9.getCurrentTileIndex()==0) {
                                foco9.setCurrentTileIndex(1);
                                lista.add(foco9);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        //cable8.setRotation(45);
        cable8.setCurrentTileIndex(0);
        registerTouchArea(cable8);
        attachChild(cable8);
        cable8.setWidth(cable8.getWidth()+10);

        // *** Agrega Cable10
        // colisiona con foco 5 y foco 30
        cable10 = new AnimatedSprite(67,548,admRecursos.regionCableLargo,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable10.getCurrentTileIndex()==1){
                        goingBack(cable10);
                    }else{

                        if (lista.getLast().equals(foco5)||lista.getLast().equals(foco30)) {
                            hudMarcador.aumentarMarcador(100);
                            cable10.setCurrentTileIndex(1);
                            lista.add(cable10);
                            if (foco5.getCurrentTileIndex()==0) {
                                foco5.setCurrentTileIndex(1);
                                lista.add(foco5);
                            }
                            if (foco30.getCurrentTileIndex()==0) {
                                foco30.setCurrentTileIndex(1);
                                lista.add(foco30);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable10.setRotation(90);
        cable10.setCurrentTileIndex(0);
        registerTouchArea(cable10);
        attachChild(cable10);

        cable10.setAnchorCenter(0.0f,0.5f);
        cable10.setWidth(cable10.getWidth()+200);

        // *** Agrega Cable 11
        // colisiona con foco 5 y foco 16
        cable11 = new AnimatedSprite(113,480,admRecursos.regionCableMediano,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable11.getCurrentTileIndex()==1){
                        goingBack(cable11);
                    }else{

                        if (lista.getLast().equals(foco5)||lista.getLast().equals(foco16)) {
                            hudMarcador.aumentarMarcador(100);
                            cable11.setCurrentTileIndex(1);
                            lista.add(cable11);
                            if (foco5.getCurrentTileIndex()==0) {
                                foco5.setCurrentTileIndex(1);
                                lista.add(foco5);
                            }
                            if (foco16.getCurrentTileIndex()==0) {
                                foco16.setCurrentTileIndex(1);
                                lista.add(foco16);
                            }

                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable11.setRotation(55);
        cable11.setCurrentTileIndex(0);
        registerTouchArea(cable11);
        attachChild(cable11);

        // *** Agrega Cable12
        // colisiona con foco7 y foco 16
        cable12 = new AnimatedSprite(193,480,admRecursos.regionCableMediano,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable12.getCurrentTileIndex()==1){
                        goingBack(cable12);
                    }else{

                        if (lista.getLast().equals(foco7)||lista.getLast().equals(foco16)) {
                            hudMarcador.aumentarMarcador(100);
                            cable12.setCurrentTileIndex(1);
                            lista.add(cable12);
                            if (foco7.getCurrentTileIndex()==0) {
                                foco7.setCurrentTileIndex(1);
                                lista.add(foco7);
                            }
                            if (foco16.getCurrentTileIndex()==0) {
                                foco16.setCurrentTileIndex(1);
                                lista.add(foco16);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable12.setRotation(125);
        cable12.setCurrentTileIndex(0);
        registerTouchArea(cable12);
        attachChild(cable12);

        // *** Agrega Cable13
        // colisiona con foco7 y foco 20
        cable13 = new AnimatedSprite(287,480,admRecursos.regionCableMediano,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable13.getCurrentTileIndex()==1){
                        goingBack(cable13);
                    }else{

                        if (lista.getLast().equals(foco7)||lista.getLast().equals(foco20)) {
                            hudMarcador.aumentarMarcador(100);
                            cable13.setCurrentTileIndex(1);
                            lista.add(cable13);
                            if (foco7.getCurrentTileIndex()==0) {
                                foco7.setCurrentTileIndex(1);
                                lista.add(foco7);
                            }
                            if (foco20.getCurrentTileIndex()==0) {
                                foco20.setCurrentTileIndex(1);
                                lista.add(foco20);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable13.setRotation(55);
        cable13.setCurrentTileIndex(0);
        registerTouchArea(cable13);
        attachChild(cable13);


        // *** Agrega Cable14
        // colisiona con foco9 y foco 20
        cable14 = new AnimatedSprite(367,480,admRecursos.regionCableMediano,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable14.getCurrentTileIndex()==1){
                        goingBack(cable14);
                    }else{

                        if (lista.getLast().equals(foco9)||lista.getLast().equals(foco20)) {
                            hudMarcador.aumentarMarcador(100);
                            cable14.setCurrentTileIndex(1);
                            lista.add(cable14);
                            if (foco9.getCurrentTileIndex()==0) {
                                foco9.setCurrentTileIndex(1);
                                lista.add(foco9);
                            }
                            if (foco20.getCurrentTileIndex()==0) {
                                foco20.setCurrentTileIndex(1);
                                lista.add(foco20);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable14.setRotation(125);
        cable14.setCurrentTileIndex(0);
        registerTouchArea(cable14);
        attachChild(cable14);

        // *** Agrega Cable15
        // colisiona con foco 5 y foco 30
        cable15 = new AnimatedSprite(413,548,admRecursos.regionCableLargo,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable15.getCurrentTileIndex()==1){
                        goingBack(cable15);
                    }else{

                        if (lista.getLast().equals(foco9)||lista.getLast().equals(foco34)) {
                            hudMarcador.aumentarMarcador(100);
                            cable15.setCurrentTileIndex(1);
                            lista.add(cable15);
                            if (foco9.getCurrentTileIndex()==0) {
                                foco9.setCurrentTileIndex(1);
                                lista.add(foco9);
                            }
                            if (foco34.getCurrentTileIndex()==0) {
                                foco34.setCurrentTileIndex(1);
                                lista.add(foco34);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable15.setRotation(90);
        cable15.setCurrentTileIndex(0);
        registerTouchArea(cable15);
        attachChild(cable15);

        cable15.setAnchorCenter(0.0f,0.5f);
        cable15.setWidth(cable15.getWidth()+200);

        // *** Agrega Cable17
        // colisiona con foco 16 y foco 18
        cable17 = new AnimatedSprite(193,364,admRecursos.regionCableMediano,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable17.getCurrentTileIndex()==1){
                        goingBack(cable17);
                    }else{

                        if (lista.getLast().equals(foco16)||lista.getLast().equals(foco18)) {
                            hudMarcador.aumentarMarcador(100);
                            cable17.setCurrentTileIndex(1);
                            lista.add(cable17);
                            if (foco16.getCurrentTileIndex()==0) {
                                foco16.setCurrentTileIndex(1);
                                lista.add(foco16);
                            }
                            if (foco18.getCurrentTileIndex()==0) {
                                foco18.setCurrentTileIndex(1);
                                lista.add(foco18);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable17.setRotation(55);
        cable17.setCurrentTileIndex(0);
        registerTouchArea(cable17);
        attachChild(cable17);

        // *** Agrega Cable 19
        // colisiona con foco 18 y foco 20
        cable19 = new AnimatedSprite(287,364,admRecursos.regionCableMediano,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable19.getCurrentTileIndex()==1){
                        goingBack(cable19);
                    }else{

                        if (lista.getLast().equals(foco18)||lista.getLast().equals(foco20)) {
                            hudMarcador.aumentarMarcador(100);
                            cable19.setCurrentTileIndex(1);
                            lista.add(cable19);
                            if (foco18.getCurrentTileIndex()==0) {
                                foco18.setCurrentTileIndex(1);
                                lista.add(foco18);
                            }
                            if (foco20.getCurrentTileIndex()==0) {
                                foco20.setCurrentTileIndex(1);
                                lista.add(foco20);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable19.setRotation(125);
        cable19.setCurrentTileIndex(0);
        registerTouchArea(cable19);
        attachChild(cable19);

        // *** Agrega Cable 21
        // colisiona con foco 24 y foco 16
        cable21 = new AnimatedSprite(154,320,admRecursos.regionCableLargo,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable21.getCurrentTileIndex()==1){
                        goingBack(cable21);
                    }else{

                        if (lista.getLast().equals(foco16)||lista.getLast().equals(foco24)) {
                            hudMarcador.aumentarMarcador(100);
                            cable21.setCurrentTileIndex(1);
                            lista.add(cable21);
                            if (foco24.getCurrentTileIndex()==0) {
                                foco24.setCurrentTileIndex(1);
                                lista.add(foco24);
                            }
                            if (foco16.getCurrentTileIndex()==0) {
                                foco16.setCurrentTileIndex(1);
                                lista.add(foco16);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable21.setRotation(90);
        cable21.setCurrentTileIndex(0);
        registerTouchArea(cable21);
        attachChild(cable21);

        // *** Agrega Cable 22
        // colisiona con ...................................................................................
        cable22 = new AnimatedSprite(240,250,admRecursos.regionCableChico,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable22.getCurrentTileIndex()==1){
                        goingBack(cable22);
                    }else{
                        if (lista.getLast().equals(foco18)||lista.getLast().equals(foco25)) {
                            hudMarcador.aumentarMarcador(100);
                            cable22.setCurrentTileIndex(1);
                            lista.add(cable22);
                            if (foco18.getCurrentTileIndex()==0) {
                                foco18.setCurrentTileIndex(1);
                                lista.add(foco18);
                            }
                            if (foco25.getCurrentTileIndex()==0) {
                                foco25.setCurrentTileIndex(1);
                                lista.add(foco25);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable22.setRotation(90);
        cable22.setCurrentTileIndex(0);
        registerTouchArea(cable22);
        attachChild(cable22);


        // *** Agrega Cable 23
        // colisiona con foco 20 y foco 26
        cable23 = new AnimatedSprite(328,320,admRecursos.regionCableLargo,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable23.getCurrentTileIndex()==1){
                        goingBack(cable23);
                    }else{

                        if (lista.getLast().equals(foco20)||lista.getLast().equals(foco26)) {
                            hudMarcador.aumentarMarcador(100);
                            cable23.setCurrentTileIndex(1);
                            lista.add(cable23);
                            if (foco20.getCurrentTileIndex()==0) {
                                foco20.setCurrentTileIndex(1);
                                lista.add(foco20);
                            }
                            if (foco26.getCurrentTileIndex()==0) {
                                foco26.setCurrentTileIndex(1);
                                lista.add(foco26);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable23.setRotation(90);
        cable23.setCurrentTileIndex(0);
        registerTouchArea(cable23);
        attachChild(cable23);

        // *** Agrega Cable 27
        // colisiona con ......9............................................................................
        cable27 = new AnimatedSprite(111,210,admRecursos.regionCableChico,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable27.getCurrentTileIndex()==1){
                        goingBack(cable27);
                    }else{

                        if (lista.getLast().equals(foco24)||lista.getLast().equals(foco30)) {
                            hudMarcador.aumentarMarcador(100);
                            cable27.setCurrentTileIndex(1);
                            lista.add(cable27);
                            if (foco24.getCurrentTileIndex()==0) {
                                foco24.setCurrentTileIndex(1);
                                lista.add(foco24);
                            }
                            if (foco30.getCurrentTileIndex()==0) {
                                foco30.setCurrentTileIndex(1);
                                lista.add(foco30);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable27.setRotation(135);
        cable27.setCurrentTileIndex(0);
        registerTouchArea(cable27);
        attachChild(cable27);

        // *** Agrega Cable 28
        // colisiona con ...................................................................................
        cable28 = new AnimatedSprite(240,180,admRecursos.regionCableChico,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable28.getCurrentTileIndex()==1){
                        goingBack(cable28);
                    }else{

                        if (lista.getLast().equals(foco25)||lista.getLast().equals(foco32)) {
                            hudMarcador.aumentarMarcador(100);
                            cable28.setCurrentTileIndex(1);
                            lista.add(cable28);
                            if (foco25.getCurrentTileIndex()==0) {
                                foco25.setCurrentTileIndex(1);
                                lista.add(foco25);
                            }
                            if (foco32.getCurrentTileIndex()==0) {
                                foco32.setCurrentTileIndex(1);
                                lista.add(foco32);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable28.setRotation(90);
        cable28.setCurrentTileIndex(0);
        registerTouchArea(cable28);
        attachChild(cable28);

        // *** Agrega Cable 29
        // colisiona con foco 26,34
        cable29 = new AnimatedSprite(368,212,admRecursos.regionCableChico,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable29.getCurrentTileIndex()==1){
                        goingBack(cable29);
                    }else{

                        if (lista.getLast().equals(foco26)||lista.getLast().equals(foco34)) {
                            hudMarcador.aumentarMarcador(100);
                            cable29.setCurrentTileIndex(1);
                            lista.add(cable29);
                            if (foco26.getCurrentTileIndex()==0) {
                                foco26.setCurrentTileIndex(1);
                                lista.add(foco26);
                            }
                            if (foco34.getCurrentTileIndex()==0) {
                                foco34.setCurrentTileIndex(1);
                                lista.add(foco34);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable29.setRotation(46);
        cable29.setCurrentTileIndex(0);
        registerTouchArea(cable29);
        attachChild(cable29);


        // *** Agrega Cable 31
        // colisiona con ........................................................................................
        cable31 = new AnimatedSprite(154,144,admRecursos.regionCableMediano,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable31.getCurrentTileIndex()==1){
                        goingBack(cable31);
                    }else{
                        if (lista.getLast().equals(foco30)||lista.getLast().equals(foco32)) {
                            hudMarcador.aumentarMarcador(100);
                            cable31.setCurrentTileIndex(1);
                            lista.add(cable31);
                            if (foco30.getCurrentTileIndex()==0) {
                                foco30.setCurrentTileIndex(1);
                                lista.add(foco30);
                            }
                            if (foco32.getCurrentTileIndex()==0) {
                                foco32.setCurrentTileIndex(1);
                                lista.add(foco32);
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable31.setRotation(16);
        cable31.setCurrentTileIndex(0);
        registerTouchArea(cable31);
        attachChild(cable31);
        cable31.setWidth(cable31.getWidth()+35);

        // *** Agrega Cable 33
        // colisiona con ........................................................................................
        cable33 = new AnimatedSprite(326,144,admRecursos.regionCableLargo,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable33.getCurrentTileIndex()==1){
                        goingBack(cable33);
                    }else{

                        if (lista.getLast().equals(foco32)||lista.getLast().equals(foco34)) {
                            hudMarcador.aumentarMarcador(100);
                            cable33.setCurrentTileIndex(1);
                            lista.add(cable33);
                            if (foco32.getCurrentTileIndex()==0) {
                                foco32.setCurrentTileIndex(1);
                                lista.add(foco32);
                            }
                            if (foco34.getCurrentTileIndex()==0) {
                                foco34.setCurrentTileIndex(1);
                                lista.add(foco34);
                            }
                        }

                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable33.setRotation(-16);
        cable33.setCurrentTileIndex(0);
        registerTouchArea(cable33);
        attachChild(cable33);
        cable33.setWidth(cable33.getWidth()+35);


        // *** Agrega StartBox
        // colisiona con cable1 y con cable4
        startBox0 = new AnimatedSprite(153,680,admRecursos.regionStartEndBox,admRecursos.vbom){
            // Aquí el código que ejecuta la caja es presionada
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()) {
                    int i = lista.size()-1;
                    while(!lista.isEmpty()){
                        hudMarcador.disminuirMarcador(125);
                        lista.get(i).setCurrentTileIndex(0);
                        lista.remove(i);
                        i--;
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        startBox0.setCurrentTileIndex(1);
        registerTouchArea(startBox0);
        attachChild(startBox0);


//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

        // *** Agrega Foco5
        // colisiona con cable 1,6,10,11
        foco5 = new AnimatedSprite(73,545,admRecursos.regionFoco,admRecursos.vbom){

        };
        foco5.setCurrentTileIndex(0);
        registerTouchArea(foco5);
        attachChild(foco5);

        // *** Agrega Foco7
        // colisiona con cable  6,2,3,8,12 13
        foco7 = new AnimatedSprite(240,545,admRecursos.regionFoco,admRecursos.vbom){

        };
        foco7.setCurrentTileIndex(0);
        registerTouchArea(foco7);
        attachChild(foco7);


        // *** Agrega Foco9
        // colisiona con cable  4,8........14 15
        foco9 = new AnimatedSprite(407,545,admRecursos.regionFoco,admRecursos.vbom){

        };
        foco9.setCurrentTileIndex(0);
        registerTouchArea(foco9);
        attachChild(foco9);

        // *** Agrega Foco16
        // colisiona con cable  11, 12, 17 ,21
        foco16 = new AnimatedSprite(154,418,admRecursos.regionFoco,admRecursos.vbom){

        };
        foco16.setCurrentTileIndex(0);
        registerTouchArea(foco16);
        attachChild(foco16);


        // *** Agrega Foco 18
        // colisiona con cable  17,19,22
        foco18 = new AnimatedSprite(240,300,admRecursos.regionFoco,admRecursos.vbom){

        };
        foco18.setCurrentTileIndex(0);
        registerTouchArea(foco18);
        attachChild(foco18);

        // *** Agrega Foco 20
        // colisiona con cable  13, 14, 19 23
        foco20 = new AnimatedSprite(328,418,admRecursos.regionFoco,admRecursos.vbom){

        };
        foco20.setCurrentTileIndex(0);
        registerTouchArea(foco20);
        attachChild(foco20);

        // *** Agrega Foco 24
        // colisiona con cable  21,27
        foco24 = new AnimatedSprite(154,255,admRecursos.regionFoco,admRecursos.vbom){

        };
        foco24.setCurrentTileIndex(0);
        registerTouchArea(foco24);
        attachChild(foco24);

        // *** Agrega Foco 25
        // colisiona con cable  22,28
        foco25 = new AnimatedSprite(240,210,admRecursos.regionFoco,admRecursos.vbom){

        };
        foco25.setCurrentTileIndex(0);
        registerTouchArea(foco25);
        attachChild(foco25);

        // *** Agrega Foco 26
        // colisiona con cable  23,29
        foco26 = new AnimatedSprite(328,255,admRecursos.regionFoco,admRecursos.vbom){

        };
        foco26.setCurrentTileIndex(0);
        registerTouchArea(foco26);
        attachChild(foco26);

        // *** Agrega Foco 30
        // colisiona con cable 10,27,31
        foco30 = new AnimatedSprite(67,170,admRecursos.regionFoco,admRecursos.vbom){

        };
        foco30.setCurrentTileIndex(0);
        registerTouchArea(foco30);
        attachChild(foco30);

        // *** Agrega Foco 32
        // colisiona con cable 28,31,33
        foco32 = new AnimatedSprite(240,120,admRecursos.regionFoco,admRecursos.vbom){

        };
        foco32.setCurrentTileIndex(0);
        registerTouchArea(foco32);
        attachChild(foco32);

        // *** Agrega Foco 34
        // colisiona con cable 15,29,33
        foco34 = new AnimatedSprite(413,170,admRecursos.regionFoco,admRecursos.vbom){

        };
        foco34.setCurrentTileIndex(0);
        registerTouchArea(foco34);
        attachChild(foco34);

        // *** Agrega EndBox
        // colisiona con cable 5 y cable 6
        endBox = new AnimatedSprite(326,680,admRecursos.regionStartEndBox,admRecursos.vbom){
            // Aquí el código que ejecuta la caja es presionada
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                boolean allBulbsOn = true;
                ArrayList<AnimatedSprite> focos = new ArrayList<AnimatedSprite>();
                focos.add(foco5);
                focos.add(foco7);
                focos.add(foco9);
                focos.add(foco16);
                focos.add(foco18);
                focos.add(foco20);
                focos.add(foco24);
                focos.add(foco26);
                focos.add(foco30);
                focos.add(foco25);
                focos.add(foco32);
                focos.add(foco34);


                for(int i=0; i<focos.size(); i++){
                    if (focos.get(i).getCurrentTileIndex()==0){
                        allBulbsOn = false;
                    }
                }
                if(allBulbsOn) {
                    if (cable3.getCurrentTileIndex() == 1 || cable4.getCurrentTileIndex() == 1) {
                        if (pSceneTouchEvent.isActionDown()) {
                            if (endBox.getCurrentTileIndex() == 0) {
                                hudMarcador.multiplicarMarcador(((int)rectanguloEnergia.getWidth()/10));
                                ganar=true;
                                endBox.setCurrentTileIndex(1);
                                // Programa la carga de la segunda escena, después de cierto tiempo
                                admRecursos.engine.registerUpdateHandler(new TimerHandler(0.5f,
                                        new ITimerCallback() {
                                            @Override
                                            public void onTimePassed(TimerHandler pTimerHandler) {
                                                admRecursos.engine.unregisterUpdateHandler(pTimerHandler); // Invalida el timer
                                                admEscenas.crearEscenaFin(1);
                                                hudMarcador.setPosition(15, ControlJuego.ALTO_CAMARA / 2);
                                                admEscenas.setEscena(TipoEscena.ESCENA_FIN);
                                                admEscenas.liberarEscenaNivel3();
                                            }
                                        }));
                            }
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        endBox.setCurrentTileIndex(0);
        registerTouchArea(endBox);
        attachChild(endBox);

        admMusica.cargarMusicaNivel3();
    }

    // El ciclo principal de la escena
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        //Log.d("pausa",String.valueOf(pausa));
        if (pausa||ganar) {     // Se prendió la bandera?
            setIgnoreUpdate(true);  // Ya no ejecuta este método
            btnContinuar.setCurrentTileIndex(0);
        }
        if (rectanguloEnergia.getWidth() > 0) {
            rectanguloEnergia.setWidth(rectanguloEnergia.getWidth() - 0.3f);
        }else{
            admEscenas.crearEscenaFin(0);
            admRecursos.camara.setHUD(null);
               // Quita el HUD
            admEscenas.setEscena(TipoEscena.ESCENA_FIN);
            admEscenas.liberarEscenaNivel3();
        }
    }

    // Agrega el hud del Marcador
    private void agregarEstado() {
        hudMarcador = new EstadoJuego(admRecursos.engine,admRecursos.actividadJuego,"");
        admRecursos.camara.setHUD(hudMarcador);
    }

    // Crea la escena que se mostrará cuando se pausa el juego
    private void crearEscenaPausa() {
        // Crea la escena que se mostrará
        escenaPausa = new Scene();
        // No muestra fondo
        escenaPausa.setBackground(fondoP);
        escenaPausa.setBackgroundEnabled(true);
        //#################################
        // Botón Continuar
        btnContinuar = new ButtonSprite(220,500,
                admRecursos.regionBtnContinuar,admRecursos.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionUp()) {
                    regresarANivel();
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        escenaPausa.registerTouchArea(btnContinuar);
        escenaPausa.attachChild(btnContinuar);
        //################################################################################33

        // *** Agrega los botones a la pantalla de Ajustes

        // Botón OnOFF para la música
        btnOnOFF_1 = new AnimatedSprite(310,390,
                admRecursos.regionBtnonOff,admRecursos.vbom) {
            // Aquí el código que ejecuta el botón cuando es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()) {
                    if (btnOnOFF_1.getCurrentTileIndex()==0){
                        btnOnOFF_1.setCurrentTileIndex(1);
                        //ControlJuego.musicaOn=true;
                    }else{
                        btnOnOFF_1.setCurrentTileIndex(0);
                        //ControlJuego.musicaOn=false;
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        escenaPausa.registerTouchArea(btnOnOFF_1);
        escenaPausa.attachChild(btnOnOFF_1);

        // Botón OnOFF para los efectos
        btnOnOFF_2 = new AnimatedSprite(310,315,
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
        escenaPausa.registerTouchArea(btnOnOFF_2);
        escenaPausa.attachChild(btnOnOFF_2);

    }

    private void pausar() {
        pausa = true;
        setChildScene(escenaPausa, false, true, true);
    }

    // Regresar al menú principal
    private void regresarANivel() {
        pausa = false;
        setIgnoreUpdate(false);
        clearChildScene();
        //btnContinuar.detachSelf();
        //btnContinuar.dispose();
        //escenaPausa.detachSelf();
        //escenaPausa.dispose();
    }


    @Override
    public void onBackKeyPressed() {
        admMusica.liberarMusicaNivel();
        admMusica.continuarMusicaMenu();
        admEscenas.crearEscenaJuego();
        admRecursos.camara.setHUD(null);    // Quita el HUD
        admEscenas.setEscena(TipoEscena.ESCENA_JUEGO);
        admEscenas.liberarEscenaNivel3();
    }

    // Permite regresar a un punto anterior en el encendido de los focos y tubos
    public void goingBack(AnimatedSprite a) {
        while (!lista.getLast().equals(a)) {
            hudMarcador.disminuirMarcador(125);
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