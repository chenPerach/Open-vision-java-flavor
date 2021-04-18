package com.primo.vision.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.primo.vision.Components.CameraThread;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MJPEGStreamer {
    CameraThread stream;

    MJPEGStreamer() {
        stream = new CameraThread(0);
    }

    @GetMapping(path = "/stream")
    public void streamMjpeg(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("multipart/x-mixed-replace; boundary=--BoundaryString");
        OutputStream sOutputStream = response.getOutputStream();

        while (true) {
            byte[] nextFrame = stream.getImg();

            try {
                sOutputStream.write(("--BoundaryString\r\n" + "Content-type: image/jpeg\r\n" + "Content-Length: "
                        + nextFrame.length + "\r\n\r\n").getBytes());
                sOutputStream.write(nextFrame);
                sOutputStream.write("\r\n\r\n".getBytes());
                sOutputStream.flush();
                TimeUnit.MILLISECONDS.sleep(40);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
