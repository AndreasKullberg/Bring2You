package com.example.ateam.bring2you;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    List<ListItemInfo> listItems = new ArrayList<>();
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listItems.add(new ListItemInfo("Pennygången 60","Gunnar Abrahamsson", "414 82", "639178923"));
        listItems.add(new ListItemInfo("Pennygången 60","Gunnar Abrahamsson", "414 82", "639178923"));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(listItems);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
