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

    //Botón Nivel_1
    public ITiledTextureRegion regionBtnNivel1;
    private BuildableBitmapTextureAtlas btaBtnNivel1;

    //Botón Nivel_2
    public ITiledTextureRegion regionBtnNivel2;
    private BuildableBitmapTextureAtlas btaBtnNivel2;

    //Botón Nivel_3
    public ITiledTextureRegion regionBtnNivel3;
    private BuildableBitmapTextureAtlas btaBtnNivel3;



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
    }

    public void liberarRecursosAjustes() {
        texturaFondoAjustes.unload();
        regionFondoAjustes = null;
    }


    public void cargarRecursosNivel1() {
        try {
            texturaFondoNivel = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "fondoNivel.jpg");
            regionFondoNivel = TextureRegionFactory.extractFromTexture(texturaFondoNivel);
            texturaFondoNivel.load();
        } catch (IOException e) {
            Log.d("cargarRecursosFondoNivel1", "No se puede cargar el fondo");
        }

        //Carga la imagen para el botón de Pausa
        btaBtnPausa = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),40,40);
        regionBtnPausa = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnPausa,
                actividadJuego.getAssets(), "BtnPausa.png",1,1);

        try{
            btaBtnPausa.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
            Log.d("CargarRecursosNivel1","No se puede cargar la imagen del botón de pausa");
        }
        btaBtnPausa.load();
    }

    public void liberarRecursosNivel1() {
        texturaFondoNivel.unload();
        regionFondoNivel = null;
    }


    public void cargarRecursosNivel2() {
        try {

            texturaFondoNivel = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "fondoNivel.jpg");
            regionFondoNivel = TextureRegionFactory.extractFromTexture(texturaFondoNivel);
            texturaFondoNivel.load();
        } catch (IOException e) {
            Log.d("cargarRecursosFondoNivel2", "No se puede cargar el fondo");
        }

        //Carga la imagen para el botón de Pausa
        btaBtnPausa = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),40,40);
        regionBtnPausa = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnPausa,
                actividadJuego.getAssets(), "BtnPausa.png",1,1);

        try{
            btaBtnPausa.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
            Log.d("CargarRecursosNivel2","No se puede cargar la imagen del botón de pausa");
        }
        btaBtnPausa.load();
    }

        public void liberarRecursosNivel2() {
        texturaFondoNivel.unload();
        regionFondoNivel = null;
    }


    public void cargarRecursosNivel3() {
        try {

            texturaFondoNivel = new AssetBitmapTexture(actividadJuego.getTextureManager(),
                    actividadJuego.getAssets(), "fondoNivel.jpg");
            regionFondoNivel = TextureRegionFactory.extractFromTexture(texturaFondoNivel);
            texturaFondoNivel.load();
        } catch (IOException e) {
            Log.d("cargarRecursosFondoNivel3", "No se puede cargar el fondo");
        }

        //Carga la imagen para el botón de Pausa
        btaBtnPausa = new BuildableBitmapTextureAtlas(actividadJuego.getTextureManager(),40,40);
        regionBtnPausa = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btaBtnPausa,
                actividadJuego.getAssets(), "BtnPausa.png",1,1);

        try{
            btaBtnPausa.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));

        }catch(ITextureAtlasBuilder.TextureAtlasBuilderException e){
            Log.d("CargarRecursosNivel3","No se puede cargar la imagen del botón de pausa");
        }
        btaBtnPausa.load();
    }

    public void liberarRecursosNivel3() {
        texturaFondoNivel.unload();
        regionFondoNivel = null;
    }

}
