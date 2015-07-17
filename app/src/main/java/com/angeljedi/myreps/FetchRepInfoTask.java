package com.angeljedi.myreps;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchRepInfoTask extends AsyncTask<String, Void, Void> {

    public static final String SEARCH_TYPE_ZIP = "search_zip";
    public static final String SEARCH_TYPE_STATE = "search_state";

    private final String LOG_TAG = FetchRepInfoTask.class.getSimpleName();

    private final String BASE_URL = "http://whoismyrepresentative.com/";
    private final String OUTPUT_PARAM = "output";
    private final String OUTPUT_VALUE = "json";

    private Activity mActivity;
    private List<Rep> mRepList;

    public FetchRepInfoTask(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    protected Void doInBackground(String... params) {
        mRepList = new ArrayList<>();

        String searchType = params[0];
        String searchValue = params[1];

        if (searchType.isEmpty() || searchValue.isEmpty()) {
            return null;
        }

        populateRepListFromApi(searchType, searchValue);

        return null;
    }

    @Override
    protected void onPostExecute(Void voidObject) {
        RepAdapter adapter = new RepAdapter(mActivity, R.layout.list_item_rep, mRepList);
        adapter.notifyDataSetChanged();
        ListView listView = (ListView) mActivity.findViewById(R.id.main_list_view);
        listView.setAdapter(adapter);
    }

    /**
     * Populates the list of reps based on the search type and search value
     * @param searchType the type of search to perform through the api
     * @param searchValue the value to use for the search (zip, state, or last name)
     * @throws IOException
     * @throws JSONException
     */
    private void populateRepListFromApi(String searchType, String searchValue) {


        URL url;
            switch (searchType) {
                case SEARCH_TYPE_STATE: {
                    break;
                }
                default: {
                    url = getAllMembersSearchUrl(searchValue);
                    callApi(url);
                }
            }

    }

    /**
     * Makes a call to the api to retrieve a list of reps and populates them in the rep list
     * @param url the url to use for the call
     */
    private void callApi(URL url) {
        if (url == null) {
            return;
        }

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Read the input stream into a String
            InputStream stream = connection.getInputStream();
            StringBuilder builder = new StringBuilder();
            if (stream == null) {
                return;
            }
            reader = new BufferedReader(new InputStreamReader(stream));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            if (builder.length() == 0) {
                return;
            }
            String repsJsonString = builder.toString();

            JSONObject jsonObject = new JSONObject(repsJsonString);
            getRepsFromJson(jsonObject);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error retrieving rep list", e);
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
    }

    /**
     * Takes a jsonObject and populates mRepList with the data it stores.
     * @param jsonObject the jsonObject retrieved from the api
     */
    private void getRepsFromJson(JSONObject jsonObject) throws JSONException {
        JSONArray array = jsonObject.getJSONArray("results");
        if (array == null) {
            return;
        }

        for (int i = 0, size = array.length(); i < size; i++) {
            JSONObject object = array.getJSONObject(i);
            Rep rep = new Rep();
            rep.setName(object.getString("name"));
            rep.setParty(object.getString("party"));
            rep.setState(object.getString("state"));
            rep.setDistrict(object.getString("district"));
            rep.setPhone(object.getString("phone"));
            rep.setOffice(object.getString("office"));
            rep.setWebsite(object.getString("link"));
            mRepList.add(rep);
        }
    }

    /**
     * Creates a url for retrieving representative and senator information by zip code.
     * @return the url to retrieve all members for the stored zip code
     * @throws IOException if the url is malformed
     */
    private URL getAllMembersSearchUrl(String zipCode) {
        try {
            final String PATH = "getall_mems.php";
            final String ZIP_PARAM = "zip";

            Uri uri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(PATH)
                    .appendQueryParameter(ZIP_PARAM, zipCode)
                    .appendQueryParameter(OUTPUT_PARAM, OUTPUT_VALUE)
                    .build();

            return new URL(uri.toString());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error retrieving rep list", e);
            e.printStackTrace();
        }
        return null;
    }
}
