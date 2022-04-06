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
import android.os.AsyncTask;
import android.os.Bundle;
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
    String API_KEY = "dc19ace9-0a20-467a-b498-42e29a529bf3";
    SharedPreferences sharedpreferences;
    String mypreference = "recent";
    TextView tv;
    EditText et;
    Boolean bypass = false;
    ArrayList<stats> statistiques;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        String pseudo = i.getStringExtra("pseudo");
        setContentView(R.layout.activity_game);
        et = findViewById(R.id.input);
        tv = findViewById(R.id.tv1);
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
               bypass = false;
            }
        });
        hideButtons();
        updateRecent();
    }

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

    private View.OnClickListener setClickecText = new View.OnClickListener() {
        public void onClick(View v) {
            Button b = findViewById(v.getId());
            et.setText(b.getText().toString());
            View myView = findViewById(R.id.checkButton);
            bypass = true;
            myView.performClick();
        }
    };
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



    public void start(View view) {
        String pseudo = et.getText().toString();
        if(!bypass){
            DecalerPref(pseudo);
            bypass = false;
        }

        RequestTask rt = new RequestTask();
        rt.execute(pseudo);
    }


    private class RequestTask extends AsyncTask<String, Void, ArrayList<String>> {
        // Le corps de la tâche asynchrone (exécuté en tâche de fond)
        ArrayList<stats> statistiques;
        @Override
        protected ArrayList<String> doInBackground(String... pseudo) {
            ArrayList<String> array = new ArrayList<String>();
            String str = requete(pseudo[0]);
            array.add(str);
            return array;
        }
        private String requete(String pseudo) {
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
            return response;
        }
        private ArrayList<stats> decodeJSON(JSONArray jsa) throws Exception {
            statistiques = new ArrayList<>();
            stats stat;
            int i=0;
            while(i<jsa.length()){
                stat = new stats();
                JSONObject obj = jsa.getJSONObject(i);
                stat.setDpName(obj.getString("displayname"));
                stat.setExperience(obj.getString("Experience"));
                stat.setBedwarsLvl("bedwars_level");
                stat.setBedwarsWin("bedwars_wars");
                statistiques.add(stat);
                i++;
            }

            return statistiques;
        }
        private String getUUID(JSONObject jso) throws Exception {
            return jso.getString("id");
        }



        // Méthode appelée lorsque la tâche de fond sera terminée
        protected void onPostExecute(ArrayList<String> array) {
            display_stat(statistiques);
        }
    }
    protected void display_stat(ArrayList<stats> st){
        statistiques = st;
        //generatedId = View.generateViewId();
        int i = 0;
        while(i < statistiques.size()) {
            stats stat = statistiques.get(i);
            LinearLayout ll = new LinearLayout(this);
            ll.setId(i);
            ll.setMinimumHeight(500);
            ll.setOnClickListener(openDetails);
            TextView dpName = new TextView(this);
            dpName.setText(stat.getDpName());
            dpName.setTextColor(Color.BLUE);
            TextView Exp = new TextView(this);
            Exp.setText(stat.getExperience());
            Exp.setTextColor(Color.BLUE);
            TextView bdLvl = new TextView(this);
            bdLvl.setText(stat.getBedwarsLvl());
            bdLvl.setTextColor(Color.BLUE);
            TextView bdw = new TextView(this);
            bdw.setText(stat.getBedwarsWin());
            bdw.setTextColor(Color.BLUE);
            ll.addView(dpName);
            ll.addView(Exp);
            ll.addView(bdLvl);
            ll.addView(bdw);
            i++;
        }
    }
    private View.OnClickListener openDetails = new View.OnClickListener() {
        public void onClick(View v) {
            stats stat = statistiques.get(v.getId());
            Intent intent = new Intent(v.getContext(), PostDetailsActivity.class);
            intent.putExtra("slug", stat.getSlug());
            startActivity(intent);
        }
    };
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