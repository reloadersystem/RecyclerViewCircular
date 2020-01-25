package reloader.circlelayoutmanager;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapterCircle extends RecyclerView.Adapter<RecyclerAdapterCircle.ViewHolder> {
    private List<mIconModel> listIcon;
    private Context mCtx;
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;

    View viewicon;

    public RecyclerAdapterCircle(List<mIconModel> listIcon, Context mCtx) {
        this.listIcon = listIcon;
        this.mCtx = mCtx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_image, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @Override
    public void onBindViewHolder(final RecyclerAdapterCircle.ViewHolder holder, final int position) {

//        int index = (position + 1) % 12;
//        int res = 0;
        loadAnimations();
        changeCameraDistance(holder.iconImg, holder.iconImg2);

        holder.setIsRecyclable(false);

        holder.iconImg.setImageResource(listIcon.get(position).getImgmenu());

        final View viewScale = holder.iconImg;

        int num = listIcon.size();

        /*if (num % 2 == position) { // pinta cada 7 elementos al iniciar
            holder.iconImg.setCircleBackgroundColorResource(R.color.colorIconAlter);
            // holder.itemView.setBackgroundColor(Color.parseColor("#373737"));
        }*/

        scaleFromBegin(holder);
        ejecutarAnimacion(holder);

        holder.iconImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mIsBackVisible) {
                    mSetRightOut.setTarget(holder.iconImg);  //Establece el objeto de destino para todos los actuales child animations de este AnimatorSet que toman objetivos ( ObjectAnimatory AnimatorSet).
                    mSetLeftIn.setTarget(holder.iconImg);
                    mSetRightOut.start();
                    mSetLeftIn.start();
                    mIsBackVisible = true;
                    openActivity(position);

                } else {
                    mSetRightOut.setTarget(holder.iconImg);
                    mSetLeftIn.setTarget(holder.iconImg);
                    mSetRightOut.start();
                    mSetLeftIn.start();
                    mIsBackVisible = false;
                    openActivity(position);
                }
            }
        });
    }

    private void scaleFromBegin(ViewHolder holder) {
        int cx = holder.iconImg.getWidth();
        int cy = holder.iconImg.getHeight();

        viewicon = holder.iconImg;
        ScaleAnimation animation = new ScaleAnimation(cx, cx + 1, cy, cy + 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        viewicon.startAnimation(animation);
    }

    private void ejecutarAnimacion(final ViewHolder holder) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                girarIcon(holder);
                //handler.postDelayed(this, 2000); //ejecutar despues del efecto
            }
        }, 4000);
    }

    private void openActivity(final int position) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String materia = listIcon.get(position).getMateria();
                Toast.makeText(mCtx, materia, Toast.LENGTH_SHORT).show();
                //int xImgPosition = holder.iconImg.getLeft();
                //int yImgPosition = holder.iconImg.getTop();
                Intent intent = new Intent(mCtx, ActivityZoomScreen.class);
                //intent.putExtra("xPosition", xImgPosition);
                //intent.putExtra("yPosition", yImgPosition);
                mCtx.startActivity(intent);
                //handler.postDelayed(this, 2000); //ejecutar despues del efecto
            }
        }, 1000);
    }

    private void girarIcon(ViewHolder holder) {

        viewicon = holder.iconImg;

        RotateAnimation animation = new RotateAnimation(-350, 0, RotateAnimation.RELATIVE_TO_SELF,
                0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(4000);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        viewicon.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return listIcon.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView iconImg;
        CircleImageView iconImg2;

        public ViewHolder(View itemView) {
            super(itemView);

            iconImg = (CircleImageView) itemView.findViewById(R.id.image);
            iconImg2 = (CircleImageView) itemView.findViewById(R.id.image2);
        }
    }

    private void changeCameraDistance(ImageView img1, ImageView img2) {
        int distance = 5000;
        float scale = mCtx.getResources().getDisplayMetrics().density * distance;
        img1.setCameraDistance(scale);
        img2.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(mCtx, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(mCtx, R.animator.in_animation);
    }
}
