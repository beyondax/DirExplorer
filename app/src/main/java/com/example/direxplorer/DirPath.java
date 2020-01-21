package com.example.direxplorer;


public class DirPath {


    private String dirPathName;
    private String name;
    private boolean isFolder;

    public DirPath(String dirPathName, String name, boolean isFolder) {
        this.name = name;
        this.isFolder = isFolder;
        this.dirPathName = dirPathName;
    }

    public String getDirPath() {
        return dirPathName;
    }


    public String getName() {
        return name;
    }


    public boolean isFolder() {
        return isFolder;
    }

}
