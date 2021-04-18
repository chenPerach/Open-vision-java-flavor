package com.primo.vision.Components ;

import com.primo.vision.pojo.Settings;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

class Camera{
    private VideoCapture cam;
    public Camera(int path){
        cam = new VideoCapture(0);
    }
    public Camera(String path){
        this.cam = new VideoCapture(path);
    }
    public void SetSettings(Settings settings){
        this.cam.set(Videoio.CAP_PROP_FRAME_WIDTH,settings.getWidth());
        this.cam.set(Videoio.CAP_PROP_FRAME_HEIGHT,settings.getHeight());
    }
    public boolean isOpen(){
        return this.cam.isOpened();
    }
    public boolean getFrame(Mat img){
        return cam.read(img);
    }
    public void release(){
        cam.release();
    }
}