package com.example.ateam.bring2you;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ListItemViewHolder> {
    private final List<ListItemInfo> listItems;
    private Context context;

    public RecyclerViewAdapter(List<ListItemInfo> listItems) {
        this.listItems = listItems;
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
    listItemViewHolder.constraintLayout.setOnClickListener(view ->{
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
    listItemViewHolder.constraintLayout.setOnClickListener(v ->{
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Item",item);
        Fragment signFragment = new SignFragment();
        signFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, signFragment)
                .addToBackStack(null).commit();
        });

    listItemViewHolder.openMap.setOnClickListener(view -> {
        // show marker google maps intent


        Intent i = new Intent(view.getContext(), MapsActivity.class);
        i.putExtra("mapKey", item);
        view.getContext().startActivity(i);
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

    private void removeItem(int index){
        if( index >= 0 && index < listItems.size()) {
            listItems.remove(index);
            this.notifyItemRemoved(index);
        }
    }

    public void removeItem(String id) {
        for (int i = 0; i < listItems.size(); i++) {
            if( listItems.get(i).getId().equals(id) ) {
                removeItem(i);
                return;
            }
        }
    }
<<<<<<< HEAD

=======
>>>>>>> origin/master
}
