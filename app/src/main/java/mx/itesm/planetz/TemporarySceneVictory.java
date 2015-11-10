package mx.itesm.planetz;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

/**
 * Created by Diego on 29/10/2015.
 */
public class TemporarySceneVictory extends BaseScene {

    //------------Imagen fondo--------------
    private Sprite backgroundImageSprite;
    ParallaxBackground background;

    //------------Botones------------------
    private Sprite exitButton;
    private Sprite retryButton;
    private Sprite continueButton;
    //Background background;
    Text text1;
    Text text2;

    //--------------Gemas------------------
    Sprite gem1;Sprite gem2;Sprite gem3;Sprite gem4;Sprite gem5;Sprite gem6;
    Sprite gem7;Sprite gem8;Sprite gem9;Sprite gemL1;Sprite gemL2;Sprite gemL3;
    Sprite gemL4;Sprite gemL5;Sprite gemL6;Sprite gemL7;Sprite gemL8;Sprite gemL9;

    //-------------entidades-----------------
    Entity level1;
    Entity level2;
    Entity level3;

    public TemporarySceneVictory(){
        super();
        sceneType = SceneType.TEMP;
    }

    @Override
    public void loadGFX() {
        resourceManager.loadYouWinResourcesGFX();
        //background = new Background(0,0,0);
        text1 = new Text(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 + 100,resourceManager.fontOne,"You won!!!",vertexBufferObjectManager);
        text2 = new Text(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 - 100,resourceManager.fontOne,"This is a placeholder",vertexBufferObjectManager);
        resourceManager.loadYouLoseResourcesGFX();
        //----fondo------
        background = new ParallaxBackground(0, 0, 0);
        backgroundImageSprite = resourceManager.loadSprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2,resourceManager.YouWinBackgroundTextureRegion);
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
        // Se crean los bloques con las gemas dependiendo el nivel y el pregreso del usuario
        attachGems();
        // Fondo
        this.setBackground(background);
        this.setBackgroundEnabled(true);

        retryButton = new Sprite(GameManager.CAMERA_WIDTH/2,150,resourceManager.YouWinRetryButtonTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y){
                //Volviendo a jugar
                if (pSceneTouchEvent.isActionUp()) {
                    switch (sessionManager.currentLevel){
                        case 1:
                            // -- Creamos la escena del primer nivel
                            sceneManager.createScene(SceneType.ADVENTURE_LEVEL_1);
                            // -- Corremos la escena del primer nivel
                            sceneManager.setScene(SceneType.ADVENTURE_LEVEL_1);
                            // -- Liberamos la escena actual
                            sceneManager.destroyScene(SceneType.TEMP);
                            break;
                        case 2:
                            sceneManager.createScene(SceneType.ADVENTURE_LEVEL_1);
                            sceneManager.setScene(SceneType.ADVENTURE_LEVEL_1);
                            sceneManager.destroyScene(SceneType.TEMP);
                            break;
                        case 3:
                            sceneManager.createScene(SceneType.ADVENTURE_LEVEL_1);
                            sceneManager.setScene(SceneType.ADVENTURE_LEVEL_1);
                            sceneManager.destroyScene(SceneType.TEMP);
                            break;
                    }

                }return true;}};

        exitButton = new Sprite(GameManager.CAMERA_WIDTH/2 +200,150, resourceManager.YouWinExitButtonTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y){
                //Regresando al menu principal
                if (pSceneTouchEvent.isActionUp()) {
                    // -- Creamos la escena del primer nivel
                    sceneManager.createScene(SceneType.MENU);
                    // -- Corremos la escena del primer nivel
                    sceneManager.setScene(SceneType.MENU);
                    // -- Liberamos la escena actual
                    sceneManager.destroyScene(SceneType.TEMP);
                }
                return true;}
        };
        continueButton = new Sprite(GameManager.CAMERA_WIDTH/2+1000,150, resourceManager.YouWinContinueButtonTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y){
                //Regresando al menu principal
                if (pSceneTouchEvent.isActionUp()) {
                    // -- Creamos la escena del primer nivel
                    //sceneManager.createScene(SceneType.MENU);
                    // -- Corremos la escena del primer nivel
                    //sceneManager.setScene(SceneType.MENU);
                    // -- Liberamos la escena actual
                    //sceneManager.destroyScene(SceneType.YOU_LOSE);
                }

                return true;}
        };

        this.attachChild(retryButton);
        this.attachChild(exitButton);
        this.registerTouchArea(retryButton);
        this.registerTouchArea(exitButton);



        //this.attachChild(text1);
        //this.attachChild(text2);


    }
    public void attachGems(){
        //-- Creación de las gemas

        gemL1 = new Sprite(GameManager.CAMERA_WIDTH/2 -300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemLocked1TextureRegion, vertexBufferObjectManager);
        gemL2 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemLocked2TextureRegion, vertexBufferObjectManager);
        gemL3 = new Sprite(GameManager.CAMERA_WIDTH/2 +300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemLocked3TextureRegion, vertexBufferObjectManager);
        /*gemL4 = new Sprite(GameManager.CAMERA_WIDTH/2 -300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemLocked1TextureRegion, vertexBufferObjectManager);
        gemL5 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemLocked2TextureRegion, vertexBufferObjectManager);
        gemL6 = new Sprite(GameManager.CAMERA_WIDTH/2 +300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemLocked3TextureRegion, vertexBufferObjectManager);
        gemL7 = new Sprite(GameManager.CAMERA_WIDTH/2 -300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemLocked1TextureRegion, vertexBufferObjectManager);
        gemL8 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemLocked2TextureRegion, vertexBufferObjectManager);
        gemL9 = new Sprite(GameManager.CAMERA_WIDTH/2 +300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemLocked3TextureRegion, vertexBufferObjectManager);
*/
        level1 = new Entity();
        level2 = new Entity();
        level3 = new Entity();
        //A G R E G A R - agrega las gemas a entidades vacias dependiendo de su bloqueo
        switch (sessionManager.currentLevel){
            case 1:
                // creación de las gemas del nivel 1
                gem1 = new Sprite(GameManager.CAMERA_WIDTH/2 -300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemBlue1TextureRegion, vertexBufferObjectManager);
                gem2 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemBlue2TextureRegion, vertexBufferObjectManager);
                gem3 = new Sprite(GameManager.CAMERA_WIDTH/2 +300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemBlue3TextureRegion, vertexBufferObjectManager);
                //agregar las gemas a la entidad
                if(sessionManager.gemsUnlocked[1][1]==true){
                    level1.attachChild(gem1);}
                if(sessionManager.gemsUnlocked[1][1]==false){
                    level1.attachChild(gemL1); }
                if(sessionManager.gemsUnlocked[1][2]==true){
                    level1.attachChild(gem2);}
                if(sessionManager.gemsUnlocked[1][2]==false){
                    level1.attachChild(gemL2);}
                if(sessionManager.gemsUnlocked[1][3]==true){
                    level1.attachChild(gem3);}
                if(sessionManager.gemsUnlocked[1][3]==false){
                    level1.attachChild(gemL3);}
                System.out.println("prueba1");
                gem1.setVisible(true);
                // agregamos la entidad
                attachChild(level1);
                System.out.println("prueba2");

                break;
            case 2:
                // creación de las gemas del nivel 2
                gem4 = new Sprite(GameManager.CAMERA_WIDTH/2 -300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemPink1TextureRegion, vertexBufferObjectManager);
                gem5 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemPink2TextureRegion, vertexBufferObjectManager);
                gem6 = new Sprite(GameManager.CAMERA_WIDTH/2 +300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemPink3TextureRegion, vertexBufferObjectManager);
                //agregar las gemas a la entidad
                if(sessionManager.gemsUnlocked[2][1]==true){
                    level2.attachChild(gem4);}
                if(sessionManager.gemsUnlocked[2][1]==false){
                    level2.attachChild(gemL1); }
                if(sessionManager.gemsUnlocked[2][2]==true){
                    level2.attachChild(gem5);}
                if(sessionManager.gemsUnlocked[2][2]==false){
                    level2.attachChild(gemL2);}
                if(sessionManager.gemsUnlocked[2][3]==true){
                    level2.attachChild(gem6);}
                if(sessionManager.gemsUnlocked[2][3]==false){
                    level2.attachChild(gemL3);}

                // agregamos la entidad
                attachChild(level2);
                break;
            case 3:
                gem7 = new Sprite(GameManager.CAMERA_WIDTH/2 -300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemYellow1TextureRegion, vertexBufferObjectManager);
                gem8 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemYellow2TextureRegion, vertexBufferObjectManager);
                gem9 = new Sprite(GameManager.CAMERA_WIDTH/2 +300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemYellow3TextureRegion, vertexBufferObjectManager);
                if(sessionManager.gemsUnlocked[3][1]==true){
                    level3.attachChild(gem7);}
                if(sessionManager.gemsUnlocked[3][1]==false){
                    level3.attachChild(gemL1); }
                if(sessionManager.gemsUnlocked[3][2]==true){
                    level3.attachChild(gem8);}
                if(sessionManager.gemsUnlocked[3][2]==false){
                    level3.attachChild(gemL2);}
                if(sessionManager.gemsUnlocked[3][3]==true){
                    level3.attachChild(gem9);}
                if(sessionManager.gemsUnlocked[3][3]==false){
                    level3.attachChild(gemL3);}

                // agregamos la entidad
                attachChild(level3);
                break;

    }}

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
