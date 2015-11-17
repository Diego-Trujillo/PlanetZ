package mx.itesm.planetz;

import android.view.KeyEvent;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.CameraFactory;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.activity.BaseGameActivity;

import java.io.IOException;

/**
 * Created by Diego on 03/10/2015.
 */
public class GameManager extends BaseGameActivity {

    // =============================================================================================
    //                          D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================

    // ===========================================================
    //               Cámara principal del juego
    // ===========================================================
    // ============== Objeto Cámara ==============================
    protected Camera camera;

    // ============== Medidas de la cámara ========================
    public static final int CAMERA_WIDTH = 1280;
    public static final int CAMERA_HEIGHT = 720;

    // ===========================================================
    //           Referencias a los demás elementos del juego
    // ===========================================================
    SceneManager sceneManager;      // Administrador de Escenas
    ResourceManager resourceManager;// Administrador de Recursos
    SessionManager sessionManager;  // Administrador de Sesión/Progreso


    // =============================================================================================
    //                                         M É T O D O S
    // =============================================================================================

    // ===========================================================
    //      Inicializa las opciones del motor para este juego
    // ===========================================================
    @Override
    public EngineOptions onCreateEngineOptions() {
        // ============== Inicializamos la cámara principal ======
        camera = new Camera(0,0,CAMERA_WIDTH,CAMERA_HEIGHT);

        // ============== Creamos las opciones del motor =========
        /* -- Orientación Landscape no modificable
        *  -- En Resolución distinta llenar la pantalla
        *  -- Cámara principal
        */
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,new FillResolutionPolicy(), camera );

        // ============== Declaramos las opciones adicionales ===
        /* -- Necesitamos Música
        *  -- Necesitamos Sonido
        *  -- Necesitamos que la pantalla no se apague
        */
        engineOptions.getAudioOptions().setNeedsMusic(true); //Declaramos que el juego usará música
        engineOptions.getAudioOptions().setNeedsSound(true); //Declaramos que el juego usará sfx
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON); //Declaramos que no se apague la pantalla
        engineOptions.getTouchOptions().setNeedsMultiTouch(true);
        return engineOptions;
    }

    // ===========================================================
    //    Inicializamos las clases que administran los recursos
    // ===========================================================
    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        // ============== Administrador de Escenas ===============
        SceneManager.initialize(this);
        sceneManager = SceneManager.getInstance();

        // ============== Administrador de Recursos ==============
        ResourceManager.initialize(this);
        resourceManager = ResourceManager.getInstance();

        // ============== Administrador de Progreso ==============
        SessionManager.initialize(this);
        sessionManager = SessionManager.getInstance();

        // ============== Terminar ===============================
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    // ===========================================================
    //    Creamos las escenas necesarias para iniciar el juego
    // ===========================================================
    // -- En ese caso necesitamos la pantalla de splash y el menú
    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        // ============== Obtenemos la configuración de AFX ======
        resourceManager.updateAudioVolume();


        // ============== Cargamos y corremos el Splash ==========
        sceneManager.createScene(SceneType.SPLASH);
        sceneManager.setScene(SceneType.SPLASH);


        pOnCreateSceneCallback.onCreateSceneFinished(sceneManager.getCurrentScene());
    }

    // ===========================================================
    //     Asignamos y corremos la escena del menú
    // ===========================================================
    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {


        // ============== Corre el menú después de 2 segundos ====
        mEngine.registerUpdateHandler(new TimerHandler(2,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        mEngine.unregisterUpdateHandler(pTimerHandler);
                        sceneManager.destroyScene(SceneType.SPLASH);
                        sceneManager.createScene(SceneType.MENU);
                        sceneManager.setScene(SceneType.MENU);
                    }
                }));

        pOnPopulateSceneCallback.onPopulateSceneFinished();

    }

    // ===========================================================
    //   Define qué hacer cuando se presiona la tecla "Back"
    // ===========================================================
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            sceneManager.getCurrentScene().onBackKeyPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // ===========================================================
    //     Define qué hacer cuando se despausa el juego
    // ===========================================================
    @Override
    public synchronized void onResumeGame() {
        super.onResumeGame();
        if(resourceManager.backgroundMusic != null && !resourceManager.backgroundMusic.isPlaying()){
            resourceManager.backgroundMusic.play();
        }
    }
    // ===========================================================
    //       Define qué hacer cuando se pausa el juego
    // ===========================================================
    @Override
    public synchronized void onPauseGame() {
        if(resourceManager.backgroundMusic != null && resourceManager.backgroundMusic.isPlaying()){
            resourceManager.backgroundMusic.pause();
        }
        super.onPauseGame();
    }

    // ===========================================================
    //          Función para salir de la aplicación
    // ===========================================================
    public void quit(){
        resourceManager.releaseAudio();
        finish();
        System.exit(0);
    }

    // ===========================================================
    //  Define el comportamiento cuando el juego está terminando
    // ===========================================================
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(sceneManager != null){
            System.exit(0);
        }
    }

}
