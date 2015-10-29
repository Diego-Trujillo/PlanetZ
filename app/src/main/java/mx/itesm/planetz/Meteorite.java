package mx.itesm.planetz;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.region.ITextureRegion;

import java.util.Random;

/**
 * Created by Diego on 27/10/2015.
 */
public class Meteorite {
    // =============================================================================================
    //                     D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================
    // ===========================================================
    //                 Elementos generales
    // ===========================================================
    // -------------- La escena del juego ------------------------
    private AdventureLevelOneScene scene;
    // -------------- Generador de números al azar ---------------
    private Random rand;
    // -------------- Define qué imagen de meteorito se usará ----
    int meteoriteTextureChosen;
    // -------------- Factor de proporción con respecto al Escalar
    float proportionFactor;
    // -------------- Escalar máximo de fuerza -------------------
    int scalarMultipier;
    // -------------- Indica si el signo es positivo o negativo --
    int signInt;

    // ===========================================================
    //                 Elementos gráficos
    // ===========================================================
    // -------------- Región Textura -----------------------------
    private ITextureRegion textureRegion;
    // -------------- Sprite -------------------------------------
    private Sprite sprite;

    // ===========================================================
    //                 Elementos del motor de física
    // ===========================================================
    // -------------- Mundo de física ----------------------------
    private PhysicsWorld physicsWorld;
    // -------------- Cuerpo -------------------------------------
    private Body body;
    // -------------- Conector Sprite-Cuerpo ---------------------
    private PhysicsConnector physicsConnector;
    // -------------- Identificador ------------------------------




    // =============================================================================================
    //                                    C O N S T R U C T O R
    // =============================================================================================
    public Meteorite(AdventureLevelOneScene scene,PhysicsWorld physicsWorld){
        // ============== Asignamos elementos dados de argumento ====
        // -- La escena del juego
        this.scene = scene;
        // -- El mundo de física
        this.physicsWorld = physicsWorld;
        // -- El generador de números al azar
        this.rand = scene.rand;

        // ============== Asignar valores numéricos al azar ==========
        // -- El índice de la textura de región que se va a elegir para este meteorito [0,15]
        meteoriteTextureChosen = rand.nextInt(scene.resourceManager.adventureLevelOneMeteoriteTextureRegions.size());
        // -- El factor de proporción (0,1)
         proportionFactor = rand.nextFloat();
        // -- Multiplicador de escala
        scalarMultipier = 1000;
        // -- Determina al azar 50% - 50% si el número es negativo o positivo
        signInt = (rand.nextInt(2) == 0) ? 1 : -1;

        // ============== Crear el sprite y cuerpo ====================
        // -- Inicializa el Sprite
        this.sprite = new Sprite(GameManager.CAMERA_WIDTH + 200, rand.nextInt(GameManager.CAMERA_HEIGHT - 100) + 50, scene.resourceManager.adventureLevelOneMeteoriteTextureRegions.get(meteoriteTextureChosen), scene.vertexBufferObjectManager);
        // -- Inicializa el Cuerpo Físico
        this.body = PhysicsFactory.createCircleBody(physicsWorld, this.sprite, BodyDef.BodyType.DynamicBody, this.scene.METEOR_FIXTURE_DEFINITION);
        // -- Adhiere el tag "meteorite" al cuerpo físico
        this.body.setUserData(this);

        // ============== Conectar sprite y cuerpo =====================
        this.physicsConnector = new PhysicsConnector(this.sprite,this.body,true,true);
        this.physicsWorld.registerPhysicsConnector(this.physicsConnector);
    }
    // ===========================================================
    //            Agrega el meteorito a la escena
    // ===========================================================
    public void attachToScene(){
        // -- Adjuntamos el sprite a la escena
        this.scene.attachChild(sprite);

        // -- Agregamos rotación perpetua al sprite
        sprite.registerEntityModifier(new LoopEntityModifier(new RotationModifier(5f/(proportionFactor +0.5f),0,signInt*360)));

        // -- Definimos una velocidad linear para que avance rápido sin importar la gravedad
        this.body.setLinearVelocity(-10f*rand.nextFloat(),0);

        // -- Aplicamos una fuerza aleatoria en x para definir una propulsión aleatoria hacia la nave
        //    y una fuerza aleratoria en Y para definir un ángulo de lanzamiento inicial.
        //    la fuerza es = masa * proporción (0,1) * factorEscalar
        this.body.applyForce(proportionFactor * scalarMultipier * body.getMass() / 5, signInt * proportionFactor * scalarMultipier * body.getMass(), body.getWorldCenter().x, body.getWorldCenter().y);
    }
    // ===========================================================
    //        Destruimos el meteorito
    // ===========================================================
    public void destroy(){
        // -- Desregistramos el conector de física entre cuerpo-sprite
        physicsWorld.unregisterPhysicsConnector(physicsConnector);
        // -- Deshabilitamos al cuerpo
        body.setActive(false);
        // -- Destruimos el cuerpo
        physicsWorld.destroyBody(this.body);
        // -- Removemos el sprite de la escena
        scene.detachChild(this.sprite);
    }
}
