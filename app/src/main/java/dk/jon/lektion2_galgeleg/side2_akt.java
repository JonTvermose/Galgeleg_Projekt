package dk.jon.lektion2_galgeleg;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class side2_akt extends Activity implements View.OnClickListener, View.OnKeyListener{

    private ImageView img;
    private EditText editTxt;
    private TextView txt;
    private TextView bogstaver;
    private String bString;
    private Button gæt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side2_akt);

        if(savedInstanceState == null) {
            img = (ImageView) findViewById(R.id.mainImgImageView);
            editTxt = (EditText) findViewById(R.id.bogstavEditText);
            editTxt.setOnKeyListener(this);
            txt = (TextView) findViewById(R.id.ordTextView);
            bogstaver = (TextView) findViewById(R.id.tidligereGætTextView);
            bString = bogstaver.getText().toString();
            gæt = (Button) findViewById(R.id.gætButton);
            gæt.setOnClickListener(this);
            gæt.setAnimation(Velkomst_akt.animation);

            //Velkomst_akt.gl.nulstil(); // Fra version 1
            hentOrd(); // Version 2 vha. asynctask

            txt.setText("");
            Toast.makeText(this, "Henter tekst fra the webz", Toast.LENGTH_SHORT).show();
        }
    }

    private void hentOrd(){
        new AsyncTask(){

            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Velkomst_akt.gl.hentOrdFraDr();
                    return "Ordene blev hentet!";
                } catch (Exception e){
                    return "Der skete en fejl!";
                }
            }

            @Override
            protected void onPostExecute(Object result){
                txt.setText(Velkomst_akt.gl.getSynligtOrd());
            }
        }.execute();
    }

    @Override
    public void onClick(View v) {
        String bogstav = editTxt.getText().toString().toLowerCase();
        editTxt.setText(""); // Nulstiller indtastningsfeltet (fjerner bogstav)

        // Gæt på et bogstav og håndter fejlsituationer (ugyldige gæt)
        try {
            Velkomst_akt.gl.gætBogstav(bogstav);
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show(); // Viser en lille popup med fejltekst fra Galgelogik
            return;
        }

        // Opdater listen af gættede bogstaver
        String bogstavGæt = "";
        for (String s  : Velkomst_akt.gl.getBrugteBogstaver()){
            bogstavGæt += s + ", " ;
        }
        bogstaver.setText(bString + bogstavGæt);

        // Opdater det synlige ord
        txt.setText(Velkomst_akt.gl.getSynligtOrd());

        // Opdater billede hvis sidste gæt er forkert
        if (!Velkomst_akt.gl.erSidsteBogstavKorrekt()){
            updateImg();
        }

        // Tjek om spillet er slut
        if (Velkomst_akt.gl.erSpilletSlut()){
            Intent i = new Intent(this, side3_akt.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish(); // Slutter aktiviteten så man ikke kan trykke på "tilbage"-knap for at se denne aktivitet
        }
    }

    // Opdaterer billedet mht. antal gæt der er brugt
    public void updateImg(){
        switch (Velkomst_akt.gl.getAntalForkerteBogstaver()) {
            case 0:
                img.setImageResource(R.drawable.galge);
                break;
            case 1:
                img.setImageResource(R.drawable.forkert1);
                break;
            case 2:
                img.setImageResource(R.drawable.forkert2);
                break;
            case 3:
                img.setImageResource(R.drawable.forkert3);
                break;
            case 4:
                img.setImageResource(R.drawable.forkert4);
                break;
            case 5:
                img.setImageResource(R.drawable.forkert5);
                break;
            case 6:
                img.setImageResource(R.drawable.forkert6);
                break;
        }
    }

    // Klikker på "Gæt"-knappen når der trykkes på "Enter"-knappen på tastaturet
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            gæt.callOnClick(); // Klikker på "Gæt"-knappen
            return true;
        }
        return false;
    }
}
