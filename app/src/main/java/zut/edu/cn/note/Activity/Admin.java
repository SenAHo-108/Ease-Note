package zut.edu.cn.note.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import zut.edu.cn.note.R;
import zut.edu.cn.note.util.CustomInputDialog;
import zut.edu.cn.note.util.MyHelper;

public class Admin extends AppCompatActivity {
    Button btn;

    ArrayAdapter adapter;
    Handler handler;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayList<String> arrayList1 = new ArrayList<String>();
    ArrayList<String> result = new ArrayList<String>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        init();

    }
   private void  init(){
       listView = findViewById(R.id.ad_lv);
       btn=findViewById(R.id.admin_back);
       add();
       for (int i = 0; i < arrayList1.size(); i++) {
           result.add(arrayList.get(i) + "---" + arrayList1.get(i));
       }
       adapter = new ArrayAdapter<>(Admin.this, R.layout.item_ad, result);
       listView.setAdapter(adapter);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               final EditText et = new EditText(Admin.this);
               new AlertDialog.Builder(Admin.this).setTitle("请输入修改后的密码").setView(et).
                       setPositiveButton("确定", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface arg0, int arg1) {
                               MyHelper helper = new MyHelper(Admin.this);
                               SQLiteDatabase db = helper.getWritableDatabase();
                               ContentValues values = new ContentValues();
                               values.put("password", et.getText().toString());
                               db.update("user", values, "username = ?", new String[]{arrayList.get(i)});
                               db.close();
                               arrayList1.set(i, et.getText().toString());
                               result.set(i, arrayList.get(i) + "---" + arrayList1.get(i));
                               Toast.makeText(Admin.this, "修改成功，新密码为：" + et.getText().toString(), Toast.LENGTH_SHORT).show();
                           }
                       }).setNegativeButton("取消", null).setCancelable(false).show();

               adapter.notifyDataSetChanged();
           }
       });
       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
               builder.setTitle("此操作不可恢复！");
               builder.setMessage("确认删除吗？？！");
               builder.setCancelable(false);
               builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       /**
                        * 从数据库删除
                        */
                       MyHelper helper1 = new MyHelper(Admin.this);
                       SQLiteDatabase db = helper1.getWritableDatabase();
                       db.delete("user", "username=?", new String[]{arrayList.get(i)});
                       db.close();
                       add();
                       /**
                        * 从arraylist删除
                        */
                       result.remove(i);
                       arrayList.remove(i);
                       arrayList1.remove(i);
                       adapter.notifyDataSetChanged();

                   }
               });
               builder.setNegativeButton("取消", null);
               builder.create().show();
               return true;
           }
       });
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(Admin.this,dengluAC.class));
               finish();
           }
       });

   }

    private void add() {

        MyHelper helper = new MyHelper(Admin.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from user";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
        } else {
            cursor.moveToFirst();
            arrayList1.add(cursor.getString(2));
            arrayList.add(cursor.getString(1));
            while (cursor.moveToNext()) {
                arrayList1.add(cursor.getString(2));
                arrayList.add(cursor.getString(1));
            }
        }
        cursor.close();
        db.close();
    }
}