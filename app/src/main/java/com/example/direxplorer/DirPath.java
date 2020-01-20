package com.example.direxplorer;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.io.File;

public class DirPath implements Parcelable {


    private String dirPathName;
    private String name;
    boolean isFolder;

    public DirPath(String dirPathName, String name, boolean isFolder) {
        this.name = name;
        this.isFolder = isFolder;
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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected DirPath(Parcel in) {
        this.dirPathName = in.readString();
        this.name = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.isFolder = in.readBoolean();
        }
    }

    public String getName() {
        return name;
    }


    public boolean isFolder() {
        return isFolder;
    }


    @NonNull
    @Override
    public String toString() {
        if (isFolder) {
            return getDirPathName();
        } else {
            return getName();
        }
    }

    public static final Parcelable.Creator<DirPath> CREATOR = new Parcelable.Creator<DirPath>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
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
