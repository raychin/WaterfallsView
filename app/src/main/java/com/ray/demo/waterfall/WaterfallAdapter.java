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
import com.ray.demo.common.ScreenUtils;

import java.util.List;

/**
 * 瀑布流demo数据适配器
 * @author ray
 * @date 2018/02/01
 */
public class WaterfallAdapter extends BaseAdapter {

    private AppCompatActivity mActivity;
    private List<ImageBean> mData;
    private LayoutInflater inflater;

    public WaterfallAdapter(AppCompatActivity activity, List<ImageBean> data) {
        this.mActivity = activity;
        this.mData = data;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return this.mData.size();
    }

    @Override
    public ImageBean getItem(int position) {
        return this.mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ImageBean bean = this.getItem(position);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_waterfall, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.imageView.getLayoutParams();
        float itemWith = (ScreenUtils.getScreeWidth(mActivity) - 4 * 10) / 3;
        layoutParams.width = (int) itemWith;
        float scale = (itemWith + 0f) / bean.getWidth();
        layoutParams.height = (int) (bean.getHeight() * scale);
        holder.imageView.setLayoutParams(layoutParams);

        Glide.with(mActivity)
                .load(bean.getImgUrl())
                .override(layoutParams.width, layoutParams.height)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.imageView);
        return convertView;
    }

    private final class ViewHolder{
        ImageView imageView;
    }
}
