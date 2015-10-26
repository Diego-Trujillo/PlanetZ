package mx.itesm.planetz;

import org.andengine.extension.physics.box2d.PhysicsWorld;

/**
 * Created by Diego on 25/10/2015.
 */
public class MeteorFactory {
    // =============================================================================================
    //                     D E C L A R A C I Ó N  D E  V A R I A B L E S
    // =============================================================================================

    // ===========================================================
    //                     Elementos del motor
    // ===========================================================
    // ============== El Administrador de Recursos ===============
    ResourceManager resourceManager;
    // ============== El Contenedor del Mundo de Física ==========
    PhysicsWorld physicsWorld;


    // =============================================================================================
    //                                    C O N S T R U C T O R
    // =============================================================================================

    public MeteorFactory(PhysicsWorld physicsWorld){
        this.physicsWorld = physicsWorld;
        resourceManager = ResourceManager.getInstance();
    }

}
