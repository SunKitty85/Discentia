package com.example.moltox.discentia;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

import MiscHelper.CardManagement;
import SqliteHelper.Card;
import SqliteHelper.Cards_done;
import SqliteHelper.DBHelperClass;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class QandA_activity extends AppCompatActivity {
    private static final String TAG = QandA_activity.class.getName();

    CardManagement cardManagement;
    int currentCardID;
    TextView tv_qanda_card_id;
    TextView tv_qanda_card_question;
    TextView tv_qanda_card_answer1;

    Button btn_newCard;
    Button btn_showAnswer;
    Button btn_AnswerCorrect;
    Button btn_AnswerIncorrect;
    Button btn_dumpQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qanda_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cardManagement = new CardManagement(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                cardManagement.showCardListAsLog();
                cardManagement.showCardDoneListAsLog();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_qanda_card_id = (TextView) findViewById(R.id.tv_qanda_card_id);
        tv_qanda_card_question = (TextView) findViewById(R.id.tv_qanda_card_question);
        tv_qanda_card_answer1 = (TextView) findViewById(R.id.tv_qanda_card_answer1);


        btn_newCard = (Button) findViewById(R.id.btn_newCard);
        btn_newCard.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               showCard(cardManagement.getRandomCard(v));
                                               }

                                       }
        );

        btn_showAnswer = (Button) findViewById(R.id.btn_showAnswer);
        btn_showAnswer.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  Log.v(TAG, "Show Answer Button");
                                                  tv_qanda_card_answer1.setVisibility(View.VISIBLE);
                                              }
                                          }
        );

        btn_AnswerCorrect = (Button) findViewById(R.id.btn_answer_correct);
        btn_AnswerCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v(TAG, "Answer Correct");
                cardManagement.makeCardDone(v,currentCardID,true);


            }
        });

        btn_AnswerIncorrect = (Button) findViewById(R.id.btn_answer_not_correct);
        btn_AnswerIncorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Extract Cards done Methods to CardManagement Class
                Log.v(TAG, "Answer Incorrect");
                cardManagement.makeCardDone(v, currentCardID,false);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void showCard(Card card) {

        tv_qanda_card_id.setText("Card ID: " + card.getId()
                + "\nRelease Date: " + card.getReleaseDate()
                + "\nKategorie ID: " + card.getCategory_id());

        tv_qanda_card_question.setText(getString(R.string.tv_qanda_frag_card_question) + " " + card.getQuestion());
        tv_qanda_card_answer1.setVisibility(View.INVISIBLE);
        tv_qanda_card_answer1.setText(getString(R.string.tv_qanda_frag_card_answer1) + "\n\n\n " + card.getAnswer1());
        currentCardID = card.getId();
    }
}
