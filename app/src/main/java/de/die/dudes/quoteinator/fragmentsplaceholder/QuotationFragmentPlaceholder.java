package de.die.dudes.quoteinator.fragmentsplaceholder;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.die.dudes.quoteinator.R;
import de.die.dudes.quoteinator.dataadapter.QuotationAdapter;
import de.die.dudes.quoteinator.dataadapter.RecyclerViewCursorAdapter;
import de.die.dudes.quoteinator.database.SqlDatabase;
import de.die.dudes.quoteinator.fragments.DocentFragment;
import de.die.dudes.quoteinator.fragments.QuotationFragment;

/**
 * Created by Phil on 05.08.2016.
 */
public class QuotationFragmentPlaceholder extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_quotation_placeholder, container, false);
        
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_quotation_container, new QuotationFragment());
        ft.commit();

        return view;
    }


}
