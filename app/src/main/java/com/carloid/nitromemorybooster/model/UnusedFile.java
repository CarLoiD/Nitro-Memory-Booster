package com.carloid.nitromemorybooster.model;

public class UnusedFile {
    public UnusedFile(String name, String path, Long size) {
        this.name = name;
        this.path = path;
        this.size = size;
        this.delete = false; // TODO: Refac this later
    }

    public String name;
    public String path;
    public Long size;
    public boolean delete;
}
