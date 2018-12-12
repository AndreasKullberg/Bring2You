package se.iths.ateam.bring2you.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.AnimatorRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import se.iths.ateam.bring2you.Activities.MapsActivity;

import se.iths.ateam.bring2you.Fragments.SignFragment;
import se.iths.ateam.bring2you.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ListItemViewHolder> {
    private final List<ListItemInfo> listItems;
    private Context context;
    private StorageReference storageReference;

    private Handler handler;

    public RecyclerViewAdapter(List<ListItemInfo> listItems) {
        this.listItems = listItems;
        context = context;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_item, viewGroup, false);
        return new ListItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder listItemViewHolder, int index) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        ListItemInfo item = listItems.get(index);
        listItemViewHolder.setData(item);


        if(item.isDelivered()){
            listItemViewHolder.listIcon.setImageResource(R.drawable.delivered);
            listItemViewHolder.foldingCell.setOnClickListener(v -> {
                storageReference = FirebaseStorage.getInstance()
                        .getReferenceFromUrl(listItemViewHolder.imageUrl);
                Glide.with(listItemViewHolder.itemView).load(storageReference).into(listItemViewHolder.signImage);
                listItemViewHolder.foldingCell.toggle(false);

            });

        }

        else {


            listItemViewHolder.listIcon.setImageResource(R.drawable.undelivered);
            listItemViewHolder.foldingCell.fold(true);

            listItemViewHolder.foldingCell.setOnClickListener(v ->{
                Fragment signFragment = new SignFragment();
                transaction(item, signFragment,v);

        });


        }







    listItemViewHolder.openMap.setOnClickListener(view -> {

            Intent i = new Intent(view.getContext(), MapsActivity.class);
            i.putExtra("mapKey", item);
            view.getContext().startActivity(i);
        });
    }

    private void transaction(ListItemInfo item, Fragment fragment, View v) {
        AppCompatActivity activity = (AppCompatActivity) v.getContext();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Item",item);
        fragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment)
                .addToBackStack(null).commit();
        slideUp(activity.findViewById(R.id.frameLayout));



    }
    // slide the view from below itself to the current position
    public void slideUp(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(700);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(700);
        animate.setFillAfter(true);
        view.startAnimation(animate);
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
            if( listItems.get(i).getId().equals(id) ) {
                removeItem(i);
                return;
            }
        }
    }
}
