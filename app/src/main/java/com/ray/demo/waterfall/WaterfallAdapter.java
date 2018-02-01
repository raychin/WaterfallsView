package com.ray.demo.waterfall;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.ray.demo.R;

/**
 * 瀑布流demo数据适配器
 * @author ray
 * @date 2018/02/01
 */
public class WaterfallAdapter extends BaseAdapter {

    private AppCompatActivity mActivity;
    private String[] mData;
    private LayoutInflater inflater;

    public WaterfallAdapter(AppCompatActivity activity, String[] data) {
        this.mActivity = activity;
        this.mData = data;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return this.mData.length;
    }

    @Override
    public String getItem(int position) {
        return this.mData[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_waterfall, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mActivity)
                .load(mData[position])
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imageView);
        return convertView;
    }

    private final class ViewHolder{
        ImageView imageView;
    }
}
