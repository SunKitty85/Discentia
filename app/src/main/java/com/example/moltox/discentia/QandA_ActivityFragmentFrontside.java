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
public class QandA_ActivityFragmentFrontside extends Fragment {
    private static final String TAG = QandA_ActivityFragmentFrontside.class.getName();

    int currentCardID;
    CardManagement cardManagement;
    TextView tv_qanda_card_id;
    TextView tv_qanda_card_question;
    TextView tv_qanda_card_answer1;
    Button btn_newCard;


    public QandA_ActivityFragmentFrontside() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qanda_activity_frontside, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardManagement = new CardManagement(getActivity(), view);
        tv_qanda_card_id = (TextView) view.findViewById(R.id.tv_qanda_card_id);
        tv_qanda_card_question = (TextView) view.findViewById(R.id.tv_qanda_card_question);
        tv_qanda_card_answer1 = (TextView) view.findViewById(R.id.tv_qanda_card_answer1);


        btn_newCard = (Button) view.findViewById(R.id.btn_newCard);
        btn_newCard.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               showCard(cardManagement.getRandomCard());
                                               Log.v(TAG, "onClick Karte ziehen");
                                           }

                                       }
        );
        showCard(cardManagement.getRandomCard());
    }


    private void showCard(Card card) {

        tv_qanda_card_id.setText("ID: " + card.getId()
                + "\nRelease Date: " + card.getReleaseDate()
                + "\nKategorie ID: " + card.getCategory_id());

        tv_qanda_card_question.setText(getString(R.string.tv_qanda_frag_card_question) + " " + card.getQuestion());
        ((TabCommunicator) getActivity()).sendCard(card);
        currentCardID = card.getId();
    }
}
