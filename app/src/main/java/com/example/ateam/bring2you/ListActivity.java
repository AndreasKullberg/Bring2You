package com.example.ateam.bring2you;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    List<ListItemInfo> listItems = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        db = FirebaseFirestore.getInstance();



        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        //mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(adapter);

        listItems.add(new ListItemInfo("Pennygången 60","Gunnar Abrahamsson", "414 82", "639178923"));
        listItems.add(new ListItemInfo("Pennygången 60","Gunnar Abrahamsson", "414 82", "639178923"));
        listItems.add(new ListItemInfo("Pennygången 60","Gunnar Abrahamsson", "414 82", "639178923"));
        listItems.add(new ListItemInfo("Pennygången 60","Gunnar Abrahamsson", "414 82", "639178923"));
        listItems.add(new ListItemInfo("Pennygången 60","Gunnar Abrahamsson", "414 82", "639178923"));
        listItems.add(new ListItemInfo("Pennygången 60","Gunnar Abrahamsson", "414 82", "639178923"));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(listItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void maps() {
        listItems.add(new ListItemInfo("Pennygången 60","Andri", "414 82", "639178923"));


    }
}
