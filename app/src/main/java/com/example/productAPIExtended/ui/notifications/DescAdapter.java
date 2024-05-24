package com.example.productAPIExtended.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productAPIExtended.databinding.ItemDescBinding;
import com.example.productAPIExtended.databinding.ItemProductBinding;
import com.example.productAPIExtended.models.ModelM;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DescAdapter extends RecyclerView.Adapter<DescAdapter.ViewHolder> {

    ItemDescBinding binding;
    Context context;
    List<ModelM> listD = new ArrayList<>();

    public DescAdapter(Context context, List<ModelM> listD) {
        this.context = context;
        this.listD = listD;
    }

    public void setListD(List<ModelM> listD) {
        this.listD = listD;
    }

    @NonNull
    @Override
    public DescAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemDescBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DescAdapter.ViewHolder holder, int position) {
        holder.onBind(listD.get(position));
    }

    @Override
    public int getItemCount() {
        return listD.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull ItemDescBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void onBind(ModelM modelM) {
            binding.nameCard.setText(modelM.getModelTitle());
            binding.priceCard.setText(String.valueOf(modelM.getPrice_d()));
            binding.descriptionCard.setText(modelM.getModelDescription());
            //Glide.with(itemView.getContext()).load(modelM.getModelImage()).into(binding.imageCard);
            Picasso.get().load(modelM.getModelImage()).into(binding.imageCard);
        }
    }
}