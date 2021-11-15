package com.example.randomnumbergenerator;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * generatorModel creates Model with fields required to generate random numbers.
 *
 * @see sceneController
 * @see RandomNumberGenerator
 */
public class generatorModel {
    /**
     * Int array that holds interval of numbers inserted by user.
     */
    private int[] intervalOfNumbers;
    /**
     * Int array that holds possibilities of numbers inserted by user.
     */
    private int[] possibilitiesOfNumber;
    /**
     * Int array that holds samples created by enumarated distribution.
     */
    private int[] samplesArray;
    /**
     * SortedMap that holds extracted values from samplesArray, used for chart drawing.
     */
    private SortedMap<Integer, String> distributionValuesMap = new TreeMap<Integer, String>();

    /**
     * Generate random numbers.Firstly dividing interval into parts, number of divisions depends on amount of
     * possibilities inserted by user. After that, generated numbers are used to create enumerated distribution,
     * with probabilities passed by user. At the end, generated values are inserted into map in order to sort them out.
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

    /**
     * Gets distribution values map.
     *
     * @return the distribution values map
     */
    public SortedMap<Integer, String> getDistributionValuesMap() {
        return distributionValuesMap;
    }

    /**
     * Sets interval of numbers.
     *
     * @param intervalOfNumbers the interval of numbers
     */
    public void setIntervalOfNumbers(int[] intervalOfNumbers) {
        this.intervalOfNumbers = intervalOfNumbers;
    }

    /**
     * Get interval of numbers int [ ].
     *
     * @return the int [ ]
     */
    public int[] getIntervalOfNumbers() {
        return intervalOfNumbers;
    }

    /**
     * Get strict interval of numbers int.
     *
     * @param i the
     * @return the int
     */
    public int getStrictIntervalOfNumbers(int i){
        return intervalOfNumbers[i];
    }

    /**
     * Sets possibilities of number.
     *
     * @param possibilitiesOfNumber the possibilities of number
     */
    public void setPossibilitiesOfNumber(int[] possibilitiesOfNumber) {
        this.possibilitiesOfNumber = possibilitiesOfNumber;
    }

    /**
     * Get possibilities of number int [ ].
     *
     * @return the int [ ]
     */
    public int[] getPossibilitiesOfNumber() {
        return possibilitiesOfNumber;
    }

    /**
     * Get strict possibilities of number int.
     *
     * @param i the
     * @return the int
     */
    public int getStrictPossibilitiesOfNumber(int i ){
        return possibilitiesOfNumber[i];
    }
}
