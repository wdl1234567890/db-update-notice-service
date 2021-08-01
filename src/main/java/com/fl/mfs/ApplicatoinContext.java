package com.fl.mfs;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fl.mfs.service.NoticeService;
import com.fl.mfs.utils.Utils;

@SpringBootApplication
public class ApplicatoinContext {
    public static void main( String[] args )
    {
    	SpringApplication.run(ApplicatoinContext.class, args);
    	try {
			NoticeService.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
}
