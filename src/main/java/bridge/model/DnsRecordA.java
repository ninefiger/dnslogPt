package bridge.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class DnsRecordA implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String subdomain;
    private Timestamp time;
    private String ip;
    private String logID;

    public DnsRecordA() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLogid() {
        return logID;
    }

    public void setLogid(String logID) {
        this.logID = logID;
    }
}
