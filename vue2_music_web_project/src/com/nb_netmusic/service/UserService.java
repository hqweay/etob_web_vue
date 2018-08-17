package com.nb_netmusic.service;

import com.nb_netmusic.bean.User;
import com.nb_netmusic.dao.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nb_netmusic.bean.User;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class UserService {
    private UserDao ud = new UserDao();

    public List<User> validateAccount(List<Object> params) {
        List<User> l = new ArrayList();
        List<Map<String, Object>> data = ud.queryValidLoginIdPassword(params);
        if (data.isEmpty()) {
            data = ud.queryValidLoginEmailPassword(params);
        }
        for (Map<String, Object> m : data) {
            User u = new User();
            u.setUsername((String) m.get("username"));
            u.setEmail((String) m.get("email"));
            u.setPassword((String) m.get("password"));
            u.setProfileImg((String) m.get("profileImg"));
            u.setSex((int) m.get("sex"));
            u.setType((int) m.get("type"));
            l.add(u);
        }
        return l;
    }

    public Boolean hasUserName(List<Object> params) {
        List<Map<String, Object>> users = ud.queryUserByID(params);
//        System.out.println(users.toString());
        return !users.isEmpty();
    }

    public List<User> getUserListByPage(List<Object> params) {
        List<Map<String, Object>> data = ud.queryUserListByPage(params);
        List<User> UserArrayList = this.mapToList(data);
        return UserArrayList;
    }

    private List<User> mapToList(List<Map<String, Object>> data) {
        List<User> lu = new ArrayList<>();
        for (Map<String, Object> item : data) {
            User temp = new User();
            temp.setUsername((String) item.get("username"));
            temp.setEmail((String) item.get("email"));
            temp.setSex((Integer) item.get("sex"));
            temp.setType((Integer) item.get("type"));
            temp.setProfileImg((String) item.get("profile_img"));
            temp.setPassword((String) item.get("password"));
            lu.add(temp);
        }
        return lu;
    }

    public int getCount() {
        List<Map<String, Object>> data = ud.queryAllUser(null);
        List<User> UserArrayList = this.mapToList(data);
        return UserArrayList.size();
    }

    public List<User> getAllUser(List<Object> params) {
        List<User> retList = new ArrayList<>();
        List<Map<String, Object>> data;
        return retList;
    }

    public List<User> getUserByUsername(List<Object> params) {
        List<User> retList;
        List<Map<String, Object>> data = ud.queryUserByID(params);
        retList = mapToList(data);
        return retList;

    }

    public boolean insertUser(List<Object> params) {
        return ud.insertUser(params);
    }

    public boolean updateUserInfo(List<Object> params) {
        return ud.updateUserInfo(params);
    }

    public boolean deleteUserByUsername(List<Object> params) {
        return ud.deleteUserByUsername(params);
    }
}
