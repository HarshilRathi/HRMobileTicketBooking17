package com.example.mobileticketbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mobileticketbooking.adapter.MovieAdapter;
import com.example.mobileticketbooking.api.Service;
import com.example.mobileticketbooking.api.client;
import com.example.mobileticketbooking.model.movie;
import com.example.mobileticketbooking.model.movieResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    //public String THE_MOVIEDB_API_TOKEN="69ca6d9610ebdccbe2c7060e3cf3601a";
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<movie> movieList;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    public static final String Log_Tag = MovieAdapter.class.getName();
    Button logout;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.main_content);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                Toast.makeText(HomeActivity.this, "Movies Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(I);
            }
        });
    }

    public Activity getActivity()
    {
        Context context=this;
        while(context instanceof ContextWrapper){
   if(context instanceof Activity)
   {
       return (Activity) context;
   }
context=((ContextWrapper) context).getBaseContext();
    }
        return null;
    }
    private void initViews() {
        pd = new ProgressDialog(this);
        pd.setMessage("Fetching Movies..");
        pd.setCancelable(false);
        pd.show();

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);

        movieList = new ArrayList<>();
        adapter = new MovieAdapter(this, movieList);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();
    }

    private void loadJSON()
    {
        try{
            if(BuildConfig.THE_MOVIEDB_API_TOKEN.isEmpty()){
            Toast.makeText(getApplicationContext(),"Get the Api key",Toast.LENGTH_LONG).show();
            pd.dismiss();
            return;
            }
            client client = new client();
            Service apiService=client.getClient().create(Service.class);
            Call<movieResponse> call = apiService.getPopularMovies(BuildConfig.THE_MOVIEDB_API_TOKEN);
            call.enqueue(new Callback<movieResponse>() {
                @Override
                public void onResponse(Call<movieResponse> call, Response<movieResponse> response) {
                    List<movie> movies=response.body().getResults();
                    recyclerView.setAdapter(new MovieAdapter(getApplicationContext(),movies));
                    recyclerView.smoothScrollToPosition(0);
                    if(swipeContainer.isRefreshing()){
                        swipeContainer.setRefreshing(false);
                    }
                    pd.dismiss();
                }

                @Override
                public void onFailure(Call<movieResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
                Toast.makeText(HomeActivity.this,"Error Fetching data!",Toast.LENGTH_SHORT).show();

                }
            });
        }catch(Exception e){
            Log.d("Error",e.getMessage());
            Toast.makeText(HomeActivity.this,e.toString(),Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


 }