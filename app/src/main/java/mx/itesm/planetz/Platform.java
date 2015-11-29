package mx.itesm.planetz;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

/**
 * Created by Diego on 28/11/2015.
 */
public class Platform {
    // =============================================================================================
    //                     D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================
    // ===========================================================
    //                 Elementos generales
    // ===========================================================
    // -------------- La escena del juego ------------------------
    private BaseScene gameScene;
    // -------------- El mundo de física ------------------------
    private PhysicsWorld physicsWorld;

    // ===========================================================
    //                 Elementos gráficos
    // ===========================================================
    private Sprite platformSprite;

    // ===========================================================
    //                 Elementos de Física
    // ===========================================================
    public Body platformBody;
    private static final FixtureDef PLATFORM_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(0, 0, 0);


    // ===========================================================
    //                Opciones de Spawn
    // ===========================================================
    //public static final int;

    // ===========================================================
    //                 Elementos de Miscelaneos
    // ===========================================================

    // =============================================================================================
    //                                    C O N S T R U C T O R
    // =============================================================================================
    /*public Platform(BaseScene gameScene,PhysicsWorld physicsWorld,int currentLevel, ){
        this.gameScene = gameScene;
        this.physicsWorld = physicsWorld;




        platformSprite.setCullingEnabled(true);
    }

*/
}
