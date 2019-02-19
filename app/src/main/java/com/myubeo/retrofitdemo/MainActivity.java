package com.myubeo.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AnswersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SOService mService;

    ArrayList<Item> items;

    @Override
    protected void onCreate (Bundle savedInstanceState)  {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_main );
        mService = ApiUtils.getSOService();
        mRecyclerView =  findViewById(R.id.rv_answers);
        items = new ArrayList<>();
        mAdapter = new AnswersAdapter(this, items);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        loadAnswers();
    }

    public void loadAnswers() {
        mService.getAnswers().enqueue(new Callback<List<SOAnswersResponse>>() {
            @Override
            public void onResponse(Call<List<SOAnswersResponse>> call, Response<List<SOAnswersResponse>> response) {
                if(response.isSuccessful()){
                    List<SOAnswersResponse> soAnswersResponseList = response.body();
                    
                    Log.d("MainActivity", "posts loaded from API");
                }
            }

            @Override
            public void onFailure(Call<List<SOAnswersResponse>> call, Throwable t) {
                showErrorMessage();
                Log.d("MainActivity", "error loading from API");
            }
        });
    }

    private void showErrorMessage() {
        Toast.makeText(this, "Error loading posts", Toast.LENGTH_SHORT).show();
    }

}
