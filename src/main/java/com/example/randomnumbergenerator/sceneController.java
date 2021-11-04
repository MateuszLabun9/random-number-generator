package com.example.randomnumbergenerator;

import customExceptions.notPositiveNumberException;
import customExceptions.wrongAmountOfValuesException;
import customExceptions.wrongIntervalException;
import customExceptions.wrongSumOfPosibilitiesException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.util.*;
import java.util.stream.IntStream;

public class sceneController {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label intervalLabel;
    @FXML
    private Label intervalErrorLabel;
    @FXML
    private Label amountLabel;
    @FXML
    private Label amountErrorLabel;
    @FXML
    private Label probabilitiesLabel;
    @FXML
    private Label probabilitiesErrorLabel;
    @FXML
    private TextField intervalAmountField;
    @FXML
    private TextField numbersAmountField;
    @FXML
    private TextField probabilitiesVectorField;
    @FXML
    private Button submitBtn;
    @FXML
    private BorderPane mainPage;
    @FXML
    private BorderPane plotPage;
    @FXML
    private BarChart barChart;

    int interval=0;
    int labelId;
    boolean isPlottable= false;
    generatorModel generator;

    public void submit(ActionEvent event){
        isPlottable=false;
        try{
            generator = new generatorModel();
            getIntervalNumbers();
            getVectorOfProbabilities();
            if(isPlottable){
                generator.generateRandomNumbers();
                plotPage.toFront();
                drawChart();
            }
        }
        catch (NumberFormatException e){
            if(labelId == 1){
                intervalErrorLabel.setText("Insert only numbers");
            }  else if(labelId == 3){
                probabilitiesErrorLabel.setText("Insert only numbers");
            }

        }
        catch(notPositiveNumberException e){
            if(labelId == 1){
                intervalErrorLabel.setText(String.valueOf(e));
            }

            else if(labelId ==3){
                probabilitiesErrorLabel.setText(String.valueOf(e));
            }
        }
        catch (wrongAmountOfValuesException e){
            if(labelId==1)
            {
                intervalErrorLabel.setText(String.valueOf(e));
            }
            else if(labelId==3)
            {
                probabilitiesErrorLabel.setText(String.valueOf(e));
            }
        }
        catch(wrongIntervalException e){
            intervalErrorLabel.setText(String.valueOf(e));
        }
        catch (wrongSumOfPosibilitiesException e){
            probabilitiesErrorLabel.setText(String.valueOf(e));
        }
        catch (Exception e){
            intervalErrorLabel.setText(String.valueOf(e));
        }
    }

    private void getIntervalNumbers()throws notPositiveNumberException, wrongAmountOfValuesException, wrongIntervalException {
        labelId=1;
        //conversion from string to Int, with extracting values with comma
        generator.setIntervalOfNumbers(Arrays.stream(intervalAmountField.getText().split(",")).mapToInt(Integer::parseInt).toArray());
        if(generator.getIntervalOfNumbers().length != 2)
        {
            throw new wrongAmountOfValuesException("equal to 2");
        }
        if(generator.getStrictIntervalOfNumbers(0) <= 0 || generator.getStrictIntervalOfNumbers(1) <=0){
            throw new notPositiveNumberException();
        }
        //calculating interval, interval ahve to be bigger than 0
        interval=generator.getStrictIntervalOfNumbers(1)-generator.getStrictIntervalOfNumbers(0) +1;
        if(interval <=0 ){
            throw new wrongIntervalException();
        }
        intervalErrorLabel.setText("");
    }


    private void getVectorOfProbabilities()throws notPositiveNumberException, wrongAmountOfValuesException, wrongSumOfPosibilitiesException {
        labelId=3;
        //vectorOfProbabilities = Arrays.stream(probabilitiesVectorField.getText().split(",")).mapToInt(Integer::parseInt).toArray();
        generator.setPossibilitiesOfNumber(Arrays.stream(probabilitiesVectorField.getText().split(",")).mapToInt(Integer::parseInt).toArray());
        for(int i =0 ; i < generator.getPossibilitiesOfNumber().length; i++){
            if(generator.getStrictPossibilitiesOfNumber(i) <= 0){
                throw new notPositiveNumberException();
            }
        }
        if(generator.getPossibilitiesOfNumber().length > interval){
            throw new wrongAmountOfValuesException(String.valueOf("smaller than " + interval));
        }
        if(IntStream.of(generator.getPossibilitiesOfNumber()).sum() != 100){
            throw new wrongSumOfPosibilitiesException();
        }
        isPlottable = true;
        probabilitiesErrorLabel.setText("");
    }

    public void drawChart(){
        XYChart.Series series = new XYChart.Series();
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);
        SortedMap<Integer, String> tm2 = generator.getDistributionValuesMap();

        for(Map.Entry m:tm2.entrySet())
        {
            int value = Integer.parseInt((String) m.getValue());
            series.getData().add(new XYChart.Data(String.valueOf(m.getKey()), value*0.01));
        }
        barChart.getData().addAll(series);
    }


    public void goBackToFront(ActionEvent event){//go back btn on plot scene
        generator=null;
        barChart.getData().clear();
        barChart.layout();
        mainPage.toFront();
    }





}