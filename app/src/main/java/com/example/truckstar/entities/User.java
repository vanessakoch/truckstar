package com.example.truckstar.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "nickname")
    private String nickname;
    @ColumnInfo(name = "password")
    private String password;

    public User(){}

    public User(long id, String name, String nickname, String password) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
    }

    public User(String name, String nickname, String password) {
        this.name = name;
        this.nickname = nickname;
        this.password = password;
    }

    protected User(Parcel in) {
        id = in.readLong();
        name = in.readString();
        nickname = in.readString();
        password = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(nickname);
        dest.writeString(password);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {

        @Override
        public User createFromParcel(Parcel in) {
            User u = new User();
            u.setId(in.readLong());
            u.setName(in.readString());
            u.setNickname(in.readString());
            u.setPassword(in.readString());
            return u;
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
