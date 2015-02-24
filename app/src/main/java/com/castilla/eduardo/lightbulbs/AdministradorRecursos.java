package com.castilla.eduardo.lightbulbs;

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
public class AdministradorRecursos
{
    // Instancia única de la clase
    private static final AdministradorRecursos INSTANCE =
            new AdministradorRecursos();

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

    //Escena mARCADOR
    private ITexture texturaFondoMarcador;
    public ITextureRegion regionFondoMarcador;

    //Escena Fin
    private ITexture texturaFondoFin;
    public ITextureRegion regionFondoFin;

    // Escena AcercaDe (imagen estática)
    private ITexture texturaFondoAcercaDe;
    public ITextureRegion regionFondoAcercaDe;

    //Botón jugar del menú
    public ITiledTextureRegion regionBtnJugar;
    private BuildableBitmapTextureAtlas btaBtnJugar;

    //Botón Instrucciones del menú
    public ITiledTextureRegion regionBtnInstrucciones;
    private BuildableBitmapTextureAtlas btaBtnInstrucciones;

    //Botón Marcador del menú
    public ITiledTextureRegion regionBtnMarcador;
    private BuildableBitmapTextureAtlas btaBtnMarcador;

    //Botón AcercaDe del menú
    public ITiledTextureRegion regionBtnAcercaDe;
    private BuildableBitmapTextureAtlas btaBtnAcercaDe;


    //////////////////////////////////////

    //Sprite ball del Juego
    private ITexture texturaball;
    public ITextureRegion regionball;

    ///////////////////////////////////77

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

            texturaball = new AssetBitmapTexture(actividadJuego.getTextureManager(),actividadJuego.getAssets(),"ball58x56.png");
            regionball = TextureRegionFactory.extractFromTexture(texturaball);
            texturaball.load();

            // Carga la imagen de fondo de la pantalla Menu
            texturaFondoMenu = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "menuInicio.jpg");
            regionFondoMenu = TextureRegionFactory.extractFromTexture(texturaFondoMenu);
            texturaFondoMenu.load();

            //Carga la imagen para el botón jugar
            btaBtnJugar = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),256,128);
            regionBtnJugar = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnJugar,
                    actividadJuego.getAssets(), "btnJugar.png",2,1);
            try{
                btaBtnJugar.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

            }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
                Log.d("CargarRecursosMenu","No se puede cargar la imagen del botón jugar");
            }
            btaBtnJugar.load();

            // Fin de carga imagen botón jugar

            ///////////////////////////////////////////////
            // Carga la imagen de fondo de la pantalla Instrucciones
            texturaFondoInstrucciones = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "Instrucciones.jpg");
            regionFondoInstrucciones = TextureRegionFactory.extractFromTexture(texturaFondoInstrucciones);
            texturaFondoInstrucciones.load();

            //Carga la imagen para el botón jugar
            btaBtnInstrucciones = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),210,107);
            regionBtnInstrucciones = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnInstrucciones,
                    actividadJuego.getAssets(), "btnInstrucciones.png",2,1);
            try{
                btaBtnInstrucciones.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

            }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
                Log.d("CargarRecursosInstrucciones","No se puede cargar la imagen del botón instrucciones");
            }
            btaBtnInstrucciones.load();

            //Carga la imagen para el botón Marcador
            btaBtnMarcador = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),210,107);
            regionBtnMarcador = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnMarcador,
                    actividadJuego.getAssets(), "btnMarcador.png",2,1);
            try{
                btaBtnMarcador.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

            }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
                Log.d("CargarRecursosMenú","No se puede cargar la imagen del botón Marcador");
            }
            btaBtnMarcador.load();

            // Fin de carga imagen botón Instrucciones

            ////////////////////////////////////////
            //Carga la imagen para el botón AcercaDe
            btaBtnAcercaDe = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),256,128);
            regionBtnAcercaDe = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnAcercaDe,
                    actividadJuego.getAssets(), "btnAcercaDe.png",2,1);
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

        texturaball.unload();
        regionball=null;
    }

    public void cargarRecursosJuego() {
        try {
            // Carga la imagen de fondo de la pantalla Juego
            texturaFondoJuego = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "juego.jpg");
            regionFondoJuego = TextureRegionFactory.extractFromTexture(texturaFondoJuego);
            texturaFondoJuego.load();

            //////////////////////
            texturaball = new AssetBitmapTexture(actividadJuego.getTextureManager(),actividadJuego.getAssets(),"ball58x56.png");
            regionball = TextureRegionFactory.extractFromTexture(texturaball);
            texturaball.load();

            ///////////////////////777
        } catch (IOException e) {
            Log.d("cargarRecursosFondoJuego", "No se puede cargar el fondo");
        }
    }

    public void liberarRecursosJuego() {
        texturaFondoJuego.unload();
        regionFondoJuego = null;
        texturaball.unload();
        regionball=null;

    }


    public void cargarRecursosInstrucciones() {
        try {
            // Carga la imagen de fondo de la pantalla Instrucciones
            texturaFondoInstrucciones = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "Instrucciones.jpg");
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

    public void cargarRecursosFin() {
        try {
            // Carga la imagen de fondo de la pantalla Fin
            texturaFondoFin = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "fin.jpg");
            regionFondoFin = TextureRegionFactory.extractFromTexture(texturaFondoFin);
            texturaFondoFin.load();
        } catch (IOException e) {
            Log.d("cargarRecursosFin", "No se puede cargar el fondo de la escena fin");
        }
    }

    public void liberarRecursosFin() {
        texturaFondoFin.unload();
        regionFondoAcercaDe = null;
    }



}
