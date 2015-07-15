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
public class DetailActivityFragment extends Fragment {

    public static final String EXTRA_REP = "extra_rep";

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        Rep rep = (Rep) bundle.getSerializable(EXTRA_REP);

        if (rep != null) {
            TextView tvName = (TextView) view.findViewById(R.id.detail_name);
            tvName.setText(rep.getName());

            TextView tvParty = (TextView) view.findViewById(R.id.detail_party);
            tvParty.setText(rep.getParty());

            TextView tvState = (TextView) view.findViewById(R.id.detail_state);
            tvState.setText(rep.getState());

            TextView tvDistrict = (TextView) view.findViewById(R.id.detail_district);
            tvDistrict.setText(rep.getDistrict());

            TextView tvPhone = (TextView) view.findViewById(R.id.detail_phone);
            tvPhone.setText(rep.getPhone());

            TextView tvOffice = (TextView) view.findViewById(R.id.detail_office);
            tvOffice.setText(rep.getOffice());

            TextView tvWebsite = (TextView) view.findViewById(R.id.detail_website);
            tvWebsite.setText(rep.getWebsite());
        }

        return view;
    }
}
