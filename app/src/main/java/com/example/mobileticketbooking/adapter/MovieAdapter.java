package com.example.mobileticketbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileticketbooking.R;
import com.example.mobileticketbooking.detailActivity;
import com.example.mobileticketbooking.model.movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    private Context mContext;
    private List<movie> movieList;

    public MovieAdapter(Context mContext, List<movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }


    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup,int i)
    {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_card,viewGroup,false);
    return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieAdapter.MyViewHolder viewHolder,int i){
        viewHolder.title.setText(movieList.get(i).getOriginalTitle());
        String vote = Double.toString(movieList.get(i).getVoteAverage());
        viewHolder.userrating.setText(vote);

        Glide.with(mContext).load(movieList.get(i).getPosterPath()).placeholder(R.drawable.load).into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount()
    {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title,userrating;
        public ImageView thumbnail;

        public MyViewHolder(View view)
        {
            super(view);
            title=(TextView)view.findViewById(R.id.title);
            userrating=(TextView)view.findViewById(R.id.userrating);
            thumbnail=(ImageView)view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        movie clickedDataItem = movieList.get(pos);
                        Intent intent=new Intent(mContext, detailActivity.class);
                        intent.putExtra("original_title",movieList.get(pos).getOriginalTitle());
                        intent.putExtra("poster_path",movieList.get(pos).getPosterPath());
                        intent.putExtra("overview",movieList.get(pos).getOverview());
                        intent.putExtra("original_title",movieList.get(pos).getOriginalTitle());
                        intent.putExtra("vote_average",Double.toString(movieList.get(pos).getVoteAverage()));
                        intent.putExtra("release_date",movieList.get(pos).getReleaseDate());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(),"you clicked "+clickedDataItem.getOriginalTitle(),Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
    }
}
