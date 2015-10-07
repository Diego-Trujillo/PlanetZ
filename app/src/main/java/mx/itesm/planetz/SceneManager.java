package mx.itesm.planetz;

import org.andengine.engine.Engine;

/**
 * Created by Diego on 03/10/2015.
 * Administra las escenas del juego.
 */
public class SceneManager {


    //Es una instancia única, por lo que la declaramos.
    private static SceneManager INSTANCE = new SceneManager();

    //Una referencia a la actividad principal yle motor del juego
    protected GameManager gameManager;
    private Engine engine;

    //Declara las escenas que contiene el juego
    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene playScene;
    private BaseScene settingsScene;
    private BaseScene backpackScene;
    private BaseScene aboutScene;

    // Declara en qué escena nos encontramos, así como su identificador.
    private BaseScene currentScene;
    private SceneType currentSceneType = SceneType.SPLASH;

    //Inicializa el SceneManager con los datos pasados de el GameManager
    public static void initialize(GameManager gameManager){
        getInstance().gameManager = gameManager;
        getInstance().engine = gameManager.getEngine();

    }

    // Inicializa los recursos de la escena elegida
    public void createScene(SceneType sceneType){
        switch (sceneType){
            case SPLASH:
                splashScene = new SplashScene();
                break;
            case MENU:
                menuScene = new MenuScene();
                break;
        }
    }

    //Asigna la escena dada como la escena actual y la pasa al motor.
    public void setScene(SceneType sceneType){
        switch(sceneType){
            case SPLASH:
                currentScene = splashScene;
                break;
            case MENU:
                currentScene = menuScene;

            default:
                currentScene = menuScene;
                break;
        }
        currentSceneType = currentScene.getSceneType();
        currentScene.createScene();
        engine.setScene(currentScene);
    }

    //Libera los recursos de la escena especificada
    public void destroyScene(SceneType sceneType){
        switch (sceneType){
            case SPLASH:
                splashScene.destroyScene();
                splashScene = null;
                break;
            case MENU:
                menuScene.destroyScene();
                menuScene = null;
        }
    }



    // GETTERS & SETTERS ELEMENTALES
    public static SceneManager getInstance() {return INSTANCE;}

    public BaseScene getCurrentScene() {return currentScene; }

    public SceneType getCurrentSceneType() {return currentSceneType;}
}
