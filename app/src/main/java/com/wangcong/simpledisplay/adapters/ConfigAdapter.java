package com.wangcong.simpledisplay.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wangcong.simpledisplay.R;
import com.wangcong.simpledisplay.beans.ConfigBean;

import java.util.List;

/**
 * 功能：实验四配置界面列表的适配器
 * <p>
 * 作者：Wang Cong
 * <p>
 * 时间：2018.12.6
 */
public class ConfigAdapter extends RecyclerView.Adapter<ConfigAdapter.ViewHolder> {

    private List<ConfigBean> configBeanList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View configView;
        TextView text_config_deviceId; // deviceId显示文本
        TextView text_config_serviceId; // serviceId显示文本
        TextView text_config_count; // 数据点数显示文本

        public ViewHolder(View view) {
            super(view);
            configView = view;
            text_config_deviceId = view.findViewById(R.id.text_config_deviceId);
            text_config_serviceId = view.findViewById(R.id.text_config_serviceId);
            text_config_count = view.findViewById(R.id.text_config_count);
        }
    }

    public ConfigAdapter(List<ConfigBean> configBeanList) {
        this.configBeanList = configBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_config, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.configView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                ConfigBean configBean = configBeanList.get(position);
                // TODO: 完成转跳

                Toast.makeText(v.getContext(), "you clicked view " + configBean, Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ConfigBean configBean = configBeanList.get(position);
        // 填充数据到单个列表项
        holder.text_config_deviceId.setText(configBean.getDeviceId());
        holder.text_config_serviceId.setText(configBean.getServiceId());
        holder.text_config_count.setText(configBean.getCount());
    }

    @Override
    public int getItemCount() {
        return configBeanList.size();
    }

}
