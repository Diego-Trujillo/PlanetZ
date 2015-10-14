package mx.itesm.planetz;

import org.andengine.entity.modifier.FadeInModifier;
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

    // =============== Bandera sobre disponibilidad ==============
    private boolean mainMenuEnabled;

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
    private ITextureRegion AndyTextureRegion;
    private ITextureRegion RebeTextureRegion;
    private ITextureRegion BrianTextureRegion;
    private ITextureRegion DiegoTextureRegion;
    private ITextureRegion DanyTextureRegion;
    /*
    private Sprite Andy;
    private Sprite Rebe;
    private Sprite Brian;
    private Sprite Diego;
    private Sprite Dany; */
    private ITextureRegion ID1TextureRegion;
    private ITextureRegion ID2TextureRegion;
    private ITextureRegion ID3TextureRegion;
    private ITextureRegion ID4TextureRegion;
    private ITextureRegion ID5TextureRegion;
    /*
    private Sprite ID1;
    private Sprite ID2;
    private Sprite ID3;
    private Sprite ID4;
    private Sprite ID5;
    */
    // ===========================================================
    //                      BACKPACK
    // ===========================================================
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


    // =============== El Contenedor =============================
    private org.andengine.entity.scene.menu.MenuScene playMenuScene;
    private org.andengine.entity.scene.menu.MenuScene BackPackMenuScene;
    private org.andengine.entity.scene.menu.MenuScene AboutMenuScene;

    // =============== Bandera sobre disponibilidad ==============
    private boolean playMenuEnabled;


    // =============== Opciones de Botones =======================
    private static final int PLAY_ADVENTURE_MODE = 1;
    private static final int PLAY_ADVENTURE_MODE_NEWGAME = 3;
    private static final int PLAY_ADVENTURE_MODE_CONTINUE = 4;
    private static final int PLAY_INFINITE_MODE = 2;

    // =============== Texturas de Botones ==============

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
        AndyTextureRegion = resourceManager.menuSubmenuDraw1TextureRegion;
        RebeTextureRegion = resourceManager.menuSubmenuDraw2TextureRegion;
        BrianTextureRegion = resourceManager.menuSubmenuDraw3TextureRegion;
        DiegoTextureRegion = resourceManager.menuSubmenuDraw4TextureRegion;
        DanyTextureRegion = resourceManager.menuSubmenuDraw5TextureRegion;
        ID1TextureRegion = resourceManager.menuSubmenuID1TextureRegion;
        ID2TextureRegion = resourceManager.menuSubmenuID2TextureRegion;
        ID3TextureRegion = resourceManager.menuSubmenuID3TextureRegion;
        ID4TextureRegion = resourceManager.menuSubmenuID4TextureRegion;
        ID5TextureRegion = resourceManager.menuSubmenuID5TextureRegion;


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
        mainMenuEnabled = true;
        setChildScene(mainMenuScene);

        // =============== Reproducir música de fondo ============
        resourceManager.menuMusic.play();

        addPlayMenu();
        addBackpack();
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
                        System.out.println("OPCION PLAY");
                        setChildScene(playMenuScene);
                        menuOverlaySprite.setVisible(true);
                    case MAIN_BACKPACK:
                        System.out.println("OPCION BACKPACK");
                        setChildScene(BackPackMenuScene);
                        menuOverlaySprite.setVisible(true);
                    case MAIN_SETTINGS:
                        System.out.println("OPCION SETTINGS");
                        break;
                    case MAIN_ABOUT:
                        System.out.println("OPCION ABOUT");
                        setChildScene(AboutMenuScene);
                        menuOverlaySprite.setVisible(true);
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
                        setChildScene(mainMenuScene);
                        menuOverlaySprite.setVisible(false);
                        break;
                }
                return true;
            }
        });
    }


    // ===========================================================
    //                Crear el menú Mochila
    // ===========================================================
    public void addBackpack(){
        // =============== Inicializando la subEscena ============
        BackPackMenuScene = new org.andengine.entity.scene.menu.MenuScene(camera);
        BackPackMenuScene.setPosition(0, 0);

        // =============== Creando los botones ===================
        IMenuItem playMenuBackButton = new ScaleMenuItemDecorator(new SpriteMenuItem(SUBMENU_BACK,menuSubmenuBackButtonRegion,vertexBufferObjectManager),0.8f,1f);

        // =============== Agregando los botones =================
        BackPackMenuScene.addMenuItem(playMenuBackButton);

        // =============== Configurando las animaciones =========
        BackPackMenuScene.buildAnimations();
        BackPackMenuScene.setBackgroundEnabled(false);

        //

        // =============== Ubicando los botones =================
        playMenuBackButton.setPosition(150,GameManager.CAMERA_HEIGHT - 125 );

        BackPackMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()){
                    case SUBMENU_BACK:
                        setChildScene(mainMenuScene);
                        menuOverlaySprite.setVisible(false);
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
        AboutMenuScene = new org.andengine.entity.scene.menu.MenuScene(camera);
        AboutMenuScene.setPosition(0, 0);

        // =============== Creando los botones ===================
        IMenuItem playMenuBackButton = new ScaleMenuItemDecorator(new SpriteMenuItem(SUBMENU_BACK,menuSubmenuBackButtonRegion,vertexBufferObjectManager),0.8f,1f);
        // =============== Agregando los botones =================
        AboutMenuScene.addMenuItem(playMenuBackButton);

        // =============== Configurando las animaciones =========
        AboutMenuScene.buildAnimations();
        AboutMenuScene.setBackgroundEnabled(false);

        // =============== Ubicando los botones =================
        playMenuBackButton.setPosition(150,GameManager.CAMERA_HEIGHT - 125 );


        // ------------------------

        //Andy = resourceManager.loadSprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2,AndyTextureRegion);
        final IMenuItem Andy = new ScaleMenuItemDecorator(new SpriteMenuItem(1,AndyTextureRegion,vertexBufferObjectManager),0.8f,1f);
        //Rebe = resourceManager.loadSprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2,RebeTextureRegion);
        IMenuItem Rebe = new ScaleMenuItemDecorator(new SpriteMenuItem(2,RebeTextureRegion,vertexBufferObjectManager),0.8f,1f);
        //Brian = resourceManager.loadSprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2,BrianTextureRegion);
        IMenuItem Brian = new ScaleMenuItemDecorator(new SpriteMenuItem(3,BrianTextureRegion,vertexBufferObjectManager),0.8f,1f);
        //Diego = resourceManager.loadSprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2,DiegoTextureRegion);
        IMenuItem Diego = new ScaleMenuItemDecorator(new SpriteMenuItem(4,DiegoTextureRegion,vertexBufferObjectManager),0.8f,1f);
        //Dany = resourceManager.loadSprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2,DanyTextureRegion);
        IMenuItem Dany = new ScaleMenuItemDecorator(new SpriteMenuItem(5,DanyTextureRegion,vertexBufferObjectManager),0.8f,1f);

        final IMenuItem ID1 = new ScaleMenuItemDecorator(new SpriteMenuItem(6,ID1TextureRegion,vertexBufferObjectManager),0.8f,1f);
        final IMenuItem ID2 = new ScaleMenuItemDecorator(new SpriteMenuItem(7,ID1TextureRegion,vertexBufferObjectManager),0.8f,1f);
        final IMenuItem ID3 = new ScaleMenuItemDecorator(new SpriteMenuItem(8,ID1TextureRegion,vertexBufferObjectManager),0.8f,1f);
        final IMenuItem ID4 = new ScaleMenuItemDecorator(new SpriteMenuItem(9,ID1TextureRegion,vertexBufferObjectManager),0.8f,1f);
        final IMenuItem ID5 = new ScaleMenuItemDecorator(new SpriteMenuItem(10,ID1TextureRegion,vertexBufferObjectManager),0.8f,1f);
        AboutMenuScene.addMenuItem(Andy);
        AboutMenuScene.addMenuItem(Rebe);
        AboutMenuScene.addMenuItem(Brian);
        AboutMenuScene.addMenuItem(Diego);
        AboutMenuScene.addMenuItem(Dany);

        AboutMenuScene.addMenuItem(ID1);
        AboutMenuScene.addMenuItem(ID2);
        AboutMenuScene.addMenuItem(ID3);
        AboutMenuScene.addMenuItem(ID4);
        AboutMenuScene.addMenuItem(ID5);

        //---ubicacion

        Andy.setPosition(100,GameManager.CAMERA_HEIGHT/4);
        Rebe.setPosition(350,GameManager.CAMERA_HEIGHT/4);
        Brian.setPosition(600,GameManager.CAMERA_HEIGHT/4);
        Diego.setPosition(850,GameManager.CAMERA_HEIGHT/4);
        Dany.setPosition(1100,GameManager.CAMERA_HEIGHT/4);
        ID1.setPosition(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2);
        ID2.setPosition(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2);
        ID3.setPosition(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2);
        ID4.setPosition(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2);
        ID5.setPosition(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2);

        AboutMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()){
                    case 1:
                        attachChild(ID1);
                    case 2:
                        attachChild(ID2);
                    case 3:
                        attachChild(ID3);
                    case 4:
                        attachChild(ID4);
                    case 5:
                        attachChild(ID5);
                    case 6:
                        detachChild(ID1);
                    case 7:
                        detachChild(ID2);
                    case 8:
                        detachChild(ID3);
                    case 9:
                        detachChild(ID4);
                    case 10:
                        detachChild(ID5);

                    case SUBMENU_BACK:
                        setChildScene(mainMenuScene);
                        menuOverlaySprite.setVisible(false);
                        break;
                }
                return true;
            }
        });
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
            setChildScene(mainMenuScene);
            menuOverlaySprite.setVisible(false);

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
