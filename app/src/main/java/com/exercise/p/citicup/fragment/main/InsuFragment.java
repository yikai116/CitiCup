package com.exercise.p.citicup.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.exercise.p.citicup.R;
import com.exercise.p.citicup.activity.ReadActivity;

/**
 * Created by p on 2017/8/5.
 */

public class InsuFragment extends Fragment{
    View root;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        root = inflater.inflate(R.layout.fragment_insu,container,false);
        initTop();
        return root;
    }

    private void initTop(){
        root.findViewById(R.id.insu_read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InsuFragment.this.getContext(), ReadActivity.class);
                startActivity(intent);
            }
        });
        root.findViewById(R.id.insu_choose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Choose", Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.insu_mistake).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Mistake", Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.insu_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Feedback", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
