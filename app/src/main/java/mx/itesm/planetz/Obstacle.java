package mx.itesm.planetz;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;

/**
 * Created by Diego on 29/11/2015.
 */
public class Obstacle {
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
    public Sprite obstacleSprite;

    // ===========================================================
    //                 Elementos de Física
    // ===========================================================
    // -------------- El mundo de física ------------------------
    private PhysicsWorld physicsWorld;
    // -------------- Cuerpo -------------------------------------
    public Body obstacleBody;
    // -------------- Conector Sprite-Cuerpo ---------------------
    private PhysicsConnector physicsConnector;
    // -------------- Definición Fixture -------------------------
    private static final FixtureDef OBSTACLE_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(0, 0, 0);


    // ===========================================================
    //                Opciones de Spawn
    // ===========================================================



    // =============================================================================================
    //                                    C O N S T R U C T O R
    // =============================================================================================

    public Obstacle(BaseScene gameScene,PhysicsWorld physicsWorld,Platform platform,int offsetX){
        // ========== Inicializamos objetos referenciales ===========
        this.gameScene = gameScene;
        this.physicsWorld = physicsWorld;
        this.resourceManager = gameScene.resourceManager;

        int positionX = (int)(platform.platformSprite.getX() - platform.platformSprite.getWidth()/2 + offsetX);
        int positionY = (int)(platform.platformSprite.getY() + platform.platformSprite.getHeight());

        switch(platform.currentLevel){
            case 2:
                obstacleSprite = resourceManager.loadSprite(positionX,positionY,resourceManager.adventureLevelTwoObstaclesTextureRegion.get(gameScene.randomNumberGenerator.nextInt(3)));
                break;
        }



        obstacleBody = PhysicsFactory.createBoxBody(physicsWorld,obstacleSprite, BodyDef.BodyType.KinematicBody,OBSTACLE_FIXTURE_DEFINITION);

        physicsConnector = new PhysicsConnector(obstacleSprite,obstacleBody,true,true);

        physicsWorld.registerPhysicsConnector(physicsConnector);




        obstacleSprite.setCullingEnabled(true);

        obstacleBody.setUserData(this);
    }


    public void attachToScene(){gameScene.attachChild(obstacleSprite); }


    public void deactivate(){obstacleBody.setActive(false);}

    // ===========================================================
    //        Destruimos el obstáculo
    // ===========================================================
    public void destroy(){
        // -- Desregistramos el conector de física entre cuerpo-sprite
        physicsWorld.unregisterPhysicsConnector(physicsConnector);
        // -- Deshabilitamos al cuerpo
        obstacleBody.setActive(false);
        // -- Destruimos el cuerpo
        physicsWorld.destroyBody(this.obstacleBody);
        // -- Removemos el sprite de la escena
        gameScene.detachChild(this.obstacleSprite);
    }


}
