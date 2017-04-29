package CursorAdapterHelper;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.moltox.discentia.R;

import SqliteHelper.DBHelperClass;


/**
 * Created by moltox on 17.04.2017.
 */

public class CategoryCursorAdapter extends CursorAdapter {
    private static final String TAG = CategoryCursorAdapter.class.getName();

    public CategoryCursorAdapter(Context context, Cursor cursor)  {
        super(context, cursor, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.lv_item_categories, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv_catId = (TextView) view.findViewById(R.id.cat_id);
        TextView tv_catName = (TextView) view.findViewById(R.id.cat_name);
        tv_catId.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelperClass.COL_COMMON_ID)));
        tv_catName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBHelperClass.COL_CATEGORY_CATEGORY)));
        Log.v(TAG, "Category bindView");
    }
}
