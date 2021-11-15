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

/**
 * The main Scene controller. Dealing with user input and checking if it is correct.
 * Plotting bar chart and switching between scenes.
 *
 * @see generatorModel
 * @see RandomNumberGenerator
 */
public class sceneController {
    /**
     * Label used to inform user about user input error. Specific description is printed when error occurs.
     */
    @FXML
    private Label intervalErrorLabel;
    /**
     * Label used to inform user about user input error. Specific description is printed when error occurs.
     */
    @FXML
    private Label probabilitiesErrorLabel;
    /**
     * FXML input text field used for inserting interval.
     */
    @FXML
    private TextField intervalAmountField;
    /**
     * FXML input text field used for inserting probabilities.
     */
    @FXML
    private TextField probabilitiesVectorField;
    /**
     * FXML BorderPane this is main screen, on which user is inserting values.
     */
    @FXML
    private BorderPane mainPage;
    /**
     * FXML BorderPane this is second screen, on which the bar chart is drawn.
     */
    @FXML
    private BorderPane plotPage;
    /**
     * FXML BarChart which presents generated values.
     */
    @FXML
    private BarChart barChart;

    /**
     * The Interval variable used for check if passed interval is correct.
     */
    int interval=0;
    /**
     * The Label id decides which error label will be changed.
     */
    int labelId;
    /**
     * The Is plottable value, when all requirements are fullfiled change value to true.
     */
    boolean isPlottable= false;
    /**
     * The Generator object.
     */
    generatorModel generator;

    /**
     * Method that is invoked after pressing submit button. Reads both input fields and check if data
     * provided by user is correct. After checking, creates new instance of generator model.
     *
     * @param event This is an event that occurs when user is pressing a button with submit function
     */
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

    /**
     * Method that check user input in first input field, if provided data is incorrect, throws exception and
     * print information to user that he have to provide correct values.
     *
     * @throws notPositiveNumberException if provided values are not positive numbers, method thow this exception.
     * @throws wrongAmountOfValuesException if user provides different quantity on numbers than 2, throws exception.
     * @throws wrongIntervalException if interval is negative or equals to 0, throws exception.
     */
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


    /**
     * Method that check second user input field, if provided data is incorrect, throws exception and
     * print information to user that he have to provide correct values.
     *
     * @throws notPositiveNumberException if provided values are not positive numbers, method thow this exception.
     * @throws wrongAmountOfValuesException if user provides different quantity on numbers than 2, throws exception.
     * @throws wrongSumOfPosibilitiesException if sum of passed posibilities is not equal 100.
     */
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

    /**
     * Draw chart with values generated in generatorModel and adjust scale.
     */
    public void drawChart(){
        XYChart.Series series = new XYChart.Series();
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);
        SortedMap<Integer, String> tm2 = generator.getDistributionValuesMap();

        for(Map.Entry m:tm2.entrySet())
        {
            int value = Integer.parseInt((String) m.getValue());
            series.getData().add(new XYChart.Data(String.valueOf(m.getKey()), value*0.000001));
        }
        barChart.getData().addAll(series);
    }

    /**
     * Go back to front method, after pressing button is switching from second view to main view with input fields.
     *
     * @param event the event that occurs when backToFront button pressed.
     */
    public void goBackToFront(ActionEvent event){
        generator=null;
        barChart.getData().clear();
        barChart.layout();
        mainPage.toFront();
    }
}