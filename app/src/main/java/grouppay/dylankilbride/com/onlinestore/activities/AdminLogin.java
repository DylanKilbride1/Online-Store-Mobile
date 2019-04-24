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

public class AdminLogin extends AppCompatActivity {

  private RetrofitAPI webserviceApi;
  private Button customerLogin, adminLogin;
  private EditText adminEmail,adminPassword;
  private String baseUrl = "http://10.0.2.2:8080";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_login);

    customerLogin = findViewById(R.id.customerLogin);
    adminLogin = findViewById(R.id.aLogin);
    adminEmail = findViewById(R.id.adminEmail);
    adminPassword = findViewById(R.id.adminPassword);

    customerLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent customerLogin = new Intent(AdminLogin.this, CustomerLogin.class);
        startActivity(customerLogin);
      }
    });

    adminLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setUpAdminLoginCall();
      }
    });
  }

  private void setUpAdminLoginCall() {
    Retrofit administratorLogin = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    webserviceApi = administratorLogin.create(RetrofitAPI.class);
    makeAdminLoginRequest();
  }

  private void makeAdminLoginRequest() {
    Call<AuthorisationResponse> call = webserviceApi.adminLogin(new LoginDetails(adminEmail.getText().toString(), adminPassword.getText().toString()));
    call.enqueue(new Callback<AuthorisationResponse>() {
      @Override
      public void onResponse(Call<AuthorisationResponse> call, Response<AuthorisationResponse> response) {
        if(!response.isSuccessful()){
          Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_LONG).show();
        } else {
          if((response.body().getRoleId() != 0) && (response.body().isAuthorisationSuccess())) {
            Intent adminHome = new Intent(AdminLogin.this, AdminHomePage.class);
            startActivity(adminHome);
          } else {
            View context = findViewById(R.id.adminLoginRL);
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
