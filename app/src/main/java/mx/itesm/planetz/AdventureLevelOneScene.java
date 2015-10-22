package mx.itesm.planetz;

import org.andengine.engine.handler.collision.CollisionHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by Diego on 22/10/2015.
 */
public class AdventureLevelOneScene extends BaseScene implements IAccelerationListener{

    // Cosas Temporales
    ITextureRegion naveRegion;
    Sprite naveSprite;


    public AdventureLevelOneScene(){
        super();
        sceneType = SceneType.ADVENTURE_LEVEL_1;

    }


    @Override
    public void loadGFX() {
        naveRegion = resourceManager.loadImage("gfx/galaxia_play.png");
    }

    @Override
    public void loadMFX() {

    }

    @Override
    public void loadSFX() {

    }

    @Override
    public void createScene() {
        naveSprite = new Sprite(100, GameManager.CAMERA_HEIGHT/2,naveRegion,vertexBufferObjectManager);
        this.attachChild(naveSprite);
        gameManager.getEngine().enableAccelerationSensor(gameManager, this);

    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void destroyScene() {

    }

    @Override
    public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {

    }

    @Override
    public void onAccelerationChanged(AccelerationData pAccelerationData) {
        // naveSprite.setX(naveSprite.getX() + pAccelerationData.getX());
        naveSprite.setY(naveSprite.getY() + pAccelerationData.getY());
    }
}
