package com.example.samuelh.memoryleak;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EntryActivity extends AppCompatActivity
        implements BrowserFragment.BrowserItemSelectedListener {

    EntryFragment entryFragment;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            openBrowser();
        });

        entryFragment = new EntryFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, entryFragment);
        transaction.commit();

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Fragment fragment = getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_container);
            if (fragment == null) {
                return;
            }
            Log.d("EntryActivity", "addOnBackStackChangedListener, "
                    + fragment.getClass().getSimpleName());
            checkAndSetFab(fragment);
        });
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
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openBrowser() {
        BrowserFragment fragment = new BrowserFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void checkAndSetFab(Fragment fragment) {
        if (fragment instanceof EntryFragment) {
            fab.show();
        } else {
            fab.hide();
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof BrowserFragment) {
            ((BrowserFragment) fragment).setBrowserItemSelectedListener(this);
        }
    }

    @Override
    public void onBrowserItemSelected(BrowserPresenter.ItemData data) {
        Log.d("EntryActivity", "onBrowserItemSelected, " + data.itemName);
        entryFragment.addBrowserData(data);
    }
}
