package de.die.dudes.quoteinator;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import de.die.dudes.quoteinator.database.SqlDatabase;

/**
 * Created by Phil on 05.08.2016.
 */
public class QuotationFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SqlDatabase db = new SqlDatabase(getContext());
        CursorAdapter cursorAdapter = new SimpleCursorAdapter(getContext(),
                android.R.layout.simple_list_item_2,
                db.getQuotationsCursor(),
                new String[]{SqlDatabase.QUOTATION_TEXT, SqlDatabase.QUOTATION_DATE},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);
        setListAdapter(cursorAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id ){

    }
}
