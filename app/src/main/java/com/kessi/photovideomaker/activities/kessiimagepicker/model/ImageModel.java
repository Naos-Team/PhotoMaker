package com.kessi.photovideomaker.activities.kessiimagepicker.model;

public class ImageModel {
    int id;
    String name;
    String pathFile;
    String pathFolder;
    long count;

    public ImageModel(String name, String pathFile, String pathFolder, long count) {
        this.name = name;
        this.pathFile = pathFile;
        this.pathFolder = pathFolder;
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }


    public String getPathFile() {
        return this.pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathFolder() {
        return this.pathFolder;
    }

    public void setPathFolder(String pathFolder) {
        this.pathFolder = pathFolder;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
