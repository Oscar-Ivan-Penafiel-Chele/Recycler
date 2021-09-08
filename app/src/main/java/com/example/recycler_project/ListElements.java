package com.example.recycler_project;

public class ListElements {
    int idAddress;
    String addressName;

    public ListElements(int idAddress, String address) {
        this.idAddress = idAddress;
        this.addressName = address;
    }

    public String getAddress() {
        return addressName;
    }

    public void setAddressName(String address) {
        this.addressName = address;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }
}
