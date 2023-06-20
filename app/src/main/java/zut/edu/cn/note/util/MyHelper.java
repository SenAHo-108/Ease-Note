package zut.edu.cn.note.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context){
        super(context,"note.db",null,3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表SQL语句
        String user_table = "create table user(_id integer primary key autoincrement, " +
                "username text not null, password text not null)";
        String info_table = "create table info(_id integer primary key autoincrement, " +
                "username text not null, record text not null)";
//执行SQL语句
        db.execSQL(user_table);
        db.execSQL(info_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
