package com.jp4mobile.cmtzipevaluation;

import android.content.Context;
import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jp on 10/22/17.
 */

public class ZipCode {
    // Instance variables
    final String state;
    final Integer zipCode;

    // Keys to parse the JSON
    static final String STATE_KEY = "State";
    static final String ZIP_CODE_KEY = "ZipCode";

    private static InputStream retrieveJSONStream(Context context) {
        return context.getResources().openRawResource(R.raw.cmt_ct_ma);
    }

    static List<ZipCode> zipCodes(Context context) throws IOException {
        return readJSONStream(retrieveJSONStream(context));
    }

    static List<ZipCode> readJSONStream(InputStream stream) throws IOException {
        JsonReader jsonReader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
        try {
            return parseZipCodeArray(jsonReader);
        } finally {
            jsonReader.close();
        }
    }

    static List<ZipCode> parseZipCodeArray(JsonReader jsonReader) throws IOException {
        List<ZipCode> zipCodes = new ArrayList<ZipCode>();

        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            zipCodes.add(parseZipCode(jsonReader));
        }
        jsonReader.endArray();
        return zipCodes;
    }

    static ZipCode parseZipCode(JsonReader jsonReader) throws IOException {
        String state = "";
        Integer zip = -1;

        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (name.equals(STATE_KEY)) {
                state = jsonReader.nextString();
            } else if (name.equals(ZIP_CODE_KEY)) {
                String zipString = jsonReader.nextString();
                zip = Integer.parseInt(zipString);
            } else {
                jsonReader.skipValue();
            }
        }

        jsonReader.endObject();

        return new ZipCode(state, zip);
    }

    // Initializer
    ZipCode(String state, Integer zipCode) {
        this.state = state;
        this.zipCode = zipCode;
    }

    public boolean isZipCode(String zip) {
        return isZipCode(Integer.parseInt(zip));
    }

    public boolean isZipCode(Integer zip) {
        return zip.equals(this.zipCode);
    }

    public String toString() {
        return String.format("(%s)[%05d]",
                this.state, this.zipCode);
    }
}
