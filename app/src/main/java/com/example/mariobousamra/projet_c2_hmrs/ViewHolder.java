package com.example.mariobousamra.projet_c2_hmrs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder{

    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;
    }

    //Set details to recycler view row
    public void setDetails(Context ctx, String title, String description, String image){
    //Views
        TextView mTitleTv = mView.findViewById(R.id.rTitleView);
        TextView mDetailsTv = mView.findViewById(R.id.rDescriptionView);
        ImageView mImageTv = mView.findViewById(R.id.rimageView);
        //set data to views
        mTitleTv.setText(title);
        mDetailsTv.setText(description);
        Picasso.get().load(image).into(mImageTv);
    }
}
