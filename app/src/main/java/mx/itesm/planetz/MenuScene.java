package mx.itesm.planetz;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
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

    // =============================================================================================
    //                     D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================

    // ===========================================================
    //                     Elementos del fondo
    // ===========================================================

    // ============== Fondo de las estrellas =====================
    private AutoParallaxBackground movingParallaxBackground;
    private ParallaxBackground.ParallaxEntity movingParallaxEntity;
    private Sprite backgroundSprite;

    // =============== Planeta ===================================
    private ITextureRegion planetTextureRegion;
    private Sprite planetSprite;

    // =============== Logotipo ==================================
    private ITextureRegion logoTextureRegion;
    private Sprite logoSprite;


    // ===========================================================
    //                      Menú Principal
    // ===========================================================

    // =============== El Contenedor =============================
    private org.andengine.entity.scene.menu.MenuScene mainMenuScene;

    // =============== Bandera sobre disponibilidad ==============
    private boolean mainMenuEnabled;

    // =============== Opciones de Botones =======================
    private static final int MAIN_PLAY = 0;
    private static final int MAIN_BACKPACK = 1;
    private static final int MAIN_SETTINGS = 2;
    private static final int MAIN_ABOUT = 3;

    // =============== Texturas de los Botones=====================
    private ITiledTextureRegion mainMenuPlayButtonRegion;
    private ITiledTextureRegion mainMenuBackpackButtonRegion;
    private ITiledTextureRegion mainMenuSettingsButtonRegion;
    private ITiledTextureRegion mainMenuAboutButtonRegion;



    // =============================================================================================
    //                                    C O N S T R U C T O R
    // =============================================================================================

    public MenuScene(){
        super();
        sceneType = SceneType.MENU;
    }

    // =============================================================================================
    //                                       M É T O D O S
    // =============================================================================================

    // ===========================================================
    //                 Cargar recursos gráficos
    // ===========================================================
    @Override
    public void loadGFX() {
        // =======================================================
        //              Cargar los elementos del fondo
        // =======================================================
        // =============== Llamar al administrador de recursos ===
        resourceManager.loadMenuResourcesGFX();

        // =============== Fondo de estrellas ====================
        movingParallaxBackground = new AutoParallaxBackground(0f,0,0,1);
        backgroundSprite = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2,resourceManager.menuBackgroundTextureRegion);
        movingParallaxEntity = new ParallaxBackground.ParallaxEntity(15f,backgroundSprite);
        movingParallaxBackground.attachParallaxEntity(movingParallaxEntity);

        // =============== Planeta giratorio ====================
        planetTextureRegion = resourceManager.menuPlanetTextureRegion;
        planetSprite = resourceManager.loadSprite(0,0,planetTextureRegion);


        // =============== Logotipo del juego ===================
        logoTextureRegion = resourceManager.menuLogoBackgroundTextureRegion;
        logoSprite = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2+100,logoTextureRegion);

        // =======================================================
        //                  Menú principal
        // =======================================================

        // =============== Botones del menú principal ============
        mainMenuPlayButtonRegion = resourceManager.buttonTextureRegion_play;
        mainMenuBackpackButtonRegion = resourceManager.buttonTextureRegion_backpack;
        mainMenuSettingsButtonRegion = resourceManager.buttonTextureRegion_settings;
        mainMenuAboutButtonRegion = resourceManager.buttonTextureRegion_about;

        mainMenuPlayButtonRegion.setCurrentTileIndex(0);
        mainMenuBackpackButtonRegion.setCurrentTileIndex(0);
        mainMenuSettingsButtonRegion.setCurrentTileIndex(0);
        mainMenuAboutButtonRegion.setCurrentTileIndex(0);

        // =============== Disponibilidad del menú ===============
        mainMenuEnabled = true;
    }

    // ===========================================================
    //                      Cargar Música
    // ===========================================================

    @Override
    public void loadMFX() {

    }

    // ===========================================================
    //                      Cargar Sonidos
    // ===========================================================
    @Override
    public void loadSFX() {

    }

    // ===========================================================
    //                      Crear Escena
    // ===========================================================
    @Override
    public void createScene() {
        // =============== Adjuntar el fondo móvil ===============
        setBackground(movingParallaxBackground);

        // =============== Adjuntar el planeta y darle rotación ==
        attachChild(planetSprite);
        planetSprite.setPosition(gameManager.CAMERA_WIDTH / 2, -100);
        attachChild(logoSprite);
        planetSprite.registerEntityModifier(new LoopEntityModifier(new RotationModifier(90,0,-360)));

        // =============== Agregar la sub-escena del menú ========
        addMainMenu();
        setChildScene(mainMenuScene);

    }

    // ===========================================================
    //                Crear el menú principal
    // ===========================================================
    public void addMainMenu(){
        // ===============  Inicializando la subescena ===========
        mainMenuScene = new org.andengine.entity.scene.menu.MenuScene(camera);
        mainMenuScene.setPosition(0,0);

        // =============== Creando los botones ===================
        IMenuItem playButton = new TiledSpriteMenuItem(MAIN_PLAY,mainMenuPlayButtonRegion,vertexBufferObjectManager);
        IMenuItem backpackButton = new TiledSpriteMenuItem(MAIN_BACKPACK, mainMenuBackpackButtonRegion, vertexBufferObjectManager);
        IMenuItem settingsButton = new TiledSpriteMenuItem(MAIN_SETTINGS, mainMenuSettingsButtonRegion, vertexBufferObjectManager);
        IMenuItem aboutButton = new TiledSpriteMenuItem(MAIN_ABOUT,mainMenuAboutButtonRegion,vertexBufferObjectManager);

        // =============== Agregando los botones =================
        mainMenuScene.addMenuItem(playButton);
        mainMenuScene.addMenuItem(backpackButton);
        mainMenuScene.addMenuItem(settingsButton);
        mainMenuScene.addMenuItem(aboutButton);

        // =============== Configurando las animaciones =========
        mainMenuScene.buildAnimations();
        mainMenuScene.setBackgroundEnabled(false);

        // =============== Ubicando los botones =================
        playButton.setPosition(179,100);
        backpackButton.setPosition(486,100);
        settingsButton.setPosition(793,100);
        aboutButton.setPosition(1100, 100);

        // === Establece lo que sea realizará al presionar b. ===
        mainMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()) {
                    case MAIN_PLAY:
                        System.out.println("OPCION PLAY");
                        break;
                    case MAIN_BACKPACK:
                        System.out.println("OPCION BACKPACK");
                        break;
                    case MAIN_SETTINGS:
                        System.out.println("OPCION SETTINGS");
                        break;
                    case MAIN_ABOUT:
                        System.out.println("OPCION ABOUT");
                        break;
                }

                // Remover la habilidad de presionar botones, y esconder este menú.
                unregisterTouchArea(mainMenuScene);
                System.out.println("UNEISTED");
                registerTouchArea(mainMenuScene);
                System.out.println("registed");

                return true;

            }
        });


    }

    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

    }

    // ===========================================================
    //            Cuando se presiona la tecla retroceder
    // ===========================================================
    @Override
    public void onBackKeyPressed() {
        // =============== Destruír la escena y salir del juego ==
        destroyScene();
        System.exit(0);
    }

    // ===========================================================
    //                      Destruir la escena actual
    // ===========================================================
    @Override
    public void destroyScene() {
        resourceManager.unloadMenuResources();
        this.detachSelf();
        this.dispose();
    }

}
