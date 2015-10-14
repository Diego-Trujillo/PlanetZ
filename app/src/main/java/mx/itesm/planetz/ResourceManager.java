package mx.itesm.planetz;

import android.util.Log;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicLibrary;
import org.andengine.audio.music.MusicManager;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundManager;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.MusicOptions;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.util.GLState;

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

    // -------------- Mochila cosas ------------------------------
    private BitmapTextureAtlas menuSubmenuMochilaTextureAtlas;
    public ITextureRegion menuSubmenuMochilaTextureRegion;
    // -------------- Gemas --------------------------------------
    private BitmapTextureAtlas menuSubmenuGema1TextureAtlas;
    public ITextureRegion menuSubmenuGema1TextureRegion;
    private BitmapTextureAtlas menuSubmenuGema2TextureAtlas;
    public ITextureRegion menuSubmenuGema2TextureRegion;
    private BitmapTextureAtlas menuSubmenuGema3TextureAtlas;
    public ITextureRegion menuSubmenuGema3TextureRegion;
    private BitmapTextureAtlas menuSubmenuGema4TextureAtlas;
    public ITextureRegion menuSubmenuGema4TextureRegion;

    // ============== SUBMENU SETTINGS ===========================
    // ============== SUBMENU ABOUT ==============================
    // -------------- Bolitas con Nombres ------------------------
    private BitmapTextureAtlas menuSubmenuDrawTextureAtlas;
    public ITextureRegion menuSubmenuDraw1TextureRegion;
    public ITextureRegion menuSubmenuDraw2TextureRegion;
    public ITextureRegion menuSubmenuDraw3TextureRegion;
    public ITextureRegion menuSubmenuDraw4TextureRegion;
    public ITextureRegion menuSubmenuDraw5TextureRegion;
    // -------------- ID's y descripción -------------------------
    private BitmapTextureAtlas menuSubmenuIDTextureAtlas;
    public ITextureRegion menuSubmenuID1TextureRegion;
    public ITextureRegion menuSubmenuID2TextureRegion;
    public ITextureRegion menuSubmenuID3TextureRegion;
    public ITextureRegion menuSubmenuID4TextureRegion;
    public ITextureRegion menuSubmenuID5TextureRegion;
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
        /*
        menuSubmenuMochilaTextureAtlas = new BitmapTextureAtlas(textureManager, 512,512,TextureOptions.BILINEAR);
        menuSubmenuGema1TextureAtlas = new BitmapTextureAtlas(textureManager, 512,512,TextureOptions.BILINEAR);
        menuSubmenuGema2TextureAtlas = new BitmapTextureAtlas(textureManager, 512,512,TextureOptions.BILINEAR);
        menuSubmenuGema3TextureAtlas = new BitmapTextureAtlas(textureManager, 512,512,TextureOptions.BILINEAR);
        menuSubmenuGema4TextureAtlas = new BitmapTextureAtlas(textureManager, 512,512,TextureOptions.BILINEAR);
    */
        // -- Botones About --
        menuSubmenuDrawTextureAtlas =  new BitmapTextureAtlas(textureManager, 384,256,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(menuSubmenuDrawTextureAtlas);
        // -- ID's --
        menuSubmenuIDTextureAtlas = new BitmapTextureAtlas(textureManager, 1024,1024,TextureOptions.BILINEAR);
        menuBitmapTextureAtlasContainer.add(menuSubmenuIDTextureAtlas);


        // ============== Cargando las imágenes ==================
        // -- Fondo --
        menuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBackgroundTextureAtlas, gameManager, "fondomenu.jpg", 0, 0);
        // -- Logotipo juego --
        menuLogoBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuLogoTextureAtlas, gameManager, "LogoSmall.png", 0, 0);
        // -- Planeta --
        menuPlanetTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuPlanetTextureAtlas, gameManager, "PlanetaGrande.png", 0, 0);
        // -- Overlay --
        menuOverlayTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuOverlayTextureAtlas, gameManager, "menuOverlay.png", 0, 0);
        //--Mochila--
        /*
        menuSubmenuMochilaTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuPlanetTextureAtlas, gameManager, "Mochila.png", 0, 0);
        menuSubmenuGema1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuPlanetTextureAtlas, gameManager, "gemabloqueada.png", 0, 0);
        menuSubmenuGema2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuPlanetTextureAtlas, gameManager, "gemaamarilla.png", 0, 0);
        menuSubmenuGema3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuPlanetTextureAtlas, gameManager, "gemaazul.png", 0, 0);
        menuSubmenuGema4TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuPlanetTextureAtlas, gameManager, "gemarosa.png", 0, 0);
        */
        //--About--

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/About/");
        menuSubmenuDraw1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuSubmenuDrawTextureAtlas, gameManager, "Draw1.png", 0, 0);
        menuSubmenuDraw2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuSubmenuDrawTextureAtlas, gameManager, "Draw2.png", 0, 0);
        menuSubmenuDraw3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuSubmenuDrawTextureAtlas, gameManager, "Draw3.png", 0, 0);
        menuSubmenuDraw4TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuSubmenuDrawTextureAtlas, gameManager, "Draw4.png", 0, 0);
        menuSubmenuDraw5TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuSubmenuDrawTextureAtlas, gameManager, "Draw5.png", 0, 0);

        menuSubmenuID1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuSubmenuIDTextureAtlas, gameManager, "ID1.png", 0, 0);
        menuSubmenuID2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuSubmenuIDTextureAtlas, gameManager, "ID2.png", 0, 0);
        menuSubmenuID3TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuSubmenuIDTextureAtlas, gameManager, "ID3.png", 0, 0);
        menuSubmenuID4TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuSubmenuIDTextureAtlas, gameManager, "ID4.png", 0, 0);
        menuSubmenuID5TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuSubmenuIDTextureAtlas, gameManager, "ID5.png", 0, 0);



        // -- Botones MenúPrincipal --
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/buttons/"); // Cambiar directorio
        mainMenuButtonTextureRegion_play = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mainMenuButtonTextureAtlas,gameManager,"playButton.png",0,0,2,1);
        mainMenuButtonTextureRegion_backpack = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mainMenuButtonTextureAtlas,gameManager,"backpackButton.png",0,128,2,1);
        mainMenuButtonTextureRegion_settings = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mainMenuButtonTextureAtlas,gameManager,"settingsButton.png",0,256,2,1);
        mainMenuButtonTextureRegion_about = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mainMenuButtonTextureAtlas,gameManager,"aboutButton.png",0,384,2,1);
        menuToggleAudioButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(menuToggleAudioButtonTextureAtlas,gameManager,"toggleSoundButton.png",0,0,2,1);

        // -- Submenú back --
        menuSubmenuBackButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuSubmenuBackButtonTextureAtlas,gameManager,"backArrow.png",0,0);
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
