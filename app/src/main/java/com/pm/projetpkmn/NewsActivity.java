package com.pm.projetpkmn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.text.LineBreaker;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {
    TextView tv;
    LinearLayout ll;
    ArrayList<blog_post> blogList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ll = (LinearLayout)findViewById(R.id.layout);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.start();
        tv = findViewById(R.id.tv1);
        RequestTask rt = new RequestTask();
        rt.execute();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        //Code de l'action bar
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

    private class RequestTask extends AsyncTask<Integer, Void, ArrayList<String>> {
        //R??cupere une list d'article de blog
        ArrayList<blog_post> blogList;

        @Override
        protected ArrayList<String> doInBackground(Integer... nb) {
            ArrayList<String> array = new ArrayList<String>();
            requete();
            return array;
        }
        private String requete() {
            ArrayList<blog_post> blogList;
            String response = "";
            try {
                HttpURLConnection connection = null;
                URL url = new
                        URL("https://hytale.com/api/blog/post/published");
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
                JSONArray toDecode = new JSONArray(response);
                decodeJSON(toDecode);
            } catch (UnsupportedEncodingException e) {
                response = "probl??me d'encodage";
            } catch (MalformedURLException e) {
                response = "probl??me d'URL ";
            } catch (IOException e) {
                response = "probl??me de connexion ";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
        private ArrayList<blog_post> decodeJSON(JSONArray jsa) throws Exception {
            blogList = new ArrayList<blog_post>();
            blog_post blog;
            int i = 0;
            while(i < jsa.length()){
                blog = new blog_post();
                JSONObject obj = jsa.getJSONObject(i);
                blog.setAuthor(obj.getString("author"));
                blog.setTitle(obj.getString("title"));
                JSONObject img = obj.getJSONObject("coverImage");
                blog.setImgUrl(img.getString("s3Key"));
                blog.setSmallContent(obj.getString("bodyExcerpt"));
                blog.setSmallContent(blog.getSmallContent().substring(0, 200).concat("..."));
                blog.setSlug(obj.getString("slug"));
                blogList.add(blog);
                i++;
            }
            return blogList;
        }
        protected void onPostExecute(ArrayList<String> array) {
            display_blog(blogList);
        }
    }
    protected void display_blog(ArrayList<blog_post> bl){
        //G??n??re autant de carte que n??cessaire avec l'image, le titre et le d??but du contenu
        blogList = bl;
        int i = 0;
        while(i < blogList.size()) {
            blog_post post = blogList.get(i);
            CardView cv = new CardView(this);
            cv.setId(i);
            cv.setCardElevation(10);
            cv.setMinimumHeight(500);
            cv.setOnClickListener(openDetails);
            cv.setPreventCornerOverlap(true);
            cv.setCardBackgroundColor(0);
            cv.setUseCompatPadding(true);
            ImageView iv = new ImageView(this);
            Picasso.get().load(post.getImgUrl()).fit().into(iv);
            TextView titre = new TextView(this);
            titre.setText(post.getTitle());
            titre.setTextColor(Color.BLACK);
            titre.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
            titre.setPadding(0, 0, 0, 220);
            titre.setTextSize(18);
            iv.setPadding(20, 20, 20, 290);
            TextView tv = new TextView(this);
            tv.setText(post.getSmallContent());
            tv.setGravity(Gravity.BOTTOM);
            tv.setPadding(30, 10, 30, 20);
            tv.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            cv.addView(iv);
            cv.addView(titre);
            cv.addView(tv);
            ll.addView(cv);
            i++;
        }
    }
    private View.OnClickListener openDetails = new View.OnClickListener() {
        //Lance l'affichage de l'activit?? affichant les d??tails d'un article
        public void onClick(View v) {
            blog_post post = blogList.get(v.getId());
            Intent intent = new Intent(v.getContext(), PostDetailsActivity.class);
            intent.putExtra("slug", post.getSlug());
            startActivity(intent);
        }
    };
}