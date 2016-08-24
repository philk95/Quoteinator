package de.die.dudes.quoteinator.createactivities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.Calendar;

import de.die.dudes.quoteinator.R;
import de.die.dudes.quoteinator.database.SqlDatabase;
import de.die.dudes.quoteinator.model.Docent;
import de.die.dudes.quoteinator.model.Module;
import de.die.dudes.quoteinator.model.Quotation;

public class CreateQuotationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quotation);

        updateModuleSpinner();

        getSupportActionBar().setSubtitle(R.string.createQuotation);
    }

    private void updateModuleSpinner() {
        SqlDatabase db = new SqlDatabase(this);
        Spinner spinner = (Spinner) findViewById(R.id.moduleSpinner);
        SpinnerAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                db.getModulesCursor(),
                new String[]{SqlDatabase.MODULE_NAME},
                new int[]{android.R.id.text1},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        spinner.setAdapter(adapter);
        db.close();
    }

    public void onAddModule(View view) {
        Intent intent = new Intent(this, CreateModuleActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateModuleSpinner();
    }

    public void onSave(View view) {
        EditText quotation = (EditText) findViewById(R.id.editQuotation);
        String quotationText = quotation.getText().toString();
        Spinner spinner = (Spinner) findViewById(R.id.moduleSpinner);

        SqlDatabase db = new SqlDatabase(this);
        String moduleName = ((Cursor) spinner.getSelectedItem()).getString(1);
        db.addQuotation(new Quotation(db.getModule(moduleName),Calendar.getInstance(), quotationText));
        db.close();
        finish();
    }


    public void onDiscard(View view) {
        finish();
    }

}
