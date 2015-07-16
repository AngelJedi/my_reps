package com.angeljedi.myreps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    public static final String EXTRA_REP = "extra_rep";

    private Rep mRep;

    private TextView mNameView;
    private TextView mPartyView;
    private TextView mStateView;
    private TextView mDistrictView;
    private TextView mPhoneView;
    private TextView mOfficeView;
    private TextView mWebsiteView;


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
        mNameView = (TextView) view.findViewById(R.id.detail_name);
        mPartyView = (TextView) view.findViewById(R.id.detail_party);
        mStateView = (TextView) view.findViewById(R.id.detail_state);
        mDistrictView = (TextView) view.findViewById(R.id.detail_district);
        mPhoneView = (TextView) view.findViewById(R.id.detail_phone);
        mOfficeView = (TextView) view.findViewById(R.id.detail_office);
        mWebsiteView = (TextView) view.findViewById(R.id.detail_website);

        loadRep();

        return view;
    }

    protected void loadRep() {
        if (mRep != null) {
            mNameView.setText(mRep.getName());
            mPartyView.setText(mRep.getParty());
            mStateView.setText(mRep.getState());
            mDistrictView.setText(mRep.getDistrict());
            mPhoneView.setText(mRep.getPhone());
            mOfficeView.setText(mRep.getOffice());
            mWebsiteView.setText(mRep.getWebsite());
        }
    }
}
