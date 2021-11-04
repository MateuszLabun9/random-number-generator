package com.example.randomnumbergenerator;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class generatorModel {
    private int[] intervalOfNumbers;
    private int[] possibilitiesOfNumber;
    private int[] samplesArray;
    private SortedMap<Integer, String> distributionValuesMap = new TreeMap<Integer, String>();


    public void generateRandomNumbers(){

        int[] numbersToGenerate = new int[possibilitiesOfNumber.length];
        double[] discreteProbabilities = new double[possibilitiesOfNumber.length];

        //Generacja losowych liczb z zakresu ustawionego przez użytkownika oraz modyfikacja prawdopodobieństwa
        Set<Integer> unique = new HashSet<>();
        while (unique.size() != possibilitiesOfNumber.length) {
            int randInt = ThreadLocalRandom.current().nextInt(this.intervalOfNumbers[0], this.intervalOfNumbers[1] + 1);
            unique.add(randInt);
        }
        int j=0;
        for(Integer e : unique){
            numbersToGenerate[j]=e;
            discreteProbabilities[j]=possibilitiesOfNumber[j]*0.01;
            j++;
        }
        EnumeratedIntegerDistribution distribution = new EnumeratedIntegerDistribution(numbersToGenerate, discreteProbabilities);
        int numSamples = 100;
        samplesArray = distribution.sample(numSamples); // losowanie 100 razy z podanych wartości

        String[] strArray = Arrays.stream(samplesArray)
                .mapToObj(String::valueOf)
                .toArray(String[]::new); // konwersja na stringa

        /*What it does is:
        Create a Stream<String> from the original array
        Group each element by identity, resulting in a Map<String, List<String>>
        For each key value pair, add it to treemap*/
        Arrays.stream(strArray)
                .collect(Collectors.groupingBy(s -> s))
                .forEach((k, v) -> distributionValuesMap.put(Integer.parseInt(k),String.valueOf(v.size())));// licze wystąpienia

        System.out.println("uwaga teraz leci posortowana mapka !!!1");
        System.out.println(distributionValuesMap);
        System.out.println("koniec posortowanej mapki !!! ");

        distribution=null;
        numbersToGenerate=null;
        discreteProbabilities=null;
    }

    public SortedMap<Integer, String> getDistributionValuesMap() {
        return distributionValuesMap;
    }

    public void setIntervalOfNumbers(int[] intervalOfNumbers) {
        this.intervalOfNumbers = intervalOfNumbers;
    }

    public int[] getIntervalOfNumbers() {
        return intervalOfNumbers;
    }
    public int getStrictIntervalOfNumbers(int i){
        return intervalOfNumbers[i];
    }

    public void setPossibilitiesOfNumber(int[] possibilitiesOfNumber) {
        this.possibilitiesOfNumber = possibilitiesOfNumber;
    }

    public int[] getPossibilitiesOfNumber() {
        return possibilitiesOfNumber;
    }
    public int getStrictPossibilitiesOfNumber(int i ){
        return possibilitiesOfNumber[i];
    }
}
