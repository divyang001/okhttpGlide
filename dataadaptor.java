package com.example.hunter.okhttp;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by hunter on 5/6/16.
 */
public class dataadaptor extends RecyclerView.Adapter<dataadaptor.MyViewHolder>  {

    private List<data> dataList;
    private Context context;
    private LayoutInflater mInflater;

    public dataadaptor(Context context, List<data> objects) {

        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        dataList = objects;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mainadress1, mainadress2;
         public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            mainadress1 = (TextView) view.findViewById(R.id.textViewName);
            mainadress2 = (TextView) view.findViewById(R.id.textViewEmail);
            image = (ImageView) view.findViewById(R.id.imageView);
        }
    }



    public dataadaptor(List<data> dataList) {
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        data gym = dataList.get(position);
        holder.mainadress1.setText(gym.getAdress1());
        holder.mainadress2.setText(gym.getAdress2());
        Glide.with(context)                 // get context of this activity
                .load(gym.getImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);  // into what you want
                /*.centerCrop()
                .transform(new CircleTransform(context))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.imagenotfound);*/





    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

