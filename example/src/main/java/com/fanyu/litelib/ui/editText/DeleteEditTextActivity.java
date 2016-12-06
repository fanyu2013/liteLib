package com.fanyu.litelib.ui.editText;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.fanyu.litelib.R;
import com.fanyu.litelibrary.DeleteEditText;

public class DeleteEditTextActivity extends AppCompatActivity {

    private DeleteEditText del_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_edit_text);
        del_et = (DeleteEditText) findViewById(R.id.del_et);
        del_et.setHint("你好 请输入");
        del_et.setOnChangeListener(new DeleteEditText.OnChangeListener() {
            @Override
            public void onTextChange(String text) {
                Toast.makeText(DeleteEditTextActivity.this,text,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
