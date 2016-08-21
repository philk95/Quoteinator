package de.die.dudes.quoteinator.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.die.dudes.quoteinator.R;
import de.die.dudes.quoteinator.database.SqlDatabase;
import de.die.dudes.quoteinator.dataadapter.QuotationAdapter;
import de.die.dudes.quoteinator.dataadapter.RecyclerViewCursorAdapter;

/**
 * Created by Phil on 05.08.2016.
 */
public class QuotationFragment extends Fragment {

    public final static String DOCENT = "DOCENT";
    public final static String MODULE = "MODULE";

    public QuotationFragment() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_quotation, container, false);

        Cursor cursor = getCursor();

        QuotationAdapter adapter = new QuotationAdapter(cursor);
        adapter.setListener(new RecyclerViewCursorAdapter.ClickListener() {
            @Override
            public void onClick(int id) {
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }

    private Cursor getCursor() {
        Bundle bundle = getArguments();
        SqlDatabase db = new SqlDatabase(getContext());
        Cursor cursor = null;

        if (bundle == null) {
            cursor = db.getQuotationsCursor();
        } else {
            if (bundle.get(DOCENT) != null) {
                int id = bundle.getInt(DOCENT);
                cursor = db.getQuotationsCursorByDocent(id);
            } else if (bundle.get(MODULE) != null) {
                int id = bundle.getInt(MODULE);
                cursor = db.getQuotationsCursorByModule(id);
            }
        }
        return cursor;
    }


}
