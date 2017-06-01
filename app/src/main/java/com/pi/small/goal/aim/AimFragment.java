package com.pi.small.goal.aim;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pi.small.goal.R;


public class AimFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aim, container, false);

        View view = inflater.inflate(R.layout.fragment_aim, container, false);
        View topView = view.findViewById(R.id.view);

        int sysVersion = Integer.parseInt(Build.VERSION.SDK);
        if (sysVersion >= 19) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            topView.setVisibility(View.GONE);
        }

        right_image = (ImageView) view.findViewById(R.id.right_image);

        right_image.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_image:
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddAimActivity.class);
                startActivity(intent);
                break;
        }
    }
}
