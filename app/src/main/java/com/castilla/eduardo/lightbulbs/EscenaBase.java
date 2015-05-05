package com.castilla.eduardo.lightbulbs;

import org.andengine.entity.scene.Scene;

/**
 * Define el comportamiento de las ESCENAS.
 * Cada escena del juego DEBE heredar de esta clase
 */
public abstract class EscenaBase extends Scene
{
    // Variable protegida para que se pueda acceder desde las subclases
    protected AdministradorRecursos admRecursos;
    protected AdministradorEscenas admEscenas;
    protected AdministradorMusica admMusica;

    public EscenaBase() {
        admRecursos = AdministradorRecursos.getInstance();
        admEscenas = AdministradorEscenas.getInstance();
        admMusica = AdministradorMusica.getInstance();
        // Llama al método que crea la escena
        crearEscena();  // Este método debe implementarse en la subclase
    }

    // Métodos abstractos
    public abstract void crearEscena(); // Arma la escena
    public abstract void onBackKeyPressed(); // Atiende el botón de back
    public abstract TipoEscena getTipoEscena(); // Regresa el tipo de escena
    public abstract void liberarEscena();   // Libera los recursos asignados
}
