package com.jinax.adweb_backend.Component.Tower;

import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : chara
 */
public class Hanoi {
    private final int numPlates;
    private final LinkedList<Plate> first;
    private final LinkedList<Plate> second;
    private final LinkedList<Plate> third;

    public Hanoi(int numPlates) {
        this.numPlates = numPlates;
        this.first = new LinkedList<>();
        this.second = new LinkedList<>();
        this.third = new LinkedList<>();
        for(int i = 0;i < numPlates;i++){
            first.offer(new Plate(i + 1));
        }
    }

    public Hanoi() {
        this(3);
    }

    public boolean update(int from,int to){
        Plate plate = getTower(from).pollLast();
        // null if the list is empty
        if(plate == null){
            return false;
        }
        return getTower(to).offer(plate);
    }



    private LinkedList<Plate> getTower(int num){
        switch (num){
            case 1: return first;
            case 2: return second;
            case 3: return third;
        }
        throw new IllegalArgumentException("num out of range");
    }

    public int getNumPlates() {
        return numPlates;
    }

    public int[] getFirstArray() {
        int[] result = new int[numPlates];
        int index = 0;
        for(Plate p : first){
            result[index] = p.getSize();
            index++;
        }
        return result;
    }

    public int[] getSecondArray() {
        int[] result = new int[numPlates];
        int index = 0;
        for(Plate p : second){
            result[index] = p.getSize();
            index++;
        }
        return result;
    }

    public int[] getThirdArray() {
        int[] result = new int[numPlates];
        int index = 0;
        for(Plate p : third){
            result[index] = p.getSize();
            index++;
        }
        return result;
    }
}
