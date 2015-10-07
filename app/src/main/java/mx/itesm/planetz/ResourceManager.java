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

/**
 * Administra los recursos visuales y auditivos y da métodos útiles
 * Created by Diego on 03/10/2015.
 */
public class ResourceManager {

    //Instancia única
    private static ResourceManager INSTANCE = new ResourceManager();

    //Referencia al Administrador de Juego, el motor, la cámara y el administrador de Texturas
    GameManager gameManager;
    Engine engine;
    Camera camera;
    TextureManager textureManager;

    //Declara los Atlas de Regiones de Bitmaps para las distintas escenas.

    //ESCENA SPLASH
    private BitmapTextureAtlas splashTextureAtlas;
    public ITextureRegion splashTextureRegion_background;
    public ITextureRegion splashTextureRegion_logo;

    //ESCENA MENU
    //Fondo
    private BitmapTextureAtlas menuBackgroundTextureAtlas;
    public ITextureRegion menuBackgroundTextureRegion;

    //Logo
    private BitmapTextureAtlas menuLogoTextureAtlas;
    public ITextureRegion menuLogoBackgroundTextureRegion;
    //Botones
    private BitmapTextureAtlas buttonTextureAtlas;

    public ITiledTextureRegion buttonTextureRegion_play;
    public ITiledTextureRegion buttonTextureRegion_backpack;
    public ITiledTextureRegion buttonTextureRegion_settings;
    public ITiledTextureRegion buttonTextureRegion_about;

    //Administrador de Sonido y música para la escena
    public SoundManager soundManager;
    public MusicManager musicManager;

    //Música
    public Music music;

    //Sonidos
    public Sound sound_1;
    public Sound sound_2;
    public Sound sound_3;

    //Inicializa el Administrador de Recursos
    public static void initialize(GameManager gameManager){
        //Inicializando los
        getInstance().gameManager = gameManager;
        getInstance().engine = gameManager.getEngine();

        //GFX
        getInstance().camera = gameManager.getEngine().getCamera();
        getInstance().textureManager = gameManager.getTextureManager();

        //SFX & MFX
        getInstance().soundManager = gameManager.getEngine().getSoundManager();
        getInstance().musicManager = gameManager.getEngine().getMusicManager();
        MusicFactory.setAssetBasePath("mfx/");

    }
/*
    //Método General
    public void loadGFX(SceneType sceneType){
        switch(sceneType){
            case SPLASH:
                loadSplashResourcesGFX();
                break;

        }

    }

    public void loadMFX(SceneType sceneType){
        switch(sceneType){
            case SPLASH:
                loadSplashResourcesMFX();

        }

    }

    public void loadSFX(SceneType sceneType){
        switch(sceneType){
            case SPLASH:
                loadSplashResourcesSFX();

        }

    }
*/

    //Métodos específicos para la escena

    //SPLASH
    public void loadSplashResourcesGFX(){
        //Asignando el directorio de trabajo a gfx/splash/
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/splash/");

        //Asignando espacio en memoria para las imágenes
        splashTextureAtlas = new BitmapTextureAtlas(textureManager,2048,2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        //Cargando las imágenes en el Atlas
        splashTextureRegion_background = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,gameManager,"background.jpg",0,0);
        splashTextureRegion_logo = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splashTextureAtlas,gameManager,"logo_itesm.png",0,720);
        
        //Cargando a memoria el atlas
        splashTextureAtlas.load();
    }

    public void loadSplashResourcesMFX(){
        try {music = MusicFactory.createMusicFromAsset(musicManager,gameManager,"splash.ogg");}
        catch (IOException e) {e.printStackTrace();}
        music.setLooping(false);
    }

    public void unloadSplashResources(){
        //GFX
        splashTextureAtlas.unload();
        splashTextureAtlas = null;
        splashTextureRegion_logo = null;
        splashTextureRegion_background = null;

        //SFX & MFX
        music.stop();
        music.release();
        music = null;
    }

    //MENU
    public void loadMenuResourcesGFX(){
        //Obteniendo folder para el fondo y el logo
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
        menuBackgroundTextureAtlas = new BitmapTextureAtlas(textureManager,1920,1200,TextureOptions.BILINEAR);
        menuBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBackgroundTextureAtlas,gameManager,"background.png",0,0);

        menuLogoTextureAtlas = new BitmapTextureAtlas(textureManager,450,450,TextureOptions.BILINEAR);
        menuLogoBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuLogoTextureAtlas,gameManager,"gameLogo.png",0,0);

        //Obteniendo folder e inicializando el atlas en memoria para los botones
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/buttons/");
        buttonTextureAtlas = new BitmapTextureAtlas(textureManager,512,512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        //Inicializando los botones
        buttonTextureRegion_play = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(buttonTextureAtlas,gameManager,"playButton.png",0,0,2,1);
        buttonTextureRegion_backpack = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(buttonTextureAtlas,gameManager,"backpackButton.png",0,128,2,1);
        buttonTextureRegion_settings = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(buttonTextureAtlas,gameManager,"settingsButton.png",0,256,2,1);
        buttonTextureRegion_about = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(buttonTextureAtlas,gameManager,"aboutButton.png",0,384,2,1);

        //Cargando a memoria las imágenes
        menuBackgroundTextureAtlas.load();
        menuLogoTextureAtlas.load();
        buttonTextureAtlas.load();

    }

    public void loadMenuResourcesMFX(){


    }

    public void unloadMenuResources(){
        menuLogoTextureAtlas.unload();
        menuLogoTextureAtlas = null;
        menuBackgroundTextureAtlas.unload();
        menuBackgroundTextureAtlas = null;
        buttonTextureAtlas.unload();
        buttonTextureAtlas = null;
    }
    //Métodos auxiliares

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

    public Sprite loadSprite(float px, float py, final ITextureRegion region){

        return new Sprite(px, py, region, gameManager.getVertexBufferObjectManager()) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) { // Optimizando
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
    }


    //GETTERS & SETTERS ELEMENTALES
    public static ResourceManager getInstance(){return INSTANCE;}
}
