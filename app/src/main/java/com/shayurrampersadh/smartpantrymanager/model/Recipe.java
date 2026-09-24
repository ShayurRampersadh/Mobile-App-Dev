package com.shayurrampersadh.smartpantrymanager.model;

public class Recipe {

    private int id;
    private String name;
    private String prepSteps;

    public Recipe(int id, String name, String prepSteps){
        this.id = id;
        this.name = name;
        this.prepSteps = prepSteps;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }

    public String getPrepSteps(){
        return prepSteps;
    }
}
