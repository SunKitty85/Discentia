package MiscHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.moltox.discentia.DownloadActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import SqliteHelper.DBHelperClass;
import cz.msebera.android.httpclient.Header;

import static MiscHelper.JsonObjectsForDownload.UserToken;

/**
 * Created by moltox on 06.05.2017.
 */

public class SyncStatUpdater {
    private static final String TAG = SyncStatUpdater.class.getName();
    private Context context;
    private ProgressDialog progressDialog;
    private String API_KEY;
    private static final String TableName_Cards = DBHelperClass.CARDS_TABLE_NAME;   //"cards";
    private static final String TableName_Category = DBHelperClass.CATEGORY_TABLE_NAME; //"category";
    private static final String TableName_Subject = DBHelperClass.SUBJECT_TABLE_NAME;
    private static final String ToDoFlag_UpdateSyncStats = "Update_Sync_Stats";
    private static final String UrlSyncUpdateExtension = "updatesyncstats.php";

    public SyncStatUpdater() {
    }

    public SyncStatUpdater(Context context) {
        this.context = context;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String unsecureApiKey = sharedPreferences.getString("pref_api_key", "");
        StringUtils stringUtils = new StringUtils();
        API_KEY = stringUtils.md5(unsecureApiKey);
    }

    public void updateSyncStatSubject(JSONArray jsonArray) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Usertoken", UserToken);
            jsonObject.put("ToDoFlag", ToDoFlag_UpdateSyncStats);
            jsonObject.put("TableName", TableName_Subject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("settings", jsonObject);
        params.put("content", jsonArray);
        String URL = DownloadActivity.SERVER_ROOT_URL + UrlSyncUpdateExtension + "?apikey=" + API_KEY;
        httpClient.post(URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.hide();
                Log.v(TAG, "Response (Update Sync Stat Subject)(String): " + response.toString());
            }
        });
    }

    public void updateSyncStatCategory(JSONArray jsonArray) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Usertoken", UserToken);
            jsonObject.put("ToDoFlag", ToDoFlag_UpdateSyncStats);
            jsonObject.put("TableName", TableName_Category);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("settings", jsonObject);
        params.put("content", jsonArray);
        String URL = DownloadActivity.SERVER_ROOT_URL + UrlSyncUpdateExtension + "?apikey=" + API_KEY;
        httpClient.post(URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.hide();
                Log.v(TAG, "Response (Update Sync Stat Subject)(String): " + response.toString());
            }
        });
    }

    public void updateSyncStatCard(JSONArray jsonArray) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Usertoken", UserToken);
            jsonObject.put("ToDoFlag", ToDoFlag_UpdateSyncStats);
            jsonObject.put("TableName", TableName_Cards);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AsyncHttpClient httpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("settings", jsonObject);
        params.put("content", jsonArray);
        String URL = DownloadActivity.SERVER_ROOT_URL + UrlSyncUpdateExtension + "?apikey=" + API_KEY;
        httpClient.post(URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.hide();
                Log.v(TAG, "Response (Update Sync Stat Subject)(String): " + response.toString());
            }



        });
    }


}
