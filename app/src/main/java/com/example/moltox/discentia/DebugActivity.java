package com.example.moltox.discentia;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import MiscHelper.StringUtils;
import SqliteHelper.DBHelperClass;

public class DebugActivity extends AppCompatActivity {
    private final static String TAG = DebugActivity.class.getName();

    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv1 = (TextView) findViewById(R.id.debug_tv1);
        fillTextViews();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean debugSwitchPref = sharedPref.getBoolean("key_debug_switch", false);
        String apiKey = sharedPref.getString("pref_api_key", "");
        StringUtils stringUtils = new StringUtils();
        String secureApiKey = stringUtils.md5(apiKey);
        Log.v(TAG, "Debug Switch: " + String.valueOf(debugSwitchPref)
                + "\nPref Api Key: " + apiKey
                + "\nSecure Api Key: " + secureApiKey);
    }

    private void fillTextViews() {
        DBHelperClass db = new DBHelperClass(getApplicationContext());
        /*
        String query = "SELECT * FROM " + db.CARDS_DONE_TABLE_NAME
                + " WHERE " + db.CARDS_DONE_TABLE_NAME + "." + db.COL_CARDS_DONE_DATETIME_INCORRECT + "> 0"
                + " AND " + db.COL_CARDS_DONE_CARD_ID
                + " NOT IN (SELECT "
                + db.COL_CARDS_DONE_CARD_ID + " FROM " + db.CARDS_DONE_TABLE_NAME
                + " WHERE " + db.CARDS_DONE_TABLE_NAME + "." + db.COL_CARDS_DONE_DATETIME_CORRECT + "> 0)"
                + " ORDER BY " + db.CARDS_DONE_TABLE_NAME + "." + db.COL_CARDS_DONE_DATETIME_CORRECT + " DESC LIMIT 0,5";
*/
        /*
        String query =  "SELECT * FROM " + db.CARDS_CATEGORY_TABLE_NAME
                + " WHERE " + db.CARDS_CATEGORY_TABLE_NAME + "." + db.COL_CARDS_CATEGORY_CARDID + "=" + "1"
                + " AND " + db.CARDS_CATEGORY_TABLE_NAME + "." + db.COL_CARDS_CATEGORY_CATEGORYID + "=" + "1";
        */
        // String query = "SELECT name FROM sqlite_master WHERE type='table'";

        String query2 = "SELECT tb_cards.*,tb_category.category,tb_subject.subject" +
                " FROM tb_cards,tb_cards_category,tb_category,tb_subject,tb_cards_subject " +
                "WHERE tb_cards._id = tb_cards_category.card_id " +
                "AND tb_cards._id = tb_cards_subject.card_id " +
                "AND tb_cards_category.category_id = 1 " +
                "AND tb_cards_category.category_id = tb_category._id " +
                "AND tb_cards_subject.subject_id = tb_subject._id";
/*
        String query = "CREATE VIEW CardCategorySubject AS SELECT " + DBHelperClass.CARDS_TABLE_NAME + ".*," + DBHelperClass.CATEGORY_TABLE_NAME + "." + DBHelperClass.COL_CATEGORY_CATEGORY + "," + DBHelperClass.SUBJECT_TABLE_NAME + "." + DBHelperClass.COL_TBSUBJECT_SUBJECT +
                " FROM " + DBHelperClass.CARDS_TABLE_NAME + "," + DBHelperClass.CARDS_CATEGORY_TABLE_NAME + "," + DBHelperClass.CATEGORY_TABLE_NAME + "," + DBHelperClass.SUBJECT_TABLE_NAME + "," + DBHelperClass.CARDS_SUBJECT_TABLE_NAME +
                " WHERE " + DBHelperClass.CARDS_TABLE_NAME + "." + DBHelperClass.COL_CARDS_CARD_ID + " = " + DBHelperClass.CARDS_CATEGORY_TABLE_NAME + "." + DBHelperClass.COL_CARDS_CATEGORY_CARDID +
                " AND " + DBHelperClass.CARDS_TABLE_NAME + "." + DBHelperClass.COL_CARDS_CARD_ID + "=" + DBHelperClass.CARDS_SUBJECT_TABLE_NAME + "." + DBHelperClass.COL_CARDS_SUBJECT_CARDID +
                " AND " + DBHelperClass.CARDS_CATEGORY_TABLE_NAME + "." + DBHelperClass.COL_CARDS_CATEGORY_CATEGORYID + "=" + DBHelperClass.CATEGORY_TABLE_NAME + "." + DBHelperClass.COL_COMMON_ID +
                " AND " + DBHelperClass.CARDS_SUBJECT_TABLE_NAME + "." + DBHelperClass.COL_CARDS_SUBJECT_SUBJECTID + "=" + DBHelperClass.SUBJECT_TABLE_NAME + "." + DBHelperClass.COL_COMMON_ID;
*/

        String query = "SELECT * FROM CardCategorySubject";
        String cursorString = db.dumpQuerytoString(query);
        //        String finalString = cursorString;
        tv1.setText(cursorString);

/*
                + " WHERE " + db.CARDS_TABLE_NAME + "." + db.COL_COMMON_ID + " = " + db.COL_CARDS_DONE_CARD_ID
                + " ORDER BY " + db.COL_CARDS_DONE_CARD_ID

        String query = "SELECT * FROM " + db.CARDS_TABLE_NAME
                + " LEFT JOIN " + db.CARDS_DONE_TABLE_NAME
                + " ON " + db.CARDS_DONE_TABLE_NAME + "." + db.COL_CARDS_DONE_CARD_ID + " = " + db.CARDS_TABLE_NAME + "." + db.COL_COMMON_ID

                // + "," + db.CARDS_TABLE_NAME


                + " WHERE " + db.COL_CARDS_DONE_DATETIME_INCORRECT + " IS NULL"
                + " AND " + db.COL_CARDS_DONE_DATETIME_CORRECT + " IS NULL"
                *
                + db.COL_CARDS_DONE_CARD_ID
                + " OR " + " NOT IN "


                + " ORDER BY " + db.CARDS_DONE_TABLE_NAME + "." + db.COL_CARDS_DONE_DATETIME_CORRECT
                + "," + db.CARDS_DONE_TABLE_NAME + "." + db.COL_CARDS_DONE_DATETIME_INCORRECT ;
  */


    }
}
