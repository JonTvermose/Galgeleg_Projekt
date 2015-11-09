package dk.jon.lektion2_galgeleg;

import android.app.Activity;
import android.content.Intent;
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

    private Button start;
    private ImageView galgeImg;
    private Handler handler = new Handler();
    private int count;
    private boolean countMode;
    private Animation fadeIn, fadeOut;

    static Galgelogik gl;
    static AnimationSet animation;

    //Test commit incoming!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_velkomst_akt);

        if(savedInstanceState == null){
            gl = new Galgelogik();
            gl.nulstil();

            start = (Button) findViewById(R.id.startButton);
            galgeImg = (ImageView) findViewById(R.id.galgeImageView);
            count = -1;
            countMode = true;

            animation = new AnimationSet(false);
            fadeOut = new AlphaAnimation(1, 0);
            fadeOut.setInterpolator(new AccelerateInterpolator());
            fadeOut.setDuration(500);
            animation.addAnimation(fadeOut);
            fadeIn = new AlphaAnimation(0, 1);
            fadeIn.setInterpolator(new AccelerateInterpolator());
            fadeIn.setDuration(500);
            animation.addAnimation(fadeIn);

            start.setAnimation(animation);
            start.setOnClickListener(this);
        }

        startRunning(500);
    }


    @Override
    public void onClick(View v) {
        // handler.removeCallbacks(this);
        if (v==start){
            count = -1;
            Intent i = new Intent(this, side2_akt.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
                break;
        }
        count();
        startRunning(500);
    }

    // Kører run efter "ms" milisekunder
    public void startRunning(int ms) {
        handler.postDelayed(this, ms);
    }

    public void count(){
        if (count<=-1){
            countMode = true;
        } else if (count>=5){
            countMode = false;
        }

        if (countMode)
            count++;
        else
            count--;
    }
}
