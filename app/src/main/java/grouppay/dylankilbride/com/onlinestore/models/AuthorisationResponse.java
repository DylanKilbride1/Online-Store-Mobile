package grouppay.dylankilbride.com.onlinestore.models;

public class AuthorisationResponse {

  private String roleId;
  private boolean authorisationSuccess;

  public AuthorisationResponse(String roleId, boolean authorisationSuccess) {
    this.roleId = roleId;
    this.authorisationSuccess = authorisationSuccess;
  }

  public AuthorisationResponse() {}

  public String getRoleId() {
    return roleId;
  }

  public void setRoleId(String roleId) {
    this.roleId = roleId;
  }

  public boolean isAuthorisationSuccess() {
    return authorisationSuccess;
  }

  public void setAuthorisationSuccess(boolean authorisationSuccess) {
    this.authorisationSuccess = authorisationSuccess;
  }
}
