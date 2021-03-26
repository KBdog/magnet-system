package com.message.mapper;

import com.message.entity.Magnet;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagnetMapper {
    List<Magnet> queryAllMagnet();
    List<Magnet> queryMagnetByKey(@Param("key") String keyword);
    List<Magnet> pagingQueryMagnet(@Param("currentPage") Integer currentPage,@Param("pageNum") Integer pageNum);
    Integer addMagnet(Magnet magnet);
    Integer deleteMagnet(Magnet magnet);
    Integer updateMagnet(@Param("oldMagnet") Magnet magnet1,@Param("newMagnet") Magnet magnet2);
    List<Magnet> queryTimeReport(@Param("start") String start,@Param("end") String end);
    @Select("select count(*) from magnet where time between #{start} and #{end}")
    Integer countMagnet(@Param("start") String start,@Param("end") String end);
    Integer batchDeleteMagnet(@Param("nameList") List<String> magnetNameList);
}
