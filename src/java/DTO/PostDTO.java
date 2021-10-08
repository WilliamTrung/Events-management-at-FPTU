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
public class PostDTO {

    private String postId;
    private UserDTO user;
    private String title;
    private String content;
    private String video;
    private Date createDate;
    private String statusName;

    public PostDTO(String postId, UserDTO user, String title, String content, String video, Date createDate, String statusName) {
        this.postId = postId;
        this.user = user;
        this.title = title;
        this.content = content;
        this.video = video;
        this.createDate = createDate;
        this.statusName = statusName;
    }

    public PostDTO() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    
}
