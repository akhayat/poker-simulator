package com.akhayat.poker.simulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("World");
        list.add("!");
        System.out.println(list.reversed());
        list = list.reversed();
        System.out.println("List: " + list);
    }
}
