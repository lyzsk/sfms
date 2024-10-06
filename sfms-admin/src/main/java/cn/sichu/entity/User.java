package cn.sichu.entity;

import cn.sichu.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author sichu huang
 * @date 2024/10/06
 **/
@TableName("t_user")
public class User extends BaseEntity {
    private String username;
    private String password;
    private String fullname;
    private String jobnumber;
    private String gender;
    private String email;
    private String phonenumber;
    private String avatar;
    private String status;
    private String loginIp;
    private String loginDate;

    public User(String username, String password, String fullname,
        String jobnumber, String gender, String email, String phonenumber,
        String avatar, String status, String loginIp, String loginDate) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.jobnumber = jobnumber;
        this.gender = gender;
        this.email = email;
        this.phonenumber = phonenumber;
        this.avatar = avatar;
        this.status = status;
        this.loginIp = loginIp;
        this.loginDate = loginDate;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" + "username='" + username + '\'' + ", password='"
            + password + '\'' + ", fullname='" + fullname + '\''
            + ", jobnumber='" + jobnumber + '\'' + ", gender='" + gender + '\''
            + ", email='" + email + '\'' + ", phonenumber='" + phonenumber
            + '\'' + ", avatar='" + avatar + '\'' + ", status='" + status + '\''
            + ", loginIp='" + loginIp + '\'' + ", loginDate='" + loginDate
            + '\'' + '}';
    }

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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getJobnumber() {
        return jobnumber;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

}
