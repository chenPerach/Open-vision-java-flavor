package com.primo.vision.pojo;

import org.json.simple.JSONObject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)

public class Settings {
    @Getter
    private int width;
    @Getter
    private int height;

    public static Settings fromJson(JSONObject obj){
        return new Settings((int)obj.get("width"),(int)obj.get("height"));
    }
    public Settings(Settings settings){
        this.width = settings.width;
        this.height = settings.height;
    }
}
