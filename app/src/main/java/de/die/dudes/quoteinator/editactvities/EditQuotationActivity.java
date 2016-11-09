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
import de.die.dudes.quoteinator.fragments.QuotationFragment;
import de.die.dudes.quoteinator.model.Quotation;

//TODO On rotate: the changes get lost. Data are from db.
public class EditQuotationActivity extends AppCompatActivity {


    private EditText mQuotationEditText;
    private Spinner mSpinner;
    private int mId;
    private Quotation mQuotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quotation);
        getSupportActionBar().setSubtitle(R.string.editQuotationSubtitle);


        bindUIElements();

        updateModuleSpinner();
    }

    private void fillUIElements() {
        Intent intent = getIntent();
        mId = intent.getIntExtra(QuotationFragment.ID_KEY, -1);

        SqlDatabase db = new SqlDatabase(this);
        mQuotation = db.getQuotation(mId);
        db.close();

        mQuotationEditText.setText(mQuotation.getText());
        int position = Util.getPositionByName(mSpinner, mQuotation.getModul().getID());
        mSpinner.setSelection(position, true);
    }

    private void bindUIElements() {
        mQuotationEditText = (EditText) findViewById(R.id.editQuotation);
        mSpinner = (Spinner) findViewById(R.id.moduleSpinner);
    }

    private void updateModuleSpinner() {
        SqlDatabase db = new SqlDatabase(this);
        SpinnerAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                db.getModulesCursor(),
                new String[]{SqlDatabase.MODULE_NAME},
                new int[]{android.R.id.text1},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        mSpinner.setAdapter(adapter);
        db.close();
    }

    public void onAddModule(View view) {
        //TODO startActivityForResult(); Directly selected in the spinner
        Intent intent = new Intent(this, EditModuleActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        fillUIElements();
    }

    public void onSave(View view) {
        String quotationText = mQuotationEditText.getText().toString();
        Cursor cursor = (Cursor) mSpinner.getSelectedItem();
        String moduleName = cursor.getString(cursor.getColumnIndex(SqlDatabase.MODULE_NAME));

        SqlDatabase db = new SqlDatabase(this);
        mQuotation.setText(quotationText);
        mQuotation.setModul(db.getModule(moduleName));
        db.updateQuotation(mId, mQuotation);
        db.close();
        finish();
    }

    public void onDiscard(View view) {
        finish();
    }


}
