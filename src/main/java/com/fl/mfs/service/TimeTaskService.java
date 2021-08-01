package com.fl.mfs.service;

import java.io.IOException;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.fl.mfs.mapping.InfoMapper;
import com.fl.mfs.pojo.Info;
import com.fl.mfs.pojo.Infos;
import com.fl.mfs.utils.Utils;

@Configuration
@EnableScheduling
public class TimeTaskService {
	
	@Autowired
	InfoMapper infoMapper;
	
	@Autowired
	Infos infos;
	
	@Scheduled(fixedRate=30000)
	public void timeTaskService() {
		LinkedList<Info> infoss = infos.getInfos();
		if(infoss!= null && infoss.size()!=0)
			infoss.forEach(e -> {
			try {
				int count = 1;
				int length = -1;
				while((length = Utils.pc(e.url))==-1&&count<5)count++;
				if(count >= 5)return;
				System.out.println("length:"+length);
				if(length!=e.oldContentLength) {
					System.out.println("update!!");
					e.oldContentLength=length;
					NoticeService.sendMessage(e.topic, e.toString());
					infoMapper.updateInfo(e);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
}
