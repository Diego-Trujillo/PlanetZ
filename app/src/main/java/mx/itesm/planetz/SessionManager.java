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
        // ============== Inicializa referencias a Adm.'s ========
        getInstance().gameManager = gameManager;

        // ============== Inicializa la sesión/progreso ==========
        getInstance().sharedPreferences = getInstance().gameManager.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        getInstance().sharedPreferencesEditor = getInstance().sharedPreferences.edit();

        // ============== Carga la sesión/progreso ===============
        getInstance().initializePreferences();
    }
    // ===========================================================
    //      Inicializa la sesión/progreso de la aplicación
    // ===========================================================
    public void initializePreferences(){
        /* Básicamente, obtiene las preferencias del archivo de preferencias "Progreso",
        *  Busca el valor indicado por la llave (key, ... ), si no existe el valor, entonces
        *  lo crea con el valor default (..., default).*/
        // ============== Estado de los AFX's ===================
        musicEnabled = sharedPreferences.getBoolean("musicEnabled", true);
        soundEnabled = sharedPreferences.getBoolean("soundEnabled", true);

        // ============== Volumen de AFX's =======================
        musicVolume = sharedPreferences.getFloat("musicVolume",1f);
        soundVolume = sharedPreferences.getFloat("soundVolume", 1f);

        // ============== Infite Mode Unlocked ====================
        infiniteModeUnlocked = sharedPreferences.getBoolean("infiniteModeUnlocked",false);

        // ============== Gemas Desbloqueadas =====================
        gemsUnlocked = new boolean[3][3];
        // -------------- Nivel 1 -------------------------------
        gemsUnlocked[0][0] = sharedPreferences.getBoolean("gem_1_1", false);
        gemsUnlocked[0][1] = sharedPreferences.getBoolean("gem_1_2",false);
        gemsUnlocked[0][2] = sharedPreferences.getBoolean("gem_1_3",false);

        // -------------- Nivel 2 -------------------------------
        gemsUnlocked[1][0] = sharedPreferences.getBoolean("gem_2_1",false);
        gemsUnlocked[1][1] = sharedPreferences.getBoolean("gem_2_2",false);
        gemsUnlocked[1][2] = sharedPreferences.getBoolean("gem_2_3",false);

        // -------------- Nivel 3 -------------------------------
        gemsUnlocked[2][0] = sharedPreferences.getBoolean("gem_3_1",false);
        gemsUnlocked[2][1] = sharedPreferences.getBoolean("gem_3_2",false);
        gemsUnlocked[2][2] = sharedPreferences.getBoolean("gem_3_3",false);

        // ============== Nivel actual de Adv. Mode ==============
        currentLevel = sharedPreferences.getInt("currentLevel",0);
    }

    // ===========================================================
    //  Escribe los cambios realizados a el archivo de Progreso
    // ===========================================================
    public void writeChanges() {

        // ============== Estado de los AFX's ===================
        sharedPreferencesEditor.putBoolean("musicEnabled", musicEnabled);
        sharedPreferencesEditor.putBoolean("soundEnabled", soundEnabled);

        // ============== Volumen de AFX's =======================
        sharedPreferencesEditor.putFloat("musicVolume", musicVolume);
        sharedPreferencesEditor.putFloat("soundVolume", soundVolume);

        // ============== Infite Mode Unlocked ====================
        sharedPreferencesEditor.putBoolean("infiniteModeUnlocked", infiniteModeUnlocked);

        // ============== Gemas Desbloqueadas ====================

        // ============== Nivel actual de Adv. Mode ==============
        sharedPreferencesEditor.putInt("currentLevel", currentLevel);

        // ============== Gemas Desbloqueadas ===================
        // -------------- Nivel 1 -------------------------------
        sharedPreferencesEditor.putBoolean("gem_1_1",gemsUnlocked[0][0]);
        sharedPreferencesEditor.putBoolean("gem_1_2",gemsUnlocked[0][1]);
        sharedPreferencesEditor.putBoolean("gem_1_3",gemsUnlocked[0][2]);

        // -------------- Nivel 2 -------------------------------
        sharedPreferencesEditor.putBoolean("gem_2_1",gemsUnlocked[1][0]);
        sharedPreferencesEditor.putBoolean("gem_2_2",gemsUnlocked[1][1]);
        sharedPreferencesEditor.putBoolean("gem_2_3",gemsUnlocked[1][2]);

        // -------------- Nivel 3 -------------------------------
        sharedPreferencesEditor.putBoolean("gem_3_1",gemsUnlocked[2][0]);
        sharedPreferencesEditor.putBoolean("gem_3_2",gemsUnlocked[2][1]);
        sharedPreferencesEditor.putBoolean("gem_3_3",gemsUnlocked[2][2]);

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