package mx.itesm.planetz;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;

/**
 * Created by Diego on 01/12/2015.
 */
public class Goal {
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
    public Sprite goalSprite;

    // ===========================================================
    //                 Elementos de Física
    // ===========================================================
    // -------------- El mundo de física ------------------------
    private PhysicsWorld physicsWorld;
    // -------------- Cuerpo -------------------------------------
    public Body goalBody;
    // -------------- Conector Sprite-Cuerpo ---------------------
    private PhysicsConnector physicsConnector;
    // -------------- Definición Fixture -------------------------
    private static final FixtureDef GOAL_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(0, 0, 0);


    // ===========================================================
    //                Opciones de Spawn
    // ===========================================================



    // =============================================================================================
    //                                    C O N S T R U C T O R
    // =============================================================================================

    public Goal(BaseScene gameScene,PhysicsWorld physicsWorld,int positionX, int positionY){
        // ========== Inicializamos objetos referenciales ===========
        this.gameScene = gameScene;
        this.physicsWorld = physicsWorld;
        this.resourceManager = gameScene.resourceManager;



        goalSprite = resourceManager.loadSprite(positionX,positionY,resourceManager.loadImage("Graphics/BraveNewWorld/Goal.png"));

        goalBody = PhysicsFactory.createBoxBody(physicsWorld,goalSprite, BodyDef.BodyType.KinematicBody,GOAL_FIXTURE_DEFINITION);

        physicsConnector = new PhysicsConnector(goalSprite,goalBody,true,true);

        physicsWorld.registerPhysicsConnector(physicsConnector);




        //obstacleSprite.setCullingEnabled(true);

        goalBody.setUserData(this);
    }


    public void attachToScene(){gameScene.attachChild(goalSprite); }


    public void deactivate(){goalBody.setActive(false);}

    // ===========================================================
    //        Destruimos el obstáculo
    // ===========================================================
    public void destroy(){
        // -- Desregistramos el conector de física entre cuerpo-sprite
        physicsWorld.unregisterPhysicsConnector(physicsConnector);
        // -- Deshabilitamos al cuerpo
        goalBody.setActive(false);
        // -- Destruimos el cuerpo
        physicsWorld.destroyBody(this.goalBody);
        // -- Removemos el sprite de la escena
        gameScene.detachChild(this.goalSprite);
    }


}
