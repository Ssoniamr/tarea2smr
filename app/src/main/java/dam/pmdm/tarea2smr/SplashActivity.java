package dam.pmdm.tarea2smr;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Clase para mostrar una pantalla splash durante un tiempo determinado, luego lanza la activityMain
 */
public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_TIME_OUT = 2000;

    /**
     * Inicializa la pantalla Splash y despues lanza la ActivityMain si la version de
     * android es menor a la 12, si es mayor lanza directamente la ActivityMain.
     *
     * @param savedInstanceState indica el estado anterior de la activity a recuperar en el caso de
     *                           qe lo haya,
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            setContentView(R.layout.activity_splash);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        } else {

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}