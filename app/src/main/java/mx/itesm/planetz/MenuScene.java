package mx.itesm.planetz;

import android.content.Context;
import android.content.SharedPreferences;
import android.transition.Fade;
import android.widget.Switch;
import android.widget.Toast;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.FadeInModifier;
import org.andengine.entity.modifier.FadeOutModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
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
import org.andengine.input.touch.detector.ScrollDetector;
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
public class MenuScene extends BaseScene{

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
    private Sprite planetSprite;

    // =============== Logotipo ==================================
    private Sprite logoSprite;

    // =============== Overlays para los submenús ===============
    private Sprite menuOverlaySprite;

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
    // =============== Opciones de Botones =======================
    IMenuItem mainMenuToggleAudioButton;

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


    // ===========================================================
    //                      SUBMENÚ BACKPACK
    // ===========================================================
    // =============== El Contenedor =============================
    private org.andengine.entity.scene.menu.MenuScene backpackMenuScene;

    // =============== Cosas que necesitan sanitizarse ===========
    Text level1Text;
    Text level2Text;
    Text level3Text;



    Sprite gem1;Sprite gem2;Sprite gem3;Sprite gem4;Sprite gem5;Sprite gem6;
    Sprite gem7;Sprite gem8;Sprite gem9;Sprite gemL1;Sprite gemL2;Sprite gemL3;
    Sprite gemL4;Sprite gemL5;Sprite gemL6;Sprite gemL7;Sprite gemL8;Sprite gemL9;

    //entidades
    Entity level1;
    Entity level2;
    Entity level3;
    //niveles contador
    int countPosition;



    // ===========================================================
    //                      SUBMENÚ SETTINGS
    // ===========================================================

    // =============== El Contenedor =============================
    private org.andengine.entity.scene.menu.MenuScene settingsMenuScene;

    // =============== Opciones de Botones =======================
    private static final int MUSIC_DECREASE = 1;
    private static final int MUSIC_INCREASE = 2;
    private static final int SOUND_DECREASE = 3;
    private static final int SOUND_INCREASE = 4;

    // =============== Contenedores de las barras de audio ========
    ArrayList<Sprite> musicBarsArrayList;
    ArrayList<Sprite> soundBarsArrayList;

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

    // =============== Sprites de ID's =================
    private Sprite aboutMenuAndyIDSprite;
    private Sprite aboutMenuRebeIDSprite;
    private Sprite aboutMenuBrianIDSprite;
    private Sprite aboutMenuDiegoIDSprite;
    private Sprite aboutMenuDanniIDSprite;


    // =============================================================================================
    //                                    C O N S T R U C T O R
    // =============================================================================================

    public MenuScene(){
        super();
        sceneType = SceneType.MENU;
        sessionManager.currentLevel= 2;

    }

    // =============================================================================================
    //                                       M É T O D O S
    // =============================================================================================

    // ===========================================================
    //                 Cargar recursos gráficos
    // ===========================================================
    @Override
    public void loadGFX() {
        // =============== Llamar al administrador de recursos ===
        resourceManager.loadMenuResourcesGFX();

        // =============== Fondo de estrellas ====================
        // -- Crea una entidad de fondo móvil
        movingParallaxBackground = new AutoParallaxBackground(0f,0,0,1);
        // -- Crea el sprite del fondo
        backgroundSprite = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2,resourceManager.menuBackgroundTextureRegion);
        // -- Crea una entidad móvil que definirá movimiento del fondo
        movingParallaxEntity = new ParallaxBackground.ParallaxEntity(15f,backgroundSprite);
        // -- Asigna la entidad móvil para que siga y de movimiento al fondo
        movingParallaxBackground.attachParallaxEntity(movingParallaxEntity);

        // =============== Planeta giratorio ====================
        // -- Carga el sprite
        planetSprite = resourceManager.loadSprite(0,0,resourceManager.menuPlanetTextureRegion);


        // =============== Logotipo del juego ===================
        // -- Carga el sprite
        logoSprite = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2+100,resourceManager.menuLogoBackgroundTextureRegion);

        // =============== Overlay para los submenús ============
        // -- Carga el sprite
        menuOverlaySprite = resourceManager.loadSprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2,resourceManager.menuOverlayTextureRegion);

        // ============== Llama a la creación de los submenús ===
        addMainMenu();
        addPlayMenu();
        addBackpack();
        addSettings();
        addAbout();

    }

    // ===========================================================
    //                      Cargar Música
    // ===========================================================

    @Override
    public void loadMFX() {
        // -- Llama al administrador de Recursos a cargar la música del nivel
        resourceManager.loadMenuResourcesMFX();
        // -- Pone el volúmen de la canción según la configuración Guardada
        resourceManager.menuMusic.setVolume(sessionManager.musicVolume);
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
        // -- Adjuntamos el Sprite del planeta
        attachChild(planetSprite);
        // -- Ajustamos su posición a la parte inferior
        planetSprite.setPosition(gameManager.CAMERA_WIDTH / 2, -gameManager.CAMERA_HEIGHT + 200);
        // -- Registramos un Modifier perpetuo que haga que el planeta gire
        planetSprite.registerEntityModifier(new LoopEntityModifier(new RotationModifier(200, 0, -360)));

        // =============== Adjuntar y esconder el overlay ========
        attachChild(menuOverlaySprite);

        // =============== Agregar la sub-escena del menú ========
        setChildScene(mainMenuScene);

        // =============== Reproducir música de fondo ============
        resourceManager.menuMusic.play();
    }

    // ===========================================================
    //                Crear el menú principal
    // ===========================================================
    public void addMainMenu(){
        // ===============  Inicializando la subescena ===========
        // -- Inicializar la instancia de escena
        mainMenuScene = new org.andengine.entity.scene.menu.MenuScene(camera);
        // -- Moificar la posición de la escena
        mainMenuScene.setPosition(0,0);

        // =============== Creando los botones ===================
        // -- Botón Play
        IMenuItem mainMenuPlayButton = new TiledSpriteMenuItem(MAIN_PLAY,resourceManager.mainMenuButtonTextureRegion_play,vertexBufferObjectManager);
        // -- Botón Backpack
        IMenuItem mainMenuBackpackButton = new TiledSpriteMenuItem(MAIN_BACKPACK, resourceManager.mainMenuButtonTextureRegion_backpack, vertexBufferObjectManager);
        // -- Botón Settings
        IMenuItem mainMenuSettingsButton = new TiledSpriteMenuItem(MAIN_SETTINGS, resourceManager.mainMenuButtonTextureRegion_settings, vertexBufferObjectManager);
        // -- Botón About
        IMenuItem mainMenuAboutButton = new TiledSpriteMenuItem(MAIN_ABOUT,resourceManager.mainMenuButtonTextureRegion_about,vertexBufferObjectManager);

        // -- Botón para habilitar/deshabilitar el audio
        mainMenuToggleAudioButton = new ToggleSpriteMenuItem(MAIN_TOGGLE_AUDIO,resourceManager.menuToggleAudioButtonTextureRegion,vertexBufferObjectManager);
        // -- Cambiamos la imagen de el botoón de hab/deshab audio según la configuración guardada en Session Manager
        ((ToggleSpriteMenuItem) mainMenuToggleAudioButton).setCurrentTileIndex((sessionManager.musicVolume > 0.025 || sessionManager.soundVolume > 0.025) ? 0 : 1);

        // =============== Agregando los botones =================
        mainMenuScene.addMenuItem(mainMenuPlayButton);
        mainMenuScene.addMenuItem(mainMenuBackpackButton);
        mainMenuScene.addMenuItem(mainMenuSettingsButton);
        mainMenuScene.addMenuItem(mainMenuAboutButton);
        mainMenuScene.addMenuItem(mainMenuToggleAudioButton);

        // =============== Configurando las animaciones =========
        // -- Construimos las animaciones del menú
        mainMenuScene.buildAnimations();
        // -- Ocultamos el overlay de los Submenús
        menuOverlaySprite.setVisible(false);
        // -- No registramos fondo
        mainMenuScene.setBackgroundEnabled(false);

        // =============== Adjuntamos el logotipo del juego =====
        mainMenuScene.attachChild(logoSprite);

        // =============== Ubicando los botones =================
        mainMenuPlayButton.setPosition(179,100);
        mainMenuBackpackButton.setPosition(486, 100);
        mainMenuSettingsButton.setPosition(793, 100);
        mainMenuAboutButton.setPosition(1100, 100);
        mainMenuToggleAudioButton.setPosition(GameManager.CAMERA_WIDTH - 64, GameManager.CAMERA_HEIGHT - 64);

        // ========= Establecer Acción según botoón presionado ===
        mainMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()) {
                    case MAIN_PLAY:
                        // -- Cambiar a al submenú Play
                        setChildScene(playMenuScene);
                        // -- Poner el Overlay
                        menuOverlaySprite.setVisible(true);
                        break;
                    case MAIN_BACKPACK:
                        // -- Cambiar al submenú Backpack
                        setChildScene(backpackMenuScene);
                        // -- Poner el Overlay
                        menuOverlaySprite.setVisible(true);
                        break;
                    case MAIN_SETTINGS:
                        // -- Cambiar al submenú Settings
                        setChildScene(settingsMenuScene);
                        // -- Actualizar la visibilidad de las barras de Audio
                        updateAudioVisibility();
                        // -- Poner el Overlay
                        menuOverlaySprite.setVisible(true);
                        break;
                    case MAIN_ABOUT:
                        // -- Cambiar al submenú About
                        setChildScene(aboutMenuScene);
                        // -- Poner el Overlay
                        menuOverlaySprite.setVisible(true);
                        gameManager.toastOnUiThread("Tap the faces to see more!",Toast.LENGTH_LONG);
                        break;
                    case MAIN_TOGGLE_AUDIO:
                        // == Cuando cualquiera de los dos canales de audio está habilitado ==
                        if(sessionManager.musicVolume > 0f || sessionManager.soundVolume > 0f){
                            // -- Enmudecer los canales de AFX
                            sessionManager.musicVolume = 0f;
                            sessionManager.soundVolume = 0f;
                        }
                        // == Cuando ningún canal de audio está habilitado ====================
                        else{
                            // --Desenmudecer los canales de AFX
                            sessionManager.musicVolume = 0.6f;
                            sessionManager.soundVolume = 0.6f;
                        }

                        // -- Cambiar el ícono de audio según elección del jugador
                        ((ToggleSpriteMenuItem) mainMenuToggleAudioButton).setCurrentTileIndex((sessionManager.musicVolume > 0.025 || sessionManager.soundVolume > 0.025) ? 0 : 1);

                        // -- Cambiar el volumen en las opciones del motor
                        resourceManager.musicManager.setMasterVolume(sessionManager.musicVolume);
                        resourceManager.soundManager.setMasterVolume(sessionManager.soundVolume);

                        // -- Cambiar el volumen de la música según el manager
                        resourceManager.menuMusic.setVolume(resourceManager.musicManager.getMasterVolume());

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
        // -- Inicializar la instancia de la escena
        playMenuScene = new org.andengine.entity.scene.menu.MenuScene(camera);
        // -- Ubicar el menú
        playMenuScene.setPosition(0, 0);


        // =============== Creando los botones ===================
        // -- Botón para regresar al menú principal
        IMenuItem backButton = new ScaleMenuItemDecorator(new SpriteMenuItem(SUBMENU_BACK,resourceManager.menuSubmenuBackButtonTextureRegion,vertexBufferObjectManager),0.8f,1f);
        // -- Botón para abrir las opciones de Adventure
        IMenuItem adventureMode = new ScaleMenuItemDecorator(new SpriteMenuItem(PLAY_ADVENTURE_MODE,resourceManager.loadImage("gfx/icon_play.png") ,vertexBufferObjectManager),1.2f,1f); /*******/

        Sprite infiniteModeSprite = resourceManager.loadSprite(GameManager.CAMERA_WIDTH/2 + 250,GameManager.CAMERA_HEIGHT/2,resourceManager.loadImage("gfx/menu/infinityModebutton(locked).png"));


        // =============== Agregando los botones =================
        playMenuScene.addMenuItem(backButton);
        playMenuScene.addMenuItem(adventureMode);

        // =============== Configurando las animaciones =========
        // -- Construimos las animaciones
        playMenuScene.buildAnimations();
        // -- No habilitamos un fondo
        playMenuScene.setBackgroundEnabled(false);

        // =============== Agregando el Texto "PLAY" =============
        playMenuScene.attachChild(new Text(350, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "PLAY", vertexBufferObjectManager));
        playMenuScene.attachChild(infiniteModeSprite);


        // =============== Ubicando los botones =================
        backButton.setPosition(150,GameManager.CAMERA_HEIGHT - 125 );
        adventureMode.setPosition(GameManager.CAMERA_WIDTH/2 - 250, GameManager.CAMERA_HEIGHT/2);

        playMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()){
                    case SUBMENU_BACK:
                        // -- Regresamos al menú principal
                        returnToMenu();
                        break;
                    case PLAY_ADVENTURE_MODE:
                        // -- Creamos la escena del primer nivel
                        sceneManager.createScene(SceneType.STORY);
                        // -- Corremos la escena del primer nivel
                        sceneManager.setScene(SceneType.STORY);
                        // -- Liberamos la escena actual
                        sceneManager.destroyScene(SceneType.MENU);
                }
                return true;
            }
        });
    }


    // ===========================================================
    //                Crear el menú Backpack
    // ===========================================================
    public void addBackpack() {
        // =============== Inicializando la subEscena ============
        // -- Crear la instancia de la escena Backpack
        backpackMenuScene = new org.andengine.entity.scene.menu.MenuScene(camera);
        // -- Ubicando al submenú
        backpackMenuScene.setPosition(0, 0);

        // =============== Creando los botones e imagenes ===================
        IMenuItem backButton = new ScaleMenuItemDecorator(new SpriteMenuItem(SUBMENU_BACK, resourceManager.menuSubmenuBackButtonTextureRegion, vertexBufferObjectManager), 0.8f, 1f);
        final IMenuItem leftArrow = new ScaleMenuItemDecorator(new SpriteMenuItem(300, resourceManager.backpackMenuLeftArrowTextureRegion, vertexBufferObjectManager), 0.8f, 1f);
        final IMenuItem rightArrow = new ScaleMenuItemDecorator(new SpriteMenuItem(600, resourceManager.backpackMenuRightArrowTextureRegion, vertexBufferObjectManager), 0.8f, 1f);


        //gemas
        gem1 = new Sprite(GameManager.CAMERA_WIDTH/2 -300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemBlue1TextureRegion, vertexBufferObjectManager);
        gem2 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemBlue2TextureRegion, vertexBufferObjectManager);
        gem3 = new Sprite(GameManager.CAMERA_WIDTH/2 +300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemBlue3TextureRegion, vertexBufferObjectManager);
        gem4 = new Sprite(GameManager.CAMERA_WIDTH/2 -300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemPink1TextureRegion, vertexBufferObjectManager);
        gem5 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemPink2TextureRegion, vertexBufferObjectManager);
        gem6 = new Sprite(GameManager.CAMERA_WIDTH/2 +300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemPink3TextureRegion, vertexBufferObjectManager);
        gem7 = new Sprite(GameManager.CAMERA_WIDTH/2 -300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemYellow1TextureRegion, vertexBufferObjectManager);
        gem8 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemYellow2TextureRegion, vertexBufferObjectManager);
        gem9 = new Sprite(GameManager.CAMERA_WIDTH/2 +300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemYellow3TextureRegion, vertexBufferObjectManager);

        gemL1 = new Sprite(GameManager.CAMERA_WIDTH/2 -300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemLocked1TextureRegion, vertexBufferObjectManager);
        gemL2 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemLocked2TextureRegion, vertexBufferObjectManager);
        gemL3 = new Sprite(GameManager.CAMERA_WIDTH/2 +300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemLocked3TextureRegion, vertexBufferObjectManager);
        gemL4 = new Sprite(GameManager.CAMERA_WIDTH/2 -300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemLocked1TextureRegion, vertexBufferObjectManager);
        gemL5 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemLocked2TextureRegion, vertexBufferObjectManager);
        gemL6 = new Sprite(GameManager.CAMERA_WIDTH/2 +300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemLocked3TextureRegion, vertexBufferObjectManager);
        gemL7 = new Sprite(GameManager.CAMERA_WIDTH/2 -300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemLocked1TextureRegion, vertexBufferObjectManager);
        gemL8 = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemLocked2TextureRegion, vertexBufferObjectManager);
        gemL9 = new Sprite(GameManager.CAMERA_WIDTH/2 +300,GameManager.CAMERA_HEIGHT/2 -50,resourceManager.backpackMenuGemLocked3TextureRegion, vertexBufferObjectManager);
        level1 = new Entity();
        level2 = new Entity();
        level3 = new Entity();

        //T E M P O R A L Esto y el metodo deben ser implementados en la clase del nivel para registrar su bloqueo


        unlonkGem(1, AppContext.getAppContext(),true);
        unlonkGem(2,AppContext.getAppContext(),false);
        unlonkGem(3,AppContext.getAppContext(),true);
        unlonkGem(4,AppContext.getAppContext(),true);
        unlonkGem(5,AppContext.getAppContext(),false);
        unlonkGem(6,AppContext.getAppContext(),false);
        unlonkGem(7,AppContext.getAppContext(),false);
        unlonkGem(8,AppContext.getAppContext(),false);
        unlonkGem(9,AppContext.getAppContext(),false);

        //A G R E G A R - agrega las gemas a entidades vacias dependiendo de su bloqueo

        if(loadGems("gem1",AppContext.getAppContext())==true){
                level1.attachChild(gem1);}
            if(loadGems("gem1",AppContext.getAppContext())==false){
                level1.attachChild(gemL1); }
            if(loadGems("gem2",AppContext.getAppContext())==true){
                level1.attachChild(gem2);}
            if(loadGems("gem2",AppContext.getAppContext())==false){
                level1.attachChild(gemL2);}
            if(loadGems("gem3",AppContext.getAppContext())==true){
                level1.attachChild(gem3);}
            if(loadGems("gem3",AppContext.getAppContext())==false){
                level1.attachChild(gemL3);}
            if(loadGems("gem4",AppContext.getAppContext())==true){
                level2.attachChild(gem4);}
            if(loadGems("gem4",AppContext.getAppContext())==false){
                level2.attachChild(gemL4); }
            if(loadGems("gem5",AppContext.getAppContext())==true){
                level2.attachChild(gem5);}
            if(loadGems("gem5",AppContext.getAppContext())==false){
                level2.attachChild(gemL5);}
            if(loadGems("gem6",AppContext.getAppContext())==true){
                level2.attachChild(gem6);}
            if(loadGems("gem6",AppContext.getAppContext())==false){
                level2.attachChild(gemL6);}
            if(loadGems("gem7",AppContext.getAppContext())==true){
                level3.attachChild(gem7);}
            if(loadGems("gem7",AppContext.getAppContext())==false){
                level3.attachChild(gemL7); }
            if(loadGems("gem8",AppContext.getAppContext())==true){
                level3.attachChild(gem8);}
            if(loadGems("gem8",AppContext.getAppContext())==false){
                level3.attachChild(gemL8);}
            if(loadGems("gem9",AppContext.getAppContext())==true){
                level3.attachChild(gem9);}
            if(loadGems("gem9",AppContext.getAppContext())==false){
                level3.attachChild(gemL9);}

        // =============== Agregando los botones =================
        backpackMenuScene.addMenuItem(backButton);
        backpackMenuScene.addMenuItem(leftArrow);
        backpackMenuScene.addMenuItem(rightArrow);
        backpackMenuScene.attachChild(level1);
        backpackMenuScene.attachChild(level2);
        backpackMenuScene.attachChild(level3);
        level1.setVisible(true);
        level2.setVisible(false);
        level3.setVisible(false);

        // =============== Configurando las animaciones =========
        backpackMenuScene.buildAnimations();
        backpackMenuScene.setBackgroundEnabled(false);

        // =============== Agregando el Texto "BACKPACK" =========
        backpackMenuScene.attachChild(new Text(425, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "BACKPACK", vertexBufferObjectManager));
        backpackMenuScene.attachChild(level1Text = new Text(GameManager.CAMERA_WIDTH / 2 + 400, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "Level 1", vertexBufferObjectManager));
        backpackMenuScene.attachChild(level2Text = new Text(GameManager.CAMERA_WIDTH / 2 + 400, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "Level 2", vertexBufferObjectManager));
        backpackMenuScene.attachChild(level3Text = new Text(GameManager.CAMERA_WIDTH / 2 + 400, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "Level 3", vertexBufferObjectManager));
        level1Text.setVisible(true);
        level2Text.setVisible(false);
        level3Text.setVisible(false);

        // =============== Ubicando los botones =================
        backButton.setPosition(150, GameManager.CAMERA_HEIGHT - 125);
        rightArrow.setPosition(GameManager.CAMERA_WIDTH - 100, GameManager.CAMERA_HEIGHT / 2 - 50);
        leftArrow.setPosition(100, GameManager.CAMERA_HEIGHT/2 -50);

        countPosition= 1;
        //quitar y poner las flechas
        if(countPosition==1)
            leftArrow.setVisible(false);


        backpackMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()) {

                    case 600:
                        countPosition += 1;
                        if (countPosition == 1) {
                            leftArrow.setVisible(false);
                        }
                        if (countPosition == 2) {
                            leftArrow.setVisible(true);
                            rightArrow.setVisible(true);
                        }
                        if (countPosition == 3) {
                            rightArrow.setVisible(false);
                        }
                        System.out.println("DERECHA " + countPosition);
                        attachGems(countPosition, 0);
                        break;
                    case 300:
                        countPosition -= 1;
                        if (countPosition == 1) {
                            leftArrow.setVisible(false);
                        }
                        if (countPosition == 2) {
                            leftArrow.setVisible(true);
                            rightArrow.setVisible(true);
                        }
                        if (countPosition == 3) {
                            rightArrow.setVisible(false);
                        }
                        System.out.println("IZQUIERDA " + countPosition);
                        attachGems(countPosition, 1);
                        break;
                    case SUBMENU_BACK:
                        returnToMenu();
                        break;
                }
                return true;
            }
        });

    }


    public void attachGems(int level,int lado){
        if(level==1) {
            level1Text.registerEntityModifier(new FadeInModifier(1.5f));
            if(lado==0){
                level1.registerEntityModifier(new MoveXModifier(1.5f,GameManager.CAMERA_WIDTH,0));}
            else{
                level1.registerEntityModifier(new MoveXModifier(1.5f,-GameManager.CAMERA_WIDTH,0));}
            level1Text.setVisible(true);
            level2Text.setVisible(false);
            level1.setVisible(true) ;
            level2.setVisible(false);}
        if(level==2){
            level2Text.registerEntityModifier(new FadeInModifier(1.5f));
            if(lado==0){
                level2.registerEntityModifier(new MoveXModifier(1.5f,GameManager.CAMERA_WIDTH,0));}
            else{
                level2.registerEntityModifier(new MoveXModifier(1.5f,-GameManager.CAMERA_WIDTH,0));}
            level1Text.setVisible(false);
            level2Text.setVisible(true);
            level3Text.setVisible(false);
            level1.setVisible(false);
            level3.setVisible(false);
            level2.setVisible(true);}
        if(level==3){
            level3Text.registerEntityModifier(new FadeInModifier(1.5f));
            if(lado==0){
                level3.registerEntityModifier(new MoveXModifier(1.5f,GameManager.CAMERA_WIDTH,0));}
            else{
                level3.registerEntityModifier(new MoveXModifier(1.5f,-GameManager.CAMERA_WIDTH,0));}
            level2Text.setVisible(false);
            level3Text.setVisible(true);
            level2.setVisible(false);
            level3.setVisible(true);
            }

    }
    //ESTO VA EN LA CLASE DE CADA NIVEL, SE PONE AQUI POR CUESTIONES DE PRUEBA
    public static void unlonkGem(int i, Context context, boolean unlocked){
        /*SharedPreferences sharedPreferences = context.getSharedPreferences("UNLOCKED_GEMS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("gem" + i, unlocked);
        editor.commit(); */
    }
    //ESTO ES PARA ACCEDER AL VALOR QUE YA SE REGISTRO EN EL JUEGO
    public static boolean loadGems(String gem,Context context){
        /*SharedPreferences sharedPreferences = context.getSharedPreferences("UNLOCKED_GEMS", Context.MODE_PRIVATE);
        boolean unlock = sharedPreferences.getBoolean(gem,true);
        return unlock;*/
        return true;
    }
    // ===========================================================
    //                Crear el menú Settings
    // ===========================================================
    public void addSettings() {
        // =============== Inicializando la subEscena ============
        // -- Inicializar la instancia de submenú
        settingsMenuScene = new org.andengine.entity.scene.menu.MenuScene(camera);
        // -- Ubicar la escena
        settingsMenuScene.setPosition(0, 0);

        // =============== Creando los botones ===================
        // -- Botón para regresar al menú principal
        IMenuItem backButton = new ScaleMenuItemDecorator(new SpriteMenuItem(SUBMENU_BACK, resourceManager.menuSubmenuBackButtonTextureRegion, vertexBufferObjectManager), 0.8f, 1f);
        // -- Botón para decrementar el volúmen de la música
        IMenuItem musicDecrease = new ScaleMenuItemDecorator(new SpriteMenuItem(MUSIC_DECREASE, resourceManager.settingsMenuDecreaseMusicButtonTextureRegion, vertexBufferObjectManager), 0.8f, 1.0f);
        // -- Botón para incrementar el volúmen de la música
        IMenuItem musicIncrease = new ScaleMenuItemDecorator(new SpriteMenuItem(MUSIC_INCREASE, resourceManager.settingsMenuIncreaseMusicButtonTextureRegion, vertexBufferObjectManager), 0.8f, 1.0f);
        // -- Botón para decrementar el volúmen del sonido
        IMenuItem soundDecrease = new ScaleMenuItemDecorator(new SpriteMenuItem(SOUND_DECREASE, resourceManager.settingsMenuDecreaseSoundButtonTextureRegion, vertexBufferObjectManager), 0.8f, 1.0f);
        // -- Botón para incrementar el volúmen del sonido
        IMenuItem soundIncrease = new ScaleMenuItemDecorator(new SpriteMenuItem(SOUND_INCREASE, resourceManager.settingsMenuIncreaseSoundButtonTextureRegion, vertexBufferObjectManager), 0.8f, 1.0f);


        // =============== Agregando los botones =================
        settingsMenuScene.addMenuItem(backButton);
        settingsMenuScene.addMenuItem(musicDecrease);
        settingsMenuScene.addMenuItem(musicIncrease);
        settingsMenuScene.addMenuItem(soundDecrease);
        settingsMenuScene.addMenuItem(soundIncrease);

        // =============== Configurando las animaciones =========
        // -- Construir las animaciones
        settingsMenuScene.buildAnimations();
        // -- Desahbilitar el fondo
        settingsMenuScene.setBackgroundEnabled(false);

        // =============== Agregando el Texto "BACKPACK" =========
        settingsMenuScene.attachChild(new Text(400, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "SETTINGS", vertexBufferObjectManager));

        // =============== Ubicando los botones =================
        backButton.setPosition(150, GameManager.CAMERA_HEIGHT - 125);
        musicDecrease.setPosition(300, GameManager.CAMERA_HEIGHT / 2 + 25);
        musicIncrease.setPosition(GameManager.CAMERA_WIDTH - 300, GameManager.CAMERA_HEIGHT / 2 + 25);
        soundDecrease.setPosition(300, GameManager.CAMERA_HEIGHT / 2 - 200);
        soundIncrease.setPosition(GameManager.CAMERA_WIDTH - 300, GameManager.CAMERA_HEIGHT / 2 - 200);

        // =============== Creando los Sprites de Settings =================
        // -- Definir un punto de inicio en X
        int barXStart = 458;
        // -- Definir un offset en X
        int barXOffset = 96;

        // -- Crear un arreglo contenedor de las barras para la música
        musicBarsArrayList = new ArrayList<Sprite>();
       // -- Cargar y agregar los sprites de las barras al contenedor
        musicBarsArrayList.add(resourceManager.loadSprite(barXStart + 0 * barXOffset, GameManager.CAMERA_HEIGHT / 2 + 75, resourceManager.settingsMenuAudioLevel_20_TextureRegion));  // -- Barra nivel 20
        musicBarsArrayList.add(resourceManager.loadSprite(barXStart + 1 * barXOffset, GameManager.CAMERA_HEIGHT / 2 + 75, resourceManager.settingsMenuAudioLevel_40_TextureRegion));  // -- Barra nivel 40
        musicBarsArrayList.add(resourceManager.loadSprite(barXStart + 2 * barXOffset, GameManager.CAMERA_HEIGHT / 2 + 75, resourceManager.settingsMenuAudioLevel_60_TextureRegion));  // -- Barra nivel 60
        musicBarsArrayList.add(resourceManager.loadSprite(barXStart + 3 * barXOffset, GameManager.CAMERA_HEIGHT / 2 + 75, resourceManager.settingsMenuAudioLevel_80_TextureRegion));  // -- Barra nivel 80
        musicBarsArrayList.add(resourceManager.loadSprite(barXStart + 4 * barXOffset, GameManager.CAMERA_HEIGHT / 2 + 75, resourceManager.settingsMenuAudioLevel_100_TextureRegion)); // -- Barra nivel 100

        // -- Agregar las barras de música a la escena del submenú
        for (Sprite bar : musicBarsArrayList) {
            settingsMenuScene.attachChild(bar);
        }

        // Crear un arreglo contenedor de las barras para el sonido
        soundBarsArrayList = new ArrayList<Sprite>();
        // -- Cargar y agregar los sprites de las barras al contenedor. Utilizamos la misma Texture Region para los dos conjuntos de barras
        soundBarsArrayList.add(resourceManager.loadSprite(barXStart + 0 * barXOffset, GameManager.CAMERA_HEIGHT / 2 - 150, resourceManager.settingsMenuAudioLevel_20_TextureRegion));  // -- Barra nivel 20
        soundBarsArrayList.add(resourceManager.loadSprite(barXStart + 1 * barXOffset, GameManager.CAMERA_HEIGHT / 2 - 150, resourceManager.settingsMenuAudioLevel_40_TextureRegion));  // -- Barra nivel 40
        soundBarsArrayList.add(resourceManager.loadSprite(barXStart + 2 * barXOffset, GameManager.CAMERA_HEIGHT / 2 - 150, resourceManager.settingsMenuAudioLevel_60_TextureRegion));  // -- Barra nivel 60
        soundBarsArrayList.add(resourceManager.loadSprite(barXStart + 3 * barXOffset, GameManager.CAMERA_HEIGHT / 2 - 150, resourceManager.settingsMenuAudioLevel_80_TextureRegion));  // -- Barra nivel 80
        soundBarsArrayList.add(resourceManager.loadSprite(barXStart + 4 * barXOffset, GameManager.CAMERA_HEIGHT / 2 - 150, resourceManager.settingsMenuAudioLevel_100_TextureRegion)); // -- Barra nivel 100

        // -- Agregar las barras de sonido a la escena del submenú
        for (Sprite bar : soundBarsArrayList) {
            settingsMenuScene.attachChild(bar);
        }

        // -- Llamamos a la función para poner el estado inicial de las barras de sonido según configuración guardada
        updateAudioVisibility();

        // ========= Establecer Acción según botoón presionado ===
        settingsMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()) {
                    case MUSIC_DECREASE:
                        // -- Si el volúmen de la música no es 0 entonces lo decrementamos en 20%
                        if (sessionManager.musicVolume > 0f) {
                            sessionManager.musicVolume -= 0.20f;
                        }
                        break;
                    case MUSIC_INCREASE:
                        // -- Si el volúmen de la música no es 1 entonces lo incrementamos en 20%
                        if (sessionManager.musicVolume < 1.0f) {
                            sessionManager.musicVolume += 0.20f;
                        }
                        break;
                    case SOUND_INCREASE:
                        // -- Si el volúmen del sonido no es 1 entonces lo incrementamos en 20%
                        if (sessionManager.soundVolume < 1.0f) {
                            sessionManager.soundVolume += 0.20f;
                        }
                        break;
                    case SOUND_DECREASE:
                        // -- Si el volúmen del sonido no es 0 entonces lo decrementamos en 20%
                        if (sessionManager.soundVolume > 0f) {
                            sessionManager.soundVolume -= 0.20f;
                        }
                        break;
                    case SUBMENU_BACK:
                        // -- Regresamos al menú principal
                        returnToMenu();
                        // -- Cambiamos el estado del botón de habilitar/deshabilitar audio del menú principal basándonos en lo hecho en esta subescena;
                        // -- ** Si ambos volúmenes son 0, entonces significa que el audio está deshabilitado, de otro modo el audio está deshabilitado
                        ((ToggleSpriteMenuItem) mainMenuToggleAudioButton).setCurrentTileIndex((sessionManager.musicVolume > 0.025 || sessionManager.soundVolume > 0.025) ? 0 : 1);
                        break;
                }

                // -- Cambiamos los volúmenes maestros de la música y el sonido basado en la acción realizada
                resourceManager.musicManager.setMasterVolume(sessionManager.musicVolume);
                resourceManager.soundManager.setMasterVolume(sessionManager.soundVolume);

                // -- Cambiamos el volúmen de la música del menú basado en la acción realizada
                resourceManager.menuMusic.setVolume(resourceManager.musicManager.getMasterVolume());

                // -- Actualizamos el estado de las barras de audio basado en la acción realizada
                updateAudioVisibility();

                // -- Llamamos al Adm. de Sesión para que escriba los cambios realizados
                sessionManager.writeChanges();
                return true;
            }
        });
    }


    void updateAudioVisibility(){
        // -- Para cada nivel de la barra de Audio
        for(int i = 0; i < 5; i++){
            /* Sean las barras {1,2,3,4,5}. Pondremos que la barra n es visible si el volumen
             * de la música o del sonido es mayor o igual a n*0.20 en cada conjunto de barras
             * de lo contrario, ocultamos la barra. */
            musicBarsArrayList.get(i).setVisible(sessionManager.musicVolume >= 0.20f * (i+1));
            soundBarsArrayList.get(i).setVisible(sessionManager.soundVolume >= 0.20f * (i+1));
        }
    }
    // ===========================================================
    //                Crear el menú About
    // ===========================================================
    public void addAbout(){
        // =============== Inicializando la subEscena ============
        // -- Inicializando la instancia de subescena
        aboutMenuScene = new org.andengine.entity.scene.menu.MenuScene(camera);
        // -- Ubicando la escena
        aboutMenuScene.setPosition(0, 0);

        // =============== Creando los botones ===================
        // -- Botón para regresar al menú principal
        IMenuItem backButton = new ScaleMenuItemDecorator(new SpriteMenuItem(SUBMENU_BACK,resourceManager.menuSubmenuBackButtonTextureRegion,vertexBufferObjectManager),0.8f,1f);
        // -- Botón para desplegar el ID de ANDY
        IMenuItem buttonAndy = new ScaleMenuItemDecorator(new SpriteMenuItem(ABOUT_ANDY, resourceManager.aboutMenuAndyButtonTextureRegion,vertexBufferObjectManager),0.8f,1f);
        // -- Botón para desplegar el ID de REBECCA
        IMenuItem buttonRebe = new ScaleMenuItemDecorator(new SpriteMenuItem(ABOUT_REBE,resourceManager.aboutMenuRebeButtonTextureRegion,vertexBufferObjectManager),0.8f,1f);
        // -- Botón para desplegar el ID de BRIAN
        IMenuItem buttonBrian = new ScaleMenuItemDecorator(new SpriteMenuItem(ABOUT_BRIAN,resourceManager.aboutMenuBrianButtonTextureRegion,vertexBufferObjectManager),0.8f,1f);
        // -- Botón para desplegar el ID de DIEGO
        IMenuItem buttonDiego = new ScaleMenuItemDecorator(new SpriteMenuItem(ABOUT_DIEGO,resourceManager.aboutMenuDiegoButtonTextureRegion,vertexBufferObjectManager),0.8f,1f);
        // -- Botón para desplegar el ID de DANIELA
        IMenuItem buttonDanni = new ScaleMenuItemDecorator(new SpriteMenuItem(ABOUT_DANNI,resourceManager.aboutMenuDanniButtonTexureRegion,vertexBufferObjectManager),0.8f,1f);


        // =============== Agregando los botones =================
        aboutMenuScene.addMenuItem(backButton);
        aboutMenuScene.addMenuItem(buttonAndy);
        aboutMenuScene.addMenuItem(buttonDanni);
        aboutMenuScene.addMenuItem(buttonRebe);
        aboutMenuScene.addMenuItem(buttonBrian);
        aboutMenuScene.addMenuItem(buttonDiego);

        // =============== Configurando las animaciones =========
        // -- Construimos las animaciones
        aboutMenuScene.buildAnimations();
        // -- Deshabilitar el fondo
        aboutMenuScene.setBackgroundEnabled(false);

        Sprite itesmLogo = resourceManager.loadSprite(GameManager.CAMERA_WIDTH - 350, 200,resourceManager.loadImage("gfx/menu/About/logotec.png"));
        // =============== Agregando textos "ABOUT" y "versión" ==
        aboutMenuScene.attachChild(new Text(350, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "ABOUT", vertexBufferObjectManager));
        aboutMenuScene.attachChild(new Text(350, 200,resourceManager.fontOne,"Version 0.3",vertexBufferObjectManager));
        aboutMenuScene.attachChild(itesmLogo);

        // =============== Ubicando los botones =================
        backButton.setPosition(150, GameManager.CAMERA_HEIGHT - 125);
        buttonAndy.setPosition(152, GameManager.CAMERA_HEIGHT/2 + 50);
        buttonDanni.setPosition(393, GameManager.CAMERA_HEIGHT/2 +50 );
        buttonRebe.setPosition(649, GameManager.CAMERA_HEIGHT/2 + 50);
        buttonBrian.setPosition(905, GameManager.CAMERA_HEIGHT/2 + 50);
        buttonDiego.setPosition(1141,GameManager.CAMERA_HEIGHT/2 + 50);



        // =============== Crear Sprites y Exlusividad al toque ==

        // -- Creamos el Sprite de ID de ANDY y asignamos una función que define qué hacer cuando se toque el sprite
        aboutMenuAndyIDSprite = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2, resourceManager.aboutMenuAndyIDTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y)
            {
                // Cuando el jugador toque el ID, llamamos la función setAboutID con ÉSTE id.
                if (pSceneTouchEvent.isActionUp()) {setAboutID(aboutMenuAndyIDSprite, false);}
                return true;
            }
        };
        // -- Creamos el Sprite de ID de DANIELA y asignamos una función que define qué hacer cuando se toque el sprite
        aboutMenuDanniIDSprite = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2, resourceManager.aboutMenuDanniIDTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y)
            {
                // Cuando el jugador toque el ID, llamamos la función setAboutID con ÉSTE id.
                if (pSceneTouchEvent.isActionUp()) {setAboutID(aboutMenuDanniIDSprite, false);}
                return true;
            }
        };
        // -- Creamos el Sprite de ID de REBECCA y asignamos una función que define qué hacer cuando se toque el sprite
        aboutMenuRebeIDSprite = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2, resourceManager.aboutMenuRebeIDTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y)
            {
                // Cuando el jugador toque el ID, llamamos la función setAboutID con ÉSTE id.
                if (pSceneTouchEvent.isActionUp()) {setAboutID(aboutMenuRebeIDSprite, false);}
                return true;
            }
        };
        // -- Creamos el Sprite de ID de BRIAN y asignamos una función que define qué hacer cuando se toque el sprite
        aboutMenuBrianIDSprite = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2, resourceManager.aboutMenuBrianIDTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y)
            {
                // Cuando el jugador toque el ID, llamamos la función setAboutID con ÉSTE id.
                if (pSceneTouchEvent.isActionUp()) {setAboutID(aboutMenuBrianIDSprite, false);}
                return true;
            }
        };
        // -- Creamos el Sprite de ID de DIEGO y asignamos una función que define qué hacer cuando se toque el sprite
        aboutMenuDiegoIDSprite = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2, resourceManager.aboutMenuDiegoIDTextureRegion,vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y)
            {
                // Cuando el jugador toque el ID, llamamos la función setAboutID con ÉSTE id.
                if (pSceneTouchEvent.isActionUp()) {setAboutID(aboutMenuDiegoIDSprite, false);}
                return true;
            }
        };


        // ========= Establecer Acción según botoón presionado ===
        aboutMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()) {
                    case ABOUT_ANDY:
                        // -- Llamar a la función para mostrar el ID de ANDY
                        setAboutID(aboutMenuAndyIDSprite, true);
                        break;
                    case ABOUT_DANNI:
                        // -- Llamar a la función para mostrar el ID de DANIELA
                        setAboutID(aboutMenuDanniIDSprite, true);
                        break;
                    case ABOUT_REBE:
                        // -- Llamar a la función para mostrar el ID de REBECCA
                        setAboutID(aboutMenuRebeIDSprite, true);
                        break;
                    case ABOUT_BRIAN:
                        // -- Llamar a la función para mostrar el ID de BRIAN
                        setAboutID(aboutMenuBrianIDSprite, true);
                        break;
                    case ABOUT_DIEGO:
                        // -- Llamar a la función para mostrar el ID de DIEGO
                        setAboutID(aboutMenuDiegoIDSprite, true);
                        break;
                    case SUBMENU_BACK:
                        // -- Regresar al menú principal
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
        // --
        if(enable){
            // -- Si la función recibe true, hacemos que los botones que despliegan las ID's se puedan tocar
            aboutMenuScene.registerTouchArea(aboutMenuScene.getChildByIndex(SUBMENU_BACK));
            aboutMenuScene.registerTouchArea(aboutMenuScene.getChildByIndex(ABOUT_ANDY));
            aboutMenuScene.registerTouchArea(aboutMenuScene.getChildByIndex(ABOUT_DANNI));
            aboutMenuScene.registerTouchArea(aboutMenuScene.getChildByIndex(ABOUT_REBE));
            aboutMenuScene.registerTouchArea(aboutMenuScene.getChildByIndex(ABOUT_BRIAN));
            aboutMenuScene.registerTouchArea(aboutMenuScene.getChildByIndex(ABOUT_DIEGO));
        }
        else{
            // -- Si la función recibe false, hacemos que los botones que despliegan las ID's NO se puedan tocar
            aboutMenuScene.unregisterTouchArea(aboutMenuScene.getChildByIndex(SUBMENU_BACK));
            aboutMenuScene.unregisterTouchArea(aboutMenuScene.getChildByIndex(ABOUT_ANDY));
            aboutMenuScene.unregisterTouchArea(aboutMenuScene.getChildByIndex(ABOUT_DANNI));
            aboutMenuScene.unregisterTouchArea(aboutMenuScene.getChildByIndex(ABOUT_REBE));
            aboutMenuScene.unregisterTouchArea(aboutMenuScene.getChildByIndex(ABOUT_BRIAN));
            aboutMenuScene.unregisterTouchArea(aboutMenuScene.getChildByIndex(ABOUT_DIEGO));
        }
    }

    // ===========================================================
    //   Deshabilita que el jugador toque otra área de la pantalla
    // ===========================================================

    public void setAboutID(Sprite sprite, boolean enable){
        if(enable){
            // -- Deshabilitar los botones para acceder a los demás ID's
            setEnableAboutButtons(false);
            // -- Superponer el ID seleccionadi
            aboutMenuScene.attachChild(sprite);
            // -- Poner un efecto de Fade-In al ID
            sprite.registerEntityModifier(new FadeInModifier(2.0f));
            // -- Registrar al ID como área táctil
            aboutMenuScene.registerTouchArea(sprite);
        }
        else{
            // -- Desregistrar el área táctil del ID
            aboutMenuScene.unregisterTouchArea(sprite);
            // -- Desajuntar el ID de la escena
            aboutMenuScene.detachChild(sprite);
            // -- Re-habilitar los botones para seleccionar otro ID
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
        // -- Remover el Overlay del los submenú
        menuOverlaySprite.setVisible(false);
        // -- Poner como escena actual al menú principal
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
        // -- Llamamos al Adm. de Recursos para liberar las imágnes y la música
        resourceManager.unloadMenuResources();
        // -- Desadjuntamos esta escena
        this.detachSelf();
        // -- Liberamos la escena
        this.dispose();
    }

}
