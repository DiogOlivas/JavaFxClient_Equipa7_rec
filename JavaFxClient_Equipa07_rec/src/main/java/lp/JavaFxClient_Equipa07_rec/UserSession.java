package lp.JavaFxClient_Equipa07_rec;

public class UserSession {
    private static UserSession instance;
    private String currentUser;
    private String sessionCookie;
    private Long userId;

    private UserSession() {}

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String user) {
        this.currentUser = user;
    }
    
    public String getSessionCookie() {
        return sessionCookie;
    }

    public void setSessionCookie(String cookie) {
        this.sessionCookie = cookie;
    }
    
    public Long getCurrentUserId() {
        return userId;
    }

    public void setCurrentUserId(Long userId) {
        this.userId = userId;
    }
    
    public void clear() {
    	currentUser = null;
    }
}
