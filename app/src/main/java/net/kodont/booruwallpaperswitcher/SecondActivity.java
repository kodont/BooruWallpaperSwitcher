package net.kodont.booruwallpaperswitcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SecondActivity
    extends AppCompatActivity
    implements TagFragment.OnListFragmentInteractionListener
{
    FragmentManager mFragmentManager;
    Fragment tagFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mFragmentManager = getSupportFragmentManager();

    }


    @Override
    public void onListFragmentInteraction(String item) {

        DeleteTagDialogFragment.
                getInstance(this).
                onCreateDialog(new Bundle()).
                show();
    }

    public void refreshFragment() {

        Set<String> tagSet = getSharedPreferences(
                MainActivity.PREFERENCES_NAME, MODE_PRIVATE).
                getStringSet(MainActivity.TAGS_KEY, new HashSet<String>());
        ArrayList<String> tagList = new ArrayList<>(tagSet);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.tag_fragment,
                TagFragment.newInstance(1, tagList));
        transaction.commit();
    }
}
