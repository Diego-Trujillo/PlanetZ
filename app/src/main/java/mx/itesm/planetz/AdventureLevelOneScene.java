package mx.itesm.planetz;

import android.hardware.SensorManager;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.Gravity;

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
import org.andengine.engine.handler.collision.CollisionHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObject;
import org.andengine.util.SocketUtils;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

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
    // -------------- Gravedad -----------------------------------
    private float GRAVITY_X = 0;
    private float GRAVITY_Y = 0;

    // -------------- Escucha los contactos ----------------------
    private ContactListener contactListener;

    //----------------Tags objetos-------------------------
    final int TAG_SHIP= 1;
    final int TAG_METEORS = 2;
    final int TAG_GEMS =3;

    // ============== Definición de Fijadores de física ==========
    final FixtureDef WALL_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(0,0f,0f);
    final FixtureDef SHIP_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(50.f,0.1f,0.5f);
    final FixtureDef METEORE_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(1f,0.9f,0.4f);

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
    //Regiones meteoritos
    ArrayList<ITextureRegion> meteorsArrayList;
    ITextureRegion meteoreRegion1;
    ITextureRegion meteoreRegion2;
    ITextureRegion meteoreRegion3;
    //Sprite meteor;
    //Body meteorite;

    float spawnVelocity;
    int timesExecuted;

    boolean movementEnabled;

    Random rand;
    // =============================================================================================
    //                                    C O N S T R U C T O R
    // =============================================================================================
    public AdventureLevelOneScene(){
        super();
        sceneType = SceneType.ADVENTURE_LEVEL_1;
        movementEnabled = false;

    }


    @Override
    public void loadGFX() {
        resourceManager.loadAdventureLevelOneResourcesGFX();

        naveRegion= resourceManager.loadImage("gfx/Level1/Meteors/prueba2.png");

    }

    @Override
    public void loadMFX() {

    }

    @Override
    public void loadSFX() {

    }

    @Override
    public void createScene() {
        physicsWorld = new PhysicsWorld(new Vector2(GRAVITY_X,GRAVITY_Y),false);

        this.registerUpdateHandler(physicsWorld);
        rand = new Random();

        createWalls();


        naveSprite = new Sprite(100, GameManager.CAMERA_HEIGHT/2,naveRegion,vertexBufferObjectManager);


        gameManager.getEngine().enableAccelerationSensor(gameManager, this);

        shipBody = PhysicsFactory.createCircleBody(physicsWorld, naveSprite, BodyDef.BodyType.DynamicBody, SHIP_FIXTURE_DEFINITION);


        physicsWorld.registerPhysicsConnector(new PhysicsConnector(naveSprite, shipBody, true, false));

        this.attachChild(naveSprite);

        naveSprite.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                shipBody.applyForce(-physicsWorld.getGravity().x * shipBody.getMass(), 0, shipBody.getWorldCenter().x, shipBody.getWorldCenter().y);
            }

            @Override
            public void reset() {

            }
        });

        spawnVelocity = 3f;
        timesExecuted = 0;

        movementEnabled = true;

        final TimerHandler timerHandler = new TimerHandler(4f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                createMeteorite();
                timesExecuted++;
                if(timesExecuted > 5){
                    spawnVelocity = 1.5f;
                    pTimerHandler.setTimerSeconds(spawnVelocity);
                }
                System.out.println("Meteor Created!");
                System.out.println("-- timesExecuted: "+timesExecuted+"  spawnVelocity:"+spawnVelocity);
            }
        });

        this.registerUpdateHandler(timerHandler);

    }

    private void createWalls(){
        // ============== Crear los SpritesRectángulos ===============
        // -- Pared Izquierda
        final Rectangle leftWallRectangle = new Rectangle(GameManager.CAMERA_WIDTH/2,0,GameManager.CAMERA_WIDTH,10, vertexBufferObjectManager);
        // -- Pared Derecha
        final Rectangle rightWallRectangle = new Rectangle(GameManager.CAMERA_WIDTH/2, GameManager.CAMERA_HEIGHT-10,GameManager.CAMERA_WIDTH,10, vertexBufferObjectManager);
        // -- Colorear ambos rectángulos de blanco
        leftWallRectangle.setColor(1f, 1f, 1f);
        rightWallRectangle.setColor(1f, 1f, 1f);
        // ============== Crear los cuerpos de física ===============
        leftWallBody = PhysicsFactory.createBoxBody(physicsWorld, leftWallRectangle, BodyDef.BodyType.StaticBody, WALL_FIXTURE_DEFINITION);
        rightWallBody = PhysicsFactory.createBoxBody(physicsWorld, rightWallRectangle, BodyDef.BodyType.StaticBody, WALL_FIXTURE_DEFINITION);

        // ============== Conectar cuerpos de física a sprites ======
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(leftWallRectangle, leftWallBody));
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(rightWallRectangle, rightWallBody));

        // ============== Adjuntar las paredes al mundo =============
        this.attachChild(leftWallRectangle);
        this.attachChild(rightWallRectangle);
    }

    private void createMeteorite(){
        int textureRegionChosen = rand.nextInt(resourceManager.adventureLevelOneMeteoriteTextureRegions.size());

        float downPosition = rand.nextFloat()*(GameManager.CAMERA_HEIGHT - 20) + 10;
        Sprite meteor;
        Body meteorite;
        meteor = new Sprite(GameManager.CAMERA_WIDTH,GameManager.CAMERA_HEIGHT/2,resourceManager.adventureLevelOneMeteoriteTextureRegions.get(textureRegionChosen), vertexBufferObjectManager);

        meteorite = PhysicsFactory.createCircleBody(physicsWorld, meteor, BodyDef.BodyType.DynamicBody, METEORE_FIXTURE_DEFINITION);
        meteorite.setUserData("meteorite");
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(meteor, meteorite, true, true));

        //meteorite.setTransform(GameManager.CAMERA_WIDTH,GameManager.CAMERA_HEIGHT/2,0);
        meteor.registerEntityModifier(new LoopEntityModifier(new RotationModifier(5.0f, 0, 360)));

        this.attachChild(meteor);
        float randFlot = rand.nextFloat() + 0.10f;
        int magnitudeMultiplier = 500;
        int sign = (rand.nextInt(2) == 0)?1:-1;
        System.out.println("SPAWNED. Yvel = "+randFlot*magnitudeMultiplier*sign);
        meteorite.setLinearVelocity(-9f,0);
        meteorite.applyForce(randFlot*magnitudeMultiplier*randFlot,randFlot*magnitudeMultiplier*sign*meteorite.getMass(),meteorite.getWorldCenter().x,meteorite.getWorldCenter().y);
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
        if(movementEnabled) {
            shipBody.setLinearVelocity(0, pAccelerationData.getY() * 4);
        }
    }

    private ContactListener createContactListener() {
        contactListener = new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                {
                    try {
                        Fixture x1 = null;
                        Fixture x2 = null;
                        //Sprite x =null;
                        x1 = contact.getFixtureA();
                        x2 = contact.getFixtureB();
                        Object x1Object = x1.getBody().getUserData();
                        Object x2Object = x2.getBody().getUserData();

                        Sprite x1Sprite= (Sprite)x1Object;
                        Sprite x2Sprite= (Sprite)x1Object;


                        if (x1Sprite.getTag()==1 && x2Sprite.getTag()==2  ) {
                            //x1.getBody().setFixedRotation(true);
                            //sound.play();
                            //mEngine.vibrate(100);
                            Log.i("CONTACT", "BETWEEN SHIP AND METEORE!");
                        }
                    } catch (Exception e) {
                        Log.d("ErrorMessage", e.getMessage());
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

            }};
        return contactListener;
    }

}
