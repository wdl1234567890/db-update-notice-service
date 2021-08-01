package com.fl.mfs.mapping;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.ibatis.annotations.Mapper;

import com.fl.mfs.pojo.Info;

@Mapper
public interface InfoMapper {
	boolean insertInfo(Info info);
	LinkedList<Info> getInfos();
	boolean updateInfo(Info info);
	boolean removeInfo(Info info);
}
