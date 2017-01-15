package demo;

import net.lighting.flow.base.K;

public class TestStr {

    public static void main(String[] args) {
        String s = K.request_+"abc";
        System.out.println(s.substring(K.request_.length()));
    }
    
}
