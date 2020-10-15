package com.lwn.test.single;

import org.junit.Test;

public class TestOne {

    @Test
    public void testB() {
        String s = "1";
        String b = "2";
        // %s 替换占位符
        System.out.println(String.format("%s:%s", s, b));
    }
}
