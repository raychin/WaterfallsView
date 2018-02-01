package com.ray.demo.refresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import com.r.refresh.layout.RyRefreshLayout;
import com.ray.demo.R;

/**
 * 上拉加载/下拉刷新demo演示
 * @author ray
 * @date 2018/01/25
 */
public class RefreshDemoActivity extends AppCompatActivity implements RyRefreshLayout.RyRefreshLayoutController, RyRefreshLayout.RyRefreshLayoutListener {

    private boolean loadMoreEnable = true;
    private RyRefreshLayout refreshLayout;
    private RecyclerView rvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        refreshLayout = (RyRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshLayoutController(this);
        refreshLayout.setRefreshLayoutListener(this);

        rvTest = (RecyclerView) findViewById(R.id.rv_test);
        TestRecyclerAdapter adapter = new TestRecyclerAdapter(this);
        rvTest.setAdapter(adapter);
    }

    @Override
    public boolean isPullRefreshEnable() {
        return true;
    }

    @Override
    public boolean isPullLoadEnable() {
        return loadMoreEnable;
    }

    @Override
    public void onRefresh() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.finishPullRefresh();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.finishPullLoad();
            }
        }, 1000);
    }
}
