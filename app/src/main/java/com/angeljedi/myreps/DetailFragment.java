package com.angeljedi.myreps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    public static final String EXTRA_REP = "extra_rep";

    private Rep mRep;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        Bundle bundle = getActivity().getIntent().getExtras();
//        Rep rep = bundle.getParcelable(EXTRA_REP);

        Bundle args = getArguments();
        if (args != null) {
            mRep = args.getParcelable(EXTRA_REP);
        }

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        if (mRep != null) {
            // Title information
            ImageView partyView = (ImageView) view.findViewById(R.id.party_indicator);
            Utility.setPartyColor(partyView, Utility.getPartyColor(getActivity(), mRep.getParty()));
            TextView nameView = (TextView) view.findViewById(R.id.detail_name);
            nameView.setText(mRep.getName());
            TextView state = (TextView) view.findViewById(R.id.detail_state);
            state.setText(String.format(getActivity().getString(R.string.format_state), mRep.getParty(), mRep.getState()));

            // Other data fields
            TextView districtView = (TextView) view.findViewById(R.id.detail_district);
            districtView.setText(String.format(getActivity().getString(R.string.format_district), mRep.getDistrict()));

            TextView phoneView = (TextView) view.findViewById(R.id.detail_phone);
            phoneView.setText(mRep.getPhone());

            TextView officeView = (TextView) view.findViewById(R.id.detail_office);
            officeView.setText(mRep.getOffice());

            TextView websiteView = (TextView) view.findViewById(R.id.detail_website);
            websiteView.setText(mRep.getWebsite());
        }

        return view;
    }
}
