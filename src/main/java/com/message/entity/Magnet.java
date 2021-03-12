package com.message.entity;

import lombok.Data;
import java.sql.Date;

/*
磁力链接
name:磁力资源名称
magnet:磁力链接
time:收录时间
 */
@Data
public class Magnet {
    private String name;
    private String magnet;
    private Date time;
}
