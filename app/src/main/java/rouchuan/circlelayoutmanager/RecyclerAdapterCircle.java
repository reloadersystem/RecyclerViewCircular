package rouchuan.circlelayoutmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapterCircle extends RecyclerView.Adapter<RecyclerAdapterCircle.ViewHolder> {
    private List<mIconModel> listIcon;
    private Context mCtx;
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

        holder.setIsRecyclable(false);

        holder.iconImg.setImageResource(listIcon.get(position).getImgmenu());

        int num = listIcon.size();

        /*if (num % 2 == position) { // pinta cada 7 elementos al iniciar
            holder.iconImg.setCircleBackgroundColorResource(R.color.colorIconAlter);
            // holder.itemView.setBackgroundColor(Color.parseColor("#373737"));
        }*/


        int cx = holder.iconImg.getWidth();
        int cy = holder.iconImg.getHeight();

        viewicon = holder.iconImg;
        ScaleAnimation animation = new ScaleAnimation(cx, cx + 1, cy, cy + 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        viewicon.startAnimation(animation);

        ejecutarAnimacion(holder);

        holder.iconImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String materia = listIcon.get(position).getMateria();
                Toast.makeText(mCtx, materia, Toast.LENGTH_SHORT).show();
            }
        });


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

        public ViewHolder(View itemView) {
            super(itemView);

            iconImg = (CircleImageView) itemView.findViewById(R.id.image);
        }
    }


}
