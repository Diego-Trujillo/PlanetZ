package mx.itesm.planetz;

import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;

import java.util.ArrayList;

/**
 * Created by Diego on 29/10/2015.
 */
public class StoryScene extends BaseScene {
    // =============================================================================================
    //                          D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================
    private int currentLevel;

    // ===========================================================
    //      Contenedores para los slides de historia
    // ===========================================================
    // ============== Contenedor de Texturas =====================
    private ArrayList<ITextureRegion> storySlidesTextureRegionContainer;
    // ============== Contenedor de Sprites =====================
    private ArrayList<Sprite> storySlidesSpriteContainer;

    // ===========================================================
    //                   Sprites de Botones
    // ===========================================================
    // ============== Sprite para saltarse la escena =============
    private Sprite skipButton;
    // ============== Sprite flecha izquierda ====================
    private Sprite leftArrow;
    // ============== Sprite flecha derecha ======================
    private Sprite rightArrow;

    // ===========================================================
    //                 Elementos de UI
    // ===========================================================
    // ============== Menú para los botones ======================
    private org.andengine.entity.scene.menu.MenuScene containerMenu;

    // ===========================================================
    //                   Control de Flujo
    // ===========================================================
    // ============== ID de Botones ==============================
    private static final int PREVIOUS_SLIDE = 0;
    private static final int NEXT_SLIDE = 1;
    private static final int SKIP_SCENE = 2;

    // ============== Pathname a los slides ======================
    private String pathName;
    // ============== Índice actual de iteración =================
    private int currentIndex;
    // ============== Índice Máximo de contenido =================
    private int maxIndex;
    // =============================================================================================
    //                                    C O N S T R U C T O R
    // =============================================================================================

    public StoryScene(){
        // -- Inicializamos la super clase --
        super();
        // -- Asignamos el tipo de escena --
        sceneType = SceneType.STORY;

    }

    // =============================================================================================
    //                                       M É T O D O S
    // =============================================================================================
    // ===========================================================
    //                 Cargar recursos gráficos
    // ===========================================================
    @Override
    public void loadGFX() {
        // ============ Inicializamos los contenedores =======
        storySlidesTextureRegionContainer = new ArrayList<>();
        storySlidesSpriteContainer = new ArrayList<>();

        // -- Obtenemos el nivel en el que el jugador se encuentra en la historia --
        currentLevel = sessionManager.currentLevel;

        // ============ Asignamos el PathName para
        // los slides y el número de slides que habrá ========
        switch (currentLevel) {
            case 1:
                maxIndex = 6;
                pathName = "Graphics/Story/CrashCourseIntoOblivion/";
                break;
            case 2:
                maxIndex = 4;

                pathName = "Graphics/Story/BraveNewWorld/IntoTheWilderness/";
                break;
            case 3:
                break;
            default:
                // -- Su ocurre un error --
                gameManager.toastOnUiThread("This isn't supposed to happen, quitting.");
                gameManager.quit();
                break;

        }

        // ============ Cargamos las imágenes las asignamos a sprites ==
        for (int i = 1; i <= maxIndex; i++) {
            // -- Para cada imágenes en el folder ---
            // -- Cargamos la imagen --
            storySlidesTextureRegionContainer.add(resourceManager.loadImage(pathName + Integer.toString(i) + ".jpg"));
            // -- Asignamos la imagen a un sprite --
            storySlidesSpriteContainer.add(resourceManager.loadSprite(GameManager.CAMERA_WIDTH / 2, GameManager.CAMERA_HEIGHT / 2, storySlidesTextureRegionContainer.get(i - 1)));
        }
    }

    // ===========================================================
    //                      Cargar Música
    // ===========================================================
    @Override
    public void loadMFX() {

    }

    // ===========================================================
    //                      Cargar Sonidos
    // ===========================================================
    @Override
    public void loadSFX() {
        // -- Carga el sonido de cambiar --
        resourceManager.setSound("Story/ButtonPress.ogg", 1);
        // -- Actualizar el volumen --
        resourceManager.updateAudioVolume();

    }

    // ===========================================================
    //                      Crear Escena
    // ===========================================================
    @Override
    public void createScene() {

        // ============ El apuntador al slide que se está viendo =
        currentIndex = 0;
        // ============ Adjuntamos slides y ocultamos ============
        for(Sprite slide : storySlidesSpriteContainer){
            // -- Adjunta el slide --
            this.attachChild(slide);
            // -- Lo hace no-visible --
            slide.setVisible(false);
        }

        // -- Ponemos el primer slide como visible --
        storySlidesSpriteContainer.get(currentIndex).setVisible(true);

        // ============ Agregamos el menú =========================
        containerMenu = new org.andengine.entity.scene.menu.MenuScene(camera);

        // ============ Inicializando los botones ==================
        // -- Flecha Izquierda --
        IMenuItem leftArrow = new ScaleMenuItemDecorator(new SpriteMenuItem(PREVIOUS_SLIDE,resourceManager.loadImage("Graphics/Story/LeftArrow.png"),vertexBufferObjectManager),1.2f,1f);
        // -- Flecha Derecha --
        IMenuItem rightArrow = new ScaleMenuItemDecorator(new SpriteMenuItem(NEXT_SLIDE,resourceManager.loadImage("Graphics/Story/RightArrow.png"),vertexBufferObjectManager),1.2f,1f);
        // -- Saltar Escena --
        IMenuItem skipButton = new ScaleMenuItemDecorator(new SpriteMenuItem(SKIP_SCENE,resourceManager.loadImage("Graphics/Story/SkipButton.png"),vertexBufferObjectManager),1.2f,1f);

        // ============ Si el fondo está habilitado ================
        containerMenu.setBackgroundEnabled(false);
        containerMenu.buildAnimations();

        // ============ Agregando los botones ======================
        containerMenu.addMenuItem(leftArrow);
        containerMenu.addMenuItem(rightArrow);
        containerMenu.addMenuItem(skipButton);

        // =============== Ubicando los botones =================
        leftArrow.setPosition(100, 100);
        rightArrow.setPosition(GameManager.CAMERA_WIDTH - 256, 100);
        skipButton.setPosition(GameManager.CAMERA_WIDTH - 100, 100);


        // =============== Registrando las accione ===============
        containerMenu.setOnMenuItemClickListener(new org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener(){
            public boolean onMenuItemClicked(org.andengine.entity.scene.menu.MenuScene pMenuScene, IMenuItem pMenuItem,
                                             float pMenuItemLocalX, float pMenuItemLocalY){


                switch(pMenuItem.getID()){
                    case PREVIOUS_SLIDE:
                        // -- Si existe un slide detrás --
                        if(currentIndex > 0){
                            // -- Quitamos la visibilidad del slide actual --
                            storySlidesSpriteContainer.get(currentIndex--).setVisible(false);
                            // -- Ponemos la visibilidad del slide anterior --
                            storySlidesSpriteContainer.get(currentIndex).setVisible(true);
                            // -- Reproducir sonido de cambiado --
                            resourceManager.soundOne.play();

                        }

                        break;
                    case NEXT_SLIDE:
                        // -- Si todavía hay un slide adelante --
                        if(currentIndex < maxIndex - 1){
                            // -- Quitamos la visibilidad del slide actual --
                            storySlidesSpriteContainer.get(currentIndex++).setVisible(false);
                            // -- Ponemos la visibilidad del slide siguiente --
                            storySlidesSpriteContainer.get(currentIndex).setVisible(true);
                            // -- Reproducir sonido de cambiado --
                            resourceManager.soundOne.play();
                        }
                        else{
                            switch (sessionManager.currentLevel) {
                                case 1:
                                    // -- Liberamos la escena actual
                                    sceneManager.destroyScene(SceneType.STORY);
                                    // -- Creamos la escena del primer nivel --
                                    sceneManager.createScene(SceneType.ADVENTURE_LEVEL_1);
                                    // -- Corremos la escena del primer nivel
                                    sceneManager.setScene(SceneType.ADVENTURE_LEVEL_1);
                                    break;
                                case 2:
                                    sceneManager.destroyScene(SceneType.STORY);
                                    sceneManager.createScene(SceneType.ADVENTURE_LEVEL_2);
                                    sceneManager.setScene(SceneType.ADVENTURE_LEVEL_2);
                                    break;
                                case 3:
                                    sceneManager.destroyScene(SceneType.STORY);
                                    sceneManager.createScene(SceneType.ADVENTURE_LEVEL_3);
                                    sceneManager.setScene(SceneType.ADVENTURE_LEVEL_3);
                                    break;
                            }
                        }
                        break;
                    case SKIP_SCENE:
                        switch (sessionManager.currentLevel) {
                            case 1:
                                // -- Liberamos la escena actual
                                sceneManager.destroyScene(SceneType.STORY);
                                // -- Creamos la escena del primer nivel --
                                sceneManager.createScene(SceneType.ADVENTURE_LEVEL_1);
                                // -- Corremos la escena del primer nivel
                                sceneManager.setScene(SceneType.ADVENTURE_LEVEL_1);
                                break;
                            case 2:
                                sceneManager.destroyScene(SceneType.STORY);
                                sceneManager.createScene(SceneType.ADVENTURE_LEVEL_2);
                                sceneManager.setScene(SceneType.ADVENTURE_LEVEL_2);
                                break;
                            case 3:
                                sceneManager.destroyScene(SceneType.STORY);
                                sceneManager.createScene(SceneType.ADVENTURE_LEVEL_3);
                                sceneManager.setScene(SceneType.ADVENTURE_LEVEL_3);
                                break;
                        }
                        break;

                }

                return true;
            }
        });

        // ============== Adjuntamos el menú =================
        this.setChildScene(containerMenu);

    }

    // ===========================================================
    //            Cuando se presiona la tecla retroceder
    // ===========================================================
    @Override
    public void onBackKeyPressed() {
        // -- Liberamos la escena actual
        sceneManager.destroyScene(SceneType.STORY);
        // -- Creamos la escena del primer nivel --
        sceneManager.createScene(SceneType.MENU);
        // -- Corremos la escena del primer nivel
        sceneManager.setScene(SceneType.MENU);

    }

    // ===========================================================
    //                      Destruir la escena actual
    // ===========================================================
    @Override
    public void destroyScene() {
        // ============== Liberamos las imágnes ==================
        for(Sprite sp : storySlidesSpriteContainer){
            sp.detachSelf();
            sp.dispose();
        }

        // -------------- Liberamos el sonido --------------------
        resourceManager.releaseAllSound();

        // ============== Liberamos todos los hijos y esto ======
        this.detachChildren();
        this.dispose();
    }
}
