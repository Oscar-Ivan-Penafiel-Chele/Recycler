package com.example.recycler_project;

public class ListElements {
    int id;
    String addressName;

    public ListElements(int id, String address) {
        this.id = id;
        this.addressName = address;

    }

    public String getAddress() {
        return addressName;
    }

    public void setAddressName(String address) {
        this.addressName = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
