package com.bs.android.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class SearchHistoryModel extends BaseVO {

    @Id(autoincrement = true)
    private Long id;

    private String searchName;

    private String dateTime;


    public SearchHistoryModel() {
    }

    @Generated(hash = 1453210560)
    public SearchHistoryModel(Long id, String searchName, String dateTime) {
        this.id = id;
        this.searchName = searchName;
        this.dateTime = dateTime;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "MenuSearchHistoryModel{" +
                "id=" + id +
                ", searchName='" + searchName + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
