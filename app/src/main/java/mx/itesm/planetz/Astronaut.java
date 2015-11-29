package mx.itesm.planetz;

import android.view.animation.AnimationUtils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimationData;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import java.util.Random;

/**
 * Created by Diego on 28/11/2015.
 */
public class Astronaut {

    // =============================================================================================
    //                     D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================
    // ===========================================================
    //                 Elementos generales
    // ===========================================================
    // -------------- La escena del juego ------------------------
    private BaseScene gameScene;

    // ===========================================================
    //                 Elementos gráficos
    // ===========================================================
    private BitmapTextureAtlas astronautBitmapTextureAtlas;
    private ITiledTextureRegion astronautTextureRegion;
    public AnimatedSprite astronautSprite;
    private Entity objectOfDesire;

    // ===========================================================
    //                 Elementos de Física
    // ===========================================================
    public Body astronautBody;
    private final FixtureDef ASTRONAUT_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(1000.f,0.1f,0.5f);


    // ===========================================================
    //                 Elementos de Mecanicas
    // ===========================================================
    public int jumpCounter;

    // ===========================================================
    //                 Elementos de Miscelaneos
    // ===========================================================
    public long[] walkingAnimationIntervals = {50,50,50,50,50,50,50,50};
    public long[] jumpingAnimationIntervals = {75,75,100,500};
    // =============================================================================================
    //                                    C O N S T R U C T O R
    // =============================================================================================
    public Astronaut(BaseScene gameScene, PhysicsWorld physicsWorld){
        this.gameScene = gameScene;

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/BraveNewWorld/");
        astronautBitmapTextureAtlas = new BitmapTextureAtlas(gameScene.resourceManager.textureManager,688,387, TextureOptions.BILINEAR);
        astronautTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(astronautBitmapTextureAtlas,gameScene.gameManager,"AstronautSprites.png",0,0,8,3);

        astronautBitmapTextureAtlas.load();
        astronautSprite = new AnimatedSprite(GameManager.CAMERA_WIDTH/2, GameManager.CAMERA_HEIGHT/2,astronautTextureRegion,gameScene.vertexBufferObjectManager);

        objectOfDesire = new Entity();
        astronautSprite.attachChild(objectOfDesire);
        objectOfDesire.setPosition(300,150);





        astronautBody = PhysicsFactory.createBoxBody(physicsWorld,astronautSprite, BodyDef.BodyType.DynamicBody,ASTRONAUT_FIXTURE_DEFINITION);
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(astronautSprite, astronautBody, true, true));
        astronautBody.setFixedRotation(true);

        astronautBody.setUserData(this);

    }
    // =============================================================================================
    //                                       M É T O D O S
    // =============================================================================================
    public void attachToScene(){
        gameScene.attachChild(astronautSprite);

        astronautSprite.animate(walkingAnimationIntervals, 0, 7, true);

        gameScene.camera.setChaseEntity(objectOfDesire);






        astronautSprite.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                astronautBody.setLinearVelocity(25f, astronautBody.getLinearVelocity().y);
            }

            @Override
            public void reset() {

            }
        });


    }

    public void jump() {
        if(jumpCounter++ < 2) {
            astronautBody.setLinearVelocity(0, 37.5f);

            astronautSprite.stopAnimation();
            astronautSprite.animate(jumpingAnimationIntervals, 16, 19, false);
        }
    }

    public void animateRun(){
        if( !astronautSprite.isAnimationRunning() ){
            astronautSprite.animate(walkingAnimationIntervals,0,7,true);
            resetJumpCounter();
        }
    }

    public void resetJumpCounter(){
        jumpCounter = 0;
    }

    public void animateJump(){
        astronautSprite.stopAnimation();
        astronautSprite.animate(jumpingAnimationIntervals, 16, 19, false);
    }

}

