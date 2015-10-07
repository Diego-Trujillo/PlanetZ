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

    //Dimensiones de la cámara para el dispositivo
    public static final int CAMERA_WIDTH = 1280;
    public static final int CAMERA_HEIGHT = 720;

    //Se declaran distintos administradores que utilizará el Juego
    SceneManager sceneManager;
    ResourceManager resourceManager;

    //Inicializamos la cámara
    protected Camera camera;

    @Override
    public EngineOptions onCreateEngineOptions() {
        //Inicializamos la cámara
        camera = new Camera(0,0,CAMERA_WIDTH,CAMERA_HEIGHT);

        //Inicializar las opciones del motor
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,new FillResolutionPolicy(), camera );

        //Declaramos opciones adicionals del motor
        engineOptions.getAudioOptions().setNeedsMusic(true); //Declaramos que el juego usará música
        engineOptions.getAudioOptions().setNeedsSound(true); //Declaramos que el juego usará sfx
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON); //Declaramos que no se apague la pantalla

        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException {
        //Inicializamos el Administrador de Escenas
        SceneManager.initialize(this);
        sceneManager = SceneManager.getInstance();

        //Inicializamos el Administrador de Recursos
        ResourceManager.initialize(this);
        resourceManager = ResourceManager.getInstance();

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws IOException {
        //Iniciamos con el Splash
        sceneManager.createScene(SceneType.SPLASH);
        sceneManager.setScene(SceneType.SPLASH);
        sceneManager.createScene(SceneType.MENU);

        pOnCreateSceneCallback.onCreateSceneFinished(sceneManager.getCurrentScene());
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {


        mEngine.registerUpdateHandler(new TimerHandler(2,
                new ITimerCallback() {
                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {
                        mEngine.unregisterUpdateHandler(pTimerHandler);
                        sceneManager.setScene(SceneType.MENU);
                        sceneManager.destroyScene(SceneType.SPLASH);
                    }
                }));

        pOnPopulateSceneCallback.onPopulateSceneFinished();

    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(sceneManager.getCurrentSceneType() == SceneType.MENU){
                finish();
            }
            else{
                sceneManager.getCurrentScene().onBackKeyPressed();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(sceneManager != null){
            System.exit(0);
        }
    }

}
