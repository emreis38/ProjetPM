package com.pm.projetpkmn;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
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

public class PostDetailsActivity extends AppCompatActivity {
    blog_post post;
    TextView tv;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.start();
        tv = findViewById(R.id.tv_content);
        iv = findViewById(R.id.img);
        //Récupère le lien de l'article à affiché.
        String slug = getIntent().getStringExtra("slug");
        RequestTask rt = new RequestTask();
        rt.execute(slug);

    }
    private class RequestTask extends AsyncTask<String, Void, String> {
        // Le corps de la tâche asynchrone (exécuté en tâche de fond)
        protected String doInBackground(String... slug) {
            String response = requete(slug[0]);
            return response;
        }
        private String requete(String slug) {
            String response = "";
            try {
                HttpURLConnection connection = null;
                URL url = new
                        URL("https://hytale.com/api/blog/post/slug/" + URLEncoder.encode(slug, "utf-8"));
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
                decodeJSON(toDecode);
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
        private void decodeJSON(JSONObject obj) throws Exception {
            //Rempli l'objet représentant l'article
            blog_post blog = new blog_post();
            blog.setAuthor(obj.getString("author"));
            blog.setTitle(obj.getString("title"));
            blog.setContent(obj.getString("body"));
            JSONObject img = obj.getJSONObject("coverImage");
            blog.setImgUrl(img.getString("s3Key"));
            post = blog;
        }
        protected void onPostExecute(String result) {
            //Picasso met l'image dans l'image view.
            Picasso.get().load(post.getImgUrl()).fit().into(iv);
            //Affiche le contenu html directement.
            tv.setText(Html.fromHtml(post.getContent(), 0));
        }
    }
}