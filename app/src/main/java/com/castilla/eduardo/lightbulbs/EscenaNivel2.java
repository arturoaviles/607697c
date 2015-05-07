package com.castilla.eduardo.lightbulbs;

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
import org.andengine.input.touch.TouchEvent;
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
public class EscenaNivel2 extends EscenaBase
{
    private Sprite spriteFondoNivel; //(el fondo de la escena, estático)


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
        btnPausa = new ButtonSprite(410,740,
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



        // *** Agrega StartBox
        // colisiona con cable1 y con cable4
        startBox = new AnimatedSprite(132,630,admRecursos.regionStartEndBox,admRecursos.vbom){
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
        startBox.setCurrentTileIndex(1);
        registerTouchArea(startBox);
        attachChild(startBox);

        // *** Agrega Cable1
        // colisiona cajaStart y con foco1
        cable1 = new AnimatedSprite(257,620,admRecursos.regionCableLargo,admRecursos.vbom){
            // Aquí el código que ejecuta el cable1 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()) {
                    if (cable1.getCurrentTileIndex()==1){
                        goingBack(cable1);
                    }else{
                        if (lista.isEmpty()||lista.getLast().equals(foco1)) {
                            hudMarcador.aumentarMarcador(100);
                            cable1.setCurrentTileIndex(1);
                            lista.add(cable1);
                            if (foco1.getCurrentTileIndex()==0) {
                                foco1.setCurrentTileIndex(1);
                                lista.add(foco1);
                            }
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
        cable2 = new AnimatedSprite(358,518,admRecursos.regionCableLargo,admRecursos.vbom){

            // Aquí el código que ejecuta el cable2 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {

                    if (cable2.getCurrentTileIndex()==1){
                        goingBack(cable2);
                    }else{

                        if (lista.getLast().equals(foco1)||lista.getLast().equals(foco2)) {
                            hudMarcador.aumentarMarcador(100);
                            cable2.setCurrentTileIndex(1);
                            lista.add(cable2);
                            if (foco1.getCurrentTileIndex()==0) {
                                foco1.setCurrentTileIndex(1);
                                lista.add(foco1);
                            }
                            if (foco2.getCurrentTileIndex()==0) {
                                foco2.setCurrentTileIndex(1);
                                lista.add(foco2);
                            }
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
        cable3 = new AnimatedSprite(258,417,admRecursos.regionCableLargo,admRecursos.vbom){
            // Aquí el código que ejecuta el cable3 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {
                    if (cable3.getCurrentTileIndex()==1){
                        goingBack(cable3);
                    }else{

                        if (lista.getLast().equals(foco3)||lista.getLast().equals(foco2)) {
                            hudMarcador.aumentarMarcador(100);
                            cable3.setCurrentTileIndex(1);
                            lista.add(cable3);
                            if (foco2.getCurrentTileIndex()==0) {
                                foco2.setCurrentTileIndex(1);
                                lista.add(foco2);
                            }
                            if (foco3.getCurrentTileIndex()==0) {
                                foco3.setCurrentTileIndex(1);
                                lista.add(foco3);
                            }
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
        cable4 = new AnimatedSprite(157,518,admRecursos.regionCableLargo,admRecursos.vbom){
            // Aquí el código que ejecuta el cable1 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()) {
                    if (cable4.getCurrentTileIndex()==1){
                        goingBack(cable4);
                    }else{
                        if (lista.isEmpty()||lista.getLast().equals(foco3)) {
                            hudMarcador.aumentarMarcador(100);
                            cable4.setCurrentTileIndex(1);
                            lista.add(cable4);
                            if (foco3.getCurrentTileIndex()==0) {
                                foco3.setCurrentTileIndex(1);
                                lista.add(foco3);
                            }
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
        cable5 = new AnimatedSprite(358,316,admRecursos.regionCableLargo,admRecursos.vbom){

            // Aquí el código que ejecuta el cable1 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {
                    if (cable5.getCurrentTileIndex()==1){
                        goingBack(cable5);
                    }else{
                        if (lista.getLast().equals(foco2)) {
                            hudMarcador.aumentarMarcador(100);
                            cable5.setCurrentTileIndex(1);
                            lista.add(cable5);
                            if (foco2.getCurrentTileIndex()==0) {
                                foco2.setCurrentTileIndex(1);
                                lista.add(foco2);
                            }
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
        cable6 = new AnimatedSprite(258,215,admRecursos.regionCableLargo,admRecursos.vbom){

            // Aquí el código que ejecuta el cable1 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {
                    if (cable6.getCurrentTileIndex()==1){
                        goingBack(cable6);
                    }else{
                        if (lista.getLast().equals(foco4)) {
                            hudMarcador.aumentarMarcador(100);
                            cable6.setCurrentTileIndex(1);
                            lista.add(cable6);
                            if (foco4.getCurrentTileIndex()==0) {
                                foco4.setCurrentTileIndex(1);
                                lista.add(foco4);
                            }
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
        cable7 = new AnimatedSprite(157,318,admRecursos.regionCableLargo,admRecursos.vbom){
            // Aquí el código que ejecuta el cable1 es presionado
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()&&!lista.isEmpty()) {
                    if (cable7.getCurrentTileIndex()==1){
                        goingBack(cable7);
                    }else{
                        if (lista.getLast().equals(foco3)||lista.getLast().equals(foco4)) {
                            hudMarcador.aumentarMarcador(100);
                            cable7.setCurrentTileIndex(1);
                            lista.add(cable7);
                            if (foco3.getCurrentTileIndex()==0) {
                                foco3.setCurrentTileIndex(1);
                                lista.add(foco3);
                            }
                            if (foco4.getCurrentTileIndex()==0) {
                                foco4.setCurrentTileIndex(1);
                                lista.add(foco4);
                            }
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

        };
        foco1.setCurrentTileIndex(0);
        registerTouchArea(foco1);
        attachChild(foco1);

        // *** Agrega Foco2
        // colisiona con cable 2 , cable 3 y cable 5
        foco2 = new AnimatedSprite(358,418,admRecursos.regionFoco,admRecursos.vbom){

        };
        foco2.setCurrentTileIndex(0);
        registerTouchArea(foco2);
        attachChild(foco2);

        // *** Agrega Foco3
        // colisiona con cable 4 cable 3 y cable 7

        foco3 = new AnimatedSprite(157,418,admRecursos.regionFoco,admRecursos.vbom){

        };
        foco3.setCurrentTileIndex(0);
        registerTouchArea(foco3);
        attachChild(foco3);

        // *** Agrega Foco4
        //colisiona con cable 6, cable 7
        foco4 = new AnimatedSprite(157,218,admRecursos.regionFoco,admRecursos.vbom){

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
                boolean allBulbsOn = true;
                ArrayList<AnimatedSprite> focos = new ArrayList<AnimatedSprite>();
                focos.add(foco1);
                focos.add(foco2);
                focos.add(foco3);
                focos.add(foco4);

                for(int i=0; i<focos.size(); i++){
                    if (focos.get(i).getCurrentTileIndex()==0){
                        allBulbsOn = false;
                    }
                }
                if(allBulbsOn) {
                    revisarMarcador();
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
                                                admEscenas.liberarEscenaNivel2();
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

        admMusica.cargarMusicaNivel2();

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
            rectanguloEnergia.setWidth(rectanguloEnergia.getWidth() - 0.5f);
        }else{
            admEscenas.crearEscenaFin(0);
            admRecursos.camara.setHUD(null);
               // Quita el HUD
            admEscenas.setEscena(TipoEscena.ESCENA_FIN);
            admEscenas.liberarEscenaNivel2();
        }
    }

    // Agrega el hud del Marcador
    private void agregarEstado() {
        hudMarcador = new EstadoJuego(admRecursos.engine,admRecursos.actividadJuego,"");
        admRecursos.camara.setHUD(hudMarcador);
        hudMarcador.setMarcadorMasAlto(admRecursos.leerRecordNivel2());
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
    }


    @Override
    public void onBackKeyPressed() {
        admMusica.liberarMusicaNivel();
        if(admMusica.leerPreferenciaMusica()){
           admMusica.reproducirMusicaMenu();
        }

        admEscenas.crearEscenaJuego();
        admRecursos.camara.setHUD(null);    // Quita el HUD
        admEscenas.setEscena(TipoEscena.ESCENA_JUEGO);
        admEscenas.liberarEscenaNivel2();
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

    private void revisarMarcador() {
        //
        int masAlto = hudMarcador.getMarcadorMasAlto();
        int puntos = hudMarcador.getMarcador();
        // si la puntacíon supera el highscore
        if (puntos>=masAlto) {
            admRecursos.modificarRecordNivel2(puntos);
        }
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_NIVEL_2;
    }
    @Override
    public void liberarEscena() {
        // Liberar cada recurso usado en esta escena
        this.detachSelf();      // La escena se deconecta del engine
        this.dispose();         // Libera la memoria
    }
}