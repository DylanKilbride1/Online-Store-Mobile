package grouppay.dylankilbride.com.onlinestore.models;

public class AuthorisationResponse {

  private long roleId;
  private boolean authorisationSuccess;

  public AuthorisationResponse(long roleId, boolean authorisationSuccess) {
    this.roleId = roleId;
    this.authorisationSuccess = authorisationSuccess;
  }

  public AuthorisationResponse() {}

  public long getRoleId() {
    return roleId;
  }

  public void setRoleId(long roleId) {
    this.roleId = roleId;
  }

  public boolean isAuthorisationSuccess() {
    return authorisationSuccess;
  }

  public void setAuthorisationSuccess(boolean authorisationSuccess) {
    this.authorisationSuccess = authorisationSuccess;
  }
}
