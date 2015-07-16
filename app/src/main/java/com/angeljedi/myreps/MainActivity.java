package com.angeljedi.myreps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MainFragment.Callback {

    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    private boolean mTwoPaneLayout;
    private String mZipCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mZipCode = Utility.getZipCode(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.rep_detail_container) != null) {
            // two pane layout is present only in large-screen layouts (layout/sw-600dp)
            mTwoPaneLayout = true;
            // add the detail view to the activity when it is a two pane layout.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.rep_detail_container, new DetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPaneLayout = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String savedZipCode = Utility.getZipCode(this);
        if (savedZipCode != null && !savedZipCode.equals(mZipCode)) {
            MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);
            if (fragment != null) {
                fragment.onZipChanged();
            }
        }
        mZipCode = savedZipCode;
    }


    @Override
    public void onItemSelected(Rep rep) {
        if (mTwoPaneLayout) {
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.EXTRA_REP, rep);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.rep_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailFragment.EXTRA_REP, rep);
            startActivity(intent);
        }
    }
}
