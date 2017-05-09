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
    public static final String SERVER_ROOT_URL = "http://5.9.67.156/Discentia/";
    public static final String SERVER_DIRECT_ORDER_EXTENSION = "query.php";
    private String API_KEY;
    private TextView tv_download_1;
    private Button btn_downloadNow;
    private Cursor getCategoriesCursor;
    private ProgressDialog progressDialog;

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
                Log.v(TAG, "DoDownload Cards");
                doDownload(jsonCards());
            }
        });
        tv_download_1 = (TextView) findViewById(R.id.tv_download_1);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void doDownload(JSONObject jsonObject) {
        progressDialog.show();
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("json", jsonObject);
        String URL = SERVER_ROOT_URL + SERVER_DIRECT_ORDER_EXTENSION + "?apikey=" + API_KEY;
        httpClient.post(URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.hide();
                insertJsonToDb(response);
                Log.v(TAG, "Response (String): " + response.toString());
                tv_download_1.setText(response.toString());
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

    private JSONObject jsonCards() {
        JsonObjectsForDownload jofd = new JsonObjectsForDownload();
        // Übergebe ArrayList mit den IDs der zu downloadenden Kategorien
        JSONObject jsonObject = jofd.getJsonForCards();
        return jsonObject;
    }

    private String getApiKey() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String unsecureApiKey = sharedPreferences.getString("pref_api_key", "");
        StringUtils stringUtils = new StringUtils();
        return stringUtils.md5(unsecureApiKey);
    }

}

