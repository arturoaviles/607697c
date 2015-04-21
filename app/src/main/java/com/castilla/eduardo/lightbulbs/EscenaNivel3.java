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
    public AnimatedSprite cable7;
    public AnimatedSprite cable8;

    public AnimatedSprite cable10;
    public AnimatedSprite cable11;

    public AnimatedSprite foco5;


    public AnimatedSprite foco7;
    public AnimatedSprite foco9;



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
        cable1 = new AnimatedSprite(120,578,admRecursos.regionCableLargo,admRecursos.vbom){
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


        // *** Agrega Cable2
        // colisiona con cajaStart0 y foco 7
        cable2 = new AnimatedSprite(194,575,admRecursos.regionCableLargo,admRecursos.vbom){

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
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable2.setRotation(65);
        cable2.setCurrentTileIndex(0);
        registerTouchArea(cable2);
        attachChild(cable2);

        // *** Agrega Cable3
        // colisiona con cajaEnd y foco 7
        cable3 = new AnimatedSprite(280,575,admRecursos.regionCableLargo,admRecursos.vbom){

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
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        cable3.setRotation(110);
        cable3.setCurrentTileIndex(0);
        registerTouchArea(cable3);
        attachChild(cable3);


        // *** Agrega Cable4
        // colisiona con cajaEnd y foco 9
        cable4 = new AnimatedSprite(362,578,admRecursos.regionCableLargo,admRecursos.vbom){

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

        // *** Agrega Cable6
        // colisiona con foco 5 y foco 7
        cable6 = new AnimatedSprite(172,480,admRecursos.regionCableChico,admRecursos.vbom){

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


        // *** Agrega Cable8
        // colisiona con foco 5 y foco 9
        cable8 = new AnimatedSprite(308,480,admRecursos.regionCableChico,admRecursos.vbom){

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

        // *** Agrega StartBox
        // colisiona con cable1 y con cable4
        startBox0 = new AnimatedSprite(ControlJuego.ANCHO_CAMARA/2-100,680,admRecursos.regionStartEndBox,admRecursos.vbom){
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































        // *** Agrega Foco5
        // colisiona con cable 1,6,10,11
        foco5 = new AnimatedSprite(120,480,admRecursos.regionFoco,admRecursos.vbom){
            // Aquí el código que ejecuta la foco es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {
                    if (foco5.getCurrentTileIndex()==1){
                        goingBack(foco5);
                    }else{
                        if (lista.getLast().equals(cable1)||lista.getLast().equals(cable6)||lista.getLast().equals(cable10)||lista.getLast().equals(cable11)) {
                            hudMarcador.aumentarMarcador(100);
                            foco5.setCurrentTileIndex(1);
                            lista.add(foco5);
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        foco5.setCurrentTileIndex(0);
        registerTouchArea(foco5);
        attachChild(foco5);

        // *** Agrega Foco7
        // colisiona con cable  6,2,3,8............... 12 13
        foco7 = new AnimatedSprite(240,485,admRecursos.regionFoco,admRecursos.vbom){
            // Aquí el código que ejecuta la foco es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {
                    if (foco7.getCurrentTileIndex()==1){
                        goingBack(foco7);
                    }else{
                        if (lista.getLast().equals(cable6)||lista.getLast().equals(cable2)||lista.getLast().equals(cable3)||lista.getLast().equals(cable8)) {
                            hudMarcador.aumentarMarcador(100);
                            foco7.setCurrentTileIndex(1);
                            lista.add(foco7);
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        foco7.setCurrentTileIndex(0);
        registerTouchArea(foco7);
        attachChild(foco7);


        // *** Agrega Foco9
        // colisiona con cable  4,8........14 15
        foco9 = new AnimatedSprite(362,480,admRecursos.regionFoco,admRecursos.vbom){
            // Aquí el código que ejecuta la foco es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {
                    if (foco9.getCurrentTileIndex()==1){
                        goingBack(foco9);
                    }else{
                        if (lista.getLast().equals(cable4)||lista.getLast().equals(cable8)) {
                            hudMarcador.aumentarMarcador(100);
                            foco9.setCurrentTileIndex(1);
                            lista.add(foco9);
                        }
                    }
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        foco9.setCurrentTileIndex(0);
        registerTouchArea(foco9);
        attachChild(foco9);



        // *** Agrega EndBox
        // colisiona con cable 5 y cable 6
        endBox = new AnimatedSprite(ControlJuego.ANCHO_CAMARA/2+100,680,admRecursos.regionStartEndBox,admRecursos.vbom){
            // Aquí el código que ejecuta la caja es presionada
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                boolean allBulbsOn = true;
                ArrayList<AnimatedSprite> focos = new ArrayList<AnimatedSprite>();
                focos.add(foco5);
                focos.add(foco7);
                focos.add(foco9);
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
                                endBox.setCurrentTileIndex(1);
                            } else {
                                endBox.setCurrentTileIndex(0);
                            }
                            // Programa la carga de la segunda escena, después de cierto tiempo
                            admRecursos.engine.registerUpdateHandler(new TimerHandler(1,
                                    new ITimerCallback() {
                                        @Override
                                        public void onTimePassed(TimerHandler pTimerHandler) {
                                            admRecursos.engine.unregisterUpdateHandler(pTimerHandler); // Invalida el timer
                                            admEscenas.crearEscenaFin(1);
                                            hudMarcador.setPosition(15,ControlJuego.ALTO_CAMARA/2);
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

    // El ciclo principal de la escena
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);
        //Log.d("pausa",String.valueOf(pausa));
        if (pausa) {     // Se prendió la bandera?
            //regresarAMenu();    // Terminar
            setIgnoreUpdate(false);  // Ya no ejecuta este método
            //terminar(); // Mostrar la escena de juego termina
            btnContinuar.setCurrentTileIndex(0);
            return;
        }
        if (rectanguloEnergia.getWidth() > 0) {
            //rectanguloEnergia.setWidth(rectanguloEnergia.getWidth() - 0.00000001f);
        }else{
            admEscenas.crearEscenaFin(0);
            admRecursos.camara.setHUD(null);    // Quita el HUD
            admEscenas.setEscena(TipoEscena.ESCENA_FIN);
            admEscenas.liberarEscenaNivel3();
        }
    }

    // Agrega el hud del Marcador
    private void agregarEstado() {
        hudMarcador = new EstadoJuego(admRecursos.engine,admRecursos.actividadJuego);
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
        //lista.getLast().setCurrentTileIndex(0);
        //lista.removeLast();
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