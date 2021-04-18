package com.primo.vision;

import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import nu.pattern.OpenCV;



@SpringBootApplication
public class VisionApplication {
	static{
		OpenCV.loadLocally();
	}
	public static void main(String[] args) {
		SpringApplication.run(VisionApplication.class, args);
		System.out.println("loaded OpenCV version: "+Core.VERSION);
	}
}
