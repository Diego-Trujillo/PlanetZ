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
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
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
    private float GRAVITY_X = 4f;
    private float GRAVITY_Y = 0;

    // -------------- Escucha los contactos ----------------------
    private ContactListener contactListener;

    // ===========================================================
    //              Elementos Gráficos
    // ===========================================================
    // ============== Sprite Nave ================================
    AnimatedSprite shipSprite;

    // ===========================================================
    //            Cuerpos  en el motor de física
    // ===========================================================

    // ============== Definición de Fijadores de física ==========
    // -- Paredes laterales
    final FixtureDef WALL_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(0,0f,0f);
    // -- Nave
    final FixtureDef SHIP_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(50.f,0.1f,0.5f);
    //-- Meteoros
    final FixtureDef METEOR_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(1f,1.1f,0.4f);

    // ============== Paredes ====================================
    // -- Izquierda
    private Body leftWallBody;
    // -- Derecha
    private Body rightWallBody;

    // ============== Nave =======================================
    private Body shipBody;

    // ===========================================================
    //           Elementos de la mecánica del nivel
    // ===========================================================

    int lives;
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
        // -- Deshabilitamos el movimiento de la nave hasta que se cargue todo el nivel.
        movementEnabled = false;

    }

    // =============================================================================================
    //                                       M É T O D O S
    // =============================================================================================

    // ===========================================================
    //                 Cargar recursos gráficos
    // ===========================================================
    @Override
    public void loadGFX() {
        // -- Llamamos al Adm. de Recursos para cargar las imágenes y los fondos
        resourceManager.loadAdventureLevelOneResourcesGFX();
        // -- Creamos un sprite animado con la textura animada de la nave
        shipSprite = new AnimatedSprite(125, GameManager.CAMERA_HEIGHT/2,resourceManager.adventureLevelOneAnimatedShipTextureRegion,vertexBufferObjectManager);

    }
    // ===========================================================
    //                       Cargar música
    // ===========================================================
    @Override
    public void loadMFX() {

    }
    // ===========================================================
    //                      Cargar sonidos
    // ===========================================================
    @Override
    public void loadSFX() {

    }

    // ===========================================================
    //                      Crear la escena
    // ===========================================================
    @Override
    public void createScene() {
        lives = 3;
        physicsWorld = new PhysicsWorld(new Vector2(GRAVITY_X,GRAVITY_Y),false);

        this.registerUpdateHandler(physicsWorld);
        rand = new Random();

        createWalls();



        gameManager.getEngine().enableAccelerationSensor(gameManager, this);

        shipBody = PhysicsFactory.createCircleBody(physicsWorld, shipSprite, BodyDef.BodyType.DynamicBody, SHIP_FIXTURE_DEFINITION);
        shipBody.setUserData("ship");

        physicsWorld.registerPhysicsConnector(new PhysicsConnector(shipSprite, shipBody, true, false));


        attachChild(shipSprite);
        shipSprite.animate(500);
        shipSprite.setRotation(-90);

        shipSprite.registerUpdateHandler(new IUpdateHandler() {
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
                if(timesExecuted == 5){
                    spawnVelocity = 2f;
                    pTimerHandler.setTimerSeconds(spawnVelocity);
                }
                if(timesExecuted > 7 && timesExecuted%3 == 0){
                    createMeteorite();
                }
            }
        });

        this.registerUpdateHandler(timerHandler);
        physicsWorld.setContactListener(createContactListener());

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

        // ============== Registrar ID de cuerpo ====================
        leftWallBody.setUserData("wall");
        rightWallBody.setUserData("wall");

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
        meteor = new Sprite(GameManager.CAMERA_WIDTH,downPosition,resourceManager.adventureLevelOneMeteoriteTextureRegions.get(textureRegionChosen), vertexBufferObjectManager);

        meteorite = PhysicsFactory.createCircleBody(physicsWorld, meteor, BodyDef.BodyType.DynamicBody, METEOR_FIXTURE_DEFINITION);
        meteorite.setUserData("meteorite");
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(meteor, meteorite, true, true));

        //meteorite.setTransform(GameManager.CAMERA_WIDTH,GameManager.CAMERA_HEIGHT/2,0);
        meteor.registerEntityModifier(new LoopEntityModifier(new RotationModifier(5.0f, 0, 360)));

        this.attachChild(meteor);
        float randFlot = rand.nextFloat();
        int magnitudeMultiplier = 500;
        int sign = (rand.nextInt(2) == 0)?1:-1;

        meteorite.setLinearVelocity(-9f,0);
        meteorite.applyForce(randFlot*magnitudeMultiplier*randFlot,
                randFlot*magnitudeMultiplier*sign*meteorite.getMass(),
                meteorite.getWorldCenter().x,meteorite.getWorldCenter().y);
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
            shipBody.setTransform(shipBody.getPosition(),shipBody.getAngle()+1f);
        }
    }

    private ContactListener createContactListener() {
        contactListener = new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                final Fixture fixtureA = contact.getFixtureA();
                final Fixture fixtureB = contact.getFixtureB();
                if(fixtureA != null && fixtureB != null){
                    if((fixtureA.getBody().getUserData().equals("ship") && fixtureB.getBody().getUserData().equals("meteorite")) || (fixtureB.getBody().getUserData().equals("ship") && fixtureA.getBody().getUserData().equals("meteorite")) ){
                        lives--;
                        System.out.println("Meteor-Ship Collision!! Lives: "+lives);
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
