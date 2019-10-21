package com.example.exercicio_obtem_filmes_populares;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CatalogAdapter extends ArrayAdapter<Catalog> {

    private List<Catalog> catalogs;
    private Context context;

    public CatalogAdapter(Context context, List<Catalog> catalogs) {
        super(context, -1, catalogs);
        this.context = context;
        this.catalogs = catalogs;
    }

    private class catalogViewHolder{
        ImageView posterPathImageView;
        TextView titleTextView;
        TextView resumeTextView;
    }

    @NonNull
    @Override
    public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {

        catalogViewHolder cvh = null;
        if (convertView != null) {
            cvh = (catalogViewHolder) convertView.getTag();
        } else{
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_main, parent, false);

            cvh = new catalogViewHolder();
            cvh.posterPathImageView = convertView.findViewById(R.id.posterPathImageView);
            cvh.titleTextView = convertView.findViewById(R.id.titleTextView);
            cvh.resumeTextView = convertView.findViewById(R.id.resumeTextView);

            convertView.setTag(cvh);
        }

        Catalog catalog = catalogs.get(i);
        Converter converter = new Converter(context, catalogs);

        converter.converterPosterPath(catalog, cvh.posterPathImageView);
        cvh.titleTextView.setText(catalog.catalogTitle);
        cvh.resumeTextView.setText(catalog.catalogOverview);

        return convertView;
    }



    @Override
    public int getCount () {
        return catalogs.size();
    }

}
