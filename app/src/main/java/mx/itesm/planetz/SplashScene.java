package mx.itesm.planetz;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.IBackground;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.adt.color.Color;

import java.io.IOException;

/**
 * Created by Diego on 03/10/2015.
 */
public class SplashScene extends BaseScene{

    //Sprites
    private SpriteBackground backgroundSprite;
    private Sprite logoSprite;

    public SplashScene(){
        super();
        sceneType = SceneType.SPLASH;
    }

    @Override
    public void loadGFX() {
        //Llamar al Administrador de Recursos
        resourceManager.loadSplashResourcesGFX();

        //Inicializar el Fondo
        backgroundSprite = new SpriteBackground(0,0,0,resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2,resourceManager.splashTextureRegion_background));
        //Inicializar el logo del ITESM
        logoSprite = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2,resourceManager.splashTextureRegion_logo);
    }

    @Override
    public void loadMFX(){
        resourceManager.loadSplashResourcesMFX();
    }

    @Override
    public void loadSFX() {

    }

    @Override
    public void createScene() {
        resourceManager.music.play();
        this.setBackground(backgroundSprite);
        attachChild(logoSprite);
    }

    @Override
    protected void onManagedUpdate(float pSecondsElapsed){
        super.onManagedUpdate(pSecondsElapsed);
    }

    @Override
    public void onBackKeyPressed() {
        destroyScene();
        gameManager.finish();
    }


    @Override
    public void destroyScene() {
        resourceManager.unloadSplashResources();
        this.detachSelf();
        this.dispose();
    }
}
