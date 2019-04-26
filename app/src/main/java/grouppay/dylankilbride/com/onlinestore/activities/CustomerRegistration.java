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

public class CustomerRegistration extends AppCompatActivity {

  private Button customerRegistration, customerLogin;
  private EditText username, email, password;
  private String baseUrl = "http://10.0.2.2:8080";
  private RetrofitAPI webserviceApi;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_customer_registration);

    customerRegistration = (Button) findViewById(R.id.cRegistration);
    customerLogin = (Button) findViewById(R.id.custLogin);
    username = (EditText) findViewById(R.id.customerRegUsername);
    email = (EditText) findViewById(R.id.customerRegEmail);
    password = (EditText) findViewById(R.id.customerRegPassword);

    customerLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent customerLogin = new Intent(CustomerRegistration.this, CustomerLogin.class);
        startActivity(customerLogin);
      }
    });

    customerRegistration.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setUpCustomerRegistrationCall();
      }
    });
  }

  private void setUpCustomerRegistrationCall() {
    Retrofit customerRegistration = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    webserviceApi = customerRegistration.create(RetrofitAPI.class);
    makeCustomerRegistrationRequest();
  }

  private void makeCustomerRegistrationRequest() {
    Call<AuthorisationResponse> call = webserviceApi.customerRegistration(new LoginDetails(username.getText().toString(),
        email.getText().toString(),
        password.getText().toString()));
    call.enqueue(new Callback<AuthorisationResponse>() {
      @Override
      public void onResponse(Call<AuthorisationResponse> call, Response<AuthorisationResponse> response) {
        if(!response.isSuccessful()){
          Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
        } else {
          if((response.body().getRoleId() != "0") && (response.body().isAuthorisationSuccess())) {
            Intent customerHome = new Intent(CustomerRegistration.this, CustomerHomePage.class);
            customerHome.putExtra("customer", response.body().getRoleId());
            startActivity(customerHome);
          } else {
            View context = findViewById(R.id.customerLoginRL);
            Snackbar.make(context, "You entered something wrong", Snackbar.LENGTH_SHORT)
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
