package com.example.samsung.mypic1;


import android.content.Context;


public class Singleton {

    private static Singleton instance;
    private String location;
    private String phoneNumber;
    private String id;
    private Context con;

    private Singleton(){

    }

    public void setContext(Context context){
        con = con;
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

        phoneNumber  = p;
    }
    public String getPhone(){
        return phoneNumber;
    }
     public void setId(String i){
         id = i.trim();
     }

    public String getId() {
        return id;
    }
}
