package mx.itesm.planetz;

import android.hardware.SensorManager;
import android.view.Gravity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.engine.handler.collision.CollisionHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.opengl.texture.region.ITextureRegion;

/**
 * Created by Diego on 22/10/2015.
 */
public class AdventureLevelOneScene extends BaseScene implements IAccelerationListener{

    // =============================================================================================
    //                     D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================

    // ===========================================================
    //           Elementos del Motor de Física
    // ===========================================================
    // ============== El Contenedor del Mundo de Física ==========
    private PhysicsWorld physicsWorld;

    // -------------- Escucha los contactos ----------------------
    private ContactListener contactListener;

    // ============== Definición de Fijadores de física ==========
    final FixtureDef WALL_FIXTURE = PhysicsFactory.createFixtureDef(0,0.1f,0.5f);

    // ============== Cuerpos ====================================
    // -------------- Paredes ------------------------------------
    private Body leftWallBody;
    private Body rightWallBody;
    private Body shipBody;

    // ===========================================================
    //              Elementos Gráficos
    // ===========================================================
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
        physicsWorld = new PhysicsWorld(new Vector2(0,-SensorManager.GRAVITY_EARTH),false);
        this.registerUpdateHandler(physicsWorld);


        naveSprite = new Sprite(100, GameManager.CAMERA_HEIGHT/2,naveRegion,vertexBufferObjectManager);
        this.attachChild(naveSprite);
        gameManager.getEngine().enableAccelerationSensor(gameManager, this);
        final Rectangle rekt = new Rectangle(0, 0, GameManager.CAMERA_WIDTH,10, vertexBufferObjectManager);
        rekt.setColor(1f,1f,1f);
        leftWallBody = PhysicsFactory.createBoxBody(physicsWorld,rekt, BodyDef.BodyType.StaticBody,WALL_FIXTURE);

        this.attachChild(rekt);
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
       /* if(naveSprite.getY() >= 0 && naveSprite.getY() <= GameManager.CAMERA_HEIGHT){
            naveSprite.setY(naveSprite.getY() + pAccelerationData.getY()*5);
        }
        else if(naveSprite.getY() > GameManager.CAMERA_HEIGHT){
            naveSprite.setY(GameManager.CAMERA_HEIGHT);
        }
        else {
            naveSprite.setY(0);
        }*/
        naveSprite.setY(naveSprite.getY() + pAccelerationData.getY());


    }
}
