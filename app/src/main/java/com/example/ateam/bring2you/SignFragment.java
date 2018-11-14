package com.example.ateam.bring2you;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class SignFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign, container, false);
        Bundle bundle = getArguments();
        ListItemInfo item = (ListItemInfo) bundle.getSerializable("Item");
        Log.d("funkar det",item.getAdress());

        return view;
    }


}
