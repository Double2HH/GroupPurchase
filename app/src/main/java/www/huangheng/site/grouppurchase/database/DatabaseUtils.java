package www.huangheng.site.grouppurchase.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库工具类
 */

public class DatabaseUtils {


    /**
     * 获取用户搜索历史记录
     */
    public static List<String> getSearchHistory(Context context) {

        List<String> history = new ArrayList<>();

        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(context, "GroupPurchase.db", null, 1);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("SearchHistory", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                history.add(cursor.getString(cursor.getColumnIndex("searchContent")));
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        databaseHelper.close();
        return history;
    }

    /**
     * 保存搜索内容到数据库中
     */
    public static void saveSearchHistory(Context context, String searchContent) {

        boolean flag = false;

        List<String> searchHistoryContent = getSearchHistory(context);

        for (int i = 0, len = searchHistoryContent.size(); i < len; i++) {
            if (searchHistoryContent.get(i).equals(searchContent)) {
                flag = true;
                break;
            }
        }

        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(context, "GroupPurchase.db", null, 1);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (flag) {
            sqLiteDatabase.delete("SearchHistory", "searchContent=?", new String[]{searchContent});
        }

        values.put("searchContent", searchContent);
        sqLiteDatabase.insert("SearchHistory", null, values);

        sqLiteDatabase.close();
        databaseHelper.close();

    }

    /**
     * 从数据库中删除指定内容
     *
     * @param searchContent 想要删除的搜索内容
     */
    public static void deleteSearchHistory(Context context, String searchContent) {

        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(context, "GroupPurchase.db", null, 1);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        sqLiteDatabase.delete("SearchHistory", "searchContent=?", new String[]{searchContent});

        sqLiteDatabase.close();
        databaseHelper.close();
    }

    /**
     * 从数据库中删除所有搜索内容
     */
    public static void deleteSearchHistory(Context context) {

        MyDatabaseHelper databaseHelper = new MyDatabaseHelper(context, "GroupPurchase.db", null, 1);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        sqLiteDatabase.delete("SearchHistory", null, null);

        sqLiteDatabase.close();
        databaseHelper.close();
    }


}
