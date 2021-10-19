/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.CommentDTO;
import DTO.UserDTO;
import Utils.DBConnection;
import static java.rmi.server.LogStream.log;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author WilliamTrung
 */
public class CommentDAO {

    public List<CommentDTO> getListEventComment(int newsId) throws SQLException {
        List<CommentDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT userId,commentDatetime, content FROM tblComments WHERE eventId=?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, newsId);
            rs = stm.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("userId");
                String content = rs.getString("content");
                Date commentDatetime = rs.getDate("commentDatetime");

                UserDTO user = new UserDAO().getUserById(userId);

                list.add(new CommentDTO(content, user, commentDatetime));
            }
        } catch (Exception e) {
            log("Error at EventDAO - getListEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }

    public List<CommentDTO> getShortListEventComment(int newsId, int index, int pageSize) throws SQLException {
        List<CommentDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "WITH tblCommentPage AS (SELECT (ROW_NUMBER() over (order by commentDatetime desc) ) AS RowNum,\n"
                    + "                    				content, commentDatetime, userId\n"
                    + "                    			FROM tblComments\n"
                    + "                   				WHERE eventId=?)\n"
                    + "                    SELECT content, commentDatetime, userId\n"
                    + "                    FROM tblCommentPage WHERE RowNum BETWEEN ?*?-(?-1) AND ?*?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, newsId);
            stm.setInt(2, index);
            stm.setInt(3, pageSize);
            stm.setInt(4, pageSize);
            stm.setInt(5, index);
            stm.setInt(6, pageSize);
            rs = stm.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("userId");
                String content = rs.getString("content");
                Date commentDatetime = rs.getDate("commentDatetime");

                UserDTO user = new UserDAO().getUserById(userId);

                list.add(new CommentDTO(content, user, commentDatetime));
            }
        } catch (Exception e) {
            log("Error at EventDAO - getListEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }

    public int countEventComment(int newsId) throws SQLException {
        List<CommentDTO> list = new CommentDAO().getListEventComment(newsId);
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public boolean insertEventComment(CommentDTO cmt, int eventId) {
        Connection conn = null;
        PreparedStatement stm = null;
        boolean flag = false;
        try {
            conn = Utils.DBConnection.getConnection();
            String sql = "INSERT INTO [tblComments] ([userId],[eventId],[commentDatetime],[content]) VALUES (?,?,?,?)";
            stm = conn.prepareStatement(sql);
            String content=cmt.getContent();
            String userId=cmt.getUser().getUserId();
            Date cmtDatetime=cmt.getCmtDatetime();
            stm.setString(1, userId);
            stm.setInt(2, eventId);
            stm.setDate(3, cmtDatetime);
            stm.setString(4, content);
            flag = stm.executeUpdate() > 0;
        } catch (Exception e) {
            log("Error at EventDAO - createEvent: " + e.toString());
        } finally {
            Utils.DBConnection.closeQueryConnection(conn, stm, null);
        }
        return flag;
    }
    public List<CommentDTO> getListPostComment(String postId) throws SQLException {
        List<CommentDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT userId,commentDatetime, content FROM tblComments WHERE postId=?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, postId);
            rs = stm.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("userId");
                String content = rs.getString("content");
                Date commentDatetime = rs.getDate("commentDatetime");

                UserDTO user = new UserDAO().getUserById(userId);

                list.add(new CommentDTO(content, user, commentDatetime));
            }
        } catch (Exception e) {
            log("Error at EventDAO - getListEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }

    public List<CommentDTO> getShortListPostComment(String postId, int index, int pageSize) throws SQLException {
        List<CommentDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            String sql = "WITH tblCommentPage AS (SELECT (ROW_NUMBER() over (order by commentDatetime desc) ) AS RowNum,\n"
                    + "                    				content, commentDatetime, userId\n"
                    + "                    			FROM tblComments\n"
                    + "                   				WHERE postId=?)\n"
                    + "                    SELECT content, commentDatetime, userId\n"
                    + "                    FROM tblCommentPage WHERE RowNum BETWEEN ?*?-(?-1) AND ?*?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, postId);
            stm.setInt(2, index);
            stm.setInt(3, pageSize);
            stm.setInt(4, pageSize);
            stm.setInt(5, index);
            stm.setInt(6, pageSize);
            rs = stm.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("userId");
                String content = rs.getString("content");
                Date commentDatetime = rs.getDate("commentDatetime");

                UserDTO user = new UserDAO().getUserById(userId);

                list.add(new CommentDTO(content, user, commentDatetime));
            }
        } catch (Exception e) {
            log("Error at EventDAO - getListEvent: " + e.toString());
        } finally {
            DBConnection.closeQueryConnection(conn, stm, rs);
        }
        return list;
    }

    public int countPostComment(String postId) throws SQLException {
        List<CommentDTO> list = new CommentDAO().getListPostComment(postId);
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public boolean insertPostComment(CommentDTO cmt, String postId) {
        Connection conn = null;
        PreparedStatement stm = null;
        boolean flag = false;
        try {
            conn = Utils.DBConnection.getConnection();
            String sql = "INSERT INTO [tblComments] ([userId],[postId],[commentDatetime],[content]) VALUES (?,?,?,?)";
            stm = conn.prepareStatement(sql);
            String content=cmt.getContent();
            String userId=cmt.getUser().getUserId();
            Date cmtDatetime=cmt.getCmtDatetime();
            stm.setString(1, userId);
            stm.setString(2, postId);
            stm.setDate(3, cmtDatetime);
            stm.setString(4, content);
            flag = stm.executeUpdate() > 0;
        } catch (Exception e) {
            log("Error at EventDAO - createEvent: " + e.toString());
        } finally {
            Utils.DBConnection.closeQueryConnection(conn, stm, null);
        }
        return flag;
    }

    public static void main(String[] args) {
        CommentDAO dao= new CommentDAO();
        try {
            List<CommentDTO> list= dao.getShortListEventComment(1,1,10);
            for (CommentDTO o : list) {
                System.out.println(o.toString());
            }
        } catch (SQLException ex) {
           
        }  
    }
}
