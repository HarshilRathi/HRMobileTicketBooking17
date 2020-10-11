package com.example.mobileticketbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class detailActivity extends AppCompatActivity {
    TextView nameOfMovie,plotsynopsis,userrating,releaseDate;
    String moviename;
    ImageView imageView;
    Button Booking;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    public void onCreate(Bundle savedInstanceState){
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_details);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initCollapsingToolbar();
        imageView=(ImageView)findViewById(R.id.thumbnail_image_header);
        nameOfMovie=(TextView)findViewById(R.id.title);
        plotsynopsis=(TextView)findViewById(R.id.plotsynopsis);
        userrating=(TextView)findViewById(R.id.userRating);
        releaseDate=(TextView)findViewById(R.id.releaseDate);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        Intent intentThatStartedThisActivity =getIntent();
        if(intentThatStartedThisActivity.hasExtra("original_title")){
            String thumbnail=getIntent().getExtras().getString("poster_path");
            String movieName=getIntent().getExtras().getString("original_title");
            String synopsis=getIntent().getExtras().getString("overview");
            String rating=getIntent().getExtras().getString("vote_average");
            String dateOfRelease=getIntent().getExtras().getString("release_date");

            Glide.with(this).load(thumbnail).placeholder(R.drawable.load).into(imageView);
            nameOfMovie.setText(movieName);
            plotsynopsis.setText(synopsis);
            userrating.setText(rating);
            releaseDate.setText(dateOfRelease);
        moviename=movieName;
        }
        else {
            Toast.makeText(this, "No API Data", Toast.LENGTH_SHORT).show();
        }

        Booking=(Button)findViewById(R.id.BookTicket);
        Booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseAuth.getCurrentUser() !=null)
                {
                    Intent intent=new Intent(detailActivity.this,BookingForm.class);
                    intent.putExtra("moviename",moviename);
                    intent.putExtra("email",firebaseUser.getEmail());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"You Need to LogIn First..",Toast.LENGTH_LONG);
                    Intent intent=new Intent(detailActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
    private void initCollapsingToolbar(){
        final CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout=(AppBarLayout)findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange= -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if(scrollRange== -1)
                {
                    scrollRange=appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + i ==0){
                    collapsingToolbarLayout.setTitle(getString(R.string.Movie_details));
                    isShow=true;
                }else if (isShow){
                    collapsingToolbarLayout.setTitle(" ");
                    isShow=false;
                }
            }
        });
    }

}
