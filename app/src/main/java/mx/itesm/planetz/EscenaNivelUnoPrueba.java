package mx.itesm.planetz;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.opengl.texture.region.ITextureRegion;

import java.util.ResourceBundle;

/**
 * Created by Diego on 28/09/2015.
 */
public class EscenaNivelUnoPrueba extends EscenaBase implements IAccelerationListener{

    //Fondo
    protected ITextureRegion regionFondo;
    protected Sprite spriteFondo;
    protected ITextureRegion regionHeroe;
    protected Sprite spriteHeroe;

    @Override
    public void cargarRecursos() {
        regionFondo = cargarImagen("backgrounds/splash.jpg");
        regionHeroe = cargarImagen("btnAcercaDe.png");
    }

    @Override
    public void crearEscena() {
        spriteFondo = cargarSprite(ControlJuego.ANCHO_CAMARA/2,ControlJuego.ALTO_CAMARA/2,regionFondo);
        spriteHeroe = cargarSprite(ControlJuego.ANCHO_CAMARA/2, ControlJuego.ALTO_CAMARA/2,regionHeroe);

        attachChild(spriteFondo);
        attachChild(spriteHeroe);
        actividadJuego.getEngine().enableAccelerationSensor(actividadJuego,this);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public TipoEscena getTipoEscena() {
        return null;
    }

    @Override
    public void liberarEscena() {

    }

    @Override
    public void liberarRecursos() {
        actividadJuego.getEngine().disableAccelerationSensor(actividadJuego);
        regionHeroe.getTexture().unload();
        regionFondo.getTexture().unload();
    }

    @Override
    public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {

    }

    @Override
    public void onAccelerationChanged(AccelerationData pAccelerationData) {
        float dx = pAccelerationData.getX();
        float dy = pAccelerationData.getY();
        float dz = pAccelerationData.getZ();
        spriteHeroe.setX(spriteHeroe.getX() + dx * 4);
        //spriteHeroe.setY(spriteHeroe.getY() + dy/10);
    }
}
