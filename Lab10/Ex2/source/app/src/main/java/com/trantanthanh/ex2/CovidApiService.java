package com.trantanthanh.ex2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CovidApiService {
    @Headers({
            "x-rapidapi-host: covid-193.p.rapidapi.com",
            "x-rapidapi-key: d5d62d9022msh8970e82b3ad8db9p1aa180jsn25837956ae64"
    })
    @GET("/statistics?country=all")
    Call<StatisticsResponse> getTotals();

    @Headers({
            "x-rapidapi-host: covid-193.p.rapidapi.com",
            "x-rapidapi-key: d5d62d9022msh8970e82b3ad8db9p1aa180jsn25837956ae64"
    })
    @GET("/statistics")
    Call<StatisticsResponse> getByCountry(@Query("country") String country);

    @Headers({
            "x-rapidapi-host: covid-193.p.rapidapi.com",
            "x-rapidapi-key: d5d62d9022msh8970e82b3ad8db9p1aa180jsn25837956ae64"
    })
    @GET("/countries")
    Call<CountryResponse> getCountries();
}