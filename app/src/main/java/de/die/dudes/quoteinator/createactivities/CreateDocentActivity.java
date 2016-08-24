package de.die.dudes.quoteinator.createactivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import de.die.dudes.quoteinator.R;
import de.die.dudes.quoteinator.database.SqlDatabase;
import de.die.dudes.quoteinator.model.Docent;

public class CreateDocentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_docent);

        getSupportActionBar().setSubtitle(R.string.createDocent);
    }

    public void onSave(View view) {
        EditText docent = (EditText) findViewById(R.id.editDocent);
        String docentName = docent.getText().toString();

        SqlDatabase db = new SqlDatabase(this);
        db.addDocent(new Docent(docentName));
        db.close();
        finish();
    }

    public void onDiscard(View view) {
        finish();
    }


}
