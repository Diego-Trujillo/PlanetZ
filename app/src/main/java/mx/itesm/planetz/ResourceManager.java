package mx.itesm.planetz;

import android.util.Log;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;
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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

import java.io.IOException;
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


    // ===========================================================
    //                        ESCENA SPLASH
    // ===========================================================

    // ============== RECURSOS GRÁFICOS ==========================
    // ===========================================================
    private BitmapTextureAtlas splashTextureAtlas;
    public ITextureRegion splashTextureRegion_background;
    public ITextureRegion splashTextureRegion_logo;

    // ============== RECURSOS MUSICALES =========================
    // ===========================================================
    public Music splashMusic;


    // ===========================================================
    //                        ESCENA MENU
    // ===========================================================

    // ============== RECURSOS GRÁFICOS ==========================
    // ===========================================================
    // ============== Contenedor de Atlas's de Región ============
    private ArrayList<BitmapTextureAtlas> menuBitmapTextureAtlasContainer;

    // ============== Fondo ======================================
    private BitmapTextureAtlas menuBackgroundTextureAtlas;
    public ITextureRegion menuBackgroundTextureRegion;

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
    // ============== SUBMENU BACKPACK ===========================
    private BitmapTextureAtlas backpackMenuButtonTextureAtlas;
    public ITextureRegion backpackMenuLeftArrowTextureRegion;
    public ITextureRegion backpackMenuRightArrowTextureRegion;


    // -------------- Mochila cosas ------------------------------
    private BitmapTextureAtlas  backpackMenuMochilaTextureAtlas;
    public ITextureRegion  backpackMenuMochilaTextureRegion;
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

    // ============== RECURSOS MUSICALES =========================
    // ===========================================================
    public Music menuMusic;

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
        MusicFactory.setAssetBasePath("mfx/");

    }

    // =========================================================== *
    //                        ESCENA SPLASH
    // ===========================================================

    // ============== Cargar Recursos Gráficos ===================
    // ===========================================================

    public void loadSplashResourcesGFX(){
        // ============== Asignar el directorio base =============
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/splash/");

        // ============== Asignarndo espacio en memoria ==========
        splashTextureAtlas = new BitmapTextureAtlas(textureManager,2048,2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        // ============== Cargando las imágenes ==================
        splashTextureRegion_background = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,gameManager,"background.jpg",0,0);
        splashTextureRegion_logo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,gameManager,"logo_itesm.png",0,720);

        // ============== Cargar el atlas de imágenes ============
        splashTextureAtlas.load();
    }

    // ============== Cargar Recursos Musicales ==================
    // ===========================================================

    public void loadSplashResourcesMFX(){
        // ============== Intentar cargar desde archivo ==========
        try {splashMusic = MusicFactory.createMusicFromAsset(musicManager,gameManager,"splash.ogg");}
        catch (IOException e) {e.printStackTrace();}

        // ============== Opciones de música =====================
        splashMusic.setVolume(musicManager.getMasterVolume());
        splashMusic.setLooping(false);
    }

    // ============== Liberar Recursos ===========================
    // ===========================================================
    public void unloadSplashResources(){
        // ============== Recursos Gráficos ======================
        splashTextureAtlas.unload();
        splashTextureAtlas = null;
        splashTextureRegion_logo = null;
        splashTextureRegion_background = null;

        // ============== Recursos  Musicales ====================
        splashMusic.stop();
        splashMusic.release();
        splashMusic = null;
    }

    // =========================================================== *
    //                        ESCENA MENÚ
    // ===========================================================

    // ============== Cargar Recursos Gráficos ===================
    // ===========================================================
    public void loadMenuResourcesGFX() {

        // ============== Cargar Fuentes =============
        fontOne = FontFactory.createFromAsset(gameManager.getFontManager(),textureManager,1024,1024,gameManager.getAssets(),"gfx/fonts/PetitFour.otf",100,true, Color.WHITE_ABGR_PACKED_INT);
        fontOne.load();

        // ============== Asignar el directorio base =============
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");

        // ============== Asignarndo espacio en memoria ==========
        // -- Inicializar Contenedor --
        menuBitmapTextureAtlasContainer = new ArrayList<BitmapTextureAtlas>();
        // -- Fondo --
        menuBackgroundTextureAtlas = new BitmapTextureAtlas(textureManager, 1280, 800, TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(menuBackgroundTextureAtlas);
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

        backpackMenuButtonTextureAtlas = new BitmapTextureAtlas(textureManager, 450,450,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(backpackMenuButtonTextureAtlas);

        backpackMenuMochilaTextureAtlas =  new BitmapTextureAtlas(textureManager, 450,450,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(backpackMenuMochilaTextureAtlas);

        backpackMenuGems1TextureAtlas = new BitmapTextureAtlas(textureManager, 1300,500,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(backpackMenuGems1TextureAtlas);
        backpackMenuGems2TextureAtlas = new BitmapTextureAtlas(textureManager, 1300,500,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(backpackMenuGems2TextureAtlas);
        backpackMenuGems3TextureAtlas = new BitmapTextureAtlas(textureManager, 1300,500,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(backpackMenuGems3TextureAtlas);
        backpackMenuGems4TextureAtlas = new BitmapTextureAtlas(textureManager, 1300,500,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(backpackMenuGems4TextureAtlas);

        // -- Submenú Settings --
        settingsMenuAudioLevelTextureAtlas = new BitmapTextureAtlas(textureManager,320,154,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        menuBitmapTextureAtlasContainer.add(settingsMenuAudioLevelTextureAtlas);

        settingsMenuAudioButtonsTextureAtlas = new BitmapTextureAtlas(textureManager,256,256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        menuBitmapTextureAtlasContainer.add(settingsMenuAudioButtonsTextureAtlas);
        // -- Botones About --
        aboutMenuButtonTextureAtlas =  new BitmapTextureAtlas(textureManager,450 ,450,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(aboutMenuButtonTextureAtlas);
        // -- ID's --
        aboutMenuIDTextureAtlas = new BitmapTextureAtlas(textureManager, 3000,2000,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        menuBitmapTextureAtlasContainer.add(aboutMenuIDTextureAtlas);
        // --Logo tec--
        aboutMenuLogoTextureAtlas = new BitmapTextureAtlas(textureManager, 550,150,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(aboutMenuLogoTextureAtlas);



        // ============== Cargando las imágenes ==================
        // -- Fondo --
        menuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBackgroundTextureAtlas, gameManager, "fondomenu.jpg", 0, 0);
        // -- Logotipo juego --
        menuLogoBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuLogoTextureAtlas, gameManager, "LogoSmall.png", 0, 0);
        // -- Planeta --
        menuPlanetTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuPlanetTextureAtlas, gameManager, "PlanetaGrande.png", 0, 0);
        // -- Overlay --
        menuOverlayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuOverlayTextureAtlas, gameManager, "menuOverlay.png", 0, 0);


        // -- Botones MenúPrincipal --
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/buttons/"); // Cambiar directorio
        mainMenuButtonTextureRegion_play = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mainMenuButtonTextureAtlas,gameManager,"playButton.png",0,0,2,1);
        mainMenuButtonTextureRegion_backpack = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mainMenuButtonTextureAtlas,gameManager,"backpackButton.png",0,128,2,1);
        mainMenuButtonTextureRegion_settings = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mainMenuButtonTextureAtlas, gameManager, "settingsButton.png", 0, 256, 2, 1);
        mainMenuButtonTextureRegion_about = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mainMenuButtonTextureAtlas, gameManager, "aboutButton.png", 0, 384, 2, 1);
        menuToggleAudioButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(menuToggleAudioButtonTextureAtlas, gameManager, "toggleSoundButton.png", 0, 0, 2, 1);

        // -- Submenú back --

        //botones
        menuSubmenuBackButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuSubmenuBackButtonTextureAtlas, gameManager, "backArrow.png", 0, 0);
        backpackMenuLeftArrowTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuButtonTextureAtlas, gameManager, "arrow_left.png", 0, 0);
        backpackMenuRightArrowTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuButtonTextureAtlas, gameManager, "arrow_right.png", 150, 0);
        //gemas
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/Backpack/");
        backpackMenuGemBlue1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems1TextureAtlas, gameManager, "GemBlue1.png", 0, 0);
        backpackMenuGemBlue2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems1TextureAtlas, gameManager, "GemBlue2.png", 400, 0);
        backpackMenuGemBlue3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems1TextureAtlas, gameManager, "GemBlue3.png", 800, 0);
        backpackMenuGemPink1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems2TextureAtlas, gameManager, "GemPink1.png",0, 0);
        backpackMenuGemPink2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems2TextureAtlas, gameManager, "GemPink2.png", 400, 0);
        backpackMenuGemPink3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems2TextureAtlas, gameManager, "GemPink3.png", 800, 0);
        backpackMenuGemYellow1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems3TextureAtlas, gameManager, "GemYellow1.png", 0, 0);
        backpackMenuGemYellow2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems3TextureAtlas, gameManager, "GemYellow2.png", 400, 0);
        backpackMenuGemYellow3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems3TextureAtlas, gameManager, "GemYellow3.png", 800, 0);
        backpackMenuGemLocked1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems4TextureAtlas, gameManager, "GemLocked1.png", 0, 0);
        backpackMenuGemLocked2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems4TextureAtlas, gameManager, "GemLocked2.png", 400, 0);
        backpackMenuGemLocked3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuGems4TextureAtlas, gameManager, "GemLocked3.png", 800, 0);

         //mochila
        backpackMenuMochilaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(backpackMenuMochilaTextureAtlas, gameManager, "mochila.png", 0, 0);


        // -- Submenú settings --
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/buttons/"); // Cambiar directorio
        settingsMenuAudioLevel_20_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioLevelTextureAtlas,gameManager,"Bar5.png",0,0);
        settingsMenuAudioLevel_40_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioLevelTextureAtlas,gameManager,"Bar4.png",64,0);
        settingsMenuAudioLevel_60_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioLevelTextureAtlas,gameManager,"Bar3.png",128,0);
        settingsMenuAudioLevel_80_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioLevelTextureAtlas,gameManager,"Bar2.png",192,0);
        settingsMenuAudioLevel_100_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioLevelTextureAtlas,gameManager,"Bar1.png",256,0);

        settingsMenuDecreaseMusicButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioButtonsTextureAtlas,gameManager,"MusicMute.png",0,0);
        settingsMenuIncreaseMusicButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioButtonsTextureAtlas,gameManager,"Music.png",128,0);
        settingsMenuDecreaseSoundButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioButtonsTextureAtlas,gameManager,"Sound2.png",0,128);;
        settingsMenuIncreaseSoundButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(settingsMenuAudioButtonsTextureAtlas,gameManager,"Sound1.png",128,128);


        // -- Regiones Botones About --
        aboutMenuAndyButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuButtonTextureAtlas, gameManager, "aboutButton_Andy.png", 0, 0);
        aboutMenuDanniButtonTexureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuButtonTextureAtlas, gameManager, "aboutButton_Danni.png", 150, 0);
        aboutMenuRebeButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuButtonTextureAtlas, gameManager, "aboutButton_Rebe.png", 300, 0);
        aboutMenuBrianButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuButtonTextureAtlas, gameManager, "aboutButton_Brian.png", 0, 150);
        aboutMenuDiegoButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuButtonTextureAtlas, gameManager, "aboutButton_Diego.png", 150, 150);
        //--Region logo--
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/About/");
        aboutMenuLogoTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuLogoTextureAtlas, gameManager, "logotec.png", 0, 0);

        // -- Regiones ID's about --
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/About/Cards/");
        aboutMenuDiegoIDTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuIDTextureAtlas, gameManager, "ID4.png", 0, 0);
        aboutMenuAndyIDTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuIDTextureAtlas, gameManager, "ID1.png", 1000, 0);
        aboutMenuDanniIDTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuIDTextureAtlas, gameManager, "ID5.png", 2000, 0);
        aboutMenuRebeIDTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuIDTextureAtlas, gameManager, "ID2.png", 0, 1000);
        aboutMenuBrianIDTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(aboutMenuIDTextureAtlas, gameManager, "ID3.png", 1000, 1000);



        // ============== Cargar el atlas de imágenes ============
         for(BitmapTextureAtlas atlas : menuBitmapTextureAtlasContainer){
             atlas.load();
         }

    }

    // ============== Cargar Recursos Musicales ==================
    // ===========================================================
    public void loadMenuResourcesMFX(){
        // ============== Intentar cargar desde archivo ==========
        try {menuMusic = MusicFactory.createMusicFromAsset(musicManager,gameManager,"menu.ogg");}
        catch (IOException e) {e.printStackTrace();}

        // ============== Opciones de música =====================
        menuMusic.setVolume(musicManager.getMasterVolume());
        menuMusic.setLooping(true);
    }

    // ============== Cargar Recursos de Sonido ==================
    // ===========================================================
    public void loadMenuResourcesSFX(){
    }

    // ============== Liberar Recursos ===========================
    // ===========================================================
    public void unloadMenuResources(){
        for(BitmapTextureAtlas atlas : menuBitmapTextureAtlasContainer){
            atlas.unload();
            atlas = null;
        }

        fontOne.unload();

        menuMusic.stop();
        menuMusic.release();
        menuMusic = null;
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

    // =============================================================================================
    //                                G E T T E R S  &  S E T T E R S
    // =============================================================================================

    // ============== REGRESA la INSTANCIA de este Adm. =========
    public static ResourceManager getInstance(){return INSTANCE;}
}
