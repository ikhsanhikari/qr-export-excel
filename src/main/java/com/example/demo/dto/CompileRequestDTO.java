/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.dto;

/**
 *
 * @author admin
 */
public class CompileRequestDTO {

    private String code;
    private String output;

    public CompileRequestDTO() {
    }

    public CompileRequestDTO(String code, String output) {
        this.code = code;
        this.output = output;
    }
    
    

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

}
