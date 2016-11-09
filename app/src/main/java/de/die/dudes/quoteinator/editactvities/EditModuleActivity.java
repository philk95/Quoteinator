package de.die.dudes.quoteinator.editactvities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import de.die.dudes.quoteinator.R;
import de.die.dudes.quoteinator.database.SqlDatabase;
import de.die.dudes.quoteinator.database.Util;
import de.die.dudes.quoteinator.fragments.ModuleFragment;
import de.die.dudes.quoteinator.model.Module;

public class EditModuleActivity extends AppCompatActivity {

    private EditText mModuleEditText;
    private Spinner mSpinner;
    private Module mModule;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_module);
        getSupportActionBar().setSubtitle(R.string.editModuleSubtitle);

        bindUIElements();

        updateDocentSpinner();
    }

    private void bindUIElements() {
        mModuleEditText = (EditText) findViewById(R.id.editModule);
        mSpinner = (Spinner) findViewById(R.id.docentSpinner);
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
        String moduleName = mModuleEditText.getText().toString();
        Cursor cursor = (Cursor) mSpinner.getSelectedItem();
        String docentName = cursor.getString(cursor.getColumnIndex(SqlDatabase.DOCENT_LASTNMAE));

        mModule.setName(moduleName);
        SqlDatabase db = new SqlDatabase(this);
        mModule.setDocent(db.getDocent(docentName));
        db.updateModule(mId, mModule);
        db.close();
        finish();
    }

    public void onDiscard(View view) {
        finish();
    }

    public void onAddDocent(View view) {
        //TODO startActivityForResult(); Directly selected in the spinner
        Intent intent = new Intent(this, EditDocentActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        fillUIElements();
    }

    private void fillUIElements() {
        Intent intent = getIntent();
        mId = intent.getIntExtra(ModuleFragment.ID_KEY, -1);

        SqlDatabase db = new SqlDatabase(this);
        mModule = db.getModule(mId);
        db.close();

        mModuleEditText.setText(mModule.getName());
        mSpinner.setSelection(Util.getPositionByName(mSpinner, mModule.getDocent().getId()));
    }
}
