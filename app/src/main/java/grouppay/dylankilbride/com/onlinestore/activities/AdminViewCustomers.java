package grouppay.dylankilbride.com.onlinestore.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import grouppay.dylankilbride.com.onlinestore.Adapters.AdminProductsRVAdapter;
import grouppay.dylankilbride.com.onlinestore.Adapters.CustomerRVAdapter;
import grouppay.dylankilbride.com.onlinestore.R;
import grouppay.dylankilbride.com.onlinestore.models.Customer;
import grouppay.dylankilbride.com.onlinestore.models.Product;
import grouppay.dylankilbride.com.onlinestore.web_service_api.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminViewCustomers extends AppCompatActivity {

  private RecyclerView adminCustomersView;
  private List<Customer> customerList = new ArrayList<>();
  private String baseUrl = "http://10.0.2.2:8080";
  private RetrofitAPI webserviceApi;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_view_customers);
  }

  public void setUpCustomerRecyclerView(List<Customer> customerList) {
    adminCustomersView = (RecyclerView) findViewById(R.id.adminCustomersRecyclerView); //TODO CHANGE ID
    LinearLayoutManager customersListLayoutManager = new LinearLayoutManager(this);
    adminCustomersView.setLayoutManager(customersListLayoutManager);
    adminCustomersView.setAdapter(new CustomerRVAdapter(customerList, R.layout.customer_list_item));
  }

  private void setUpCustomersListCall() {
    Retrofit getCustomers = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    webserviceApi = getCustomers.create(RetrofitAPI.class);
    makeGetCustomersRequest();
  }

  private void makeGetCustomersRequest() {
    Call<List<Customer>> call = webserviceApi.getAllCustomers();
    call.enqueue(new Callback<List<Customer>>() {
      @Override
      public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
        if(!response.isSuccessful()){
          Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
        } else {
          customerList.clear();
          for (int i = 0; i < response.body().size(); i++) {
            customerList.add(new Customer(response.body().get(i).getUsername(),
                response.body().get(i).getEmail()));
          }
          setUpCustomerRecyclerView(customerList);
        }
      }

      @Override
      public void onFailure(Call<List<Customer>> call, Throwable t) {

      }

    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    setUpCustomersListCall();
  }
}
