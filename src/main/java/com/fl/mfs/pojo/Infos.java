package com.fl.mfs.pojo;

import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fl.mfs.mapping.InfoMapper;

@Component
public class Infos {
	
	@Autowired
	InfoMapper infoMapper;
	
	private static LinkedList<Info> infos = null;
	
	public LinkedList<Info> getInfos(){
		if(infos==null) {
			LinkedList<Info> ins = infoMapper.getInfos();
			if(ins==null || ins.size()==0)infos=new LinkedList<Info>();
			else infos=ins;
		}
		return infos;
	}
	
	public void addInfo(Info info) {
		if(infos==null) {
			LinkedList<Info> ins = infoMapper.getInfos();
			if(ins==null || ins.size()==0)infos=new LinkedList<Info>();
			else infos=ins;
		}
		infos.add(info);
	}
	
	public Info getInfoByUrl(String url) {
		if(infos==null) {
			LinkedList<Info> ins = infoMapper.getInfos();
			if(ins==null || ins.size()==0)infos=new LinkedList<Info>();
			else infos=ins;
		}

		for(Info info: infos ) {
			if(info.url.equals(url))return info;
		}
		return null;
	}
	
	public Info getInfoByTopic(String topic) {
		if(infos==null) {
			LinkedList<Info> ins = infoMapper.getInfos();
			if(ins==null || ins.size()==0)infos=new LinkedList<Info>();
			else infos=ins;
		}
		for(Info info: infos ) {
			if(info.topic.equals(topic))return info;
		}
		return null;
	}
	
}
