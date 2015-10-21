package mx.itesm.planetz;

import android.app.Application;
import android.content.Context;

/**
 * Created by Andrea Pérez on 20/10/2015.
 */
public class AppContext extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        AppContext.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return AppContext.context;
    }
}