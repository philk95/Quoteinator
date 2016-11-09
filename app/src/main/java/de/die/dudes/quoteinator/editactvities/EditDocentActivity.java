package de.die.dudes.quoteinator.editactvities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import de.die.dudes.quoteinator.R;
import de.die.dudes.quoteinator.database.SqlDatabase;
import de.die.dudes.quoteinator.fragments.DocentFragment;
import de.die.dudes.quoteinator.model.Docent;

public class EditDocentActivity extends AppCompatActivity {

    private EditText mDocent;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_docent);

        getSupportActionBar().setSubtitle(R.string.editDocentSubtitle);

        bindUIElements();
        fillUIElements();
    }

    private void bindUIElements() {
        mDocent = (EditText) findViewById(R.id.editDocent);
    }

    private void fillUIElements() {
        Intent intent = getIntent();
        mId = intent.getIntExtra(DocentFragment.ID_KEY, -1);

        SqlDatabase db = new SqlDatabase(this);
        Docent docent = db.getDocent(mId);
        db.close();

        mDocent.setText(docent.getName());
    }

    public void onSave(View view) {
        String docentName = mDocent.getText().toString();

        SqlDatabase db = new SqlDatabase(this);
        db.updateDocent(mId, new Docent(mDocent.getText().toString()));
        db.close();
        finish();
    }

    public void onDiscard(View view) {
        finish();
    }


}
