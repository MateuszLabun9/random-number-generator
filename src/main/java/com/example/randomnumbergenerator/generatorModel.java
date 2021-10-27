package com.example.randomnumbergenerator;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

import java.util.Arrays;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class generatorModel {
    private int[] intervalOfNumbers;
    private int[] possibilitiesOfNumber;
    private int[] samplesArray;
    private SortedMap<Integer, String> tm2 = new TreeMap<Integer, String>();


    public void generateRandomNumbers(int[] intervalOfNumbers, int[] possibilitiesOfNumbers){
        this.intervalOfNumbers=intervalOfNumbers;
        this.possibilitiesOfNumber=possibilitiesOfNumbers;

        int[] numbersToGenerate = new int[possibilitiesOfNumbers.length];
        double[] discreteProbabilities = new double[possibilitiesOfNumbers.length];

        for(int i=0; i < possibilitiesOfNumbers.length;i++){
            //losowanie z podanego przedziału, tyle liczb ile jest podanych prawdopodobieństwa
            numbersToGenerate[i]= ThreadLocalRandom.current().nextInt(this.intervalOfNumbers[0],this.intervalOfNumbers[1] + 1);
            discreteProbabilities[i]=possibilitiesOfNumber[i]*0.01;// zamiana na poprawne prawdopodobieństwo np z 20 do 0.2
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
                .forEach((k, v) -> tm2.put(Integer.parseInt(k),String.valueOf(v.size())));// licze wystąpienia

        System.out.println("uwaga teraz leci posortowana mapka !!!1");
        System.out.println(tm2);
        System.out.println("koniec posortowanej mapki !!! ");

        distribution=null;
        numbersToGenerate=null;
        discreteProbabilities=null;



    }

    public SortedMap<Integer, String> getTm2() {
        return tm2;
    }

    public int[] getSamplesArray() {
        return samplesArray;
    }
}
