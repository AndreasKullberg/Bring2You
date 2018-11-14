package com.example.ateam.bring2you;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ListItemViewHolder> {
    List<ListItemInfo> listItems;
    Context context;

    public RecyclerViewAdapter(List<ListItemInfo> listItems) {
        this.listItems = listItems;
        context = context;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item,viewGroup,false);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder listItemViewHolder, int index) {
    ListItemInfo item = listItems.get(index);
    listItemViewHolder.setData(item);
    listItemViewHolder.constraintLayout.setOnClickListener(v ->{
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        Fragment mapFragment = new MapFragment();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, mapFragment)
                .addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public void addItem(ListItemInfo info){
        listItems.add(info);
        this.notifyItemInserted(listItems.size()-1);
    }

    public void removeItem(int index){
        if( index >= 0 && index < listItems.size()) {
            listItems.remove(index);
            this.notifyItemRemoved(index);
        }
    }

    public void removeItem(String id) {
        for (int i = 0; i < listItems.size(); i++) {
            if( listItems.get(i).id.equals(id) ) {
                removeItem(i);
                return;
            }
        }
    }
}
