package mx.itesm.planetz;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.menu.*;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;

import java.util.ArrayList;

/**
 * Created by Diego on 29/10/2015.
 */
public class StoryScene extends BaseScene {

    private ArrayList<ITextureRegion> storyLevelOneTextureContainer;
    private ArrayList<Sprite> storyLevelOneSpriteContainer;

    int index;
    private HUD hudThing;

    public StoryScene(){
        super();
        sceneType = SceneType.STORY;
    }

    @Override
    public void loadGFX() {
        storyLevelOneSpriteContainer = new ArrayList<>();
        storyLevelOneTextureContainer = new ArrayList<>();

        for(int i = 1; i <= 6 ; i++){
            storyLevelOneTextureContainer.add(resourceManager.loadImage("gfx/Level1/SketchesComic/"+Integer.toString(i)+".jpg"));
            storyLevelOneSpriteContainer.add(resourceManager.loadSprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2,storyLevelOneTextureContainer.get(i-1)));
        }
    }

    @Override
    public void loadMFX() {

    }

    @Override
    public void loadSFX() {

    }

    @Override
    public void createScene() {

        index = 0;

        for(Sprite sp: storyLevelOneSpriteContainer){
            this.attachChild(sp);
            sp.setVisible(false);
        }
        storyLevelOneSpriteContainer.get(index).setVisible(true);

        Sprite flechaDerecha = new Sprite(GameManager.CAMERA_WIDTH - 100,GameManager.CAMERA_HEIGHT - 150, resourceManager.loadImage("gfx/menu/buttons/arrow_right.png"),vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y)
            {
                // Cuando el jugador toque el ID, llamamos la función setAboutID con ÉSTE id.
                if (pSceneTouchEvent.isActionUp()) {
                    System.out.println("je");
                    if(index < 6-1){
                        storyLevelOneSpriteContainer.get(index++).setVisible(false);
                        storyLevelOneSpriteContainer.get(index).setVisible(true);
                    }
                    else{
                        sceneManager.createScene(SceneType.ADVENTURE_LEVEL_1);
                        // -- Corremos la escena del primer nivel
                        sceneManager.setScene(SceneType.ADVENTURE_LEVEL_1);
                        // -- Liberamos la escena actual
                        sceneManager.destroyScene(SceneType.STORY);
                    }
                }
                return true;
            }
        };

        this.attachChild(flechaDerecha);
        this.registerTouchArea(flechaDerecha);

       // this.attachChild(hudThing);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void destroyScene() {
        for(Sprite sp : storyLevelOneSpriteContainer){
            sp.detachSelf();
            sp.dispose();
        }

        this.detachChildren();
        this.dispose();
    }
}
