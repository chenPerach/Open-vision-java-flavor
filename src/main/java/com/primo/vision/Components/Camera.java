package com.primo.vision.Components ;

import com.primo.vision.pojo.Settings;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

class Camera{
    private VideoCapture cam;
    private int path;
    public Camera(int path){
        this.path = path;
        cam = new VideoCapture(0);
        
        // this.cam.set(Videoio.CAP_PROP_FRAME_WIDTH, 320);
        // this.cam.set(Videoio.CAP_PROP_FRAME_HEIGHT, 240);
    }
    public Camera(String path){
        this.cam = new VideoCapture(path);
        this.cam.set(Videoio.CAP_PROP_FRAME_WIDTH, 320);
        this.cam.set(Videoio.CAP_PROP_FRAME_HEIGHT, 240);
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
    public int getPath() {
        return path;
    }
}