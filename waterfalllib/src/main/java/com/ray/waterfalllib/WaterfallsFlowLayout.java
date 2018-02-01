package com.ray.waterfalllib;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Parcelable;
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
    }

    private LinearLayout rootLayout;
    private LayoutParams childParams;

    private void init() {
        rootLayout = new LinearLayout(getContext());
        rootLayout.setOrientation(LinearLayout.HORIZONTAL);
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
//        requestLayout();
        mItemCount = mAdapter.getCount();
        initItemView();
    }

    private AdapterDataSetObserver mObserver;
    public View getSelectedView() {
        if(getAdapter() == null || getAdapter().getCount() ==0) { return null; }
        return getAdapter().getView(selected, null, null);
    }

    private int selected = 0;
    public void setSelection(int position) {
        selected = position;
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
            childParams = new LayoutParams(getScreeWidth() / column, LayoutParams.WRAP_CONTENT, 1.0f);
            for(int i = 0; i < column; i ++) {
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                rootLayout.addView(linearLayout, childParams);
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
        int start = 0;
        if (mItemCount > mOldItemCount && mOldItemCount != 0) {
            start = (mItemCount - mOldItemCount) - 1;
        }
        for(int i = start; i < mItemCount; i ++) {
            View view = getAdapter().getView(i, null, null);

            if(isMeasureHeight) { position = mixHeightView(); }

            linearLayouts[position].addView(view);
            if(!isMeasureHeight) {
                position++;
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
    private int mixHeightView(){
        if(linearLayouts == null || linearLayouts.length == 0) { return 0; }
        int mixPosition = 0;
        int mixHeight = calculateHeight(linearLayouts[mixPosition]);
        for(int i = 1; i < linearLayouts.length; i ++) {
            int tempHeight = calculateHeight(linearLayouts[i]);
            if(mixHeight > tempHeight) {
                mixHeight = tempHeight;
                mixPosition = i;
            }
        }
        return mixPosition;
    }

    private int getScreeWidth () {
        return ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
    }
}
