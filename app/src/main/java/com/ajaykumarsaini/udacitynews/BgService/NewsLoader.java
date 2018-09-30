package com.ajaykumarsaini.udacitynews.BgService;

import android.content.AsyncTaskLoader;
import android.content.Context;
import com.ajaykumarsaini.udacitynews.Model.NewsItem;
import com.ajaykumarsaini.udacitynews.Utility;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsItem>>{

    private String murl;

    public NewsLoader(Context context,String url) {
        super(context);
        this.murl = url;
    }

    @Override
    public List<NewsItem> loadInBackground() {
        List<NewsItem> newsList = new ArrayList<>();
        if(murl == null){
            return null;
        }
        else {
            try{
                newsList = Utility.fetchNewsItems(murl);
            } catch (JSONException e){
                e.printStackTrace();
            }
            return newsList;
        }
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
