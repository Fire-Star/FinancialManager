package cn.ejie.po;

/**
 * Created by Administrator on 2017/8/9.
 */
public class User {
    /**
     * 用户名和密码
     */
    private String username;
    private String password;
    private String city;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    public User() {
    }

    public User(String username, String password, String city) {
        this.username = username;
        this.password = password;
        this.city = city;
    }
}
