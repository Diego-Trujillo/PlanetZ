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
import com.badlogic.gdx.physics.box2d.Shape;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.UpdateHandlerList;
import org.andengine.engine.handler.collision.CollisionHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
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
    // ============== Sprites================================
    AnimatedSprite shipSprite;
    //-----botones sprite
    Sprite pauseButton;
    Sprite resumeButton;


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

    // ============== Elementos del control ======================
    // -- Dice si el movimiento de la nave con el accelerómetro está habilitado
    boolean movementEnabled;
    // ============== Elementos de la nave =======================
    // -- Vidas de la nave
    int playerLives;
    // ============== Elementos de la funcion que crea Meteoritos=
    // -- Veces que ha sido ejecutada la función de timer.
    int timesExecuted;

    // ============== Miscéláneo ==================================
    // -- Generador de números aleatorios
    Random rand;
    // -- Float
    int meteoriteTextureChosen;
    // -- Factor de proporción con respecto al Escalar
    float proportionFactor;
    // -- Escalar máximo de fuerza
    int scalarMultipier;
    // -- Indica si el signo es positivo o negativo
    int signInt;
    // -- Indica el step para crear la pausa
    int timestep= 1/30;
    // -- Bandera del click
    boolean click = false;

    // FONDO
    private AutoParallaxBackground movingParallaxBackground;
    private ParallaxBackground.ParallaxEntity movingParallaxEntity;
    private Sprite backgroundSprite;

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


        // =============== Fondo de estrellas ====================
        // -- Crea una entidad de fondo móvil
        movingParallaxBackground = new AutoParallaxBackground(0f,0,0,1);
        // -- Crea el sprite del fondo
        backgroundSprite = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2,resourceManager.menuBackgroundTextureRegion);
        // -- Crea una entidad móvil que definirá movimiento del fondo
        movingParallaxEntity = new ParallaxBackground.ParallaxEntity(15f,backgroundSprite);
        // -- Asigna la entidad móvil para que siga y de movimiento al fondo
        movingParallaxBackground.attachParallaxEntity(movingParallaxEntity);

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
        // ============== Crear el mundo de física ===============
        // -- Inicializarlo
        physicsWorld = new PhysicsWorld(new Vector2(GRAVITY_X,GRAVITY_Y),true);
        // -- Registrar al mundo de física ante la escena ========
        this.registerUpdateHandler(physicsWorld);

        //-- Fondo
        this.setBackground(movingParallaxBackground);
        // ============== Crear los objetos del juego ============


        // -- Las paredes del juego
        createWalls();
        // -- La nave
        createShip();

        // ============== Inicializar elementos de mecánica ======
        // -- Inicializar el generador de números aleatorios
        rand = new Random();
        // -- Las veces que se ha ejecutado el ciclo principal
        timesExecuted = 0;
        playerLives = 3;


        // ============== PAUSA ============================

                resumeButton = new Sprite(GameManager.CAMERA_WIDTH/2, GameManager.CAMERA_HEIGHT/2, resourceManager.adventureLevel1ResumeButtonTextureRegion,vertexBufferObjectManager){
                    //si se da click se reanuda colocando el time step en 1/30
                    @Override
                    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y){
                        //si se da click se reanuda colocando el time step en 1/30
                        if (pSceneTouchEvent.isActionUp()) {
                            //this.registerUpdateHandler(physicsWorld);
                            click= false;
                            this.setIgnoreUpdate(false);
                            resumeButton.setVisible(false);}
                        return true;}};
                pauseButton = new Sprite(50,GameManager.CAMERA_HEIGHT-50,resourceManager.adventureLevel1PauseButtonTextureRegion,vertexBufferObjectManager){
                    @Override
                    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y){
                        // si se da click se pone el time step para pausa
                        if (pSceneTouchEvent.isActionUp()&& click==false) {
                            //physicsWorld.unregisterPhysicsConnector();
                            //movementEnabled = false;
                            click= true;
                            resumeButton.setVisible(true);
                            System.out.println("CLICK EN PAUSA");
                            this.setIgnoreUpdate(true);


                        }
                        return true;
                    }
                };
                this.attachChild(pauseButton);
                this.attachChild(resumeButton);
                resumeButton.setVisible(false);
                this.registerTouchArea(pauseButton);
                this.registerTouchArea(resumeButton);

                //---- se le da el step al mundo que se checa constantemente


        // ============== CICLO PRINCIPAL =========================
        // -- Se va a ejecutar la acción cada x segundos (x inicial es 4f)
        final TimerHandler timerHandler = new TimerHandler(4f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if(click==false) {
                    createMeteorite();
                    timesExecuted++;
                    if (timesExecuted == 5) {
                        ;
                        pTimerHandler.setTimerSeconds(2f);
                    }
                    if (timesExecuted > 7 && timesExecuted % 3 == 0) {
                        createMeteorite();
                    }
                }
            }
        });

        // ============== Registrar ciclos del juego ==============
        // -- Registrar el ciclo principal en la escena
        this.registerUpdateHandler(timerHandler);
        // -- Registrar el manejador de colisiones en el mundo de física
        physicsWorld.setContactListener(createContactListener());

        // -- Ya que se cargó tod o, habilitamos el movimiento e iniciamos el accelerómetro
        movementEnabled = true;
        gameManager.getEngine().enableAccelerationSensor(gameManager, this);




    }

    // ===========================================================
    //            Crea las paredes límites del juego
    // ===========================================================
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

    // ===========================================================
    //                      Crea la nave
    // ===========================================================
    private void createShip(){

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

    }

    // ===========================================================
    //                Crea una instancia de un meteorito, lo adhiere a la escena y lo propulsa
    // ===========================================================
    private void createMeteorite() {
        // ============== Asignar valores numéricos al azar ==========
        // -- El índice de la textura de región que se va a elegir para este meteorito [0,15]
        meteoriteTextureChosen = rand.nextInt(resourceManager.adventureLevelOneMeteoriteTextureRegions.size());
        // -- El factor de proporción (0,1)
        proportionFactor = rand.nextFloat();
        // -- Multiplicador de escala
        scalarMultipier = 500;
        // -- Determina al azar 50% - 50% si el número es negativo o positivo
        signInt = (rand.nextInt(2) == 0) ? 1 : -1;

        // ============== Declara el sprite y el cuerpo físico ========
        Sprite meteorSprite;
        Body meteorBody;

        // -- Inicializa el Sprite
        meteorSprite = new Sprite(GameManager.CAMERA_WIDTH, rand.nextInt(GameManager.CAMERA_HEIGHT - 100) + 50, resourceManager.adventureLevelOneMeteoriteTextureRegions.get(meteoriteTextureChosen), vertexBufferObjectManager);
        // -- Inicializa el Cuerpo Físico
        meteorBody = PhysicsFactory.createCircleBody(physicsWorld, meteorSprite, BodyDef.BodyType.DynamicBody, METEOR_FIXTURE_DEFINITION);
        // -- Adhiere el tag "meteorite" al cuerpo físico
        meteorBody.setUserData("meteorite");
        // -- Registra el conector entre el Sprite y el Cuertpo
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(meteorSprite, meteorBody, true, true));

        // -- Registramos una rotación eterna del Sprite del meteorito
        meteorSprite.registerEntityModifier(new LoopEntityModifier(new RotationModifier(5.0f, 0, 360)));

        // -- Adjuntamos el sprite a la escena
        this.attachChild(meteorSprite);

        // -- Definimos una velocidad linear para que avance rápido sin importar la gravedad
        meteorBody.setLinearVelocity(-9f,0);
        // -- Aplicamos una fuerza aleatoria en x para definir una propulsión aleatoria hacia la nave
        //    y una fuerza aleratoria en Y para definir un ángulo de lanzamiento inicial.
        //    la fuerza es = masa * proporción (0,1) * factorEscalar
        meteorBody.applyForce(proportionFactor*scalarMultipier*meteorBody.getMass()/5,proportionFactor*scalarMultipier*meteorBody.getMass() ,meteorBody.getWorldCenter().x,meteorBody.getWorldCenter().y);
    }
    // ===========================================================
    //  Define comportamiento cuando se presione la tecla BACK
    // ===========================================================
    @Override
    public void onBackKeyPressed() {

    }
    // ===========================================================
    //             Comportamiento para liberar la escena
    // ===========================================================
    @Override
    public void destroyScene() {

    }
    // ===========================================================
    //         Cuando se cambia la precisión del accelerómetro
    // ===========================================================
    @Override
    public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {

    }
    // ===========================================================
    //  Define comportamiento cuando la inclinación cambia: Accelerómetro
    // ===========================================================
    @Override
    public void onAccelerationChanged(AccelerationData pAccelerationData) {
        if(movementEnabled) {
            // -- Mueve a la nave en la direción indicada por el accelerómetro
            shipBody.setLinearVelocity(0, pAccelerationData.getY() * 4);
        }
    }
    // ===========================================================
    //       Define cómo reaccionar ante las colisiones
    // ===========================================================
    private ContactListener createContactListener() {
        // -- Creamos un objeto tipo ContactListener
        contactListener = new ContactListener() {
            // ========= Cuando se detecta la colisión ===========
            @Override
            public void beginContact(Contact contact) {
                // -- Obtener objeto A
                final Fixture fixtureA = contact.getFixtureA();
                // -- Obtener objeto B
                final Fixture fixtureB = contact.getFixtureB();
                // -- Si los dos objetos existen
                if(fixtureA != null && fixtureB != null){
                    // MANEJA LA COLISIÓN ENTRE NAVE Y METEORITO
                    if((fixtureA.getBody().getUserData().equals("ship") && fixtureB.getBody().getUserData().equals("meteorite")) || (fixtureB.getBody().getUserData().equals("ship") && fixtureA.getBody().getUserData().equals("meteorite")) ){
                        // -- Resta el contador de vidas
                        playerLives--;
                        System.out.println("Meteor-Ship Collision!! Lives: "+playerLives);
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
