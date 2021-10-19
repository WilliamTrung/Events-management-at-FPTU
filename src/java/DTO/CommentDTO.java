/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.sql.Date;

/**
 *
 * @author SE151264
 */
public class CommentDTO {
    private String content;
    private UserDTO user;
    private Date cmtDatetime;

    @Override
    public String toString() {
        return "CommentDTO{" + "content=" + content + ", user=" + user + ", cmtDatetime=" + cmtDatetime + '}';
    }

    public CommentDTO() {
    }

    public CommentDTO(String comment, UserDTO user, Date cmtDatetime) {
        this.content = comment;
        this.user = user;
        this.cmtDatetime = cmtDatetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Date getCmtDatetime() {
        return cmtDatetime;
    }

    public void setCmtDatetime(Date cmtDatetime) {
        this.cmtDatetime = cmtDatetime;
    }
}
