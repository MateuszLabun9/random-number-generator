package com.example.randomnumbergenerator;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import java.util.concurrent.ThreadLocalRandom;

public class generatorModel {
    private final int[] intervalOfNumbers;
    private final int[] possibilitiesOfNumbers;


//    public generatorModel(){
//        int[] tab = new int[0];
//        int[]tab2=new int[0];
//        this.intervalOfNumbers = tab;
//        this.possibilitiesOfNumbers=tab2;
//    }
    public generatorModel(int[] intervalOfNumbers,  int[] possibilitiesOfNumbers) {
        this.intervalOfNumbers = intervalOfNumbers;
        this.possibilitiesOfNumbers = possibilitiesOfNumbers;
    }

    public void generateRandomNumbers(){
        int[] numbersToGenerate = new int[possibilitiesOfNumbers.length];
        double[] discreteProbabilities = new double[possibilitiesOfNumbers.length];
        for(int i=0; i < possibilitiesOfNumbers.length;i++){
            numbersToGenerate[i]= ThreadLocalRandom.current().nextInt(this.intervalOfNumbers[0],this.intervalOfNumbers[1] + 1);
            System.out.println(possibilitiesOfNumbers[i]);
            System.out.println(possibilitiesOfNumbers[i]*0.01);
            discreteProbabilities[i]=possibilitiesOfNumbers[i]*0.01;
        }

        EnumeratedIntegerDistribution distribution = new EnumeratedIntegerDistribution(numbersToGenerate, discreteProbabilities);

        int numSamples = 100;
        int[] samples = distribution.sample(numSamples);

        System.out.println(samples);

    }



}
