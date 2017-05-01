package com.example.moltox.discentia;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A placeholder fragment containing a simple view.
 */
public class QandA_ActivityFragment extends Fragment {

    public QandA_ActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qanda_activity, container, false);
        return view;
    }

/*
    private void test()  {
        String test = DBHelperClass.lcs_db.DATABASE_NAME;
    }
*/


}
