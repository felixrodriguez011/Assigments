package com.example.felix.dialogapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class Thing implements Parcelable {
    @PrimaryKey(autoGenerate = true) //auto_increment
    @NonNull
    private int ID;
    private String name;
    private String attribute;

    public Thing(String name, String attribute) {
        this.name = name;
        this.attribute = attribute;
    }

    protected Thing(Parcel in) {
        ID = in.readInt();
        name = in.readString();
        attribute = in.readString();
    }

    public static final Creator<Thing> CREATOR = new Creator<Thing>() {
        @Override
        public Thing createFromParcel(Parcel in) {
            return new Thing(in);
        }

        @Override
        public Thing[] newArray(int size) {
            return new Thing[size];
        }
    };

    @NonNull
    public int getID() {
        return ID;
    }

    public void setID(@NonNull int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribute() {
        return attribute;
    }

    @Override
    public String toString() {
        return "Thing{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", attribute='" + attribute + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(name);
        dest.writeString(attribute);
    }
}