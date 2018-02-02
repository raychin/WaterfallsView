package com.ray.waterfalllib;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;

/**
 * 瀑布流布局
 * @author Ray
 * @date 2018/01/29
 */
public class WaterfallsFlowLayout extends LinearLayout {
    public WaterfallsFlowLayout(Context context) {
        super(context);
        initAttrs(context, null);
        init();
    }

    public WaterfallsFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    public WaterfallsFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WaterfallsFlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(context, attrs);
        init();
    }

    /**
     * 初始化控件属性
     * @param context 上下文
     * @param attrs 属性值
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        int resId;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RayWaterFallsLayout);
//        Resources resources = context.getResources();

        // 列数
        resId = ta.getResourceId(R.styleable.RayWaterFallsLayout_column, -1);
        column = ta.getInt(R.styleable.RayWaterFallsLayout_column, 1);

        // 是否通过测量最小高度判断子布局位置
        resId = ta.getResourceId(R.styleable.RayWaterFallsLayout_is_measure_height, -1);
        isMeasureHeight = ta.getBoolean(R.styleable.RayWaterFallsLayout_is_measure_height, false);

        // 列之间和控件上下左右间隔屏幕的距离
        resId = ta.getResourceId(R.styleable.RayWaterFallsLayout_interval, -1);
        interval = ta.getDimensionPixelOffset(R.styleable.RayWaterFallsLayout_interval, -1);
    }

    private LinearLayout rootLayout;
    private LayoutParams childParams;
    private LayoutParams childParams2;

    private void init() {
        rootLayout = new LinearLayout(getContext());
        rootLayout.setOrientation(LinearLayout.HORIZONTAL);
        rootLayout.setPadding(interval, interval, interval, 0);
        addView(rootLayout, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setColumn(column);
    }

    private ListAdapter mAdapter;

    public ListAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(ListAdapter adapter) {
        mAdapter = adapter;
        if(mAdapter != null) {
            mObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mObserver);
        }
        mItemCount = mAdapter.getCount();
        initItemView();
    }

    private AdapterDataSetObserver mObserver;
    public View getSelectedView() {
        if(getAdapter() == null || getAdapter().getCount() == 0) { return null; }
        return getAdapter().getView(selected, null, null);
    }

    private int selected = 0;
    public void setSelection(int position) {
        selected = position;
    }

    /**
     * 列之间和控件上下左右间隔屏幕的距离
     */
    private int interval = 5;

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    /**
     * 瀑布流列数，默认为1
     */
    private int column = 1;

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
        if(rootLayout != null) {
            rootLayout.removeAllViews();
            linearLayouts = new LinearLayout[column];
            int width = getScreeWidth() / column;
            childParams = new LayoutParams(width, LayoutParams.WRAP_CONTENT, 1.0f);
            childParams2 = new LayoutParams(width, LayoutParams.WRAP_CONTENT, 1.0f);
            childParams2.rightMargin = interval;
            for(int i = 0; i < column; i ++) {
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                rootLayout.addView(linearLayout, (column > 1 && (i != column - 1)) ? childParams2 : childParams);
                linearLayouts[i] = linearLayout;
            }
        }
    }

    private boolean mDataChanged = false;
    private int mOldItemCount = 0;
    private int mItemCount = 0;
    public class AdapterDataSetObserver extends DataSetObserver {
        private Parcelable mInstanceState = null;
        public AdapterDataSetObserver() { }

        @Override
        public void onChanged() {
            mDataChanged = true;
            mOldItemCount = mItemCount;
            mItemCount = getAdapter().getCount();
            initItemView();
        }

        @Override
        public void onInvalidated() {
            mDataChanged = true;
            mOldItemCount = mItemCount;
            mItemCount = getAdapter().getCount();
            initItemView();
            if(getAdapter().hasStableIds()) {
            }
        }
    }

    /**
     * 上一次添加item的子布局的位置
     */
    private int position = 0;

    /**
     * 是否通过计算子布局高度决定添加item的子布局位置，默认不判断
     */
    private boolean isMeasureHeight = false;

    public boolean isMeasureHeight() {
        return isMeasureHeight;
    }

    public void setMeasureHeight(boolean measureHeight) {
        isMeasureHeight = measureHeight;
    }

    private LinearLayout[] linearLayouts;
    private void initItemView() {
        if(linearLayouts == null || linearLayouts.length == 0) { return; }
        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        itemParams.bottomMargin = interval;
        int start = 0;
        if (mItemCount > mOldItemCount && mOldItemCount != 0) {
            start = (mItemCount - mOldItemCount) - 1;
        }
        for(int i = start; i < mItemCount; i ++) {
            View view = getAdapter().getView(i, null, null);
            view.setTag(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if(mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(rootLayout, v, pos, (long) mAdapter.getItemId(pos));
                    }
                }
            });
            view.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = (int) v.getTag();
                    if(mOnItemLongClickListener != null) {
                        mOnItemLongClickListener.onItemLongClick(rootLayout, v, pos, (long) mAdapter.getItemId(pos));
                    }
                    return true;
                }
            });

            if(isMeasureHeight) { position = minHeightView(); }

            linearLayouts[position].addView(view, itemParams);
            if(!isMeasureHeight) {
                position ++;
                if (position >= column) {
                    position = 0;
                }
            }
        }
    }

    /**
     * 计算控件高度
     * @param view
     * @return
     */
    private int calculateHeight (View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

    /**
     * 查找子布局高度最小的布局位置
     * @return
     */
    private int minHeightView(){
        if(linearLayouts == null || linearLayouts.length == 0) { return 0; }
        int minPosition = 0;
        int minHeight = calculateHeight(linearLayouts[minPosition]);
        for(int i = 0; i < linearLayouts.length; i ++) {
            int tempHeight = calculateHeight(linearLayouts[i]);
            if(minHeight > tempHeight) {
                minHeight = tempHeight;
                minPosition = i;
            }
        }
        return minPosition;
    }

    /**
     * 获取屏幕宽度
     * @return
     */
    private int getScreeWidth () {
        return ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
    }

    private OnItemClickListener mOnItemClickListener;
    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * @return The callback to be invoked with an item in this AdapterView has
     *         been clicked, or null id no callback has been set.
     */
    @Nullable
    public final OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * AdapterView has been clicked.
     */
    public interface OnItemClickListener {
        /**
         * Callback method to be invoked when an item in this AdapterView has
         * been clicked.
         * <p>
         * Implementers can call getItemAtPosition(position) if they need
         * to access the data associated with the selected item.
         *
         * @param parent The LinearLayout where the click happened.
         * @param view The view within the AdapterView that was clicked (this
         *            will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         * @param id The row id of the item that was clicked.
         */
        void onItemClick(LinearLayout parent, View view, int position, long id);
    }

    private OnItemLongClickListener mOnItemLongClickListener;
    /**
     * Interface definition for a callback to be invoked when an item in this
     * view has been clicked and held.
     */
    public interface OnItemLongClickListener {
        /**
         * Callback method to be invoked when an item in this view has been
         * clicked and held.
         *
         * Implementers can call getItemAtPosition(position) if they need to access
         * the data associated with the selected item.
         *
         * @param parent The LinearLayout where the click happened
         * @param view The view within the AbsListView that was clicked
         * @param position The position of the view in the list
         * @param id The row id of the item that was clicked
         *
         * @return true if the callback consumed the long click, false otherwise
         */
        boolean onItemLongClick(LinearLayout parent, View view, int position, long id);
    }


    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked and held
     *
     * @param listener The callback that will run
     */
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        if (!isLongClickable()) {
            setLongClickable(true);
        }
        mOnItemLongClickListener = listener;
    }

    /**
     * @return The callback to be invoked with an item in this AdapterView has
     *         been clicked and held, or null id no callback as been set.
     */
    public final OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }
}
