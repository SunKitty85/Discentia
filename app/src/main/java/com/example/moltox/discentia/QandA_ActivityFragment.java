package com.example.moltox.discentia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import Interfaces.TabCommunicator;
import MiscHelper.CardManagement;
import SqliteHelper.Card;


/**
 * A placeholder fragment containing a simple view.
 */
public class QandA_ActivityFragment extends Fragment {
    private static final String TAG = QandA_ActivityFragment.class.getName();

    int currentCardID;
    CardManagement cardManagement;
    TextView tv_qanda_card_id;
    TextView tv_qanda_card_question;
    TextView tv_qanda_card_answer1;

    Button btn_newCard;
    Button btn_showAnswer;
    Button btn_AnswerCorrect;
    Button btn_AnswerIncorrect;
    Button btn_dumpQuery;

    public QandA_ActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qanda_activity, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardManagement = new CardManagement(getActivity(),view);
        tv_qanda_card_id = (TextView) view.findViewById(R.id.tv_qanda_card_id);
        tv_qanda_card_question = (TextView) view.findViewById(R.id.tv_qanda_card_question);
        tv_qanda_card_answer1 = (TextView) view.findViewById(R.id.tv_qanda_card_answer1);


        btn_newCard = (Button) view.findViewById(R.id.btn_newCard);
        btn_newCard.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                showCard(cardManagement.getRandomCard(v));
                                                Log.v(TAG, "onClick Karte ziehen");
                                           }

                                       }
        );

        btn_showAnswer = (Button) view.findViewById(R.id.btn_showAnswer);
        btn_showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v(TAG, "GetActivity String " + getActivity().toString());
            }
        });

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

    private void showCard(Card card) {

        tv_qanda_card_id.setText("Card ID: " + card.getId()
                + "\nRelease Date: " + card.getReleaseDate()
                + "\nKategorie ID: " + card.getCategory_id());

        tv_qanda_card_question.setText(getString(R.string.tv_qanda_frag_card_question) + " " + card.getQuestion());
        ((TabCommunicator) getActivity()).sendString(getString(R.string.tv_qanda_frag_card_answer1) + "\n\n\n " + card.getAnswer1());

        currentCardID = card.getId();
    }
}
