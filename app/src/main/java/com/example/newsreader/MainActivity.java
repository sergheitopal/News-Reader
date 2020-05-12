package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ListView newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsList = findViewById(R.id.newsList);

        Log.d(TAG, "onCreate: downloading data");
        DownloadNews downloadNews = new DownloadNews();
        downloadNews.execute("http://feeds.bbci.co.uk/news/world/rss.xml");
        Log.d(TAG, "onCreate: download finished");
    }

    private class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            // Receives the result from doInBackground() and parses it

            ParseNews parseNews = new ParseNews();
            parseNews.parse(s);

            ArrayAdapter<News> arrayAdapter = new ArrayAdapter<>(
                    MainActivity.this, R.layout.news_item, parseNews.getNewsArrayList()
            );

            newsList.setAdapter(arrayAdapter);
        }

        @Override
        protected String doInBackground(String... strings) {
            return downloadXML(strings[0]);
        }

    }

    private String downloadXML(String urlPath) {
        StringBuilder xmlResult = new StringBuilder();

        try {
            // Create URL, and then an HTTP connection with it
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int response = connection.getResponseCode();
            Log.d(TAG, "downloadXML: response code is " + response);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));


            int charsRead;
            char[] inputBuffer = new char[500];

            while (true) {
                charsRead = reader.read(inputBuffer);

                if (charsRead < 0) {
                    break;
                }

                if (charsRead > 0) {
                    xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                }
            }

            reader.close();
            return xmlResult.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "downloadXML: invalid URL ", e);
        } catch (IOException e) {
            Log.e(TAG, "downloadXML: IO Exception ", e);
        } catch (Exception e) {
            Log.e(TAG, "downloadXML: Error", e);
        }

        return null;
    }


}
