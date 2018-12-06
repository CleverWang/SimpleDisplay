package com.wangcong.simpledisplay.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wangcong.simpledisplay.R;
import com.wangcong.simpledisplay.utils.Const;

/**
 * 功能：实验四配置修改界面
 * <p>
 * 作者：Wang Cong
 * <p>
 * 时间：2018.12.6
 */
public class ChangeConfigActivity extends AppCompatActivity {
    private EditText edit_change_config; // 配置修改文本区域
    private Button btn_change_config_cancel; // 取消按钮
    private Button btn_change_config_confirm; // 确认按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_config);
        // 绑定控件
        bindView();

        // 初始化数据
        initData();

        // 事件监听
        initEvent();
    }

    private void bindView() {
        edit_change_config = findViewById(R.id.edit_change_config);
        btn_change_config_cancel = findViewById(R.id.btn_change_config_cancel);
        btn_change_config_confirm = findViewById(R.id.btn_change_config_confirm);

    }

    private void initData() {
        edit_change_config.setText(Const.CONFIGS_4); // 加载配置内容到文本区域
    }

    private void initEvent() {
        btn_change_config_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_change_config_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 确认后保存配置
                String config = edit_change_config.getText().toString();
                Const.CONFIGS_4 = config;
                finish();
            }
        });
    }
}
