package mx.itesm.planetz;

import android.util.Log;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.audio.sound.SoundManager;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Administra los recursos visuales y auditivos y da métodos útiles
 *
 * Created by Diego on 03/10/2015.
 */
public class ResourceManager {
    // =============================================================================================
    //                          D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================

    // ============== Referencia de Instancia Única =============
    private static ResourceManager INSTANCE = new ResourceManager();

    // ===========================================================
    //    Referencia a los Adm. de Juego y de Sesión/Progreso
    // ===========================================================
    GameManager gameManager;
    SessionManager sessionManager;

    // ===========================================================
    //               Referencia al motor y sus elementos
    // ===========================================================
    Engine engine;                      //Motor del juego
    Camera camera;                      // Cámara principal
    TextureManager textureManager;      // Administrador de Texturas
    public MusicManager musicManager;   // Administrador de Música
    public SoundManager soundManager;   // Administrador de Sonido

    // ===========================================================
    //                     Creación de FONTS
    // ===========================================================
    public Font fontOne;


    // ===============================================================
    //                        ESCENA SPLASH
    // ===============================================================

    // ============== RECURSOS GRÁFICOS ==========================
    // ===========================================================
    private BitmapTextureAtlas splashTextureAtlas;
    public ITextureRegion splashTextureRegion_background;
    public ITextureRegion splashTextureRegion_logo;

    // ============== RECURSOS MUSICALES =========================
    // ===========================================================
    public Music splashMusic;



    // =========================================================================
    //                        ESCENA MENU
    // =========================================================================

    // ============== RECURSOS GRÁFICOS ==========================
    // ===========================================================
    // ============== Contenedor de Atlas's de Región ============
    private ArrayList<BitmapTextureAtlas> menuBitmapTextureAtlasContainer;

    // ============== Fondo ======================================
    // -------------- Fondo Sólido -------------------------------
    private BitmapTextureAtlas menuBackgroundTextureAtlas;
    public ITextureRegion menuBackgroundTextureRegion;

    // -------------- Fondo Estrellas 1 --------------------------
    private BitmapTextureAtlas menuBackgroundStars1TextureAtlas;
    public ITextureRegion menuBackgroundStars1TextureRegion;

    // -------------- Fondo Estrellas 2 --------------------------
    private BitmapTextureAtlas menuBackgroundStars2TextureAtlas;
    public ITextureRegion menuBackgroundStars2TextureRegion;

    // -------------- Fondo Estrellas 3 --------------------------
    private BitmapTextureAtlas menuBackgroundStars3TextureAtlas;
    public ITextureRegion menuBackgroundStars3TextureRegion;

    // ============== Logotipo Juego =============================
    private BitmapTextureAtlas menuLogoTextureAtlas;
    public ITextureRegion menuLogoBackgroundTextureRegion;

    // ============== Planeta ====================================
    private BitmapTextureAtlas menuPlanetTextureAtlas;
    public ITextureRegion menuPlanetTextureRegion;

    // ============== Overlay Submenús ===========================
    private BitmapTextureAtlas menuOverlayTextureAtlas;
    public ITextureRegion menuOverlayTextureRegion;

    // ============== Botón Back Submenús ========================
    private BitmapTextureAtlas menuSubmenuBackButtonTextureAtlas;
    public ITextureRegion menuSubmenuBackButtonTextureRegion;

    // ============== Bocina Mute ================================
    private BitmapTextureAtlas menuToggleAudioButtonTextureAtlas;
    public ITiledTextureRegion menuToggleAudioButtonTextureRegion;

    // ============== MENÚ PRINCIPAL =============================

    // -------------- Botones ------------------------------------
    private BitmapTextureAtlas mainMenuButtonTextureAtlas;

    public ITiledTextureRegion mainMenuButtonTextureRegion_play;
    public ITiledTextureRegion mainMenuButtonTextureRegion_backpack;
    public ITiledTextureRegion mainMenuButtonTextureRegion_settings;
    public ITiledTextureRegion mainMenuButtonTextureRegion_about;

    // ============== SUBMENU PLAY ===============================
    private BitmapTextureAtlas playMenuButtonTextureAtlas;
    public ITextureRegion playMenuAdventureModeButtonTextureRegion;

    // ============== SUBMENU BACKPACK ===========================
    private BitmapTextureAtlas backpackMenuButtonTextureAtlas;
    public ITextureRegion backpackMenuLeftArrowTextureRegion;
    public ITextureRegion backpackMenuRightArrowTextureRegion;


    // -------------- Gemas --------------------------------------
    private BitmapTextureAtlas backpackMenuGems1TextureAtlas;
    private BitmapTextureAtlas backpackMenuGems2TextureAtlas;
    private BitmapTextureAtlas backpackMenuGems3TextureAtlas;
    private BitmapTextureAtlas backpackMenuGems4TextureAtlas;
    public ITextureRegion backpackMenuGemBlue1TextureRegion;
    public ITextureRegion backpackMenuGemBlue2TextureRegion;
    public ITextureRegion backpackMenuGemBlue3TextureRegion;
    public ITextureRegion backpackMenuGemPink1TextureRegion;
    public ITextureRegion backpackMenuGemPink2TextureRegion;
    public ITextureRegion backpackMenuGemPink3TextureRegion;
    public ITextureRegion backpackMenuGemYellow1TextureRegion;
    public ITextureRegion backpackMenuGemYellow2TextureRegion;
    public ITextureRegion backpackMenuGemYellow3TextureRegion;
    public ITextureRegion backpackMenuGemLocked1TextureRegion;
    public ITextureRegion backpackMenuGemLocked2TextureRegion;
    public ITextureRegion backpackMenuGemLocked3TextureRegion;


    // ============== SUBMENU SETTINGS ===========================
    // -------------- Las barras de audio ------------------------
    private BitmapTextureAtlas settingsMenuAudioLevelTextureAtlas;
    public ITextureRegion settingsMenuAudioLevel_20_TextureRegion;
    public ITextureRegion settingsMenuAudioLevel_40_TextureRegion;
    public ITextureRegion settingsMenuAudioLevel_60_TextureRegion;
    public ITextureRegion settingsMenuAudioLevel_80_TextureRegion;
    public ITextureRegion settingsMenuAudioLevel_100_TextureRegion;

    // --------------- Los botones de subir/bajar volumen --------
    private BitmapTextureAtlas settingsMenuAudioButtonsTextureAtlas;
    public ITextureRegion settingsMenuDecreaseMusicButtonTextureRegion;
    public ITextureRegion settingsMenuIncreaseMusicButtonTextureRegion;
    public ITextureRegion settingsMenuDecreaseSoundButtonTextureRegion;
    public ITextureRegion settingsMenuIncreaseSoundButtonTextureRegion;

    // ============== SUBMENU ABOUT ==============================

    //----------------Logo----------------------
    private BitmapTextureAtlas aboutMenuLogoTextureAtlas;
    public ITextureRegion aboutMenuLogoTextureRegion;
    // -------------- Bolitas con Nombres ------------------------
    private BitmapTextureAtlas aboutMenuButtonTextureAtlas;
    public ITextureRegion aboutMenuAndyButtonTextureRegion;
    public ITextureRegion aboutMenuDanniButtonTexureRegion;
    public ITextureRegion aboutMenuRebeButtonTextureRegion;
    public ITextureRegion aboutMenuBrianButtonTextureRegion;
    public ITextureRegion aboutMenuDiegoButtonTextureRegion;

    // -------------- ID's y descripción -------------------------
    private BitmapTextureAtlas aboutMenuIDTextureAtlas;
    public ITextureRegion aboutMenuAndyIDTextureRegion;
    public ITextureRegion aboutMenuDanniIDTextureRegion;
    public ITextureRegion aboutMenuRebeIDTextureRegion;
    public ITextureRegion aboutMenuBrianIDTextureRegion;
    public ITextureRegion aboutMenuDiegoIDTextureRegion;


    // ===========================================================================
    //                ESCENA NIVEL UNO ADVENTURE MODE
    // ===========================================================================

    // ============== RECURSOS GRÁFICOS ==========================
    // ===========================================================

    // ============== Contenedor de BitmapAtlas ==================
    ArrayList<BitmapTextureAtlas> adventureLevel1BitmapTextureAtlasContainer;

    // ============== Contenedor de Atlas's de Región ============
    public BitmapTextureAtlas adventureLevel1OneButtonsTextureAtlas;
    public BitmapTextureAtlas adventureLevelOneShipTextureAtlas;
    public BitmapTextureAtlas adventureLevel1Background1TextureAtlas;
    public BitmapTextureAtlas adventureLevel1Background2TextureAtlas;

    // =============== Regiones de Texturas ======================
    // --------------- Botones -----------------------------------
    public ITextureRegion adventureLevel1PauseButtonTextureRegion;
    public ITextureRegion adventureLevel1PauseScreenTextureRegion;
    public ITextureRegion adventureLevel1PlayButtonTextureRegion;
    public ITextureRegion adventureLevel1BackButtonTextureRegion;

    // --------------- Meteoritos --------------------------------
    public ArrayList<ITextureRegion> adventureLevelOneMeteoriteTextureRegions;
    public ITiledTextureRegion adventureLevelOneAnimatedShipTextureRegion;
    // --------------- Vidas -------------------------------------
    public ITextureRegion adventureLevelOneLivesTexureRegion;
    // --------------- Fondo ------------------------------------
    public ITextureRegion adventureLevel1BackgroundTextureRegion;
    public ITextureRegion adventureLevel1BackgroundStarsTextureRegion;


    // =================================================================
    //                  ESCENA YOU LOSE
    // =================================================================

    // ============== RECURSOS GRÁFICOS ==========================
    // ===========================================================
    //-------Atlas-------
    BitmapTextureAtlas YouLoseBackgroundTextureAtlas;
    BitmapTextureAtlas YouLoseButtonsTextureAtlas;
    //--------Texturas----
    ITextureRegion YouLoseBackgroundTextureRegion;
    ITextureRegion YouLoseExitButtonTextureRegion;
    ITextureRegion YouLoseRetryButtonTextureRegion;

    // =================================================================
    //                  ESCENA YOU WIN
    // =================================================================

    // ============== RECURSOS GRÁFICOS ==========================
    // ===========================================================
    //-------Atlas-------
    BitmapTextureAtlas YouWinBackgroundTextureAtlas;
    BitmapTextureAtlas YouWinButtonsTextureAtlas;
    //--------Texturas----
    ITextureRegion YouWinBackgroundTextureRegion;
    ITextureRegion YouWinExitButtonTextureRegion;
    ITextureRegion YouWinRetryButtonTextureRegion;
    ITextureRegion YouWinContinueButtonTextureRegion;

    // -------------- Gemas --------------------------------------
    private BitmapTextureAtlas YouWinGems1TextureAtlas;
    private BitmapTextureAtlas YouWinGems2TextureAtlas;
    private BitmapTextureAtlas YouWinGems3TextureAtlas;
    private BitmapTextureAtlas YouWinGems4TextureAtlas;
    public ITextureRegion YouWinGemBlue1TextureRegion;
    public ITextureRegion YouWinGemBlue2TextureRegion;
    public ITextureRegion YouWinGemBlue3TextureRegion;
    public ITextureRegion YouWinGemPink1TextureRegion;
    public ITextureRegion YouWinGemPink2TextureRegion;
    public ITextureRegion YouWinGemPink3TextureRegion;
    public ITextureRegion YouWinGemYellow1TextureRegion;
    public ITextureRegion YouWinGemYellow2TextureRegion;
    public ITextureRegion YouWinGemYellow3TextureRegion;
    public ITextureRegion YouWinGemLocked1TextureRegion;
    public ITextureRegion YouWinGemLocked2TextureRegion;
    public ITextureRegion YouWinGemLocked3TextureRegion;
    // ============================================================================
    //                                RECURSOS DE AUDIO
    // ============================================================================
    // ============== Música =====================================
    // ===========================================================
    public Music backgroundMusic;

    // ============== Sounds =====================================
    // ===========================================================
    public Sound soundOne;
    public Sound soundTwo;
    public Sound soundThree;
    public Sound soundFour;
    public Sound soundFive;




    // =============================================================================================
    //                                         M É T O D O S
    // =============================================================================================

    // ===========================================================
    //    Inicializa la instancia única de el Adm. de Recursos
    // ===========================================================
    public static void initialize(GameManager gameManager){

        // ============== Inicializando los Adm. =================
        getInstance().gameManager = gameManager;
        getInstance().engine = gameManager.getEngine();
        getInstance().sessionManager = SessionManager.getInstance();

        // ============== Inicializando cámara y adm. de text. ===
        getInstance().camera = gameManager.getEngine().getCamera();
        getInstance().textureManager = gameManager.getTextureManager();

        // ============== Inicializando los Adm. de SFX y MFX ====
        getInstance().soundManager = gameManager.getEngine().getSoundManager();
        getInstance().musicManager = gameManager.getEngine().getMusicManager();
        MusicFactory.setAssetBasePath("Audio/Music/");
        SoundFactory.setAssetBasePath("Audio/Sound/");


        // ============== Cargar Fuentes =============
        getInstance().fontOne = FontFactory.createFromAsset(gameManager.getFontManager(), getInstance().textureManager, 1024, 1024, gameManager.getAssets(), "Graphics/Fonts/PetitFour.otf", 100, true, Color.WHITE_ABGR_PACKED_INT);
        getInstance().fontOne.load();
    }

    // =========================================================== *
    //                        ESCENA SPLASH
    // ===========================================================

    // ============== Cargar Recursos Gráficos ===================
    // ===========================================================

    public void loadSplashResourcesGFX(){
        // ============== Asignar el directorio base =============
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/Splash/");

        // ============== Asignarndo espacio en memoria ==========
        splashTextureAtlas = new BitmapTextureAtlas(textureManager,2048,2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        // ============== Cargando las imágenes ==================
        splashTextureRegion_background = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,gameManager,"Background.jpg",0,0);
        splashTextureRegion_logo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,gameManager,"ITESMLogo.png",0,720);

        // ============== Cargar el atlas de imágenes ============
        splashTextureAtlas.load();
    }

    // ============== Liberar Recursos ===========================
    // ===========================================================
    public void unloadSplashResources(){
        // ============== Recursos Gráficos ======================
        splashTextureAtlas.unload();
        splashTextureAtlas = null;
        splashTextureRegion_logo = null;
        splashTextureRegion_background = null;
    }

    // =========================================================== *
    //                        ESCENA MENÚ
    // ===========================================================

    // ============== Cargar Recursos Gráficos ===================
    // ===========================================================
    public void loadMenuResourcesGFX() {
        // ============== Asignar el directorio base =============
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/Menu/");

        // ============== Asignarndo espacio en memoria ==========
        // -- Inicializar Contenedor --
        menuBitmapTextureAtlasContainer = new ArrayList<BitmapTextureAtlas>();
        // -- Fondo --
        menuBackgroundTextureAtlas = new BitmapTextureAtlas(textureManager, 1280, 720, TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(menuBackgroundTextureAtlas);

        // -- Estrellas Fondo 2 --
        menuBackgroundStars1TextureAtlas = new BitmapTextureAtlas(textureManager, 1280, 720, TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(menuBackgroundStars1TextureAtlas);

        // -- Estrellas Fondo 3 --
        menuBackgroundStars2TextureAtlas = new BitmapTextureAtlas(textureManager, 1280, 720, TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(menuBackgroundStars2TextureAtlas);

        // -- Estrellas Fondo 4 --
        menuBackgroundStars3TextureAtlas = new BitmapTextureAtlas(textureManager, 1280, 720, TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(menuBackgroundStars3TextureAtlas);

        // -- Logotipo juego --
        menuLogoTextureAtlas = new BitmapTextureAtlas(textureManager, 675, 475, TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(menuLogoTextureAtlas);
        // -- Planeta --
        menuPlanetTextureAtlas = new BitmapTextureAtlas(textureManager, 1600, 1600, TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(menuPlanetTextureAtlas);
        // -- Overlay --
        menuOverlayTextureAtlas = new BitmapTextureAtlas(textureManager, 1280, 720, TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(menuOverlayTextureAtlas);
        // -- Botón Back --
        menuSubmenuBackButtonTextureAtlas = new BitmapTextureAtlas(textureManager,128,128,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(menuSubmenuBackButtonTextureAtlas);
        // -- Botones MenúPrincipal --
        mainMenuButtonTextureAtlas = new BitmapTextureAtlas(textureManager, 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        menuBitmapTextureAtlasContainer.add(mainMenuButtonTextureAtlas);
        menuToggleAudioButtonTextureAtlas = new BitmapTextureAtlas(textureManager,128,64,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        menuBitmapTextureAtlasContainer.add(menuToggleAudioButtonTextureAtlas);
       //-- Objetos backpack --

        backpackMenuButtonTextureAtlas = new BitmapTextureAtlas(textureManager, 230,175,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(backpackMenuButtonTextureAtlas);

        backpackMenuGems1TextureAtlas = new BitmapTextureAtlas(textureManager, 900,400,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(backpackMenuGems1TextureAtlas);
        backpackMenuGems2TextureAtlas = new BitmapTextureAtlas(textureManager, 900,400,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(backpackMenuGems2TextureAtlas);
        backpackMenuGems3TextureAtlas = new BitmapTextureAtlas(textureManager, 900,400,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(backpackMenuGems3TextureAtlas);
        backpackMenuGems4TextureAtlas = new BitmapTextureAtlas(textureManager, 900,400,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(backpackMenuGems4TextureAtlas);

        // -- Submenú Settings --
        settingsMenuAudioLevelTextureAtlas = new BitmapTextureAtlas(textureManager,320,154,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        menuBitmapTextureAtlasContainer.add(settingsMenuAudioLevelTextureAtlas);

        settingsMenuAudioButtonsTextureAtlas = new BitmapTextureAtlas(textureManager,190,154,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        menuBitmapTextureAtlasContainer.add(settingsMenuAudioButtonsTextureAtlas);



        // -- Botones About --
        aboutMenuButtonTextureAtlas =  new BitmapTextureAtlas(textureManager,875 ,175,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(aboutMenuButtonTextureAtlas);
        // -- ID's --
        aboutMenuIDTextureAtlas = new BitmapTextureAtlas(textureManager, 2000,1500,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(aboutMenuIDTextureAtlas);
        // --Logo tec--
        aboutMenuLogoTextureAtlas = new BitmapTextureAtlas(textureManager, 550,150,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(aboutMenuLogoTextureAtlas);



        // ============== Cargando las imágenes ==================
        // -------------- Fondo ----------------------------------
        menuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBackgroundTextureAtlas, gameManager, "BackgroundLayer1.jpg", 0, 0);
        menuBackgroundStars1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBackgroundStars1TextureAtlas, gameManager, "BackgroundLayer2.png", 0, 0);
        menuBackgroundStars2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBackgroundStars2TextureAtlas, gameManager, "BackgroundLayer3.png", 0, 0);
        menuBackgroundStars3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBackgroundStars3TextureAtlas, gameManager, "BackgroundLayer4.png", 0, 0);


        // -- Logotipo juego --
        menuLogoBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuLogoTextureAtlas, gameManager, "Logo.png", 0, 0);
        // -- Planeta --
        menuPlanetTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuPlanetTextureAtlas, gameManager, "Planet.png", 0, 0);
        // -- Overlay --
        menuOverlayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuOverlayTextureAtlas, gameManager, "Overlay.png", 0, 0);
        // -- Back Arrow --
        menuSubmenuBackButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuSubmenuBackButtonTextureAtlas, gameManager, "BackArrow.png", 0, 0);


        // -- Botones MenúPrincipal --
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/Menu/Main/"); // Cambiar directorio
        mainMenuButtonTextureRegion_play = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mainMenuButtonTextureAtlas,gameManager,"PlayButton.png",0,0,2,1);
        mainMenuButtonTextureRegion_backpack = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mainMenuButtonTextureAtlas,gameManager,"BackpackButton.png",0,128,2,1);
        mainMenuButtonTextureRegion_settings = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mainMenuButtonTextureAtlas, gameManager, "SettingsButton.png", 0, 256, 2, 1);
        mainMenuButtonTextureRegion_about = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mainMenuButtonTextureAtlas, gameManager, "AboutButton.png", 0, 384, 2, 1);
        menuToggleAudioButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(menuToggleAudioButtonTextureAtlas, gameManager, "AudioToggleButton.png", 0, 0, 2, 1);

        // -- Submenú back --

        //botones
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/Menu/Backpack/");
        backpackMenuLeftArrowTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuButtonTextureAtlas, gameManager, "LeftArrow.png", 0, 0);
        backpackMenuRightArrowTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuButtonTextureAtlas, gameManager, "RightArrow.png", 115, 0);
        backpackMenuGemBlue1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems1TextureAtlas, gameManager, "GemBlue1.png", 0, 0);
        backpackMenuGemBlue2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems1TextureAtlas, gameManager, "GemBlue2.png", 300, 0);
        backpackMenuGemBlue3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems1TextureAtlas, gameManager, "GemBlue3.png", 600, 0);
        backpackMenuGemPink1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems2TextureAtlas, gameManager, "GemPink1.png",0, 0);
        backpackMenuGemPink2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems2TextureAtlas, gameManager, "GemPink2.png", 300, 0);
        backpackMenuGemPink3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems2TextureAtlas, gameManager, "GemPink3.png", 600, 0);
        backpackMenuGemYellow1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems3TextureAtlas, gameManager, "GemYellow1.png", 0, 0);
        backpackMenuGemYellow2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems3TextureAtlas, gameManager, "GemYellow2.png", 300, 0);
        backpackMenuGemYellow3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems3TextureAtlas, gameManager, "GemYellow3.png", 600, 0);
        backpackMenuGemLocked1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems4TextureAtlas, gameManager, "GemLocked1.png", 0, 0);
        backpackMenuGemLocked2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems4TextureAtlas, gameManager, "GemLocked2.png", 300, 0);
        backpackMenuGemLocked3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems4TextureAtlas, gameManager, "GemLocked3.png", 600, 0);


        // -- Submenú settings --
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/Menu/Settings/"); // Cambiar directorio
        settingsMenuAudioLevel_20_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioLevelTextureAtlas,gameManager,"AudioBar1.png",0,0);
        settingsMenuAudioLevel_40_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioLevelTextureAtlas,gameManager,"AudioBar2.png",64,0);
        settingsMenuAudioLevel_60_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioLevelTextureAtlas,gameManager,"AudioBar3.png",128,0);
        settingsMenuAudioLevel_80_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioLevelTextureAtlas,gameManager,"AudioBar4.png",192,0);
        settingsMenuAudioLevel_100_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioLevelTextureAtlas,gameManager,"AudioBar5.png",256,0);

        settingsMenuDecreaseMusicButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioButtonsTextureAtlas,gameManager,"MusicDecreaseButton.png",0,0);
        settingsMenuIncreaseMusicButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioButtonsTextureAtlas,gameManager,"MusicIncreaseButton.png",95,0);
        settingsMenuDecreaseSoundButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioButtonsTextureAtlas,gameManager,"SoundDecreaseButton.png",0,77);;
        settingsMenuIncreaseSoundButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioButtonsTextureAtlas,gameManager,"SoundIncreaseButton.png",95,77);


        // -- Regiones Botones About --
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/Menu/About/");
        aboutMenuAndyButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuButtonTextureAtlas, gameManager, "AndyButton.png", 0, 0);
        aboutMenuDanniButtonTexureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuButtonTextureAtlas, gameManager, "DanniButton.png", 175, 0);
        aboutMenuRebeButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuButtonTextureAtlas, gameManager, "RebeButton.png", 350, 0);
        aboutMenuBrianButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuButtonTextureAtlas, gameManager, "BrianButton.png", 525, 0);
        aboutMenuDiegoButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuButtonTextureAtlas, gameManager, "DiegoButton.png", 700, 0);
        //--Region logo--
        aboutMenuLogoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuLogoTextureAtlas, gameManager, "ITESMLogo.png", 0, 0);

        // -- Regiones ID's about --
        aboutMenuDiegoIDTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuIDTextureAtlas, gameManager, "DiegoID.png", 0, 0);
        aboutMenuAndyIDTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuIDTextureAtlas, gameManager, "AndyID.png", 1000, 0);
        aboutMenuDanniIDTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuIDTextureAtlas, gameManager, "DanniID.png", 0, 500);
        aboutMenuRebeIDTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuIDTextureAtlas, gameManager, "RebeID.png", 1000, 500);
        aboutMenuBrianIDTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuIDTextureAtlas, gameManager, "BrianID.png", 0, 1000);



        // ============== Cargar el atlas de imágenes ============
         for(BitmapTextureAtlas atlas : menuBitmapTextureAtlasContainer){
             atlas.load();
         }

    }


    // ============== Liberar Recursos ===========================
    // ===========================================================
    public void unloadMenuResources(){
        for(BitmapTextureAtlas atlas : menuBitmapTextureAtlasContainer){
            atlas.unload();
            atlas = null;
        }

    }
    // ================================================================
    //            ESCENA NIVEL UNO ADVENTURE MODE
    // =================================================================

    // ============== Cargar Recursos Gráficos ===================
    // ===========================================================
    public void loadAdventureLevelOneResourcesGFX(){

        // ============== Texture Atlas ==========================
        adventureLevel1BitmapTextureAtlasContainer = new ArrayList<>();
        //---------------fondo----------------------------------
        //menuBackgroundTextureAtlas = new BitmapTextureAtlas(textureManager, 1280, 800, TextureOptions.BILINEAR);
        adventureLevel1Background1TextureAtlas = new BitmapTextureAtlas(textureManager,2048,1440,TextureOptions.BILINEAR);
        adventureLevel1BitmapTextureAtlasContainer.add(adventureLevel1Background1TextureAtlas);
        //adventureLevel1Background2TextureAtlas = new BitmapTextureAtlas(textureManager,600,4000);
        // -------------- Nave -----------------------------------
        adventureLevelOneShipTextureAtlas = new BitmapTextureAtlas(textureManager,214,235,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        adventureLevel1BitmapTextureAtlasContainer.add(adventureLevelOneShipTextureAtlas);
        // -------------- Meteoritos -----------------------------
        adventureLevelOneMeteoriteTextureRegions = new ArrayList<>();
        //---------------Botones----------------------------------
        adventureLevel1OneButtonsTextureAtlas = new BitmapTextureAtlas(textureManager,650,350,TextureOptions.BILINEAR);
        adventureLevel1BitmapTextureAtlasContainer.add(adventureLevel1OneButtonsTextureAtlas);

        // ============== Regiones =============================
        //----------------fondo----------------

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Level1/");
        adventureLevel1BackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevel1Background1TextureAtlas, gameManager, "fondoChico.png", 0, 0);
        adventureLevel1BackgroundStarsTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevel1Background1TextureAtlas, gameManager, "estrellasChicas.png", 0, 720);

        //------------Botones--------------
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Level1/buttons/");
        adventureLevel1PauseButtonTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevel1OneButtonsTextureAtlas, gameManager, "PauseButton.png", 500, 200);
        adventureLevel1PauseScreenTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevel1OneButtonsTextureAtlas, gameManager, "PauseScreen.png", 0, 0);
        adventureLevel1PlayButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevel1OneButtonsTextureAtlas, gameManager, "PlayButtonPauseScreen.png", 500, 0);
        adventureLevel1BackButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevel1OneButtonsTextureAtlas, gameManager, "DoorButtonPauseScreen.png", 500, 100);

        // -------------- Nave ---------------------------------
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Sprites/");
        adventureLevelOneAnimatedShipTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(adventureLevelOneShipTextureAtlas, gameManager, "sprite_nave.png",0,0,3,1);

        // -------------- Vidas --------------------------------
        adventureLevelOneLivesTexureRegion = loadImage("gfx/Level1/Lifes/casco_vida.png");

        // -------------- Meteoritos ---------------------------
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("gfx/Level1/Meteors/1.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("gfx/Level1/Meteors/2.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("gfx/Level1/Meteors/3.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("gfx/Level1/Meteors/4.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("gfx/Level1/Meteors/5.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("gfx/Level1/Meteors/6.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("gfx/Level1/Meteors/7.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("gfx/Level1/Meteors/8.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("gfx/Level1/Meteors/9.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("gfx/Level1/Meteors/10.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("gfx/Level1/Meteors/11.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("gfx/Level1/Meteors/12.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("gfx/Level1/Meteors/13.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("gfx/Level1/Meteors/14.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("gfx/Level1/Meteors/15.png"));

        // ================ Cargar los Atlas ====================
        for(BitmapTextureAtlas atlas : adventureLevel1BitmapTextureAtlasContainer){
            atlas.load();
        }
    }

    public void unloadAdventureLevelOneResources(){
        for(BitmapTextureAtlas atlas : adventureLevel1BitmapTextureAtlasContainer){
            atlas.unload();
        }

    }

    public void loadYouLoseResourcesGFX(){
        //--atlas del fondo
        YouLoseBackgroundTextureAtlas = new BitmapTextureAtlas(textureManager,1280,720,TextureOptions.BILINEAR);
        //--atlas de los botones
        YouLoseButtonsTextureAtlas = new BitmapTextureAtlas(textureManager,400,200,TextureOptions.BILINEAR_PREMULTIPLYALPHA);


        // ============== Regiones =============================
        //--fondo-------
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/YouLose/");
        YouLoseBackgroundTextureRegion  = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouLoseBackgroundTextureAtlas, gameManager, "fondo.jpg", 0, 0);
        //--Botones-----
        YouLoseExitButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouLoseButtonsTextureAtlas, gameManager, "DoorButtonPauseScreen.png", 0, 0);
        YouLoseRetryButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouLoseButtonsTextureAtlas, gameManager, "Retry_Norm.png", 200, 0);

        // ================ Cargar los Atlas ====================
        YouLoseBackgroundTextureAtlas.load();
        YouLoseButtonsTextureAtlas.load();

    }

    public void loadYouWinResourcesGFX(){
        //--atlas del fondo
        YouWinBackgroundTextureAtlas = new BitmapTextureAtlas(textureManager,1280,720,TextureOptions.BILINEAR);
        //--atlas de los botones
        YouWinButtonsTextureAtlas = new BitmapTextureAtlas(textureManager,650,200,TextureOptions.BILINEAR);
        YouWinGems1TextureAtlas = new BitmapTextureAtlas(textureManager,900,400,TextureOptions.BILINEAR);
        YouWinGems2TextureAtlas = new BitmapTextureAtlas(textureManager,900,400,TextureOptions.BILINEAR);
        YouWinGems3TextureAtlas = new BitmapTextureAtlas(textureManager,900,400,TextureOptions.BILINEAR);
        YouWinGems4TextureAtlas = new BitmapTextureAtlas(textureManager,900,400,TextureOptions.BILINEAR);


        // ============== Regiones =============================
        //--fondo-------
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/YouLose/");
        YouWinBackgroundTextureRegion  = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinBackgroundTextureAtlas, gameManager, "fondoWin.jpg", 0, 0);
        //--Botones-----
        YouWinExitButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinButtonsTextureAtlas, gameManager, "DoorButtonPauseScreen.png", 0, 0);
        YouWinRetryButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinButtonsTextureAtlas, gameManager, "Retry_Norm.png", 200, 0);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/buttons/");
        YouWinContinueButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinButtonsTextureAtlas, gameManager, "arrow_right.png", 315, 0);
        // --Gemas------
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/Menu/Backpack/");
        YouWinGemBlue1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems1TextureAtlas, gameManager, "GemBlue1.png", 0, 0);
        YouWinGemBlue2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems1TextureAtlas, gameManager, "GemBlue2.png", 300, 0);
        YouWinGemBlue3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems1TextureAtlas, gameManager, "GemBlue3.png", 600, 0);
        YouWinGemPink1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems2TextureAtlas, gameManager, "GemPink1.png",0, 0);
        YouWinGemPink2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems2TextureAtlas, gameManager, "GemPink2.png", 300, 0);
        YouWinGemPink3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems2TextureAtlas, gameManager, "GemPink3.png", 600, 0);
        YouWinGemYellow1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems3TextureAtlas, gameManager, "GemYellow1.png", 0, 0);
        YouWinGemYellow2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems3TextureAtlas, gameManager, "GemYellow2.png", 300, 0);
        YouWinGemYellow3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems3TextureAtlas, gameManager, "GemYellow3.png", 600, 0);
        YouWinGemLocked1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems4TextureAtlas, gameManager, "GemLocked1.png", 0, 0);
        YouWinGemLocked2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems4TextureAtlas, gameManager, "GemLocked2.png", 300, 0);
        YouWinGemLocked3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems4TextureAtlas, gameManager, "GemLocked3.png", 600, 0);

        // ================ Cargar los Atlas ====================
        YouWinBackgroundTextureAtlas.load();
        YouWinButtonsTextureAtlas.load();
        YouWinGems1TextureAtlas.load();
        YouWinGems2TextureAtlas.load();
        YouWinGems3TextureAtlas.load();
        YouWinGems4TextureAtlas.load();


    }




    // =============================================================================================
    //                              M É T O D O S  A U X I L I A R E S
    // =============================================================================================

    // ===========================================================
    //               Cargar una imágen de archivo
    // ===========================================================
    public ITextureRegion loadImage(String filename){
        ITextureRegion region = null;
        try {
            ITexture texture = new AssetBitmapTexture(
                    gameManager.getTextureManager(), gameManager.getAssets(),filename);
            texture.load();
            region = TextureRegionFactory.
                    extractFromTexture(texture);
        } catch (IOException e) {
            Log.i("loadImage()", "Could not load: " + filename);
        }
        return region;
    }

    // ===========================================================
    //         Cargar un sprite a partir de un ITextureRegion
    // ===========================================================
    public Sprite loadSprite(float px, float py, final ITextureRegion region){

        return new Sprite(px, py, region, gameManager.getVertexBufferObjectManager()) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) { // Optimizando
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
    }


    // ===========================================================
    //          Cargar la música del nivel
    // ===========================================================
    public void setMusic(String filename){
        if(backgroundMusic != null){releaseMusic();}

        try{
            backgroundMusic = MusicFactory.createMusicFromAsset(musicManager,gameManager,filename);
            backgroundMusic.setLooping(true);
        }
        catch(IOException e){
            e.printStackTrace();
            return;
        }
    }

    public void setSound(String filename, int soundNumber){
        releaseSound(soundNumber);
        switch(soundNumber){
            case 1:
                try{soundOne = SoundFactory.createSoundFromAsset(soundManager,gameManager,filename);}
                catch(IOException e){e.printStackTrace();return;}
                break;
            case 2:
                try{soundTwo = SoundFactory.createSoundFromAsset(soundManager,gameManager,filename);}
                catch(IOException e){e.printStackTrace();return;}
                break;
            case 3:
                try{soundThree = SoundFactory.createSoundFromAsset(soundManager,gameManager,filename);}
                catch(IOException e){e.printStackTrace();return;}
                break;
            case 4:
                try{soundFour = SoundFactory.createSoundFromAsset(soundManager,gameManager,filename);}
                catch(IOException e){e.printStackTrace();return;}
                break;
            case 5:
                try{soundFive = SoundFactory.createSoundFromAsset(soundManager,gameManager,filename);}
                catch(IOException e){e.printStackTrace();return;}
                break;
        }
    }
    // ===========================================================
    //              Cambiar el volumen de las cosas
    // ===========================================================
    public void updateAudioVolume(){
        musicManager.setMasterVolume(sessionManager.musicVolume);
        soundManager.setMasterVolume(sessionManager.soundVolume);

        if(backgroundMusic != null){backgroundMusic.setVolume(musicManager.getMasterVolume());}
        if(soundOne != null){soundOne.setVolume(soundManager.getMasterVolume());}
        if(soundTwo!= null){soundTwo.setVolume(soundManager.getMasterVolume());}
        if(soundThree != null){soundThree.setVolume(soundManager.getMasterVolume());}
        if(soundFour != null){soundFour.setVolume(soundManager.getMasterVolume());}
        if(soundFive != null){soundFive.setVolume(soundManager.getMasterVolume());}

        sessionManager.writeChanges();
    }

    // ===========================================================
    //                   Detener Audio
    // ===========================================================
    public void releaseAudio(){
        releaseMusic();
        releaseAllSound();
    }
    // ===========================================================
    //                     Liberar la música
    // ===========================================================
    public void releaseMusic(){
        if(backgroundMusic != null){
            backgroundMusic.stop();
            backgroundMusic.release();
            backgroundMusic = null;
        }
    }
    // ===========================================================
    //                   Liberar TODOS los sonidos
    // ===========================================================
    public void releaseAllSound(){
        if(soundOne != null){
            soundOne.release();
            soundOne = null;
        }
        if(soundTwo != null){
            soundTwo.release();
            soundTwo = null;
        }
        if(soundThree != null){
            soundThree.release();
            soundThree = null;
        }
        if(soundFour != null){
            soundFour.release();
            soundFour = null;
        }
        if(soundFive != null){
            soundFive.release();
            soundFive = null;
        }
    }
    // ===========================================================
    //                  Liberar un sonido en particular
    // ===========================================================
    public void releaseSound(int soundNumber){
        switch (soundNumber){
            case 1:
                if(soundOne != null){
                    soundOne.release();
                    soundOne = null;
                }
                break;
            case 2:
                if(soundTwo != null){
                    soundTwo.release();
                    soundTwo = null;
                }
                break;
            case 3:
                if(soundThree != null){
                    soundThree.release();
                    soundThree = null;
                }
                break;
            case 4:
                if(soundFour != null){
                    soundFour.release();
                    soundFour = null;
                }
                break;
            case 5:
                if(soundFive != null){
                    soundFive.release();
                    soundFive = null;
                }
                break;
        }
    }

    // =============================================================================================
    //                                G E T T E R S  &  S E T T E R S
    // =============================================================================================

    // ============== REGRESA la INSTANCIA de este Adm. =========
    public static ResourceManager getInstance(){return INSTANCE;}
}
