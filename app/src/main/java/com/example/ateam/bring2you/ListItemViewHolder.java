package com.example.ateam.bring2you;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class ListItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView adress;
    private final TextView name;
    private final TextView postalCode;
    private final TextView senderId;
    final CardView constraintLayout;

    public ListItemViewHolder(@NonNull View itemView) {
        super(itemView);
        View itemview = itemView;
        adress = itemview.findViewById(R.id.textAdress);
        name = itemView.findViewById(R.id.textName);
        postalCode = itemView.findViewById(R.id.textPostalCode);
        senderId = itemView.findViewById(R.id.textSenderId);
        this.constraintLayout = itemView.findViewById(R.id.cardview);
    }

    public void setData(ListItemInfo info){
       adress.setText(info.getAdress());
       name.setText(info.getName());
       postalCode.setText(info.getPostalCode());
       senderId.setText(info.getSenderId());
    }
}
