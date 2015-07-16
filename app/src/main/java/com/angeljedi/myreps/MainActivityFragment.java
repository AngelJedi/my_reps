package com.angeljedi.myreps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) view.findViewById(R.id.main_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Rep rep = (Rep) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivityFragment.EXTRA_REP, rep);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadReps(FetchRepInfoTask.SEARCH_TYPE_ZIP, Utility.getZipCode(getActivity()));
    }

    private void loadReps(String searchType, String value) {
        new FetchRepInfoTask(getActivity()).execute(searchType, value);
    }

    public void onZipChanged() {
        loadReps(FetchRepInfoTask.SEARCH_TYPE_ZIP, Utility.getZipCode(getActivity()));
    }
}
