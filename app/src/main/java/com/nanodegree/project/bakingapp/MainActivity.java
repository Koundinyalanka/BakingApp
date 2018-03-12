package com.nanodegree.project.bakingapp;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.nanodegree.project.bakingapp.Utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public static final int BASIC_LOADER=22;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=(TextView)findViewById(R.id.textsample);
        Toast.makeText(this,NetworkUtils.getUrl().toString(),Toast.LENGTH_LONG).show();
        getLoaderManager().initLoader(BASIC_LOADER,null,this);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        switch (i)
        {
            case BASIC_LOADER:
                return new AsyncTaskLoader<String>(this) {
                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                        forceLoad();
                    }

                    @Override
                    public String loadInBackground() {
                        String s="";
                        try {
                            s= NetworkUtils.getTheResponse(NetworkUtils.getUrl());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return s;
                    }
                };
                default: return null;
        }

    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        int id=loader.getId();
        switch (id)
        {
            case BASIC_LOADER:
                tv.setText(s);
                try {
                    parse(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
    public void parse(String s) throws JSONException {
        JSONObject results=new JSONObject(s);
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
        JSONArray jsonArray=new JSONArray(s);
        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            Toast.makeText(MainActivity.this,jsonObject.getInt("id"),Toast.LENGTH_LONG).show();
        }
    }
}
