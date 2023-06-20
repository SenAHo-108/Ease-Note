package zut.edu.cn.note.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;

import zut.edu.cn.note.Activity.Admin;
import zut.edu.cn.note.Activity.zhuceAC;
import zut.edu.cn.note.R;
import zut.edu.cn.note.util.MyHelper;

public class FirstFrag extends Fragment {
    Button btn_add;
    EditText editText;
    View view;
    String username;
    GridView gv;
    ArrayAdapter adapter;
    private ArrayList<String> arrayList = new ArrayList<String>();


    @SuppressLint({"MissingInflatedId", "SuspiciousIndentation"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fir_frag, container, false);
        /**
         * 初始化
         */
        init();
        /**
         * 读取record表的内容，添加到arraylist里面
         */
        if (arrayList.size() == 0) {
            add();
        }
        /**
         * 把数据加载到列表上
         */
        adapter = new ArrayAdapter(getActivity(), R.layout.item1, arrayList);
        gv.setAdapter(adapter);
        return view;
    }

    private void init() {
        System.out.println("test");
        gv = view.findViewById(R.id.shouye_lv);
        btn_add = view.findViewById(R.id.shouye_btn_add);
        username = getActivity().getIntent().getStringExtra("username");  //得到用户的username
        editText = view.findViewById(R.id.shouye_in);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final EditText et = new EditText(getActivity());
                new AlertDialog.Builder(getActivity()).setTitle("输入要修改的记录").setView(et).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        MyHelper helper = new MyHelper(getActivity());
                        SQLiteDatabase db = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("record", et.getText().toString());
                        db.update("info", values, "record = ?", new String[]{arrayList.get(position)});
                        db.close();
                        arrayList.set(position, et.getText().toString());
                        Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", null).show();
            }
        });
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("此操作不可恢复！");
                builder.setMessage("确认删除吗？？！");
                builder.setCancelable(false);
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String delinfo = arrayList.get(position).toString();
                        /**
                         * 从数据库删除
                         */
                        MyHelper helper1 = new MyHelper(getActivity());
                        SQLiteDatabase db = helper1.getWritableDatabase();
                        db.delete("info", "record=?", new String[]{delinfo});
                        db.close();
                        /**
                         * 从arraylist删除
                         */
                        arrayList.remove(position);
                        adapter.notifyDataSetChanged();

                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
                return true;
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "不能记录空数据", Toast.LENGTH_SHORT).show();
                } else {
                    MyHelper helper = new MyHelper(getActivity());
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    String sql = "select * from info where username like ? and record like ?";
                    Cursor cursor = db.rawQuery(sql, new String[]{username, editText.getText().toString()});
                    cursor.moveToFirst();
                    int count = cursor.getCount();
                    Log.i("tag1", count + "");
                    if (count == 0) {
                        values.put("username", username);
                        values.put("record", editText.getText().toString());
                        db.insert("info", null, values);
                        arrayList.add(editText.getText().toString());
                        adapter.notifyDataSetChanged();
                        db.close();

                    } else {
                        Toast.makeText(getActivity(), "记录已经存在", Toast.LENGTH_SHORT).show();
                        db.close();
                    }
                }
                editText.setText("");
            }
        });
    }

    private void add() {
        MyHelper helper = new MyHelper(getActivity());
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from info where username like ?";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
        } else {
            cursor.moveToFirst();
            arrayList.add(cursor.getString(2));
            while (cursor.moveToNext()) {
                arrayList.add(cursor.getString(2));
            }
        }
        cursor.close();
        db.close();
    }

}
