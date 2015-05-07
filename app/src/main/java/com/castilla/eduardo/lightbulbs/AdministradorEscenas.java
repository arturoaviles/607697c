package com.castilla.eduardo.lightbulbs;

import org.andengine.engine.Engine;

/**
 * Administra la escena que se verá en la pantalla
 */
public class AdministradorEscenas
{
    // Instancia única
    private static final AdministradorEscenas INSTANCE =
            new AdministradorEscenas();

    // Declara las distintas escenas que forman el juego
    private EscenaBase escenaSplash;
    private EscenaBase escenaMenu;
    private EscenaBase escenaAcercaDe;
    private EscenaBase escenaJuego;
    private EscenaBase escenaAjustes;
    private EscenaBase escenaInstrucciones;
    private EscenaBase escenaMarcador;
    private EscenaBase escenaNivel1;
    private EscenaBase escenaNivel2;
    private EscenaBase escenaNivel3;
    private EscenaBase escenaFin;

    // El tipo de escena que se está mostrando
    private TipoEscena tipoEscenaActual = TipoEscena.ESCENA_SPLASH;
    // La escena que se está mostrando
    private EscenaBase escenaActual;
    // El engine para hacer el cambio de escenas
    private Engine engine = AdministradorRecursos.getInstance().engine;
    // El administrados de recursos
    private AdministradorRecursos admRecursos = AdministradorRecursos.getInstance();
    // Regresa la instancia del administrador de escenas
    public static AdministradorEscenas getInstance() {
        return INSTANCE;
    }
    // Regresa el tipo de la escena actual
    public TipoEscena getTipoEscenaActual() {
        return tipoEscenaActual;
    }
    // Regresa la escena actual
    public EscenaBase getEscenaActual() {
        return escenaActual;
    }



    /*
     * Pone en la pantalla la escena que llega como parámetro y guarda el nuevo estado
     */
    private void setEscenaBase(EscenaBase nueva) {
        engine.setScene(nueva);
        escenaActual = nueva;
        tipoEscenaActual = nueva.getTipoEscena();
    }

    /**
     * Cambia a la escena especificada en el parámetro
     * @param nuevoTipo la nueva escena que se quiere mostrar
     */
    public void setEscena(TipoEscena nuevoTipo) {
        switch (nuevoTipo) {
            case ESCENA_SPLASH:
                setEscenaBase(escenaSplash);
                break;
            case ESCENA_MENU:
                setEscenaBase(escenaMenu);
                break;
            case ESCENA_JUEGO:
                setEscenaBase(escenaJuego);
                break;
            case ESCENA_AJUSTES:
                setEscenaBase(escenaAjustes);
                break;
            case ESCENA_INSTRUCCIONES:
                setEscenaBase(escenaInstrucciones);
                break;
            case ESCENA_ACERCA_DE:
                setEscenaBase(escenaAcercaDe);
                break;
            case ESCENA_MARCADOR:
                setEscenaBase(escenaMarcador);
                break;
            case ESCENA_NIVEL_1:
                setEscenaBase(escenaNivel1);
                break;
            case ESCENA_NIVEL_2:
                setEscenaBase(escenaNivel2);
                break;
            case ESCENA_NIVEL_3:
                setEscenaBase(escenaNivel3);
                break;
            case ESCENA_FIN:
                setEscenaBase(escenaFin);
                break;
        }
    }

    //*** Crea la escena de Splash
    public void crearEscenaSplash() {
        // Carga los recursos
        admRecursos.cargarRecursosSplash();
        escenaSplash = new EscenaSplash();
    }

    //*** Libera la escena de Splash
    public void liberarEscenaSplash() {
        admRecursos.liberarRecursosSplash();
        escenaSplash.liberarEscena();
        escenaSplash = null;
    }

    //*** Crea la escena de Menu
    public void crearEscenaMenu() {
        // Carga los recursos
        admRecursos.cargarRecursosMenu();
        escenaMenu = new EscenaMenu();
    }

    //*** Libera la escena de Menu
    public void liberarEscenaMenu() {
        admRecursos.liberarRecursosMenu();
        escenaMenu.liberarEscena();
        escenaMenu = null;
    }

    //*** Crea la escena de Juego
    public void crearEscenaJuego() {
        // Carga los recursos
        admRecursos.cargarRecursosJuego();
        escenaJuego = new EscenaJuego();
    }

    //*** Libera la escena de Juego
    public void liberarEscenaJuego() {
        admRecursos.liberarRecursosJuego();
        escenaJuego.liberarEscena();
        escenaJuego = null;
    }

    //*** Crea la escena de Instrucciones
    public void crearEscenaInstrucciones() {
        // Carga los recursos
        admRecursos.cargarRecursosInstrucciones();
        escenaInstrucciones = new EscenaInstrucciones();
    }

    //*** Libera la escena de Instrucciones
    public void liberarEscenaInstrucciones() {
        admRecursos.liberarRecursosInstrucciones();
        escenaInstrucciones.liberarEscena();
        escenaInstrucciones = null;
    }

    //*** Crea la escena de Marcador
    public void crearEscenaMarcador() {
        // Carga los recursos
        admRecursos.cargarRecursosMarcador();
        escenaMarcador = new EscenaMarcador();
    }

    //*** Libera la escena de Marcador
    public void liberarEscenaMarcador() {
        admRecursos.liberarRecursosMarcador();
        escenaMarcador.liberarEscena();
        escenaMarcador= null;
    }

    //*** Crea la escena de Ajustes
    public void crearEscenaAjustes() {
        // Carga los recursos
        admRecursos.cargarRecursosAjustes();
        escenaAjustes = new EscenaAjustes();
    }

    //*** Libera la escena de Ajuestes
    public void liberarEscenaAjustes() {
        admRecursos.liberarRecursosAjustes();
        escenaAjustes.liberarEscena();
        escenaAjustes= null;
    }

    //*** Crea la escena de AcercaDe
    public void crearEscenaAcercaDe() {
        // Carga los recursos
        admRecursos.cargarRecursosAcercaDe();
        escenaAcercaDe = new EscenaAcercaDe();
    }

    //*** Libera la escena de AcercaDe
    public void liberarEscenaAcercaDe() {
        admRecursos.liberarRecursosAcercaDe();
        escenaAcercaDe.liberarEscena();
        escenaAcercaDe = null;
    }

    //*** Crea la escena de Nivel1
    public void crearEscenaNivel1() {
        // Carga los recursos
        admRecursos.cargarRecursosNivel1();
        escenaNivel1 = new EscenaNivel1();
    }

    //*** Libera la escena de Nivel1
    public void liberarEscenaNivel1() {
        admRecursos.liberarRecursosNivel1();
        escenaNivel1.liberarEscena();
        escenaNivel1 = null;
    }

    //*** Crea la escena de Nivel2
    public void crearEscenaNivel2() {
        // Carga los recursos
        admRecursos.cargarRecursosNivel2();
        escenaNivel2 = new EscenaNivel2();
    }

    //*** Libera la escena de Nivel2
    public void liberarEscenaNivel2() {
        admRecursos.liberarRecursosNivel2();
        escenaNivel2.liberarEscena();
        escenaNivel2 = null;
    }

    //*** Crea la escena de Nivel3
    public void crearEscenaNivel3() {
        // Carga los recursos
        admRecursos.cargarRecursosNivel3();
        escenaNivel3 = new EscenaNivel3();
    }

    //*** Libera la escena de Nivel3
    public void liberarEscenaNivel3() {
        admRecursos.liberarRecursosNivel3();
        escenaNivel3.liberarEscena();
        escenaNivel3 = null;
    }

    //*** Crea la escena de Fin
    public void crearEscenaFin(int i) {
        // Carga los recursos
        admRecursos.cargarRecursosFin(i);
        escenaFin = new EscenaFin();
    }

    //*** Libera la escena de Fin
    public void liberarEscenaFin() {
        admRecursos.liberarRecursosFin();
        escenaFin.liberarEscena();
        escenaFin = null;
    }
}
