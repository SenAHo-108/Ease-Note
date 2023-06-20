package zut.edu.cn.note.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import zut.edu.cn.note.R;

import zut.edu.cn.note.util.Downloadfromhttp;
import zut.edu.cn.note.util.MyHelper;

public class SecFrag extends Fragment {
    static  int anInt=0;
    Button btnxiayige,btnshangyige;
    TextView textView;
    ArrayList<String> results=new ArrayList<String>();
    String url="http://v.juhe.cn/joke/randJoke.php?key=0925730710fcbc6dcba2adcade733e36";
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                textView.setText(results.get(anInt));
            }
            if (msg.what==2){
                Toast.makeText(getActivity(),"API请求出错，请联系管理员！",Toast.LENGTH_SHORT).show();
                textView.setText("请求次数用完辣！");
                anInt=-1;
            }
        }
    };
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sec_frag,container,false);
        textView=view.findViewById(R.id.sec_xiaohua);
        btnxiayige=view.findViewById(R.id.btn_xiayige);
        btnxiayige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (anInt<results.size()-1){
                    textView.setText(results.get(++anInt));
                }
                else if (anInt==-1){

                }
                else {
                    Toast.makeText(getActivity(),"后面没有了",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnshangyige=view.findViewById(R.id.btn_shangyige);
        btnshangyige.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (anInt>0){
                    textView.setText(results.get(--anInt));
                }
                else {
                    Toast.makeText(getActivity(),"前面没有了",Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(anInt==0||anInt%10==0)
        {DW dw=new DW();
            dw.start();}
        return view;
    }
    class DW extends Thread{
        @Override
        public void run() {
            super.run();
            String result;
            try {
                result= Downloadfromhttp.download(url);
                Log.i("result1",result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                JSONObject jsonObject=new JSONObject(result);
                int resultcode=jsonObject.optInt("error_code");
                if (resultcode==0){
                    JSONArray array=jsonObject.optJSONArray("result");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj=(JSONObject)array.opt(i);
                        String temp=obj.getString("content");
                        results.add(temp);
                    }
                    Message msg=new Message();
                    msg.what=1;
                    handler.sendMessage(msg);
                }
                else {
                    Message msg=new Message();
                    msg.what=2;
                    handler.sendMessage(msg);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
