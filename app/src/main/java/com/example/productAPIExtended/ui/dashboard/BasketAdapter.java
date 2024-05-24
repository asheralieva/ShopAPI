package com.example.productAPIExtended.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productAPIExtended.R;
import com.example.productAPIExtended.databinding.ItemOrderBinding;
import com.example.productAPIExtended.models.ModelM;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {
    ItemOrderBinding binding;
    Context context;

    ArrayList<ModelM> nn_list = new ArrayList<>();

    int pp =1;

    public BasketAdapter(Context context, ArrayList<ModelM> nn_list) {
        this.context = context;
        this.nn_list = nn_list;
    }

    @NonNull
    @Override
    public BasketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemOrderBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BasketAdapter.ViewHolder holder, int position) {
        holder.onBind(nn_list.get(position));

    }

    @Override
    public int getItemCount() {
        return nn_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemOrderBinding binding;
        public ViewHolder(@NonNull ItemOrderBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;



        }

        public void onBind(ModelM modelM) {

            binding.nameProductCard.setText(modelM.getModelTitle());
            binding.priceCard.setText(String.valueOf(modelM.getPrice_d()));
            Picasso.get()
                    .load(modelM.getModelImage())
                    .placeholder(R.drawable.place_holder_my)
                    .into(binding.imageCard);


            final int[] pp = {Integer.parseInt(binding.productsCounter.getText().toString())};

            binding.btnIncrementPlus.setOnClickListener(v1 -> {
                binding.productsCounter.setText(String.valueOf(pp[0]++));
                int[] cb = new int[]{modelM.getCounterProduct()};
                cb = pp;
            });
            binding.btnIncrementMinus.setOnClickListener(v2->{
                if(pp[0] >2) {
                    binding.productsCounter.setText(String.valueOf(pp[0]--));
                }else {
                    binding.productsCounter.setText("1");
                }
                int[] cb = new int[]{modelM.getCounterProduct()};
                cb = pp;
            });




        }
    }
}



//public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder>{
//    ItemOrderBinding binding;
//    Context context;
//    ArrayList<ModelM> nn_list;
//    int pp =1;
//
//    public BasketAdapter(Context context, ArrayList<ModelM> nn_list) {
//        this.context = context;
//        this.nn_list = nn_list;
//    }
//
//    @NonNull
//    @Override
//    public BasketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        binding = ItemOrderBinding
//                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
//
//        return new ViewHolder(binding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull BasketAdapter.ViewHolder holder, int position) {
//        holder.onBind(nn_list.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return nn_list.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        ItemOrderBinding binding;
//        public ViewHolder(@NonNull ItemOrderBinding itemView) {
//            super(itemView.getRoot());
//            this.binding = itemView;
//        }
//
//        public void onBind(ModelM modelM) {
//
//            binding.nameProductCard.setText(modelM.getModelTitle());
//            binding.priceCard.setText(String.valueOf(modelM.getPrice_d()));
//            Picasso.get()
//                    .load(modelM.getModelImage())
//                    .placeholder(R.drawable.place_holder_my)
//                    .into(binding.imageCard);
//
//
//            final int[] pp = {Integer.parseInt(binding.productsCounter.getText().toString())};
//
//            binding.btnIncrementPlus.setOnClickListener(v1 -> {
//                binding.productsCounter.setText(String.valueOf(pp[0]++));
//                int[] cb = new int[]{modelM.getCounterProduct()};
//                cb = pp;
//            });
//            binding.btnIncrementMinus.setOnClickListener(v2->{
//                if(pp[0] >2) {
//                    binding.productsCounter.setText(String.valueOf(pp[0]--));
//                }else {
//                    binding.productsCounter.setText("1");
//                }
//                int[] cb = new int[]{modelM.getCounterProduct()};
//                cb = pp;
//            });
//
//
//
//
//        }
//    }
//}
