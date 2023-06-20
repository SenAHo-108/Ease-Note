package zut.edu.cn.note.fragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import zut.edu.cn.note.Activity.dengluAC;
import zut.edu.cn.note.R;
import zut.edu.cn.note.util.MyHelper;

public class ThrFrag extends Fragment {
    TextView textView;
    View view;
    SQLiteDatabase db;
    String username;
    Button btn_delete;
    Button btn_update;
    Button btn_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thr_frag, container, false);
        textView = view.findViewById(R.id.geren_username);
        btn_delete = view.findViewById(R.id.geren_delete);
        btn_update = view.findViewById(R.id.geren_update);
        username = getActivity().getIntent().getStringExtra("username");
        textView.setText(username);
        btn_back = view.findViewById(R.id.geren_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), dengluAC.class));
                getActivity().finish();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        return view;
    }

    private void update() {
        MyHelper helper = new MyHelper(getActivity());
        SQLiteDatabase db = helper.getReadableDatabase();

        final EditText et = new EditText(getActivity());
        new AlertDialog.Builder(getActivity()).setTitle("输入您要修改的密码！").setView(et).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        MyHelper helper = new MyHelper(getActivity());
                        SQLiteDatabase db = helper.getReadableDatabase();
                        ContentValues values = new ContentValues();
                        String sql = "select * from user where username like ?";
                        Cursor cursor = db.rawQuery(sql, new String[]{username});
                        cursor.moveToFirst();
                        String pwd = cursor.getString(2);
                        db = helper.getWritableDatabase();
                        values.put("password", et.getText().toString());
                        db.update("user", values, "username=?", new String[]{username});
                        Toast.makeText(getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("取消", null).setCancelable(false).show();
    }

    private void delete() {
        MyHelper helper = new MyHelper(getActivity());
        db = helper.getWritableDatabase();
        dialog(username);
    }

    private void dialog(String username) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请确认是否注销账户！");
        builder.setMessage("该操作不可恢复！");
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.delete("user", "username=?", new String[]{username});
                db.delete("info", "username=?", new String[]{username});
                db.close();
                startActivity(new Intent(getActivity(), dengluAC.class));
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

}






