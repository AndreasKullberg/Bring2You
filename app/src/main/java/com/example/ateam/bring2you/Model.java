package com.example.ateam.bring2you;

import android.arch.lifecycle.ViewModel;

class Model extends ViewModel {
    public void setRecyclerViewAdapter(RecyclerViewAdapter recyclerViewAdapter) {
        this.recyclerViewAdapter = recyclerViewAdapter;
    }

    private boolean listFilled;
    private RecyclerViewAdapter recyclerViewAdapter;

    public RecyclerViewAdapter getRecyclerViewAdapter() {
        return recyclerViewAdapter;
    }



    public boolean isListFilled() {
        return listFilled;
    }

    public void setListFilled(boolean listFilled) {
        this.listFilled = listFilled;
    }
}
