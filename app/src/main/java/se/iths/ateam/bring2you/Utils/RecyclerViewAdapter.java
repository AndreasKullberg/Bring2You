package se.iths.ateam.bring2you.Utils;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import se.iths.ateam.bring2you.Activities.MainActivity;
import se.iths.ateam.bring2you.Activities.MapsActivity;
//import se.iths.ateam.bring2you.Fragments.InfoFragment;
import se.iths.ateam.bring2you.Fragments.ListFragment;
import se.iths.ateam.bring2you.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ListItemViewHolder> {
    private final List<ListItemInfo> listItems;
    private Context context;
    private MyCanvas myCanvas;
    private StorageReference storageReference;
    private StorageTask<UploadTask.TaskSnapshot> storageTask;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String collection;
    private List<ListItemInfo> listItemscopy;
    private ListItemInfo item;


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

        listItemscopy = listItems;
       item = listItems.get(index);
       listItemViewHolder.setData(item);
       Fragment listFragment = new ListFragment();
       ViewFlipper viewFlipper = listItemViewHolder.viewFlipper;
       storageReference = FirebaseStorage.getInstance().getReference("Signatures");
       firestore = FirebaseFirestore.getInstance();
       firebaseAuth = FirebaseAuth.getInstance();
       firebaseUser = firebaseAuth.getCurrentUser();
       collection = firebaseUser.getEmail();


        Objects.requireNonNull(listItemViewHolder.sendButton).setOnClickListener(v -> {

            StorageReference fileRef = storageReference.child(item.getId() + ".png");
            storageTask = fileRef.putBytes(makeSignature()).addOnSuccessListener(taskSnapshot -> {});

            item.setSignImageUrl("gs://" +fileRef.getBucket()+"/Signatures/" + item.getId() + ".png");
            item.setSignedBy(listItemViewHolder.signedByView.toString());
            item.setDate(getDate());
            item.setTime(getTime());
            item.setDelivered(true);

            Task task1 = firestore.collection(collection).document(item.getId()).set(item);

            Task task2 = firestore.collection("Delivered").document(item.getId()).set(item);

            Tasks.whenAllSuccess(task1, task2, storageTask).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                @Override
                public void onSuccess(List<Object> objects) {

//                    Toast.makeText(new MainActivity(),getString(R.string.successDelivered, Toast.LENGTH_SHORT).show();


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(new MainActivity(),getString(R.string.), Toast.LENGTH_SHORT).show();
                }
            });

        //getActivity().recreate();
            listItemViewHolder.constraintLayout.toggle(true);
            listItemViewHolder.cardProgress.setVisibility(View.VISIBLE);
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            listItemViewHolder.cardProgress.setVisibility(View.INVISIBLE);
            ((ListFragment) listFragment).send();

        });

        listItemViewHolder.clearBtn.setOnClickListener(v -> {listItemViewHolder.canvas.clear();});
        myCanvas = listItemViewHolder.canvas;


        listItemViewHolder.constraintLayout.setOnClickListener(v -> {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if(item.isDelivered()){
                viewFlipper.setDisplayedChild(1);

            }
            else {
                viewFlipper.setDisplayedChild(0);

            }
            listItemViewHolder.constraintLayout.toggle(false);
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
    private String getDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = simpleDateFormat.format(new Date());

        return currentDate;
    }

    private String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String currentTime = simpleDateFormat.format(new Date());

        return currentTime;
    }

    private byte[] makeSignature() {
        View content = myCanvas;
        content.setDrawingCacheEnabled(true);
        content.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = content.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);


        return stream.toByteArray();


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
