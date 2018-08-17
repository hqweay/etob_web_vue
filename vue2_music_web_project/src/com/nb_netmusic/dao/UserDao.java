package com.nb_netmusic.dao;

import com.nb_netmusic.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    private static final String DELETE_USER_BY_ID_SQL = "delete from nb_user where username = ?";
    private JdbcUtils ju = new JdbcUtils();
    //    private static final String SELECT_USRE_USERNAME = "select * from nb_user where username"
    private static final String SELECT_ALL = "select * from nb_user";
    private static final String SELECT_BY_USERID = "select * from nb_user where username=?";
    private static final String SELECT_ID_PWD = "select * from nb_user where username=? and password=?";
    private static final String SELECT_Email_PWD = "select * from nb_user where email=? and password=?";
    private static final String INSERT_USER = "insert into nb_user(username,password,type,email,sex,profile_img) values(?,?,?,?,?,?)";
    private static final String UPDATE_USER_INFO = "update nb_user set username=?,password=?,email=?,sex=?,profile_img=? where username=?";
    private static final String SELECT_COUNT_USER = "select count(*) from nb_user";
    private static final String SELECT_BY_PAGE = "select * from nb_user limit ?,?";


    public List<Map<String, Object>> queryValidLoginIdPassword(List<Object> params) {
        return getMaps(params, ju, SELECT_ID_PWD);
    }

    public List<Map<String, Object>> queryUserListByPage(List<Object> params) {
        return getMaps(params, ju, SELECT_BY_PAGE);
    }

    public boolean insertUser(List<Object> params) {
        boolean result = false;
        ju.getConnection();
        try {
            result = ju.updateByPreparedStatement(INSERT_USER, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Map<String, Object>> queryValidLoginEmailPassword(List<Object> params) {
        return getMaps(params, ju, SELECT_Email_PWD);

    }

    public List<Map<String, Object>> queryAllUser(List<Object> params) {
        return getMaps(params, ju, SELECT_ALL);
    }

    public List<Map<String, Object>> queryUserByID(List<Object> params) {
        return getMaps(params, ju, SELECT_BY_USERID);
    }

    private static List<Map<String, Object>> getMaps(List<Object> params, JdbcUtils ju, String SQL) {
        List<Map<String, Object>> data = null;
        ju.getConnection();
        try {
            data = ju.findModeResult(SQL, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ju.releaseConn();
        return data;
    }


    public boolean updateUserInfo(List<Object> params) {
        boolean result = false;
        ju.getConnection();
        try {
            result = ju.updateByPreparedStatement(UPDATE_USER_INFO, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ju.releaseConn();
        return result;
    }

    public boolean deleteUserByUsername(List<Object> params) {
        boolean result = false;
        ju.getConnection();
        try {
            result = ju.updateByPreparedStatement(DELETE_USER_BY_ID_SQL, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ju.releaseConn();
        return result;

    }
}
