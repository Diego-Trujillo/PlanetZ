package mx.itesm.planetz;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;

/**
 * Created by Diego on 29/10/2015.
 */
public class TemporarySceneVictory extends BaseScene {

    Background background;
    Text text1;
    Text text2;

    public TemporarySceneVictory(){
        super();
        sceneType = SceneType.TEMP;
    }

    @Override
    public void loadGFX() {
        background = new Background(0,0,0);
        text1 = new Text(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 + 100,resourceManager.fontOne,"You won!!!",vertexBufferObjectManager);
        text2 = new Text(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 - 100,resourceManager.fontOne,"This is a placeholder",vertexBufferObjectManager);
    }

    @Override
    public void loadMFX() {

    }

    @Override
    public void loadSFX() {

    }

    @Override
    public void createScene() {
        this.setBackground(background);
        this.setBackgroundEnabled(true);
        this.attachChild(text1);
        this.attachChild(text2);
    }

    @Override
    public void onBackKeyPressed() {
        // -- Creamos la escena del primer nivel
        sceneManager.createScene(SceneType.MENU);
        // -- Corremos la escena del primer nivel
        sceneManager.setScene(SceneType.MENU);
        // -- Liberamos la escena actual
        sceneManager.destroyScene(SceneType.TEMP);
    }

    @Override
    public void destroyScene() {
        this.detachSelf();
        this.dispose();
    }
}
