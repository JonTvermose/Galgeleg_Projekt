package dk.jon.lektion2_galgeleg;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class side3_akt extends Activity implements View.OnClickListener {

    private Button again;
    private TextView status, ordet, tabt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side3_akt);

        if(savedInstanceState == null) {
            again = (Button) findViewById(R.id.againButton);
            again.setOnClickListener(this);
            again.setAnimation(Velkomst_akt.animation);
            status = (TextView) findViewById(R.id.ord);
            ordet = (TextView) findViewById(R.id.ord2);
            tabt = (TextView) findViewById(R.id.ordetVar);

            if (Velkomst_akt.gl.erSpilletTabt()) {
                tabt.setText("Ordet var:");
                status.setText("Du har tabt!");
            } else if (Velkomst_akt.gl.erSpilletVundet()) {
                tabt.setText("Du gættede:");
                status.setText("Du har vundet!");
            }
            ordet.setText(Velkomst_akt.gl.getOrdet());
        }
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, side2_akt.class);
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
