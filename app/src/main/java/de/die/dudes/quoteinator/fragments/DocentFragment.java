package de.die.dudes.quoteinator.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import de.die.dudes.quoteinator.R;
import de.die.dudes.quoteinator.createactivities.CreateDocentActivity;
import de.die.dudes.quoteinator.dataadapter.DocentAdapter;
import de.die.dudes.quoteinator.dataadapter.RecyclerViewCursorAdapter;
import de.die.dudes.quoteinator.database.SqlDatabase;
import de.die.dudes.quoteinator.dialog.DeleteDialog;
import de.die.dudes.quoteinator.dialog.OptionDialog;
import de.die.dudes.quoteinator.editactvities.EditDocentActivity;

/**
 * Created by Phil on 05.08.2016.
 */
public class DocentFragment extends Fragment implements DeleteDialog.DeleteDialogListener, OptionDialog.OptionDialogListener {

    public static final String ID_KEY = "ID_KEY";
    private SqlDatabase db;
    private DocentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) inflater.inflate(R.layout.fragment_docent, container, false);
        RecyclerView recyclerView = (RecyclerView) coordinatorLayout.findViewById(R.id.docent_recycler);

        adapter = new DocentAdapter(getCursor());

        adapter.setListener(new RecyclerViewCursorAdapter.ClickListener() {
            @Override
            public void onClick(int id) {
                Fragment fragment = new QuotationFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(QuotationFragment.DOCENT, id);
                fragment.setArguments(bundle);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_docent_container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        adapter.setLongClickListener(new RecyclerViewCursorAdapter.LongClickListener() {
            @Override
            public void onClick(int id) {
                OptionDialog optionDialog = new OptionDialog();
                Bundle bundle = new Bundle();
                bundle.putInt(ID_KEY, id);
                optionDialog.setArguments(bundle);
                optionDialog.setTargetFragment(DocentFragment.this, 0);
                optionDialog.show(getFragmentManager(), "option");
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) coordinatorLayout.findViewById(R.id.addDocent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateDocentActivity.class);
                startActivity(intent);
            }
        });

        return coordinatorLayout;
    }

    private Cursor getCursor() {
        db = new SqlDatabase(getContext());
        return db.getDocentsCursor();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.swapCursor(getCursor());
    }

    @Override
    public void onPause() {
        super.onPause();
        db.close();
    }

    @Override
    public void onPositiveClick(Bundle bundle) {
        try {
            db.removeDocent(bundle.getInt(ID_KEY));
            adapter.changeCursor(getCursor());
        } catch (SQLiteConstraintException e) {
            Toast.makeText(getContext(), getString(R.string.cannotDeleteDocent), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onNegativeClick() {

    }

    @Override
    public void onDeleteClick(Bundle bundle) {
        DeleteDialog deleteDialog = new DeleteDialog();
        deleteDialog.setArguments(bundle);
        deleteDialog.setTargetFragment(DocentFragment.this, 0);
        deleteDialog.show(getFragmentManager(), "delete");
    }

    @Override
    public void onEditClick(Bundle bundle) {
        Intent intent = new Intent(getContext(), EditDocentActivity.class);
        intent.putExtra(ID_KEY, bundle.getInt(ID_KEY));
        startActivity(intent);
    }
}
