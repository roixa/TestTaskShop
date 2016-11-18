package com.roix.testtaskshop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by roix on 17.11.2016.
 */

public class ItemsAdapter  extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {

    private List<Item> items;
    private MainActivity context;

    public ItemsAdapter(List<Item> items, MainActivity context){
        this.context=context;
        this.items=items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        ItemViewHolder holder=new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.name.setText(items.get(position).getName());
        holder.cost.setText(items.get(position).getCost());
        holder.total.setText("0");
    }

    @Override
    public int getItemCount() {
        if(items!=null)return items.size();
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements SeekBar.OnSeekBarChangeListener{

        public TextView name;
        public TextView cost;
        public TextView total;
        public SeekBar seekbar;

        public ItemViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            cost=(TextView)itemView.findViewById(R.id.cost);
            total=(TextView)itemView.findViewById(R.id.total);
            seekbar=(SeekBar)itemView.findViewById(R.id.seekbar);
            seekbar.setOnSeekBarChangeListener(this);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            int pos=getAdapterPosition();
            Item item=items.get(pos);
            total.setText(""+progress*Integer.parseInt(item.getCost()));
            item.setCount(progress);
            context.updateViews(items);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
