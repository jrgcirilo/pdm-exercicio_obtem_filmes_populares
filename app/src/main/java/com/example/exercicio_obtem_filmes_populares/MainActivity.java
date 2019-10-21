package com.example.exercicio_obtem_filmes_populares;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private CatalogAdapter adapter;
    private ListView moviesListView;
    private List<Catalog> catalogList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        moviesListView = findViewById(R.id.moviesListView);
        catalogList = new ArrayList<>();
        adapter = new CatalogAdapter(this, catalogList);
        moviesListView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.refreshFloatingActionButton);
        fab.setOnClickListener((View v) -> {

            @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String site =
                    getString(R.string.site_TMDX
                    );


            new Thread(
                    () -> {

                        try {
                            URL url = new URL(site);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            InputStream is = conn.getInputStream();
                            InputStreamReader isr = new InputStreamReader(is);
                            BufferedReader reader = new BufferedReader(isr);
                            String l = null;
                            StringBuilder res = new StringBuilder();

                            while ((l = reader.readLine()) != null) {
                                res.append(l);

                            }
                            reader.close();
                            conn.disconnect();
                            libraryJSON(res.toString());


                        } catch (MalformedURLException ex){
                            ex.printStackTrace();
                        }
                        catch (IOException ex){
                            ex.printStackTrace();
                        }
                            }
                        ).start();
                    }
            );
        }










        private void libraryJSON (String textJSON) throws IOException {
            String site = getString(R.string.site_TMDX);
            URL url = new URL(site);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            try {

                JSONObject json = new JSONObject(textJSON);
                JSONArray res = json.getJSONArray("results");
                for (int i = 0; i < res.length(); i++) {
                    JSONObject obj = res.getJSONObject(i);
                    String posterPath = obj.getString("poster_path");
                    String title = obj.getString("title");
                    String overview = obj.getString("overview");

                    Catalog m = new Catalog(posterPath, title, overview);
                    catalogList.add(m);
                }
                runOnUiThread(
                        () ->{
                            adapter.notifyDataSetChanged();
                        }
                );
            }

             catch (Exception e) {
                e.printStackTrace();
            }

            /*@Override
            public boolean onCreateOptionsMenu (Menu menu){
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected (MenuItem item){
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    return true;
                }

                return super.onOptionsItemSelected(item);
            }*/




        }
}