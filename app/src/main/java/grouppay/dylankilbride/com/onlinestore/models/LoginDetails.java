package grouppay.dylankilbride.com.onlinestore.models;

public class LoginDetails {

  private String username;
  private String email;
  private String password;

  public LoginDetails(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public LoginDetails(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public LoginDetails() {}

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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}