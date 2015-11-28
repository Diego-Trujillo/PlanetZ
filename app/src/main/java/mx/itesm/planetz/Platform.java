package mx.itesm.planetz;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
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

    // ===========================================================
    //                 Elementos gráficos
    // ===========================================================
    private BitmapTextureAtlas astronautBitmapTextureAtlas;
    private ITiledTextureRegion astronautTextureRegion;
    private AnimatedSprite astronautSprite;
    private Entity objectOfDesire;

    // ===========================================================
    //                 Elementos de Física
    // ===========================================================
    public Body astronautBody;
    private final FixtureDef ASTRONAUT_FIXTURE_DEFINITION = PhysicsFactory.createFixtureDef(1000.f, 0.1f, 0.5f);

    // ===========================================================
    //                 Elementos de Miscelaneos
    // ===========================================================
}
