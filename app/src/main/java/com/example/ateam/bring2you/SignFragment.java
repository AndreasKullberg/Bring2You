package com.example.ateam.bring2you;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class SignFragment extends Fragment {
    private FirebaseFirestore firestore;
    private EditText signedByView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        signedByView = view.findViewById(R.id.signedBy);
        Bundle bundle = getArguments();
        ListItemInfo item = (ListItemInfo) Objects.requireNonNull(bundle).getSerializable("Item");
        firestore = FirebaseFirestore.getInstance();

        view.findViewById(R.id.sendButton).setOnClickListener(v -> {
            item.setSignedBy(signedByView.getText().toString());

            firestore.collection("Delivered").document(item.getId())
                    .set(item).addOnSuccessListener(aVoid -> Log.d("succsesSet", "DocumentSnapshot successfully added!")).addOnFailureListener(e -> Log.w("succsesSet", "Error deleting document", e));
            firestore.collection("Deliveries").document(item.getId())
                    .delete().addOnSuccessListener(aVoid -> Log.d("succsesDelete", "DocumentSnapshot successfully deleted!")).addOnFailureListener(e -> Log.w("succsesDelete", "Error deleting document", e));
        });


        return view;
    }


}
