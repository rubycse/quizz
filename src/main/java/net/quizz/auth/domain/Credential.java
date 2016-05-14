package net.quizz.auth.domain;

/**
 * @author lutfun
 * @since 5/7/16
 */
public class Credential {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
