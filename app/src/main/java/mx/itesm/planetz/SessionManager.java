package mx.itesm.planetz;

import org.andengine.audio.music.MusicManager;

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
    private static SessionManager INSTANCE = new SessionManager();

    // ===========================================================
    //           Referencias a los demás elementos del juego
    // ===========================================================
    private GameManager gameManager;
    private ResourceManager resourceManager;

    // ===========================================================
    //                   Variables Contenedoras
    // ===========================================================

    // ============== Nombre de archivo con el progreso ==========
    private String fileName;

    // ============== Opciones de Audio. Orden: Musica-Sonido ====
    public boolean musicEnabled, soundEnabled;
    public float musicVolume, soundVolume;

    // ============== Nivel de modo Aventura en el que se halla ==
    public int currentLevel;

    // ============== Si se ha desbloqueado Infinite Mode ========
    public boolean infiniteModeUnlocked;

    // ============== Contiene qué gemas se han desbloqueado =====
    public boolean gemsUnlocked[][];

    // =============================================================================================
    //                                         M É T O D O S
    // =============================================================================================

    public static void initialize(GameManager gameManager){
        getInstance().gameManager = gameManager;
        getInstance().resourceManager = gameManager.resourceManager;
    }
    // =============================================================================================
    //                                G E T T E R S  &  S E T T E R S
    // =============================================================================================

    // ============== La instancia particular de esta clase ======
    public static SessionManager getInstance() {
        return INSTANCE;
    }
}