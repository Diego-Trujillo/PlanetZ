package mx.itesm.planetz;

import org.andengine.audio.music.MusicManager;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.io.IOException;

/**
 *  Define qué debe hacer una escena, así como ofrece algunos métodos útiles
 *
 * Created by Diego on 03/10/2015.
 */
public abstract class BaseScene extends Scene {
    //El identificador de la escena
    protected SceneType sceneType;

    //Referencia al Administrador de Juego, Administrador de Escenas y el Administrador de Recursos
    protected GameManager gameManager;
    protected SceneManager sceneManager;
    protected ResourceManager resourceManager;

    //Referencia al motor, la cámara y el Vertex Buffer Object Manager
    protected Engine engine;
    protected VertexBufferObjectManager vertexBufferObjectManager;
    protected Camera camera;

    //Constructor para la escena Base
    public BaseScene(){
        //Inicializa las referencias a los Administradores
        this.sceneManager =  SceneManager.getInstance();
        this.gameManager = sceneManager.gameManager;
        this.resourceManager = ResourceManager.getInstance();

        //Inicializa los demás objetos importantes
        this.engine = gameManager.getEngine();
        this.vertexBufferObjectManager = gameManager.getVertexBufferObjectManager();
        this.camera = engine.getCamera();

        //Carga los recursos y crea los visuales de la escena
        loadResources();
    }

    public void loadResources(){
        loadGFX();
        loadMFX();
        loadSFX();
    }

    //Métodos abstractos que se deben implementar
    public abstract void loadGFX();
    public abstract void loadMFX();
    public abstract void loadSFX();
    public abstract void createScene();
    public abstract void onBackKeyPressed();
    public abstract void destroyScene();

    // SETTERS & GETTERS ELEMENTALES
    public SceneType getSceneType() {return sceneType;}
}
