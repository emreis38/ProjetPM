package com.pm.projetpkmn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.provider.Settings;
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
            //Joue la musique
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.music);
            mediaPlayer.start();

    }
    //on instancie les boutons sur la pages d'accueil et les actions qui leurs sont dédiés.
    //suivant le boutons sur lequel l'utilisateur appuie, la page qu'il demande se lance sous forme d'Intent.
    public void launch(View view) {
        Intent intent;
        //Switch selon le bouton cliqué pour lancer l'activité correspondante,
        // en testant si l'utilisateur à activé la wifi/ les données mobiles
            switch (view.getId()) {
                case R.id.b_stats:
                    if (!isConnected(this)) {
                        showCustomDialog();
                    } else {
                        intent = new Intent(this, StatsActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.b_news:
                    if (!isConnected(this)) {
                    showCustomDialog();

                } else {
                        intent = new Intent(this, NewsActivity.class);
                        startActivity(intent);
                    }
                    break;
                default:
                    intent = new Intent(this, StatsActivity.class);//Supprimer la branche qu'on laisse dans défault
            }

        }

    //Cette méthode va nous servire à vérifier si il y a une connexion de quelconque type (wifi ou données mobile)
    //à internet. Si non un message comm
    private boolean isConnected(MainActivity mainActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
        if (wifiConn != null && wifiConn.isConnected() || mobileConn != null && mobileConn.isConnected()){
            return true;
        }
        else{
            return false;
        }

    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Veuillez vous connecter à Internet pour continuer")
                .setCancelable(false)
                .setPositiveButton("Connectez vous", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        System.exit(0);
                    }
                });
        builder.show();

    }
}