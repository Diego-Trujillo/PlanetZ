package mx.itesm.planetz;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

/**
 * Created by Diego on 28/11/2015.
 */
public class Platform {
    // =============================================================================================
    //                     D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================
    // ===========================================================
    //                 Elementos generales
    // ===========================================================
    // -------------- La escena del juego ------------------------
    private BaseScene gameScene;
    // -------------- El Adm. de Recursos ------------------------
    private ResourceManager resourceManager;


    // ===========================================================
    //                 Elementos gráficos
    // ===========================================================
    public Sprite platformSprite;

    // ===========================================================
    //                 Elementos de Física
    // ===========================================================
    // -------------- El mundo de física ------------------------
    private PhysicsWorld physicsWorld;
    // -------------- Cuerpo -------------------------------------
    public Body platformBody;
    // -------------- Conector Sprite-Cuerpo ---------------------
    private PhysicsConnector physicsConnector;
    // -------------- Definición Fixture -------------------------
    private static final FixtureDef PLATFORM_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(0, 0, 0);


    // ===========================================================
    //                Opciones de Spawn
    // ===========================================================
    public static final int BIG = 0;
    public static final int SMALL = 1;

    // ===========================================================
    //                Opciones de Spawn
    // ===========================================================
    public int currentLevel;


    // =============================================================================================
    //                                    C O N S T R U C T O R
    // =============================================================================================

    public Platform(BaseScene gameScene,PhysicsWorld physicsWorld,int currentLevel, int size,int positionX, int positionY, boolean containsObstacle){
        // ========== Inicializamos objetos referenciales ===========
        this.gameScene = gameScene;
        this.physicsWorld = physicsWorld;
        this.resourceManager = gameScene.resourceManager;
        this.currentLevel = currentLevel;

        if(size == BIG){
            switch(currentLevel){
                case 2:
                    platformSprite = resourceManager.loadSprite(positionX,positionY,resourceManager.adventureLevelTwoPlatformsBigTextureRegion.get(gameScene.randomNumberGenerator.nextInt(3)));
                    break;
            }
        }


        platformBody = PhysicsFactory.createBoxBody(physicsWorld,platformSprite, BodyDef.BodyType.KinematicBody,PLATFORM_FIXTURE_DEFINITION);

        physicsConnector = new PhysicsConnector(platformSprite,platformBody,true,false);

        physicsWorld.registerPhysicsConnector(physicsConnector);




        platformSprite.setCullingEnabled(true);

        platformBody.setUserData(this);


        if(containsObstacle){
            Obstacle obstacle = new Obstacle(gameScene,physicsWorld,this,500);
            obstacle.attachToScene();
        }
    }


    public void attachToScene(){
        gameScene.attachChild(platformSprite);
    }


    // ===========================================================
    //        Destruimos la plataforma
    // ===========================================================
    public void destroy(){
        // -- Desregistramos el conector de física entre cuerpo-sprite
        physicsWorld.unregisterPhysicsConnector(physicsConnector);
        // -- Deshabilitamos al cuerpo
        platformBody.setActive(false);
        // -- Destruimos el cuerpo
        physicsWorld.destroyBody(this.platformBody);
        // -- Removemos el sprite de la escena
        gameScene.detachChild(this.platformSprite);
    }


}
