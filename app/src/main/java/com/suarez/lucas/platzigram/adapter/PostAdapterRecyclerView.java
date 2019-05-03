package com.suarez.lucas.platzigram.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.suarez.lucas.platzigram.R;
import com.suarez.lucas.platzigram.model.Picture;
import com.suarez.lucas.platzigram.model.Post;
import com.suarez.lucas.platzigram.view.PictureDetailActivity;

import java.util.ArrayList;

public class PostAdapterRecyclerView extends RecyclerView.Adapter<PostAdapterRecyclerView.PictureViewHolder> {

    private ArrayList<Post> post;
    private int resource;
    private Activity activity;

    public PostAdapterRecyclerView(ArrayList<Post> posts, int resource, Activity activity) {
        this.post = posts;
        this.resource = resource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder pictureViewHolder, int i) {

        Post post2 = post.get(i);
        pictureViewHolder.usernameCard.setText(post2.getAuthor());
        pictureViewHolder.timeCard.setText(String.valueOf(post2.getTimestampCreated()));
        pictureViewHolder.likeNumberCard.setText("1");
        Picasso.get().load(post2.getImageUrl()).into(pictureViewHolder.pictureCard);

        pictureViewHolder.pictureCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, PictureDetailActivity.class);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Explode explode = new Explode();
                    explode.setDuration(1000);
                    activity.getWindow().setExitTransition(explode);
                    activity.startActivity(intent,
                            ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, activity.getString(R.string.transitionname_picture)).toBundle());
                }
                else {
                    activity.startActivity(intent);
                }



            }
        });

    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder{
        private ImageView pictureCard;
        private TextView usernameCard;
        private TextView timeCard;
        private TextView likeNumberCard;

        public PictureViewHolder(@NonNull View itemView) {
            super(itemView);

            pictureCard = (ImageView) itemView.findViewById(R.id.pictureCard);
            usernameCard = (TextView) itemView.findViewById(R.id.userNameCard);
            timeCard = (TextView) itemView.findViewById(R.id.timeCard);
            likeNumberCard = (TextView) itemView.findViewById(R.id.likeNumberCard);
        }
    }


}
