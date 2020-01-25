package reloader.circlelayoutmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private boolean isCircle = true;

    private List<mIconModel> listIcon;
    RecyclerAdapterCircle recyclerAdapterCircle;
    RecyclerView recyclerView;
    FrameLayout fr_contenedor;

    ImageView img_central;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listIcon = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        fr_contenedor = (FrameLayout) findViewById(R.id.fr_contenedor);

        loadRecyclerView();


        img_central = (ImageView) findViewById(R.id.img_central);

        //final ScrollZoomLayoutManager scrollZoomLayoutManager = new ScrollZoomLayoutManager(this,Dp2px(10));
        //recyclerView.addOnScrollListener(new CenterScrollListener());

        img_central.setImageResource(R.drawable.helico1);

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


    private void loadRecyclerView() {

        //listAddIcon();
        listAddIconSocial();

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
        listIcon.add(new mIconModel(R.drawable.helico1, "Física"));
        listIcon.add(new mIconModel(R.drawable.helico2, "Química"));
        listIcon.add(new mIconModel(R.drawable.helico3, "Algebra"));
        listIcon.add(new mIconModel(R.drawable.helico4, "Trigonometría"));
        listIcon.add(new mIconModel(R.drawable.helico5, "Biología"));
        listIcon.add(new mIconModel(R.drawable.helico6, "Psicología"));
        listIcon.add(new mIconModel(R.drawable.helico7, "Historia del Perú"));
        listIcon.add(new mIconModel(R.drawable.helico8, "Historia Universal"));
        listIcon.add(new mIconModel(R.drawable.helico9, "Literatura"));
        listIcon.add(new mIconModel(R.drawable.helico10, "Inglés"));
        listIcon.add(new mIconModel(R.drawable.helico11, "Aritmética"));
        listIcon.add(new mIconModel(R.drawable.helico12, "Razonamiento Matemático"));
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


}
