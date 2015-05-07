package com.castilla.eduardo.lightbulbs;

import android.util.Log;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.util.adt.align.HorizontalAlign;

/**
 * Estado del juego en la pantalla. Es un HUD (head-up display)
 */
public class EstadoJuego extends HUD
{
    private int marcador;
    private Engine engine;
    private Text txtMarcador;
    private ControlJuego actividad;

    // Marcador más alto
    private Text txtMarcadorMasAlto;
    private int marcadorMasAlto;



    public EstadoJuego(Engine engine, ControlJuego actividad,String Escena) {
        this.engine = engine;
        marcador = 0;
        this.actividad = actividad;

        if(Escena.equals("marcadores")){
            agregarMarcadorMasAlto();
        }else{
            agregarMarcadorMasAlto();
            agregarMarcador();
        }

    }

    // Método para actualizar el marcador
    public void disminuirMarcador(int puntos) {

        marcador -= puntos;

        if (marcador<0) {
            marcador = 0;
        }
        // Actualiza en el HUD
        txtMarcador.setText("Puntos: " + marcador);
    }

    // Método para actualizar el marcador
    public void aumentarMarcador(int puntos) {
        marcador += puntos;
        // Actualiza en el HUD
        txtMarcador.setText("Puntos: " + marcador);

        // ¿Supera el marcador anterior?
        if (marcador>marcadorMasAlto) {
            setMarcadorMasAlto(marcador);
        }
    }

    public void multiplicarMarcador(int delta) {
        marcador *= delta;
        // Actualiza en el HUD
        txtMarcador.setText("Puntos: " + marcador);
    }

    // Arma el HUD del juego.
    public void agregarMarcador() {
        // Un cuadro negro transparente arriba a la derecha
        Rectangle cuadro = new Rectangle(ControlJuego.ANCHO_CAMARA/2,80,
                300,100, engine.getVertexBufferObjectManager());
        cuadro.setColor(0,0,0,0);
        this.attachChild(cuadro);

        // Letrero sobre el cuadro
        Font tipo = crearTipoLetra();
        txtMarcador = new Text(150,50,
                tipo,"Puntos: " + marcador,15,engine.getVertexBufferObjectManager());
        txtMarcador.setWidth(280);
        txtMarcador.setHorizontalAlign(HorizontalAlign.LEFT);

        // Agrega el marcador al recuadro trasparente
        cuadro.attachChild(txtMarcador);
    }

    // Crea y regresa un font que carga desde un archivo .ttf  (http://www.1001freefonts.com, http://www.1001fonts.com/)
    private Font crearTipoLetra() {

        // La imagen que contiene cada símbolo
        final ITexture fontTexture = new BitmapTextureAtlas(engine.getTextureManager(),1024,1024);

        // Carga el archivo, tamaño 36, antialias y color
        Font tipoLetra = FontFactory.createFromAsset(engine.getFontManager(),
                fontTexture, actividad.getAssets(), "DK Crayon Crumble.ttf", 36, true, 0xffffffff);
        tipoLetra.load();
        tipoLetra.prepareLetters("Puntos: 0123456789".toCharArray());

        return tipoLetra;
    }

    // Método accesor
    public int getMarcador() {
        return marcador;
    }

    // Método modificador
        public void setMarcador(int marcador) {
        this.marcador = marcador;
    }

    // Para el marcador mayor
    public void agregarMarcadorMasAlto() {
        // Un cuadro transparente arriba centrado
        Rectangle cuadroMasAlto = new Rectangle(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2-25,
                270,450, engine.getVertexBufferObjectManager());
        cuadroMasAlto.setColor(0.2f, 0.2f, 0.2f, 0.1f);
        this.attachChild(cuadroMasAlto);

        // Letrero sobre el cuadro
        Font tipo = crearTipoLetra();
        txtMarcadorMasAlto = new Text(0,0,
                tipo,"Mayor: 0",15,engine.getVertexBufferObjectManager());
        txtMarcadorMasAlto.setWidth(200);
        txtMarcadorMasAlto.setHorizontalAlign(HorizontalAlign.CENTER);

        // Agrega el marcador al recuadro trasparente
        //cuadroMasAlto.attachChild(txtMarcadorMasAlto);

    }

    // Para el marcador mayor
    public void agregarListadeMarcadores() {
        // Un cuadro transparente arriba centrado
        Rectangle cuadroMarcadores = new Rectangle(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2-25,
                270,450, engine.getVertexBufferObjectManager());
        cuadroMarcadores.setColor(0.2f, 0.2f, 0.2f, 0.5f);
        this.attachChild(cuadroMarcadores);

        // Letrero sobre el cuadro
        Font tipo = crearTipoLetra();
        txtMarcadorMasAlto = new Text(0,0,
                tipo,"Mayor: 0",15,engine.getVertexBufferObjectManager());
        txtMarcadorMasAlto.setWidth(200);
        txtMarcadorMasAlto.setHorizontalAlign(HorizontalAlign.CENTER);

        // Agrega el marcador al recuadro trasparente
        cuadroMarcadores.attachChild(txtMarcadorMasAlto);

    }

    public int getMarcadorMasAlto() {
        return marcadorMasAlto;
    }

    public void setMarcadorMasAlto(int ultimoMarcador) {
        marcadorMasAlto = ultimoMarcador;
        // Mostrar el marcador más alto
        txtMarcadorMasAlto.setText("Mayor: " + ultimoMarcador);
    }


}
