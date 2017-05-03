package SqliteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moltox on 04.03.2017.
 */

public class DBHelperClass extends SQLiteOpenHelper {
    private static final String TAG = DBHelperClass.class.getName();
    private static final int DATABASE_VERSION = 42;
    public static final String DATABASE_NAME = "DISCENTIA.db";

    // Table Names
    public static final String CARDS_TABLE_NAME = "tb_cards";
    public static final String CARDS_DONE_TABLE_NAME = "tb_cards_done";
    public static final String PULLS_TABLE_NAME = "tb_pulls";
    public static final String CATEGORY_TABLE_NAME = "tb_category";
    public static final String CARDS_CATEGORY_TABLE_NAME = "tb_cards_category";
    public static final String SUBJECT_TABLE_NAME = "tb_subject";
    public static final String CARDS_SUBJECT_TABLE_NAME = "tb_cards_subject";

    // Common Column Names
    public static final String COL_COMMON_ID = "_id";

    // Columns cards Table
    public static final String COL_CARDS_CARD_ID = "card_id";
    public static final String COL_CARDS_QUESTION = "question";
    public static final String COL_CARDS_ANSWER01 = "answer1";
    public static final String COL_CARDS_ANSWER02 = "answer2";
    public static final String COL_CARDS_ANSWER03 = "answer3";
    public static final String COL_CARDS_ANSWER04 = "answer4";
    public static final String COL_CARDS_RELEASE_DATE = "releaseDate";
    public static final String COL_CARDS_IMG_PATH = "img_path";

    // Columns cards_done table
    public static final String COL_CARDS_DONE_CARD_ID = "card_id";
    public static final String COL_CARDS_DONE_DATETIME_CORRECT = "datetime_correct";
    public static final String COL_CARDS_DONE_DATETIME_INCORRECT = "datetime_incorrect";

    // Columns pulls table
    public static final String COL_PULLS_POLLDATETIME = "polldatetime";

    // Columns category table
    public static final String COL_CATEGORY_CATEGORY = "category";
    public static final String COL_CATEGORY_PICFILENAME = "img_path";

    // Cards_Category table
    public static final String COL_CARDS_CATEGORY_CARDID = "card_id";
    public static final String COL_CARDS_CATEGORY_CATEGORYID = "category_id";

    // Subject table
    public static final String COL_TBSUBJECT_SUBJECT = "subject";
    public static final String COL_TBSUBJECT_IMGPATH = "img_path";

    // Cards_Subject table
    public static final String COL_CARDS_SUBJECT_CARDID = "card_id";
    public static final String COL_CARDS_SUBJECT_SUBJECTID = "subject_id";

    // Views names
    public static final String VIEW_CARD_CATEGORY_SUBJECT = "CardCategorySubject";

    // JSONFlags
    public static final int TABLE_FLAG_CARDS = 01;
    public static final int TABLE_FLAG_CATEGORY = 02;
    public static final int TABLE_FLAG_CARDS_CATEGORY = 03;
    public static final int TABLE_FLAG_SUBJECT = 04;

    public DBHelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_TABLE_CARDS =
            "CREATE TABLE " + CARDS_TABLE_NAME + " ("
                    + COL_COMMON_ID + " integer primary key, "
                    + COL_CARDS_CARD_ID + " integer, "
                    + COL_CARDS_QUESTION + " text, "
                    + COL_CARDS_ANSWER01 + " text, "
                    + COL_CARDS_ANSWER02 + " text, "
                    + COL_CARDS_ANSWER03 + " text, "
                    + COL_CARDS_ANSWER04 + " text, "
                    + COL_CARDS_RELEASE_DATE + " text, "
                    + COL_CARDS_IMG_PATH + " text)";

    private static final String CREATE_TABLE_CARDS_DONE =
            "CREATE TABLE " + CARDS_DONE_TABLE_NAME + " ("
                    + COL_COMMON_ID + " integer primary key, "
                    + COL_CARDS_DONE_CARD_ID + " integer, "
                    + COL_CARDS_DONE_DATETIME_CORRECT + " integer, "
                    + COL_CARDS_DONE_DATETIME_INCORRECT + " integer)";

    private static final String CREATE_TABLE_PULLS =
            "CREATE TABLE " + PULLS_TABLE_NAME + " ("
                    + COL_COMMON_ID + " integer primary key, "
                    + COL_PULLS_POLLDATETIME + " integer)";

    private static final String CREATE_TABLE_CATEGORY =
            "CREATE TABLE " + CATEGORY_TABLE_NAME + " ("
                    + COL_COMMON_ID + " integer primary key, "
                    + COL_CATEGORY_CATEGORY + " text, "
                    + COL_CATEGORY_PICFILENAME + " text)";

    private static final String CREATE_TABLE_CARD_CATEGORY =
            "CREATE TABLE " + CARDS_CATEGORY_TABLE_NAME + " ("
                    + COL_CARDS_CATEGORY_CARDID + " integer, "
                    + COL_CARDS_CATEGORY_CATEGORYID + " integer," +
                    " PRIMARY KEY (" + COL_CARDS_CATEGORY_CARDID + "," + COL_CARDS_CATEGORY_CATEGORYID + "))";

    private static final String CREATE_TABLE_SUBJECT =
            "CREATE TABLE " + SUBJECT_TABLE_NAME + " ("
                    + COL_COMMON_ID + " integer primary key, "
                    + COL_TBSUBJECT_SUBJECT + " text, "
                    + COL_TBSUBJECT_IMGPATH + " text)";

    private static final String CREATE_TABLE_CARDS_SUBJECT =
            "CREATE TABLE " + CARDS_SUBJECT_TABLE_NAME + " ("
                    + COL_CARDS_SUBJECT_CARDID + " integer, "
                    + COL_CARDS_SUBJECT_SUBJECTID + " integer," +
                    " PRIMARY KEY (" + COL_CARDS_SUBJECT_CARDID + "," + COL_CARDS_SUBJECT_SUBJECTID + "))";

    //View(s) createn
    private static final String CREATE_VIEW_CARD_CATEGORY_SUBJECT =
            "CREATE VIEW " + VIEW_CARD_CATEGORY_SUBJECT + " AS SELECT " + DBHelperClass.CARDS_TABLE_NAME + ".*," + DBHelperClass.CATEGORY_TABLE_NAME + "." + DBHelperClass.COL_CATEGORY_CATEGORY + "," + DBHelperClass.SUBJECT_TABLE_NAME + "." + DBHelperClass.COL_TBSUBJECT_SUBJECT +
            " FROM " + DBHelperClass.CARDS_TABLE_NAME + "," + DBHelperClass.CARDS_CATEGORY_TABLE_NAME + "," + DBHelperClass.CATEGORY_TABLE_NAME + "," + DBHelperClass.SUBJECT_TABLE_NAME + "," + DBHelperClass.CARDS_SUBJECT_TABLE_NAME +
            " WHERE " + DBHelperClass.CARDS_TABLE_NAME + "." + DBHelperClass.COL_CARDS_CARD_ID + " = " + DBHelperClass.CARDS_CATEGORY_TABLE_NAME + "." + DBHelperClass.COL_CARDS_CATEGORY_CARDID +
            " AND " + DBHelperClass.CARDS_TABLE_NAME + "." + DBHelperClass.COL_CARDS_CARD_ID + "=" + DBHelperClass.CARDS_SUBJECT_TABLE_NAME + "." + DBHelperClass.COL_CARDS_SUBJECT_CARDID +
            " AND " + DBHelperClass.CARDS_CATEGORY_TABLE_NAME + "." + DBHelperClass.COL_CARDS_CATEGORY_CATEGORYID + "=" + DBHelperClass.CATEGORY_TABLE_NAME + "." + DBHelperClass.COL_COMMON_ID +
            " AND " + DBHelperClass.CARDS_SUBJECT_TABLE_NAME + "." + DBHelperClass.COL_CARDS_SUBJECT_SUBJECTID + "=" + DBHelperClass.SUBJECT_TABLE_NAME + "." + DBHelperClass.COL_COMMON_ID;



    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, "DBHelperClass: onCreate");
        // Create Tables
        db.execSQL(CREATE_TABLE_CARDS);
        db.execSQL(CREATE_TABLE_CARDS_DONE);
        db.execSQL(CREATE_TABLE_PULLS);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_CARD_CATEGORY);
        db.execSQL(CREATE_TABLE_SUBJECT);
        db.execSQL(CREATE_TABLE_CARDS_SUBJECT);
        // Create Views
        db.execSQL(CREATE_VIEW_CARD_CATEGORY_SUBJECT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(TAG, "onUpgrade: DROPPING ALL TABLES NOW!");
        db.execSQL("DROP TABLE IF EXISTS " + CARDS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CARDS_DONE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PULLS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CARDS_CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SUBJECT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CARDS_SUBJECT_TABLE_NAME);
        Log.v(TAG, "onUpgrade: DROPPED ALL TABLES!");
        Log.v(TAG, "onUpgrade: DROP ALL VIEWS");
        db.execSQL("DROP VIEW IF EXISTS " + VIEW_CARD_CATEGORY_SUBJECT);
        Log.v(TAG, "onUpgrade: DROPPED ALL VIEWS");
        Log.v(TAG, "Calling method to Creating new Database");
        onCreate(db);
        // TODO DBHelperClass onUpgrade richtig machen
    }

    // Table Methods
    public long createCard(Card table_card) {
        if (!idInTableExist(table_card.getId(), CARDS_TABLE_NAME)) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            // values.put(COL_COMMON_ID, table_card.getId());
            values.put(COL_CARDS_CARD_ID, table_card.getCardId());
            values.put(COL_CARDS_QUESTION, table_card.getQuestion());
            values.put(COL_CARDS_ANSWER01, table_card.getAnswer1());
            values.put(COL_CARDS_ANSWER02, table_card.getAnswer2());
            values.put(COL_CARDS_ANSWER03, table_card.getAnswer3());
            values.put(COL_CARDS_ANSWER04, table_card.getAnswer4());
            values.put(COL_CARDS_RELEASE_DATE, table_card.getReleaseDate());
            values.put(COL_CARDS_IMG_PATH, table_card.getImg_path());
            long card_id = db.insert(CARDS_TABLE_NAME, null, values);
            return card_id;
        } else {
            Log.w(TAG, "Card ID " + table_card.getId() + " already exist");
            return -1;
        }
    }

    public long createCard_DoneRow(Cards_done done_card) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_CARDS_DONE_CARD_ID, done_card.getCard_id());
        values.put(COL_CARDS_DONE_DATETIME_CORRECT, done_card.getDone_DateTime_Correct());
        values.put(COL_CARDS_DONE_DATETIME_INCORRECT, done_card.getDone_DateTime_Incorrect());
        long card_done_ID = db.insert(CARDS_DONE_TABLE_NAME, null, values);
        return card_done_ID;
    }

    // Fetching a special Card
    // *******************************************************************
    public Card getCard(long card_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =
                "SELECT * FROM " + CARDS_TABLE_NAME + " WHERE "
                        + COL_COMMON_ID + " = " + card_id;
        Log.v(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
            Log.v(TAG, "Moved Cursor to first");
        }
        Log.v(TAG, "Cursor Count: " + cursor.getCount() + "\nCursor pos: " + cursor.getPosition());
        Card card = new Card();
        card.setId(cursor.getInt(cursor.getColumnIndex(COL_COMMON_ID)));
        card.setCardId(cursor.getInt(cursor.getColumnIndex(COL_CARDS_CARD_ID)));
        card.setQuestion(cursor.getString(cursor.getColumnIndex((COL_CARDS_QUESTION))));
        card.setAnswer1(cursor.getString(cursor.getColumnIndex((COL_CARDS_ANSWER01))));
        card.setAnswer2(cursor.getString(cursor.getColumnIndex((COL_CARDS_ANSWER02))));
        card.setAnswer3(cursor.getString(cursor.getColumnIndex((COL_CARDS_ANSWER03))));
        card.setAnswer4(cursor.getString(cursor.getColumnIndex((COL_CARDS_ANSWER04))));
        card.setReleaseDate(cursor.getString(cursor.getColumnIndex(COL_CARDS_RELEASE_DATE)));
        card.setImg_path(cursor.getString(cursor.getColumnIndex(COL_CARDS_IMG_PATH)));
        return card;
    }

    // Fetching a special Card from CardCategorySubject View
    // *******************************************************************
    public Card getCardCategorySubject(long card_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =
                "SELECT * FROM " + VIEW_CARD_CATEGORY_SUBJECT + " WHERE "
                        + COL_CARDS_CARD_ID + " = " + card_id;
        Log.v(TAG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
            Log.v(TAG, "Moved Cursor to first");
        }
        Log.v(TAG, "Cursor Count: " + cursor.getCount() + "\nCursor pos: " + cursor.getPosition());
        Card card = new Card();
        card.setId(cursor.getInt(cursor.getColumnIndex(COL_COMMON_ID)));
        card.setCardId(cursor.getInt(cursor.getColumnIndex(COL_CARDS_CARD_ID)));
        card.setQuestion(cursor.getString(cursor.getColumnIndex((COL_CARDS_QUESTION))));
        card.setAnswer1(cursor.getString(cursor.getColumnIndex((COL_CARDS_ANSWER01))));
        card.setAnswer2(cursor.getString(cursor.getColumnIndex((COL_CARDS_ANSWER02))));
        card.setAnswer3(cursor.getString(cursor.getColumnIndex((COL_CARDS_ANSWER03))));
        card.setAnswer4(cursor.getString(cursor.getColumnIndex((COL_CARDS_ANSWER04))));
        card.setReleaseDate(cursor.getString(cursor.getColumnIndex(COL_CARDS_RELEASE_DATE)));
        card.setImg_path(cursor.getString(cursor.getColumnIndex(COL_CARDS_IMG_PATH)));
        card.setCategory(cursor.getString(cursor.getColumnIndex(COL_CATEGORY_CATEGORY)));
        card.setSubject(cursor.getString(cursor.getColumnIndex(COL_TBSUBJECT_SUBJECT)));
        return card;
    }

    // Fetching all cards
    // ***************************************************************************
    public List<Card> getAllCards() {
        List<Card> tableCards = new ArrayList<Card>();
        String selectQuery = "SELECT * FROM " + CARDS_TABLE_NAME;
        Log.v(TAG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Card tc = new Card();
                tc.setId(cursor.getInt((cursor.getColumnIndex(COL_COMMON_ID))));
                tc.setQuestion(cursor.getString(cursor.getColumnIndex(COL_CARDS_QUESTION)));
                tableCards.add(tc);
            } while (cursor.moveToNext());
        }
        return tableCards;
    }

    public Cursor getAllCardsCursor() {
        String selectQuery = "SELECT * FROM " + CARDS_TABLE_NAME;
        Log.v(TAG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

    public Cursor getCategorys() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + CATEGORY_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }


    public int getCountCards() {
        String selectQuery = "SELECT * FROM " + CARDS_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        Log.v(TAG, "Card Count: " + String.valueOf(count));
        return count;
    }


    public List<Cards_done> getAllCards_Done_Table() {
        List<Cards_done> tableCards_Done = new ArrayList<Cards_done>();
        String selectQuery = "SELECT * FROM " + CARDS_DONE_TABLE_NAME;
        Log.v(TAG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Cards_done dc = new Cards_done();
                dc.setId(cursor.getInt(cursor.getColumnIndex(COL_COMMON_ID)));
                dc.setCard_id(cursor.getLong(cursor.getColumnIndex(COL_CARDS_DONE_CARD_ID)));
                dc.setDone_DateTime_Correct(cursor.getLong(cursor.getColumnIndex(COL_CARDS_DONE_DATETIME_CORRECT)));
                dc.setDone_DateTime_Incorrect(cursor.getLong(cursor.getColumnIndex(COL_CARDS_DONE_DATETIME_INCORRECT)));
                tableCards_Done.add(dc);
            } while (cursor.moveToNext());
        }
        return tableCards_Done;
    }

    public void showDone_CardListAsLog() {
        Log.v(TAG, "Get all Done_Cards");
        List<Cards_done> allCards = this.getAllCards_Done_Table();
        for (Cards_done dc : allCards) {
            Log.v(TAG, "ID: " + dc.getId() + "\nCard ID: " + dc.getCard_id() + "\n");
        }
    }

    public void selectAllOfTabletoLog(String tableName) {
        String selectQuery = "SELECT * FROM " + tableName;
        Log.v(TAG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        String cursorString = DatabaseUtils.dumpCursorToString(cursor);
        Log.v(TAG, "dumpCursortoString: \n" + cursorString);
    }

    public void dumpQuerytoLog(String query) {
        Log.v(TAG, query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String cursorString = DatabaseUtils.dumpCursorToString(cursor);
        Log.v(TAG, "dumpCursortoString: \n" + cursorString);
    }

    public String dumpQuerytoString(String query) {
        Log.v(TAG, query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        String cursorString = DatabaseUtils.dumpCursorToString(cursor);
        return cursorString;
    }

    public void dumpQuerytoSystemout(String query) {
        Log.v(TAG, query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        Log.v(TAG, "Rows: " + cursor.getCount() + "\nColumns: " + cursor.getColumnCount());
        DatabaseUtils.dumpCursor(cursor);
        //String cursorString = DatabaseUtils.dumpCursorToString(cursor);
        // Log.v(TAG,"dumpCursortoString: \n" + cursorString);
    }

    public void insertIntoFromJson(JSONObject jsonObject) {
        try {
            int table_flag = jsonObject.getInt("TABLE_FLAG");
            Log.v(TAG, "Table Flag: " + table_flag + "\nTable Flag string:" + TABLE_FLAG_CATEGORY + "|");

            switch (table_flag) {
                case TABLE_FLAG_CARDS:
                    Log.v(TAG, "TABLE_FLAG_CARDS");
                    JSONArray jArrayDbCards = new JSONArray((jsonObject.getString("out_JSON_Cards")));
                    JSONArray jArrayDbCardsCategories = new JSONArray((jsonObject.getString("out_JSON_Cards_Categories")));
                    JSONArray jArrayDbCardsSubjects = new JSONArray((jsonObject.getString("out_JSON_Cards_Subjects")));

                    // Card anlegen und in DB inserten
                    for (int i = 0; i < jArrayDbCards.length() - 1; i++) {
                        if (jArrayDbCards.getString(i) != null) {
                            JSONObject jObj = new JSONObject(jArrayDbCards.getString(i));
                            int cardId = 0;
                            try {
                                cardId = Integer.parseInt(jObj.getString("id"));
                            } catch (NumberFormatException nfe) {
                                System.out.println("Could not parse " + nfe);
                            }
                            Card card = new Card(cardId, jObj.getString("question"), jObj.getString("answer1"), jObj.getString("answer2"), jObj.getString("answer3"), jObj.getString("answer4"), jObj.getString("releaseDate"));
                            long card_id = this.createCard(card);
                        } else {
                            break;
                        }
                    }
                    // Cards_Category Table insert
                    for (int i = 0; i < jArrayDbCardsCategories.length() - 1; i++) {
                        JSONObject jObj = new JSONObject(jArrayDbCardsCategories.getString(i));
                        int card_id = 0;
                        int category_id = 0;
                        Log.v(TAG, "JArrayLength: " + jArrayDbCardsCategories.length() + " i = " + i);
                        Log.v(TAG, "JArry String: " + jObj.toString());
                        try {
                            card_id = Integer.parseInt(jObj.getString("card_id"));
                            category_id = Integer.parseInt(jObj.getString("category_id"));
                        } catch (NumberFormatException nfe) {
                            System.out.println("Could not parse " + nfe);
                        }
                        ContentValues contentValues = new ContentValues();

                        contentValues.put(COL_CARDS_CATEGORY_CARDID, card_id);
                        contentValues.put(COL_CARDS_CATEGORY_CATEGORYID, category_id);
                        long cat_id = insertCards_Category(contentValues);
                    }
                    // Cards_Subject Table insert
                    for (int i = 0; i < jArrayDbCardsSubjects.length() - 1; i++) {
                        JSONObject jObj = new JSONObject(jArrayDbCardsSubjects.getString(i));
                        int card_id = 0;
                        int subject_id = 0;
                        Log.v(TAG, "JArrayLength: " + jArrayDbCardsSubjects.length() + " i = " + i);
                        Log.v(TAG, "JArry String: " + jObj.toString());
                        try {
                            card_id = Integer.parseInt(jObj.getString("card_id"));
                            subject_id = Integer.parseInt(jObj.getString("subject_id"));
                        } catch (NumberFormatException nfe) {
                            System.out.println("Could not parse " + nfe);
                        }
                        ContentValues contentValues = new ContentValues();

                        contentValues.put(COL_CARDS_SUBJECT_CARDID, card_id);
                        contentValues.put(COL_CARDS_SUBJECT_SUBJECTID, subject_id);
                        long cat_id = insertCards_Subject(contentValues);
                    }
                    break;
                case TABLE_FLAG_CATEGORY:
                    // Data for DB extract:
                    JSONArray jArrayDb = new JSONArray(jsonObject.getString("out_JSON"));
                    Log.v(TAG, "Categories Array Length is: " + String.valueOf(jArrayDb.length()));
                    // Put Data in ContentValue from JSON and store to DB
                    for (int i = 0; i < jArrayDb.length() - 1; i++) {
                        JSONObject jObj = new JSONObject(jArrayDb.getString(i));
                        Log.v(TAG, "i " + String.valueOf(i) + "\nJSON: \n"
                                + "\nid= " + jObj.getString("id")
                                + "\ncategory= " + jObj.getString("category")
                                + "\nimg_path= " + jObj.getString("img_path"));
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(COL_COMMON_ID, jObj.getString("id"));
                        contentValues.put(COL_CATEGORY_CATEGORY, jObj.getString("category"));
                        contentValues.put(COL_CATEGORY_PICFILENAME, jObj.getString("img_path"));
                        long cat_id = insertCategory(contentValues);
                        Log.v(TAG, "Category inserted with id: " + cat_id);
                    }
                    break;

                case TABLE_FLAG_SUBJECT:
                    // Data for DB extract:
                    JSONArray jArrayDbSubject = new JSONArray(jsonObject.getString("out_JSON"));
                    Log.v(TAG, "Categories Array Length is: " + String.valueOf(jArrayDbSubject.length()));
                    // Put Data in ContentValue from JSON and store to DB
                    for (int i = 0; i < jArrayDbSubject.length() - 1; i++) {
                        JSONObject jObj = new JSONObject(jArrayDbSubject.getString(i));
                        Log.v(TAG, "i " + String.valueOf(i) + "\nJSON: \n"
                                + "\nid= " + jObj.getString("id")
                                + "\ncategory= " + jObj.getString("subject")
                                + "\nimg_path= " + jObj.getString("img_path"));
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(COL_COMMON_ID, jObj.getString("id"));
                        contentValues.put(COL_TBSUBJECT_SUBJECT, jObj.getString("subject"));
                        contentValues.put(COL_TBSUBJECT_IMGPATH, jObj.getString("img_path"));
                        long cat_id = insertSubject(contentValues);
                        Log.v(TAG, "Category inserted with id: " + cat_id);
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private long insertCategory(ContentValues contentValues) {
        Log.v(TAG, "insert Category method");
        SQLiteDatabase db = this.getWritableDatabase();
        long cat_id = db.insert(CATEGORY_TABLE_NAME, null, contentValues);
        return cat_id;
    }

    private long insertSubject(ContentValues contentValues) {
        Log.v(TAG, "insert Subject method");
        SQLiteDatabase db = this.getWritableDatabase();
        long subject_id = db.insert(SUBJECT_TABLE_NAME, null, contentValues);
        return subject_id;
    }

    // TODO Methoden "zusammenfassen"

    private long insertCards_Category(ContentValues contentValues) {
        Log.v(TAG, "insert Cards_Category");
        if (!cardCategoryExist(contentValues.getAsInteger(COL_CARDS_CATEGORY_CARDID), contentValues.getAsInteger(COL_CARDS_CATEGORY_CATEGORYID))) {
            Log.v(TAG, "Card ID: " + contentValues.getAsInteger(COL_CARDS_CATEGORY_CARDID)
                    + " Category ID: " + contentValues.getAsInteger(COL_CARDS_CATEGORY_CATEGORYID)
                    + "seems not to exist");
            SQLiteDatabase db = this.getWritableDatabase();
            long card_cat_id = db.insert(CARDS_CATEGORY_TABLE_NAME, null, contentValues);
            return card_cat_id;
        } else {
            Log.w(TAG, "CardID: " + contentValues.getAsInteger(COL_CARDS_CATEGORY_CARDID)
                    + " CategoryID: " + contentValues.getAsInteger(COL_CARDS_CATEGORY_CATEGORYID)
                    + " already exist");
            return -1;
        }
    }

    private long insertCards_Subject(ContentValues contentValues) {
        Log.v(TAG, "insert Cards_Category");
        if (!idInTableExist(CARDS_SUBJECT_TABLE_NAME, COL_CARDS_SUBJECT_CARDID, COL_CARDS_SUBJECT_SUBJECTID, contentValues.getAsInteger(COL_CARDS_SUBJECT_CARDID), contentValues.getAsInteger(COL_CARDS_SUBJECT_SUBJECTID))) {
            Log.v(TAG, "Card ID: " + contentValues.getAsInteger(COL_CARDS_SUBJECT_CARDID)
                    + " Category ID: " + contentValues.getAsInteger(COL_CARDS_SUBJECT_SUBJECTID)
                    + "seems not to exist");
            SQLiteDatabase db = this.getWritableDatabase();
            long card_cat_id = db.insert(CARDS_SUBJECT_TABLE_NAME, null, contentValues);
            return card_cat_id;
        } else {
            Log.w(TAG, "CardID: " + contentValues.getAsInteger(COL_CARDS_SUBJECT_CARDID)
                    + " CategoryID: " + contentValues.getAsInteger(COL_CARDS_SUBJECT_SUBJECTID)
                    + " already exist");
            return -1;
        }
    }

    private boolean cardCategoryExist(int cardid, int categoryid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + this.CARDS_CATEGORY_TABLE_NAME
                + " WHERE " + this.CARDS_CATEGORY_TABLE_NAME + "." + this.COL_CARDS_CATEGORY_CARDID + "=" + cardid
                + " AND " + this.CARDS_CATEGORY_TABLE_NAME + "." + this.COL_CARDS_CATEGORY_CATEGORYID + "=" + categoryid;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean idInTableExist(String tableName, String primKeyCol1, String primKeyCol2, int primKeyCol1id, int primKeyCol2id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableName
                + " WHERE " + tableName + "." + primKeyCol1 + "=" + primKeyCol1id
                + " AND " + tableName + "." + primKeyCol2 + "=" + primKeyCol2id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean idInTableExist(int id, String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tableName
                + " WHERE " + tableName + "." + this.COL_COMMON_ID + "=" + id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }

    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) db.close();
    }
}
