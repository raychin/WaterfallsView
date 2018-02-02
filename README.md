# WaterfallsView
瀑布流控件

![image1](./pic/Screenshot_20180201-180530.png)

使用方法：

```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".waterfall.WaterFallsActivity">
        <com.ray.waterfalllib.WaterfallsFlowLayout
                android:id="@+id/waterfallsFlowLayout"
                app:column="3"
                app:interval="10dp"
                app:is_measure_height="true"
                android:layout_width="match_parent"
                android:layout_height="match_parent" />
</ScrollView>
```

需要使用ScrollView等控件包进去，同时需要应用xmlns:app="http://schemas.android.com/apk/res-auto"

app:column设置瀑布流列数

app:interval="10dp"设置间距，包含控件上下左右及列与列之间的间距

app:is_measure_height="true"设置是否通过计算每列的长度增加item，默认为false通过顺序增加item

activity中：

```java
WaterfallAdapter adapter = new WaterfallAdapter(WaterFallsActivity.this, beans);
        waterfallsFlowLayout.setAdapter(adapter);
        waterfallsFlowLayout.setOnItemClickListener(new WaterfallsFlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(LinearLayout parent, View view, int position, long id) {
                Toast.makeText(WaterFallsActivity.this, "第" + position +"项被点击", Toast.LENGTH_SHORT).show();
            }
        });
        waterfallsFlowLayout.setOnItemLongClickListener(new WaterfallsFlowLayout.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(LinearLayout parent, View view, int position, long id) {
                Toast.makeText(WaterFallsActivity.this, "第" + position +"项被长按", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
```

上拉加载或者刷新控件时，使用adapter.notifyDataSetChanged();或者adapter.notifyDataSetInvalidated();刷新数据。