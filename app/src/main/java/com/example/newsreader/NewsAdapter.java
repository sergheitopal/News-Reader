package com.example.newsreader;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NewsAdapter extends ArrayAdapter {
    private static final String TAG = "NewsAdapter";

    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<News> news;

    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<News> news) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.news = news;
    }

    // ListView needs to know the number of views to display
    @Override
    public int getCount() {
        return news.size();
    }

    // This method gives a new view to listView to display
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        News currentNews = news.get(position);

        viewHolder.newsHeading.setText(currentNews.getTitle());
        viewHolder.newsDate.setText(currentNews.getPublicationDate());
        viewHolder.newsContent.setText(currentNews.getDescription());

        return convertView;
    }

    public class ViewHolder {
        final TextView newsHeading;
        final TextView newsDate;
        final TextView newsContent;

        ViewHolder(View v) {
            newsHeading = v.findViewById(R.id.news_heading_textView);
            newsDate = v.findViewById(R.id.news_date_textView);
            newsContent = v.findViewById(R.id.news_content_textView);
        }
    }
}
