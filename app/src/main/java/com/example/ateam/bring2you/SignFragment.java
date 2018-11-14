package com.example.ateam.bring2you;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class SignFragment extends Fragment {
    FirebaseFirestore firestore;
    EditText signedByView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        signedByView = view.findViewById(R.id.signedBy);
        Bundle bundle = getArguments();
        ListItemInfo item = (ListItemInfo) bundle.getSerializable("Item");
        firestore = FirebaseFirestore.getInstance();

        view.findViewById(R.id.sendButton).setOnClickListener(v -> {
            item.setSignedBy(signedByView.getText().toString());

            firestore.collection("Delivered").document(item.getId())
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
            firestore.collection("Deliveries").document(item.getId())
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
            });
        });


        return view;
    }


}
