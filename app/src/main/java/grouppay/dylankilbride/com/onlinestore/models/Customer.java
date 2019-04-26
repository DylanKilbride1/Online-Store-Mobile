package grouppay.dylankilbride.com.onlinestore.models;

import java.io.Serializable;

public class Customer implements Serializable {

  private long customerId;
  private String username;
  private String email;
  private String password;
  private Cart customersCart = new Cart();

  public Customer(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public Customer(String name, String email) {
    this.username = name;
    this.email = email;
  }

  public Customer() {}

  public long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(long customerId) {
    this.customerId = customerId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Cart getCustomersCart() {
    return customersCart;
  }

  public void setCustomersCart(Cart customersCart) {
    this.customersCart = customersCart;
  }
}
