package com.jp4mobile.cmtzipevaluation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ZipEntryActivity extends AppCompatActivity {

    // Close continue button to be made invalid/valid and add listeners
    Button closeContinue;
    // Zip code entry
    EditText zipCodeEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_entry);

        closeContinue = (Button)findViewById(R.id.continueCloseButton);
        zipCodeEntry = (EditText)findViewById(R.id.zipCodeEntry);

        // Set up the button click functionality
        closeContinue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View button) {
                passAlongZipCode();
            }
        });

        // Set up the edit text listener functionality
        zipCodeEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Turn on/off the button depending on whether 5 digits are in the entry field
                boolean isValidZip = isValidZipCode(zipCodeEntry.getText().toString());

                closeContinue.setEnabled(isValidZipCode(zipCodeEntry.getText().toString()));

                if (isValidZip) {
                    closeContinue.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }
        });

    }

    // Business logic
    protected boolean isValidZipCode(String input) {
        return (input.length() == 5);
    }

    protected void passAlongZipCode() {
        String result = zipCodeEntry.getText().toString();
//        Log.d("Test", "Button pressed <" + result + ">");

        Intent intent = new Intent(this, ZipDetailActivity.class);
        intent.putExtra(ZipDetailActivity.ZIP_CODE_KEY, result);

        startActivity(intent);
    }
}
