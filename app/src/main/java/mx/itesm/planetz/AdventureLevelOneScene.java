package mx.itesm.planetz;

import android.hardware.SensorManager;
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

import org.andengine.engine.handler.collision.CollisionHandler;
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

import java.util.ArrayList;
import java.util.Random;

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

    // -------------- Escucha los contactos ----------------------
    private ContactListener contactListener;

    //----------------Tags objetos-------------------------
    final int TAG_SHIP= 1;
    final int TAG_METEORS = 2;
    final int TAG_GEMS =3;

    // ============== Definición de Fijadores de física ==========
    final FixtureDef WALL_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(0,0.1f,0.5f);
    final FixtureDef SHIP_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(10.f,0.1f,0.5f);
    final FixtureDef METEORE_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(15f,0,0.5f);

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


    public AdventureLevelOneScene(){
        super();
        sceneType = SceneType.ADVENTURE_LEVEL_1;

    }


    @Override
    public void loadGFX() {
        meteorsArrayList = new ArrayList<ITextureRegion>();
        naveRegion = resourceManager.loadImage("gfx/galaxia_play.png");
        meteoreRegion1 = resourceManager.loadImage("gfx/Level1/Meteors/1.png");
        meteoreRegion2 = resourceManager.loadImage("gfx/Level1/Meteors/2.png");
        meteoreRegion3 = resourceManager.loadImage("gfx/Level1/Meteors/3.png");
        meteorsArrayList.add(meteoreRegion1);
        meteorsArrayList.add(meteoreRegion2);
        meteorsArrayList.add(meteoreRegion3);

    }

    @Override
    public void loadMFX() {

    }

    @Override
    public void loadSFX() {

    }

    @Override
    public void createScene() {
        physicsWorld = new PhysicsWorld(new Vector2(0,0),false);

        this.registerUpdateHandler(physicsWorld);


        naveSprite = new Sprite(100, GameManager.CAMERA_HEIGHT/2,naveRegion,vertexBufferObjectManager);


        gameManager.getEngine().enableAccelerationSensor(gameManager,this);

        final Rectangle rekt = new Rectangle(0,GameManager.CAMERA_HEIGHT/2,10,GameManager.CAMERA_HEIGHT, vertexBufferObjectManager);
        rekt.setColor(1f, 1f, 1f);
        final Rectangle rektt = new Rectangle(GameManager.CAMERA_WIDTH-10, GameManager.CAMERA_HEIGHT/2,10,GameManager.CAMERA_HEIGHT, vertexBufferObjectManager);
        rektt.setColor(1f,1f,1f);
        leftWallBody = PhysicsFactory.createBoxBody(physicsWorld, rekt, BodyDef.BodyType.StaticBody, WALL_FIXTURE_DEFINITION);
        rightWallBody = PhysicsFactory.createBoxBody(physicsWorld, rektt, BodyDef.BodyType.StaticBody, WALL_FIXTURE_DEFINITION);
        shipBody = PhysicsFactory.createCircleBody(physicsWorld, naveSprite, BodyDef.BodyType.DynamicBody, SHIP_FIXTURE_DEFINITION);
        naveSprite.setTag(TAG_SHIP);

        physicsWorld.registerPhysicsConnector(new PhysicsConnector(naveSprite,shipBody,true,false));
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(rekt,leftWallBody,false,false));
        physicsWorld.registerPhysicsConnector(new PhysicsConnector(rektt,rightWallBody,false,false));
        this.attachChild(naveSprite);
        this.attachChild(rekt);
        this.attachChild(rektt);
        for(int i=0; i<6; i++){
            createMeteorite();
        }
    }

    private void createMeteorite(){
        int num1= (int)Math.random() * ( 4 -  1);
        float num2 = (float)Math.random() *(GameManager.CAMERA_WIDTH -100);
        Sprite meteor;
        Body meteorite;
        meteor = new Sprite(num2,GameManager.CAMERA_HEIGHT-200,meteorsArrayList.get(num1), vertexBufferObjectManager);
        meteor.registerEntityModifier(new MoveYModifier(5.0f,GameManager.CAMERA_HEIGHT,-100));
        meteor.registerEntityModifier(new RotationModifier(5.0f,0,360));
        meteor.setTag(TAG_METEORS);
        meteorite = PhysicsFactory.createCircleBody(physicsWorld, naveSprite, BodyDef.BodyType.KinematicBody, METEORE_FIXTURE_DEFINITION);
        meteorite.setTransform(num2,-100,90);
        //physicsWorld.registerPhysicsConnector(new PhysicsConnector(meteor,meteorite,false,false));
        this.attachChild(meteor);
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
       /*if(naveSprite.getY() >= 0 && naveSprite.getY() <= GameManager.CAMERA_HEIGHT){
            naveSprite.setY(naveSprite.getY() + pAccelerationData.getY()*5);

        }
        else if(naveSprite.getY() > GameManager.CAMERA_HEIGHT){
            naveSprite.setY(GameManager.CAMERA_HEIGHT+1);
        }
        else {
            naveSprite.setY(0);
        }*/

        //final Vector2 gravity = Vector2Pool.obtain(0,pAccelerationData.getY()*5);
        //this.physicsWorld.setGravity(gravity);

        //Vector2Pool.recycle(gravity);
        shipBody.setLinearVelocity(pAccelerationData.getX()*4,0);

    }
}
