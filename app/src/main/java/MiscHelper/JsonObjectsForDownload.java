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

import java.util.ArrayList;

import SqliteHelper.DBHelperClass;
import cz.msebera.android.httpclient.Header;

import static com.example.moltox.discentia.R.id.tv_download_1;

/**
 * Created by moltox on 26.03.2017.
 */


/*
JsonObject:
['myKey'] = String
['ToDoFlag'] = String (SYNC,)
['TableName'] = String
['Category_ID'] = INT
['minAge'] = INT
['lastPoll'] = INT
 */
public class JsonObjectsForDownload {
    private static final String TAG = JsonObjectsForDownload.class.getName();
    public static final String UserToken = "mv02u2vu9023mu90u230";
    private static final String ToDoFlag_SYNC = "SYNC";
    private static final String TableName_Cards = DBHelperClass.CARDS_TABLE_NAME;   //"cards";
    private static final String TableName_Category = DBHelperClass.CATEGORY_TABLE_NAME; //"category";
    private static final String TableName_Subject = DBHelperClass.SUBJECT_TABLE_NAME;


    public JsonObjectsForDownload()  {

    }



    public JSONObject getJsonForCategory()  {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Usertoken", UserToken);
            jsonObject.put("ToDoFlag",ToDoFlag_SYNC);
            jsonObject.put("TableName",TableName_Category);
            jsonObject.put("Subject_ID","");
            jsonObject.put("minAge","");
            jsonObject.put("lastPoll",4711);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getJsonForSubject()  {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Usertoken", UserToken);
            jsonObject.put("ToDoFlag",ToDoFlag_SYNC);
            jsonObject.put("TableName",TableName_Subject);
            jsonObject.put("Subject_ID","");
            jsonObject.put("minAge","");
            jsonObject.put("lastPoll",4711);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getJsonForCards(ArrayList<Integer> idsArrayList)  {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Usertoken", UserToken);
            jsonObject.put("ToDoFlag",ToDoFlag_SYNC);
            jsonObject.put("TableName",TableName_Cards);
            jsonObject.put("Categories",idsArrayList.toString());
            jsonObject.put("Card_ID","");
            jsonObject.put("minAge","");
            jsonObject.put("lastPoll",4711);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    
    
    
}
