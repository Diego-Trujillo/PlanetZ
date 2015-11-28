package mx.itesm.planetz;

import org.andengine.audio.music.MusicManager;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.detector.ClickDetector;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.io.IOException;

/**
 *  Define qué debe hacer una escena, así como ofrece algunos métodos útiles
 *
 * Created by Diego on 03/10/2015.
 */
public abstract class BaseScene extends Scene{

    // =============================================================================================
    //                          D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================

    // ===========================================================
    //       Identifica el tipo de escena que es
    // ===========================================================
    protected SceneType sceneType;

    // ===========================================================
    //        Referencias a los administradores de recursos
    // ===========================================================
    protected GameManager gameManager;          // Administrador de Juego
    protected SceneManager sceneManager;        // Administrador de Escenas
    protected ResourceManager resourceManager;  // Administrador de Recursos
    protected SessionManager sessionManager;    // Administrador de Sesión/Progreso

    // ===========================================================
    //          Referencias a elementos del motor
    // ===========================================================
    protected Engine engine;
    protected VertexBufferObjectManager vertexBufferObjectManager;
    protected Camera camera;

    // =============================================================================================
    //                                      C O N S T R U C T O R
    // =============================================================================================
    public BaseScene(){
        // ============== Obtiene referencias a los admin. ======
        this.sceneManager =  SceneManager.getInstance();
        this.gameManager = sceneManager.gameManager;
        this.resourceManager = ResourceManager.getInstance();
        this.sessionManager = SessionManager.getInstance();

        // ============== Obtiene referencias a elem. del motor =
        this.engine = gameManager.getEngine();
        this.vertexBufferObjectManager = gameManager.getVertexBufferObjectManager();
        this.camera = engine.getCamera();

        // ============== Carga los recursos de la escena =======
        loadResources();
    }
    // =============================================================================================
    //                                         M É T O D O S
    // =============================================================================================
    // ===========================================================
    //    Define cómo se van a cargar los recursos de la escena
    // ===========================================================
    public void loadResources(){
        loadGFX();
        loadMFX();
        loadSFX();
    }
    // =============================================================================================
    //                      M É T O D O S  S E M I - A B S T R A C T O S
    // =============================================================================================
    // ===========================================================
    //    Define el comportamiento cuando se pause/despause el juego
    // ===========================================================
    public void pause(){}
    public void unPause(){}

    // ===========================================================
    //    Define el comportamiento cuando se presionan los botones del HUD
    // ===========================================================
    public void HUDButton1Pressed(){}
    public void HUDButton2Pressed(){}

    // =============================================================================================
    //                                M É T O D O S  A B S T R A C T O S
    // =============================================================================================

    public abstract void loadGFX();         // Cargar los recursos gráficos
    public abstract void loadMFX();         // Cargar los recursos de música
    public abstract void loadSFX();         // Cargar los recursos de sonido
    public abstract void createScene();     // Crear y adjuntar los elementos de la escena
    public abstract void onBackKeyPressed();// Define el comportamiento cuando se presione la tecla "Back"
    public abstract void destroyScene();    // Define cómo se debe liberar la escena y sus recursos

    // =============================================================================================
    //                                G E T T E R S  &  S E T T E R S
    // =============================================================================================
    // ============== REGRESA el TIPO DE ESCENA que es ===========
    public SceneType getSceneType() {return sceneType;}
}
