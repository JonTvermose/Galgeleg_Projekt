package dk.jon.lektion2_galgeleg;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class side2_akt extends Activity implements View.OnClickListener{

    private ImageView img;
    private EditText editTxt;
    private TextView txt;
    private TextView bogstaver;
    private String bString;
    private Button gæt;
    private int antalGæt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side2_akt);

        if(savedInstanceState == null) {
            img = (ImageView) findViewById(R.id.mainImgImageView);
            editTxt = (EditText) findViewById(R.id.bogstavEditText);
            txt = (TextView) findViewById(R.id.ordTextView);
            bogstaver = (TextView) findViewById(R.id.tidligereGætTextView);
            bString = bogstaver.getText().toString();
            gæt = (Button) findViewById(R.id.gætButton);
            gæt.setOnClickListener(this);
            antalGæt = -1;

            Velkomst_akt.gl.nulstil();
            txt.setText(Velkomst_akt.gl.getSynligtOrd());
        }
    }

    @Override
    public void onClick(View v) {
        if (!bogstaver.getText().equals("Du har tabt!") || !bogstaver.getText().equals("Du har vundet!")){
            String bogstav = editTxt.getText().toString().toLowerCase();
            if (!Velkomst_akt.gl.getBrugteBogstaver().contains(bogstav)){
                Velkomst_akt.gl.gætBogstav(bogstav);

                String bogstavGæt = "";
                for (String s  : Velkomst_akt.gl.getBrugteBogstaver()){
                    bogstavGæt += s + ", " ;
                }
                bogstaver.setText(bString + bogstavGæt);

                txt.setText(Velkomst_akt.gl.getSynligtOrd());
                if (!Velkomst_akt.gl.erSidsteBogstavKorrekt()){
                    antalGæt++;
                    updateImg();
                }
                if (Velkomst_akt.gl.erSpilletSlut()){
                    if (Velkomst_akt.gl.erSpilletTabt()){
                        bogstaver.setText("Du har tabt!");
                    } else if (Velkomst_akt.gl.erSpilletVundet()){
                        bogstaver.setText("Du har vundet!");
                    }
                }
            }
        }
    }

    public void updateImg(){
        switch (antalGæt) {
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
}
