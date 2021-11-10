package com.example.appblehv3;

public class PlantasInfo {

    private String nombre;
    private String info;
    private int picture;


    public PlantasInfo(){

    }

    public PlantasInfo(String nombre, String info, int picture) {
        this.nombre = nombre;
        this.info = info;
        this.picture = picture;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getpicture() {
        return picture;
    }

    public void setpicture(int picture) {
        this.picture = picture;
    }


}
