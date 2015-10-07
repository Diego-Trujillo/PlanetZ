package mx.itesm.planetz;

import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.IBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.background.modifier.LoopBackgroundModifier;
import org.andengine.entity.scene.menu.item.AnimatedSpriteMenuItem;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.TiledSpriteMenuItem;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.SocketUtils;
import org.andengine.util.modifier.ParallelModifier;

/**
 * Created by Diego on 04/10/2015.
 */
public class MenuScene extends BaseScene {

    //Constantes que representan cada opción
    private final int MENU_PLAY = 0;
    private final int MENU_BACKPACK = 1;
    private static final int MENU_SETTINGS = 2;
    private static final int MENU_ABOUT = 3;

    //El menu
    private org.andengine.entity.scene.menu.MenuScene menuScene;

    //Texturas
    //Fondo
    private AutoParallaxBackground movingParallaxBackground;
    private ParallaxBackground.ParallaxEntity movingParallaxEntity;
    private Sprite backgroundSprite;

    //Logo
    private ITextureRegion logoTextureRegion;
    private Sprite logoSprite;

    //Botones
    private ITiledTextureRegion playButtonRegion;
    private ITiledTextureRegion backpackButtonRegion;
    private ITiledTextureRegion settingsButtonRegion;
    private ITiledTextureRegion aboutButtonRegion;

    public MenuScene(){
        super();
        sceneType = SceneType.MENU;
    }
    @Override
    public void loadGFX() {
        //Llamamos al administrador de recursos
        resourceManager.loadMenuResourcesGFX();

        //Fondo
        movingParallaxBackground = new AutoParallaxBackground(0f,0,0,1);
        backgroundSprite = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2,resourceManager.menuBackgroundTextureRegion);
        movingParallaxEntity = new ParallaxBackground.ParallaxEntity(-15f,backgroundSprite);
        movingParallaxBackground.attachParallaxEntity(movingParallaxEntity);


        //Logo
        logoTextureRegion = resourceManager.menuLogoBackgroundTextureRegion;
        logoSprite = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2+100,logoTextureRegion);

        //Botones
        playButtonRegion = resourceManager.buttonTextureRegion_play;
        backpackButtonRegion = resourceManager.buttonTextureRegion_backpack;
        settingsButtonRegion = resourceManager.buttonTextureRegion_settings;
        aboutButtonRegion = resourceManager.buttonTextureRegion_about;

        ;


        playButtonRegion.setCurrentTileIndex(0);
        backpackButtonRegion.setCurrentTileIndex(0);
        settingsButtonRegion.setCurrentTileIndex(0);
        aboutButtonRegion.setCurrentTileIndex(0);

    }

    @Override
    public void loadMFX() {

    }

    @Override
    public void loadSFX() {

    }

    @Override
    public void createScene() {
        setBackground(movingParallaxBackground);
        attachChild(logoSprite);
        addMenu();

    }

    public void addMenu(){
        //Inicializando el menú
        menuScene = new org.andengine.entity.scene.menu.MenuScene(camera);
        menuScene.setPosition(0,0);

        //Creando los botones
        IMenuItem playButton = new TiledSpriteMenuItem(MENU_PLAY,playButtonRegion,vertexBufferObjectManager);
        IMenuItem backpackButton = new TiledSpriteMenuItem(MENU_BACKPACK, backpackButtonRegion, vertexBufferObjectManager);
        IMenuItem settingsButton = new TiledSpriteMenuItem(MENU_SETTINGS, settingsButtonRegion, vertexBufferObjectManager);
        IMenuItem aboutButton = new TiledSpriteMenuItem(MENU_ABOUT,aboutButtonRegion,vertexBufferObjectManager);

        //Agregando las opciones al menú
        menuScene.addMenuItem(playButton);
        menuScene.addMenuItem(backpackButton);
        menuScene.addMenuItem(settingsButton);
        menuScene.addMenuItem(aboutButton);

        //Configurando fondo y animaciones
        menuScene.buildAnimations();
        menuScene.setBackgroundEnabled(false);

        //Asignando las posiciones a los botones
        playButton.setPosition(179,100);
        backpackButton.setPosition(486,100);
        settingsButton.setPosition(793,100);
        aboutButton.setPosition(1100, 100);

        menuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                switch(pMenuItem.getID()) {
                    case MENU_PLAY:
                        System.out.println("OPCION PLAY");
                        break;
                    case MENU_BACKPACK:
                        backpackButtonRegion.setCurrentTileIndex(1);
                        System.out.println("OPCION BACKPACK");
                        break;
                    case MENU_SETTINGS:
                        System.out.println("OPCION SETTINGS");
                        break;
                    case MENU_ABOUT:
                        System.out.println("OPCION ABOUT");
                        break;
                }
                return true;
            }
        });

        // Asigna este menú a la escena
        setChildScene(menuScene);

    }

    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

    }

    @Override
    public void onBackKeyPressed() {
        destroyScene();
        System.exit(0);
    }

    @Override
    public void destroyScene() {
        resourceManager.unloadMenuResources();
        this.detachSelf();
        this.dispose();
    }

}
