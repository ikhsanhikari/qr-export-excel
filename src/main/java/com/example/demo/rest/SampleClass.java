/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

/**
 *
 * @author admin
 */
public class SampleClass {
    private Integer number1;
    private String operator;
    private Integer number2;

    public int execute(Integer number1, String operator, Integer number2){
        int result = 0;
        if(operator.equals("+")){
            result = number1+number2;
        }else{
            
        }
        return result;
    }
    
    public Integer getNumber1() {
        return number1;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getNumber2() {
        return number2;
    }

    public void setNumber2(Integer number2) {
        this.number2 = number2;
    }
    
    
}
