package com.example.sender;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsageDetail implements Serializable {

    public UsageDetail() {
    }

    public String username;

    public long duration;

    public long data;

}