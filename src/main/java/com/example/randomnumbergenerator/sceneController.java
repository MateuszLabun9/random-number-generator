package com.example.randomnumbergenerator;

import com.example.randomnumbergenerator.generatorModel;
import customExceptions.notPositiveNumberException;
import customExceptions.wrongAmountOfValuesException;
import customExceptions.wrongIntervalException;
import customExceptions.wrongSumOfPosibilitiesException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.util.Arrays;
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


    int[] intervalOfNumbers;
    int interval=0;
    int[] vectorOfProbabilities;
    int labelId;
    boolean isPlottable= false;

    generatorModel modelik;

    public void submit(ActionEvent event){

        try{
            getIntervalNumbers();
            getVectorOfProbabilities();
            if(isPlottable){
                generatorModel generator = new generatorModel(intervalOfNumbers, vectorOfProbabilities);
                generator.generateRandomNumbers();
                plotPage.toFront();
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
        intervalOfNumbers = Arrays.stream(intervalAmountField.getText().split(",")).mapToInt(Integer::parseInt).toArray();
        if(intervalOfNumbers.length != 2)
        {
            throw new wrongAmountOfValuesException("equal to 2");
        }
        if(intervalOfNumbers[0] <= 0 || intervalOfNumbers[1] <=0){
            throw new notPositiveNumberException();
        }
        System.out.println(intervalOfNumbers[0]);
        System.out.println(intervalOfNumbers[1]);
        interval=intervalOfNumbers[1]-intervalOfNumbers[0] +1;
        if(interval <=0 ){
            throw new wrongIntervalException();
        }
        intervalErrorLabel.setText("");
    }


    private void getVectorOfProbabilities()throws notPositiveNumberException, wrongAmountOfValuesException, wrongSumOfPosibilitiesException {
        labelId=3;
        vectorOfProbabilities = Arrays.stream(probabilitiesVectorField.getText().split(",")).mapToInt(Integer::parseInt).toArray();
        for(int i =0 ; i < vectorOfProbabilities.length; i++){
            if(vectorOfProbabilities[i] <= 0){
                throw new notPositiveNumberException();
            }
        }
        if(vectorOfProbabilities.length > interval){
            System.out.println(vectorOfProbabilities.length);
            throw new wrongAmountOfValuesException(String.valueOf("smaller than " + interval));
        }
        if(IntStream.of(vectorOfProbabilities).sum() != 100){
            throw new wrongSumOfPosibilitiesException();
        }
        isPlottable = true;
    }

    public void goBackToFront(ActionEvent event){//go back btn on plot scene
        mainPage.toFront();
    }


}