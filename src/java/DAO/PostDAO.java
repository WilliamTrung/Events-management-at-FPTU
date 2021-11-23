/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.PostDTO;
import DTO.UserDTO;
import Utils.DBConnection;
import static java.rmi.server.LogStream.log;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SE151264
 */
public class PostDAO {

    public List<PostDTO> getListPostByPage(String search, int index, int pageSize) {
        List<PostDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "WITH tblPostPage AS (SELECT (ROW_NUMBER() over (order by createDate) ) AS RowNum,\n"
                    + "					postId, userId, title, content, video, createDate,s.statusName AS status \n"
                    + "				FROM tblPosts e, tblStatusPost s \n"
                    + "				WHERE title like ? AND e.statusId = s.statusId)\n"
                    + "SELECT postId, userId, title, content, video, createDate, status \n"
                    + "FROM tblPostPage WHERE RowNum BETWEEN ?*?-(?-1) AND ?*?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            //index*pageSize - (pageSize-1) AND index*pageSize
            stm.setInt(2, index);
            stm.setInt(3, pageSize);
            stm.setInt(4, pageSize);
            stm.setInt(5, index);
            stm.setInt(6, pageSize);
            rs = stm.executeQuery();
            while (rs.next()) {
                String postId = rs.getString("postId");
                String userId = rs.getString("userId");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String video = rs.getString("video");
                Date createDate = rs.getDate("createDate");
                String statusName = rs.getString("status");

                UserDTO user = new UserDAO().getUserById(userId);

                list.add(new PostDTO(postId, user, title, content, video, createDate, statusName));
            }
        } catch (Exception e) {
            log("Error at PostDAO - getListPostByPage: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }

    public boolean updatePost(PostDTO newPost) {
        Connection conn = null;
        PreparedStatement stm = null;
        boolean check = false;
        try {
            conn = Utils.DBConnection.getConnection();
            String sql = "UPDATE [tblPosts] SET [title] = ?, [content] = ? ,[video] = ? ,[createDate] = ? ,[statusId] = (SELECT statusId FROM tblStatusPost WHERE statusName=?) WHERE postId =? AND userId=?";
            stm = conn.prepareStatement(sql);
            String postId = newPost.getPostId();
            String userId = newPost.getUser().getUserId();
            String title = newPost.getTitle();
            String content = newPost.getContent();
            String video = newPost.getVideo();
            Date createDate = newPost.getCreateDate();
            String statusName = newPost.getStatusName();

            stm.setString(1, title);
            stm.setString(2, content);
            stm.setString(3, video);
            stm.setDate(4, createDate);
            stm.setString(5, statusName);
            stm.setString(6, postId);
            stm.setString(7, userId);

            check = stm.executeUpdate() > 0;
        } catch (Exception e) {
            log("Error at PostDAO - updatePost: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return check;
    }

    public boolean insertPost(PostDTO newPost) {
        Connection conn = null;
        PreparedStatement stm = null;
        boolean check = false;
        try {
            conn = Utils.DBConnection.getConnection();
            String sql = "INSERT INTO [tblPosts] ([userId], [title], [content], [video], [createDate], [statusId])\n"
                    + "                    VALUES (?,?,?,?,?,(SELECT statusId FROM tblStatusPost WHERE statusName = ?))";
            stm = conn.prepareStatement(sql);
            String userId = newPost.getUser().getUserId();
            String title = newPost.getTitle();
            String content = newPost.getContent();
            String video = newPost.getVideo();
            Date createDate = newPost.getCreateDate();
            String statusName = newPost.getStatusName();

            stm.setString(1, userId);
            stm.setString(2, title);
            stm.setString(3, content);
            stm.setString(4, video);
            stm.setDate(5, createDate);
            stm.setString(6, statusName);

            check = stm.executeUpdate() > 0;
        } catch (Exception e) {
            log("Error at PostDAO - insertPost: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return check;
    }

    public PostDTO getPostById(String postId) {
        PostDTO post = null;
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT userId, title, content, video, createDate, s.statusName\n"
                    + "                    FROM tblPosts e, tblStatusPost s\n"
                    + "                    WHERE e.postId=? AND e.statusId = s.statusId";
            stm = conn.prepareStatement(sql);
            stm.setString(1, postId);
            rs = stm.executeQuery();
            if (rs.next()) {
                String userId = rs.getString("userId");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String video = rs.getString("video");
                Date createDate = rs.getDate("createDate");
                String statusName = rs.getString("statusName");

                UserDTO user = new UserDAO().getUserById(userId);

                post = new PostDTO(postId, user, title, content, video, createDate, statusName);
            }
        } catch (Exception e) {
            log("Error at PostDAO - getPostById: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return post;
    }

    public int countPost() {
        int rs = 0;
        PostDAO dao = new PostDAO();
        List<PostDTO> list = dao.getListPost("");
        rs = list.size();
        return rs;
    }

    private List<PostDTO> getListPost(String search) {
        List<PostDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT postId, userId, title, content, video, createDate, s.statusName\n"
                    + "                    FROM tblPosts e, tblStatusPost s\n"
                    + "                    WHERE title LIKE ? AND e.statusId = s.statusId";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                String postId = rs.getString("postId");
                String userId = rs.getString("userId");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String video = rs.getString("video");
                Date createDate = rs.getDate("createDate");
                String statusName = rs.getString("statusName");

                UserDTO user = new UserDAO().getUserById(userId);

                list.add(new PostDTO(postId, user, title, content, video, createDate, statusName));
            }
        } catch (Exception e) {
            log("Error at PostDAO - getListPostByPage: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }

    public boolean deletePost(String postId) {
        Connection conn = null;
        PreparedStatement stm = null;
        boolean check = false;
        try {
            conn = Utils.DBConnection.getConnection();
            String sql = "DELETE tblPosts WHERE postId=?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, postId);
            
            check = stm.executeUpdate() > 0;
        } catch (Exception e) {
            log("Error at PostDAO - insertPost: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, null);
        }
        return check;
    }

    public List<PostDTO> getListPostByPageByOwner(UserDTO user, String search, int index, int pageSize) {
        List<PostDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "WITH tblPostPage AS (SELECT (ROW_NUMBER() over (order by createDate) ) AS RowNum,\n"
                    + "					postId, userId, title, content, video, createDate,s.statusName AS status \n"
                    + "				FROM tblPosts e, tblStatusPost s \n"
                    + "				WHERE title like ? AND e.statusId = s.statusId AND e.userId=?)\n"
                    + "SELECT postId, userId, title, content, video, createDate, status \n"
                    + "FROM tblPostPage WHERE RowNum BETWEEN ?*?-(?-1) AND ?*?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, user.getUserId());
            //index*pageSize - (pageSize-1) AND index*pageSize
            stm.setInt(3, index);
            stm.setInt(4, pageSize);
            stm.setInt(5, pageSize);
            stm.setInt(6, index);
            stm.setInt(7, pageSize);
            rs = stm.executeQuery();
            while (rs.next()) {
                String postId = rs.getString("postId");
                String userId = rs.getString("userId");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String video = rs.getString("video");
                Date createDate = rs.getDate("createDate");
                String statusName = rs.getString("status");


                list.add(new PostDTO(postId, user, title, content, video, createDate, statusName));
            }
        } catch (Exception e) {
            log("Error at PostDAO - getListPostByPage: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }
    private List<PostDTO> getListPostOwned(String search, UserDTO user) {
        List<PostDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT postId, userId, title, content, video, createDate, s.statusName\n"
                    + "                    FROM tblPosts e, tblStatusPost s\n"
                    + "                    WHERE title LIKE ? AND e.statusId = s.statusId AND userId=?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + search + "%");
            stm.setString(2, user.getUserId());
            rs = stm.executeQuery();
            while (rs.next()) {
                String postId = rs.getString("postId");
                String userId = rs.getString("userId");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String video = rs.getString("video");
                Date createDate = rs.getDate("createDate");
                String statusName = rs.getString("statusName");

                list.add(new PostDTO(postId, user, title, content, video, createDate, statusName));
            }
        } catch (Exception e) {
            log("Error at PostDAO - getListPostByPage: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }
    public int countListOwnedEvent(UserDTO user) {
        int rs = 0;
        PostDAO dao = new PostDAO();
        List<PostDTO> list = dao.getListPostOwned("", user);
        rs = list.size();
        return rs;
    }
}
