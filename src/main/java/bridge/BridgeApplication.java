package bridge;

import bridge.config.DnslogConfig;
import bridge.dnsserver.UDPServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@SpringBootApplication
@MapperScan("bridge.mapper")
@EnableScheduling
public class BridgeApplication {

    public static void main(String[] args) {
    try {
        DnslogConfig.dnslogDomain = args[0];
        DnslogConfig.managerDomain = args[1];
        DnslogConfig.ip = args[2];
        DnslogConfig.signal = args[3];
        if (args.length == 5) {
            DnslogConfig.headerSignal = args[4];

        }
        System.out.println("参数的总长度是：" + args.length);
        ConfigurableApplicationContext context = SpringApplication.run(BridgeApplication.class, args);
        UDPServer udpServer = context.getBean(UDPServer.class);
        udpServer.start();
    } catch(Exception e){
        System.out.println("[-] 参数错误");
        System.out.println("[!] eg:\n" +
                "\tjava -jar dns_log-0.0.1-SNAPSHOT.jar dnslogDomain managerDomain ip signal\n" +
                "\tjava -jar dns_log-0.0.1-SNAPSHOT.jar dnslogDomain managerDomain ip signal headerSignal\n\n" +
                "dnslogDomain: dnslog的域名\n" +
                "managerDomain: dnslog平台web访问的域名\t" +
                "ip: dnslog默认解析记录的响应\n" +
                "signal: dnslog平台注册的暗号\n" +
                "headerSignal: log平台web访问自定义的header内容，如DnslogConfig中配置的Accept-Cache\n");
    }
    }

}
