package com.nb_netmusic.service;

import com.nb_netmusic.bean.Song;
import com.nb_netmusic.dao.SongDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class SongService {
    private SongDao sd = new SongDao();

    public List<Song> getSongList(List<Object> params) {
        List<Song> retList = null;
        List<Map<String, Object>> data = sd.queryAllSong(params);
        retList = fitSongData(retList, data);
        return retList;
    }

    public List<Song> getSongListByName(List<Object> params) {
        List<Song> retList = null;
        List<Map<String, Object>> data = sd.querySongByName(params);
        retList = fitSongData(retList, data);
        return retList;
    }

    public List<Song> getSongListByAlbum(List<Object> params) {
        List<Song> retList = null;
        List<Map<String, Object>> data = sd.querySongByAlbum(params);
        retList = fitSongData(retList, data);
        return retList;
    }

    public List<Song> getSongListById(List<Object> params) {
        List<Song> retList = null;
        List<Map<String, Object>> data = sd.querySongById(params);
        retList = fitSongData(retList, data);
        return retList;
    }

    private List<Song> fitSongData(List<Song> retList, List<Map<String, Object>> data) {
        if (data != null) {
            retList = this.mapToList(data);
//              new ArrayList<>();
//      for (Map<String, Object> m : data) {
//        Song s = new Song();
//        s.setId((Integer) m.get("id"));
//        s.setName((String) m.get("name"));
//        s.setAlbum((String) m.get("album"));
//        s.setSinger((String) m.get("singer"));
//        s.setDuration((String) m.get("duration"));
//        s.setFrequency((Integer) m.get("frequency"));
//        s.setLink((String) m.get("link"));
//        s.setScore((Float) m.get("score"));
//        s.setSong_image((String) m.get("song_image"));
//        retList.add(s);
//      }
        }
        return retList;
    }

    public boolean insertSong(List<Object> params) {
        return sd.insertSong(params);
    }

    public List<Song> getSongListByPage(List<Object> params) {
        List<Map<String, Object>> data = sd.querySongListByPage(params);
        List<Song> songArrayList = this.mapToList(data);
        return songArrayList;
    }

    public List<Song> getScoreSongsByUser(List<Object> params) {
        List<Map<String, Object>> data = sd.getScoreSongsByUser(params);
        List<Song> songArrayList = this.mapToList(data);
        return songArrayList;
    }


    public Song getNextSong(int id, List<Object> params) {
        List<Song> l = getSongList(params);
        int nextSongIndex = -1;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getId() == id) {
                nextSongIndex = i + 1;
                if (nextSongIndex == l.size()) {
                    nextSongIndex = 0;
                }
                break;
            }
        }
        return l.get(nextSongIndex);
    }

    public int getCount() {
        List<Object> params = new ArrayList<>();
        List<Map<String, Object>> data = sd.queryAllSong(params);
        List<Song> songArrayList = this.mapToList(data);
        return songArrayList.size();
    }

    public boolean deleteSong(List<Object> params) {
        return sd.deleteSong(params);
    }

    private List<Song> mapToList(List<Map<String, Object>> data) {
        List<Song> songArrayList = new ArrayList<>();
        for (Map<String, Object> m : data) {
            Song s = new Song();
            s.setId((Integer) m.get("id"));
            s.setName((String) m.get("name"));
            s.setAlbum((String) m.get("album"));
            s.setSinger((String) m.get("singer"));
            s.setDuration((String) m.get("duration"));
            s.setFrequency((Integer) m.get("frequency"));
            s.setLink((String) m.get("link"));
            s.setScore((Float) m.get("score"));
            s.setSong_image((String) m.get("song_image"));
            songArrayList.add(s);
        }
        return songArrayList;
    }

    public boolean updateSongById(List<Object> params) {
        return sd.updateSongById(params);
    }

    public Song getFormerSong(int id, List<Object> params) {
        List<Song> l = getSongList(params);
        int formerSongIndex = -1;
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getId() == id) {
                formerSongIndex = i - 1;
                if (formerSongIndex == -1) {
                    formerSongIndex = l.size() - 1;
                }
                break;
            }
        }
        return l.get(formerSongIndex);
    }
}
