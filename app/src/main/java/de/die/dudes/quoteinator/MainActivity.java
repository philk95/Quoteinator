package de.die.dudes.quoteinator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SqlDatabase db = new SqlDatabase(getApplicationContext());

        Module m = new Module("Analysis I");
         db.addModule(m);

        ArrayList<Module> modules = db.getModules();

        for (Module mod : modules){
            Log.e("++++++++", mod.getName());
            Log.e("++++++++", ""+mod.getDocent_id());
            Log.e("++++++++", ""+mod.getModule_id());
        }
    }
}
