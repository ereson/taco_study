package com.ereson.toca.email;


import com.ereson.toca.Taco;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class Order {
    /**
     * 传入的email地址
     */
    private final String email;
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}
