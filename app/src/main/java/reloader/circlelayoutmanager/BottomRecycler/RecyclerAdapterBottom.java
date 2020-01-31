package reloader.circlelayoutmanager.BottomRecycler;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import reloader.circlelayoutmanager.ActivityZoomScreen;
import reloader.circlelayoutmanager.R;

public class RecyclerAdapterBottom extends RecyclerView.Adapter<RecyclerAdapterBottom.MyViewHolder> {

    private Context mContext;
    private List<mIconBottom> mIconBottoms;
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;

    View viewicon;

    public RecyclerAdapterBottom(Context mContext, List<mIconBottom> mIconBottoms) {
        this.mContext = mContext;
        this.mIconBottoms = mIconBottoms;
    }

    @Override
    public RecyclerAdapterBottom.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        // view = mInflater.inflate(R.layout.item_balotarios, parent, false);
        view = mInflater.inflate(R.layout.item_iconsbottom, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterBottom.MyViewHolder holder, final int position) {

        loadAnimations();
        changeCameraDistance(holder.img_bottom);
        holder.img_bottom.setImageResource(mIconBottoms.get(position).getModulo());
        scaleFromBegin(holder);

        holder.img_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsBackVisible) {
                    mSetRightOut.setTarget(holder.img_bottom);  //Establece el objeto de destino para todos los actuales child animations de este AnimatorSet que toman objetivos ( ObjectAnimatory AnimatorSet).
                    mSetLeftIn.setTarget(holder.img_bottom);
                    mSetRightOut.start();
                    mSetLeftIn.start();
                    mIsBackVisible = true;
                    openActivity(position);

                } else {
                    mSetRightOut.setTarget(holder.img_bottom);
                    mSetLeftIn.setTarget(holder.img_bottom);
                    mSetRightOut.start();
                    mSetLeftIn.start();
                    mIsBackVisible = false;
                    openActivity(position);
                }
            }
        });
    }

    private void openActivity(final int position) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String materia = String.valueOf(position);
                Toast.makeText(mContext, materia, Toast.LENGTH_SHORT).show();
                //int xImgPosition = holder.iconImg.getLeft();
                //int yImgPosition = holder.iconImg.getTop();
                Intent intent = new Intent(mContext, ActivityZoomScreen.class);
                //intent.putExtra("xPosition", xImgPosition);
                //intent.putExtra("yPosition", yImgPosition);
                mContext.startActivity(intent);
                //handler.postDelayed(this, 2000); //ejecutar despues del efecto
            }
        }, 1000);
    }


    private void scaleFromBegin(MyViewHolder holder) {

        int cx = holder.img_bottom.getWidth();
        int cy = holder.img_bottom.getHeight();

        viewicon = holder.img_bottom;
        ScaleAnimation animation = new ScaleAnimation(cx, cx + 1, cy, cy + 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        viewicon.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return mIconBottoms.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_bottom;

        public MyViewHolder(View itemView) {
            super(itemView);
            img_bottom = (ImageView) itemView.findViewById(R.id.img_bottom);
        }
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.in_animation);
    }

    private void changeCameraDistance(ImageView img1) {
        int distance = 5000;
        float scale = mContext.getResources().getDisplayMetrics().density * distance;
        img1.setCameraDistance(scale);
    }
}
