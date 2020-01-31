package reloader.circlelayoutmanager;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import reloader.circlelayoutmanager.BottomRecycler.RecyclerAdapterBottom;
import reloader.circlelayoutmanager.BottomRecycler.mIconBottom;

public class MainActivity extends AppCompatActivity {

    private boolean isCircle = true;

    private List<mIconModel> listIcon;
    private List<mIconBottom> lisIconBottom;


    RecyclerAdapterCircle recyclerAdapterCircle;
    RecyclerView recyclerView, recyclerBottom;
    LinearLayout ln_contenedor;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerAdapterBottom recyclerAdapterBottom;

    ImageView img_central;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listIcon = new ArrayList<>();
        lisIconBottom = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        ln_contenedor = (LinearLayout) findViewById(R.id.ln_contenedor);
        recyclerBottom = (RecyclerView) findViewById(R.id.recyclerBottom);

        //int myColor = Color.parseColor("#ff00ff");
        //swipeRefreshLayout.setProgressBackgroundColorSchemeColor(myColor);


//        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.YELLOW);
//        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
//        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.colorIconAlter);

        loadRecyclerView();

        loadRecyclerBottom();

        img_central = (ImageView) findViewById(R.id.img_central);

        //final ScrollZoomLayoutManager scrollZoomLayoutManager = new ScrollZoomLayoutManager(this,Dp2px(10));
        //recyclerView.addOnScrollListener(new CenterScrollListener());

        img_central.setImageResource(R.drawable.primaria7);

        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;


        //FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isCircle){
//                    recyclerView.setLayoutManager(scrollZoomLayoutManager);
//                }else{
//                    recyclerView.setLayoutManager(circleLayoutManager);
//                }
//                isCircle = !isCircle;
//            }
//        });
    }

    private void loadRecyclerBottom() {

        lisIconBottom.add(new mIconBottom(R.drawable.panelprincipal7, "Física"));
        lisIconBottom.add(new mIconBottom(R.drawable.panelprincipal8, "Química"));
        lisIconBottom.add(new mIconBottom(R.drawable.panelprincipal9, "Biologia"));
        lisIconBottom.add(new mIconBottom(R.drawable.panelprincipal10, "Lenguaje"));

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerAdapterBottom = new RecyclerAdapterBottom(this, lisIconBottom);
        recyclerBottom.setLayoutManager(layoutManager);
        recyclerBottom.setAdapter(recyclerAdapterBottom);
    }


    private void loadRecyclerView() {

        listAddIcon();
        //listAddIconSocial();

        recyclerAdapterCircle = new RecyclerAdapterCircle(listIcon, this);
        final CircleLayoutManager circleLayoutManager = new CircleLayoutManager(this);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        recyclerView.setLayoutManager(circleLayoutManager);
        recyclerView.setAdapter(recyclerAdapterCircle);
    }

    private void listAddIconSocial() {
        listIcon.add(new mIconModel(R.drawable.image1, "Física"));
        listIcon.add(new mIconModel(R.drawable.image2, "Química"));
        listIcon.add(new mIconModel(R.drawable.image3, "Algebra"));
        listIcon.add(new mIconModel(R.drawable.image4, "Trigonometría"));
        listIcon.add(new mIconModel(R.drawable.image5, "Biología"));
        listIcon.add(new mIconModel(R.drawable.image6, "Psicología"));
        listIcon.add(new mIconModel(R.drawable.image7, "Historia del Perú"));
        listIcon.add(new mIconModel(R.drawable.image8, "Historia Universal"));
        listIcon.add(new mIconModel(R.drawable.image9, "Literatura"));
        listIcon.add(new mIconModel(R.drawable.image10, "Inglés"));
        listIcon.add(new mIconModel(R.drawable.image11, "Aritmética"));
        listIcon.add(new mIconModel(R.drawable.image12, "Razonamiento Matemático"));

    }

    private void listAddIcon() {
        listIcon.add(new mIconModel(R.drawable.image1, "Física"));
        listIcon.add(new mIconModel(R.drawable.image2, "Química"));
        listIcon.add(new mIconModel(R.drawable.image3, "Biologia"));
        listIcon.add(new mIconModel(R.drawable.image4, "Lenguaje"));
        listIcon.add(new mIconModel(R.drawable.image5, "Diverti Aprendo"));
        listIcon.add(new mIconModel(R.drawable.image6, "Inglés"));
//        listIcon.add(new mIconModel(R.drawable.helico3, "Algebra"));
//        listIcon.add(new mIconModel(R.drawable.helico4, "Trigonometría"));
//        listIcon.add(new mIconModel(R.drawable.helico5, "Biología"));
//        listIcon.add(new mIconModel(R.drawable.helico6, "Psicología"));
//        listIcon.add(new mIconModel(R.drawable.helico7, "Historia del Perú"));
//        listIcon.add(new mIconModel(R.drawable.helico8, "Historia Universal"));
//        listIcon.add(new mIconModel(R.drawable.helico9, "Literatura"));
//        listIcon.add(new mIconModel(R.drawable.helico10, "Inglés"));
//        listIcon.add(new mIconModel(R.drawable.helico11, "Aritmética"));
//        listIcon.add(new mIconModel(R.drawable.helico12, "Razonamiento Matemático"));
    }

    public int Dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.my_image, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            //int index = (position+1)%6;
            int index = (position + 1) % 12;
            int res = 0;
            switch (index) {
                case 0:
                    res = R.drawable.helico1;

                    break;
                case 1:
                    res = R.drawable.helico2;
                    break;
                case 2:
                    res = R.drawable.helico3;
                    break;
                case 3:
                    res = R.drawable.helico4;
                    break;
                case 4:
                    res = R.drawable.helico5;
                    break;
                case 5:
                    res = R.drawable.helico6;
                    break;

                case 6:
                    res = R.drawable.helico7;
                    break;

                case 7:
                    res = R.drawable.helico8;
                    break;

                case 8:
                    res = R.drawable.helico8;
                    break;

                case 9:
                    res = R.drawable.helico8;
                    break;

                case 10:
                    res = R.drawable.helico8;
                    break;

                case 11:
                    res = R.drawable.helico4;
                    break;

            }
            ((MyViewHolder) holder).imageView.setImageResource(res);
        }

        @Override
        public int getItemCount() {
            return 12;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image);
            }
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        //| View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
