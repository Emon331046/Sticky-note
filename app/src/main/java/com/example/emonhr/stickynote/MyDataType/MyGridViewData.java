package com.example.emonhr.stickynote.MyDataType;



public class MyGridViewData {

    int id;


    String description;

    public MyGridViewData(int id,int colorCode, String description) {
        this.id = id;
        this.description = description;
        this.colorCode=colorCode;
    }

    public int getColorCode() {
        return colorCode;
    }

    public void setColorCode(int colorCode) {
        this.colorCode = colorCode;
    }

    int colorCode;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
