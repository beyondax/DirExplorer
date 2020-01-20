package com.example.direxplorer;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.File;

public class DirPath implements Parcelable {

    private String dirPathName;

    public DirPath(String dirPathName) {
        this.dirPathName = dirPathName;
    }

    public String getDirPathName() {
        return dirPathName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dirPathName);
    }

    protected DirPath(Parcel in) {
        this.dirPathName = in.readString();
    }

    @NonNull
    @Override
    public String toString() {
        return "" + dirPathName;
    }

    public static final Parcelable.Creator<DirPath> CREATOR = new Parcelable.Creator<DirPath>() {
        @Override
        public DirPath createFromParcel(Parcel source) {
            return new DirPath(source);
        }

        @Override
        public DirPath[] newArray(int size) {
            return new DirPath[size];
        }
    };
}
