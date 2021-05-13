package com.primo.vision.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.primo.vision.Components.CameraThread;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Controller(value = "/api/")
public class MJPEGStreamer {
    CameraThread stream;

    MJPEGStreamer() {
        stream = new CameraThread(0);
    }

    @GetMapping(path = "/stream")
    public StreamingResponseBody streamMjpeg(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("multipart/x-mixed-replace; boundary=--BoundaryString");
        return (os) -> {
            byte[] nextFrame = stream.getImg();
            if (nextFrame == null)
                return;
            try {
                os.write(("--BoundaryString\r\n" + "Content-type: image/jpeg\r\n" + "Content-Length: "
                        + nextFrame.length + "\r\n\r\n").getBytes());
                os.write(nextFrame);
                os.write("\r\n\r\n".getBytes());
                os.flush();
            } catch (Exception e) {
            }
        };
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

}
