package com.example.moltox.discentia;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import Interfaces.TabReceiver;


/**
 * A simple {@link Fragment} subclass.
 */
public class QandA_ActivityFragmentBackside extends Fragment implements QandA_activity.OnStringSend {
    private static final String TAG = QandA_ActivityFragmentBackside.class.getName();
    private View view;
    private TextView tv;
    private QandA_activity mMain;


    public QandA_ActivityFragmentBackside() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_qand_a__activity_fragment_backside, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        tv = (TextView) view.findViewById(R.id.tv_backsidefrag_answer);

    }

    // Called when the fragment's activity has been created
    // and this fragment's view hierarchy instantiated
    @Override public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        mMain = (QandA_activity) getActivity();
        mMain.setOnStringSend(this);
    }


    @Override
    public void submit(String s) {
        tv.setText(s);
        
    }
}
