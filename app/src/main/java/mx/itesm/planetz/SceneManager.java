package mx.itesm.planetz;

import org.andengine.engine.Engine;

/**
 * Created by Diego on 03/10/2015.
 * Administra las escenas del juego.
 */
public class SceneManager {

    // =============================================================================================
    //                          D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================

    // ============== Referencia de Instancia Única =============
    private static SceneManager INSTANCE = new SceneManager();

    // ===========================================================
    //     Referencia al Administrador de Juego y el motor
    // ===========================================================
    protected GameManager gameManager;
    private Engine engine;

    // ===========================================================
    //                    ESCENAS DEL JUEGO
    // ===========================================================
    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene levelOneScene;
    private BaseScene YouLoseScene;

    // ===========================================================
    //  Referencia y ID de la escena en la que estamos actualmente
    // ===========================================================
    private BaseScene currentScene;
    private SceneType currentSceneType = SceneType.SPLASH;

    // =============================================================================================
    //                                         M É T O D O S
    // =============================================================================================

    // ===========================================================
    //    Inicializa la instancia única de el Adm. de Escenas
    // ===========================================================
    public static void initialize(GameManager gameManager){
        getInstance().gameManager = gameManager;
        getInstance().engine = gameManager.getEngine();

    }

    // ===========================================================
    //     Crea la escena seleccionada por el SceneType dado
    // ===========================================================
    public void createScene(SceneType sceneType){
        // ============== Crea la escena según el argumento ======
        switch (sceneType){
            case SPLASH:
                splashScene = new SplashScene();
                break;
            case MENU:
                menuScene = new MenuScene();
                break;
            case ADVENTURE_LEVEL_1:
                levelOneScene = new AdventureLevelOneScene();
                break;
            case YOU_LOSE:
                YouLoseScene = new YouLose();
                break;

        }
    }

    // ===========================================================
    // Selecciona y corre la escena seleccionada por el SceneType dado
    // ===========================================================
    public void setScene(SceneType sceneType){
        // ============== Asigna la escena actual según el arg. ==
        switch(sceneType){
            case SPLASH:
                currentScene = splashScene;
                break;
            case MENU:
                currentScene = menuScene;
                break;
            case ADVENTURE_LEVEL_1:
                currentScene = levelOneScene;
                break;
            case YOU_LOSE:
                currentScene = YouLoseScene;
                break;
            default:
                currentScene = menuScene;
                break;
        }
        // ============== Asigna el ID de escena =================
        System.out.println(currentScene);
        currentSceneType = currentScene.getSceneType();

        // ============== Llama al método de la escena a crearse =
        currentScene.createScene();

        // ============== Llama al motor para correr la escena ===
        engine.setScene(currentScene);
    }

    // ===========================================================
    //    Libera los recursos y la escena dada por el SceneType
    // ===========================================================
    public void destroyScene(SceneType sceneType){
        // ============== Llama destroyScene() de la selección ===
        switch (sceneType){
            case SPLASH:
                splashScene.destroyScene();
                splashScene = null;
                break;
            case MENU:
                menuScene.destroyScene();
                menuScene = null;
                break;
            case ADVENTURE_LEVEL_1:
                levelOneScene.destroyScene();
                levelOneScene = null;
                break;
            case YOU_LOSE:
                YouLoseScene.destroyScene();
                YouLoseScene = null;
                break;
        }
    }

    // =============================================================================================
    //                                G E T T E R S  &  S E T T E R S
    // =============================================================================================
    // ============== REGRESA la INSTANCIA de este Adm. =========
    public static SceneManager getInstance() {return INSTANCE;}

    // ============== REGRESA la ESCENA ACTUAL en el juego ======
    public BaseScene getCurrentScene() {return currentScene; }

    // ============== REGRESA el TIPO DE ESCENA ACTUAL ==========
    public SceneType getCurrentSceneType() {return currentSceneType;}
}
