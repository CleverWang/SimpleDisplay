package com.wangcong.simpledisplay.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wangcong.simpledisplay.R;

/**
 * 功能：主界面
 * <p>
 * 作者：Wang Cong
 * <p>
 * 时间：2018.12.6
 */
public class EnterActivity extends AppCompatActivity {
    private Button btn_experiment_4; // 实验四按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        // 绑定控件
        bindView();

        // 初始化数据
        initData();

        // 事件监听
        initEvent();
    }

    private void bindView() {
        btn_experiment_4 = findViewById(R.id.btn_experiment_4);
    }

    private void initData() {
    }

    private void initEvent() {
        btn_experiment_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 转跳到实验四配置展示界面
                Intent intent = new Intent(EnterActivity.this, ShowConfigActivity.class);
                startActivity(intent);
            }
        });
    }
}
