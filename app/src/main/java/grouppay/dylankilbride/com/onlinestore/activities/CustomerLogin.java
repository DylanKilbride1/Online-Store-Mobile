package grouppay.dylankilbride.com.onlinestore.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import grouppay.dylankilbride.com.onlinestore.R;
import grouppay.dylankilbride.com.onlinestore.models.AuthorisationResponse;
import grouppay.dylankilbride.com.onlinestore.models.LoginDetails;
import grouppay.dylankilbride.com.onlinestore.web_service_api.RetrofitAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomerLogin extends AppCompatActivity {

  private RetrofitAPI webserviceApi;
  private Button adminLogin, customerLogin, customerRegistration;
  private EditText customerEmail, customerPassword;
  private String baseUrl = "http://10.0.2.2:8080";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_customer_login);

    customerLogin = findViewById(R.id.cLogin);
    adminLogin = findViewById(R.id.adminLogin);
    customerRegistration = findViewById(R.id.cRegistration);
    customerEmail = findViewById(R.id.customerEmail);
    customerPassword = findViewById(R.id.customerPassword);

    adminLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent adminLogin = new Intent(CustomerLogin.this, AdminLogin.class);
        startActivity(adminLogin);
      }
    });

    customerLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setUpCustomerLoginCall();
      }
    });

    customerRegistration.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent customerReg = new Intent(CustomerLogin.this, CustomerRegistration.class);
        startActivity(customerReg);
      }
    });
  }


  private void setUpCustomerLoginCall() {
    Retrofit customerLogin = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    webserviceApi = customerLogin.create(RetrofitAPI.class);
    makeCustomerLoginRequest();
  }

  private void makeCustomerLoginRequest() {
    Call<AuthorisationResponse> call = webserviceApi.customerLogin(new LoginDetails(customerEmail.getText().toString(), customerPassword.getText().toString()));
    call.enqueue(new Callback<AuthorisationResponse>() {
      @Override
      public void onResponse(Call<AuthorisationResponse> call, Response<AuthorisationResponse> response) {
        if(!response.isSuccessful()){
          Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
        } else {
          if((response.body().getRoleId() != "0") && (response.body().isAuthorisationSuccess())) {
            Intent customerHome = new Intent(CustomerLogin.this, CustomerHomePage.class);
            customerHome.putExtra("customer", response.body().getRoleId());
            startActivity(customerHome);
          } else {
            View context = findViewById(R.id.customerLoginRL);
            Snackbar.make(context, "Incorrect login details", Snackbar.LENGTH_SHORT)
                .show();
          }
        }
      }

      @Override
      public void onFailure(Call<AuthorisationResponse> call, Throwable t) {
        Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
      }
    });
  }
}
