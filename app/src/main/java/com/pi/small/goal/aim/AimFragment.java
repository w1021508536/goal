package com.pi.small.goal.aim;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pi.small.goal.R;
import com.pi.small.goal.aim.activity.AddAimActivity;


public class AimFragment extends Fragment implements View.OnClickListener {


    private ImageView right_image;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aim, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        right_image = (ImageView) view.findViewById(R.id.right_image);

        right_image.setOnClickListener(this);
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
