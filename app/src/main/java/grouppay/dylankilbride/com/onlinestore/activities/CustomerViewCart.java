package grouppay.dylankilbride.com.onlinestore.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import grouppay.dylankilbride.com.onlinestore.Adapters.CustomerCartRVAdapter;
import grouppay.dylankilbride.com.onlinestore.Adapters.ProductsRVAdapter;
import grouppay.dylankilbride.com.onlinestore.R;
import grouppay.dylankilbride.com.onlinestore.models.Customer;
import grouppay.dylankilbride.com.onlinestore.models.PaymentReceipt;
import grouppay.dylankilbride.com.onlinestore.models.Product;
import grouppay.dylankilbride.com.onlinestore.web_service_api.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerViewCart extends AppCompatActivity {

  private RecyclerView productsListRV;
  private CustomerCartRVAdapter adapter;
  private Button paypal, debit;
  private String baseUrl = "http://10.0.2.2:8080";
  private RetrofitAPI webserviceApi;

  public List<Product> productsInCart = new ArrayList<>();
  private Customer activeCustomer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_customer_view_cart);

    activeCustomer = (Customer) getIntent().getSerializableExtra("customer");
    productsInCart = activeCustomer.getCustomersCart().getProducts();

    setUpProductsRecyclerView(productsInCart);

    debit = findViewById(R.id.debitPurchase);
    paypal = findViewById(R.id.paypalPurchase);

    debit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setUpProductsPurchaseCall("debit");
      }
    });

    paypal.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setUpProductsPurchaseCall("paypal");
      }
    });
  }

  public void setUpProductsRecyclerView(List<Product> productsInCart) {
    productsListRV = (RecyclerView) findViewById(R.id.customerCartRecyclerView);
    LinearLayoutManager productsListLayoutManager = new LinearLayoutManager(this);
    productsListRV.setLayoutManager(productsListLayoutManager);
    adapter = new CustomerCartRVAdapter(productsInCart, R.layout.cart_list_item);
    productsListRV.setAdapter(adapter);
  }


  private void setUpProductsPurchaseCall(String paymentMethod) {
    Retrofit purchaseProducts = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    webserviceApi = purchaseProducts.create(RetrofitAPI.class);
    makePurchaseProductsRequest(paymentMethod);
  }

  private void makePurchaseProductsRequest(String paymentMethod) {
    Call<PaymentReceipt> call = webserviceApi.purchaseProducts(paymentMethod, productsInCart);
    call.enqueue(new Callback<PaymentReceipt>() {
      @Override
      public void onResponse(Call<PaymentReceipt> call, Response<PaymentReceipt> response) {
        if(!response.isSuccessful()){
          Toast.makeText(getApplicationContext(), "Payment Failed!", Toast.LENGTH_LONG).show();
        } else {
            if (response.body().getSuccess().equals("success")) {
              Toast.makeText(getApplicationContext(), "Payment Success!", Toast.LENGTH_LONG).show();
              productsInCart.clear();
              finish();
            } else {
              Toast.makeText(getApplicationContext(), "Payment Failed!", Toast.LENGTH_LONG).show();
            }
        }
      }

      @Override
      public void onFailure(Call<PaymentReceipt> call, Throwable t) {
        Toast.makeText(getApplicationContext(), "Payment Failed!", Toast.LENGTH_LONG).show();
      }
    });
  }

}
