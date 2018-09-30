package com.ajaykumarsaini.udacitynews;

import android.app.LoaderManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ajaykumarsaini.udacitynews.BgService.NewsLoader;
import com.ajaykumarsaini.udacitynews.Controller.NewsItemAdapter;
import com.ajaykumarsaini.udacitynews.Model.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>>, AdapterView.OnItemClickListener {

    private ListView listView;
    private NewsItemAdapter adapter;
    private ArrayList<NewsItem> mNewsItems;
    private final String BASE_URL = "https://content.guardianapis.com/search?&show-tags=contributor&api-key=";
    private final String API_KEY = "339f9aa4-6082-41d9-8997-d897dafc118e";
    private final String ORDER_BY_ATTR = "&order-by=oldest";
    String selectedCategory;


    @Override
    protected void onResume() {
        fetchSavedPreferences();
        super.onResume();
    }

    private void fetchSavedPreferences() {
        String url;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getString(getString(R.string.category_key), getString(R.string.default_order_value)).equals(getString(R.string.default_order_value))){
            url = BASE_URL + API_KEY;
        }
        else{
            url = BASE_URL + API_KEY + ORDER_BY_ATTR;
        }
        Log.v("switch_value", preferences.getString("category_list", ""));
        selectedCategory = preferences.getString("category_list", "");
        switch(selectedCategory){
            case "All" :
                Utility.setmURL(url);
                break;
            case "Politics" :
                Utility.setmURL(url + "&q=politics");
                break;
            case "Sports" :
                Utility.setmURL(url + "&q=sports");
                break;
            case "Finance" :
                Utility.setmURL(url + "&q=finance");
                break;
            case "Education" :
                Utility.setmURL(url + "&q=education");
                break;
            default:
                Utility.setmURL(url);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);
        fetchSavedPreferences();
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        listView = findViewById(R.id.news_headline_list);
        // create an empty list of news items
        mNewsItems = new ArrayList<>();

        if(!isConnected()){
            // Device is not connected
            // Display the error information
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.no_internet_message)
                    .setTitle(R.string.no_internet_title);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            // else start to fetch the data in background
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);
            // To display details of a news
            // implement onclick for each item in list
            listView.setOnItemClickListener(HomeScreen.this);
        }
    }

    // Add category selection menu onto homescreen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    // Get selected item id and start activity accordingly
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_choose_category){
            Intent categorySelectionIntent =  new Intent(this,ChooseCategory.class);
            startActivity(categorySelectionIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // On creation of loader
    // create a http request on guardian server
    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(Utility.getmURL());
        Uri.Builder uriBuilder = baseUri.buildUpon();
        return new NewsLoader(this, uriBuilder.toString());
    }
    // on receiving the response, Update the UI
    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> data) {
        mNewsItems = new ArrayList<>(data);
        if(mNewsItems.isEmpty()){
            // No news fetched
            // Display Message to user
            // possible reasons are server is down
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.server_down_message)
                    .setTitle(R.string.server_down_title);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        UpdateView(mNewsItems);
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        adapter.clear();
    }

    private void UpdateView(ArrayList<NewsItem> newsItems) {
        adapter = new NewsItemAdapter(getApplicationContext(), newsItems);
        listView.setAdapter(adapter);
    }

    // On click over any item in list
    // a webview will be opened with url specific to that item
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mNewsItems.get(position).getNewsUrl()));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.no_browser_found_message,  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    // Checks if device is connected to internet
    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
