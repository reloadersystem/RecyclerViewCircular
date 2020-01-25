package reloader.circlelayoutmanager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;

public class ActivityZoomScreen extends AppCompatActivity {

    private View background;

    //int xPosition, yPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        setContentView(R.layout.activity_zoom_screen);
        background = findViewById(R.id.background);


//        if (getIntent() != null) {
//            Bundle bundle = this.getIntent().getExtras();
//            xPosition = bundle.getInt("xPosition");
//            yPosition = bundle.getInt("yPosition");
//        }


        if (savedInstanceState == null) {

            background.setVisibility(View.INVISIBLE);

            final ViewTreeObserver viewTreeObserver = background.getViewTreeObserver();

            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        //circularLetTopShow();
                        circularCenterShow();
                        background.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    }
                });
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void circularCenterShow() {

        int cx = background.getWidth() / 2;
        int cy = background.getHeight() / 2;
        float finalRadius = (float) Math.hypot(cx, cy);
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, 0, finalRadius);
        circularReveal.setDuration(2000);
        background.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void circularLetTopShow() {
        int cx = background.getLeft() - getDips(44);
        //int cx = background.getRight() - getDips(44);
        //int cy = background.getBottom() - getDips(44);
        int cy = background.getTop() - getDips(44);

        float finalRadius = Math.max(background.getWidth(), background.getHeight());
        Animator circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, 0, finalRadius);
        circularReveal.setDuration(2000);
        background.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    private int getDips(int dps) {

        Resources resources = getResources();

        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                resources.getDisplayMetrics());
    }


    @Override
    public void onBackPressed() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = background.getWidth() - getDips(44);
            int cy = background.getBottom() - getDips(44);

            float finalRadius = Math.max(background.getWidth(), background.getHeight());
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, 0, finalRadius);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    super.onAnimationCancel(animation);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    background.setVisibility(View.INVISIBLE);
                    finish();
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    super.onAnimationRepeat(animation);
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                }

                @Override
                public void onAnimationPause(Animator animation) {
                    super.onAnimationPause(animation);
                }

                @Override
                public void onAnimationResume(Animator animation) {
                    super.onAnimationResume(animation);
                }
            });
            circularReveal.setDuration(1000);
            circularReveal.start();
            //super.onBackPressed();

        }else{
            super.onBackPressed();
        }
    }
}
