package com.example.alexisgadsproj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ProgressBar mLoadingProgress;
    private RecyclerView rvTopLearners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingProgress = (ProgressBar) findViewById(R.id.pb_Loading);
        rvTopLearners = (RecyclerView) findViewById(R.id.rv_topLearners);

        LinearLayoutManager topLearnerLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvTopLearners.setLayoutManager(topLearnerLayoutManager);

        try {
            URL hoursUrl = ApiUtil.buildUrl("hours");
            new HoursQueryTask().execute(hoursUrl);

//            URL skillsUrl = ApiUtil.buildUrl("skilliq");

        }
        catch (Exception e){
            Log.d("Error", e.getMessage());
        }
    }

    public class HoursQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String result = null;
            try{
                result = ApiUtil.getJson(searchURL);
            }
            catch (Exception e){
                Log.d("Error", e.getMessage());
                return null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView tvError = (TextView) findViewById(R.id.tvError);
            mLoadingProgress.setVisibility(View.INVISIBLE);

            if (result== null){
                rvTopLearners.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);
            }
            else {
                rvTopLearners.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.INVISIBLE);
            }
            ArrayList<TopLearner> topLearners = ApiUtil.getTopLearnersFromJson(result);

           TopLearnersAdapter adapter = new TopLearnersAdapter(topLearners);
            rvTopLearners.setAdapter(adapter);
        }

    }
}
