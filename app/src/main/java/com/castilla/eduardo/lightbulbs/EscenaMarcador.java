package com.castilla.eduardo.lightbulbs;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

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
        // Configuración de la imagen
        spriteFondo.setPosition(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2);
        //spriteFondo.setScale(0.7f);

        // Crea el fondo de la pantalla
        SpriteBackground fondo = new SpriteBackground(0,0,0.60f,spriteFondo);
        setBackground(fondo);
        setBackgroundEnabled(true);

        //Agrega el marcador
        agregarEstado();
    }

    @Override
    public void onBackKeyPressed() {
        admEscenas.crearEscenaMenu();
        admEscenas.setEscena(TipoEscena.ESCENA_MENU);
        admEscenas.liberarEscenaMarcador();
        admRecursos.camara.setHUD(null);    // Quita el HUD
    }

    // Agrega el hud del Marcador
    private void agregarEstado() {
        hudMarcador = new EstadoJuego(admRecursos.engine,admRecursos.actividadJuego,"marcadores");
        admRecursos.camara.setHUD(hudMarcador);
    }

    @Override
    public TipoEscena getTipoEscena() {
        return TipoEscena.ESCENA_MARCADOR;
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
