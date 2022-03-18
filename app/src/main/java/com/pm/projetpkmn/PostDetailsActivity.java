package com.pm.projetpkmn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        String url = getIntent().getStringExtra("slug");
        //url = "https://hytale.com/api/blog/post/slug/" + url; pour l'api
        Log.d("url", url);
      //  As

    }
    private class RequestTask extends AsyncTask<Integer, Void, ArrayList<String>> {
        ArrayList<blog_post> blogList;

        @Override
        protected ArrayList<String> doInBackground(Integer... nb) {
            ArrayList<String> array = new ArrayList<String>();
            //requete();
            return array;
        }
        private String requete(String slug) {
            ArrayList<blog_post> blogList;
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
                JSONArray toDecode = new JSONArray(response);
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
        private blog_post decodeJSON(JSONArray jsa) throws Exception {
            blog_post blog = new blog_post();
            int i = 0;
            while(i < jsa.length()){
                JSONObject obj = jsa.getJSONObject(i);
                blog.setAuthor(obj.getString("author"));
                blog.setTitle(obj.getString("title"));
                JSONObject img = obj.getJSONObject("coverImage");
                blog.setImgUrl(img.getString("s3Key"));
                blog.setSmallContent(obj.getString("bodyExcerpt"));
                blog.setSmallContent(blog.getSmallContent().substring(0, 200).concat("..."));
                blog.setSlug(obj.getString("slug"));
                i++;
            }
            return blog;
        }
        protected void onPostExecute(ArrayList<String> array) {

        }
    }
}