package mx.itesm.planetz;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
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

    private org.andengine.entity.scene.menu.MenuScene scene;

    public TemporarySceneVictory(){
        super();
        sceneType = SceneType.TEMP;
    }

    @Override
    public void loadGFX() {
        resourceManager.loadYouWinResourcesGFX();


    }

    @Override
    public void loadMFX() {

    }

    @Override
    public void loadSFX() {

    }

    @Override
    public void createScene() {
        scene = new org.andengine.entity.scene.menu.MenuScene(camera);
        scene.setPosition(0, 0);

        // Se crean los bloques con las gemas dependiendo el nivel y el pregreso del usuario
        attachGems();
        // Fondo
        scene.setBackground(new SpriteBackground(0,0,0,new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2,resourceManager.YouWinBackgroundTextureRegion,vertexBufferObjectManager)));
        scene.setBackgroundEnabled(true);

        IMenuItem retryButton = new ScaleMenuItemDecorator(new SpriteMenuItem(1, resourceManager.YouWinRetryButtonTextureRegion, vertexBufferObjectManager), 0.8f, 1f);
        IMenuItem exitButton = new ScaleMenuItemDecorator(new SpriteMenuItem(2, resourceManager.YouWinExitButtonTextureRegion, vertexBufferObjectManager), 0.8f, 1f);
        IMenuItem continueButton = new ScaleMenuItemDecorator(new SpriteMenuItem(3, resourceManager.YouWinContinueButtonTextureRegion, vertexBufferObjectManager), 0.8f, 1f);

        this.setChildScene(scene);

        scene.addMenuItem(retryButton);
        scene.addMenuItem(exitButton);
        if(sessionManager.infiniteModeActivated == false) {
            scene.addMenuItem(continueButton);
        }
        retryButton.setPosition(GameManager.CAMERA_WIDTH/2+200,100);
        exitButton.setPosition(GameManager.CAMERA_WIDTH/2 +400,100);
        continueButton.setPosition(GameManager.CAMERA_WIDTH/2+580,100);




        scene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {

                switch (pMenuItem.getID()) {
                    case 1:
                        if(sessionManager.infiniteModeActivated==false){
                        switch (sessionManager.currentLevel) {
                            case 1:
                                sceneManager.destroyScene(SceneType.TEMP);
                                // -- Creamos la escena del primer nivel
                                sceneManager.createScene(SceneType.ADVENTURE_LEVEL_1);
                                // -- Corremos la escena del primer nivel
                                sceneManager.setScene(SceneType.ADVENTURE_LEVEL_1);
                                // -- Liberamos la escena actual

                                break;
                            case 2:
                                sceneManager.destroyScene(SceneType.TEMP);
                                sceneManager.createScene(SceneType.ADVENTURE_LEVEL_2);
                                sceneManager.setScene(SceneType.ADVENTURE_LEVEL_2);

                                break;
                            case 3:
                                sceneManager.destroyScene(SceneType.TEMP);
                                sceneManager.createScene(SceneType.ADVENTURE_LEVEL_3);
                                sceneManager.setScene(SceneType.ADVENTURE_LEVEL_3);

                                break;
                        }}
                        else{
                            switch (sessionManager.currentLevelInfiniteMode) {
                                case 1:
                                    sceneManager.destroyScene(SceneType.TEMP);
                                    sceneManager.createScene(SceneType.ADVENTURE_LEVEL_INF_1);
                                    sceneManager.setScene(SceneType.ADVENTURE_LEVEL_INF_1);
                                    break;
                                case 2:
                                    sceneManager.destroyScene(SceneType.TEMP);
                                    sceneManager.createScene(SceneType.ADVENTURE_LEVEL_INF_2);
                                    sceneManager.setScene(SceneType.ADVENTURE_LEVEL_INF_2);

                                    break;
                                case 3:
                                    sceneManager.destroyScene(SceneType.TEMP);
                                    sceneManager.createScene(SceneType.ADVENTURE_LEVEL_INF_3);
                                    sceneManager.setScene(SceneType.ADVENTURE_LEVEL_INF_3);

                                    break;
                            }
                        }
                        break;
                    case 2:
                        sessionManager.infiniteModeActivated = false;
                        sceneManager.destroyScene(SceneType.TEMP);
                        // -- Creamos la escena del primer nivel
                        sceneManager.createScene(SceneType.MENU);
                        // -- Corremos la escena del primer nivel
                        sceneManager.setScene(SceneType.MENU);
                        // -- Liberamos la escena actual


                        //Cambiamos el curent level al siguiente y actualizamos los desbloqueados
                        if(sessionManager.infiniteModeActivated = false){
                            switch(sessionManager.currentLevel){
                                case 1:
                                    sessionManager.unlockedLevels=1;
                                    sessionManager.currentLevel=2;
                                    break;
                                case 2:
                                    sessionManager.unlockedLevels=2;
                                    sessionManager.currentLevel=3;
                                    break;
                                case 3:
                                    sessionManager.unlockedLevels=3;
                                    sessionManager.currentLevel=4;
                                    break;

                            }// -- Creamos el story line de nuevo
                        }

                        sessionManager.writeChanges();
                        break;
                    case 3:

                        //Cambiamos el curent level al siguiente
                        switch(sessionManager.currentLevel){
                            case 1:
                                sessionManager.unlockedLevels =1;
                                sessionManager.currentLevel=2;
                                break;
                            case 2:
                                sessionManager.unlockedLevels = 2;
                                sessionManager.currentLevel=3;
                                break;
                            case 3:
                                sessionManager.unlockedLevels = 3;
                                sessionManager.currentLevel=4;
                                break;

                        }

                        // -- Creamos el story line de nuevo
                        sceneManager.destroyScene(SceneType.TEMP);
                        sceneManager.createScene(SceneType.STORY);
                        // -- Corremos la escena del primer nivel
                        sceneManager.setScene(SceneType.STORY);
                        // -- Liberamos la escena actual

                        sessionManager.writeChanges();
                        break;

                }

                return true;
            }
        });
    }

    public void attachGems(){
        //-- Creación de las gemas

        gemL1 = new Sprite(GameManager.CAMERA_WIDTH/2 -200,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemLocked1TextureRegion, vertexBufferObjectManager);
        gemL2 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemLocked2TextureRegion, vertexBufferObjectManager);
        gemL3 = new Sprite(GameManager.CAMERA_WIDTH/2 +230,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemLocked3TextureRegion, vertexBufferObjectManager);
        level1 = new Entity();
        level2 = new Entity();
        level3 = new Entity();
        //A G R E G A R - agrega las gemas a entidades vacias dependiendo de su bloqueo
        switch (sessionManager.currentLevel){
            case 1:
                // creación de las gemas del nivel 1
                gem1 = new Sprite(GameManager.CAMERA_WIDTH/2 -200,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemBlue1TextureRegion, vertexBufferObjectManager);
                gem2 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemBlue2TextureRegion, vertexBufferObjectManager);
                gem3 = new Sprite(GameManager.CAMERA_WIDTH/2 +230,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemBlue3TextureRegion, vertexBufferObjectManager);
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
                scene.attachChild(level1);
                System.out.println("prueba2");

                break;
            case 2:
                // creación de las gemas del nivel 2
                gem4 = new Sprite(GameManager.CAMERA_WIDTH/2 -200,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemPink1TextureRegion, vertexBufferObjectManager);
                gem5 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemPink2TextureRegion, vertexBufferObjectManager);
                gem6 = new Sprite(GameManager.CAMERA_WIDTH/2 +230,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemPink3TextureRegion, vertexBufferObjectManager);
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
                scene.attachChild(level2);
                break;
            case 3:
                gem7 = new Sprite(GameManager.CAMERA_WIDTH/2 -200,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemYellow1TextureRegion, vertexBufferObjectManager);
                gem8 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemYellow2TextureRegion, vertexBufferObjectManager);
                gem9 = new Sprite(GameManager.CAMERA_WIDTH/2 +230,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.YouWinGemYellow3TextureRegion, vertexBufferObjectManager);
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
                scene.attachChild(level3);
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
