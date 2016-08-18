package de.die.dudes.quoteinator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.die.dudes.quoteinator.database.SqlDatabase;
import de.die.dudes.quoteinator.model.Docent;
import de.die.dudes.quoteinator.model.Module;
import de.die.dudes.quoteinator.model.Quotation;

public class MainActivity extends Activity {
    private static final java.lang.String POSITION = "POSITION";
    private static final String VISIBLE_FRAGMENT = "VISIBLE_TAG";
    private String[] titles;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToogle;
    private ListView drawerList;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpInOnCreate(savedInstanceState);
    }



    private void setUpInOnCreate(Bundle savedInstanceState) {
        titles = getResources().getStringArray(R.array.titles);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToogle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.addDrawerListener(drawerToogle);

        //Drawerlist
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        if (savedInstanceState == null) {
            selectItem(0);
        } else {
            currentPosition = savedInstanceState.getInt(POSITION);
        }

        //ActionBar
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        //BackStackListener
        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager fragMan = getFragmentManager();
                Fragment fragment = fragMan.findFragmentByTag(VISIBLE_FRAGMENT);
                if (fragment instanceof TopFragment) {
                    currentPosition = 0;
                } else if (fragment instanceof DocentFragment) {
                    currentPosition = 2;
                } else if (fragment instanceof ModuleFragment) {
                    currentPosition = 3;
                } else if (fragment instanceof QuotationFragment) {
                    currentPosition = 1;
                }
                setActionBarTitle(currentPosition);
                drawerList.setItemChecked(currentPosition, true);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToogle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setActionBarTitle(int currentPosition) {
        String title = null;

        if (currentPosition == 0) {
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[currentPosition];
        }

        getActionBar().setTitle(title);
    }

    private void selectItem(int pos) {
        currentPosition = pos;
        Fragment fragment = null;

        switch (pos) {
            case 1:
                fragment = new QuotationFragment();
                break;
            case 2:
                fragment = new DocentFragment();
                break;
            case 3:
                fragment = new ModuleFragment();
                break;
            default:
                fragment = new TopFragment();
                break;
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, VISIBLE_FRAGMENT);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        //setActionbar title
        setActionBarTitle(pos);

        //Close the drawer
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToogle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToogle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, currentPosition);
    }

    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
