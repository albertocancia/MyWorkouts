package com.example.bodybuilding;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

public class NewItemActivity extends AppCompatActivity {
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        editText = findViewById(R.id.edittext);

        // set the Home button
        Toolbar myToolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // goto back activity from here
                Intent resultIntent = new Intent();
                resultIntent.putExtra("ITEM_TASK", editText.getText().toString());
                setResult(RESULT_OK, resultIntent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
