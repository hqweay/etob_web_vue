package com.nb_netmusic.dao;

import com.nb_netmusic.utils.JdbcUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoreDao {
    private JdbcUtils jdbcUtils = new JdbcUtils();
    private static final String INSERT_INTO_USER_SONG_SCORE_SQL = "insert into nb_user_song_score(username,song_id,song_score) values (?,?,?)";
    private static final String UPDATE_USER_SONG_SCORE_SQL = "UPDATE nb_user_song_score set song_score = ? where username = ? and song_id = ?";

    private static final String INSERT_INTO_NB_SONG_SCORE_SQL = "insert into nb_song_score(song_id,score,number_of_people) values(?,?,?) " + "                                                                on duplicate key update number_of_people = number_of_people + 1,score = score + ?";
    private static final String QUREY_BY_SONG_ID = "select * from nb_song_score where song_id=?";
    private static final String HAS_USER_SONG_SCORE = "SELECT * from nb_user_song_score where username = ? and song_id = ?";
  private static final String QUREY_SONG_SCORE_BY_SONG_ID = "select * from nb_user_song_score where song_id=?";


//    public boolean insertIntoUserSongScore(List<Object> params) {
//        boolean _result = false;
//        int song_id = (int) params.get(1);
//        int score = (int) params.get(2);
//        float aveScore = 0.0f;
//        jdbcUtils.getConnection();
//        try {
//            if(jdbcUtils.updateByPreparedStatement(INSERT_INTO_USER_SONG_SCORE_SQL, params)){
//                List<Object> p = new ArrayList<>();
//                p.add(song_id);
//                p.add(score);
//                p.add(1);
//                p.add(score);
//                try {
//                    _result = jdbcUtils.updateByPreparedStatement(INSERT_INTO_NB_SONG_SCORE_SQL, p);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                List<Object> secParams = new ArrayList<>();
//                secParams.add(song_id);
//                try {
//                    Map<String, Object> data = jdbcUtils.findModeResult(QUREY_BY_SONG_ID, secParams).get(0);
//                    float _score = (float) data.get("score");
//                    int amount = (int) data.get("number_of_people");
//                    aveScore = _score / amount;
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                List<Object> _p = new ArrayList<>();
//                _p.add(aveScore);
//                _p.add(song_id);
////                updateSongScore(_p);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        jdbcUtils.releaseConn();
//        return _result;
//    }


    //平均分逻辑还没写
//不能这样写
    public boolean updateSongScore(List<Object> params){
        jdbcUtils.getConnection();
        int song_id = (int) params.get(1);
        float aveScore = 0.0f;
        List<Object> secParams = new ArrayList<>();
        secParams.add(song_id);

        try {
            //获取所有评分
          //从 nb_song_score 获取
          //平均分 人数
          //  Map<String, Object> data = jdbcUtils.findModeResult(QUREY_BY_SONG_ID, secParams).get(0);
          List<Map<String, Object>> data = jdbcUtils.findModeResult(QUREY_SONG_SCORE_BY_SONG_ID, secParams);

          System.out.println("这里是");
          System.out.println(data);

          int userNum = data.size();
          int scoreSum = 0;
          for(Map<String, Object> song : data){
            scoreSum += (int)song.get("song_score");
          }
          //平均分 = 总分 / 总人数
          aveScore = scoreSum/userNum;

          //  float _score = (float) data.get("score");
         //   int amount = (int) data.get("number_of_people");
         //   aveScore = _score / amount;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //更新 每首音乐的平均分
        List<Object> _p = new ArrayList<>();
        _p.add(aveScore);
        _p.add(song_id);
        boolean result = false;
        String sql = "update nb_song set score=? where id = ?";
        try {
            result = jdbcUtils.updateByPreparedStatement(sql, _p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtils.releaseConn();
        return result;
    }

    public int getSongScoreNum(List<Object> params){
      jdbcUtils.getConnection();
      int song_id = (int) params.get(0);

      List<Object> secParams = new ArrayList<>();
      secParams.add(song_id);
      int count = 0;

      try {
        List<Map<String, Object>> data = jdbcUtils.findModeResult(QUREY_SONG_SCORE_BY_SONG_ID, secParams);
        count = data.size();
      } catch (SQLException e) {
        e.printStackTrace();
      }


      jdbcUtils.releaseConn();
      return count;
    }

    //user 是否 对 某 song 有评分 有的话更新 无的话 插入
    public boolean hasUserSongScore(List<Object> params) throws SQLException {
        jdbcUtils.getConnection();
        Boolean flag = false;
        Map<String, Object> data = jdbcUtils.findSimpleResult(HAS_USER_SONG_SCORE, params);
        if(data.size() > 0){
            flag =  true;
        }else{
            flag =  false;
        }
        jdbcUtils.releaseConn();
        return flag;
    }
    //插入
    public boolean insertUserSongScore(List<Object> params){
       return this.handleSql(INSERT_INTO_USER_SONG_SCORE_SQL, params);
    }
    //更新
    public boolean updateUserSongScore(List<Object> params){
        return this.handleSql(UPDATE_USER_SONG_SCORE_SQL, params);
    }
    //操作数据库的方法
    public boolean handleSql(String sql,List<Object> params){
        jdbcUtils.getConnection();
        Boolean flag = false;
        try {
            if(jdbcUtils.updateByPreparedStatement(sql, params)){
                flag = true;
            }else{
                flag = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jdbcUtils.releaseConn();;
        return flag;
    }
    //public boolean updateSong
}
