package mx.itesm.planetz;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import java.util.ArrayList;

/**
 * Created by Diego on 28/11/2015.
 */
public class PlayerHUD extends HUD {
    // =============================================================================================
    //                     D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================
    public Text timeRemainingText;
    public BaseScene gameScene;
    public ResourceManager resourceManager;
    public SceneManager sceneManager;



    public ArrayList<Sprite> playerLivesSprites;
    public Sprite pauseButton;
    public Sprite pauseScreen;
    public Sprite unpauseButton;
    public Sprite backButton;


    // =============================================================================================
    //                                    C O N S T R U C T O R
    // =============================================================================================
    public PlayerHUD(final BaseScene gameScene, int currentLevel){
        super();
        this.gameScene = gameScene;
        resourceManager = gameScene.resourceManager;
        sceneManager = gameScene.sceneManager;

        resourceManager.loadHUD();


        // ============= Cargamos recursos gráficos ==============
        // -- Crea los sprites de los cascos de la vida
        playerLivesSprites = new ArrayList<>();
        playerLivesSprites.add(resourceManager.loadSprite(50,GameManager.CAMERA_HEIGHT - 50,resourceManager.HUDPlayerLivesTextureRegion));
        playerLivesSprites.add(resourceManager.loadSprite(50 + 75,GameManager.CAMERA_HEIGHT - 50,resourceManager.HUDPlayerLivesTextureRegion));
        playerLivesSprites.add(resourceManager.loadSprite(50 + 75 + 75,GameManager.CAMERA_HEIGHT - 50,resourceManager.HUDPlayerLivesTextureRegion));

        // -- El texto de tiempo para ganar
        timeRemainingText = new Text(GameManager.CAMERA_WIDTH/2,GameManager.CAMERA_HEIGHT - 50,resourceManager.fontOne,"  ",gameScene.vertexBufferObjectManager);


        // ============= Creamos la SubPantalla de Pausa ==========
        // -- Creamos el botón de PAUSA y sus acciones
        pauseButton = new Sprite(GameManager.CAMERA_WIDTH - 50,GameManager.CAMERA_HEIGHT-50,resourceManager.HUDPauseButtonTextureRegion,gameScene.vertexBufferObjectManager){
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y){
                // Cuando se toca el botón de pausa
                if (pSceneTouchEvent.isActionUp()) {
                    pause(true);
                }
                return true;
            }
        };
         // -- Creamos la pantalla de PAUSA
        pauseScreen = new Sprite(GameManager.CAMERA_WIDTH/2, GameManager.CAMERA_HEIGHT/2, resourceManager.HUDPauseScreenTextureRegion,gameScene.vertexBufferObjectManager);

        unpauseButton = new Sprite(GameManager.CAMERA_WIDTH/2 - 75, GameManager.CAMERA_HEIGHT/2 - 75, resourceManager.HUDUnpauseButtonTextureRegion,gameScene.vertexBufferObjectManager){
            //si se da click se reanuda colocando el time step en 1/30
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y){
                //Cuando se toque la pantalla de pausa
                if (pSceneTouchEvent.isActionUp()) {
                    pause(false);
                }
                return true;}
             };

        backButton  = new Sprite(GameManager.CAMERA_WIDTH/2 + 75 , GameManager.CAMERA_HEIGHT/2 - 75, resourceManager.HUDReturnToMenuButtonTextureRegion,gameScene.vertexBufferObjectManager){
            //regresa al menu play en
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float X, float Y){
                //Cuando se toque la pantalla de pausa
                if (pSceneTouchEvent.isActionUp()) {
                    // -- Liberamos la escena actual
                    sceneManager.destroyScene(gameScene.sceneType);
                    // -- Creamos la escena del primer nivel
                    sceneManager.createScene(SceneType.MENU);
                    // -- Corremos la escena del primer nivel
                    sceneManager.setScene(SceneType.MENU);


                }
                return true;}
        };

        // ============= Adjuntamos todos los elementos al HUD ===
        this.attachChild(pauseButton);
        this.attachChild(pauseScreen);
        this.attachChild(unpauseButton);
        this.attachChild(backButton);
        this.attachChild(timeRemainingText);
        this.attachChild(playerLivesSprites.get(0));
        this.attachChild(playerLivesSprites.get(1));
        this.attachChild(playerLivesSprites.get(2));

        // ============= Deshabilitamos la pantalla de pausa =====
        pauseScreen.setVisible(false);
        unpauseButton.setVisible(false);
        backButton.setVisible(false);
    }


    // =============================================================================================
    //                                       M É T O D O S
    // =============================================================================================
    public void attachToScene(){
        gameScene.camera.setHUD(this);
        this.registerTouchArea(pauseButton);
    }


    public void pause(boolean willBePaused){
        if(!willBePaused){
            // -- Quita la visibilidad de la pantalla
            pauseScreen.setVisible(false);
            unpauseButton.setVisible(false);
            backButton.setVisible(false);
            // -- Desregistra la habilidad de tocar esta pantalla
            this.unregisterTouchArea(backButton);
            this.unregisterTouchArea(unpauseButton);

            // -- Despausa el juego
            gameScene.unPause();
        }
        else{
            // -- Hacemos a la pantalla de pausa visible
            pauseScreen.setVisible(true);
            unpauseButton.setVisible(true);
            backButton.setVisible(true);

            // -- Registramos el área táctil de la pantalla de pausa
            this.registerTouchArea(unpauseButton);
            this.registerTouchArea(backButton);

            //-- Pausa el juego
            gameScene.pause();
        }
    }

    public void updateLives(int playerLives){
        for(int i = 0; i < playerLivesSprites.size(); i++){
            playerLivesSprites.get(i).setVisible(playerLives >= i + 1);
        }
    }

    public void updateTimeText(int timeRemaining){
        timeRemainingText.setText(Integer.toString(timeRemaining));
    }

    public void destroy(){
        resourceManager.unloadHUD();
        this.detachChildren();
        this.clearTouchAreas();
        this.detachSelf();
        this.dispose();
    }

}
