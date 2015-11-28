package mx.itesm.planetz;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.UpdateHandlerList;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
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
    private float GRAVITY_Y = -20f;

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

    Random rand;
    private int i= 1;

    private Rectangle astronautRectangle;
    private Body astronautBody;

    private Astronaut player;
    private PlayerHUD sceneHUD;
    @Override
    public void loadGFX() {
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
        physicsWorld = new PhysicsWorld(new Vector2(GRAVITY_X,GRAVITY_Y),true);

        this.registerUpdateHandler(physicsWorld);

        sceneHUD.attachToScene();

        this.setBackground(new Background(0f, 0f, 0f));
        createWalls();

        createAstronaut();
        rand = new Random();
        TimerHandler platformSpawner = new TimerHandler(1,true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                final Rectangle rekt= new Rectangle(GameManager.CAMERA_WIDTH * i ,40*rand.nextFloat() ,GameManager.CAMERA_HEIGHT*1.25F,50, vertexBufferObjectManager);
                rekt.setColor(1f, 0, 1f);
                Body rektBody = PhysicsFactory.createBoxBody(physicsWorld, rekt, BodyDef.BodyType.KinematicBody, WALL_FIXTURE_DEFINITION);

                physicsWorld.registerPhysicsConnector(new PhysicsConnector(rekt, rektBody));

                attachChild(rekt);
                i++;
                //rektBody.setLinearVelocity(-5,0);
            }
        });


        this.registerUpdateHandler(platformSpawner);

    }


    private void createWalls(){

        // ============== Crear los SpritesRectángulos ===============
        // -- Pared Izquierda
        final Rectangle leftWallRectangle = new Rectangle(GameManager.CAMERA_WIDTH/2,0,GameManager.CAMERA_WIDTH + 400,10, vertexBufferObjectManager);
        // -- Pared Derecha
        final Rectangle rightWallRectangle = new Rectangle(GameManager.CAMERA_WIDTH/2, GameManager.CAMERA_HEIGHT,GameManager.CAMERA_WIDTH + 400,10, vertexBufferObjectManager);
        // -- "Pared de la muerte", definimos un espacio para que los meteoritos que no impactan al jugador se borren del juego
        final Rectangle wallOfDeathRectangle = new Rectangle(-256,GameManager.CAMERA_HEIGHT/2,10,GameManager.CAMERA_HEIGHT*2,vertexBufferObjectManager);

        // -- Colorear ambos rectángulos de blanco
        leftWallRectangle.setColor(1f, 1f, 0f);
        rightWallRectangle.setColor(1f, 1f, 0f);
        wallOfDeathRectangle.setColor(1f, 1f,1f);

        // ============== Crear los cuerpos de física ===============
        leftWallBody = PhysicsFactory.createBoxBody(physicsWorld, leftWallRectangle, BodyDef.BodyType.StaticBody, WALL_FIXTURE_DEFINITION);
        rightWallBody = PhysicsFactory.createBoxBody(physicsWorld, rightWallRectangle, BodyDef.BodyType.StaticBody, WALL_FIXTURE_DEFINITION);
        wallOfDeathBody = PhysicsFactory.createBoxBody(physicsWorld,wallOfDeathRectangle, BodyDef.BodyType.StaticBody,WALL_FIXTURE_DEFINITION);

        // ============== Registrar ID de cuerpo ====================
        leftWallBody.setUserData("wall");
        rightWallBody.setUserData("wall");
        wallOfDeathBody.setUserData("wod");

        // ============== Conectar cuerpos de física a sprites ======
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(leftWallRectangle, leftWallBody));
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(rightWallRectangle, rightWallBody));
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(wallOfDeathRectangle,wallOfDeathBody));

        // ============== Adjuntar las paredes al mundo =============
        this.attachChild(leftWallRectangle);
        this.attachChild(rightWallRectangle);
        this.attachChild(wallOfDeathRectangle);
    }


    private void createAstronaut(){
        player = new Astronaut(this, physicsWorld);
        player.attachToScene();

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
    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void destroyScene() {

    }
}
