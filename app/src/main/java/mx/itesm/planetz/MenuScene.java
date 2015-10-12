package mx.itesm.planetz;

import org.andengine.entity.modifier.FadeInModifier;
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
import org.andengine.entity.scene.menu.item.ToggleSpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.SocketUtils;
import org.andengine.util.modifier.ParallelModifier;

/**
 *  Contiene la escena del menú principal.
 *
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

    // =============== Overlays para los submenús ===============
    private ITextureRegion menuOverlayTextureRegion;
    private Sprite menuOverlaySprite;

    // =============== Botón Back para Submenús =================
    private ITextureRegion menuSubmenuBackButtonRegion;

    // ===========================================================
    //                   MENÚ PRINCIPAL
    // ===========================================================

    // =============== El Contenedor =============================
    private org.andengine.entity.scene.menu.MenuScene mainMenuScene;

    // =============== Opciones de Botones =======================
    private static final int MAIN_PLAY = 0;
    private static final int MAIN_BACKPACK = 1;
    private static final int MAIN_SETTINGS = 2;
    private static final int MAIN_ABOUT = 3;
    private static final int MAIN_TOGGLE_AUDIO = 4;

    // =============== Texturas de los Botones ====================
    private ITiledTextureRegion mainMenuPlayButtonRegion;
    private ITiledTextureRegion mainMenuBackpackButtonRegion;
    private ITiledTextureRegion mainMenuSettingsButtonRegion;
    private ITiledTextureRegion mainMenuAboutButtonRegion;
    private ITiledTextureRegion mainMenuToggleAudioButtonRegion;


    // =============== OPCIÓN REGRESAR DE SUBMENUS =================
    // =============================================================
    private static final int SUBMENU_BACK = 0;


    // ===========================================================
    //                   SUBMENÚ PLAY
    // ===========================================================

    // =============== El Contenedor =============================
    private org.andengine.entity.scene.menu.MenuScene playMenuScene;

    // =============== Opciones de Botones =======================
    private static final int PLAY_ADVENTURE_MODE = 1;
    private static final int PLAY_ADVENTURE_MODE_NEWGAME = 3;
    private static final int PLAY_ADVENTURE_MODE_CONTINUE = 4;
    private static final int PLAY_INFINITE_MODE = 2;

    // =============== Texturas de Botones ======================


    // ===========================================================
    //                   SUBMENÚ BACKPACK
    // ===========================================================

    // =============== El Contenedor =============================
    private org.andengine.entity.scene.menu.MenuScene backPackMenuScene;

    // =============== Opciones de Botones =======================

    // =============== Texturas de Botones ==============

    // ===========================================================
    //                   SUBMENÚ SETTINGS
    // ===========================================================

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

        // =============== Overlay para los submenús ============
        menuOverlayTextureRegion = resourceManager.menuOverlayTextureRegion;
        menuOverlaySprite = resourceManager.loadSprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2,menuOverlayTextureRegion);

        // ============== Botón Back Submenús ========================
        menuSubmenuBackButtonRegion = resourceManager.menuSubmenuBackButtonTextureRegion;

        // =======================================================
        //                  Menú principal
        // =======================================================

        // =============== Botones del menú principal ============
        mainMenuPlayButtonRegion = resourceManager.mainMenuButtonTextureRegion_play;
        mainMenuBackpackButtonRegion = resourceManager.mainMenuButtonTextureRegion_backpack;
        mainMenuSettingsButtonRegion = resourceManager.mainMenuButtonTextureRegion_settings;
        mainMenuAboutButtonRegion = resourceManager.mainMenuButtonTextureRegion_about;
        mainMenuToggleAudioButtonRegion = resourceManager.menuToggleAudioButtonTextureRegion;

        // =======================================================
        //                  Submenú Play
        // =======================================================



    }

    // ===========================================================
    //                      Cargar Música
    // ===========================================================

    @Override
    public void loadMFX() {
        resourceManager.loadMenuResourcesMFX();
    }

    // ===========================================================
    //                      Cargar Sonidos
    // ===========================================================
    @Override
    public void loadSFX() {}

    // ===========================================================
    //                      Crear Escena
    // ===========================================================
    @Override
    public void createScene() {
        // =============== Adjuntar el fondo móvil ===============
        setBackground(movingParallaxBackground);

        // =============== Adjuntar el planeta y darle rotación ==
        attachChild(planetSprite);
        planetSprite.setPosition(gameManager.CAMERA_WIDTH / 2, -gameManager.CAMERA_HEIGHT + 200);
        planetSprite.registerEntityModifier(new LoopEntityModifier(new RotationModifier(250, 0, -360)));

        // =============== Adjuntar y esconder el overlay ========
        attachChild(menuOverlaySprite);

        // =============== Agregar los menus y subemnús =========
        addMainMenu();
        addPlayMenu();

        // ============== Asignar el menú principal =============

        setChildScene(mainMenuScene);

        // =============== Reproducir música de fondo ============
        resourceManager.menuMusic.play();
    }

    // ===========================================================
    //                Crear el menú principal
    // ===========================================================
    public void addMainMenu(){
        // ===============  Inicializando la subescena ===========
        mainMenuScene = new org.andengine.entity.scene.menu.MenuScene(camera);
        mainMenuScene.setPosition(0,0);

        // =============== Creando los botones ===================
        IMenuItem mainMenuPlayButton = new TiledSpriteMenuItem(MAIN_PLAY,mainMenuPlayButtonRegion,vertexBufferObjectManager);
        IMenuItem mainMenuBackpackButton = new TiledSpriteMenuItem(MAIN_BACKPACK, mainMenuBackpackButtonRegion, vertexBufferObjectManager);
        IMenuItem mainMenuSettingsButton = new TiledSpriteMenuItem(MAIN_SETTINGS, mainMenuSettingsButtonRegion, vertexBufferObjectManager);
        IMenuItem mainMenuAboutButton = new TiledSpriteMenuItem(MAIN_ABOUT,mainMenuAboutButtonRegion,vertexBufferObjectManager);
        IMenuItem mainMenuToggleAudioButton = new ToggleSpriteMenuItem(MAIN_TOGGLE_AUDIO,mainMenuToggleAudioButtonRegion,vertexBufferObjectManager);
        ((ToggleSpriteMenuItem) mainMenuToggleAudioButton).setCurrentTileIndex((sessionManager.musicEnabled && sessionManager.soundEnabled) ? 0 : 1);

        // =============== Agregando los botones =================
        mainMenuScene.addMenuItem(mainMenuPlayButton);
        mainMenuScene.addMenuItem(mainMenuBackpackButton);
        mainMenuScene.addMenuItem(mainMenuSettingsButton);
        mainMenuScene.addMenuItem(mainMenuAboutButton);
        mainMenuScene.addMenuItem(mainMenuToggleAudioButton);

        // =============== Configurando las animaciones =========
        mainMenuScene.buildAnimations();
        menuOverlaySprite.setVisible(false);
        mainMenuScene.setBackgroundEnabled(false);

        // =============== Ubicando los botones =================
        mainMenuScene.attachChild(logoSprite);

        // =============== Ubicando los botones =================
        mainMenuPlayButton.setPosition(179,100);
        mainMenuBackpackButton.setPosition(486, 100);
        mainMenuSettingsButton.setPosition(793, 100);
        mainMenuAboutButton.setPosition(1100, 100);
        mainMenuToggleAudioButton.setPosition(GameManager.CAMERA_WIDTH - 64, GameManager.CAMERA_HEIGHT - 64);




        // === Establece lo que sea realizará al presionar b. ===
        mainMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()) {
                    case MAIN_PLAY:
                        System.out.println("OPCION PLAY");
                        setChildScene(playMenuScene);
                        menuOverlaySprite.setVisible(true);
                    case MAIN_BACKPACK:
                        System.out.println("OPCION BACKPACK");
                        break;
                    case MAIN_SETTINGS:
                        System.out.println("OPCION SETTINGS");
                        break;
                    case MAIN_ABOUT:
                        System.out.println("OPCION ABOUT");
                        break;
                    case MAIN_TOGGLE_AUDIO:
                        // == Cuando cualquiera de los dos canales de audio está habilitado ==
                        if(sessionManager.musicEnabled || sessionManager.soundEnabled){
                            // -- Cambiar al sprite de la bocina
                            ((ToggleSpriteMenuItem)mainMenuScene.getMenuItem(MAIN_TOGGLE_AUDIO)).setCurrentTileIndex(1);

                            // -- Enmudecer los canales de AFX
                            resourceManager.musicManager.setMasterVolume(0);
                            resourceManager.soundManager.setMasterVolume(0);

                        }
                        // == Cuando ningún canal de audio está habilitado ====================
                        else{
                            // -- Cambiar el sprite de la bocina
                            ((ToggleSpriteMenuItem)mainMenuScene.getMenuItem(MAIN_TOGGLE_AUDIO)).setCurrentTileIndex(0);

                            // --Desenmudecer los canales de AFX
                            resourceManager.musicManager.setMasterVolume(sessionManager.musicVolume);
                            resourceManager.musicManager.setMasterVolume(sessionManager.soundVolume);

                        }
                        // -- Cambiar el volumen de la pista actual
                        resourceManager.menuMusic.setVolume(resourceManager.musicManager.getMasterVolume());

                        // -- Cambiar la bandera de habilitación de AFX
                        sessionManager.musicEnabled = !(sessionManager.musicEnabled);
                        sessionManager.soundEnabled = !(sessionManager.soundEnabled);

                        // -- Escribir los cambios al Adm. de Sesión
                        sessionManager.writeChanges();
                        break;
                }

                return true;

            }
        });




    }

    // ===========================================================
    //                Crear el Submenú Play
    // ===========================================================
    public void addPlayMenu(){
        // =============== Inicializando la subEscena ============
        playMenuScene = new org.andengine.entity.scene.menu.MenuScene(camera);
        playMenuScene.setPosition(0, 0);

        // =============== Creando los botones ===================
       IMenuItem playMenuBackButton = new ScaleMenuItemDecorator(new SpriteMenuItem(SUBMENU_BACK,menuSubmenuBackButtonRegion,vertexBufferObjectManager),0.8f,1f);

        // =============== Agregando los botones =================
        playMenuScene.addMenuItem(playMenuBackButton);

        // =============== Configurando las animaciones =========
        playMenuScene.buildAnimations();
        playMenuScene.setBackgroundEnabled(false);

        // =============== Ubicando los botones =================
        playMenuBackButton.setPosition(150,GameManager.CAMERA_HEIGHT - 125 );

        playMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()){
                    case SUBMENU_BACK:
                        returnToMainMenu();
                        break;
                }
                return true;
            }
        });
    }

    // ===========================================================
    //                Crear el Submenú Backpack
    // ===========================================================
    public void addBackpackMenu(){

    }

    // ===========================================================
    //      Realiza lo necesario para regresar al menú principal
    // ===========================================================
    public void returnToMainMenu(){
        setChildScene(mainMenuScene);
        menuOverlaySprite.setVisible(false);
        ((ToggleSpriteMenuItem)(mainMenuScene.getMenuItem(MAIN_TOGGLE_AUDIO))).setCurrentTileIndex((sessionManager.musicEnabled && sessionManager.soundEnabled) ? 0 : 1);
    }
    // ===========================================================
    //                          Condición Update
    // ===========================================================

    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

    }

    // ===========================================================
    //            Cuando se presiona la tecla retroceder
    // ===========================================================
    @Override
    public void onBackKeyPressed() {

        if(this.getChildScene() == mainMenuScene){
            // =============== Salir del Juego ===================
            destroyScene();
            gameManager.quit();
        }
        else{
            // =============== Habilitar el menú ==================
            returnToMainMenu();

        }
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
