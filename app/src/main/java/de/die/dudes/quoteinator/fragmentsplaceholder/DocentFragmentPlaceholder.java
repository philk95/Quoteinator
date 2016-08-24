package de.die.dudes.quoteinator.fragmentsplaceholder;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.die.dudes.quoteinator.R;
import de.die.dudes.quoteinator.fragments.DocentFragment;

/**
 * Created by Phil on 05.08.2016.
 */
public class DocentFragmentPlaceholder extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_docent_placeholder, container, false);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_docent_container, new DocentFragment());
        ft.commit();

        return  view;
    }
}
