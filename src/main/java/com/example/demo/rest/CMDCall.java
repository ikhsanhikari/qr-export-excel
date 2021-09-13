/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.dto.CompileRequestDTO;
import java.io.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
public class CMDCall {

    @GetMapping("/call")
    public String test() throws IOException {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "cd \"D:\\ikhsan\\tiny-language-antlr4-master\" && mvn -q exec:java");
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = "awal";
        String tmp = "";
        while (true) {
            line = r.readLine();
            if (line == null) {
                break;
            }
            System.out.println(line);
            tmp += line + "\n";
        }
        return tmp;
    }

    @PostMapping("/compile")
    public CompileRequestDTO write(@RequestBody CompileRequestDTO crdto) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("D:\\ikhsan\\sample.txt"), "utf-8"))) {
            writer.write(crdto.getCode());
        }
        String a = test();
        crdto.setOutput(a);
        return new CompileRequestDTO(crdto.getCode(), crdto.getOutput());
    }
}
