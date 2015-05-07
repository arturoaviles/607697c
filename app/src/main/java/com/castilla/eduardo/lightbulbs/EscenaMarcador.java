package com.castilla.eduardo.lightbulbs;

import android.content.Context;
import android.content.SharedPreferences;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.align.HorizontalAlign;

/**
 * La escena que se muestra cuando corre la aplicación (Logo del TEC)
 */
public class EscenaMarcador extends EscenaBase
{
    private Sprite spriteFondo; //(el fondo de la escena, estático)
    //Marcador
    private EstadoJuego hudMarcador;

    @Override
    public void crearEscena() {
        // Creamos el sprite de manera óptima
        spriteFondo = new Sprite(0,0, admRecursos.regionFondoMarcador,admRecursos.vbom) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };

        // Carcar los marcadores



        // Configuración de la imagen
        spriteFondo.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
        //spriteFondo.setScale(0.7f);

        // Crea el fondo de la pantalla
        SpriteBackground fondo = new SpriteBackground(0,0,0.60f,spriteFondo);
        setBackground(fondo);
        setBackgroundEnabled(true);

        agregarListadeMarcadores();

    }

    @Override
    public void onBackKeyPressed() {
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaMarcador();
        admRecursos.camara.setHUD(null);    // Quita el HUD
    }


    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_MARCADOR;
    }

    // Crea y regresa un font que carga desde un archivo .ttf  (http://www.1001freefonts.com, http://www.1001fonts.com/)
    private Font crearTipoLetra() {

        // La imagen que contiene cada símbolo
        final ITexture fontTexture = new BitmapTextureAtlas(admRecursos.engine.getTextureManager(),1024,1024);

        // Carga el archivo, tamaño 36, antialias y color
        Font tipoLetra = FontFactory.createFromAsset(admRecursos.engine.getFontManager(),
                fontTexture, admRecursos.actividadJuego.getAssets(), "DK Crayon Crumble.ttf", 36, true, 0xffffffff);
        tipoLetra.load();
        tipoLetra.prepareLetters("Puntos: 0123456789".toCharArray());

        return tipoLetra;
    }

    // Para el marcador mayor
    public void agregarListadeMarcadores() {
        // Un cuadro transparente arriba centrado
        Rectangle cuadroMarcadores = new Rectangle(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2-25,
                270,450, admRecursos.engine.getVertexBufferObjectManager());
        cuadroMarcadores.setColor(0.2f, 0.2f, 0.2f, 0.5f);
        this.attachChild(cuadroMarcadores);

        int marcadorMasAlto1 = admRecursos.leerRecordNivel1();
        int marcadorMasAlto2 = admRecursos.leerRecordNivel2();
        int marcadorMasAlto3 = admRecursos.leerRecordNivel3();

        Text txtMarcadorMasAlto1;
        Text txtMarcadorMasAlto2;
        Text txtMarcadorMasAlto3;


        // Letrero sobre el cuadro
        Font tipo = crearTipoLetra();
        txtMarcadorMasAlto1 = new Text(20,380,
                tipo,"Mayor: 0",15,admRecursos.engine.getVertexBufferObjectManager());
        txtMarcadorMasAlto1.setText("NIVEL 1: "+marcadorMasAlto1);
        txtMarcadorMasAlto1.setWidth(200);
        txtMarcadorMasAlto1.setHorizontalAlign(HorizontalAlign.CENTER);
        txtMarcadorMasAlto1.setAnchorCenter(0.0f,0.5f);

        // Agrega el marcador al recuadro trasparente
        cuadroMarcadores.attachChild(txtMarcadorMasAlto1);

        txtMarcadorMasAlto2 = new Text(20,230,
                tipo,"Mayor: 0",15,admRecursos.engine.getVertexBufferObjectManager());
        txtMarcadorMasAlto2.setText("NIVEL 2: "+marcadorMasAlto2);
        txtMarcadorMasAlto2.setWidth(200);
        txtMarcadorMasAlto2.setHorizontalAlign(HorizontalAlign.CENTER);
        txtMarcadorMasAlto2.setAnchorCenter(0.0f,0.5f);

        // Agrega el marcador al recuadro trasparente
        cuadroMarcadores.attachChild(txtMarcadorMasAlto2);

        txtMarcadorMasAlto3 = new Text(20,80,
                tipo,"Mayor: 0",15,admRecursos.engine.getVertexBufferObjectManager());
        txtMarcadorMasAlto3.setText("NIVEL 3: "+marcadorMasAlto3);
        txtMarcadorMasAlto3.setWidth(200);
        txtMarcadorMasAlto3.setHorizontalAlign(HorizontalAlign.CENTER);
        txtMarcadorMasAlto3.setAnchorCenter(0.0f,0.5f);

        // Agrega el marcador al recuadro trasparente
        cuadroMarcadores.attachChild(txtMarcadorMasAlto3);
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
