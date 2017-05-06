package com.example.moltox.discentia;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import CursorAdapterHelper.CategoryCursorAdapter;
import MiscHelper.JsonObjectsForDownload;
import MiscHelper.StringUtils;
import SqliteHelper.DBHelperClass;
import cz.msebera.android.httpclient.Header;

public class DownloadActivity extends AppCompatActivity {
    // private Context context;
    private static final String TAG = DownloadActivity.class.getName();
    private static final String SERVER_ROOT_URL = "http://5.9.67.156/Discentia/";
    private static final String SERVER_DIRECT_ORDER_EXTENSION = "query.php";
    private String API_KEY;
    private TextView tv_download_1;
    private Button btn_downloadNow;
    private ListView lv_categories;
    Cursor getCategoriesCursor;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait");
        progressDialog.setCancelable(false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        API_KEY = getApiKey();
        btn_downloadNow = (Button) findViewById(R.id.btn_download_downloadNow);
        btn_downloadNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "DoDownload Subect");
                doDownload(jsonSubject());
                Log.v(TAG, "DoDownload Category");
                doDownload(jsonCategory());
            }
        });
        tv_download_1 = (TextView) findViewById(R.id.tv_download_1);
        lv_categories = (ListView) findViewById(R.id.lv_categories);
        lv_categories.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        lv_categories.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater menuInflater = actionMode.getMenuInflater();
                menuInflater.inflate(R.menu.menu_contextual_actionbar, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                //lv_categories.setItemChecked(0,true);

                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                Log.v(TAG, "Menu Item: " + menuItem.getTitle());
                switch (menuItem.getItemId()) {
// TODO Fix problem where Position 0 is true (as checked) on default
                    case R.id.menu_download:
                        //action on clicking contextual action bar menu item
                        SparseBooleanArray checkedItems = lv_categories.getCheckedItemPositions();

                        ArrayList<Integer> ids = new ArrayList<>();
                        getCategoriesCursor.moveToFirst();
                        Log.v(TAG, "CheckItems: " + checkedItems.toString());
                        for(int i =0;i< checkedItems.size();i++){
                            if(checkedItems.valueAt(i) == true){
                                Log.v(TAG, "CheckedItmes ValueAt: " + i + " " + String.valueOf(checkedItems.valueAt(i)));
                                getCategoriesCursor.moveToPosition(i);
                                Log.v(TAG, "Cursor Position in if: " + String.valueOf(getCategoriesCursor.getPosition()));
                                ids.add(getCategoriesCursor.getInt(getCategoriesCursor.getColumnIndexOrThrow(DBHelperClass.COL_COMMON_ID)));
                            }
                        }
                        Log.v(TAG, "Checked IDs: " + ids.toString());
                        doDownload(jsonCards(ids));
                        return true;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }

            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                actionMode.setTitle("" + lv_categories.getCheckedItemCount() + " items selected");

            }
        });
        /*lv_categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(TAG, "Item clicked:\nPosition: " + String.valueOf(position) + " id: " + String.valueOf(id) );
                // TODO delete Debug entries:
                TextView tv_category = (TextView) view.findViewById(R.id.cat_name);
                Log.v(TAG, "Cat name: " + tv_category.getText());
                // ... end debug entries
                doDownload(jsonCards());
            }
        });
        */
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillListView();

    }

    private void fillListView() {
        DBHelperClass db = new DBHelperClass(this);
        getCategoriesCursor = db.getCategorys();

        CursorAdapter categoryCursorAdapter = new CategoryCursorAdapter(this, getCategoriesCursor);
        lv_categories.setAdapter(categoryCursorAdapter);
    }


    private void doDownload(JSONObject jsonObject)  {
        progressDialog.show();
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("json",jsonObject);
        String URL = SERVER_ROOT_URL + SERVER_DIRECT_ORDER_EXTENSION + "?apikey=" + API_KEY;
        httpClient.post(URL,params,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                insertJsonToDb(response);
                Log.v(TAG, "Response (String): " + response.toString() );
                tv_download_1.setText(response.toString());
                fillListView();
                progressDialog.hide();


            }
        });
    }


    private void insertJsonToDb(JSONObject jsonObject) {
        DBHelperClass dbHelperClass = new DBHelperClass(this);
        dbHelperClass.insertIntoFromJson(jsonObject);
        dbHelperClass.closeDB();
    }

    private JSONObject jsonCategory() {
        JsonObjectsForDownload jofd = new JsonObjectsForDownload();
        JSONObject jsonObject = jofd.getJsonForCategory();
        return jsonObject;
    }

    private JSONObject jsonSubject() {
        JsonObjectsForDownload jofd = new JsonObjectsForDownload();
        JSONObject jsonObject = jofd.getJsonForSubject();
        return jsonObject;
    }

    private JSONObject jsonCards(ArrayList<Integer> arrayList) {
        JsonObjectsForDownload jofd = new JsonObjectsForDownload();
        // Übergebe ArrayList mit den IDs der zu downloadenden Kategorien
        JSONObject jsonObject = jofd.getJsonForCards(arrayList);
        return jsonObject;
    }

    private String getApiKey() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String unsecureApiKey = sharedPreferences.getString("pref_api_key", "");
        StringUtils stringUtils = new StringUtils();
        return stringUtils.md5(unsecureApiKey);
    }
}

// Test
