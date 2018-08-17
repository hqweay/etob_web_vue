package com.nb_netmusic.service;

import com.nb_netmusic.dao.ScoreDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreService {
    private ScoreDao sd = new ScoreDao();

//    public boolean insertIntoUserSongScore(List<Object> params) {
//        //hasUserScore
//        //如果有 updata
//        //无 insert
//        return sd.insertIntoUserSongScore(params);
//    }

    public boolean addUserSongScore(List<Object> params) throws SQLException {
        //username song_id score
      //user and song_id
      boolean flag = false;
      List<Object> hasUserSongScore = new ArrayList<>();
      hasUserSongScore.add(params.get(0));
      hasUserSongScore.add(params.get(1));

      List<Object> updateUserSongScore = new ArrayList<>();
      updateUserSongScore.add(params.get(2));
      updateUserSongScore.add(params.get(0));
      updateUserSongScore.add(params.get(1));

      if(sd.hasUserSongScore(hasUserSongScore)){
        if(sd.updateUserSongScore(updateUserSongScore)){
          flag = true;
        }else{
          flag = false;
        }
      }else{
        if(sd.insertUserSongScore(params)){
          flag = true;
        }else{
          flag = false;
        }
      }
      //更新分数
      if(sd.updateSongScore(params)){
        flag = true;
      }else{
        flag = false;
      }
      return flag;
    }

    public int getSongScoreNum(List<Object> params){
      return sd.getSongScoreNum(params);
    }

}
