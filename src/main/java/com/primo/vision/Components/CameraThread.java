package com.primo.vision.Components;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;

import lombok.Getter;

public class CameraThread implements Runnable {
    private static final String TAG = "CAM_THREAD";
    private Camera cam;
    private boolean interrupted;
    private Thread thread;
    private Mat placeHolder;
    @Getter
    private byte[] img;

    public CameraThread(int path) {
        this.cam = new Camera(path);
        config();
    }
    public CameraThread(String path) {
        this.cam = new Camera(path);
        config();
    }
    private void config(){
        interrupted = false;
        placeHolder = Imgcodecs.imread(Path.of("assets","pictures","place_holder.jpg").toString());
        thread = new Thread(this);
        thread.start();
    }
    public void interrupt(){
        this.interrupted = true;
    }
    @Override
    public void run() {
        Mat frame = new Mat();
        while (!interrupted) {
            if (!cam.isOpen())
                continue;
            cam.getFrame(frame);
            if (frame.empty()){
                Log.put(TAG,"coulden't get image from camera");
                frame = placeHolder;
            }
            img = mat2byte(frame);
        }
        cam.release();
    }

    private static byte[] mat2byte(Mat m) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1)
            type = BufferedImage.TYPE_3BYTE_BGR;
        
        int bufferSize = m.channels()*m.cols()*m.rows();
        byte[] buffer = new byte[bufferSize];

        m.get(0,0,buffer);
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpg", baos );
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			return imageInByte;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }
}
