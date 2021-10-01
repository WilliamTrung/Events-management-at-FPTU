/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author WilliamTrung
 */

// COMMENT 01/10/2021
public class UserDTO {
    private String id;
    private String email;
    private String username;
    private String status;
    private String role;
    private boolean verified_email;
    private String hd;
    private String picture;
    public UserDTO() {
    }

    public UserDTO(String id, String email, String username, String status, String role, boolean verified_email, String hd, String picture) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.status = status;
        this.role = role;
        this.verified_email = verified_email;
        this.hd = hd;
        this.picture = picture;
    }

    public String getUserId() {
        return id;
    }

    public void setUserId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isVerified_email() {
        return verified_email;
    }

    public void setVerified_email(boolean verified_email) {
        this.verified_email = verified_email;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

}
