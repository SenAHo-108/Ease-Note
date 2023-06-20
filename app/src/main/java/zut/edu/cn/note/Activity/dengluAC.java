package zut.edu.cn.note.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    CheckBox cb_remember;
    SharedPreferences remember;
    Boolean b_remember=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);
        init();


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

    private void init(){
        et_pwd=findViewById(R.id.et_password);
        et_uname=findViewById(R.id.et_name);
        btn_denglu=findViewById(R.id.btn_denglu);
        cb_remember=findViewById(R.id.cb_rem_pwd);
        remember=getSharedPreferences("remember",MODE_PRIVATE);
        cb_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId()==R.id.cb_rem_pwd){
                    b_remember=isChecked;

                }
            }
        });


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
                if(cb_remember.isChecked()){
                    SharedPreferences.Editor editor=remember.edit();
                    editor.putString("uname",et_uname.getText().toString());
                    editor.putString("pwd",et_pwd.getText().toString());
                    editor.putBoolean("isremember",cb_remember.isChecked());
                    editor.commit();
                }
            }
        });


        remember = getSharedPreferences("remember", MODE_PRIVATE);
        reload();
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

    //对SharePreference中的数据进行读取
    public void reload(){
        boolean isremember=remember.getBoolean("isremember",false);
        if(isremember){
            String uname=remember.getString("uname","");
            String pwd=remember.getString("pwd","");
            et_uname.setText(uname);
            et_pwd.setText(pwd);

        }
    }





}