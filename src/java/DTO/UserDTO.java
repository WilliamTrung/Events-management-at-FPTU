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
public class UserDTO {
    private String id;
    private String email;
    private String username;
    private String statusId;
    private String roleId;
    private boolean verified_email;
    private String hd;
    private String picture;
    public UserDTO() {
    }

    public UserDTO(String id, String email, String username, String statusId, String roleId, String hd, String picture) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.statusId = statusId;
        this.roleId = roleId;
        this.hd = hd;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
    
    public String getUsername() {
        return username;
    }

    public String getStatusId() {
        return statusId;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getHd() {
        return hd;
    }

    public String getPicture() {
        return picture;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVerified_email(boolean verified_email) {
        this.verified_email = verified_email;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isVerified_email() {
        return verified_email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    
}
