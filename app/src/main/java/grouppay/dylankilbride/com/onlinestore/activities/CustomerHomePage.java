package grouppay.dylankilbride.com.onlinestore.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import grouppay.dylankilbride.com.onlinestore.Adapters.ProductsRVAdapter;
import grouppay.dylankilbride.com.onlinestore.R;
import grouppay.dylankilbride.com.onlinestore.models.AuthorisationResponse;
import grouppay.dylankilbride.com.onlinestore.models.LoginDetails;
import grouppay.dylankilbride.com.onlinestore.models.Product;
import grouppay.dylankilbride.com.onlinestore.web_service_api.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerHomePage extends AppCompatActivity {

  private Spinner sortOptions;
  private RecyclerView prodcutsListRV;
  private RetrofitAPI webserviceApi;
  private ProductsRVAdapter adapter;
  private String baseUrl = "http://10.0.2.2:8080";
  private String selectedSortType = "ascending";
  private List<Product> productList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_customer_home_page);

    String[] selectableSortingOptions = {"ascending", "descending"};
    ArrayAdapter<String> dropDownAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, selectableSortingOptions);
    sortOptions = findViewById(R.id.sortOptions);
    sortOptions.setAdapter(dropDownAdapter);
    sortOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedSortType = adapterView.getSelectedItem().toString();
        setUpProductsListCall();
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });
  }

  public void setUpProductsRecyclerView(List<Product> productsList) {
    prodcutsListRV = (RecyclerView) findViewById(R.id.customerProductsRecyclerView);
    LinearLayoutManager productsListLayoutManager = new LinearLayoutManager(this);
    prodcutsListRV.setLayoutManager(productsListLayoutManager);
    prodcutsListRV.setAdapter(new ProductsRVAdapter(productsList, R.layout.product_list_item));

  }

  private void setUpProductsListCall() {
    Retrofit getProducts = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    webserviceApi = getProducts.create(RetrofitAPI.class);
    makeGetProductsRequest();
  }

  private void makeGetProductsRequest() {
    Call<List<Product>> call = webserviceApi.getAllProducts(selectedSortType);
    call.enqueue(new Callback<List<Product>>() {
      @Override
      public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
        if(!response.isSuccessful()){
          Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
        } else {
          productList.clear();
          for (int i = 0; i < response.body().size(); i++) {
            productList.add(new Product(response.body().get(i).getManufacturer(),
                response.body().get(i).getTitle(),
                response.body().get(i).getPrice(),
                response.body().get(i).getStock()));
          }
          setUpProductsRecyclerView(productList);
        }
      }

      @Override
      public void onFailure(Call<List<Product>> call, Throwable t) {

      }

    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    setUpProductsListCall();
  }
}