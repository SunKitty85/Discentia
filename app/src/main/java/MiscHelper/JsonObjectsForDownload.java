package MiscHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import SqliteHelper.DBHelperClass;

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
    private static final String MyKey = "myKey2017";
    private static final String ToDoFlag_SYNC = "SYNC";
    private static final String TableName_Cards = DBHelperClass.CARDS_TABLE_NAME;   //"cards";
    private static final String TableName_Category = DBHelperClass.CATEGORY_TABLE_NAME; //"category";
    private static final String TableName_Subject = DBHelperClass.SUBJECT_TABLE_NAME;
    
    public JsonObjectsForDownload()  {

    }
    public JSONObject getJsonForCategory()  {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("MyKey", MyKey);
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
            jsonObject.put("MyKey", MyKey);
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
            jsonObject.put("MyKey", MyKey);
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
