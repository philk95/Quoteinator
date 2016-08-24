package de.die.dudes.quoteinator.createactivities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import de.die.dudes.quoteinator.R;
import de.die.dudes.quoteinator.database.SqlDatabase;
import de.die.dudes.quoteinator.model.Docent;
import de.die.dudes.quoteinator.model.Module;

public class CreateModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_module);

        updateDocentSpinner();

        getSupportActionBar().setSubtitle(R.string.createModule);
    }

    private void updateDocentSpinner() {
        SqlDatabase db = new SqlDatabase(this);
        Spinner spinner = (Spinner) findViewById(R.id.docentSpinner);
        SpinnerAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                db.getDocentsCursor(),
                new String[]{SqlDatabase.DOCENT_LASTNMAE},
                new int[]{android.R.id.text1},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spinner.setAdapter(adapter);
        db.close();
    }

    public void onSave(View view) {
        EditText module = (EditText) findViewById(R.id.editModule);
        String moduleName = module.getText().toString();
        Spinner spinner = (Spinner) findViewById(R.id.docentSpinner);

        SqlDatabase db = new SqlDatabase(this);
        String docentName = ((Cursor) spinner.getSelectedItem()).getString(1);
        db.addModule(new Module(moduleName, new Docent(docentName)));
        db.close();
        finish();
    }

    public void onDiscard(View view) {
        finish();
    }

    public void onAddDocent(View view) {
        Intent intent = new Intent(this, CreateDocentActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDocentSpinner();
    }
}
