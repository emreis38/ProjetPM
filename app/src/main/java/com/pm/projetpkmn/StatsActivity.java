package com.pm.projetpkmn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.text.LineBreaker;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {
    //Commençons par initialiser nos variables
    stats info;
    String API_KEY = "dc19ace9-0a20-467a-b498-42e29a529bf3";
    SharedPreferences sharedpreferences;
    String mypreference = "recent";
    TextView tv, tv1, tv2, tv3, tv4, tv5, tvs, tv6, tv7, tv8;
    EditText et;
    Boolean bypass = false;

    //On instancie les valeur des la création de l'activité.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        String pseudo = i.getStringExtra("pseudo");
        setContentView(R.layout.activity_game);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.start();
        et = findViewById(R.id.input);
        tv = findViewById(R.id.tv1);
        tv1 = findViewById(R.id.tv2);
        tv2 = findViewById(R.id.tv3);
        tv3 = findViewById(R.id.tv4);
        tv4 = findViewById(R.id.tv5);
        tv5 = findViewById(R.id.tv6);
        tvs = findViewById(R.id.statsont);
        tv6 = findViewById(R.id.tv7);
        tv7 = findViewById(R.id.tv8);
        tv8 = findViewById(R.id.tv9);
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
               bypass = false;
            }
        });
        hideButtons();
        updateRecent();
    }

    //Ici nous initialisons les boutons du système de préférence.
    private void hideButtons() {
        Button b = findViewById(R.id.b1);
        b.setVisibility(View.GONE);
        b.setOnClickListener(setClickecText);
        b = findViewById(R.id.b2);
        b.setVisibility(View.GONE);
        b.setOnClickListener(setClickecText);;
        b = findViewById(R.id.b3);
        b.setVisibility(View.GONE);
        b.setOnClickListener(setClickecText);
        b = findViewById(R.id.b4);
        b.setVisibility(View.GONE);
        b.setOnClickListener(setClickecText);
        b = findViewById(R.id.b5);
        b.setVisibility(View.GONE);
        b.setOnClickListener(setClickecText);

    }
    //Une méthode pour dire qu'il faut récupérer la valeur mise dans l'Edit Text quand on clic sur le bouton
    private View.OnClickListener setClickecText = new View.OnClickListener() {
        public void onClick(View v) {
            Button b = findViewById(v.getId());
            et.setText(b.getText().toString());
            View myView = findViewById(R.id.checkButton);
            bypass = true;
            myView.performClick();
        }
    };
    //Cette méthode sert à afficher le Menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    //Nous disons vers quelle page re-diriger quand on clique sur l'une des pages du menu
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
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
    }



    public void start(View view) {
        String pseudo = et.getText().toString();
        if(!bypass){
            DecalerPref(pseudo);
            bypass = false;
        }

        RequestTask rt = new RequestTask();
        rt.execute(pseudo);
    }

    //Ici nous récupérons le JSON
    private class RequestTask extends AsyncTask<String, Void, ArrayList<String>> {
        // Le corps de la tâche asynchrone (exécuté en tâche de fond)
        ArrayList<stats> statistiques;
        @Override
        protected ArrayList<String> doInBackground(String... pseudo) {
            ArrayList<String> array = new ArrayList<String>();
            info = requete(pseudo[0]);

            return array;
        }
        private stats requete(String pseudo) {
            stats te = null;
            String response = "";
            ArrayList<String> stats;
            try {
                HttpURLConnection connection = null;
                URL url = new
                        URL("https://api.mojang.com/users/profiles/minecraft/"+ URLEncoder.encode(pseudo,"utf-8"));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String ligne = bufferedReader.readLine() ;
                while (ligne!= null){
                    response+=ligne;
                    ligne = bufferedReader.readLine();
                }
                JSONObject toDecode = new JSONObject(response);
                response = getUUID(toDecode);
                Log.d("uuid", String.valueOf(response.length()));
                if(response.length() > 1){
                    HttpURLConnection connection2 = null;
                    URL url2 = new
                            URL("https://api.hypixel.net/player?uuid="+ URLEncoder.encode(response,"utf-8")+"&key=" + URLEncoder.encode(API_KEY, "utf-8"));
                    connection2 = (HttpURLConnection) url2.openConnection();
                    connection2.setRequestMethod("GET");
                    InputStream inputStream2 = connection2.getInputStream();
                    InputStreamReader inputStreamReader2 = new InputStreamReader(inputStream2);
                    bufferedReader = new BufferedReader(inputStreamReader2);
                    String str = bufferedReader.readLine();
                    response = "";

                    while (str!= null){
                        response+=str;
                        str = bufferedReader.readLine();
                    }
                    toDecode = new JSONObject(response);
                    te = decodeJSON(toDecode);
                    //response = decodeJSON(toDecode);
                }
            } catch (UnsupportedEncodingException e) {
                response = "problème d'encodage";
            } catch (MalformedURLException e) {
                response = "problème d'URL ";
            } catch (IOException e) {
                response = "problème de connexion ";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return te;
        }
        //On parse le JSON afin de récupérer seulement les informations qui nous serrons utiles.
        private stats decodeJSON(JSONObject jsa) throws Exception {

            stats stat = null;
            int i=0;
            while(i<jsa.length()){
                stat = new stats();
                JSONObject obj = jsa.getJSONObject("player");
                JSONObject obj1 = obj.getJSONObject("stats");
                JSONObject obj2 = obj1.getJSONObject("Bedwars");
                JSONObject obj3 = obj.getJSONObject("achievements");
                stat.setDpName(obj.getString("displayname"));
                stat.setExperience(obj2.getString("Experience"));
                stat.setBedwarsLvl(obj3.getString("bedwars_level"));
                stat.setBedwarsWin(obj3.getString("bedwars_wins"));
                stat.setLanguage(obj.getString("language"));
                stat.setUuId(obj.getString("uuid"));
                stat.setBedwarsplg(obj2.getString("games_played_bedwars_1"));
                stat.setSkwins(obj3.getString("skywars_wins_solo"));
                stat.setSkwint(obj3.getString("skywars_wins_team"));
                Log.d("ici", stat.toString());
                i++;
            }

            return stat;
        }
        private String getUUID(JSONObject jso) throws Exception {
            return jso.getString("id");
        }



        // Méthode appelée lorsque la tâche de fond sera terminée
        protected void onPostExecute(ArrayList<String> array) {
            display_stat(statistiques);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //Cette méthode sert à gérer l'affichage.
    protected void display_stat(ArrayList<stats> st) {
        String s = et.getText().toString();
        if (s.equals("")) { //Si l'utilisateur ne rentre pas de pseudo, l'appli lui demande de rentrer un pseudo
            tv.setText("Entrer un pseudo");
            tv1.setText("");
            tv2.setText("");
            tv3.setText("");
            tv4.setText("");
            tv5.setText("");
            tv6.setText("");
            tv7.setText("");
            tv8.setText("");
            tvs.setText("");

        } else {
            try {
                tv.setText(" UUID : " + info.getUuId()); //On essaie de récupérer l'UUID du joueur
            } catch (Exception e) { //Comme chaque joueur en possède un, si il n'y en a pas c'est que le joueur
                tv.setText(" Cet utilisateur n'existe pas"); // n'existe pas, c'est pourquoi on retourne ce message.
                tv1.setText("");
                tv2.setText("");
                tv3.setText("");
                tv4.setText("");
                tv5.setText("");
                tv6.setText("");
                tv7.setText("");
                tv8.setText("");
                tvs.setText("");
            }//Si on nous retourne pas ce message c'est qu'un UUID a été trouver, et que le joueur existe bien.
            if (tv.getText() != "Cet utilisateur n'éxiste pas") {
                tvs.setText(" Les stats de ce joueur sont : ");
                try {
                    tv1.setText(" Pseudo : " + info.getDpName());
                } catch (Exception e) {
                    tv1.setText(" Cet utilisateur n'a pas de nom");
                }
                try {
                    tv2.setText(" Experience : " + info.getExperience());
                } catch (Exception e) {
                    tv2.setText(" Cet utilisateur n'à pas d'experience");
                }
                try {
                    tv3.setText(" Langue : " + info.getLanguage());
                } catch (Exception e) {
                    tv3.setText(" Cet utilisateur ne possède pas de langage définit");
                }
                try {
                    tv4.setText(" Parties jouées en mode Bedwars : " + info.getBedwarsplg());
                } catch (Exception e) {
                    tv4.setText(" Cet utilisateur ne possède pas de victoire en BedWars");
                }
                try {
                    tv5.setText(" Victoire en bedwars : " + info.getBedwarsWin());
                } catch (Exception e) {
                    tv5.setText(" Cet utilisateur n'a jamais gagné en BedWars");
                }
                try {
                    tv6.setText(" Niveau en bedwars : " + info.getBedwarsLvl());
                } catch (Exception e) {
                    tv6.setText(" Cet utilisateur ne possède pas de level en mode BedWars");
                }
                try {
                    tv7.setText(" Victoire solo en Skywars : " + info.getSkwins());
                } catch (Exception e) {
                    tv7.setText(" Cet utilisateur n'a jamais gagné en skywars");
                }
                try {
                    tv8.setText(" Niveau en bedwars : " + info.getSkwint());
                } catch (Exception e) {
                    tv8.setText(" Cet utilisateur  n'a jamais gagné en BedWars en équipe");
                }

            }
        }
    }

    //Ici nous déclarons notre système de préférence.
    private void DecalerPref(String pseudo ){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String nick1 = sharedpreferences.getString("first", "no");
        String nick2 = sharedpreferences.getString("second", "no");
        String nick3 = sharedpreferences.getString("third", "no");
        String nick4 = sharedpreferences.getString("four", "no");
        if(!nick1.equals("no")){
            editor.putString("second", nick1);
            if(!nick2.equals("no")){
                editor.putString("third", nick2);
                if(!nick3.equals("no")) {
                    editor.putString("four", nick3);
                    if(!nick4.equals("no")) {
                        editor.putString("five", nick4);
                    }
                }
            }
        }
        editor.putString("first", pseudo);
        editor.commit() ;
    }

    //Chaque fois que l'utilisateur rentre un nouveau pseudo, le système de préférence est mis a jour,
    //les premières recherches disparaisse, et laisse leur place aux dernières.
    private void updateRecent(){
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        String nick = sharedpreferences.getString("first", "no");
        Button b = findViewById(R.id.b1);
        if(!nick.equals("no")){
            LinearLayout ll = findViewById(R.id.ll_nicknames);
            b.setText(nick);
            b.setVisibility(View.VISIBLE);
            nick = sharedpreferences.getString("second", "no");
            b = findViewById(R.id.b2);
            if(!nick.equals("no")){
                b.setText(nick);
                b.setVisibility(View.VISIBLE);
                nick = sharedpreferences.getString("third", "no");
                b = findViewById(R.id.b3);
                if(!nick.equals("no")){
                    b.setText(nick);
                    b.setVisibility(View.VISIBLE);
                    nick = sharedpreferences.getString("four", "no");
                    b = findViewById(R.id.b4);
                    if(!nick.equals("no")){
                        b.setText(nick);
                        b.setVisibility(View.VISIBLE);
                        nick = sharedpreferences.getString("five", "no");
                        b = findViewById(R.id.b5);
                        if(!nick.equals("no")){
                            b.setText(nick);
                            b.setVisibility(View.VISIBLE);
                        }
                    }
                }

            }

        }
    }
}