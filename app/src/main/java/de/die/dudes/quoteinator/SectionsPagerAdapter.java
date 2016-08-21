package de.die.dudes.quoteinator;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import de.die.dudes.quoteinator.fragments.ModuleFragment;
import de.die.dudes.quoteinator.fragments.QuotationFragment;
import de.die.dudes.quoteinator.fragmentsplaceholder.DocentFragmentPlaceholder;
import de.die.dudes.quoteinator.fragmentsplaceholder.ModuleFragmentPlaceholder;
import de.die.dudes.quoteinator.fragmentsplaceholder.QuotationFragmentPlaceholder;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    Context mContext;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new QuotationFragment();
                break;
            case 1:
                fragment = new ModuleFragmentPlaceholder();
                break;
            case 2:
                fragment = new DocentFragmentPlaceholder();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.quotations);
            case 1:
                return mContext.getString(R.string.modules);
            case 2:
                return mContext.getString(R.string.docents);
        }
        return mContext.getString(R.string.app_name);
    }
}