package com.ray.demo.waterfall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ray.demo.R;
import com.ray.waterfalllib.WaterfallsFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 瀑布流demo演示
 * @author ray
 * @date 2018/02/01
 */
public class WaterFallsActivity extends AppCompatActivity {
    public final static String[] imageUrls = new String[] {
            "https://picsum.photos/200/300/?image=0",
            "https://picsum.photos/400/300/?image=1",
            "https://picsum.photos/200/180/?image=2",
            "https://picsum.photos/160/40/?image=3",
            "https://picsum.photos/200/300/?image=4",
            "https://picsum.photos/400/180/?image=5",
            "https://picsum.photos/160/300/?image=6",
            "https://picsum.photos/150/300/?image=7",
            "https://picsum.photos/200/300/?image=8",
            "https://picsum.photos/200/220/?image=9",
            "https://picsum.photos/240/380/?image=10" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 布局实现
        setContentView(R.layout.activity_waterfall);

        WaterfallsFlowLayout waterfallsFlowLayout = (WaterfallsFlowLayout) findViewById(R.id.waterfallsFlowLayout);

//        // 代码实现
//        WaterfallsFlowLayout waterfallsFlowLayout = new WaterfallsFlowLayout(WaterFallsActivity.this);
//        waterfallsFlowLayout.setColumn(3);
//        waterfallsFlowLayout.setMeasureHeight(false);
//
//        ScrollView scrollView = new ScrollView(WaterFallsActivity.this);
//        scrollView.addView(waterfallsFlowLayout,
//                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        setContentView(scrollView);

        List<ImageBean> beans = new ArrayList<ImageBean>();
        ImageBean bean = new ImageBean(imageUrls[0], 200, 300);
        beans.add(bean);
        bean = new ImageBean(imageUrls[1], 400, 300);
        beans.add(bean);
        bean = new ImageBean(imageUrls[2], 200, 180);
        beans.add(bean);
        bean = new ImageBean(imageUrls[3], 160, 40);
        beans.add(bean);
        bean = new ImageBean(imageUrls[4], 200, 300);
        beans.add(bean);
        bean = new ImageBean(imageUrls[5], 400, 180);
        beans.add(bean);
        bean = new ImageBean(imageUrls[6], 160, 300);
        beans.add(bean);
        bean = new ImageBean(imageUrls[7], 150, 300);
        beans.add(bean);
        bean = new ImageBean(imageUrls[8], 200, 300);
        beans.add(bean);
        bean = new ImageBean(imageUrls[9], 200, 220);
        beans.add(bean);
        bean = new ImageBean(imageUrls[10], 240, 380);
        beans.add(bean);

        WaterfallAdapter adapter = new WaterfallAdapter(WaterFallsActivity.this, beans);
        waterfallsFlowLayout.setAdapter(adapter);
    }
}
