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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import se.iths.ateam.bring2you.Utils.ListItemInfo;
import se.iths.ateam.bring2you.R;

public class CreateDeliveryFragment extends Fragment {
    private FirebaseFirestore firestore;
    private EditText createAdress, createName, createPostal, createSender, sendTo;
    private String adress, name, postalCode, senderId;
    private String collection;
    private String user;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_createdelivery, container, false);
        createAdress = view.findViewById(R.id.createAdress);
        createName = view.findViewById(R.id.createName);
        createPostal = view.findViewById(R.id.createPostal);
        createSender = view.findViewById(R.id.createSender);
        sendTo = view.findViewById(R.id.sendTo);
        firestore = FirebaseFirestore.getInstance();

        view.findViewById(R.id.createButton).setOnClickListener(v -> {
            user = sendTo.getText().toString();
            adress = createAdress.getText().toString();
            name = createName.getText().toString();
            postalCode = createPostal.getText().toString();
            senderId = createSender.getText().toString();

            firestore.collection("Users").document(user).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    collection = user;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    collection = "";
                }
            });

            if(adress.equals("")||name.equals("")||postalCode.equals("")||senderId.equals("")){
                Log.d("nej", "works");
            }
            else if(collection.equals("")){

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
                getActivity().recreate();
            }

        });

        return view;
    }
}