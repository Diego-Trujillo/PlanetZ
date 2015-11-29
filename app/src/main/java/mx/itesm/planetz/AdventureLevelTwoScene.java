package mx.itesm.planetz;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.UpdateHandlerList;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.modifier.IBackgroundModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;

import java.util.Random;

/**
 * Created by Diego on 16/11/2015.
 */
public class AdventureLevelTwoScene extends BaseScene{
    // =============================================================================================
    //                     D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================

    // ===========================================================
    //           Elementos del Motor de Física
    // ===========================================================
    // ============== El Contenedor del Mundo de Física ==========
    private PhysicsWorld physicsWorld;
    // -------------- Gravedad -----------------------------------
    private float GRAVITY_X = 0f;
    private float GRAVITY_Y = -100f;

    // -------------- Escucha los contactos ----------------------
    private ContactListener contactListener;

    // ===========================================================
    //            Cuerpos  en el motor de física
    // ===========================================================

    // ============== Definición de Fijadores de física ==========
    // -- Paredes laterales
    final FixtureDef WALL_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(0,0f,0f);
    // -- Nave
    final FixtureDef SHIP_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(1000.f,0.1f,0.5f);


    // ============== Paredes ====================================
    // -- Izquierda
    private Body leftWallBody;
    // -- Derecha
    private Body rightWallBody;
    // -- "Pared de la muerte"
    private Body wallOfDeathBody;

    // ===========================================================
    //                     Elementos Gráficos
    // ===========================================================
    // ================ Fondos ===================================
    private Sprite backgroundSkySprite;
    private Sprite backgroundRocks1Sprite;
    private Sprite backgroundRocks2Sprite;
    private Sprite backgroundRocks3Sprite;

    // ===========================================================
    //                     Elementos de Mecánicas
    // ===========================================================


    Random rand;
    private int i= 1;

    private Rectangle astronautRectangle;
    private Body astronautBody;

    private Astronaut player;
    private PlayerHUD sceneHUD;
    // =============================================================================================
    //                                    C O N S T R U C T O R
    // =============================================================================================
    public AdventureLevelTwoScene() {
        super();
        sceneType = SceneType.ADVENTURE_LEVEL_2;
        sessionManager.currentLevel= 2;
    }
    // =============================================================================================
    //                                       M É T O D O S
    // =============================================================================================

    // ===========================================================
    //                 Cargar recursos gráficos
    // ===========================================================

    @Override
    public void loadGFX() {
        resourceManager.loadAdventureLevelTwoResourcesGFX();
        sceneHUD = new PlayerHUD(this,2);



    }

    @Override
    public void loadMFX() {

    }

    @Override
    public void loadSFX() {

    }

    @Override
    public void createScene() {

        backgroundSkySprite = resourceManager.loadSprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2,resourceManager.adventureLevelTwoBackgroundSkyTextureRegion);
        backgroundRocks1Sprite = resourceManager.loadSprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2,resourceManager.adventureLevelTwoBackgroundRocks1TextureRegion);
        backgroundRocks2Sprite = resourceManager.loadSprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2,resourceManager.adventureLevelTwoBackgroundRocks2TextureRegion);
        backgroundRocks3Sprite = resourceManager.loadSprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2,resourceManager.adventureLevelTwoBackgroundRocks3TextureRegion);


        ParallaxBackground background = new AutoParallaxBackground((208f/255f),(162f/255f),(142f/255f),2f);

        this.setBackground(background);


        background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-4f, backgroundSkySprite));
        background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-22f, backgroundRocks1Sprite));
        background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-44f,backgroundRocks2Sprite));
        background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-88f, backgroundRocks3Sprite));


        physicsWorld = new PhysicsWorld(new Vector2(GRAVITY_X,GRAVITY_Y),true);

        this.registerUpdateHandler(physicsWorld);

        sceneHUD.attachToScene();

        createWalls();

        createAstronaut();
        rand = new Random();
        TimerHandler platformSpawner = new TimerHandler(1,true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                Platform platform = new Platform(getWorld(), physicsWorld,2,Platform.BIG,GameManager.CAMERA_WIDTH * i++ + 150,(int)(300*randomNumberGenerator.nextFloat() + 175),true);
                platform.attachToScene();
            }
        });


        this.registerUpdateHandler(platformSpawner);
        physicsWorld.setContactListener(getContactListener());

    }


    private void createWalls(){

        // ============== Crear los SpritesRectángulos ===============
        // -- Pared Izquierda
        final Rectangle leftWallRectangle = new Rectangle(GameManager.CAMERA_WIDTH/2,0,GameManager.CAMERA_WIDTH + 400,10, vertexBufferObjectManager);

        // -- Colorear ambos rectángulos de blanco
        leftWallRectangle.setColor(1f, 1f, 0f);


        // ============== Crear los cuerpos de física ===============
        leftWallBody = PhysicsFactory.createBoxBody(physicsWorld, leftWallRectangle, BodyDef.BodyType.KinematicBody, WALL_FIXTURE_DEFINITION);


        // ============== Registrar ID de cuerpo ====================
        leftWallBody.setUserData("wall");

        // ============== Conectar cuerpos de física a sprites ======
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(leftWallRectangle, leftWallBody));

        // ============== Adjuntar las paredes al mundo =============
        this.attachChild(leftWallRectangle);


        leftWallRectangle.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                //leftWallBody.setLinearVelocity(astronautBody.getLinearVelocity().x,0);
                leftWallBody.setTransform(player.astronautBody.getPosition().x, 0, 0);
            }

            @Override
            public void reset() {

            }
        });
    }


    private void createAstronaut(){
        player = new Astronaut(this, physicsWorld);
        player.attachToScene();

    }
    public BaseScene getWorld(){
        return this;
    }

    @Override
    public void pause(){
        this.setIgnoreUpdate(true);
    }

    @Override
    public void unPause(){
        this.setIgnoreUpdate(false);
    }

    @Override
    public void HUDButton1Pressed(){
        player.jump();
    }


    public ContactListener getContactListener(){
        ContactListener contactListener = new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                final Body bodyA = contact.getFixtureA().getBody();
                final Body bodyB = contact.getFixtureB().getBody();

                if((bodyA.getUserData() instanceof Astronaut && bodyB.getUserData() instanceof Platform) || (bodyB.getUserData() instanceof Astronaut && bodyA.getUserData() instanceof Platform)){
                    if(bodyA.getUserData() instanceof Astronaut) {((Astronaut) bodyA.getUserData()).animateRun();}
                    else{((Astronaut) bodyB.getUserData()).animateRun();}
                }

                else if((bodyA.getUserData() instanceof Astronaut && bodyB.getUserData() instanceof Obstacle) || (bodyB.getUserData() instanceof Astronaut && bodyA.getUserData() instanceof Obstacle)){
                    if(bodyA.getUserData() instanceof Astronaut) {
                        ((Obstacle)bodyB.getUserData()).deactivate();
                        ((Astronaut) bodyA.getUserData()).onDamage();
                    }
                    else{
                        ((Obstacle)bodyA.getUserData()).deactivate();
                        ((Astronaut) bodyB.getUserData()).onDamage();
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        };

        return contactListener;
    }



    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void destroyScene() {
        camera.setChaseEntity(null);
        camera.setCenter(GameManager.CAMERA_WIDTH / 2, GameManager.CAMERA_HEIGHT / 2);
        sceneHUD.destroy();
        resourceManager.unloadAdventureLevelTwoResources();
        this.detachChildren();
        this.detachSelf();
        this.dispose();
    }
}
