package de.die.dudes.quoteinator.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.die.dudes.quoteinator.R;
import de.die.dudes.quoteinator.dataadapter.DocentAdapter;
import de.die.dudes.quoteinator.dataadapter.RecyclerViewCursorAdapter;
import de.die.dudes.quoteinator.database.SqlDatabase;

/**
 * Created by Phil on 05.08.2016.
 */
public class DocentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_docent, container, false);

        SqlDatabase db = new SqlDatabase(getContext());

        DocentAdapter adapter = new DocentAdapter(db.getDocentsCursor());

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }
}
