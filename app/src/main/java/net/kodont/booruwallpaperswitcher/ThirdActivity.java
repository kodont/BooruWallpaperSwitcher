package net.kodont.booruwallpaperswitcher;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class ThirdActivity extends AppCompatActivity {

    private TextView tagListTextView;
    private EditText addTagEditText;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        mSharedPreferences = getSharedPreferences(MainActivity.PREFERENCES_NAME,
                MODE_PRIVATE);
        tagListTextView = findViewById(R.id.simple_tag_list_text_view);
        addTagEditText = findViewById(R.id.simple_add_tag_edit_text);

        Set<String> tagSet = mSharedPreferences.getStringSet(MainActivity.TAGS_KEY,
                null);
        if (tagSet == null)
            tagSet = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        for (String s: tagSet) {
            sb.append(s);
            sb.append("\n");
        }
        tagListTextView.setText(sb);
    }

    public void addTag(View view) {

        String newTag = addTagEditText.getText().toString();
        if (newTag.isEmpty()) {
            Toast.makeText(this, "Empty field!", Toast.LENGTH_SHORT).show();
            return;
        }
        Set<String> tagSet = mSharedPreferences.getStringSet(MainActivity.TAGS_KEY,
                new HashSet<String>());
        tagSet.add(newTag);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putStringSet(MainActivity.TAGS_KEY, tagSet);
        editor.apply();

        StringBuilder sb = new StringBuilder();
        for (String s: tagSet) {
            sb.append(s);
            sb.append("\n");
        }
        tagListTextView.setText(sb);
        addTagEditText.setText("");
    }

    public void clearTags(View view) {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putStringSet(MainActivity.TAGS_KEY, new HashSet<String>());
        editor.apply();

        tagListTextView.setText("");
        addTagEditText.setText("");
    }
}
