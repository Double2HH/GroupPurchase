package www.huangheng.site.grouppurchase.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 自定义数据库助手类
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {


    private static final String SEARCHHISTORY = "CREATE TABLE SearchHistory(searchContent text)";


    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SEARCHHISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
