package com.message.service;

import com.message.entity.Magnet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.util.List;

@Service
public interface MagnetService {
    List<Magnet>queryAllMagnet();
    List<Magnet>queryMagnetByKey(String keyword);
    List<Magnet>pagingQueryMagnet(Integer currentPage,Integer pageNum);
    Integer addMagnet(Magnet magnet);
    Integer deleteMagnet(Magnet magnet);
    Integer updateMagnet(Magnet oldMagnet,Magnet newMagnet);
    List <Magnet> queryTimeReport(String start,String end);
    boolean downloadExcel(Magnet magnets[], ServletOutputStream out,String titles[]);
    Integer countMagnet(String start,String end);
    Integer batchDeleteMagnet(List<String> magnetNameList);
}
