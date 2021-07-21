package com.example.demo;

import com.example.demo.rest.SampleClass;
import com.example.demo.util.JavaStringCompiler;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import on.the.fly.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
//        JavaStringCompiler compiler = new JavaStringCompiler();
//        Map<String, byte[]> results = compiler.compile("UserProxy.java", JAVA_SOURCE_CODE);
//        Class<?> clazz = compiler.loadClass("on.the.fly.UserProxy", results);
        // try instance:
//        User user = (User) clazz.newInstance();

        

        SpringApplication.run(DemoApplication.class, args);
    }

    static final String JAVA_SOURCE_CODE = ""
            + "package on.the.fly;                                            "
            + "public class UserProxy  {                     "
            + " private String test;                                                       "
            + "public UserProxy(){"
            + "System.out.print(\"How to make\");"
            + "}"
            + "public static void main(String[] args){System.out.print(\"How to make\");} "
            + "}                                                              ";

}
