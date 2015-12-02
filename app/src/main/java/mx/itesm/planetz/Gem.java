package mx.itesm.planetz;

import android.widget.Toast;

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
public class Gem {
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
    public Sprite gemSprite;

    // ===========================================================
    //                 Elementos de Física
    // ===========================================================
    // -------------- El mundo de física ------------------------
    private PhysicsWorld physicsWorld;
    // -------------- Cuerpo -------------------------------------
    public Body gemBody;
    // -------------- Conector Sprite-Cuerpo ---------------------
    private PhysicsConnector physicsConnector;
    // -------------- Definición Fixture -------------------------
    private static final FixtureDef GEM_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(3f,0.5f, 0);


    // ===========================================================
    //                Elementos de mecánica
    // ===========================================================
    int referenceLevel;
    int numberOfGem;


    // =============================================================================================
    //                                    C O N S T R U C T O R
    // =============================================================================================

    public Gem(BaseScene gameScene,PhysicsWorld physicsWorld,int level,int gem,int positionX, int positionY){
        // ========== Inicializamos objetos referenciales ===========
        this.gameScene = gameScene;
        this.physicsWorld = physicsWorld;
        this.resourceManager = gameScene.resourceManager;

        referenceLevel = level;
        numberOfGem = gem;

        String filename = "Graphics/LevelGems/Gem";
        switch(referenceLevel){
            case 1:
                filename += "Blue";
                break;
            case 2:
                filename += "Pink";
                break;
            case 3:
                filename += "Yellow";
                break;
        }

        switch(numberOfGem){
            case 1:
                filename += "1";
                break;
            case 2:
                filename += "2";
                break;
            case 3:
                filename += "3";
                break;
        }

        filename += ".png";

        gemSprite = resourceManager.loadSprite(positionX,positionY,resourceManager.loadImage(filename));

        gemBody = PhysicsFactory.createBoxBody(physicsWorld, gemSprite, BodyDef.BodyType.KinematicBody, GEM_FIXTURE_DEFINITION);

        physicsConnector = new PhysicsConnector(gemSprite,gemBody,true,true);

        physicsWorld.registerPhysicsConnector(physicsConnector);




        //obstacleSprite.setCullingEnabled(true);

        gemBody.setUserData(this);
    }


    public void attachToScene(){gameScene.attachChild(gemSprite); }


    public void collect(){
        gemBody.setActive(false);
        gemSprite.setVisible(false);
        gameScene.detachChild(gemSprite);


        if(gameScene.sessionManager.gemsUnlocked[referenceLevel][numberOfGem] != true){gameScene.gameManager.toastOnUiThread("New Gem collected!", Toast.LENGTH_LONG);}
        gameScene.sessionManager.gemsUnlocked[referenceLevel][numberOfGem] =  true;
        gameScene.sessionManager.writeChanges();

    }

    // ===========================================================
    //        Destruimos el obstáculo
    // ===========================================================
    public void destroy(){
        // -- Desregistramos el conector de física entre cuerpo-sprite
        physicsWorld.unregisterPhysicsConnector(physicsConnector);
        // -- Deshabilitamos al cuerpo
        gemBody.setActive(false);
        // -- Destruimos el cuerpo
        physicsWorld.destroyBody(this.gemBody);
        // -- Removemos el sprite de la escena
        gameScene.detachChild(this.gemSprite);
    }


}
