package zut.edu.cn.note.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import zut.edu.cn.note.util.MyHelper;
import zut.edu.cn.note.R;

public class zhuceAC extends AppCompatActivity {
    Button btn_back;
    EditText et_in_username;
    EditText et_in_password;
    Button btn_in_queren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);
        init();
    }
    private void init(){
        et_in_password=findViewById(R.id.et_in_password);
        et_in_username=findViewById(R.id.et_in_name);
        btn_back=findViewById(R.id.zhuce_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_in_queren=findViewById(R.id.btn_in_queren);
        btn_in_queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_in_username.getText().toString().equals("")||et_in_password.getText().toString().equals("")){
                Toast.makeText(zhuceAC.this,"你还未输入用户名或者密码！",Toast.LENGTH_SHORT).show();
            }
                else if(compete()){
                    Toast.makeText(zhuceAC.this,"该用户名已被注册！",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    insert();
                    startActivity(new Intent(zhuceAC.this,dengluAC.class));
                }

            }
        });
    }
    public void insert(){
        String username=et_in_username.getText().toString();
        String password=et_in_password.getText().toString();
        MyHelper helper=new MyHelper(zhuceAC.this);
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("username",username);
        values.put("password",password);
        long id=db.insert("user",null,values);
        db.close();
    }
    /**
     * 点击空白位置 隐藏软键盘
     */
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {

            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
    private boolean compete(){
        MyHelper helper=new MyHelper(zhuceAC.this);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("user",null,null,null,null,null,null);
        if (cursor.getCount()==0){
            cursor.close();
            db.close();
            return false;
        }
        else {
            cursor.moveToFirst();
            if (cursor.getString(1).equals(et_in_username.getText().toString())){
                cursor.close();
                db.close();
                return true;
            }else {
                while(cursor.moveToNext()){
                    if (cursor.getString(1).equals(et_in_username.getText().toString())){
                        cursor.close();
                        db.close();
                        return true;
                    }
                }
            }

        }
        cursor.close();
        db.close();
        return false;
    }
}