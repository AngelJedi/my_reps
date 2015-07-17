package com.angeljedi.myreps;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    private static final String SELECTED_KEY = "selected_position";

    private RepAdapter mRepAdapter;
    private ListView mListView;
    private int mPosition = ListView.INVALID_POSITION;
    private String mZipCode;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mRepAdapter = new RepAdapter(getActivity(), 0, new ArrayList<Rep>());

        mListView = (ListView) view.findViewById(R.id.main_list_view);
        mListView.setAdapter(mRepAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Rep rep = (Rep) parent.getItemAtPosition(position);
                ((Callback) getActivity()).onItemSelected(rep);
                mPosition = position;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mZipCode = Utility.getZipCode(getActivity());
        if (mZipCode.equals(getResources().getString(R.string.pref_zip_default))) {
            showInputDialog();
        } else {
            loadReps(FetchRepInfoTask.SEARCH_TYPE_ZIP, mZipCode);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    private void loadReps(String searchType, String value) {
        new FetchRepInfoTask(getActivity()).execute(searchType, value);
    }

    public void onZipChanged() {
        loadReps(FetchRepInfoTask.SEARCH_TYPE_ZIP, Utility.getZipCode(getActivity()));
    }

    protected void showInputDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.dialog_edit_text);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mZipCode = editText.getText().toString();
                        updateZipPreference(mZipCode);
                        loadReps(FetchRepInfoTask.SEARCH_TYPE_ZIP, mZipCode);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void updateZipPreference(String zipCode) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(getResources().getString(R.string.pref_zip_key), zipCode);

        editor.commit();
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(Rep rep);
    }
}
