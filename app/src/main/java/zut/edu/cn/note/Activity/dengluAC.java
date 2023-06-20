package zut.edu.cn.note.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import zut.edu.cn.note.util.MyHelper;
import zut.edu.cn.note.R;

public class dengluAC extends AppCompatActivity  {
    static String username;

    Button btn_denglu;
    Button btn_zhuce;
    EditText et_uname;
    EditText et_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);
        init();
    }

    private void init(){
        et_pwd=findViewById(R.id.et_password);
        et_uname=findViewById(R.id.et_name);
        btn_denglu=findViewById(R.id.btn_denglu);
        btn_denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_uname.getText().toString().equals("admin")&&et_pwd.getText().toString().equals("admin")){
                    startActivity(new Intent(dengluAC.this,Admin.class));
                    finish();
                }
               else if (et_uname.getText().toString().equals("")||et_pwd.getText().toString().equals("")){
                Toast.makeText(dengluAC.this,"您输入的信息为空！",Toast.LENGTH_SHORT).show();
            }
               else if (compete()){
                     Intent intent=new Intent(dengluAC.this,shouyeAc.class);
                     intent.putExtra("username",username);
                     Toast.makeText(dengluAC.this,"欢迎您使用Ease-Note\n              "
                             +username,Toast.LENGTH_SHORT).show();
                     startActivity(intent);
                     finish();
                }
               else{
                     Toast.makeText(dengluAC.this,"您输入的信息有误！",Toast.LENGTH_SHORT).show();
                 }
            }
        });
        btn_zhuce=findViewById(R.id.btn_zhuce);
        btn_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dengluAC.this,zhuceAC.class));
            }
        });

    }
    private boolean compete(){
        MyHelper helper=new MyHelper(dengluAC.this);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("user",null,null,null,null,null,null);
        if (cursor.getCount()==0){
            cursor.close();
            db.close();
            return false;
        }
        else {
            cursor.moveToFirst();
            if (cursor.getString(1).equals(et_uname.getText().toString())&&cursor.getString(2).equals(et_pwd.getText().toString())){
                username=cursor.getString(1);
                Log.i("tag",username);
                cursor.close();
                db.close();
                return true;
            }else {
                while(cursor.moveToNext()){
                    if (cursor.getString(1)!=null){
                        if (cursor.getString(1).equals(et_uname.getText().toString())&&cursor.getString(2).equals(et_pwd.getText().toString())){
                            username=cursor.getString(1);
                            Log.i("tag",username);
                            cursor.close();
                            db.close();
                            return true;
                        }
                    }
            }
                cursor.close();
                db.close();
                return false;
        }

    }

    }



}