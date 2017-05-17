package com.example.samsung.mypic1;



public class Singleton {

    private static Singleton instance;
    private String location;
    private String phone;
    private Singleton(){

    }

    public static Singleton getInstance(){

        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }

    public void setLocation(String l){
        location = l;
    }
    public String getLocation(){
        return location;
    }
    public void setPhoneNumber(String p){
        phone = p;
    }
    public String getPhone(){
        return phone;
    }

}
