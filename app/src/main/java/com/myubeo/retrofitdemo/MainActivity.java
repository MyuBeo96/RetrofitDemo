package com.myubeo.retrofitdemo;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AnswersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SOService mService;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    TextView menu1;
    TextView menu1_1;
    TextView menu1_2;

    LinearLayout ln_menu1;

    private ExpandableListView lvPhones;

    ArrayList<Item> items = new ArrayList<>();

    @Override
    protected void onCreate (Bundle savedInstanceState)  {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_main );

        mService = ApiUtils.getSOService();
        mRecyclerView =  findViewById(R.id.rv_answers);
        mAdapter = new AnswersAdapter(this, items);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        lvPhones = (ExpandableListView) findViewById(R.id.phone_list);
        
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


//        menu1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(menu1.isShown())
//                {
////                    ln_menu1.setVisibility(View.GONE);
////                    ln_menu1.startAnimation(animationDown);
//                    menu1_1.setVisibility(View.VISIBLE);
//                    menu1_2.setVisibility(View.VISIBLE);
//                }else {
////                    ln_menu1.setVisibility(View.INVISIBLE);
////                    ln_menu1.startAnimation(animationDown);
//                    menu1_1.setVisibility(View.GONE);
//                    menu1_2.setVisibility(View.GONE);
//                }
//
//            }
//        });

        loadAnswers();
        new LoadContentAsync().execute();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu1:
                Toast.makeText(this, "Menu 1", Toast.LENGTH_LONG).show();
                break;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadAnswers() {
        mService.getAnswers().enqueue(new Callback<SOAnswersResponse>() {
            @Override
            public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {
                mAdapter.updateAnswers(response.body().getItems());

            }

            @Override
            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {
                showErrorMessage();
            }
        });
    }

    private void showErrorMessage() {
        Toast.makeText(this, "Error loading posts", Toast.LENGTH_SHORT).show();
    }

    class LoadContentAsync extends AsyncTask<Void, Void, MainContentModel> {

        @Override
        protected MainContentModel doInBackground(Void... voids) {
            Gson gson = new Gson();
            MainContentModel mainContentModel = null;
            try {
                InputStream is = getAssets().open("phones.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                synchronized (this) {
                    mainContentModel = gson.fromJson(reader, MainContentModel.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mainContentModel;
        }

        @Override
        protected void onPostExecute(MainContentModel mainContentModel) {
            super.onPostExecute(mainContentModel);

            PhoneListAdapter phoneListAdapter = new PhoneListAdapter(MainActivity.this, mainContentModel.getCompanies());
            lvPhones.setAdapter(phoneListAdapter);
        }
    }


}
