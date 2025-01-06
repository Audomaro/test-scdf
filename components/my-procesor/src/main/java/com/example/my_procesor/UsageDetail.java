package com.example.my_procesor;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsageDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private long duration;
    private long data;

    public UsageDetail() {
    }

    public UsageDetail(String username, long duration, long data) {
        this.username = username;
        this.duration = duration;
        this.data = data;
    }
}