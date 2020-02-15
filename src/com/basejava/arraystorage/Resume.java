package com.basejava.arraystorage;

/**
 * Initial resume class
 */
public class Resume {

    // Unique identifier
   private String uuid;

    @Override
    public String toString() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
