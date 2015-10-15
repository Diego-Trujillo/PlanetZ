package mx.itesm.planetz;

import android.transition.Fade;
import android.widget.Switch;

import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.primitive.Rectangle;
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
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ClickDetector;
import org.andengine.input.touch.detector.SurfaceScrollDetector;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.util.SocketUtils;
import org.andengine.util.modifier.ParallelModifier;

import java.util.ArrayList;
import java.util.List;

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

    // =============== Texturas de Botones ==============

    // ===========================================================
    //                      SUBMENÚ BACKPACK
    // ===========================================================
    // =============== El Contenedor =============================
    private org.andengine.entity.scene.menu.MenuScene backpackMenuScene;

    protected static int FONT_SIZE = 24;
    protected static int PADDING = 50;
    protected static int MENUITEMS = 3;
    /*
    private Font mFont;
    private BitmapTextureAtlas mFontTexture;

    private BitmapTextureAtlas mMenuTextureAtlas;
    private TextureRegion mMenuLeftTextureRegion;
    private TextureRegion mMenuRightTextureRegion;

    private Sprite menuleft;
    private Sprite menuright;
    */

    // Scrolling
    private SurfaceScrollDetector mScrollDetector;
    private ClickDetector mClickDetector;

    private float mMinX = 0;
    private float mMaxX = 0;
    private float mCurrentX = 0;
    private int iItemClicked = -1;

    private Rectangle scrollBar;
    private List<TextureRegion> columns = new ArrayList<TextureRegion>();

    // ===========================================================
    //                      SUBMENÚ SETTINGS
    // ===========================================================

    // =============== El Contenedor =============================
    private org.andengine.entity.scene.menu.MenuScene settingsMenuScene;



    // ===========================================================
    //                      SUBMENÚ ABOUT
    // ===========================================================

    // =============== El Contenedor =============================
    private org.andengine.entity.scene.menu.MenuScene aboutMenuScene;

    // =============== Opciones de Botones =======================
    private static final int ABOUT_ANDY = 1;
    private static final int ABOUT_DANNI = 2;
    private static final int ABOUT_REBE = 3;
    private static final int ABOUT_BRIAN = 4;
    private static final int ABOUT_DIEGO = 5;

    // =============== Texturas de Botones ==============
    private ITextureRegion aboutMenuAndyButtonTextureRegion;
    private ITextureRegion aboutMenuRebeButtonTextureRegion;
    private ITextureRegion aboutMenuBrianButtonTextureRegion;
    private ITextureRegion aboutMenuDiegoButtonTextureRegion;
    private ITextureRegion aboutMenuDanniButtonTextureRegion;

    // =============== Texturas de ID's==================
    private ITextureRegion aboutMenuAndyIDTextureRegion;
    private ITextureRegion aboutMenuRebeIDTextureRegion;
    private ITextureRegion aboutMenuBrianIDTextureRegion;
    private ITextureRegion aboutMenuDiegoIDTextureRegion;
    private ITextureRegion aboutMenuDanniIDTextureRegion;

    // =============== Sprites de ID's =================
    private Sprite aboutMenuAndyIDSprite;
    private Sprite aboutMenuRebeIDSprite;
    private Sprite aboutMenuBrianIDSprite;
    private Sprite aboutMenuDiegoIDSprite;
    private Sprite aboutMenuDanniIDSprite;

    // =============== Bandera de Credencial Seleccionada=
    boolean flagIDup;

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

        // =======================================================
        //                  Submenú About
        // =======================================================
        // =============== Botones con caritas ===================
        aboutMenuAndyButtonTextureRegion = resourceManager.aboutMenuAndyButtonTextureRegion;
        aboutMenuRebeButtonTextureRegion = resourceManager.aboutMenuRebeButtonTextureRegion;
        aboutMenuBrianButtonTextureRegion = resourceManager.aboutMenuBrianButtonTextureRegion;
        aboutMenuDiegoButtonTextureRegion = resourceManager.aboutMenuDiegoButtonTextureRegion;
        aboutMenuDanniButtonTextureRegion = resourceManager.aboutMenuDanniButtonTexureRegion;

        // =============== Regiones de ID'S ============
        aboutMenuAndyIDTextureRegion = resourceManager.aboutMenuAndyIDTextureRegion;
        aboutMenuRebeIDTextureRegion = resourceManager.aboutMenuRebeIDTextureRegion;
        aboutMenuBrianIDTextureRegion = resourceManager.aboutMenuBrianIDTextureRegion;
        aboutMenuDiegoIDTextureRegion = resourceManager.aboutMenuDiegoIDTextureRegion;
        aboutMenuDanniIDTextureRegion = resourceManager.aboutMenuDanniIDTextureRegion;


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

        // =============== Agregar la sub-escena del menú ========
        addMainMenu();
        setChildScene(mainMenuScene);

        // =============== Reproducir música de fondo ============
        resourceManager.menuMusic.play();

        addPlayMenu();
        addBackpack();
        addSettings();
        addAbout();
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
                        setChildScene(playMenuScene);
                        menuOverlaySprite.setVisible(true);
                        break;
                    case MAIN_BACKPACK:
                        setChildScene(backpackMenuScene);
                        menuOverlaySprite.setVisible(true);
                        break;
                    case MAIN_SETTINGS:
                        setChildScene(settingsMenuScene);
                        menuOverlaySprite.setVisible(true);
                        break;
                    case MAIN_ABOUT:
                        setChildScene(aboutMenuScene);
                        menuOverlaySprite.setVisible(true);
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
    //                Crear el menú Play
    // ===========================================================
    public void addPlayMenu(){
        // =============== Inicializando la subEscena ============
        playMenuScene = new org.andengine.entity.scene.menu.MenuScene(camera);
        playMenuScene.setPosition(0, 0);



        // =============== Creando los botones ===================
       IMenuItem backButton = new ScaleMenuItemDecorator(new SpriteMenuItem(SUBMENU_BACK,menuSubmenuBackButtonRegion,vertexBufferObjectManager),0.8f,1f);

        // =============== Agregando los botones =================
        playMenuScene.addMenuItem(backButton);

        // =============== Configurando las animaciones =========
        playMenuScene.buildAnimations();
        playMenuScene.setBackgroundEnabled(false);

        // =============== Agregando el Texto "PLAY" =============
        playMenuScene.attachChild(new Text(350, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "PLAY", vertexBufferObjectManager));


        // =============== Ubicando los botones =================
        backButton.setPosition(150,GameManager.CAMERA_HEIGHT - 125 );

        playMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()){
                    case SUBMENU_BACK:
                        returnToMenu();
                        break;
                }
                return true;
            }
        });
    }


    // ===========================================================
    //                Crear el menú Backpack
    // ===========================================================
    public void addBackpack(){
        // =============== Inicializando la subEscena ============
        backpackMenuScene = new org.andengine.entity.scene.menu.MenuScene(camera);
        backpackMenuScene.setPosition(0, 0);

        // =============== Creando los botones ===================
        IMenuItem backButton = new ScaleMenuItemDecorator(new SpriteMenuItem(SUBMENU_BACK,menuSubmenuBackButtonRegion,vertexBufferObjectManager),0.8f,1f);

        // =============== Agregando los botones =================
        backpackMenuScene.addMenuItem(backButton);

        // =============== Configurando las animaciones =========
        backpackMenuScene.buildAnimations();
        backpackMenuScene.setBackgroundEnabled(false);

        // =============== Agregando el Texto "BACKPACK" =========
        backpackMenuScene.attachChild(new Text(425, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "BACKPACK", vertexBufferObjectManager));

        // =============== Ubicando los botones =================
        backButton.setPosition(150, GameManager.CAMERA_HEIGHT - 125);

        backpackMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()){
                    case SUBMENU_BACK:
                        returnToMenu();
                        break;
                }
                return true;
            }
        });
    }

    // ===========================================================
    //                Crear el menú Settings
    // ===========================================================
    public void addSettings(){
        // =============== Inicializando la subEscena ============
        settingsMenuScene = new org.andengine.entity.scene.menu.MenuScene(camera);
        settingsMenuScene.setPosition(0, 0);

        // =============== Creando los botones ===================
        IMenuItem backButton = new ScaleMenuItemDecorator(new SpriteMenuItem(SUBMENU_BACK,menuSubmenuBackButtonRegion,vertexBufferObjectManager),0.8f,1f);

        // =============== Agregando los botones =================
        settingsMenuScene.addMenuItem(backButton);

        // =============== Configurando las animaciones =========
        settingsMenuScene.buildAnimations();
        settingsMenuScene.setBackgroundEnabled(false);

        // =============== Agregando el Texto "BACKPACK" =========
        settingsMenuScene.attachChild(new Text(400, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "SETTINGS", vertexBufferObjectManager));

        // =============== Ubicando los botones =================
        backButton.setPosition(150,GameManager.CAMERA_HEIGHT - 125 );

        settingsMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()){
                    case SUBMENU_BACK:
                        returnToMenu();
                        break;
                }
                return true;
            }
        });
    }
    // ===========================================================
    //                Crear el menú About
    // ===========================================================
    public void addAbout(){
        // =============== Inicializando la subEscena ============
        aboutMenuScene = new org.andengine.entity.scene.menu.MenuScene(camera);
        aboutMenuScene.setPosition(0, 0);

        // =============== Creando los botones ===================
        IMenuItem backButton = new ScaleMenuItemDecorator(new SpriteMenuItem(SUBMENU_BACK,menuSubmenuBackButtonRegion,vertexBufferObjectManager),0.8f,1f);
        IMenuItem buttonAndy = new ScaleMenuItemDecorator(new SpriteMenuItem(ABOUT_ANDY,aboutMenuAndyButtonTextureRegion,vertexBufferObjectManager),0.8f,1f);
        IMenuItem buttonRebe = new ScaleMenuItemDecorator(new SpriteMenuItem(ABOUT_REBE,aboutMenuRebeButtonTextureRegion,vertexBufferObjectManager),0.8f,1f);
        IMenuItem buttonBrian = new ScaleMenuItemDecorator(new SpriteMenuItem(ABOUT_BRIAN,aboutMenuBrianButtonTextureRegion,vertexBufferObjectManager),0.8f,1f);
        IMenuItem buttonDiego = new ScaleMenuItemDecorator(new SpriteMenuItem(ABOUT_DIEGO,aboutMenuDiegoButtonTextureRegion,vertexBufferObjectManager),0.8f,1f);
        IMenuItem buttonDanni = new ScaleMenuItemDecorator(new SpriteMenuItem(ABOUT_DANNI,aboutMenuDanniButtonTextureRegion,vertexBufferObjectManager),0.8f,1f);

        // =============== Agregando los botones =================
        aboutMenuScene.addMenuItem(backButton);
        aboutMenuScene.addMenuItem(buttonAndy);
        aboutMenuScene.addMenuItem(buttonDanni);
        aboutMenuScene.addMenuItem(buttonRebe);
        aboutMenuScene.addMenuItem(buttonBrian);
        aboutMenuScene.addMenuItem(buttonDiego);

        // =============== Configurando las animaciones =========
        aboutMenuScene.buildAnimations();
        aboutMenuScene.setBackgroundEnabled(false);

        // =============== Agregando el Texto "ABOUT" =============
        aboutMenuScene.attachChild(new Text(350, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "ABOUT", vertexBufferObjectManager));

        // =============== Ubicando los botones =================
        backButton.setPosition(150, GameManager.CAMERA_HEIGHT - 125);
        buttonAndy.setPosition(152, GameManager.CAMERA_HEIGHT/2);
        buttonDanni.setPosition(393, GameManager.CAMERA_HEIGHT/2 );
        buttonRebe.setPosition(649, GameManager.CAMERA_HEIGHT/2);
        buttonBrian.setPosition(905, GameManager.CAMERA_HEIGHT/2);
        buttonDiego.setPosition(1141,GameManager.CAMERA_HEIGHT/2);




        //ID1.setAlpha(0);ID2.setAlpha(0);ID3.setAlpha(0);ID4.setAlpha(0);ID5.setAlpha(0);
        /*AboutMenuScene.addMenuItem(ID1);
        AboutMenuScene.addMenuItem(ID2);
        AboutMenuScene.addMenuItem(ID3);
        AboutMenuScene.addMenuItem(ID4);
        AboutMenuScene.addMenuItem(ID5);*/
        //ID1.setPosition(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2);
        //ID2.setPosition(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2);
        //ID3.setPosition(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2);
        //ID4.setPosition(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2);
        //ID5.setPosition(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2);

        flagIDup = false;

        // =============== Crear Sprites y Exlusividad al toque ==
        aboutMenuAndyIDSprite = new Sprite(GameManager.CAMERA_WIDTH/2 + 200,600, aboutMenuAndyButtonTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y)
            {
                if (pSceneTouchEvent.isActionUp()) {setAboutID(aboutMenuAndyIDSprite, false);}
                return true;
            }
        };

        aboutMenuDanniIDSprite = new Sprite(GameManager.CAMERA_WIDTH/2 + 200,600, aboutMenuDanniButtonTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y)
            {
                if (pSceneTouchEvent.isActionUp()) {setAboutID(aboutMenuDanniIDSprite, false);}
                return true;
            }
        };

        aboutMenuRebeIDSprite = new Sprite(GameManager.CAMERA_WIDTH/2 + 200,600, aboutMenuRebeButtonTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y)
            {
                if (pSceneTouchEvent.isActionUp()) {setAboutID(aboutMenuRebeIDSprite, false);}
                return true;
            }
        };

        aboutMenuBrianIDSprite = new Sprite(GameManager.CAMERA_WIDTH/2 + 200,600, aboutMenuBrianButtonTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y)
            {
                if (pSceneTouchEvent.isActionUp()) {setAboutID(aboutMenuBrianIDSprite, false);}
                return true;
            }
        };

        aboutMenuDiegoIDSprite = new Sprite(GameManager.CAMERA_WIDTH/2 + 200,600, aboutMenuDiegoButtonTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y)
            {
                if (pSceneTouchEvent.isActionUp()) {setAboutID(aboutMenuDiegoIDSprite, false);}
                return true;
            }
        };
        aboutMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()) {
                    case ABOUT_ANDY:
                        setAboutID(aboutMenuAndyIDSprite, true);
                        break;
                    case ABOUT_DANNI:
                        setAboutID(aboutMenuDanniIDSprite, true);
                        break;
                    case ABOUT_REBE:
                        setAboutID(aboutMenuRebeIDSprite, true);
                        break;
                    case ABOUT_BRIAN:
                        setAboutID(aboutMenuBrianIDSprite, true);
                        break;
                    case ABOUT_DIEGO:
                        setAboutID(aboutMenuDiegoIDSprite, true);
                        break;
                    case SUBMENU_BACK:
                        returnToMenu();
                        break;
                    }

                return true;
            }
        });


    }

    // ===========================================================
    //          Deshabilita los botones de el Submenú About
    // ===========================================================

    public void setEnableAboutButtons(boolean enable){
        if(enable){
            aboutMenuScene.registerTouchArea(aboutMenuScene.getChildByIndex(SUBMENU_BACK));
            aboutMenuScene.registerTouchArea(aboutMenuScene.getChildByIndex(ABOUT_ANDY));
            aboutMenuScene.registerTouchArea(aboutMenuScene.getChildByIndex(ABOUT_DANNI));
            aboutMenuScene.registerTouchArea(aboutMenuScene.getChildByIndex(ABOUT_REBE));
            aboutMenuScene.registerTouchArea(aboutMenuScene.getChildByIndex(ABOUT_BRIAN));
            aboutMenuScene.registerTouchArea(aboutMenuScene.getChildByIndex(ABOUT_DIEGO));
        }
        else{
            aboutMenuScene.unregisterTouchArea(aboutMenuScene.getChildByIndex(SUBMENU_BACK));
            aboutMenuScene.unregisterTouchArea(aboutMenuScene.getChildByIndex(ABOUT_ANDY));
            aboutMenuScene.unregisterTouchArea(aboutMenuScene.getChildByIndex(ABOUT_DANNI));
            aboutMenuScene.unregisterTouchArea(aboutMenuScene.getChildByIndex(ABOUT_REBE));
            aboutMenuScene.unregisterTouchArea(aboutMenuScene.getChildByIndex(ABOUT_BRIAN));
            aboutMenuScene.unregisterTouchArea(aboutMenuScene.getChildByIndex(ABOUT_DIEGO));
        }
    }

    // ===========================================================
    //          Deshabilita los botones de el Submenú About
    // ===========================================================

    public void setAboutID(Sprite sprite, boolean enable){
        if(enable){
            setEnableAboutButtons(false);
            aboutMenuScene.attachChild(sprite);
            sprite.registerEntityModifier(new FadeInModifier(2.0f));
            aboutMenuScene.registerTouchArea(sprite);
        }
        else{
            aboutMenuScene.unregisterTouchArea(sprite);
            aboutMenuScene.detachChild(sprite);
            setEnableAboutButtons(true);
        }


    }
    // ===========================================================
    //                          Condición Update
    // ===========================================================

    protected void onManagedUpdate(float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

    }

    // ===========================================================
    //            Regresar a la escena de menú
    // ===========================================================
    void returnToMenu(){
        menuOverlaySprite.setVisible(false);
        setChildScene(mainMenuScene);

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
            returnToMenu();
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
