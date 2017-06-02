package com.pi.small.goal.search;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.pi.small.goal.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        View topView = view.findViewById(R.id.view);

        int sysVersion = Integer.parseInt(Build.VERSION.SDK);
        if (sysVersion >= 19) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            topView.setVisibility(View.GONE);
        }

        return view;
    }

}
