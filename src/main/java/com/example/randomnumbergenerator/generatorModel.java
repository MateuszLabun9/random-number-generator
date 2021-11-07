package com.example.randomnumbergenerator;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * generatorModel creates Model with fields required to generate random numbers.
 */
public class generatorModel {
    private int[] intervalOfNumbers;
    private int[] possibilitiesOfNumber;
    private int[] samplesArray;
    private SortedMap<Integer, String> distributionValuesMap = new TreeMap<Integer, String>();

    /**
     *
     */
    public void generateRandomNumbers(){
        int[] numbersToGenerate = new int[possibilitiesOfNumber.length];
        double[] discreteProbabilities = new double[possibilitiesOfNumber.length];
        int beginning = this.intervalOfNumbers[0];//beginning od interval
        int higherInterval = this.intervalOfNumbers[1];//ending of interval
        //span between beginning and ending of interval, divided by number of probabilities
        int valueOfIntervals = (higherInterval -this.intervalOfNumbers[0])/ this.possibilitiesOfNumber.length;


        //generate random numbers, amount is equal to number of probabilities
        for(int i =0; i<this.possibilitiesOfNumber.length;i++){
            if((i + 1) != this.possibilitiesOfNumber.length){
                numbersToGenerate[i] = ThreadLocalRandom.current().nextInt(beginning, beginning + valueOfIntervals);
                discreteProbabilities[i]=possibilitiesOfNumber[i]*0.01;//adjusting probabilities
                System.out.println("przedzial od: " + beginning + "do " + (beginning+valueOfIntervals));
                beginning+=valueOfIntervals;
            }
            else {
                numbersToGenerate[i] = ThreadLocalRandom.current().nextInt(beginning,higherInterval );
                discreteProbabilities[i]=possibilitiesOfNumber[i]*0.01;//adjusting probabilities
                System.out.println("koncowy przedzial od: " + beginning + "do " + higherInterval);
            }
        }
        System.out.println("leca wylosowane liczby" + Arrays.toString(numbersToGenerate));
        //creating enumerated distribution
        EnumeratedIntegerDistribution distribution = new EnumeratedIntegerDistribution(numbersToGenerate, discreteProbabilities);
        //
        int numSamples = 1000000;
        samplesArray = distribution.sample(numSamples); // losowanie 100 razy z podanych wartości
        String[] strArray = Arrays.stream(samplesArray)
                .mapToObj(String::valueOf)
                .toArray(String[]::new); // konwersja na stringa


        /*Create a Stream<String> from the original array
        Group each element by identity, resulting in a Map<String, List<String>>
        For each key value pair, add it to treemap*/
        Arrays.stream(strArray)
                .collect(Collectors.groupingBy(s -> s))
                .forEach((k, v) -> distributionValuesMap.put(Integer.parseInt(k),String.valueOf(v.size())));// licze wystąpienia

        //Deleting created objects
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
