package de.die.dudes.quoteinator;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import de.die.dudes.quoteinator.database.SqlDatabase;
import de.die.dudes.quoteinator.model.QuotationAdapter;

/**
 * Created by Phil on 05.08.2016.
 */
public class QuotationFragment extends Fragment {

    public QuotationFragment() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_quotation, container, false);

        SqlDatabase db = new SqlDatabase(getContext());
       /* CursorAdapter cursorAdapter = new SimpleCursorAdapter(getContext(),
                android.R.layout.simple_list_item_2,
                db.getQuotationsCursor(),
                new String[]{SqlDatabase.QUOTATION_TEXT, SqlDatabase.QUOTATION_DATE},
                new int[]{android.R.id.text1, android.R.id.text2},
                0);*/

        QuotationAdapter adapter = new QuotationAdapter(db.getQuotationsCursor());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }


}
