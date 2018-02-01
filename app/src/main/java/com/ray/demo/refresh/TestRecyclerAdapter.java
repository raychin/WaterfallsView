package com.ray.demo.refresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.ray.demo.R;

/**
 * 数据适配器
 * @author ray
 * @date 2018/02/01
 */
public class TestRecyclerAdapter extends RecyclerView.Adapter<TestRecyclerAdapter.TestViewHolder> {

    private Context context;

    public TestRecyclerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestViewHolder(LayoutInflater.from(context).inflate(R.layout.item_test,
                parent, false));
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, final int position) {
        holder.textView.setText("Item Data " + position);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You have click position: " + position, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    static class TestViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public TestViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}
