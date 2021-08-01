package com.fl.mfs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fl.mfs.mapping.InfoMapper;
import com.fl.mfs.pojo.Info;
import com.fl.mfs.pojo.Infos;
import com.fl.mfs.utils.Utils;

@RestController()
@RequestMapping("/mfs")
public class ClickController {
	
	@Autowired
	InfoMapper infoMapper;
	@Autowired
	Infos infos;
	
	@PostMapping("/subscribeTopic")
	public Info subscribeTopic(@RequestBody Map<String,String> map) {
		String url = map.get("url");
		if(url==null || url.equals(""))return null;
		Info info = infos.getInfoByUrl(url);
		if(info != null) {
			info.subscribeCount++;
			infoMapper.updateInfo(info);
		}else {
			try {
				int count = 1;
				info = null;
				while((info = Utils.getInfo(url)) == null && count < 10)count++;
				infos.addInfo(info);
				infoMapper.insertInfo(info);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return info;
	}
	
	@PostMapping("/unsubscribeTopic")
	public boolean unsubscribeTopic(@RequestBody Map<String,String> map) {
		Info info = infos.getInfoByTopic(map.get("topic"));
		if(info == null)return false;
		info.subscribeCount--;
		if(info.subscribeCount==0) {
			infos.getInfos().remove(info);
			infoMapper.removeInfo(info);
		}else {
			infoMapper.updateInfo(info);
		}
		return true;
	}
	
	
	@PostMapping("/uninstall")
	public boolean uninstall(@RequestBody ArrayList<String> topics) {
		topics.forEach(e->{
			Map<String,String> map = new HashMap<>();
			map.put("topic", e);
			unsubscribeTopic(map);
		});
		return true;
	}

}
