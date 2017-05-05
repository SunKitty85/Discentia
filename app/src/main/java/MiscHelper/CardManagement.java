package MiscHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.example.moltox.discentia.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import SqliteHelper.Card;
import SqliteHelper.Cards_done;
import SqliteHelper.DBHelperClass;

import static android.R.attr.id;
import static java.lang.Boolean.TRUE;

/**
 * Created by moltox on 30.04.2017.
 */

public class CardManagement {
    private static final String TAG = CardManagement.class.getName();
    private Context context;
    private View view;

    private DBHelperClass dbHelperClass;

    public CardManagement(Context context)  {
        this.context = context;
        dbHelperClass = new DBHelperClass(this.context);
    }

    public CardManagement(Context context, View view)  {
        this.context = context;
        this.view = view;
        dbHelperClass = new DBHelperClass(this.context);
    }

    private void showSnackBar(View view, String snackBarText)  {
        Snackbar.make(view, snackBarText, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void showCardListAsLog() {
        Log.v(TAG, "Get all Cards");
        List<Card> allCards = dbHelperClass.getAllCards();
        for (Card tc : allCards) {
            Log.v(TAG, "CARD: " + tc.getId() + " " + tc.getQuestion());
        }
    }

    public void showCardDoneListAsLog() {
        Log.v(TAG, "Get all DoneCards");
        List<Cards_done> allDoneCards = dbHelperClass.getAllCards_Done_Table();
        for (Cards_done cd : allDoneCards) {
            Log.v(TAG, "DoneCard: "
                    + "\nID: " + cd.getId()
                    +"\nCardID: " + cd.getCard_id()
            +   "\nTimestamp Correct: " + String.valueOf(cd.getDone_DateTime_Correct())
            + "\nTimestamp Incorrect: " + String.valueOf(cd.getDone_DateTime_Incorrect()));
        }
    }
    public Card getRandomCard() {
        Card card = new Card();
        long rand;
        long currentCardID = 0;
        Random r = new Random();
        int countCards = dbHelperClass.getCountCards();
        Log.v(TAG, "Es gibt  " + countCards + " Cards");
        if (countCards < 1) {
            showSnackBar(view, context.getString(R.string.no_cards_available));
            card.setQuestion(context.getString(R.string.no_cards_available));
        } else {
            do {
                rand = r.nextInt(1500);
                currentCardID = rand % (countCards + 1);
                Log.v(TAG, "Random ist: " + String.valueOf(rand) +
                        "\n Card ID ist: " + currentCardID);
            } while (currentCardID == 0);
            card = dbHelperClass.getCardCategorySubject(currentCardID);

        }

        return card;
    }



    private ArrayList<Integer> findunansweredCards()  {
        ArrayList<Integer> unansweredCards = new ArrayList<Integer>();


        return unansweredCards;
    }




    public void makeCardDone(View view, int currentCardID, boolean answerCorrect)  {
        if (currentCardID != -1) {
            Cards_done dc = new Cards_done(currentCardID, answerCorrect);
            long cardDoneRowId = dbHelperClass.createCard_DoneRow(dc);
            Log.v(TAG, "CardDoneRowId = " + cardDoneRowId
                    + " Card ID: " + currentCardID);
        }  else  {
            showSnackBar(view, context.getString(R.string.no_cards_chosen));
        }
    }
}
