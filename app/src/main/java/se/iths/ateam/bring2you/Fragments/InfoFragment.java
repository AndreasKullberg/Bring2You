package se.iths.ateam.bring2you.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import se.iths.ateam.bring2you.R;

public class InfoFragment extends Fragment {
    ImageView signImage;
    StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        signImage = getActivity().findViewById(R.id.signImage);
        storageReference = FirebaseStorage.getInstance()
                .getReferenceFromUrl("gs://bring2you-da7a0.appspot.com/Signatures/3A1TZ3hSRTiCdTofSsos.png");


        Glide.with(this).load(storageReference).into(signImage);
    }
}
