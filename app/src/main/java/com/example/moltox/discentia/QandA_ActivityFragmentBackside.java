package com.example.moltox.discentia;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import MiscHelper.CardManagement;
import SqliteHelper.Card;


/**
 * A simple {@link Fragment} subclass.
 */
public class QandA_ActivityFragmentBackside extends Fragment implements QandA_activity.OnCardSend {
    private static final String TAG = QandA_ActivityFragmentBackside.class.getName();
    private View view;
    private TextView tv;

    private Button btn_AnswerCorrect;
    private Button btn_AnswerIncorrect;
    private QandA_activity mMain;
    private CardManagement cardManagement;
    int currentCardID;

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

        return inflater.inflate(R.layout.fragment_qanda__activity_backside, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        tv = (TextView) view.findViewById(R.id.tv_backsidefrag_answer);

        btn_AnswerCorrect = (Button) view.findViewById(R.id.btn_answer_correct);
        btn_AnswerCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v(TAG, "Answer Correct");
                cardManagement.makeCardDone(v,currentCardID,true);


            }
        });

        btn_AnswerIncorrect = (Button) view.findViewById(R.id.btn_answer_not_correct);
        btn_AnswerIncorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Extract Cards done Methods to CardManagement Class
                Log.v(TAG, "Answer Incorrect");
                cardManagement.makeCardDone(v, currentCardID,false);

            }
        });

    }

    // Called when the fragment's activity has been created
    // and this fragment's view hierarchy instantiated
    @Override public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        mMain = (QandA_activity) getActivity();
        mMain.setOnCardSend(this);
    }




    @Override
    public void submit(Card card) {
        tv.setText(card.getAnswer1());
        currentCardID = card.getCardId();
    }
}
