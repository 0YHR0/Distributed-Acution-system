package com.group4.ds.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

/**
 * not used yet
 * @author Yang haoran
 */
@AllArgsConstructor
public class User implements Principal{
    private String name;

    @Override
    public String getName() {
        return name;
    }
}
