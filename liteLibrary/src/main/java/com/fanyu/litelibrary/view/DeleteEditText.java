package com.fanyu.litelibrary.view;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fanyu.litelibrary.R;

/**
 *
 * Created by fanyu on 16/7/6.
 */
public class DeleteEditText extends FrameLayout {
    private EditText editText;

    private TextView deleteTV;

    public interface OnChangeListener {
        void onTextChange(String text);
    }

    private OnChangeListener onChangeListener = null;

    public DeleteEditText(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.ui_delete_edittext, this);
        editText = (EditText) findViewById(R.id.edittext);
        deleteTV = (TextView) findViewById(R.id.textview);
        deleteTV.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.edittext_clear));
        deleteTV.setVisibility(GONE);
        deleteTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        setVisible();
    }

    public void setOnChangeListener(OnChangeListener listener) {
        this.onChangeListener = listener;
    }

    public void setVisible() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0){
                    deleteTV.setVisibility(VISIBLE);
                }else {
                    deleteTV.setVisibility(GONE);
                }
                if (onChangeListener!=null){
                    onChangeListener.onTextChange(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setHint(String str){
        editText.setHint(str);
    }

    public String getText(){
        return editText.getText().toString();
    }

    public void setPwdInputType(){
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    public void setWordInputType(){
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    public void setText(String str){
        editText.setText(str);
    }

    public void setSelection(int index){
        editText.setSelection(index);
    }
}
