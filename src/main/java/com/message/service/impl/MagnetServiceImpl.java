package com.message.service.impl;

import com.message.entity.Magnet;
import com.message.mapper.MagnetMapper;
import com.message.service.MagnetService;
import com.message.utils.MagnetDownloadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.util.List;

@Component
@Service
//autowired自动装配优先选择此实现类
@Primary
public class MagnetServiceImpl implements MagnetService {
    @Autowired
    private MagnetMapper mapper;
    MagnetDownloadUtils utils=new MagnetDownloadUtils();
    @Override
    public List<Magnet> queryAllMagnet() {
        return mapper.queryAllMagnet();
    }

    @Override
    public List<Magnet> queryMagnetByKey(String keyword) {
        return mapper.queryMagnetByKey(keyword);
    }

    @Override
    public List<Magnet> pagingQueryMagnet(Integer currentPage, Integer pageNum) {
        return mapper.pagingQueryMagnet(currentPage,pageNum);
    }

    @Override
    public Integer addMagnet(Magnet magnet) {
        return mapper.addMagnet(magnet);
    }

    @Override
    public Integer deleteMagnet(Magnet magnet) {
        return mapper.deleteMagnet(magnet);
    }

    @Override
    public Integer updateMagnet(Magnet oldMagnet, Magnet newMagnet) {
        return mapper.updateMagnet(oldMagnet,newMagnet);
    }

    @Override
    public List<Magnet> queryTimeReport(String start, String end) {
        return mapper.queryTimeReport(start,end);
    }

    @Override
    public boolean downloadExcel(Magnet[] magnets, ServletOutputStream out,String titles[]) {
        return utils.export(magnets,out,titles);
    }

    @Override
    public Integer countMagnet(String start, String end) {
        return mapper.countMagnet(start,end);
    }

    @Override
    public Integer batchDeleteMagnet(List<String> magnetNameList) {
        return mapper.batchDeleteMagnet(magnetNameList);
    }
}
