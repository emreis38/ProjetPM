package com.pm.projetpkmn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.start();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        //getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    /*public boolean onOptionsItemSelected(@NonNull MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.accueil:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.Stats:
                intent = new Intent(this, StatsActivity.class);
                startActivity(intent);
                return true;

            case R.id.News:
                intent = new Intent(this, NewsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    public void launch(View view) {
        Intent intent;
        if (false) {
            Context context = getApplicationContext();
            CharSequence text = "Erreur : aucune connexion!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else {
            switch (view.getId()) {
                case R.id.b_stats:
                    intent = new Intent(this, StatsActivity.class);
                    break;
                case R.id.b_news:
                    intent = new Intent(this, NewsActivity.class);
                    break;
                default:
                    intent = new Intent(this, StatsActivity.class);//Supprimer la branche qu'on laisse dans défault
            }
            startActivity(intent);
        }
    }
}