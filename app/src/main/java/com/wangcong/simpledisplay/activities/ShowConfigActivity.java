package com.wangcong.simpledisplay.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.wangcong.simpledisplay.R;
import com.wangcong.simpledisplay.adapters.ConfigAdapter;
import com.wangcong.simpledisplay.beans.ConfigBean;
import com.wangcong.simpledisplay.utils.Const;
import com.wangcong.simpledisplay.utils.DataExtractUtil;

import java.util.List;

/**
 * 功能：实验四展示配置界面
 * <p>
 * 作者：Wang Cong
 * <p>
 * 时间：2018.12.6
 */
public class ShowConfigActivity extends AppCompatActivity {
    private RecyclerView recycle_config_display; // 配置展示列表
    private Button btn_change_config; // 修改配置按钮

    private List<ConfigBean> configBeanList;
    private ConfigAdapter configAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_config);

        // 绑定控件
        bindView();

        // 初始化数据
        initData();

        // 事件监听
        initEvent();
    }

    private void bindView() {
        recycle_config_display = findViewById(R.id.recycle_config_display);
        recycle_config_display.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        btn_change_config = findViewById(R.id.btn_change_config);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycle_config_display.setLayoutManager(layoutManager);
    }

    private void initData() {
        // 从SharedPreferences读取配置
        SharedPreferences sharedPreferences = getSharedPreferences("experiment4", MODE_PRIVATE);

        // 从SharedPreferences加载配置
        Const.CONFIGS_4 = sharedPreferences.getString("configs", Const.CONFIGS_4_TEST); // 加载失败使用测试配置
//        Const.CONFIGS_4 = sharedPreferences.getString("configs", ""); // 加载失败使用空配置

        configBeanList = DataExtractUtil.configExtractor(Const.CONFIGS_4); // 解析保存的配置
        configAdapter = new ConfigAdapter(configBeanList);
        recycle_config_display.setAdapter(configAdapter);
    }

    private void initEvent() {
        btn_change_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 转跳到配置修改界面
                Intent intent = new Intent(ShowConfigActivity.this, ChangeConfigActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 配置修改完以后，重新加载配置列表
        List<ConfigBean> temp = DataExtractUtil.configExtractor(Const.CONFIGS_4);
        configBeanList.clear();
        configBeanList.addAll(temp);
        configAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 保存配置到SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("experiment4", MODE_PRIVATE).edit();
        editor.putString("configs", Const.CONFIGS_4);
        editor.apply();
    }
}
