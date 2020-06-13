package hw09.jdbc.jdbc.mapper.testingclasses;

import java.math.BigDecimal;

import hw09.jdbc.jdbc.mapper.annotations.Id;

public class Account {
    @Id
    private long no;
    private String type;
    private BigDecimal rest;

    public Account() {
    }

    public Account(long no, String type, BigDecimal rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public void setNo(long no) {
        this.no = no;
    }

    public long getNo() {
        return no;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setRest(BigDecimal rest) {
        this.rest = rest;
    }

    public BigDecimal getRest() {
        return rest;
    }
}
