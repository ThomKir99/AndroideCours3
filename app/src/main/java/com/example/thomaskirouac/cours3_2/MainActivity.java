package com.example.thomaskirouac.cours3_2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
     ImageView homeImage;
     String bingURL = "http://www.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1";
    ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeImage= findViewById(R.id.imageView_homeImage);
        progressBar =  findViewById(R.id.progressBar_downloadImage);
        setListener();
    }

    private void setListener(){
    findViewById(R.id.btn_downloadImage).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
         progressBar.setVisibility(View.VISIBLE);
        downloadBingImageOfTheDayLink();

        }
    });
    findViewById(R.id.btn_connexion).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
moveToConnectActivity();
        }
    });
    }

    private void moveToConnectActivity(){
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
    }

    private void downloadHomeImage(String imageUrl){
        ImageDownloader imageDownloader = new ImageDownloader();
        try {
            imageDownloader.execute("https://bing.com"+imageUrl);
        }catch (Exception e){

        }
    }
    public class ImageDownloader extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url =new URL(urls[0]);
                HttpsURLConnection connexion = (HttpsURLConnection) url.openConnection();
                connexion.connect();
                InputStream inputStream = connexion.getInputStream();
                Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                return imageBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            homeImage.setImageBitmap(bitmap);
        }
    }

    private void downloadBingImageOfTheDayLink(){

        BindImageLinkDownloader bindImageLinkDownloader = new BindImageLinkDownloader();
        try {
            bindImageLinkDownloader.onPreExecute();
            bindImageLinkDownloader.execute(bingURL);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public class BindImageLinkDownloader extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            BufferedReader reader = null;
            HttpURLConnection connection=null;
            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();
                if(inputStream == null){
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader( inputStream));
                String line;
                while ((line = reader.readLine())!=null){
                    stringBuffer.append(line+"\n");
                }
                if(stringBuffer.length()==0){
                    return null;
                }
                return stringBuffer.toString();
            } catch (MalformedURLException e) {
            e.printStackTrace();
                return null;
            }catch(IOException e){
            e.printStackTrace();
                return null;
        }finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String responses) {
          if(responses!=null){
              JSONObject jsonroot = null;
              try {
                  jsonroot = new JSONObject(responses);
                  JSONArray jsonArrayImage = jsonroot.getJSONArray("images");
                  String urlImage = jsonArrayImage.getJSONObject(0).getString("url");
                  downloadHomeImage(urlImage);
                  progressBar.setVisibility(View.INVISIBLE);

              }catch (Exception e){
                  e.printStackTrace();
              }
          }
        }
    }

}
