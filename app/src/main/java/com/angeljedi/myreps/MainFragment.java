package com.angeljedi.myreps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
        loadReps(FetchRepInfoTask.SEARCH_TYPE_ZIP, Utility.getZipCode(getActivity()));
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
