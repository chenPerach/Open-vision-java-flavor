package com.primo.vision.pojo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Settings {
    @Getter
    private int width;
    @Getter
    private int height;
}
