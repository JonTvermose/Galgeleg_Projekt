package dk.jon.lektion2_galgeleg;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Svaerhedsgrad extends Activity {

    ListView listView;
    ArrayAdapter<String> adapter;
    String[] valg = {"Let", "Middel", "Svær"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svaerhedsgrad);

        listView = (ListView) findViewById(R.id.difView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,valg);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                   // Velkomst_akt.gl.saetsvaerhedsgrad(position);
                    Toast.makeText(getBaseContext(), "Der blev trykket på " + position, Toast.LENGTH_SHORT).show();
                    finish();
                } else if(position==1){
                    //Velkomst_akt.gl.saetsvaerhedsgrad(position);
                    Toast.makeText(getBaseContext(), "Der blev trykket på " + position, Toast.LENGTH_SHORT).show();
                    finish();
                } else if(position==2){
                    //Velkomst_akt.gl.saetsvaerhedsgrad(position);
                    Toast.makeText(getBaseContext(), "Der blev trykket på " + position, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
