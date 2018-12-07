package se.iths.ateam.bring2you.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import se.iths.ateam.bring2you.Utils.ListItemInfo;
import se.iths.ateam.bring2you.R;

@SuppressWarnings("deprecation")
public class SignFragment extends Fragment {
    private FirebaseFirestore firestore;
    private EditText signedByView;
    static String scanResult;
    private ListItemInfo item;
    private ImageView signature;
    private StorageReference storageReference;
    private StorageTask storageTask;
    private String collection;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    public static void setScanResult(String myResult) {

        SignFragment.scanResult = myResult;
    }

    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.titleSign);
        firestore = FirebaseFirestore.getInstance();
        signedByView = view.findViewById(R.id.signedBy);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        collection = firebaseUser.getEmail();
        storageReference = FirebaseStorage.getInstance().getReference("Signatures");


        if (scanResult != null) {
            firestore.collection(collection).document(scanResult).get().addOnCompleteListener(task -> checkScan(task));
        }

        if (scanResult == null) {
            Bundle bundle = getArguments();
            item = (ListItemInfo) Objects.requireNonNull(bundle).getSerializable("Item");
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Objects.requireNonNull(getActivity()).findViewById(R.id.sendButton).setOnClickListener(v -> {

            StorageReference fileRef = storageReference.child(item.getId() + ".png");
            storageTask = fileRef.putBytes(makeSignature()).addOnSuccessListener(taskSnapshot -> {
            });

            item.setSignImageUrl("gs://" + fileRef.getBucket() + "/Signatures/" + item.getId() + ".png");
            item.setSignedBy(signedByView.getText().toString());
            item.setDate(getDate());
            item.setTime(getTime());
            item.setDelivered(true);

            firestore.collection(collection).document(item.getId()).set(item)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
            firestore.collection("Delivered")
                    .document(item.getId())
                    .set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("succsesSet", "DocumentSnapshot successfully added!");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("succsesSet", "Error deleting document", e);
                }
            });

            /*:firestore.collection(collection)
                    .document(item.getId())
                    .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("succsesDelete", "DocumentSnapshot successfully deleted!");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("succsesDelete", "Error deleting document", e);
                }
            });*/
            getActivity().recreate();
        });
    }

    private byte[] makeSignature() {
        View content = getActivity().findViewById(R.id.my_canvas);
        content.setDrawingCacheEnabled(true);
        content.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = content.getDrawingCache();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);


        return stream.toByteArray();

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

    private void checkScan(Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
            DocumentSnapshot document = task.getResult();
            if (Objects.requireNonNull(document).exists()) {
                item = document.toObject(ListItemInfo.class);
                Objects.requireNonNull(item).setId(scanResult);
            } else {

            }
        } else {
        }
    }

}


//TODO: Kolla dessa else statements? ska de finnas kvar?