package com.castilla.eduardo.lightbulbs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.io.IOException;

/**
 * Carga/Descarga los recurso del juego. Imágenes, Audios
 */
public class AdministradorRecursos {
    // Instancia única de la clase
    private static final AdministradorRecursos INSTANCE = new AdministradorRecursos();

    public Engine engine;
    public ControlJuego actividadJuego;
    public Camera camara;
    public VertexBufferObjectManager vbom;

    // ** TEXTURAS **
    // Escena Splash (imagen estática)
    private ITexture texturaSplash;
    public ITextureRegion regionSplash;

    // Escena Menu (imagen estática)
    private ITexture texturaFondoMenu;
    public ITextureRegion regionFondoMenu;

    // Escena Juego (imagen estática)
    private ITexture texturaFondoJuego;
    public ITextureRegion regionFondoJuego;

    //Escena Instrucciones
    private ITexture texturaFondoInstrucciones;
    public ITextureRegion regionFondoInstrucciones;

    //Escena Marcador
    private ITexture texturaFondoMarcador;
    public ITextureRegion regionFondoMarcador;

    //Sprite Animado "lo hiciste bien"
    private BuildableBitmapTextureAtlas texturaBien;
    public ITiledTextureRegion regionBien;

    //Escena Fin
    private ITexture texturaFondoFin;
    public ITextureRegion regionFondoFin;

    // Escena AcercaDe (imagen estática)
    private ITexture texturaFondoAcercaDe;
    public ITextureRegion regionFondoAcercaDe;

    // Escena Nivel (imagen estática)
    private ITexture texturaFondoNivel;
    public ITextureRegion regionFondoNivel;

    // Escena Ajustes (imagen estática)
    private ITexture texturaFondoAjustes;
    public ITextureRegion regionFondoAjustes;

    //Botón jugar del menú
    public ITiledTextureRegion regionBtnJugar;
    private BuildableBitmapTextureAtlas btaBtnJugar;

    //Botón Instrucciones del menú
    public ITiledTextureRegion regionBtnInstrucciones;
    private BuildableBitmapTextureAtlas btaBtnInstrucciones;

    //Botón Marcador del menú
    public ITiledTextureRegion regionBtnMarcador;
    private BuildableBitmapTextureAtlas btaBtnMarcador;

    //Botón Ajustes del Menú
    public ITiledTextureRegion regionBtnAjustes;
    private BuildableBitmapTextureAtlas btaBtnAjustes;

    //Botón AcercaDe del menú
    public ITiledTextureRegion regionBtnAcercaDe;
    private BuildableBitmapTextureAtlas btaBtnAcercaDe;

    //Botón Pausa del Nivel
    public ITiledTextureRegion regionBtnPausa;
    private BuildableBitmapTextureAtlas btaBtnPausa;

    //Botón onOFF de la pantalla ajustes
    public ITiledTextureRegion regionBtnonOff;
    private BuildableBitmapTextureAtlas btaBtnOnOff;

    //Botón Nivel_1
    public ITiledTextureRegion regionBtnNivel1;
    private BuildableBitmapTextureAtlas btaBtnNivel1;

    //Botón Nivel_2
    public ITiledTextureRegion regionBtnNivel2;
    private BuildableBitmapTextureAtlas btaBtnNivel2;

    //Botón Nivel_3
    public ITiledTextureRegion regionBtnNivel3;
    private BuildableBitmapTextureAtlas btaBtnNivel3;

    //Botón Continuar Escena Pausa
    public ITiledTextureRegion regionBtnContinuar;
    private BuildableBitmapTextureAtlas btaBtnContinuar;


    // Nivel Imagenes

    // Bateria
    private ITexture texturaBateria;
    public ITextureRegion regionBateria;

    //StartBox
    private BuildableBitmapTextureAtlas texturaStartEndBox;
    public ITiledTextureRegion regionStartEndBox;

    // Cable Largo
    private BuildableBitmapTextureAtlas texturaCableLargo;
    public ITiledTextureRegion regionCableLargo;

    // Cable Mediano
    private BuildableBitmapTextureAtlas texturaCableMediano;
    public ITiledTextureRegion regionCableMediano;

    // Cable Chico
    private BuildableBitmapTextureAtlas texturaCableChico;
    public ITiledTextureRegion regionCableChico;

    // Foco
    private BuildableBitmapTextureAtlas texturaFoco;
    public ITiledTextureRegion regionFoco;



    public static AdministradorRecursos getInstance() {
        return INSTANCE;
}

    public static void inicializarAdministrador(Engine engine,
                                                ControlJuego control, Camera camara, VertexBufferObjectManager vbom) {
        getInstance().engine = engine;
        getInstance().actividadJuego=control;
        getInstance().camara = camara;
        getInstance().vbom = vbom;
    }

    public void cargarRecursosSplash() {
        try {
            // Carga la imagen de fondo de la pantalla Splash
            texturaSplash = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "logoTec.png");
            regionSplash = TextureRegionFactory.extractFromTexture(texturaSplash);
            texturaSplash.load();
        } catch (IOException e) {
            Log.d("cargarRecursosSplash", "No se puede cargar el fondo");
        }
    }

    public void liberarRecursosSplash() {
        texturaSplash.unload();
        regionSplash = null;
    }

    public void cargarRecursosMenu() {
        try {

            // Carga la imagen de fondo de la pantalla Menu
            texturaFondoMenu = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "PantallaInicio.jpg");
            regionFondoMenu = TextureRegionFactory.extractFromTexture(texturaFondoMenu);
            texturaFondoMenu.load();

            //Carga la imagen para el botón jugar
            btaBtnJugar = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),264,132);
            regionBtnJugar = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnJugar,
                    actividadJuego.getAssets(), "BtnPlay.png",2,1);
            try{
                btaBtnJugar.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

            }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
                Log.d("CargarRecursosMenu","No se puede cargar la imagen del botón jugar");
            }
            btaBtnJugar.load();

            //Carga la imagen para el botón Ajustes
            btaBtnAjustes = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),264,132);
            regionBtnAjustes = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnAjustes,
                    actividadJuego.getAssets(), "BtnAjustes.png",2,1);
            try{
                btaBtnAjustes.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

            }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
                Log.d("CargarRecursosAjustes","No se puede cargar la imagen del botón Ajustes");
            }
            btaBtnAjustes.load();

            // Fin de carga imagen botón jugar


            //Carga la imagen para el botón instrucciones
            btaBtnInstrucciones = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),264,132);
            regionBtnInstrucciones = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnInstrucciones,
                    actividadJuego.getAssets(), "btnInstrucciones.png",2,1);
            try{
                btaBtnInstrucciones.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

            }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
                Log.d("CargarRecursosInstrucciones","No se puede cargar la imagen del botón instrucciones");
            }
            btaBtnInstrucciones.load();

            //Carga la imagen para el botón Marcador
            btaBtnMarcador = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),264,132);
            regionBtnMarcador = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnMarcador,
                    actividadJuego.getAssets(), "BtnPuntaje.png",2,1);
            try{
                btaBtnMarcador.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

            }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
                Log.d("CargarRecursosMenú","No se puede cargar la imagen del botón Marcador");
            }
            btaBtnMarcador.load();

            // Fin de carga imagen botón Instrucciones

            ////////////////////////////////////////
            //Carga la imagen para el botón AcercaDe
            btaBtnAcercaDe = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),264,132);
            regionBtnAcercaDe = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnAcercaDe,
                    actividadJuego.getAssets(), "BtnInfo.png",2,1);
            try{
                btaBtnAcercaDe.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

            }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
                Log.d("CargarRecursosAcercaDe","No se puede cargar la imagen del botón jugar");
            }
            btaBtnAcercaDe.load();
            // Fin de carga imagen botón AcercaDe

        } catch (IOException e) {
            Log.d("cargarRecursosMenu", "No se puede cargar el fondo del menu");
        }
    }


    public void liberarRecursosMenu() {
        // Fondo
        texturaFondoMenu.unload();
        regionFondoMenu = null;
        //botón jugar
        btaBtnJugar.unload();
        regionBtnJugar=null;
        //botón instrucciones
        btaBtnInstrucciones.unload();
        regionBtnInstrucciones=null;
        //botón acercaDe
        btaBtnAcercaDe.unload();
        regionBtnAcercaDe=null;
        //botón Marcador
        btaBtnMarcador.unload();
        regionBtnMarcador=null;
        //botón Ajustes
        btaBtnAjustes.unload();
        regionBtnAjustes=null;
    }

    public void cargarRecursosJuego() {
        try {
            // Carga la imagen de fondo de la pantalla Juego
            texturaFondoJuego = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "SeleccionNiveles.jpg");
            regionFondoJuego = TextureRegionFactory.extractFromTexture(texturaFondoJuego);
            texturaFondoJuego.load();

        } catch (IOException e) {
            Log.d("cargarRecursosFondoJuego", "No se puede cargar el fondo");
        }

        //Carga la imagen para el botón nivel_1
        btaBtnNivel1 = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),264,132);
        regionBtnNivel1 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnNivel1,
                actividadJuego.getAssets(), "BtnNivelesUno.png",2,1);

        try{
            btaBtnNivel1.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
            Log.d("CargarRecursosJuego","No se puede cargar la imagen del botón nivel_1");
        }
        btaBtnNivel1.load();

        //Carga la imagen para el botón nivel_2
        btaBtnNivel2 = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),264,132);
        regionBtnNivel2 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnNivel2,
                actividadJuego.getAssets(), "BtnNivelesDos.png",2,1);

        try{
            btaBtnNivel2.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
            Log.d("CargarRecursosJuego","No se puede cargar la imagen del botón nivel_2");
        }
        btaBtnNivel2.load();

        //Carga la imagen para el botón nivel_3
        btaBtnNivel3 = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),264,132);
        regionBtnNivel3 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnNivel3,
                actividadJuego.getAssets(), "BtnNivelesTres.png",2,1);
        try{
            btaBtnNivel3.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
            Log.d("CargarRecursosJuego","No se puede cargar la imagen del botón nivel_3");
        }
        btaBtnNivel3.load();
    }


    public void liberarRecursosJuego() {
        texturaFondoJuego.unload();
        regionFondoJuego = null;

        //liberar botón nivel_1
        btaBtnNivel1.unload();
        regionBtnNivel1 = null;
        //liberar botón nivel_2
        btaBtnNivel2.unload();
        regionBtnNivel2 = null;
        //liberar botón nivel_3
        btaBtnNivel3.unload();
        regionBtnNivel3 = null;
    }

    public void cargarRecursosInstrucciones() {
        try {
            // Carga la imagen de fondo de la pantalla Instrucciones
            texturaFondoInstrucciones = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "PantallaInstrucciones.jpg");
            regionFondoInstrucciones = TextureRegionFactory.extractFromTexture(texturaFondoInstrucciones);
            texturaFondoInstrucciones.load();
        } catch (IOException e) {
            Log.d("cargarRecursosFondoInstrucciones", "No se puede cargar el fondo");
        }
    }

    public void liberarRecursosInstrucciones() {
        texturaFondoInstrucciones.unload();
        regionFondoInstrucciones = null;
    }

    public void cargarRecursosMarcador() {
        try {
            // Carga la imagen de fondo de la pantalla Marcador
            texturaFondoMarcador = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "Marcador.jpg");
            regionFondoMarcador = TextureRegionFactory.extractFromTexture(texturaFondoMarcador);
            texturaFondoMarcador.load();
        } catch (IOException e) {
            Log.d("cargarRecursosFondoMarcador", "No se puede cargar el marcador");
        }
    }

    public void liberarRecursosMarcador() {
        texturaFondoMarcador.unload();
        regionFondoMarcador= null;
    }


    public void cargarRecursosAcercaDe() {
        try {
            // Carga la imagen de fondo de la pantalla Splash
            texturaFondoAcercaDe = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "acercaDe.jpg");
            regionFondoAcercaDe = TextureRegionFactory.extractFromTexture(texturaFondoAcercaDe);
            texturaFondoAcercaDe.load();
        } catch (IOException e) {
            Log.d("cargarRecursosFondoAcercaDe", "No se puede cargar el fondo");
        }
    }

    public void liberarRecursosAcercaDe() {
        texturaFondoAcercaDe.unload();
        regionFondoAcercaDe = null;
    }

    public void cargarRecursosFin(int i) {
        try {
            // Carga la imagen de fondo de la pantalla Fin
            texturaFondoFin = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "fin.jpg");
            regionFondoFin = TextureRegionFactory.extractFromTexture(texturaFondoFin);
            texturaFondoFin.load();
        } catch (IOException e) {
            Log.d("cargarRecursosFin", "No se puede cargar el fondo de la escena fin");
        }

        // Carga las imagines de "Lo hiciste bien".
        texturaBien = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),960,800);
        if(i==0){
            regionBien = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                    texturaBien,actividadJuego, "Mal.jpg",2,1);
        }else{
            regionBien = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                    texturaBien,actividadJuego, "PantallaBien.jpg",2,1);
        }

        try {
            texturaBien.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));
            texturaBien.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("onCreateResources","No se puede cargar la imagen para el Sprite lo hiciste bien");
        }
    }

    public void liberarRecursosFin() {
        texturaFondoFin.unload();
        regionFondoAcercaDe = null;
        
        texturaBien.unload();
        regionBien = null;
    }


    public void cargarRecursosAjustes() {
        try {
            // Carga la imagen de fondo de la pantalla Ajustes
            texturaFondoAjustes = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "PantallaAjustes.jpg");
            regionFondoAjustes = TextureRegionFactory.extractFromTexture(texturaFondoAjustes);
            texturaFondoAjustes.load();
        } catch (IOException e) {
            Log.d("cargarRecursosAjustes", "No se puede cargar el fondo de la escena Ajustes");
        }

        //Carga la imagen para el botón onOFF de la pantalla Ajustes
        btaBtnOnOff = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),280,69);
        regionBtnonOff = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnOnOff,
                actividadJuego.getAssets(), "BtnOnOff.png",2,1);
        try{
            btaBtnOnOff.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
            Log.d("CargarRecursosJuego","No se puede cargar la imagen del botón nivel_3");
        }
        btaBtnOnOff.load();
    }

    public void liberarRecursosAjustes() {
        texturaFondoAjustes.unload();
        regionFondoAjustes = null;
        btaBtnOnOff.unload();
        regionBtnonOff=null;
    }


    public void cargarRecursosNivel1() {
        cargarRecursosBasicosDeNivel(1);
    }


    public void liberarRecursosNivel1() {
        liberarRecursosBasicosDeNivel();
    }


    public void cargarRecursosNivel2() {
        cargarRecursosBasicosDeNivel(2);
    }

    public void liberarRecursosNivel2() {
        liberarRecursosBasicosDeNivel();
    }


    public void cargarRecursosNivel3() {

        cargarRecursosBasicosDeNivel(3);
    }

    public void liberarRecursosNivel3() {
        liberarRecursosBasicosDeNivel();
    }


    public void cargarRecursosBasicosDeNivel(int i){

        //Carga la imagen de fondo
        try {

            if(i == 1){
                texturaFondoNivel = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                        actividadJuego.getAssets(), "fondoNivel.jpg");

            }else if(i==2){
                texturaFondoNivel = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                        actividadJuego.getAssets(), "nivelMadera.jpg");
            }else{
                texturaFondoNivel = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                        actividadJuego.getAssets(), "nivelMetal.jpg");
            }

            regionFondoNivel = TextureRegionFactory.extractFromTexture(texturaFondoNivel);
            texturaFondoNivel.load();
        } catch (IOException e) {
            Log.d("cargarRecursosFondoNivel", "No se puede cargar el fondo");
        }

        //Carga la imagen de la bateria
        try {
            texturaBateria = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "bateria.png");
            regionBateria = TextureRegionFactory.extractFromTexture(texturaBateria);
            texturaBateria.load();
        } catch (IOException e) {
            Log.d("cargarRecursosNivel", "No se puede cargar la bateria");
        }


        //Carga la imagen para el botón de Pausa
        btaBtnPausa = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),40,40);
        regionBtnPausa = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnPausa,
                actividadJuego.getAssets(), "BtnPausa.png",1,1);

        try{
            btaBtnPausa.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
            Log.d("CargarRecursosNivel","No se puede cargar la imagen del botón de pausa");
        }
        btaBtnPausa.load();


        //Carga la imagen para el botón de Continuar
        btaBtnContinuar = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),432,66);
        regionBtnContinuar = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnContinuar,
                actividadJuego.getAssets(), "BtnContinuar.png",2,1);

        try{
            btaBtnContinuar.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
            Log.d("CargarRecursosNivel","No se puede cargar la imagen del botón de pausa");
        }
        btaBtnContinuar.load();

        //Carga la imagen para el botón onOFF de la pantalla Pausa
        btaBtnOnOff = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),280,69);
        regionBtnonOff = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnOnOff,
                actividadJuego.getAssets(), "BtnOnOff.png",2,1);
        try{
            btaBtnOnOff.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
            Log.d("CargarRecursosJuego","No se puede cargar la imagen del botón nivel_3");
        }
        btaBtnOnOff.load();

        // Carga las imágenes del StartEndBox
        texturaStartEndBox = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),172,59);
        regionStartEndBox = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                texturaStartEndBox,actividadJuego, "StartEndBox.png",2,1);
        try {
            texturaStartEndBox.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("onCreateResources","No se puede cargar la imagen para el Sprite del StartEndBox");
        }
        texturaStartEndBox.load();

        // Carga las imágenes del Cable LARGO
        texturaCableLargo = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),326,62);
        regionCableLargo = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                texturaCableLargo,actividadJuego, "CableLargo.png",2,1);
        try {
            texturaCableLargo.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("onCreateResources","No se puede cargar la imagen para el Sprite del Cable1");
        }
        texturaCableLargo.load();

        // Carga las imágenes del Cable MEDIANO
        texturaCableMediano = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),246,62);
        regionCableMediano = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                texturaCableMediano,actividadJuego, "CableMediano.png",2,1);
        try {
            texturaCableMediano.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("onCreateResources","No se puede cargar la imagen para el Sprite del Cable1");
        }
        texturaCableMediano.load();

        // Carga las imágenes del Cable CHICO
        texturaCableChico = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),196,62);
        regionCableChico = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                texturaCableChico,actividadJuego, "CableChico.png",2,1);
        try {
            texturaCableChico.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("onCreateResources","No se puede cargar la imagen para el Sprite del Cable1");
        }
        texturaCableChico.load();


        // Carga las imágenes del Foco
        texturaFoco = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),127,50);
        regionFoco = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
                texturaFoco,actividadJuego, "foco.png",2,1);
        try {
            texturaFoco.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));
            texturaFoco.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.d("onCreateResources","No se puede cargar la imagen para el Sprite del Foco1");
        }


    }

    public void liberarRecursosBasicosDeNivel(){
        texturaFondoNivel.unload();
        regionFondoNivel=null;

        texturaCableLargo.unload();
        regionCableLargo = null;

        texturaCableMediano.unload();
        regionCableMediano = null;

        texturaCableChico.unload();
        regionCableChico = null;

        texturaFoco.unload();
        regionFoco = null;

        texturaStartEndBox.unload();
        regionStartEndBox = null;

        btaBtnPausa.unload();
        regionBtnPausa = null;

        texturaBateria.unload();
        regionBateria = null;
    }


    ///////////////// Marcadores

    public int leerRecordNivel1() {
        SharedPreferences mSharedPrefs = actividadJuego.getSharedPreferences("PrefRecord1", Context.MODE_PRIVATE);
        SharedPreferences.Editor mPrefsEditor = mSharedPrefs.edit();
        int record = mSharedPrefs.getInt("Record1", 0);
        return record;
    }

    public void modificarRecordNivel1(int newRecord) {
        SharedPreferences mSharedPrefs = actividadJuego.getSharedPreferences("PrefRecord1",Context.MODE_PRIVATE);
        SharedPreferences.Editor mPrefsEditor = mSharedPrefs.edit();
        mPrefsEditor.putInt("Record1", newRecord);
        mPrefsEditor.commit();
    }

    public int leerRecordNivel2() {
        SharedPreferences mSharedPrefs = actividadJuego.getSharedPreferences("PrefRecord2", Context.MODE_PRIVATE);
        SharedPreferences.Editor mPrefsEditor = mSharedPrefs.edit();
        int record2 = mSharedPrefs.getInt("Record2", 0);
        return record2;
    }

    public void modificarRecordNivel2(int newRecord) {
        SharedPreferences mSharedPrefs = actividadJuego.getSharedPreferences("PrefRecord2",Context.MODE_PRIVATE);
        SharedPreferences.Editor mPrefsEditor = mSharedPrefs.edit();
        mPrefsEditor.putInt("Record2", newRecord);
        mPrefsEditor.commit();
    }

    public int leerRecordNivel3() {
        SharedPreferences mSharedPrefs = actividadJuego.getSharedPreferences("PrefRecord3", Context.MODE_PRIVATE);
        SharedPreferences.Editor mPrefsEditor = mSharedPrefs.edit();
        int record3 = mSharedPrefs.getInt("Record3", 0);
        return record3;
    }

    public void modificarRecordNivel3(int newRecord) {
        SharedPreferences mSharedPrefs = actividadJuego.getSharedPreferences("PrefRecord3",Context.MODE_PRIVATE);
        SharedPreferences.Editor mPrefsEditor = mSharedPrefs.edit();
        mPrefsEditor.putInt("Record3", newRecord);
        mPrefsEditor.commit();
    }

}
