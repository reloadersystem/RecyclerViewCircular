package reloader.circlelayoutmanager;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Reloader
 */
public class CircleLayoutManager extends RecyclerView.LayoutManager {

    private static int INTERVAL_ANGLE = 60;
    private static float DISTANCE_RATIO = 10f;
    private static int SCROLL_LEFT = 1;
    private static int SCROLL_RIGHT = 2;

    private Context context;
    private int mDecoratedChildWidth;
    private int mDecoratedChildHeight;
    private int startLeft;
    private int startTop;
    private int mRadius;
    private int intervalAngle;
    private float offsetRotate;

    private int minRemoveDegree;
    private int maxRemoveDegree;

    private int contentOffsetX = -1;
    private int contentOffsetY = -1;

    private int firstChildRotate = 0;

    private SparseBooleanArray itemAttached = new SparseBooleanArray();
    private SparseArray<Float> itemsRotate = new SparseArray<>();

    private float maxScale;
    private static final float SCALE_RATE = 1.3f;


    public CircleLayoutManager(Context context) {
        this.context = context;
        intervalAngle = INTERVAL_ANGLE;
        offsetRotate = 0;
//        minRemoveDegree = -90;
        minRemoveDegree = -330;
        maxRemoveDegree = 330;
//        maxRemoveDegree = 90;
        maxScale = SCALE_RATE;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            offsetRotate = 0;
            return;
        }

        if (getChildCount() == 0) {
            View scrap = recycler.getViewForPosition(0);
            addView(scrap);
            measureChildWithMargins(scrap, 0, 0);
            mDecoratedChildWidth = getDecoratedMeasuredWidth(scrap);
            mDecoratedChildHeight = getDecoratedMeasuredHeight(scrap);
            startLeft = contentOffsetX == -1 ? (getHorizontalSpace() - mDecoratedChildWidth) / 2 : contentOffsetX;
            startTop = contentOffsetY == -1 ? 0 : contentOffsetY;
            mRadius = mDecoratedChildHeight;
            detachAndScrapView(scrap, recycler);
        }

        float rotate = firstChildRotate;
        for (int i = 0; i < getItemCount(); i++) {
            itemsRotate.put(i, rotate);
            itemAttached.put(i, false);
            rotate += intervalAngle;
        }

        detachAndScrapAttachedViews(recycler);
        fixRotateOffset();
        layoutItems(recycler, state);
    }

    private void layoutItems(RecyclerView.Recycler recycler, RecyclerView.State state) {
        layoutItems(recycler, state, SCROLL_RIGHT);
    }

    private void layoutItems(RecyclerView.Recycler recycler,
                             RecyclerView.State state, int oritention) {
        if (state.isPreLayout()) return;

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int position = getPosition(view);
            if (itemsRotate.get(position) - offsetRotate > maxRemoveDegree
                    || itemsRotate.get(position) - offsetRotate < minRemoveDegree) {
                itemAttached.put(position, false);
                removeAndRecycleView(view, recycler);
            }
        }

        for (int i = 0; i < getItemCount(); i++) {
            if (itemsRotate.get(i) - offsetRotate <= maxRemoveDegree
                    && itemsRotate.get(i) - offsetRotate >= minRemoveDegree) {
                if (!itemAttached.get(i)) {
                    View scrap = recycler.getViewForPosition(i);
                    measureChildWithMargins(scrap, 0, 0);
                    if (oritention == SCROLL_LEFT)
                        addView(scrap, 0);
                    else
                        addView(scrap);
                    float rotate = itemsRotate.get(i) - offsetRotate;
                    int left = calLeftPosition(rotate);
                    int top = calTopPosition(rotate);
                    scrap.setRotation(rotate);
                    layoutDecorated(scrap, startLeft + left, startTop + top,
                            startLeft + left + mDecoratedChildWidth, startTop + top + mDecoratedChildHeight);
                    itemAttached.put(i, true);


                    Log.v("RADIO", String.valueOf(getRadius()));
                }
            }
        }
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int willScroll = dx;

        float theta = dx / DISTANCE_RATIO;
        float targetRotate = offsetRotate + theta;

        if (targetRotate < 0) {
            willScroll = (int) (-offsetRotate * DISTANCE_RATIO);
        } else if (targetRotate > getMaxOffsetDegree()) {
            willScroll = (int) ((getMaxOffsetDegree() - offsetRotate) * DISTANCE_RATIO);
        }
        theta = willScroll / DISTANCE_RATIO;

        offsetRotate += theta;

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            float newRotate = view.getRotation() - theta;
            float scale = calculateScale(view.getLeft());
            view.setScaleX(scale % 10f); //TODO scala habilitando
            view.setScaleY(scale % 10f);
            int offsetX = calLeftPosition(newRotate);
            int offsetY = calTopPosition(newRotate);
            layoutDecorated(view, startLeft + offsetX, startTop + offsetY,
                    startLeft + offsetX + mDecoratedChildWidth, startTop + offsetY + mDecoratedChildHeight);
            view.setRotation(newRotate);
        }

        if (dx < 0)
            layoutItems(recycler, state, SCROLL_LEFT);
        else
            layoutItems(recycler, state, SCROLL_RIGHT);
        return willScroll;
    }

    private float calculateScale(int x) {
        int deltaX = Math.abs(x - (getHorizontalSpace() - mDecoratedChildWidth) / 2);
        float diff = 0f;
        if ((mDecoratedChildWidth - deltaX) > 0) diff = mDecoratedChildWidth - deltaX;
        return (maxScale - 1f) / mDecoratedChildWidth * diff + 1;
//TODO ESCALAR
    }

    private int calLeftPosition(float rotate) {
        return (int) (mRadius * Math.cos(Math.toRadians(180 - rotate)));
    }


    private int calTopPosition(float rotate) {
        return (int) (mRadius - mRadius * Math.sin(Math.toRadians(180 - rotate)));
        //return (int) (mRadius - mRadius * Math.sin(Math.toRadians(210 - rotate))); //eclipse
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingRight() - getPaddingLeft();
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }


    private void fixRotateOffset() {
        if (offsetRotate < 0) {
            offsetRotate = 0;
        }
        if (offsetRotate > getMaxOffsetDegree()) {
            offsetRotate = getMaxOffsetDegree();
        }
    }


    private float getMaxOffsetDegree() {
        return (getItemCount() - 1) * intervalAngle;
    }

    private PointF computeScrollVectorForPosition(int targetPosition) {
        if (getChildCount() == 0) {
            return null;
        }
        final int firstChildPos = getPosition(getChildAt(0));
        final int direction = targetPosition < firstChildPos ? -1 : 1;
        return new PointF(direction, 0);
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public void scrollToPosition(int position) {
        if (position < 0 || position > getItemCount() - 1) return;
        float targetRotate = position * intervalAngle;
        if (targetRotate == offsetRotate) return;
        offsetRotate = targetRotate;
        fixRotateOffset();
        requestLayout();
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(context) {
            @Override
            public PointF computeScrollVectorForPosition(int targetPosition) {
                return CircleLayoutManager.this.computeScrollVectorForPosition(targetPosition);
            }
        };
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    @Override
    public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) {
        removeAllViews();
        offsetRotate = 0;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    public int getCurrentPosition() {
        return Math.round(offsetRotate / intervalAngle);
    }


    public int getOffsetCenterView() {
        return (int) ((getCurrentPosition() * intervalAngle - offsetRotate) * DISTANCE_RATIO);
    }


    public int getRadius() {
        return mRadius;
    }


    public void setRadius(int mRadius) {
        this.mRadius = mRadius;
    }


    public int getIntervalAngle() {
        return intervalAngle;
    }


    public void setIntervalAngle(int intervalAngle) {
        this.intervalAngle = intervalAngle;
    }


    public int getContentOffsetX() {
        return contentOffsetX;
    }


    public void setContentOffsetX(int contentOffsetX) {
        this.contentOffsetX = contentOffsetX;
    }


    public int getContentOffsetY() {
        return contentOffsetY;
    }


    public void setContentOffsetY(int contentOffsetY) {
        this.contentOffsetY = contentOffsetY;
    }


    public int getFirstChildRotate() {
        return firstChildRotate;
    }


    public void setFirstChildRotate(int firstChildRotate) {
        this.firstChildRotate = firstChildRotate;
    }
    public void setDegreeRangeWillShow(int min, int max) {
        if (min > max) return;
        minRemoveDegree = min;
        maxRemoveDegree = max;
    }
}
