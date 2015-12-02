package mx.itesm.planetz;

import android.content.Context;
import android.content.SharedPreferences;
import android.transition.Fade;
import android.widget.Switch;
import android.widget.Toast;

import org.andengine.audio.sound.SoundFactory;
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
import org.andengine.util.adt.color.Color;
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
    // -------------- Sprites ------------------------------------
    private Sprite backgroundSprite;
    private Sprite backgroundStars1Sprite;
    private Sprite backgroundStars2Sprite;
    private Sprite backgroundStars3Sprite;
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
    private Text level1Text;
    private Text level2Text;
    private Text level3Text;

    private Sprite gemSprite[][];

    private static final int LEFT_ARROW = 1;
    private static final int RIGHT_ARROW = 2;

    //entidades
    private Entity backpackGemContainerLevel1;
    private Entity backpackGemContainerLevel2;
    private Entity backpackGemContainerLevel3;

    //niveles contador
    private int countPosition;



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
        // -- Crea una entidad de fondo móvil con color de fondo --
        movingParallaxBackground = new AutoParallaxBackground((22f/255f),(61f/255f),(76f/255f),1f);
        // -- Habilita el color de fondo ..
        movingParallaxBackground.setColorEnabled(true);


        // -- Cargamos a Sprites las tres capas de estrellas --
        backgroundStars1Sprite = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2,resourceManager.menuBackgroundStars1TextureRegion);
        backgroundStars2Sprite = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2,resourceManager.menuBackgroundStars2TextureRegion);
        backgroundStars3Sprite = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2,resourceManager.menuBackgroundStars3TextureRegion);


        // -- Asignamos los sprites como entidades móviles y les damos distintas velocidades de movimiento --
        movingParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(5f,backgroundStars1Sprite));
        movingParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-10f,backgroundStars2Sprite));
        movingParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(20f,backgroundStars3Sprite));

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
        // -- Llama al administrador de Recursos a cargar la música del nivel --
        resourceManager.setMusic("Menu.ogg");
    }

    // ===========================================================
    //                      Cargar Sonidos
    // ===========================================================
    @Override
    public void loadSFX() {
        // -- LLamamos al Adm. de Recursos para cargar los sonidos --
        resourceManager.setSound("Menu/Button1.ogg",1);
        resourceManager.setSound("Menu/Button2.ogg",2);
        resourceManager.setSound("Menu/Button3.ogg",3);
    }

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
        menuOverlaySprite.setVisible(false);

        // =============== Agregar la sub-escena del menú ========
        setChildScene(mainMenuScene);

        // =============== Reproducir música de fondo ============
        resourceManager.backgroundMusic.play();
        resourceManager.updateAudioVolume();

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
                if(pMenuItem.getID() != MAIN_TOGGLE_AUDIO){resourceManager.soundOne.play();}
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
                        gameManager.toastOnUiThread("Play the game to collect more gems!");
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
                        gameManager.toastOnUiThread("Tap on the Planets to learn more!");
                        break;
                    case MAIN_TOGGLE_AUDIO:
                        // == Cuando cualquiera de los dos canales de audio está habilitado ==
                        if(sessionManager.musicVolume > 0.025f || sessionManager.soundVolume > 0.025f){
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


                        // -- Cambiar el volumen de la música según el manager
                        resourceManager.updateAudioVolume();

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
        IMenuItem adventureMode = new ScaleMenuItemDecorator(new SpriteMenuItem(PLAY_ADVENTURE_MODE,resourceManager.loadImage("Graphics/Menu/Play/AdventureModeButton.png") ,vertexBufferObjectManager),1.2f,1f); /*******/
        // -- Botón para abrir las opciones de Adventure
        IMenuItem infinityMode = new ScaleMenuItemDecorator(new SpriteMenuItem(PLAY_INFINITE_MODE,resourceManager.loadImage("Graphics/Menu/Play/InfinityModeButtonLocked.png"),vertexBufferObjectManager),0.8f,1f);


        // =============== Agregando los botones =================
        playMenuScene.addMenuItem(backButton);
        playMenuScene.addMenuItem(adventureMode);
        playMenuScene.addMenuItem(infinityMode);

        // =============== Configurando las animaciones =========
        // -- Construimos las animaciones
        playMenuScene.buildAnimations();
        // -- No habilitamos un fondo
        playMenuScene.setBackgroundEnabled(false);

        // =============== Agregando el Texto "PLAY" =============
        playMenuScene.attachChild(new Text(350, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "PLAY", vertexBufferObjectManager));

        // =============== Ubicando los botones =================
        backButton.setPosition(150, GameManager.CAMERA_HEIGHT - 125);
        adventureMode.setPosition(GameManager.CAMERA_WIDTH/2 - 250, GameManager.CAMERA_HEIGHT/2);
        infinityMode.setPosition(GameManager.CAMERA_WIDTH/2 + 250,GameManager.CAMERA_HEIGHT/2);

        playMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()){
                    case SUBMENU_BACK:
                        // -- Regresamos al menú principal
                        returnToMenu();
                        resourceManager.soundOne.play();
                        break;
                    case PLAY_ADVENTURE_MODE:
                        // -- Liberamos la escena actual
                        sceneManager.destroyScene(SceneType.MENU);
                        // -- Creamos la escena del primer nivel
                        sceneManager.createScene(SceneType.STORY);
                        // -- Corremos la escena del primer nivel
                        sceneManager.setScene(SceneType.STORY);
                        break;
                    case PLAY_INFINITE_MODE:
                        // -- Liberamos la escena actual
                        sceneManager.destroyScene(SceneType.MENU);
                        // -- Creamos la escena del primer nivel
                        sceneManager.createScene(SceneType.ADVENTURE_LEVEL_3);
                        // -- Corremos la escena del primer nivel
                        sceneManager.setScene(SceneType.ADVENTURE_LEVEL_3);
                        break;

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
        // -- Botón para regresar al menú principal --
        IMenuItem backButton = new ScaleMenuItemDecorator(new SpriteMenuItem(SUBMENU_BACK, resourceManager.menuSubmenuBackButtonTextureRegion, vertexBufferObjectManager), 0.8f, 1f);
        // -- Botón para cambiar las gemas mostradas del nivel anterior --
        final IMenuItem leftArrow = new ScaleMenuItemDecorator(new SpriteMenuItem(LEFT_ARROW, resourceManager.backpackMenuLeftArrowTextureRegion, vertexBufferObjectManager), 0.8f, 1f);
        // -- Botón para cambiar las gemas mostradas del nivel siguiente --
        final IMenuItem rightArrow = new ScaleMenuItemDecorator(new SpriteMenuItem(RIGHT_ARROW, resourceManager.backpackMenuRightArrowTextureRegion, vertexBufferObjectManager), 0.8f, 1f);

        // ============== Creando contenedores de tercias de gemas ===========
        backpackGemContainerLevel1= new Entity();
        backpackGemContainerLevel2 = new Entity();
        backpackGemContainerLevel3 = new Entity();

        // -------------- Inicializar el contenedor de Sprites --------------
        gemSprite = new Sprite[4][4];

        // --------------- Gemas Nivel 1 -------------------------------------
        gemSprite[1][1] = new Sprite(GameManager.CAMERA_WIDTH/2 -300,GameManager.CAMERA_HEIGHT/2 -50,(sessionManager.gemsUnlocked[1][1])?resourceManager.backpackMenuGemBlue1TextureRegion:resourceManager.backpackMenuGemLocked1TextureRegion, vertexBufferObjectManager);
        gemSprite[1][2] = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,(sessionManager.gemsUnlocked[1][2])?resourceManager.backpackMenuGemBlue2TextureRegion:resourceManager.backpackMenuGemLocked2TextureRegion, vertexBufferObjectManager);
        gemSprite[1][3] = new Sprite(GameManager.CAMERA_WIDTH/2 +300,GameManager.CAMERA_HEIGHT/2 -50,(sessionManager.gemsUnlocked[1][3])?resourceManager.backpackMenuGemBlue3TextureRegion:resourceManager.backpackMenuGemLocked3TextureRegion, vertexBufferObjectManager);

        // --------------- Gemas Nivel 2 --------------------------------------
        gemSprite[2][1] = new Sprite(GameManager.CAMERA_WIDTH/2 -300,GameManager.CAMERA_HEIGHT/2 -50,(sessionManager.gemsUnlocked[2][1])?resourceManager.backpackMenuGemPink1TextureRegion:resourceManager.backpackMenuGemLocked1TextureRegion, vertexBufferObjectManager);
        gemSprite[2][2] = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,(sessionManager.gemsUnlocked[2][2])?resourceManager.backpackMenuGemPink2TextureRegion:resourceManager.backpackMenuGemLocked2TextureRegion, vertexBufferObjectManager);
        gemSprite[2][3] = new Sprite(GameManager.CAMERA_WIDTH/2 +300,GameManager.CAMERA_HEIGHT/2 -50,(sessionManager.gemsUnlocked[2][3])?resourceManager.backpackMenuGemPink3TextureRegion:resourceManager.backpackMenuGemLocked3TextureRegion, vertexBufferObjectManager);


        // --------------- Gemas Nivel 3 --------------------------------------
        gemSprite[3][1] = new Sprite(GameManager.CAMERA_WIDTH/2 -300,GameManager.CAMERA_HEIGHT/2 -50,(sessionManager.gemsUnlocked[3][1])?resourceManager.backpackMenuGemYellow1TextureRegion:resourceManager.backpackMenuGemLocked1TextureRegion, vertexBufferObjectManager);
        gemSprite[3][2] = new Sprite(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT/2 -50,(sessionManager.gemsUnlocked[3][2])?resourceManager.backpackMenuGemYellow2TextureRegion:resourceManager.backpackMenuGemLocked2TextureRegion, vertexBufferObjectManager);
        gemSprite[3][3] = new Sprite(GameManager.CAMERA_WIDTH/2 +300,GameManager.CAMERA_HEIGHT/2 -50,(sessionManager.gemsUnlocked[3][3])?resourceManager.backpackMenuGemYellow3TextureRegion:resourceManager.backpackMenuGemLocked3TextureRegion, vertexBufferObjectManager);




        // ================ Adjuntarmos las gemas sus resp. contenedores =======
        // -- El contenedor funge como entidad de referencia para que cuando
        //    cambiemos de "nivel", podamos mover las tres gemas de ese
        //    nivel con facilidad (una sóla llamada).
        // ---------------- Gemas Nivel 1 --------------------------------------
        backpackGemContainerLevel1.attachChild(gemSprite[1][1]);
        backpackGemContainerLevel1.attachChild(gemSprite[1][2]);
        backpackGemContainerLevel1.attachChild(gemSprite[1][3]);

        // ---------------- Gemas Nivel 2 ---------------------------------------
        backpackGemContainerLevel2.attachChild(gemSprite[2][1]);
        backpackGemContainerLevel2.attachChild(gemSprite[2][2]);
        backpackGemContainerLevel2.attachChild(gemSprite[2][3]);

        // ----------------- Gemas Nivel 3 ---------------------------------------
        backpackGemContainerLevel3.attachChild(gemSprite[3][1]);
        backpackGemContainerLevel3.attachChild(gemSprite[3][2]);
        backpackGemContainerLevel3.attachChild(gemSprite[3][3]);

        // =============== Agregando los botones =================
        backpackMenuScene.addMenuItem(backButton);
        backpackMenuScene.addMenuItem(leftArrow);
        backpackMenuScene.addMenuItem(rightArrow);

        // =============== Agregando los contenedores ============
        backpackMenuScene.attachChild(backpackGemContainerLevel1);
        backpackMenuScene.attachChild(backpackGemContainerLevel2);
        backpackMenuScene.attachChild(backpackGemContainerLevel3);

        // -- Sólo mostramos las gemas del primer nivel
        backpackGemContainerLevel1.setVisible(true);
        backpackGemContainerLevel2.setVisible(false);
        backpackGemContainerLevel3.setVisible(false);

        // =============== Configurando las animaciones =========
        backpackMenuScene.buildAnimations();
        backpackMenuScene.setBackgroundEnabled(false);

        // =============== Agregando el Textos ==================
        // --------------- Creando los objetos ------------------
        // -- Texto BACKPACK --
        backpackMenuScene.attachChild(new Text(425, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "BACKPACK", vertexBufferObjectManager));
        // -- Texto para cada nivel
        backpackMenuScene.attachChild(level1Text = new Text(GameManager.CAMERA_WIDTH / 2 + 400, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "Level 1", vertexBufferObjectManager));
        backpackMenuScene.attachChild(level2Text = new Text(GameManager.CAMERA_WIDTH / 2 + 400, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "Level 2", vertexBufferObjectManager));
        backpackMenuScene.attachChild(level3Text = new Text(GameManager.CAMERA_WIDTH / 2 + 400, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "Level 3", vertexBufferObjectManager));

        // ---------------- Esconder los textos que no sean N1 ---
        level1Text.setVisible(true);
        level2Text.setVisible(false);
        level3Text.setVisible(false);

        // =============== Ubicando los botones =================
        backButton.setPosition(150, GameManager.CAMERA_HEIGHT - 125);
        rightArrow.setPosition(GameManager.CAMERA_WIDTH - 100, GameManager.CAMERA_HEIGHT / 2 - 50);
        leftArrow.setPosition(100, GameManager.CAMERA_HEIGHT/2 -50);

        // ============== Inicializar contador para el Scroll ===
        countPosition= 1;

        leftArrow.setVisible(false);

        backpackMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()) {
                    //------  Quita y pone las flechas dependiendo
                    // del nivel para evitar que se sigan presionando.
                    // decrementamos countPosition según el caso,
                    // llamamos attachGems y reproducimos un sonido --
                    case LEFT_ARROW:
                        if(countPosition > 1){
                            countPosition--;
                            attachGems(countPosition, LEFT_ARROW);
                            resourceManager.soundThree.play();
                        }
                        break;
                    case RIGHT_ARROW:
                        if(countPosition < 3){
                            countPosition++;
                            attachGems(countPosition, RIGHT_ARROW);
                            resourceManager.soundThree.play();
                        }
                        break;
                    //Regresa al menu si se presiona la flecha de back
                    case SUBMENU_BACK:
                        returnToMenu();
                        resourceManager.soundOne.play();
                        break;
                }
                // -- Registramos la visibilidad de las flechas según el valor de
                //    countPosition
                if (countPosition == 1) {
                    leftArrow.setVisible(false);
                }
                else if (countPosition == 2) {
                    leftArrow.setVisible(true);
                    rightArrow.setVisible(true);
                }
                else if (countPosition == 3) {
                    rightArrow.setVisible(false);
                }
                return true;
            }
        });

    }

    // ===========================================================
    // Se hacen visibles los bloques de gemas dependiendo el nivel
    // ===========================================================
    public void attachGems(int level,int side){
        if(level == 1) {
            //Registra un modifier de FadeIn para el texto del nivel
            level1Text.registerEntityModifier(new FadeInModifier(1.0f));
            // -- Realiza un modifier que mueve las gemas del lado dependiendo de dónde se viene (isquierda o derecha) --
            if(side == RIGHT_ARROW){backpackGemContainerLevel1.registerEntityModifier(new MoveXModifier(0.5f, GameManager.CAMERA_WIDTH, 0));}
            else{backpackGemContainerLevel1.registerEntityModifier(new MoveXModifier(0.5f,-GameManager.CAMERA_WIDTH,0));}
            // -- Mostrar texto de nivel 1 y ocular el del 2 --
            level1Text.setVisible(true);
            level2Text.setVisible(false);
            // -- Hacer visible el bloque 1 y ocultar el bloque 2  --
            backpackGemContainerLevel1.setVisible(true) ;
            backpackGemContainerLevel2.setVisible(false);}

        else if(level==2){
            //Registra un modifier de FadeIn para el texto del nivel
            level2Text.registerEntityModifier(new FadeInModifier(1.0f));
            // -- Realiza un modifier que mueve las gemas del lado dependiendo de dónde se viene (isquierda o derecha) --
            if(side == RIGHT_ARROW){backpackGemContainerLevel2.registerEntityModifier(new MoveXModifier(0.5f,GameManager.CAMERA_WIDTH,0));}
            else{ backpackGemContainerLevel2.registerEntityModifier(new MoveXModifier(0.5f,-GameManager.CAMERA_WIDTH,0));}
            // -- Mostrar texto de nivel 2 y ocular los de 2 y 3 --
            level1Text.setVisible(false);
            level2Text.setVisible(true);
            level3Text.setVisible(false);
            // -- Hacer visible el bloque 2 y ocultar los bloques 2 y 3 --
            backpackGemContainerLevel1.setVisible(false);
            backpackGemContainerLevel3.setVisible(false);
            backpackGemContainerLevel2.setVisible(true);}
        else if(level==3){
            //Registra un modifier de FadeIn para el texto del nivel
            level3Text.registerEntityModifier(new FadeInModifier(1.0f));
            // -- Realiza un modifier que mueve las gemas del lado dependiendo de dónde se viene (isquierda o derecha) --
            if(side == RIGHT_ARROW){backpackGemContainerLevel3.registerEntityModifier(new MoveXModifier(0.5f,GameManager.CAMERA_WIDTH,0));}
            else{ backpackGemContainerLevel3.registerEntityModifier(new MoveXModifier(0.5f,-GameManager.CAMERA_WIDTH,0));}
            // -- Mostrar texto de nivel 3 y ocular el del 2 --
            level2Text.setVisible(false);
            level3Text.setVisible(true);
            // -- Hacer visible el bloque 3 y ocultar el bloque 2  --
            backpackGemContainerLevel2.setVisible(false);
            backpackGemContainerLevel3.setVisible(true);
        }
        else{
            gameManager.toastOnUiThread("Algo ocurrió...");
        }

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

        // -- Sprite Alerta
        final Sprite alert = resourceManager.loadSprite(gameManager.CAMERA_WIDTH/2,gameManager.CAMERA_HEIGHT/2+100,resourceManager.settingsMenuAlertTextureRegion);

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
        // --Botón para crear un juego nuevo
        IMenuItem newGame = new ScaleMenuItemDecorator((new SpriteMenuItem(666, resourceManager.settingsMenuNewGameButtonTextureRegion,vertexBufferObjectManager)),0.8f,1.0f);
        // -- Botones alerta
        final IMenuItem cancel = new ScaleMenuItemDecorator(new SpriteMenuItem(100, resourceManager.settingsMenuCancelButtonTextureRegion,vertexBufferObjectManager),0.8f,1.0f);
        final IMenuItem ok = new ScaleMenuItemDecorator(new SpriteMenuItem(200, resourceManager.settingsMenuOKButtonTextureRegion,vertexBufferObjectManager),0.8f,1.0f);


        // =============== Agregando los botones =================
        settingsMenuScene.addMenuItem(cancel);
        settingsMenuScene.addMenuItem(ok);
        settingsMenuScene.addMenuItem(backButton);
        settingsMenuScene.addMenuItem(musicDecrease);
        settingsMenuScene.addMenuItem(musicIncrease);
        settingsMenuScene.addMenuItem(soundDecrease);
        settingsMenuScene.addMenuItem(soundIncrease);
        settingsMenuScene.addMenuItem(newGame);



        // Cambiar el Zeta de Nuevo Juego
        alert.setZIndex(10);
        newGame.setZIndex(9);
        cancel.setZIndex(11);
        ok.setZIndex(11);




        // =============== Configurando las animaciones =========
        // -- Construir las animaciones
        settingsMenuScene.buildAnimations();
        // -- Desahbilitar el fondo
        settingsMenuScene.setBackgroundEnabled(false);

        // =============== Agregando el Texto "BACKPACK" =========
        settingsMenuScene.attachChild(new Text(400, GameManager.CAMERA_HEIGHT - 125, resourceManager.fontOne, "SETTINGS", vertexBufferObjectManager));

        // =============== Ubicando los botones =================

        newGame.setPosition(GameManager.CAMERA_WIDTH-350,GameManager.CAMERA_HEIGHT-150);
        cancel.setPosition(GameManager.CAMERA_WIDTH/2-100,GameManager.CAMERA_HEIGHT/2-200);
        ok.setPosition(GameManager.CAMERA_WIDTH/2+100,GameManager.CAMERA_HEIGHT/2-200);
        backButton.setPosition(150, GameManager.CAMERA_HEIGHT - 125);
        musicDecrease.setPosition(300, GameManager.CAMERA_HEIGHT / 2 + 25);
        musicIncrease.setPosition(GameManager.CAMERA_WIDTH - 300, GameManager.CAMERA_HEIGHT / 2 + 25);
        soundDecrease.setPosition(300, GameManager.CAMERA_HEIGHT / 2 - 200);
        soundIncrease.setPosition(GameManager.CAMERA_WIDTH - 300, GameManager.CAMERA_HEIGHT / 2 - 200);

        settingsMenuScene.attachChild(alert);
        //Ocultando los botones de cancel y ok
        alert.setVisible(false);
        cancel.setVisible(false); settingsMenuScene.unregisterTouchArea(cancel);
        ok.setVisible(false); settingsMenuScene.unregisterTouchArea(ok);

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

        settingsMenuScene.sortChildren();

        // ========= Establecer Acción según botoón presionado ===
        settingsMenuScene.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                switch (pMenuItem.getID()) {
                    case MUSIC_DECREASE:
                        // -- Si el volúmen de la música no es 0 entonces lo decrementamos en 20%
                        if (sessionManager.musicVolume > 0.025f) {
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
                        if (sessionManager.soundVolume > 0.025f) {
                            sessionManager.soundVolume -= 0.20f;
                        }
                        break;
                    case 666:
                        //-- Se abre el cuadro de dialogo de alerta
                        alert.setVisible(true);
                        //-- Hacer visibles los botones
                        cancel.setVisible(true);settingsMenuScene.registerTouchArea(cancel);
                        ok.setVisible(true);settingsMenuScene.registerTouchArea(ok);
                        break;
                    case 100:
                        alert.setVisible(false);
                         cancel.setVisible(false);settingsMenuScene.unregisterTouchArea(cancel);
                         ok.setVisible(false);settingsMenuScene.unregisterTouchArea(ok);
                         break;
                    case 200:
                         sessionManager.currentLevel= 1;

                         sessionManager.gemsUnlocked[1][1]= false;
                         sessionManager.gemsUnlocked[1][2]= false;
                         sessionManager.gemsUnlocked[1][3]= false;
                         sessionManager.gemsUnlocked[2][1]= false;
                         sessionManager.gemsUnlocked[2][2]= false;
                         sessionManager.gemsUnlocked[2][3]= false;
                         sessionManager.gemsUnlocked[3][1]= false;
                         sessionManager.gemsUnlocked[3][2]= false;
                         sessionManager.gemsUnlocked[3][3]= false;
                         sessionManager.writeChanges();
                        alert.setVisible(false);
                        cancel.setVisible(false);settingsMenuScene.unregisterTouchArea(cancel);
                        ok.setVisible(false);settingsMenuScene.unregisterTouchArea(ok);
                        break;


                    case SUBMENU_BACK:
                        alert.setVisible(false);
                        cancel.setVisible(false);settingsMenuScene.unregisterTouchArea(cancel);
                        ok.setVisible(false);settingsMenuScene.unregisterTouchArea(ok);
                        // -- Regresamos al menú principal
                        returnToMenu();
                        resourceManager.soundOne.play();
                        // -- Cambiamos el estado del botón de habilitar/deshabilitar audio del menú principal basándonos en lo hecho en esta subescena;
                        // -- ** Si ambos volúmenes son 0, entonces significa que el audio está deshabilitado, de otro modo el audio está deshabilitado
                        ((ToggleSpriteMenuItem) mainMenuToggleAudioButton).setCurrentTileIndex((sessionManager.musicVolume > 0.025 || sessionManager.soundVolume > 0.025) ? 0 : 1);
                        break;
                }

                // -- Cambiamos los volúmenes maestros de la música y el sonido basado en la acción realizada
                resourceManager.updateAudioVolume();

                // -- Actualizamos el estado de las barras de audio basado en la acción realizada
                updateAudioVisibility();

                if(pMenuItem.getID() == SOUND_DECREASE || pMenuItem.getID() == SOUND_INCREASE){
                    resourceManager.soundTwo.play();
                }
                return true;
            }
        });
    }

    // ===========================================================
    //           Actualiza el estado de las barras de audio
    // ===========================================================
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
                if(pMenuItem.getID() != SUBMENU_BACK){resourceManager.soundThree.play();}
                switch (pMenuItem.getID()) {
                    case ABOUT_ANDY:
                        // -- Llamar a la función para mostrar el ID de ANDY
                        setAboutID(aboutMenuAndyIDSprite, true);
                        gameManager.toastOnUiThread("Bitch I'm fabulous!",Toast.LENGTH_LONG);
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
                        gameManager.toastOnUiThread("'It's not about how much time you have, it's how you use it.'",Toast.LENGTH_LONG);
                        break;
                    case ABOUT_DIEGO:
                        // -- Llamar a la función para mostrar el ID de DIEGO
                        setAboutID(aboutMenuDiegoIDSprite, true);
                        gameManager.toastOnUiThread("'Don't do it, compa.' - in response to something.",Toast.LENGTH_LONG);
                        break;
                    case SUBMENU_BACK:
                        // -- Regresar al menú principal
                        returnToMenu();
                        resourceManager.soundOne.play();
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
            sprite.registerEntityModifier(new FadeInModifier(0.25f));
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
            resourceManager.soundOne.play();
        }
    }

    // ===========================================================
    //                      Destruir la escena actual
    // ===========================================================
    @Override
    public void destroyScene() {
        // -- Llamamos al Adm. de Recursos para liberar las imágnes y la música
        resourceManager.unloadMenuResources();
        resourceManager.releaseAudio();
        // -- Desadjuntamos esta escena
        this.detachSelf();
        // -- Liberamos la escena
        this.dispose();
    }

}
