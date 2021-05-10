# From Bridge 

基于SPuerBRead的Bridge项目的改版

原版：https://github.com/SPuerBRead/Bridge

详细修改内容：https://ninefiger.github.io/2021/05/06/dnslog平台探索/

部署方法（手动，docker可使用原版）
-----------

### 1. 手动部署

#### 域名解析

假设根域名是ninefiger.top，服务器IP是1.1.1.1进行以下配置

    
    配置A记录，子域名ns，解析到1.1.1.1
    
    配置NS记录，子域名dns，解析到ns1.dnslog.com
    
    配置A记录，子域名log，解析到10.10.10.10

![15655801079930](https://github.com/ninefiger/dnslogPt/blob/master/pic/dns配置示例.jpg)
    
log.ninefiger.top用于访问平台web
    
ninefiger.top 作为测试时payload中设置的域名，每个用户对应dns.ninefiger.top下的子域名，如xkse.dns.ninefiger.top登录平台后可以在API或DNSLOG中看到对应的地址
    
#### 数据库配置

登录mysql执行以下命令，bridge.sql在程序的根目录下
    
    source bridge.sql

#### 服务器配置

环境：Java 1.8、Maven
    
修改resources目录下application.properties文件中的web服务端口（默认80端口）和数据库连接信息
    
    mvn clean package -DskipTests
    
maven生成的jar包位置在target目录下，如dns_log-0.0.1-SNAPSHOT.jar

    java -jar dns_log-0.0.1-SNAPSHOT.jar dnslogDomain managerDomain ip signal
    java -jar dns_log-0.0.1-SNAPSHOT.jar dnslogDomain managerDomain ip signal headerSignal
    java -jar dns_log-0.0.1-SNAPSHOT.jar dns.ninefiger xkse.dns.ninefiger.top 23.74.41.75 ninefiger
    
参数一：dnslog的域名
参数二：dnslog平台的web管理域名
参数三：dns请求返回的ip地址
参数四：注册时需要使用的暗号
参数五：启动时如果不带有该参数，默认所有人都可以访问到web管理页面，执行参数后需要配置自定义header如。Header-Signal: headerSignal
    

部分截图
-----------


自定义header正确时请求web平台

![15655801079930](https://github.com/ninefiger/dnslogPt/blob/master/pic/自定义header正确时请求web平台.jpg)


自定义header错误时请求web平台

![自定义header错误时请求web平台](https://github.com/ninefiger/dnslogPt/blob/master/pic/自定义header错误时请求web平台.jpg)

DNSLOG

![DNSLOG](https://github.com/ninefiger/dnslogPt/blob/master/pic/DNSLOG.png)

WEBLOG

![WEBLOG](https://github.com/ninefiger/dnslogPt/blob/master/pic/HTTPLOG.jpg)