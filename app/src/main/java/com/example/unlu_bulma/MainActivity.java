package com.example.unlu_bulma;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    String url = "https://www.imdb.com/list/ls058011111/";
    ImageView imageView;
    TextView filmTextView;

    Actor actor;
    int index;
    int correctoption=0;//0,1,2

    ArrayList<Actor> actors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetVariables();
        GetActorsFromWeb();
        StartGame();
    }
    private void SetVariables()
    {
        imageView = (ImageView) findViewById(R.id.imageView);
        filmTextView = (TextView) findViewById(R.id.textView);
    }
    private void GetActorsFromWeb()
    {
        try {
            GetActorsFromWeb getActorsFromWeb = new GetActorsFromWeb();
            actors = getActorsFromWeb.execute(url).get();
        }catch (InterruptedException interruptedException)
        {

        }catch (ExecutionException executionException)
        {

        }
    }
    private void StartGame()
    {
        DetermineCorrectOption();
        SetDataOnUI();
    }
    private void DetermineCorrectOption()
    {
        Random random = new Random();
        correctoption = random.nextInt(3);
    }
    private void SetDataOnUI()
    {
        actor = GetRandomActor();
        Picasso.get().load(actor.pictureLink).into(imageView);
        filmTextView.setText(actor.film);
        SetOptionsOnUI();
    }
    private void SetOptionsOnUI()
    {
        Button btn1 = findViewById(R.id.button);
        SetOptionOnUI(btn1, 0);

        Button btn2 = findViewById(R.id.button2);
        SetOptionOnUI(btn2, 1);

        Button btn3 = findViewById(R.id.button3);
        SetOptionOnUI(btn3, 2);
    }
    private void SetOptionOnUI(Button btn, int btnOption)
    {
        if(correctoption==btnOption)
        {
            btn.setText(actor.fullName);
        }
        else
        {
            int i = GenerateDifferentIndex();
            Actor ranActor = actors.get(i);
            btn.setText(ranActor.fullName);
        }
    }

    private Actor GetRandomActor()
    {
        Random rand = new Random();
        int index = rand.nextInt(100);
        this.index=index;
        return actors.get(index);
    }
    private int GenerateDifferentIndex()
    {
        int i=index;
        do{
            Random random = new Random();
            i = random.nextInt(100);
        }while(i==index);

        return i;
    }

    private void CheckWinCondition(int optionIndex)
    {
        if(correctoption==optionIndex)
        {
            Toast.makeText(this, "Tebrikler",Toast.LENGTH_SHORT).show();
            StartGame();
        }
        else
        {
            Toast.makeText(this, "Malesef :(",Toast.LENGTH_SHORT).show();
        }
    }

    public void Option1(View view)
    {
        CheckWinCondition(0);
    }

    public void Option2(View view)
    {
        CheckWinCondition(1);
    }
    public void Option3(View view)
    {
        CheckWinCondition(2);
    }
}