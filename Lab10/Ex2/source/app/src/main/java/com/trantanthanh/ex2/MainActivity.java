package com.trantanthanh.ex2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.trantanthanh.ex2.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://covid-193.p.rapidapi.com";
    private CovidApiService covidApiService;

    private ActivityMainBinding binding;

    private List<String> countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Covid19");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        covidApiService = retrofit.create(CovidApiService.class);

        // Gọi API để lấy danh sách quốc gia
        Call<CountryResponse> countryCall = covidApiService.getCountries();
        countryCall.enqueue(new Callback<CountryResponse>() {
            @Override
            public void onResponse(Call<CountryResponse> call, Response<CountryResponse> response) {
                if (response.isSuccessful()) {
                    CountryResponse countryResponse = response.body();
                    countries = countryResponse.getResponse();
                    Log.d("COUNTRY", countries.get(0));
                    invalidateOptionsMenu();
                } else {
                    // Xử lý khi request không thành công
                    int statusCode = response.code();
                    Log.e("API_CALL", "Request failed with status code: " + statusCode);

                }
            }

            @Override
            public void onFailure(Call<CountryResponse> call, Throwable t) {
                // Xử lý khi yêu cầu thất bại
                Log.e("API_CALL", "Request failed: " + t.getMessage());
            }
        });


        Call<StatisticsResponse> call = covidApiService.getTotals();
        call.enqueue(new Callback<StatisticsResponse>() {
            @Override
            public void onResponse(Call<StatisticsResponse> call, Response<StatisticsResponse> response) {
                if (response.isSuccessful()) {
                    StatisticsResponse body = response.body();
                    StatisticsResponse.CovidData data = body.getResponse()[0];
                    long confirmedCases = data.getCases().getActive();
                    long recoveredCases = data.getCases().getRecovered();
                    long criticalCases = data.getCases().getCritical();
                    long deathCases = data.getDeaths().getTotal();
                    binding.txtConfirm.setText(confirmedCases + "\nConfirmed");
                    binding.txtRecovered.setText(recoveredCases + "\nRecovered");
                    binding.txtCritical.setText(criticalCases + "\nCritical");
                    binding.txtDeaths.setText(deathCases + "\nDeaths");
                } else {
                    // Xử lý khi có lỗi từ server
                    Log.d("Failed", response.message());
                }
            }

            @Override
            public void onFailure(Call<StatisticsResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (countries != null) {
            for (String country : countries) {
                menu.add(Menu.NONE, Menu.NONE, countries.indexOf(country), country);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String selectedCountry = item.getTitle().toString();

        Toast.makeText(getApplicationContext(), "Selected Country: " + selectedCountry, Toast.LENGTH_SHORT).show();
        binding.txtCountry.setText(selectedCountry);
        Call<StatisticsResponse> call = covidApiService.getByCountry(selectedCountry);
        call.enqueue(new Callback<StatisticsResponse>() {
            @Override
            public void onResponse(Call<StatisticsResponse> call, Response<StatisticsResponse> response) {
                if (response.isSuccessful()) {
                    StatisticsResponse body = response.body();
                    StatisticsResponse.CovidData data = body.getResponse()[0];
                    long confirmedCases = data.getCases().getActive();
                    long recoveredCases = data.getCases().getRecovered();
                    long criticalCases = data.getCases().getCritical();
                    long deathCases = data.getDeaths().getTotal();
                    binding.txtConfirm.setText(confirmedCases + "\nConfirmed");
                    binding.txtRecovered.setText(recoveredCases + "\nRecovered");
                    binding.txtCritical.setText(criticalCases + "\nCritical");
                    binding.txtDeaths.setText(deathCases + "\nDeaths");
                } else {
                    // Xử lý khi có lỗi từ server
                    Log.d("Failed", response.message());
                }
            }

            @Override
            public void onFailure(Call<StatisticsResponse> call, Throwable t) {

            }
        });
        return super.onOptionsItemSelected(item);
    }
}