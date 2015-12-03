package mx.itesm.planetz;

import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

/**
 * Created by Andrea Pérez on 28/10/2015.
 */
public class YouLose extends BaseScene {

    //------------Imagen fondo--------------
    private Sprite backgroundImageSprite;
    ParallaxBackground background;

    //------------Botones------------------
    private Sprite exitButton;
    private Sprite retryButton;

    // =============================================================================================
    public YouLose() {
        // ============== Iniciamos la super-clase ===============
        super();
        // ============== Asignamos el tipo de escena ============
        sceneType = SceneType.YOU_LOSE;
    }

    @Override
    public void loadGFX() {
        resourceManager.loadYouLoseResourcesGFX();
        //----fondo------
        background = new ParallaxBackground(0, 0, 0);
        backgroundImageSprite = resourceManager.loadSprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2,resourceManager.YouLoseBackgroundTextureRegion);
        background.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(0,backgroundImageSprite));

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

        retryButton = new Sprite(GameManager.CAMERA_WIDTH/2,150,resourceManager.YouLoseRetryButtonTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y){
                //Volviendo a jugar
                if (pSceneTouchEvent.isActionUp()) {
                    if(sessionManager.infiniteModeActivated==false) {
                        switch (sessionManager.currentLevel) {
                            case 1:
                                // -- Creamos la escena del primer nivel
                                sceneManager.destroyScene(SceneType.YOU_LOSE);
                                sceneManager.createScene(SceneType.ADVENTURE_LEVEL_1);
                                // -- Corremos la escena del primer nivel
                                sceneManager.setScene(SceneType.ADVENTURE_LEVEL_1);
                                // -- Liberamos la escena actual

                                break;
                            case 2:
                                sceneManager.destroyScene(SceneType.YOU_LOSE);
                                sceneManager.createScene(SceneType.ADVENTURE_LEVEL_2);
                                sceneManager.setScene(SceneType.ADVENTURE_LEVEL_2);
                                break;
                            case 3:
                                sceneManager.destroyScene(SceneType.YOU_LOSE);
                                sceneManager.createScene(SceneType.ADVENTURE_LEVEL_3);
                                sceneManager.setScene(SceneType.ADVENTURE_LEVEL_3);

                                break;
                        }
                    }
                    else{
                        switch (sessionManager.currentLevelInfiniteMode) {
                            case 1:
                                sceneManager.destroyScene(SceneType.YOU_LOSE);
                                // -- Creamos la escena del primer nivel
                                sceneManager.createScene(SceneType.ADVENTURE_LEVEL_INF_1);
                                // -- Corremos la escena del primer nivel
                                sceneManager.setScene(SceneType.ADVENTURE_LEVEL_INF_1);
                                // -- Liberamos la escena actual

                                break;
                            case 2:
                                sceneManager.destroyScene(SceneType.YOU_LOSE);
                                sceneManager.createScene(SceneType.ADVENTURE_LEVEL_INF_2);
                                sceneManager.setScene(SceneType.ADVENTURE_LEVEL_INF_2);

                                break;
                            case 3:
                                sceneManager.destroyScene(SceneType.YOU_LOSE);
                                sceneManager.createScene(SceneType.ADVENTURE_LEVEL_INF_3);
                                sceneManager.setScene(SceneType.ADVENTURE_LEVEL_INF_3);

                                break;
                        }
                    }
        }return true;}};

        exitButton = new Sprite(GameManager.CAMERA_WIDTH/2 +400,150, resourceManager.YouLoseExitButtonTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y){
                //Regresando al menu principal
                if (pSceneTouchEvent.isActionUp()) {
                    sessionManager.infiniteModeActivated = false;
                    // -- Creamos la escena del primer nivel
                    sceneManager.createScene(SceneType.MENU);
                    // -- Corremos la escena del primer nivel
                    sceneManager.setScene(SceneType.MENU);
                    // -- Liberamos la escena actual
                    sceneManager.destroyScene(SceneType.YOU_LOSE);
                }
                sessionManager.writeChanges();
                return true;}
        };
        this.attachChild(retryButton);
        this.attachChild(exitButton);
        this.registerTouchArea(retryButton);
        this.registerTouchArea(exitButton);

    }



    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void destroyScene() {

    }
}
