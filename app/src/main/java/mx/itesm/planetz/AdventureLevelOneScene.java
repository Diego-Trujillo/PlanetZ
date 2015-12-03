package mx.itesm.planetz;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.os.Vibrator;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

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
import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.IEntityParameterCallable;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationAtModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.SpriteParticleSystem;
import org.andengine.entity.particle.emitter.CircleParticleEmitter;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.ExpireParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.ScaleParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
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
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObject;
import org.andengine.util.SocketUtils;

import java.util.ArrayList;
import java.util.Iterator;
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
    //           Elementos de las particulas
    // ===========================================================

    private ParticleSystem<Sprite> smokeParticleSystem;
    private CircleParticleEmitter smokeEmmiter;
    private Vibrator mVibrator;
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
    // ============== Sprites=====================================
    // -------------- Nave ---------------------------------------
    //private AnimatedSprite shipSprite;
    private TiledSprite shipSprite;
    // -------------- Elementos HUD ------------------------------
    // -- Botón de Pausa
    private Sprite pauseButton;
    // -- Pantalla de pausa
    private Sprite pauseScreen;
    // -- Botón de Play
    private Sprite resumeButton;
    // -- Botón de Back
    private Sprite backButton;
    // -- Contenedor de vidas
    private ArrayList<Sprite> playerLivesSprites;
    // ============== Textos ======================================
    // -- El texto que dice las vidas que nos quedan
    Text timeRemainingText;

    // ============== Fondo =======================================
    // -------------- Fondo Móvil ---------------------------------
    private AutoParallaxBackground movingParallaxBackground;
    // -------------- Entidad Móvil -------------------------------
    private ParallaxBackground.ParallaxEntity movingParallaxEntityBackground;
    private ParallaxBackground.ParallaxEntity movingParralaxEntityStars;
    // -------------- Sprite del Fondo ----------------------------
    private Sprite backgroundSprite;
    private Sprite backgroundStars1Sprite;
    private Sprite backgroundStars2Sprite;
    private Sprite backgroundStars3Sprite;
    //-------------Particula--------------------------
    private ITextureRegion particle;

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
    // -- "Pared de la muerte"
    private Body wallOfDeathBody;

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


    // ===========================================================
    //          Elementos de la funcion que crea Meteoritos
    // ===========================================================
    // -- Veces que ha sido ejecutada la función del loop de tiempo
    private int timeRemaining;
    // -- Veces que ha sido ejecutada la función de meteorSpawner
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
    // -- Bandera que indica si el juego acabó
    private boolean gameWon = false;


    public PlayerHUD sceneHUD;

    // =============================================================================================
    //                                    C O N S T R U C T O R
    // =============================================================================================
    public AdventureLevelOneScene(){
        super();
        sceneType = SceneType.ADVENTURE_LEVEL_1;
        sessionManager.currentLevel = 1;

        // -- Deshabilitamos el movimiento de la nave hasta que se cargue todo el nivel.
        movementEnabled = false;

    }

    public AdventureLevelOneScene(boolean infiniteModeActivated){
        super();
        sceneType = SceneType.ADVENTURE_LEVEL_1;
        // -- Deshabilitamos el movimiento de la nave hasta que se cargue todo el nivel.
        movementEnabled = false;

        if(infiniteModeActivated==true) {
            sessionManager.currentLevelInfiniteMode = 1;
        }

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
        shipSprite = new AnimatedSprite(130, GameManager.CAMERA_HEIGHT/2,resourceManager.adventureLevelOneAnimatedShipTextureRegion,vertexBufferObjectManager);


        // =============== Fondo de estrellas ====================
        backgroundStars1Sprite = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2,resourceManager.adventureLevelOneBackgroundStars1TextureRegion);
        backgroundStars2Sprite = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2,resourceManager.adventureLevelOneBackgroundStars2TextureRegion);
        backgroundStars3Sprite = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2,resourceManager.adventureLevelOneBackgroundStars3TextureRegion);

        //----------------Particula------------------------------
        particle = resourceManager.adventureLevel1ParticleTextureRegion;

        movingParallaxBackground = new AutoParallaxBackground((22f/255f),(61f/255f),(76f/255f),10f);
        // -- Habilita el color de fondo ..
        movingParallaxBackground.setColorEnabled(true);
        // -- Asignamos los sprites como entidades móviles y les damos distintas velocidades de movimiento --
        movingParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-20f, backgroundStars1Sprite));
        movingParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-80f, backgroundStars2Sprite));
        movingParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-175f,backgroundStars3Sprite));
     }
    // ===========================================================
    //                       Cargar música
    // ===========================================================
    @Override
    public void loadMFX() {
        //resourceManager.setMusic("Menu.ogg");
    }
    // ===========================================================
    //                      Cargar sonidos
    // ===========================================================
    @Override
    public void loadSFX() {
        // -- LLamamos al Adm. de Recursos para cargar los sonidos --
        resourceManager.setSound("Level1/shipCrash.ogg",1);
        resourceManager.setSound("Level1/wallcollide.ogg",2);
        resourceManager.updateAudioVolume();

    }

    // ===========================================================
    //                      Crear la escena
    // ===========================================================
    @Override
    public void createScene() {
        mVibrator = (Vibrator) gameManager.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
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
        timeRemaining = 32;
        timesExecuted = 0;
        playerLives = 3;



        // ============== Crear los objetos del juego ============
        // -- Las paredes del juego
        createWalls();
        // -- La nave
        createShip();
        // -- El Heads-Up Display con la información del el nivel
        sceneHUD = new PlayerHUD(this,1);
        sceneHUD.attachToScene();


        // -- Creamos la lista de elementos a ser borrados
        toBeDeleted = new ArrayList<Meteorite>();


        // =============== Reproducir música de fondo ============
        //resourceManager.backgroundMusic.play();
        //resourceManager.updateAudioVolume();

        // ============== CICLO PRINCIPAL =========================
        // -- Se va a ejecutar la acción cada x segundos (x inicial es 4f)
        final TimerHandler meteorSpawner = new TimerHandler(3f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                // -- Si el juego no está en pausa
                if(isPaused == false) {
                    // -- Crear un meteorito
                    createMeteorite(1);
                    // -- Sumar el número de veces que se ejecutó este ciclo
                    timesExecuted++;
                    if (timesExecuted == 4) {
                        pTimerHandler.setTimerSeconds(1.5f);
                    }
                    if (timesExecuted > 2 && timesExecuted % 2 == 0) {
                        createMeteorite(1);
                        createMeteorite(1);
                    }
                    if (timesExecuted > 4 && timesExecuted % 2 == 1) {
                        createMeteorite(2);
                        createMeteorite(2);
                    }

                    // -- Incrementar el número de veces que se ejecutó este ciclo
                    timesExecuted++;
                }
            }
        });

        final TimerHandler timeLoop = new TimerHandler(1f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if(timeRemaining >= 0)sceneHUD.updateTimeText(timeRemaining);
                else if(timeRemaining == -2){
                    pTimerHandler.setTimerSeconds(3);
                    sceneManager.getCurrentScene().unregisterUpdateHandler(meteorSpawner);
                    gameManager.getEngine().disableAccelerationSensor(gameManager);
                    shipBody.setType(BodyDef.BodyType.KinematicBody);
                    shipBody.setLinearVelocity(10f, 0);
                    //smokeEmmiter.setCenterX(shipSprite.getX()-20);
                    gameWon = true;
                    //gameManager.toastOnUiThread("Gems Unlocked!", Toast.LENGTH_SHORT);
                    sessionManager.gemsUnlocked[1][1]= true;
                    sessionManager.gemsUnlocked[1][2]= (playerLives >= 2);
                    sessionManager.gemsUnlocked[1][3]= (playerLives >= 3);
                    //sessionManager.currentLevel = 2;
                    sessionManager.writeChanges();

                }
                else if(timeRemaining == -3){
                    sceneManager.destroyScene(SceneType.ADVENTURE_LEVEL_1);
                    // -- Creamos la escena del primer nivel
                    sceneManager.createScene(SceneType.TEMP);
                    // -- Corremos la escena del primer nivel
                    sceneManager.setScene(SceneType.TEMP);
                    // -- Liberamos la escena actual

                }
                timeRemaining--;

            }
        });
        // ============== Registrar ciclos del juego ==============
        // -- Registrar el ciclo principal en la escena
        this.registerUpdateHandler(meteorSpawner);
        this.registerUpdateHandler(timeLoop);
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
        final Rectangle leftWallRectangle = new Rectangle(GameManager.CAMERA_WIDTH/2,-5,GameManager.CAMERA_WIDTH + 400,10, vertexBufferObjectManager);
        // -- Pared Derecha
        final Rectangle rightWallRectangle = new Rectangle(GameManager.CAMERA_WIDTH/2, GameManager.CAMERA_HEIGHT + 5,GameManager.CAMERA_WIDTH + 400,10, vertexBufferObjectManager);
        // -- "Pared de la muerte", definimos un espacio para que los meteoritos que no impactan al jugador se borren del juego
        final Rectangle wallOfDeathRectangle = new Rectangle(-256,GameManager.CAMERA_HEIGHT/2,10,GameManager.CAMERA_HEIGHT*2,vertexBufferObjectManager);

        // -- Colorear ambos rectángulos de blanco
        leftWallRectangle.setColor(0f, 0f, 0f);
        rightWallRectangle.setColor(0f, 0f, 0f);
        wallOfDeathRectangle.setColor(1f, 1f, 1f);

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
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(wallOfDeathRectangle, wallOfDeathBody));

        // ============== Adjuntar las paredes al mundo =============
        this.attachChild(leftWallRectangle);
        this.attachChild(rightWallRectangle);
        this.attachChild(wallOfDeathRectangle);
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
        shipSprite.setCurrentTileIndex(0);
        shipSprite.attachChild(getSmokeParticleSystem());
        //-- Igualar la posicion de la particula
        //smokeEmmiter.setCenterX(shipSprite.getX()-20);

        // -- Animar el sprite
        //shipSprite.animate(500);
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
    private void createMeteorite(int num) {
        // -- Crea un nuevo objeto meteorito con angulo aleatorio o sin angulo
        Meteorite meteorite;
        switch(num){
            case 1:
                meteorite = new Meteorite(this);
                meteorite.attachToScene(num);
                break;
            case 2:
                meteorite = new Meteorite(this);
                meteorite.attachToScene(num);
                break;
        }

        // -- Agregamos el objeto a la escena

    }

    // ===========================================================
    //    Pausa el Juego
    // ===========================================================
    @Override
    public void pause(){
        shipBody.setLinearVelocity(0,0);
        gameManager.getEngine().disableAccelerationSensor(gameManager);
        this.setIgnoreUpdate(true);
        isPaused = true;
    }

    // ===========================================================
    //    Despausa el Juego
    // ===========================================================
    @Override
    public void unPause(){
        this.setIgnoreUpdate(false);
        gameManager.getEngine().enableAccelerationSensor(gameManager,this);
        isPaused = false;


    }

    //Sistema de particulas

    public ParticleSystem<Sprite> getSmokeParticleSystem() {

        IEntityFactory<Sprite> ief = new IEntityFactory<Sprite>() {
            @Override
            public Sprite create(float pX, float pY)
            {
                //Sprite s = resourceManager.loadSprite(pX,pY,particle);
                return new Sprite (pX,pY,particle,vertexBufferObjectManager);
                //return s;
            }
        };

        smokeEmmiter = new CircleParticleEmitter(30,100,0.0f,5.5f);
        //resourceManager.adventureLevel1ParticleTextureRegion.getWidth()
        smokeParticleSystem = new ParticleSystem<Sprite>(ief,smokeEmmiter,60,90,180);
        smokeParticleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(
                GLES20.GL_SRC_ALPHA,GLES20.GL_ONE));
        float tiempoVida = 0.5f;   // Segundos de vida de cada partícula
        smokeParticleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(5.0f,0.3f,0.2f));
        smokeParticleSystem.addParticleInitializer(new ExpireParticleInitializer<Sprite>(tiempoVida));
        smokeParticleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(250.0f,-250.0f,260.0f,160.0f));
        smokeParticleSystem.addParticleInitializer(new ScaleParticleInitializer<Sprite>(0.5f,2.5f));
        smokeParticleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(-100, 100));
        smokeParticleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(0.0f,1.1f,0.45f,0.0f));
        //controla la escala en un lapso de tiempo
        smokeParticleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0.0f,0.05f,0.5f,2.0f));
        smokeParticleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0.05f,1.0f,4.0f,0.5f));

        return smokeParticleSystem;
    }


    // ===========================================================
    //   Actualiza el indice del sprite para mostrar el daño
    // ===========================================================
    private void updateSprite(){
        switch(playerLives){
            case 2:
                shipSprite.setCurrentTileIndex(1);
                break;
            case 1:
                shipSprite.setCurrentTileIndex(2);
                break;
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
                    if(((fixtureA.getBody().getUserData().equals("ship") && fixtureB.getBody().getUserData() instanceof Meteorite) || (fixtureB.getBody().getUserData().equals("ship") && fixtureA.getBody().getUserData() instanceof Meteorite)) && gameWon == false){
                        // -- Resta el contador de vidas
                        playerLives--;
                        mVibrator.vibrate(300);
                        resourceManager.soundOne.play();
                        sceneHUD.updateLives(playerLives);
                        updateSprite();
                        if(playerLives == 0){
                            if(sessionManager.infiniteModeUnlocked==false && sessionManager.unlockedLevels == 0) {
                                sessionManager.gemsUnlocked[3][1] = false;
                                sessionManager.gemsUnlocked[3][2] = false;
                                sessionManager.gemsUnlocked[3][3] = false;
                                sessionManager.writeChanges();
                            }
                            // -- Liberamos la escena actual
                            sceneManager.destroyScene(SceneType.ADVENTURE_LEVEL_1);
                            // -- Creamos la escena del primer nivel
                            sceneManager.createScene(SceneType.YOU_LOSE);
                            // -- Corremos la escena del primer nivel
                            sceneManager.setScene(SceneType.YOU_LOSE);

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

                    // -========== Registra el contacto entre meteorito y la pared de la muerte ==
                    if((fixtureA.getBody().getUserData().equals("wod") && fixtureB.getBody().getUserData() instanceof Meteorite) || (fixtureB.getBody().getUserData().equals("wod") && fixtureA.getBody().getUserData() instanceof Meteorite) ){
                        System.out.println("Wall of death takes another victim >:)!");
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
                    // -========== Registra el contacto entre meteorito y los muros ==
                    if((fixtureA.getBody().getUserData().equals("wall") && fixtureB.getBody().getUserData() instanceof Meteorite) || (fixtureB.getBody().getUserData().equals("wall") && fixtureA.getBody().getUserData() instanceof Meteorite) ){
                        resourceManager.soundTwo.play();

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

        // -- Deshabilitamos el accelerómetro
        gameManager.getEngine().disableAccelerationSensor(gameManager);
        // -- Liberamos los recursos de esta escena
        resourceManager.unloadAdventureLevelOneResources();
        // -- Destruimos el mundo de física

        // TO BE ADDED LATER JAJAJA
        /*
        Iterator<Body> bodyIterator = physicsWorld.getBodies();
        while(bodyIterator.hasNext()){
            physicsWorld.destroyBody(bodyIterator.next());
        }
        physicsWorld.clearForces();
        physicsWorld.clearPhysicsConnectors();
        physicsWorld.reset();
        physicsWorld.dispose();
        */


        sceneHUD.destroy();
        // -- Borramos la escena
        this.detachChildren();
        this.detachSelf();
        // -.- Quitamos la escena
        this.dispose();
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
            shipBody.setLinearVelocity(0, (pAccelerationData.getY() + 5f) * 7);
            //smokeEmmiter.setCenterY((pAccelerationData.getY() + 5f)*7);
            //smokeEmmiter.setCenterX(500);
            //smokeParticleSystem.setPosition(shipSprite.getX()-10,(pAccelerationData.getY() + 5f)*7);
        }
    }

    public PhysicsWorld getPhysicsWorld(){
        return physicsWorld;
    }


}
