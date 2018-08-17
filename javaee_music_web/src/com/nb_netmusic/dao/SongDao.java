package com.nb_netmusic.dao;

import com.nb_netmusic.utils.JdbcUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SongDao {
    private static final String UPDATE_SONG_SQL = "update nb_song set id=?,name=?,singer=?,album=?,frequency=?,duration=?,link=?,score=?,song_image=? where id=?";
    private JdbcUtils jdbcUtils = new JdbcUtils();
    private static final String SELECT_BY_PAGE = "select * from nb_song limit ?, ?";
    private static final String INSERT_SQL = "insert into nb_song(name,singer,album,frequency,duration,link,score) value(?,?,?,?,?,?,?)";
    private static final String SELECT_ALL = "select * from nb_song order by id asc";
    private static final String SELECT_COUNT = "select count(*) from nb_song";
    private static final String SELECT_BY_NAME_SQL = "select * from nb_song where name LIKE \"\"?\"%\" or singer LIKE \"\"?\"%\" or album LIKE \"\"?\"%\"";
    private static final String SELECT_BY_ALBUM_SQL = "select * from nb_song where album LIKE \"\"?\"%\"";
    private static String DELETE_SQL;
    private static final String SELECT_SINGLE_SONG = "select * from nb_song where id = ?";
    private static final String SELECT_SCORE_SONGS_BY_USER = "select id,name,singer,album,frequency,duration,link,song_image,score FROM nb_user_song_score as A LEFT JOIN nb_song as B on A.song_id = B.id where username = ?";

    public List<Map<String, Object>> querySongById(List<Object> params) {
        return getMaps(params, jdbcUtils, SELECT_SINGLE_SONG);
    }

    public List<Map<String, Object>> queryAllSong(List<Object> params) {
        return getMaps(params, jdbcUtils, SELECT_ALL);
    }

    public List<Map<String, Object>> querySongByName(List<Object> params) {
        return getMaps(params, jdbcUtils, SELECT_BY_NAME_SQL);
    }

    public List<Map<String, Object>> querySongByAlbum(List<Object> params) {
        return getMaps(params, jdbcUtils, SELECT_BY_ALBUM_SQL);
    }

    public List<Map<String, Object>> querySongListByPage(List<Object> params) {
        return getMaps(params, jdbcUtils, SELECT_BY_PAGE);
    }

    public List<Map<String,Object>> getScoreSongsByUser(List<Object> params){
        return getMaps(params, jdbcUtils, SELECT_SCORE_SONGS_BY_USER);
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

    public boolean insertSong(List<Object> params) {
        boolean result = false;
        jdbcUtils.getConnection();
        try {
            //出错
            result = jdbcUtils.updateByPreparedStatement(INSERT_SQL, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtils.releaseConn();
        return result;
    }

    public boolean deleteSong(List<Object> params) {
        int length = params.size();
        DELETE_SQL = "delete from nb_song where id in (";
        StringBuilder paramSQL = new StringBuilder();
        for (int i = 0; i < length; i++) {
            paramSQL.append("?,");
        }
        paramSQL.deleteCharAt(paramSQL.length() - 1);
        paramSQL.append(")");
        DELETE_SQL += paramSQL.toString();
        System.out.println(DELETE_SQL);
        boolean result = false;
        jdbcUtils.getConnection();
        try {
            result = jdbcUtils.updateByPreparedStatement(DELETE_SQL, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtils.releaseConn();
        return result;
    }

    public boolean updateSongById(List<Object> params) {
        boolean result = false;
        jdbcUtils.getConnection();
        try {
            result = jdbcUtils.updateByPreparedStatement(UPDATE_SONG_SQL, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtils.releaseConn();
        return result;
    }
}
