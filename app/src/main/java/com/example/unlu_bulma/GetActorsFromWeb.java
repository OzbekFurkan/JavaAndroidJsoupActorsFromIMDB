package com.example.unlu_bulma;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class GetActorsFromWeb extends AsyncTask<String, Void, ArrayList<Actor>> {
    @Override
    protected ArrayList<Actor> doInBackground(String... strings) {
        String url = strings[0];
        ArrayList<Actor> actors = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements actorsElements = document.select(".lister-item");
            for(Element actor:actorsElements)
            {
                String fullName = actor.select(".lister-item-header > a").text();
                String film = actor.select(".text-muted > a").text();
                Element pictureElement = actor.select(".lister-item-image > a > img").first();
                String imgSrc = pictureElement.attr("src");
                Actor newActor = new Actor(imgSrc, fullName, film);
                actors.add(newActor);
            }

        }catch (IOException ioException)
        {

        }
        return actors;
    }
}
