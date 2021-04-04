package com.example.courseworkone;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class CarList {

    private final int[] cars = new int[]{R.drawable.audi_1,R.drawable.audi_2, R.drawable.audi_3, R.drawable.audi_4, R.drawable.audi_5,
            R.drawable.bmw_1, R.drawable.bmw_2, R.drawable.bmw_3, R.drawable.bmw_4, R.drawable.bmw_5,
            R.drawable.mercedes_1, R.drawable.mercedes_2, R.drawable.mercedes_3, R.drawable.mercedes_4, R.drawable.mercedes_5,
            R.drawable.tesla_1, R.drawable.tesla_2, R.drawable.tesla_3, R.drawable.tesla_4, R.drawable.tesla_5,
            R.drawable.toyota_1, R.drawable.toyota_2, R.drawable.toyota_3, R.drawable.toyota_4, R.drawable.toyota_5,
            R.drawable.honda_1, R.drawable.honda_2, R.drawable.honda_3, R.drawable.honda_4, R.drawable.honda_5};
    private final String[] carNames = new String[]{"Audi","BMW","Mercedes Benz","Tesla","Toyota","Honda"};
    private int numGens;
    private Random randomise;
    private final ArrayList<String> returnNameList = new ArrayList<>();
    private final ArrayList<Integer> returnResourceList = new ArrayList<>();
    private final ArrayList<Integer> directPrevious = new ArrayList<>();
    private final ArrayList<String> directNames = new ArrayList<>();

    public CarList(int numGens) {
        this.numGens = numGens;
        randomise = new Random();
    }

    public void randomize() {
        boolean passer = false;
        returnResourceList.clear();
        returnNameList.clear();
        for (int i = 0; i < numGens; i++) {
            int index;
            do {
                index = randomise.nextInt(cars.length);
                for (int a = 0; a < cars.length; a++) {
                    if (((directPrevious.size() > 0) && (cars[index] == directPrevious.get(a))) ||
                            ((directNames.size() > 0) && (carNames[index].equals(directNames.get(a))))) {
                        passer = true;
                        break;
                    }
                }
            } while (passer);
            Log.e("IDEX", String.valueOf(cars[index]));
            returnNameList.add(carNames[index]);
            returnResourceList.add(cars[index]);
            directPrevious.add(cars[index]);
            passer = false;
        }
    }

    public ArrayList<String> getNameList() {
        return returnNameList;
    }

    public ArrayList<Integer> getResourceList() {
        return returnResourceList;
    }

    public String[] getAllNameList() {
        return carNames;
    }

    public int[] getAllResourcesList() {
        return cars;
    }

}
