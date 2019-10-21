package com.example.exercicio_obtem_filmes_populares;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.widget.ImageView;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.List;


public class Converter extends ArrayAdapter<Catalog>{

    private Context context;
    private List<Catalog> catalogs;

    public Converter(Context context, List<Catalog> catalogs) {
        super(context, -1, catalogs);
        this.context = context;
        this.catalogs = catalogs;
    }

    public void converterPosterPath(Catalog catalog , ImageView image){

        new Thread(() -> {
            try {
                URL url = new URL(catalog.catalogPosterPath);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();

                Bitmap figura = BitmapFactory.decodeStream(is);
                ((Activity) context).runOnUiThread(() -> {
                   image.setImageBitmap(figura);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    @Override
    public int getCount () {
        return catalogs.size() ;
    }

}
