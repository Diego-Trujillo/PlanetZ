package org.andengine.entity.scene.menu.item;

/**
 * Created by Diego on 11/10/2015.
 */

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.sprite.vbo.ITiledSpriteVertexBufferObject;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.modifier.IModifier;

public class ToggleSpriteMenuItem extends TiledSprite implements IMenuItem{
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

     private final int mID;

    // ===========================================================
    // Constructors
    // ===========================================================

    public ToggleSpriteMenuItem(final int pID, final ITiledTextureRegion pTiledTextureRegion,final VertexBufferObjectManager pVertexBufferObectManager){
        super(0,0,pTiledTextureRegion,pVertexBufferObectManager);

        this.mID = pID;
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================
    @Override
    public int getID() {
       return this.mID;
    }


    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
    @Override
    public void onSelected() {

    }

    @Override
    public void onUnselected() {


    }


    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
}
