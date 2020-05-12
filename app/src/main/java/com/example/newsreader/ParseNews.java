package com.example.newsreader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class ParseNews {
    public static final String TAG = "ParseNews";
    private ArrayList<News> newsArrayList;

    public ParseNews() {
        this.newsArrayList = new ArrayList<>();
    }

    public ArrayList<News> getNewsArrayList() {
        return newsArrayList;
    }

    public boolean parse(String xmlData) {
        boolean status = true;
        News currentNews = null;
        boolean inItem = false;
        String textValue = "";

        try {
            // Setup XmlPullParserFactory to create an XmlPullParser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            // Send data into the XPP
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();

            // Keep looping until you reach end of document
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("item".equalsIgnoreCase(tagName)) {
                            inItem = true;
                            currentNews = new News();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (inItem) {
                            if ("item".equalsIgnoreCase(tagName)) {
                                newsArrayList.add(currentNews);
                                inItem = false;
                            } else if ("title".equalsIgnoreCase(tagName)) {
                                currentNews.setTitle(textValue);
                            } else if ("description".equalsIgnoreCase(tagName)) {
                                currentNews.setDescription(textValue);
                            } else if ("pubdate".equalsIgnoreCase(tagName)) {
                                currentNews.setPublicationDate(textValue);
                            }
                        }
                        break;
                }

                eventType = xpp.next();
            }

            return true;

        } catch (Exception e) {
            Log.e(TAG, "parse: error", e);
        }

        return false;
    }
}
