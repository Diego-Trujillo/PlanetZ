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
    //---------------Boton de juego nuevo, alerta y opciones
    private BitmapTextureAtlas settingsMenuButtonsTextureAtlas;
    private BitmapTextureAtlas settingsMenuAlertTextureAtlas;
    public ITextureRegion settingsMenuNewGameButtonTextureRegion;
    public ITextureRegion settingsMenuCancelButtonTextureRegion;
    public ITextureRegion settingsMenuOKButtonTextureRegion;
    public ITextureRegion settingsMenuAlertTextureRegion;


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
    public BitmapTextureAtlas adventureLevelOneBackgroundStars1TextureAtlas;
    public BitmapTextureAtlas adventureLevelOneBackgroundStars2TextureAtlas;
    public BitmapTextureAtlas adventureLevelOneBackgroundStars3TextureAtlas;

    // =============== Regiones de Texturas ======================
    // --------------- Botones -----------------------------------
    public ITextureRegion adventureLevel1PauseButtonTextureRegion;
    public ITextureRegion adventureLevel1PauseScreenTextureRegion;
    public ITextureRegion adventureLevel1PlayButtonTextureRegion;
    public ITextureRegion adventureLevel1BackButtonTextureRegion;

    //-----------------Particula----------------------------------
    public ITextureRegion adventureLevel1ParticleTextureRegion;
    // --------------- Meteoritos --------------------------------
    public ArrayList<ITextureRegion> adventureLevelOneMeteoriteTextureRegions;
    public ITiledTextureRegion adventureLevelOneAnimatedShipTextureRegion;
    // --------------- Vidas -------------------------------------
    public ITextureRegion adventureLevelOneLivesTexureRegion;
    // --------------- Fondo ------------------------------------
    public BitmapTextureAtlas adventureLevelOneParticleTextureAtlas;
    public ITextureRegion adventureLevelOneBackgroundStars1TextureRegion;
    public ITextureRegion adventureLevelOneBackgroundStars2TextureRegion;
    public ITextureRegion adventureLevelOneBackgroundStars3TextureRegion;


    // ===========================================================================
    //                ESCENA NIVEL DOS ADVENTURE MODE
    // ===========================================================================

    // ============== RECURSOS GRÁFICOS ==========================
    // ===========================================================

    // ============== Contenedor de BitmapAtlas ==================
    ArrayList<BitmapTextureAtlas> adventureLevelTwoBitmapTextureAtlasContainer;

    // ============== Contenedor de Atlas's de Región ============
    // -------------- Fondo
    public BitmapTextureAtlas adventureLevelTwoBackgroundSkyTextureAtlas;
    public BitmapTextureAtlas adventureLevelTwoBackgroundRocks1TextureAtlas;
    public BitmapTextureAtlas adventureLevelTwoBackgroundRocks2TextureAtlas;
    public BitmapTextureAtlas adventureLevelTwoBackgroundRocks3TextureAtlas;
    // -------------- Plataformas
    public BitmapTextureAtlas adventureLevelTwoPlatormsBigTextureAtlas;
    // -------------- Obstáculos
    public BitmapTextureAtlas adventureLevelTwoObstaclesTextureAtlas;

    // ============== Texturas ====================================
    public ITextureRegion adventureLevelTwoBackgroundSkyTextureRegion;
    public ITextureRegion adventureLevelTwoBackgroundRocks1TextureRegion;
    public ITextureRegion adventureLevelTwoBackgroundRocks2TextureRegion;
    public ITextureRegion adventureLevelTwoBackgroundRocks3TextureRegion;

    public ArrayList<ITextureRegion> adventureLevelTwoPlatformsBigTextureRegion;

    public ArrayList<ITextureRegion> adventureLevelTwoObstaclesTextureRegion;



    // ===========================================================================
    //                ESCENA NIVEL TRES ADVENTURE MODE
    // ===========================================================================

    // ============== RECURSOS GRÁFICOS ==========================
    // ===========================================================

    // ============== Contenedor de BitmapAtlas ==================
    ArrayList<BitmapTextureAtlas> adventureLevelThreeBitmapTextureAtlasContainer;

    // ============== Contenedor de Atlas's de Región ============
    // -------------- Fondo
    public BitmapTextureAtlas adventureLevelThreeBackgroundSkyTextureAtlas;
    public BitmapTextureAtlas adventureLevelThreeBackgroundRocks1TextureAtlas;
    public BitmapTextureAtlas adventureLevelThreeBackgroundRocks2TextureAtlas;
    public BitmapTextureAtlas adventureLevelThreeBackgroundRocks3TextureAtlas;
    // -------------- Plataformas
    public BitmapTextureAtlas adventureLevelThreePlatformsBigTextureAtlas;
    public BitmapTextureAtlas adventureLevelThreePlatformsSmallTextureAtlas;
    // -------------- Obstáculos
    public BitmapTextureAtlas adventureLevelThreeObstaclesTextureAtlas;

    // ============== Texturas ====================================
    public ITextureRegion adventureLevelThreeBackgroundSkyTextureRegion;
    public ITextureRegion adventureLevelThreeBackgroundRocks1TextureRegion;
    public ITextureRegion adventureLevelThreeBackgroundRocks2TextureRegion;
    public ITextureRegion adventureLevelThreeBackgroundRocks3TextureRegion;

    public ArrayList<ITextureRegion> adventureLevelThreePlatformsBigTextureRegion;
    public ArrayList<ITextureRegion> adventureLevelThreePlatformsSmallTextureRegion;


    public ArrayList<ITextureRegion> adventureLevelThreeObstaclesTextureRegion;




    // ============================================================================
    //                            Elementos del HUD
    // ============================================================================
    public ITextureRegion HUDPlayerLivesTextureRegion;
    public ITextureRegion HUDPauseButtonTextureRegion;
    public ITextureRegion HUDPauseScreenTextureRegion;
    public ITextureRegion HUDUnpauseButtonTextureRegion;
    public ITextureRegion HUDReturnToMenuButtonTextureRegion;

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
    public BitmapTextureAtlas YouWinBackgroundTextureAtlas;
    public BitmapTextureAtlas YouWinButtonsTextureAtlas;
    //--------Texturas----
    public ITextureRegion YouWinBackgroundTextureRegion;
    public ITextureRegion YouWinExitButtonTextureRegion;
    public ITextureRegion YouWinRetryButtonTextureRegion;
    public ITextureRegion YouWinContinueButtonTextureRegion;

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
        // -- Estrellas Fondo 2 --
        menuBackgroundStars1TextureAtlas = new BitmapTextureAtlas(textureManager, 1366, 768, TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(menuBackgroundStars1TextureAtlas);

        // -- Estrellas Fondo 3 --
        menuBackgroundStars2TextureAtlas = new BitmapTextureAtlas(textureManager, 1366, 768, TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(menuBackgroundStars2TextureAtlas);

        // -- Estrellas Fondo 4 --
        menuBackgroundStars3TextureAtlas = new BitmapTextureAtlas(textureManager, 1366, 768, TextureOptions.BILINEAR);
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

        settingsMenuButtonsTextureAtlas =  new BitmapTextureAtlas(textureManager, 480,200,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(settingsMenuButtonsTextureAtlas);

        settingsMenuAlertTextureAtlas =  new BitmapTextureAtlas(textureManager, 500,300,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(settingsMenuAlertTextureAtlas);


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

        menuBackgroundStars1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBackgroundStars1TextureAtlas, gameManager, "layerstars1.png", 0, 0);
        menuBackgroundStars2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBackgroundStars2TextureAtlas, gameManager, "layerstars2.png", 0, 0);
        menuBackgroundStars3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBackgroundStars3TextureAtlas, gameManager, "layerstars3.png", 0, 0);

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
        mainMenuButtonTextureRegion_play = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mainMenuButtonTextureAtlas, gameManager, "PlayButton.png", 0, 0, 2, 1);
        mainMenuButtonTextureRegion_backpack = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mainMenuButtonTextureAtlas, gameManager, "BackpackButton.png", 0, 128, 2, 1);
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

        settingsMenuNewGameButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuButtonsTextureAtlas,gameManager,"eraseData.png",0,0);
        settingsMenuCancelButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuButtonsTextureAtlas,gameManager,"cancelButton.png",200,0);
        settingsMenuOKButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuButtonsTextureAtlas,gameManager,"okButton.png",340,0);
        settingsMenuAlertTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAlertTextureAtlas,gameManager,"alert.png",0,0);
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
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/Background/");
        adventureLevelOneBackgroundStars1TextureAtlas = new BitmapTextureAtlas(textureManager, 1366, 768, TextureOptions.BILINEAR);
        adventureLevelOneBackgroundStars2TextureAtlas = new BitmapTextureAtlas(textureManager, 1366, 768, TextureOptions.BILINEAR);
        adventureLevelOneBackgroundStars3TextureAtlas = new BitmapTextureAtlas(textureManager, 1366, 768, TextureOptions.BILINEAR);
        // -------------- Nave -----------------------------------
        adventureLevelOneShipTextureAtlas = new BitmapTextureAtlas(textureManager,214,130,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        adventureLevel1BitmapTextureAtlasContainer.add(adventureLevelOneShipTextureAtlas);
        //----------------Particulas-------------------------------
        adventureLevelOneParticleTextureAtlas = new BitmapTextureAtlas(textureManager, 15, 16, TextureOptions.BILINEAR);
        adventureLevel1BitmapTextureAtlasContainer.add(adventureLevelOneParticleTextureAtlas);
        // -------------- Meteoritos -----------------------------
        adventureLevelOneMeteoriteTextureRegions = new ArrayList<>();


        // ============== Regiones =============================
        //----------------fondo----------------

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/CrashCourseIntoOblivion/");
        adventureLevel1BitmapTextureAtlasContainer.add(adventureLevelOneBackgroundStars1TextureAtlas);
        adventureLevel1BitmapTextureAtlasContainer.add(adventureLevelOneBackgroundStars2TextureAtlas);
        adventureLevel1BitmapTextureAtlasContainer.add(adventureLevelOneBackgroundStars3TextureAtlas);

        // -------------- Nave ---------------------------------
        adventureLevelOneAnimatedShipTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(adventureLevelOneShipTextureAtlas, gameManager, "Ship.png", 0, 0, 3, 1);

        //----------------Particula-----------------------------
        adventureLevel1ParticleTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelOneParticleTextureAtlas, gameManager, "smokeParticle.png", 0, 0);



        //----------------Fondos--------------------------------
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/CrashCourseIntoOblivion/Background/");
        adventureLevelOneBackgroundStars1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelOneBackgroundStars1TextureAtlas, gameManager, "BackgroundLayer1.png", 0, 0);
        adventureLevelOneBackgroundStars2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelOneBackgroundStars2TextureAtlas, gameManager, "BackgroundLayer2.png", 0, 0);
        adventureLevelOneBackgroundStars3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelOneBackgroundStars3TextureAtlas, gameManager, "BackgroundLayer3.png", 0, 0);


        // -------------- Meteoritos ---------------------------
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("Graphics/CrashCourseIntoOblivion/Meteors/1.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("Graphics/CrashCourseIntoOblivion/Meteors/2.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("Graphics/CrashCourseIntoOblivion/Meteors/3.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("Graphics/CrashCourseIntoOblivion/Meteors/4.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("Graphics/CrashCourseIntoOblivion/Meteors/5.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("Graphics/CrashCourseIntoOblivion/Meteors/6.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("Graphics/CrashCourseIntoOblivion/Meteors/7.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("Graphics/CrashCourseIntoOblivion/Meteors/8.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("Graphics/CrashCourseIntoOblivion/Meteors/9.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("Graphics/CrashCourseIntoOblivion/Meteors/10.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("Graphics/CrashCourseIntoOblivion/Meteors/11.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("Graphics/CrashCourseIntoOblivion/Meteors/12.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("Graphics/CrashCourseIntoOblivion/Meteors/13.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("Graphics/CrashCourseIntoOblivion/Meteors/14.png"));
        adventureLevelOneMeteoriteTextureRegions.add(loadImage("Graphics/CrashCourseIntoOblivion/Meteors/15.png"));

        // ================ Cargar los Atlas ====================
        for(BitmapTextureAtlas atlas : adventureLevel1BitmapTextureAtlasContainer){
            atlas.load();
        }
    }
    // ============== Liberar Recursos ===========================
    // ===========================================================
    public void unloadAdventureLevelOneResources(){
        for(BitmapTextureAtlas atlas : adventureLevel1BitmapTextureAtlasContainer){
            atlas.unload();
        }

    }
    // ================================================================
    //            ESCENA NIVEL DOS ADVENTURE MODE
    // =================================================================

    // ============== Cargar Recursos Gráficos ===================
    // ===========================================================
    public void loadAdventureLevelTwoResourcesGFX(){
        adventureLevelTwoBitmapTextureAtlasContainer = new ArrayList<>();

        // -- Bitmap TextureAtlas
        adventureLevelTwoBackgroundSkyTextureAtlas = new BitmapTextureAtlas(textureManager,1280,720,TextureOptions.BILINEAR);
        adventureLevelTwoBitmapTextureAtlasContainer.add(adventureLevelTwoBackgroundSkyTextureAtlas);

        adventureLevelTwoBackgroundRocks1TextureAtlas = new BitmapTextureAtlas(textureManager,1280,720,TextureOptions.BILINEAR);
        adventureLevelTwoBitmapTextureAtlasContainer.add(adventureLevelTwoBackgroundRocks1TextureAtlas);

        adventureLevelTwoBackgroundRocks2TextureAtlas = new BitmapTextureAtlas(textureManager,1280,720,TextureOptions.BILINEAR);
        adventureLevelTwoBitmapTextureAtlasContainer.add(adventureLevelTwoBackgroundRocks2TextureAtlas);

        adventureLevelTwoBackgroundRocks3TextureAtlas = new BitmapTextureAtlas(textureManager,1280,720,TextureOptions.BILINEAR);
        adventureLevelTwoBitmapTextureAtlasContainer.add(adventureLevelTwoBackgroundRocks3TextureAtlas);

        adventureLevelTwoPlatormsBigTextureAtlas = new BitmapTextureAtlas(textureManager,600,108,TextureOptions.BILINEAR);
        adventureLevelTwoBitmapTextureAtlasContainer.add(adventureLevelTwoPlatormsBigTextureAtlas);

        adventureLevelTwoObstaclesTextureAtlas = new BitmapTextureAtlas(textureManager,197,64,TextureOptions.BILINEAR);
        adventureLevelTwoBitmapTextureAtlasContainer.add(adventureLevelTwoObstaclesTextureAtlas);


        // ============ Texturas
        // ------------- Fondo
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/BraveNewWorld/IntoTheWilderness/");
        adventureLevelTwoBackgroundSkyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelTwoBackgroundSkyTextureAtlas,gameManager,"BackgroundSky.png",0,0);
        adventureLevelTwoBackgroundRocks1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelTwoBackgroundRocks1TextureAtlas,gameManager,"BackgroundRocks1.png",0,0);
        adventureLevelTwoBackgroundRocks2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelTwoBackgroundRocks2TextureAtlas,gameManager,"BackgroundRocks2.png",0,0);;
        adventureLevelTwoBackgroundRocks3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelTwoBackgroundRocks3TextureAtlas,gameManager,"BackgroundRocks3.png",0,0);;

        // ----------- Plataformas
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/BraveNewWorld/IntoTheWilderness/Platforms/Big/");
        adventureLevelTwoPlatformsBigTextureRegion = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            adventureLevelTwoPlatformsBigTextureRegion.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelTwoPlatormsBigTextureAtlas,gameManager,Integer.toString(i+1)+".png",0,i*36));
        }

        // ------------ Obstáculos
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/BraveNewWorld/IntoTheWilderness/Enemies/");
        adventureLevelTwoObstaclesTextureRegion = new ArrayList<>();


        adventureLevelTwoObstaclesTextureRegion.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelTwoObstaclesTextureAtlas,gameManager,"1.png",0,0));
        adventureLevelTwoObstaclesTextureRegion.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelTwoObstaclesTextureAtlas,gameManager,"2.png",66,0));
        adventureLevelTwoObstaclesTextureRegion.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelTwoObstaclesTextureAtlas,gameManager,"3.png",129,0));

        // ============== Cargar Atlas
        for(BitmapTextureAtlas atlas : adventureLevelTwoBitmapTextureAtlasContainer){
            atlas.load();
        }

    }
    // ============== Liberar Recursos ===========================
    // ===========================================================
    public void unloadAdventureLevelTwoResources(){
        for(BitmapTextureAtlas atlas : adventureLevelTwoBitmapTextureAtlasContainer){
            atlas.unload();
            atlas = null;
        }
    }


    // ================================================================
    //            ESCENA NIVEL TRES ADVENTURE MODE
    // =================================================================

    // ============== Cargar Recursos Gráficos ===================
    // ===========================================================
    public void loadAdventureLevelThreeResourcesGFX(){
        adventureLevelThreeBitmapTextureAtlasContainer = new ArrayList<>();

        // -- Bitmap TextureAtlas
        adventureLevelThreeBackgroundSkyTextureAtlas = new BitmapTextureAtlas(textureManager,1280,720,TextureOptions.BILINEAR);
        adventureLevelThreeBitmapTextureAtlasContainer.add(adventureLevelThreeBackgroundSkyTextureAtlas);

        adventureLevelThreeBackgroundRocks1TextureAtlas = new BitmapTextureAtlas(textureManager,1280,720,TextureOptions.BILINEAR);
        adventureLevelThreeBitmapTextureAtlasContainer.add(adventureLevelThreeBackgroundRocks1TextureAtlas);

        adventureLevelThreeBackgroundRocks2TextureAtlas = new BitmapTextureAtlas(textureManager,1280,720,TextureOptions.BILINEAR);
        adventureLevelThreeBitmapTextureAtlasContainer.add(adventureLevelThreeBackgroundRocks2TextureAtlas);

        adventureLevelThreeBackgroundRocks3TextureAtlas = new BitmapTextureAtlas(textureManager,1280,720,TextureOptions.BILINEAR);
        adventureLevelThreeBitmapTextureAtlasContainer.add(adventureLevelThreeBackgroundRocks3TextureAtlas);

        adventureLevelThreePlatformsBigTextureAtlas = new BitmapTextureAtlas(textureManager,589,104,TextureOptions.BILINEAR);
        adventureLevelThreeBitmapTextureAtlasContainer.add(adventureLevelThreePlatformsBigTextureAtlas);

        adventureLevelThreePlatformsSmallTextureAtlas = new BitmapTextureAtlas(textureManager,308,150,TextureOptions.BILINEAR);
        adventureLevelThreeBitmapTextureAtlasContainer.add(adventureLevelThreePlatformsSmallTextureAtlas);

        adventureLevelThreeObstaclesTextureAtlas = new BitmapTextureAtlas(textureManager,160,68,TextureOptions.BILINEAR);
        adventureLevelThreeBitmapTextureAtlasContainer.add(adventureLevelThreeObstaclesTextureAtlas);


        // ============ Texturas
        // ------------- Fondo
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/BraveNewWorld/NewtonRedefined/");
        adventureLevelThreeBackgroundSkyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelThreeBackgroundSkyTextureAtlas,gameManager,"colorfondo.png",0,0);
        adventureLevelThreeBackgroundRocks1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelThreeBackgroundRocks1TextureAtlas,gameManager,"fondofinal.png",0,0);
        adventureLevelThreeBackgroundRocks2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelThreeBackgroundRocks2TextureAtlas,gameManager,"fondomedio.png",0,0);;
        adventureLevelThreeBackgroundRocks3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelThreeBackgroundRocks3TextureAtlas,gameManager,"fondofrente.png",0,0);;

        // ----------- Plataformas
        adventureLevelThreePlatformsBigTextureRegion = new ArrayList<>();
        adventureLevelThreePlatformsSmallTextureRegion = new ArrayList<>();


        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/BraveNewWorld/NewtonRedefined/Platforms/Small/");
        adventureLevelThreePlatformsSmallTextureRegion.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelThreePlatformsSmallTextureAtlas,gameManager,"1.png",0,0));
        adventureLevelThreePlatformsSmallTextureRegion.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelThreePlatformsSmallTextureAtlas,gameManager,"2.png",0,39));
        adventureLevelThreePlatformsSmallTextureRegion.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelThreePlatformsSmallTextureAtlas,gameManager,"3.png",0,68));

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/BraveNewWorld/NewtonRedefined/Platforms/Big/");
        adventureLevelThreePlatformsBigTextureRegion.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelThreePlatformsBigTextureAtlas,gameManager,"1.png",0,0));
        adventureLevelThreePlatformsBigTextureRegion.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelThreePlatformsBigTextureAtlas,gameManager,"2.png",0,26));
        adventureLevelThreePlatformsBigTextureRegion.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelThreePlatformsBigTextureAtlas,gameManager,"3.png",0,52));
        //------------ Obstáculos
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/BraveNewWorld/NewtonRedefined/Enemies/");
        adventureLevelThreeObstaclesTextureRegion = new ArrayList<>();


        adventureLevelThreeObstaclesTextureRegion.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelThreeObstaclesTextureAtlas,gameManager,"cosamala.png",0,0));
        adventureLevelThreeObstaclesTextureRegion.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(adventureLevelThreeObstaclesTextureAtlas,gameManager,"picos.png",84,0));

        // ============== Cargar Atlas
        for(BitmapTextureAtlas atlas : adventureLevelThreeBitmapTextureAtlasContainer){
            atlas.load();
        }

    }
    // ============== Liberar Recursos ===========================
    // ===========================================================
    public void unloadAdventureLevelThreeResources(){
        for(BitmapTextureAtlas atlas : adventureLevelThreeBitmapTextureAtlasContainer){
            atlas.unload();
            atlas = null;
        }
    }
    // ================================================================
    //                         ELEMENTOS HUD
    // =================================================================
    public void loadHUD(){
        HUDPlayerLivesTextureRegion = loadImage("Graphics/HUD/LifeIcon.png");
        HUDPauseButtonTextureRegion = loadImage("Graphics/HUD/PauseButton.png");
        HUDPauseScreenTextureRegion = loadImage("Graphics/HUD/PauseScreen.png");
        HUDUnpauseButtonTextureRegion = loadImage("Graphics/HUD/UnpauseButton.png");
        HUDReturnToMenuButtonTextureRegion = loadImage("Graphics/HUD/BackButton.png");
    }

    public void unloadHUD(){
        HUDPlayerLivesTextureRegion.getTexture().unload();
        HUDPlayerLivesTextureRegion =  null;

        HUDPauseButtonTextureRegion.getTexture().unload();
        HUDPauseButtonTextureRegion = null;

        HUDPauseScreenTextureRegion.getTexture().unload();
        HUDPauseScreenTextureRegion = null;

        HUDUnpauseButtonTextureRegion.getTexture().unload();
        HUDUnpauseButtonTextureRegion = null;

        HUDReturnToMenuButtonTextureRegion.getTexture().unload();
        HUDReturnToMenuButtonTextureRegion = null;
    }

    public void loadYouLoseResourcesGFX(){
        //--atlas del fondo
        YouLoseBackgroundTextureAtlas = new BitmapTextureAtlas(textureManager,1280,720,TextureOptions.BILINEAR);
        //--atlas de los botones
        YouLoseButtonsTextureAtlas = new BitmapTextureAtlas(textureManager,400,200,TextureOptions.BILINEAR_PREMULTIPLYALPHA);


        // ============== Regiones =============================
        //--fondo-------
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/GameOver/");
        YouLoseBackgroundTextureRegion  = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouLoseBackgroundTextureAtlas, gameManager, "Background.jpg", 0, 0);
        //--Botones-----
        YouLoseExitButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouLoseButtonsTextureAtlas, gameManager, "BackButton.png", 0, 0);
        YouLoseRetryButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouLoseButtonsTextureAtlas, gameManager, "RetryButton.png", 200, 0);

        // ================ Cargar los Atlas ====================
        YouLoseBackgroundTextureAtlas.load();
        YouLoseButtonsTextureAtlas.load();

    }

    public void loadYouWinResourcesGFX(){
        //--atlas del fondo
        YouWinBackgroundTextureAtlas = new BitmapTextureAtlas(textureManager,1280,720,TextureOptions.BILINEAR);
        //--atlas de los botones
        YouWinButtonsTextureAtlas = new BitmapTextureAtlas(textureManager,384,128,TextureOptions.BILINEAR);
        YouWinGems1TextureAtlas = new BitmapTextureAtlas(textureManager,600,300,TextureOptions.BILINEAR);
        YouWinGems2TextureAtlas = new BitmapTextureAtlas(textureManager,600,300,TextureOptions.BILINEAR);
        YouWinGems3TextureAtlas = new BitmapTextureAtlas(textureManager,600,300,TextureOptions.BILINEAR);
        YouWinGems4TextureAtlas = new BitmapTextureAtlas(textureManager,600,300,TextureOptions.BILINEAR);


        // ============== Regiones =============================
        //--fondo-------
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("Graphics/WinScene/");
        YouWinBackgroundTextureRegion  = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinBackgroundTextureAtlas, gameManager, "ScreenWin.jpg", 0, 0);
        //--Botones-----
        YouWinExitButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinButtonsTextureAtlas, gameManager, "DoorButtonPauseScreen.png", 0, 0);
        YouWinRetryButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinButtonsTextureAtlas, gameManager, "Retry_Norm.png", 128, 0);

        // Flecha de Next
        YouWinContinueButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinButtonsTextureAtlas, gameManager, "NextArrow.png", 256, 0);
        // --Gemas------

        YouWinGemBlue1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems1TextureAtlas, gameManager, "ScreenWinGemBlue1.png", 0, 0);
        YouWinGemBlue2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems1TextureAtlas, gameManager, "ScreenWinGemBlue2.png", 200, 0);
        YouWinGemBlue3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems1TextureAtlas, gameManager, "ScreenWinGemBlue3.png", 400, 0);
        YouWinGemPink1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems2TextureAtlas, gameManager, "ScreenWinGemPink1.png",0, 0);
        YouWinGemPink2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems2TextureAtlas, gameManager, "ScreenWinGemPink2.png", 200, 0);
        YouWinGemPink3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems2TextureAtlas, gameManager, "ScreenWinGemPink3.png", 400, 0);
        YouWinGemYellow1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems3TextureAtlas, gameManager, "ScreenWinGemYellow1.png", 0, 0);
        YouWinGemYellow2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems3TextureAtlas, gameManager, "ScreenWinGemYellow2.png", 200, 0);
        YouWinGemYellow3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems3TextureAtlas, gameManager, "ScreenWinGemYellow3.png", 400, 0);
        YouWinGemLocked1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems4TextureAtlas, gameManager, "ScreenWinGemLocked1.png", 0, 0);
        YouWinGemLocked2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems4TextureAtlas, gameManager, "ScreenWinGemLocked2.png", 200, 0);
        YouWinGemLocked3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(YouWinGems4TextureAtlas, gameManager, "ScreenWinGemLocked3.png", 400, 0);

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
