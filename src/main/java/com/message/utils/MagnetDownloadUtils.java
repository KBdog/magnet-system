package com.message.utils;

import com.message.entity.Magnet;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
下载报表工具类
 */
public class MagnetDownloadUtils {
    public boolean export(Magnet magnets[], ServletOutputStream out,String titles[]) {
        boolean flag=false;
        //1.先创建一个工作簿workbook,对应于一个excel文件
        XSSFWorkbook workbook=new XSSFWorkbook();
        //2.在工作簿中创建一个sheet,对应于excel中的sheet,并设置宽度
        XSSFSheet sheet=workbook.createSheet("磁力收录情况");
        sheet.setDefaultColumnWidth(20);
        //3.在sheet中添加表头第0行
        XSSFRow row=sheet.createRow(0);
        //4.创建单元格样式
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        //居中样式
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //5.设置表头,添加列标题
        XSSFCell cell=null;
        for(int i=0;i<titles.length;i++){
            //创建表头单元格
            cell=row.createCell(i);
            //设置列名
            cell.setCellValue(titles[i]);
            //设置列样式
            cell.setCellStyle(cellStyle);
        }
        //6.把获取的数据放入集合中
        List<Magnet> magnetList=new ArrayList<Magnet>();
        for(Magnet magnet:magnets){
            magnetList.add(magnet);
        }
        //7.创建单元格,设置值
        XSSFCellStyle dateStyle = workbook.createCellStyle();
        //日期样式
        XSSFDataFormat dataFormat=workbook.createDataFormat();
        dateStyle.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));
        //居中样式
        dateStyle.setAlignment(HorizontalAlignment.CENTER);
        for(int i=0;i<magnetList.size();i++){
            //逐行添加数据
            row=sheet.createRow(i+1);
            row.createCell(0).setCellValue(magnetList.get(i).getName());
            row.createCell(1).setCellValue(magnetList.get(i).getMagnet());
            //把第三列设置为日期样式
            XSSFCell cell_3 = row.createCell(2);
            cell_3.setCellStyle(dateStyle);
            cell_3.setCellValue(magnetList.get(i).getTime());
        }
        //8.将文件写出客户端
        try {
            workbook.write(out);
            out.flush();
            flag=true;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
}
