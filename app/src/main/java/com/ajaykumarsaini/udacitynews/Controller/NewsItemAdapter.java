package com.ajaykumarsaini.udacitynews.Controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.ajaykumarsaini.udacitynews.Model.NewsItem;
import com.ajaykumarsaini.udacitynews.R;
import java.util.ArrayList;

public class NewsItemAdapter extends ArrayAdapter<NewsItem> {

    public NewsItemAdapter(Context context, ArrayList<NewsItem> newsItems){
        super(context,0,newsItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){

            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,false);
        }
        NewsItem newsItem = getItem(position);
        TextView heading =listItemView.findViewById(R.id.news_item_headline);
        TextView category =listItemView.findViewById(R.id.news_item_category);
        TextView author =listItemView.findViewById(R.id.news_item_Author);
        TextView date =listItemView.findViewById(R.id.news_item_Date);

        if(newsItem != null) {
            heading.setText(newsItem.getNewsHeadline());
            category.setText(newsItem.getNewsCategory());
            author.setText(newsItem.getNewsAuthor());
            date.setText(newsItem.getPublishDate());
        }

        return listItemView;
    }
}
