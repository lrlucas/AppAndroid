package com.suarez.lucas.platzigram.apifirebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.suarez.lucas.platzigram.R;
import com.suarez.lucas.platzigram.model.RetroPhoto;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<RetroPhoto> dataList;
    private Context context;

    public CustomAdapter(List<RetroPhoto> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        TextView txtTitle;
        private ImageView coverImage;

        public CustomViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            // buscar estos id
            txtTitle = mView.findViewById(R.id.titleImage);
            coverImage = mView.findViewById(R.id.coverImage);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.custom_row, viewGroup, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        customViewHolder.txtTitle.setText(dataList.get(i).getTitle());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(i).getThumbnailUrl())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(customViewHolder.coverImage);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


}
