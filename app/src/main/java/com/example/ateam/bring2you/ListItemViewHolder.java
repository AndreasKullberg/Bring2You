package com.example.ateam.bring2you;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class ListItemViewHolder extends RecyclerView.ViewHolder {
    private View itemview;
    private TextView adress;
    private TextView name;
    private TextView postalCode;
    private TextView senderId;

    public ListItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemview = itemView;
        adress = itemview.findViewById(R.id.textAdress);
        name = itemView.findViewById(R.id.textName);
        postalCode = itemView.findViewById(R.id.textPostalCode);
        senderId = itemView.findViewById(R.id.textSenderId);
    }

    public void setData(ListItemInfo info){
       adress.setText(info.getAdress());
       name.setText(info.getName());
       postalCode.setText(info.getPostalCode());
       senderId.setText(info.getSenderId());
    }
}
