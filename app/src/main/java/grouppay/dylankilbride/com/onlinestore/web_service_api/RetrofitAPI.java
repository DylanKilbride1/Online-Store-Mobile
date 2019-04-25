package grouppay.dylankilbride.com.onlinestore.web_service_api;

import java.util.List;

import grouppay.dylankilbride.com.onlinestore.models.AuthorisationResponse;
import grouppay.dylankilbride.com.onlinestore.models.Customer;
import grouppay.dylankilbride.com.onlinestore.models.LoginDetails;
import grouppay.dylankilbride.com.onlinestore.models.Product;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RetrofitAPI {

  @POST("/authorisation/customer/login")
  Call<AuthorisationResponse> customerLogin(@Body LoginDetails loginDetails);

  @POST("/authorisation/customer/register")
  Call<AuthorisationResponse> customerRegistration(@Body LoginDetails loginDetails);

  @POST("/authorisation/admin/login")
  Call<AuthorisationResponse> adminLogin(@Body LoginDetails loginDetails);

  @GET("/products/getallproducts/{sortType}")
  Call<List<Product>> getAllProducts(@Path("sortType") String sortType);

  @PUT("/products/updateproductstock")
  Call<List<Product>> updateProductStockLevels(@Body List<Product> updatedProducts);

  @GET("/customers/getallcustomers")
  Call<List<Customer>> getAllCustomers();
}
