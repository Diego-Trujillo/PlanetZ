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

import org.andengine.engine.camera.hud.HUD;
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
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
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
    private float GRAVITY_X = -5f;
    private float GRAVITY_Y = 0;

    // -------------- Escucha los contactos ----------------------
    private ContactListener contactListener;

    // ============== Contenedor de Elementos a Borrar ===========
    private ArrayList<Meteorite> toBeDeleted;
    // ===========================================================
    //              Elementos Gráficos
    // ===========================================================
    // ============== Sprites================================
    // -------------- Nave ----------------------------------
    private AnimatedSprite shipSprite;
    // -------------- Elementos HUD -------------------------
    // -- Botón de Pausa
    private Sprite pauseButton;
    // -- Pantalla de pausa
    private Sprite pauseScreen;
    // -- Botón de Play
    private Sprite resumeButton;
    // -- Botón de Back
    private Sprite backButton;

    // -- El texto que dice las vidas que nos quedan
    Text livesRemainingText;

    // ===========================================================
    //            Cuerpos  en el motor de física
    // ===========================================================

    // ============== Definición de Fijadores de física ==========
    // -- Paredes laterales
    final FixtureDef WALL_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(0,0f,0f);
    // -- Nave
    final FixtureDef SHIP_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(50.f,0.1f,0.5f);
    //-- Meteoros
    final FixtureDef METEOR_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(1f,0.9f,0.4f);

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
    // ============== HUD ========================================
    private HUD playerHUD;

    // ============== Elementos del control ======================
    // -- Dice si el movimiento de la nave con el accelerómetro está habilitado
    boolean movementEnabled;
    // ============== Elementos de la nave =======================
    // -- Vidas de la nave
    private int playerLives;
    // ============== Elementos de la funcion que crea Meteoritos=
    // -- Veces que ha sido ejecutada la función de timer.
    private int timesExecuted;

    // ============== Miscéláneo ==================================
    // -- Generador de números aleatorios
    public Random rand;
    // -- Define qué imagen de meteorito se usará
    int meteoriteTextureChosen;
    // -- Factor de proporción con respecto al Escalar
    float proportionFactor;
    // -- Escalar máximo de fuerza
    int scalarMultipier;
    // -- Indica si el signo es positivo o negativo
    int signInt;
    // -- Bandera que indica si el juego está en pausa
    private boolean isPaused = false;

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
        backgroundSprite = resourceManager.loadSprite(0,gameManager.CAMERA_HEIGHT/2,resourceManager.adventureLevel1BackgroundTextureRegion);
        // -- Crea una entidad móvil que definirá movimiento del fondo
        movingParallaxEntity = new ParallaxBackground.ParallaxEntity(-70f,backgroundSprite);
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
        physicsWorld = new PhysicsWorld(new Vector2(GRAVITY_X,GRAVITY_Y),true){
            @Override
        public void onUpdate(float pSecondsElapsed){
                super.onUpdate(pSecondsElapsed);
                // -- Para cada meteorito en la lista a ser borrado
                for(Meteorite meteorite : toBeDeleted){
                    // -- Llama el método borrar del meteorito
                    meteorite.destroy();
                }
                // -- Limpia la lista de elementos a ser borrados
                toBeDeleted.clear();
                // -- Llamamos al recolector de basura
                System.gc();
            }
        };

        // -- Registrar al mundo de física ante la escena ========
        this.registerUpdateHandler(physicsWorld);

        //-- Fondo
        this.setBackground(movingParallaxBackground);

        // ============== Inicializar elementos de mecánica ======
        // -- Inicializar el generador de números aleatorios
        rand = new Random();
        // -- Las veces que se ha ejecutado el ciclo principal
        timesExecuted = 0;
        playerLives = 3;


        // ============== Crear los objetos del juego ============
        // -- Las paredes del juego
        createWalls();
        // -- La nave
        createShip();
        // -- El Heads-Up Display con la información del el nivel
        createHUD();



        toBeDeleted = new ArrayList<Meteorite>();


        // ============== CICLO PRINCIPAL =========================
        // -- Se va a ejecutar la acción cada x segundos (x inicial es 4f)
        final TimerHandler timerHandler = new TimerHandler(4f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                // -- Si el juego no está en pausa
                if(isPaused == false) {
                    // -- Crear un meteorito
                    createMeteorite();
                    // -- Sumar el número de veces que se ejecutó este ciclo
                    timesExecuted++;
                    if (timesExecuted == 5) {
                        ;
                        pTimerHandler.setTimerSeconds(2f);
                    }
                    if (timesExecuted > 7 && timesExecuted % 3 == 0) {
                        createMeteorite();
                    }

                    // -- Incrementar el número de veces que se ejecutó este ciclo
                    timesExecuted++;
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
        final Rectangle leftWallRectangle = new Rectangle(GameManager.CAMERA_WIDTH/2,-5,GameManager.CAMERA_WIDTH,10, vertexBufferObjectManager);
        // -- Pared Derecha
        final Rectangle rightWallRectangle = new Rectangle(GameManager.CAMERA_WIDTH/2, GameManager.CAMERA_HEIGHT + 5,GameManager.CAMERA_WIDTH,10, vertexBufferObjectManager);
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
        // -- Crear el cuerpo físico
        shipBody = PhysicsFactory.createCircleBody(physicsWorld, shipSprite, BodyDef.BodyType.DynamicBody, SHIP_FIXTURE_DEFINITION);
        // -- Agregar el tag de "ship" para identificar el cuerpo físico
        shipBody.setUserData("ship");
        // -- Conectar el cuerpo físico con el sprite de la nave
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(shipSprite, shipBody, true, false));

        // -- Adjuntar la nave a la escena
        attachChild(shipSprite);
        // -- Animar el sprite
        shipSprite.animate(500);
        // -- Girar el sprite 90°
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
    //  Crea una instancia de un meteorito, lo adhiere a la escena y lo propulsa
    // ===========================================================
    private void createMeteorite() {
        // -- Crea un nuevo objeto meteorito
        Meteorite meteorite = new Meteorite(this,physicsWorld);
        // -- Agregamos el objeto a la escena
        meteorite.attachToScene();
    }
    // ===========================================================
    //                  Crea el HUD del nivel
    // ===========================================================
    private void createHUD(){
        // -- Inicializamos un nuevo HUD
        playerHUD = new HUD();

        // ============ Creamos el texto de vidas ================
        livesRemainingText = new Text(60,160,resourceManager.fontOne,"Lives: "+playerLives,vertexBufferObjectManager);
        livesRemainingText.setRotation(-90);
        playerHUD.attachChild(livesRemainingText);
        // ============= Creamos los elementos del HUD ===========
        // -- Creamos el botón de PAUSA y sus acciones
        pauseButton = new Sprite(50,GameManager.CAMERA_HEIGHT-50,resourceManager.adventureLevel1PauseButtonTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y){
                // Cuando se toca el botón de pausa
                if (pSceneTouchEvent.isActionUp()&& isPaused == false) {
                    setPauseGame();
                }
                return true;
            }
        };

        // ========== Botones de pausa y sus acciones ================
        // -- Creamos la pantalla de PAUSA
        pauseScreen = new Sprite(GameManager.CAMERA_WIDTH/2, GameManager.CAMERA_HEIGHT/2, resourceManager.adventureLevel1ResumeButtonTextureRegion,vertexBufferObjectManager);


        resumeButton = new Sprite(GameManager.CAMERA_WIDTH/2 + 75, GameManager.CAMERA_HEIGHT/2 - 75, resourceManager.adventureLevel1PlayButtonTextureRegion,vertexBufferObjectManager){
            //si se da click se reanuda colocando el time step en 1/30
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y){
                //Cuando se toque la pantalla de pausa
                if (pSceneTouchEvent.isActionUp()) {
                    setPauseGame();
                }
                return true;}};
        backButton  = new Sprite(GameManager.CAMERA_WIDTH/2 + 75 , GameManager.CAMERA_HEIGHT/2 + 75, resourceManager.adventureLevel1BackButtonTextureRegion,vertexBufferObjectManager){
            //regresa al menu play en
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y){
                //Cuando se toque la pantalla de pausa
                if (pSceneTouchEvent.isActionUp()) {
                    //
                }
                return true;}
        };


        // ============= Adjuntamos todos los elementos al HUD ===
        playerHUD.attachChild(pauseButton);
        playerHUD.attachChild(pauseScreen);
        playerHUD.attachChild(resumeButton);
        playerHUD.attachChild(backButton);
        // ============= Rotamos los elementos de la pantalla Pausa ==
        pauseScreen.setRotation(-90);
        resumeButton.setRotation(-90);
        backButton.setRotation(-90);

        // ============= Deshabilitamos la pantalla de pausa =====
        pauseScreen.setVisible(false);
        resumeButton.setVisible(false);
        backButton.setVisible(false);

        // ============= Registramos las áreas táctiles ==========
        playerHUD.registerTouchArea(pauseButton);



        // -- Adjuntamos el HUD a la cámara
        camera.setHUD(playerHUD);

    }

    // ===========================================================
    //    Pausa el Juego
    // ===========================================================
    void setPauseGame(){
        if(isPaused){
            // -- Declaramos que retome todos los Update Handlers
            sceneManager.getCurrentScene().setIgnoreUpdate(false);
            // -- Quita la visibilidad de la pantalla
            pauseScreen.setVisible(false);
            resumeButton.setVisible(false);
            backButton.setVisible(false);
            // -- Desregistra la habilidad de tocar esta pantalla
            playerHUD.unregisterTouchArea(backButton);
            playerHUD.unregisterTouchArea(resumeButton);
            // -- Habilitamos el movimiento de la nave con el accelerómetro
            gameManager.getEngine().enableAccelerationSensor(gameManager, (AdventureLevelOneScene) sceneManager.getCurrentScene());
            // -- Cambiamos la bandera de juego pausado
            isPaused = false;
        }
        else{
            // -- Deshabilitamos el movimiento de la nave
            gameManager.getEngine().disableAccelerationSensor(gameManager);
            // -- Detenemos a la nave
            shipBody.setLinearVelocity(0,0);
            // -- Hacemos a la pantalla de pausa visible
            pauseScreen.setVisible(true);
            resumeButton.setVisible(true);
            backButton.setVisible(true);

            // -- Registramos el área táctil de la pantalla de pausa
            playerHUD.registerTouchArea(resumeButton);
            playerHUD.registerTouchArea(backButton);
            // -- Declaramos que ignore todos los Update Handlers de esta escena
            sceneManager.getCurrentScene().setIgnoreUpdate(true);
            // -- Cambiamos la bandera de juego pausado
            isPaused = true;
        }


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
                    // -- Si la colisión ocurre entre la nave y una insancia de meteorito
                    if((fixtureA.getBody().getUserData().equals("ship") && fixtureB.getBody().getUserData() instanceof Meteorite) || (fixtureB.getBody().getUserData().equals("ship") && fixtureA.getBody().getUserData() instanceof Meteorite) ){
                        // -- Resta el contador de vidas
                        playerLives--;
                        livesRemainingText.setText("Lives: "+playerLives);
                        if(playerLives==0){
                            // -- Creamos la escena del primer nivel
                            sceneManager.createScene(SceneType.YOU_LOSE);
                            // -- Corremos la escena del primer nivel
                            sceneManager.setScene(SceneType.YOU_LOSE);
                            // -- Liberamos la escena actual
                            sceneManager.destroyScene(SceneType.ADVENTURE_LEVEL_1);
                        }

                        // -- Preguntamos cuál de los dos cuerpos es el meteorito
                        if(fixtureA.getBody().getUserData() instanceof Meteorite){
                            // -- Agregamos el meteorito a la lista de elemtos a ser borrados
                            toBeDeleted.add((Meteorite) fixtureA.getBody().getUserData());
                        }
                        else{
                            // -- Agregamos el meteorito a la lista de elemtos a ser borrados
                            toBeDeleted.add((Meteorite)fixtureB.getBody().getUserData());
                        }
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
