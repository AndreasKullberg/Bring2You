package se.iths.ateam.bring2you.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import se.iths.ateam.bring2you.Utils.ListItemInfo;
import se.iths.ateam.bring2you.R;

public class CreateDeliveryFragment extends Fragment {
    private FirebaseFirestore firestore;
    private EditText createAdress, createName, createPostal, createSender;
    private String adress, name, postalCode, senderId;
    private String collection = "test@hotmail.com";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_createdelivery, container, false);
        createAdress = view.findViewById(R.id.createAdress);
        createName = view.findViewById(R.id.createName);
        createPostal = view.findViewById(R.id.createPostal);
        createSender = view.findViewById(R.id.createSender);
        firestore = FirebaseFirestore.getInstance();

        view.findViewById(R.id.createButton).setOnClickListener(v -> {
            adress = createAdress.getText().toString();
            name = createName.getText().toString();
            postalCode = createPostal.getText().toString();
            senderId = createSender.getText().toString();

            if(adress.equals("")||name.equals("")||postalCode.equals("")||senderId.equals("")){
                Log.d("nej", "works");
            }
            else {
                ListItemInfo info = new ListItemInfo(adress, name, postalCode, senderId);

                firestore.collection(collection)
                        .add(info)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("firebase", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("firebase", "Error adding document", e);
                            }
                        });
            }
        });

        return view;
    }
}