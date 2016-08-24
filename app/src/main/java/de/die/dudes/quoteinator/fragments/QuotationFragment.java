package de.die.dudes.quoteinator.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import de.die.dudes.quoteinator.R;
import de.die.dudes.quoteinator.createactivities.CreateDocentActivity;
import de.die.dudes.quoteinator.createactivities.CreateQuotationActivity;
import de.die.dudes.quoteinator.database.SqlDatabase;
import de.die.dudes.quoteinator.dataadapter.QuotationAdapter;
import de.die.dudes.quoteinator.dataadapter.RecyclerViewCursorAdapter;
import de.die.dudes.quoteinator.dialog.DeleteDialog;

/**
 * Created by Phil on 05.08.2016.
 */
public class QuotationFragment extends Fragment implements DeleteDialog.DeleteDialogListener {

    public static final String ID_KEY = "ID_KEY";
    public final static String DOCENT = "DOCENT";
    public final static String MODULE = "MODULE";

    private SqlDatabase db;
    private QuotationAdapter adapter;

    public QuotationFragment() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) inflater.inflate(R.layout.fragment_quotation, container, false);
        RecyclerView recyclerView = (RecyclerView) coordinatorLayout.findViewById(R.id.quotation_recycler);

        adapter = new QuotationAdapter(getCursor());
        adapter.setListener(new RecyclerViewCursorAdapter.ClickListener() {
            @Override
            public void onClick(int id) {
            }
        });

        adapter.setLongClickListener(new RecyclerViewCursorAdapter.LongClickListener() {
            @Override
            public void onClick(int id) {
                DeleteDialog deleteDialog = new DeleteDialog();
                Bundle bundle = new Bundle();
                bundle.putInt(ID_KEY, id);
                deleteDialog.setArguments(bundle);
                deleteDialog.setTargetFragment(QuotationFragment.this, 0);
                deleteDialog.show(getFragmentManager(), "delete");
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) coordinatorLayout.findViewById(R.id.addQuotation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateQuotationActivity.class);
                startActivity(intent);
            }
        });

        return coordinatorLayout;
    }

    private Cursor getCursor() {
        Bundle bundle = getArguments();
        db = new SqlDatabase(getContext());
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

    @Override
    public void onResume() {
        super.onResume();
        //TODO test of getcursor. whats with the bundle
        db = new SqlDatabase(getActivity());
        adapter.changeCursor(getCursor());
    }

    @Override
    public void onPause() {
        super.onPause();
        db.close();
    }

    @Override
    public void onPositiveClick(Bundle bundle) {
        try {
            db.removeQuotation(bundle.getInt(ID_KEY));
            adapter.changeCursor(getCursor());
        } catch (SQLiteConstraintException e) {
            Toast.makeText(getContext(), getString(R.string.cannotDeleteQuotation), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onNegativeClick() {

    }
}
