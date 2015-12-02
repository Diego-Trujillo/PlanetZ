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

import java.util.ArrayList;
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
    final FixtureDef WOD_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(0,0f,0f,true);


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
    public int playerLives;
    private ArrayList<Obstacle> toBeDeleted_Obstacle;
    private ArrayList<Platform> toBeDeleted_Platform;

    private ArrayList<Platform> toBeAdded_Platform;

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

        toBeDeleted_Obstacle = new ArrayList<>();
        toBeDeleted_Platform = new ArrayList<>();


        physicsWorld = new PhysicsWorld(new Vector2(GRAVITY_X,GRAVITY_Y),true){
            @Override
            public void onUpdate(float pSecondsElapsed){
                super.onUpdate(pSecondsElapsed);
                // -- Para cada plataforma en la lista a ser borrado, llama el método para borrarse
                for(Platform platform : toBeDeleted_Platform){platform.destroy(); }
                // -- Para cada obstáculo en la lista a ser borrado, llama el método para borrarse
                for(Obstacle obstacle : toBeDeleted_Obstacle){obstacle.destroy();}

                // -- Limpia la lista de elementos a ser borrados
                toBeDeleted_Platform.clear();
                toBeDeleted_Obstacle.clear();

                // -- Llamamos al recolector de basura
                System.gc();
            }

        };

        this.registerUpdateHandler(physicsWorld);

        sceneHUD.attachToScene();

        playerLives = 3;


        createAstronaut();
        createWalls();


        rand = new Random();
        TimerHandler platformSpawner = new TimerHandler(1,true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                Platform platform = new Platform(getWorld(), physicsWorld,2,Platform.BIG,GameManager.CAMERA_WIDTH * i++ + 150,(int)(300*randomNumberGenerator.nextFloat() + 175),true);
                platform.attachToScene();
            }
        });


        //this.registerUpdateHandler(platformSpawner);
        spawnPlatforms();
        physicsWorld.setContactListener(getContactListener());

    }


    private void createWalls(){

        // ============== Crear los SpritesRectángulos ===============
        // -- Pared Izquierda
        final Rectangle leftWallRectangle = new Rectangle(GameManager.CAMERA_WIDTH/2,-500,GameManager.CAMERA_WIDTH + 400,10, vertexBufferObjectManager);
        // -- "Pared de la muerte", definimos un espacio para que los meteoritos que no impactan al jugador se borren del juego
        final Rectangle wallOfDeathRectangle = new Rectangle(-50,GameManager.CAMERA_HEIGHT/2,10,GameManager.CAMERA_HEIGHT*2,vertexBufferObjectManager);

        // -- Colorear ambos rectángulos de blanco
        leftWallRectangle.setColor(1f, 1f, 0f,0);
        wallOfDeathRectangle.setColor(1f,0.5f,0.25f,0);


        // ============== Crear los cuerpos de física ===============
        leftWallBody = PhysicsFactory.createBoxBody(physicsWorld, leftWallRectangle, BodyDef.BodyType.KinematicBody, WALL_FIXTURE_DEFINITION);
        wallOfDeathBody =  PhysicsFactory.createBoxBody(physicsWorld,wallOfDeathRectangle, BodyDef.BodyType.DynamicBody,WOD_FIXTURE_DEFINITION);

        // ============== Registrar ID de cuerpo ====================
        leftWallBody.setUserData("boundary");
        wallOfDeathBody.setUserData("wod");

        // ============== Conectar cuerpos de física a sprites ======
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(leftWallRectangle, leftWallBody,true,true));
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(wallOfDeathRectangle, wallOfDeathBody, true, true));

        // ============== Adjuntar las paredes al mundo =============
        this.attachChild(leftWallRectangle);
        this.attachChild(wallOfDeathRectangle);


        leftWallRectangle.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                leftWallBody.setTransform(player.astronautBody.getPosition().x, -25, 0);
            }

            @Override
            public void reset() {

            }
        });


        wallOfDeathRectangle.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                wallOfDeathBody.setTransform(player.astronautBody.getPosition().x - 100, player.astronautBody.getPosition().y, 0);

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

                    playerLives--;
                    sceneHUD.updateLives(playerLives);

                    if(playerLives == 0){
                        // -- Liberamos la escena actual
                        sceneManager.destroyScene(SceneType.ADVENTURE_LEVEL_2);
                        // -- Creamos la escena del primer nivel
                        sceneManager.createScene(SceneType.YOU_LOSE);
                        // -- Corremos la escena del primer nivel
                        sceneManager.setScene(SceneType.YOU_LOSE);

                    }

                    if(bodyA.getUserData() instanceof Astronaut) {
                        ((Obstacle)bodyB.getUserData()).deactivate();
                        ((Astronaut) bodyA.getUserData()).onDamage();
                    }
                    else{
                        ((Obstacle)bodyA.getUserData()).deactivate();
                        ((Astronaut) bodyB.getUserData()).onDamage();
                    }

                }
                else if(bodyA.getUserData().equals("wod")){
                    if(bodyB.getUserData() instanceof Platform){toBeDeleted_Platform.add((Platform)(bodyB.getUserData()));System.out.println("Wall of death takes yet another victim >:) A - Platform");}
                    else if(bodyB.getUserData() instanceof Obstacle){toBeDeleted_Obstacle.add((Obstacle) (bodyB.getUserData()));System.out.println("Wall of death takes yet another victim >:) A - Obstacle");}

                }
                else if(bodyB.getUserData().equals("wod")){
                    if(bodyA.getUserData() instanceof Platform){toBeDeleted_Platform.add((Platform)(bodyA.getUserData()));}
                    else if(bodyA.getUserData() instanceof Obstacle){toBeDeleted_Obstacle.add((Obstacle)(bodyA.getUserData()));}
                    System.out.println("Wall of death takes yet another victim >:) B");
                }
                else if((bodyA.getUserData() instanceof Astronaut && bodyB.getUserData().equals("boundary")) || (bodyB.getUserData() instanceof Astronaut && bodyA.getUserData().equals("boundary"))){
                    // -- Liberamos la escena actual
                    sceneManager.destroyScene(SceneType.ADVENTURE_LEVEL_2);
                    // -- Creamos la escena del primer nivel
                    sceneManager.createScene(SceneType.YOU_LOSE);
                    // -- Corremos la escena del primer nivel
                    sceneManager.setScene(SceneType.YOU_LOSE);
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


    public void spawnPlatforms(){
        toBeAdded_Platform = new ArrayList<>();
        // Agregamos las plataformas
        int i = 900;
        int j = 3;
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,0,(200),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,600,(200),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,1200,(200),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,1800,(200),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,2400,(200),true));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,3000,(200),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(200),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(400),true));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(400),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(200),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(400),true));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(600),true));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(400),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(600),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(600),true));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(800),true));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(600),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(200),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(400),true));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(400),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(200),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(400),true));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(600),true));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(600),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(800),true));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(400),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(200),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(400),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(600),true));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(200),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(400),true));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(400),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(600),true));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(800),true));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(400),false));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(400),true));
        toBeAdded_Platform.add(new Platform(getWorld(), physicsWorld,2,Platform.BIG,i*j++,(400),false));














        for(Platform plat:toBeAdded_Platform){
            plat.attachToScene();
        }

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
