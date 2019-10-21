package com.example.exercicio_obtem_filmes_populares;

import java.util.Locale;

public class Catalog {

    public final String catalogPosterPath;
    public final String catalogTitle;
    public final String catalogOverview;

    public Catalog (
            String catalogPosterPath,
            String catalogTitle,
            String catalogOverview
    ){
        this.catalogPosterPath=String.format(Locale.getDefault(),
                "https://image.tmdb.org/t/p/w500%s", catalogPosterPath);
        this.catalogTitle=catalogTitle;
        this.catalogOverview=catalogOverview;

    }



}
