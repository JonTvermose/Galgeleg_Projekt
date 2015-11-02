package dk.jon.lektion2_galgeleg;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;

public class Velkomst_akt extends Activity implements View.OnClickListener, Runnable{

    private Galgelogik gl;
    private Button start;
    private ImageView galgeImg;
    private Handler handler = new Handler();
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_velkomst_akt);

        if(savedInstanceState == null){
            gl = new Galgelogik();
            start = (Button) findViewById(R.id.startButton);

            galgeImg = (ImageView) findViewById(R.id.galgeImageView);
            count = -1;
        }

        startRunning(500);
    }


    @Override
    public void onClick(View v) {
        if (v==start){

        }
    }

    @Override
    public void run() {
        switch (count){
            case -1:
                galgeImg.setImageResource(R.drawable.galge);
                break;
            case 0:
                galgeImg.setImageResource(R.drawable.forkert1);
                break;
            case 1:
                galgeImg.setImageResource(R.drawable.forkert2);
                break;
            case 2:
                galgeImg.setImageResource(R.drawable.forkert3);
                break;
            case 3:
                galgeImg.setImageResource(R.drawable.forkert4);
                break;
            case 4:
                galgeImg.setImageResource(R.drawable.forkert5);
                break;
            case 5:
                galgeImg.setImageResource(R.drawable.forkert6);
                count = -2; // reset animation
                break;
        }
        count++;
        startRunning(500);
    }

    // Kører run efter "ms" milisekunder
    public void startRunning(int ms){
        handler.postDelayed(this, ms);
    }
}
