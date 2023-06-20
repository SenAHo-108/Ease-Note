package zut.edu.cn.note.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import zut.edu.cn.note.R;

public class CustomInputDialog extends Dialog{
    Context mContext;
    private TextView btn_ok;
    private TextView btn_cancel;
    private TextView title;
    private EditText editText;
    public CustomInputDialog(@NonNull Context context) {
        super(context, R.style.CustomInputStyle);
        this.mContext = context;
        initView();
    }
    @SuppressLint("MissingInflatedId")
    public void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.input_dialog, null);
        title = (TextView) view.findViewById(R.id.title);
        editText = (EditText) view.findViewById(R.id.et_input);
        btn_ok = (TextView) view.findViewById(R.id.btn_ok);
        btn_cancel = (TextView) view.findViewById(R.id.btn_cancel);
        super.setContentView(view);
    }
    public CustomInputDialog setTile(String s) {
        title.setText(s);
        return this;
    }
    //获取当前输入框对象
    public View getEditText() {
        return editText;
    }
    //传递数据给输入框对象
    public CustomInputDialog setEditText(String s) {
        editText.setText(s);
        return this;
    }
    //确定键监听器
    public void setOnSureListener(View.OnClickListener listener) {
        btn_ok.setOnClickListener(listener);
    }
    //取消键监听器
    public void setOnCanlceListener(View.OnClickListener listener) {
        btn_cancel.setOnClickListener(listener);
    }
}
