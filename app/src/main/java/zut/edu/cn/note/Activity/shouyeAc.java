package zut.edu.cn.note.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.Serializable;
import java.util.ArrayList;

import zut.edu.cn.note.R;
import zut.edu.cn.note.fragment.FirstFrag;
import zut.edu.cn.note.fragment.SecFrag;
import zut.edu.cn.note.fragment.ThrFrag;

public class shouyeAc extends AppCompatActivity {
    RadioGroup rg;
    FirstFrag frag1;
    SecFrag frag2;
    ThrFrag frag3;
    FragmentManager fm;
    Fragment fragment;
    String username;
    RadioButton rb1, rb2, rb3;
    ArrayList<String> arrayList = new ArrayList<String>();
    public  void init(){
        frag1=new FirstFrag();
        frag2=new SecFrag();
        frag3=new ThrFrag();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        init();
        RadioGroup rg = findViewById(R.id.rg);
        firstinit();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (checkedId == R.id.rb1) {

                    if (!frag1.isAdded()) {
                        transaction.add(R.id.fg, frag1);
                    }
                    transaction.show(frag1);
                    if (frag2.isAdded()) {
                        transaction.hide(frag2);
                    }
                    if (frag3.isAdded()) {
                        transaction.hide(frag3);
                    }
                }
                else    if (checkedId == R.id.rb2) {

                    if (!frag2.isAdded()) {
                        transaction.add(R.id.fg, frag2);
                    }
                    transaction.show(frag2);
                    if (frag1.isAdded()) {
                        transaction.hide(frag1);
                    }
                    if (frag3.isAdded()) {
                        transaction.hide(frag3);
                    }
                }
                else    if (checkedId == R.id.rb3) {

                    if (!frag3.isAdded()) {
                        transaction.add(R.id.fg, frag3);
                    }
                    transaction.show(frag3);
                    if (frag2.isAdded()) {
                        transaction.hide(frag2);
                    }
                    if (frag1.isAdded()) {
                        transaction.hide(frag1);
                    }
                }
                transaction.commit();
            }
        });
    }


    private void firstinit() {
        fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (!frag1.isAdded()){
            transaction.add(R.id.fg,frag1);
        }
        transaction.show(frag1);
        if (frag2.isAdded()){
            transaction.hide(frag2);
        }
        if (frag3.isAdded()){
            transaction.hide(frag3);
        }
        transaction.commit();
    }




}