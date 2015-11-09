package mx.itesm.planetz;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.IBackground;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ClickDetector;
import org.andengine.input.touch.detector.ScrollDetector;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.color.Color;

import java.io.IOException;

/**
 * Created by Diego on 03/10/2015.
 */
public class SplashScene extends BaseScene{

    // =============================================================================================
    //                          D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================
    // ===========================================================
    //                     Sprites
    // ===========================================================
    // ============== Fondo ======================================
    private SpriteBackground backgroundSprite;

    // ============== Logotipo ITESM =============================
    private Sprite logoSprite;

    // =============================================================================================
    //                                      C O N S T R U C T O R
    // =============================================================================================
    public SplashScene(){
        // ============== Iniciamos la super-clase ===============
        super();
        // ============== Asignamos el tipo de escena ============
        sceneType = SceneType.SPLASH;
    }

    // =============================================================================================
    //                                         M É T O D O S
    // =============================================================================================
    // ===========================================================
    //              Cargamos los gráficos de la escena
    // ===========================================================
    @Override
    public void loadGFX() {
        // ============== Llamamos al Adm. de Recursos ===========
        resourceManager.loadSplashResourcesGFX();

        // ============== Cargamos el fondo =====================
        backgroundSprite = new SpriteBackground(0,0,0,resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2,resourceManager.splashTextureRegion_background));

        // ============== Cargamos el logotipo de ITESM =========
        logoSprite = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2,resourceManager.splashTextureRegion_logo);
    }
    // ===========================================================
    //              Cargamos la música de la escena
    // ===========================================================
    @Override
    public void loadMFX(){
        resourceManager.setMusic("Splash.ogg");
    }

    // ===========================================================
    //         Cargamos los sonidos de la escena (No utilizado)
    // ===========================================================
    @Override
    public void loadSFX() {return;}

    // ===========================================================
    //                     Creamos la escena
    // ===========================================================
    @Override
    public void createScene() {
        // ============== Adjuntamos el fondo de la escena =======
        this.setBackground(backgroundSprite);

        // ============== Adjuntamos el logotipo a la escena =====
        attachChild(logoSprite);

        // ============== Reproducimos el sonido de Bienvenida ===
        resourceManager.backgroundMusic.play();
        resourceManager.updateAudioVolume();
    }
    // ===========================================================
    //                Actualización de pantalla
    // ===========================================================
    @Override
    protected void onManagedUpdate(float pSecondsElapsed){
        super.onManagedUpdate(pSecondsElapsed);
    }

    // ===========================================================
    //   Define el comportamiento al presionarse la tecla de Back
    // ===========================================================
    @Override
    public void onBackKeyPressed() {
        // ============== Liberamos los recursos de la escena ====
        destroyScene();

        // ============== Terminamos el juego ====================
        gameManager.finish();
    }

    // ===========================================================
    //   Define cómo se debe liberar la escena y los recursos
    // ===========================================================
    @Override
    public void destroyScene() {
        resourceManager.unloadSplashResources();
        this.unregisterTouchArea(this);
        this.detachSelf();
        this.dispose();
    }

}
