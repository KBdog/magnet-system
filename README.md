# kb磁力后端
该springboot项目用于存储磁力链接以及进行用户管理
## 使用技术
spring、springmvc、mybatis、redis、springboot、jwt
## 功能介绍
1. 磁力链接的添加、删除、更改、查询
2. 用户信息的管理
3. 用户登录检验
4. token签发
5. 请求拦截token验证
6. RESTful API返回数据封装
7. 时段磁力收录表格(excel)下载
## 配置
配置文件`application.yml`
```yaml
server:
  port: 8082
spring:
  redis:
    host: 127.0.0.1
    port: 6379
    timeout: 20000
    database: 0
    jedis:
      pool:
        max-active: 8
        max-wait: -1
        max-idle: 500
        min-idle: 0
    lettuce:
      shutdown-timeout: 0
  datasource:
    username: root
    password: root
    url: jdbc:mysql://localhost:3306/message_system?useSSL=false&serverTimezone=GMT
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    properties:
      hibernate:
        ddl-auto: update
        format_sql: true
    database-platform: org.hibernate.dialect.MySQLDialect
    show-sql: false
mvc:
  view:
    prefix: /templates/
    suffix: .html
mybatis:
  mapper-locations: classpath:mapping/*Mapper.xml
  type-aliases-package: com.message.entity

# jwt 配置
custom:
  jwt:
    # header:凭证(校验的变量名)
    header: Authorization
    # 有效期1天(单位:s)
    #expire: 5184000
    # token有效期s
    expire: 86400
    # secret: 秘钥(普通字符串) 不能太短，太短可能会导致报错
    secret: 99c2918fe19d30bce25abfac8a3733ec
    # 签发者
    issuer: kbdog

```
## RESTful API接口
### 登录验证
POST `127.0.0.1:8082/login/validate`
### 获取用户信息
POST `127.0.0.1:8082/login/message`
### 获得所有磁力信息
GET `127.0.0.1:8082/queryMagnet/all`
### 模糊查询
POST `127.0.0.1:8082/queryMagnet/keyword`
### 分页查询
GET `127.0.0.1:8082/queryMagnet/all/{currentPage}/{pageNum}`
### 添加磁力
PUT `127.0.0.1:8082/queryMagnet/add_magnet`
### 删除磁力
DELETE `127.0.0.1:8082/queryMagnet/delete_magnet`
### 批量删除磁力
DELETE `127.0.0.1:8082/queryMagnet/batch/delete`
### 编辑磁力
PUT `127.0.0.1:8082/queryMagnet/update_magnet`
### 时段收录磁力
POST `127.0.0.1:8082/queryMagnet/query_TimeReport`
### 下载磁力收录表格
POST `127.0.0.1:8082/queryMagnet/downloadExcel`
### 时段收录磁力数
GET `127.0.0.1:8082/queryMagnet/count`
## 感谢
* [潘子夜个人博客jwt教程](https://www.panziye.com/java/1349.html)
* [飞污熊博客](https://www.xncoding.com/2017/07/05/spring/sb-restful.html)
* [知乎关于json以及RESTful API的介绍](https://zhuanlan.zhihu.com/p/85895338)



