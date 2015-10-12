package mx.itesm.planetz;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *  Esta clase es un contenedor para el progreso del jugador.
 *
 *
 * Created by Diego on 11/10/2015.
 */
public class SessionManager {
    // =============================================================================================
    //                          D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================

    // ============== Referencia de Instancia Única =============
    private static SessionManager INSTANCE = new SessionManager();

    // ===========================================================
    //          Referencia a las preferencias de usuario
    // ===========================================================
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferencesEditor;

    // ===========================================================
    //           Referencias a los demás elementos del juego
    // ===========================================================
    private GameManager gameManager;

    // ===========================================================
    //                   Variables Contenedoras
    // ===========================================================

    // ============== NOMBRE DE ARCHIVO con el progreso ==========
    private static final String preferenceName = "Progreso";

    // ============== Opciones de AUDIO ==========================
    public boolean musicEnabled, soundEnabled;
    public float musicVolume, soundVolume;

    // ============== Nivel de ADVENTURE MODE en el que se halla =
    public int currentLevel;

    // ============== Si se ha desbloqueado INFINITE MODE========
    public boolean infiniteModeUnlocked;

    // ============== Contiene qué GEMAS se han desbloqueado =====
    public boolean gemsUnlocked[][];
    // =============================================================================================
    //                                         M É T O D O S
    // =============================================================================================

    // ===========================================================
    // Inicializa la instancia única de el Adm. de Sesión/Progreso
    // ===========================================================
    public static void initialize(GameManager gameManager){
        getInstance().gameManager = gameManager;

        getInstance().sharedPreferences = getInstance().gameManager.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        getInstance().sharedPreferencesEditor = getInstance().sharedPreferences.edit();

        // ============== Inicializa la sesión/progreso ==========
        getInstance().initializePreferences();
    }
    // ===========================================================
    //      Inicializa la sesión/progreso de la aplicación
    // ===========================================================
    public void initializePreferences(){

        // ============== Estado de los AFX's ===================
        musicEnabled = sharedPreferences.getBoolean("musicEnabled", true);
        soundEnabled = sharedPreferences.getBoolean("soundEnabled", true);

        // ============== Volumen de AFX's =======================
        musicVolume = sharedPreferences.getFloat("musicVolume",1f);
        soundVolume = sharedPreferences.getFloat("soundVolume",1f);

        // ============== Nivel actual de Adv. Mode ==============
        currentLevel = sharedPreferences.getInt("currentLevel",0);
    }

    // ===========================================================
    //  Escribe los cambios realizados a el archivo de Progreso
    // ===========================================================
    public void writeChanges() {

        // ============== Estado de los AFX's ===================
        sharedPreferencesEditor.putBoolean("musicEnabled",musicEnabled);
        sharedPreferencesEditor.putBoolean("soundEnabled", soundEnabled);

        // ============== Volumen de AFX's =======================
        sharedPreferencesEditor.putFloat("musicVolume", musicVolume);
        sharedPreferencesEditor.putFloat("soundVolume", soundVolume);

        // ============== Nivel actual de Adv. Mode ==============
        sharedPreferencesEditor.putInt("currentLevel",0);

        // ============== Escribir los Cambios ===================
        sharedPreferencesEditor.commit();

    }



    // =============================================================================================
    //                                G E T T E R S  &  S E T T E R S
    // =============================================================================================

    // ============== REGRESA la INSTANCIA de este Adm. =========
    public static SessionManager getInstance() {
        return INSTANCE;
    }


}