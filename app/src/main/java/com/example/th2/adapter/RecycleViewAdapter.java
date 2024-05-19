package com.example.th2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th2.R;
import com.example.th2.model.Item;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewHolder> {

    private List<Item> list;
    private ItemListener itemListener;

    public RecycleViewAdapter() {
        list = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<Item> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Item getItem(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent ,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Item item = list.get(position);
        holder.name.setText(item.getName());
        holder.desc.setText(item.getDesc());
        holder.category.setText(item.getCategory());
        holder.condition.setText(item.getCondition());
        holder.date.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name,desc, category, condition, date;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            category = itemView.findViewById(R.id.tvCategory);
            desc = itemView.findViewById(R.id.tvDesc);
            date = itemView.findViewById(R.id.tvDate);
            condition = itemView.findViewById(R.id.tvCondition);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener!=null){
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
    public interface ItemListener{
        void onItemClick(View view, int position);
    }
}

