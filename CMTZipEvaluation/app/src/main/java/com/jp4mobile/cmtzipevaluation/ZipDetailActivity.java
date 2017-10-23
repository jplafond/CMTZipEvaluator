package com.jp4mobile.cmtzipevaluation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class ZipDetailActivity extends AppCompatActivity {

    public static final String ZIP_CODE_KEY = "com.jp4mobile.cmtzipevaluation.ZIP_CODE";

    // Close continue button to be made invalid/valid and add listeners
    Button closeContinue;
    // Zip code entry
    EditText zipCodeEntry;
    // Results text
    TextView zipCodeResults;

    List<ZipCode> zipCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip_entry);

        closeContinue = (Button)findViewById(R.id.continueCloseButton);
        zipCodeEntry = (EditText)findViewById(R.id.zipCodeEntry);
        zipCodeResults = (TextView)findViewById(R.id.resultText);

        Intent intent = getIntent();
        String zipCode = intent.getStringExtra(ZIP_CODE_KEY);

        zipCodeEntry.setText(zipCode);
        zipCodeEntry.setEnabled(false);


        try {
            // Ideally this should be asynchronous and on a background thread.
            zipCodes = ZipCode.zipCodes(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isValidCTMAZipCode(zipCode)) {
            presentValidResults();
        } else {
            presentInvalidResults();
        }
    }

    // Business logic
    protected boolean isValidCTMAZipCode(String input) {
        if (zipCodes == null || zipCodes.isEmpty()) {
            return false;
        }
        for (ZipCode zipCode : zipCodes) {
            if (zipCode.isZipCode(input)) {
                return true;
            }
        }
        return false;
    }

    protected void presentInvalidResults() {
        zipCodeResults.setVisibility(View.VISIBLE);
        zipCodeResults.setTextColor(getResources().getColor(R.color.invalidText));
        zipCodeResults.setText(R.string.unavailableInRegion);
        closeContinue.setText(R.string.closeButtonTitle);
    }

    protected void presentValidResults() {
        zipCodeResults.setVisibility(View.VISIBLE);
        zipCodeResults.setTextColor(getResources().getColor(R.color.validText));
        zipCodeResults.setText(R.string.availableInRegion);
        closeContinue.setText(R.string.continueButtonTitle);
    }

}
