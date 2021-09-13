/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.rest;

import com.example.demo.service.CallCompilerProgrammaticallyExample;
import com.example.demo.util.QRCodeUtil;
import com.example.demo.util.ClassUtils;
import com.example.demo.util.ExcellExporter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.openhft.compiler.CompilerUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author admin
 */
@RestController
@RequestMapping("")
public class HikariExcelEndPoint {

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

//        List<User> listUsers = service.listAll();
//        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);
        ExcellExporter excelExporter = new ExcellExporter();

        excelExporter.export(response);
    }

    @GetMapping("compile")
    public String compile() throws InstantiationException, IllegalArgumentException {
        try {
            String className = "mypackage.MyClass";
            String javaCode = "package mypackage;\n"
                    + "public class MyClass implements Runnable {\n"
                    + "    public void run() {\n"
                    + "        System.out.println(\"Hello World\");\n"
                    + "    }\n"
                    + "public String f(String a){"
                    + "return a;"
                    + "}"
                    + "}\n";
            Class aClass = null;
            try {
                aClass = CompilerUtils.CACHED_COMPILER.loadFromJava(className, javaCode);
                Method method;
                try {
                    method = aClass.getDeclaredMethod("f", String.class);
                    Object test = null;
                    try {
                        //                    try {
////                        test = method.invoke(aClass);
//                    } catch (InvocationTargetException ex) {
//                        Logger.getLogger(HikariExcelEndPoint.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                        System.out.println("Method return :" + );
                        test = method.invoke(aClass, "test");
                    } catch (InvocationTargetException ex) {
                        System.out.println("ini logggggggggggggg");
                        Logger.getLogger(HikariExcelEndPoint.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(HikariExcelEndPoint.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(HikariExcelEndPoint.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HikariExcelEndPoint.class.getName()).log(Level.SEVERE, null, ex);
            }
            Runnable runner = (Runnable) aClass.newInstance();
            runner.run();
            return "Berhasil";
        } catch (IllegalAccessException ex) {
            Logger.getLogger(HikariExcelEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Berhasil";
    }

    @GetMapping("/invoke")
    public Object invoke() {
        Class clazz = SampleClass.class;
        SampleClass sampleClass = new SampleClass();
        Object result = 0;
        try {
            Method method = clazz.getMethod("execute", Integer.class, String.class, Integer.class);
            System.out.println("Method name :" + method.getName());
            try {

                try {
                    result = method.invoke(sampleClass, 1, "+", 5);
                    System.out.println("Result :" + result);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(HikariExcelEndPoint.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Result :" + result);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(HikariExcelEndPoint.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(HikariExcelEndPoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(HikariExcelEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(HikariExcelEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @GetMapping("/getField")
    String getField() {
        SampleClass sampleClass = new SampleClass();
        Class clazz = SampleClass.class;
        try {
            ClassUtils.setField(sampleClass, "operator", "+", true);

            System.out.println("Class :" + ClassUtils.getField(sampleClass, "operator", true));
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(HikariExcelEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(HikariExcelEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(HikariExcelEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";

    private static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    private byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

    @GetMapping("/qr")
    public String generate() {
        try {
            generateQRCodeImage("This is my first QR Code", 350, 350, QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
        return "Berhasil generate";
    }

    @GetMapping("/get-qr")
    public byte[] getImage(HttpServletResponse response) throws IOException, WriterException {
        return getQRCodeImage("jabfjhabfj", 350, 350);
    }

    @RequestMapping(value = "/createCommonQRCode")
    public void createCommonQRCode(HttpServletResponse response, String url) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            //Use tools to generate QR code
            QRCodeUtil.encode(url, stream);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    /**
     * Generate QR code with logo according to url
     */
    @RequestMapping(value = "/createLogoQRCode")
    public void createLogoQRCode(HttpServletResponse response, String url) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            String logoPath = Thread.currentThread().getContextClassLoader().getResource("").getPath()
                    + "templates" + File.separator + "logo.jpg";
            //Use tools to generate QR code
            QRCodeUtil.encode(url, logoPath, stream, true);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    @GetMapping("/test/compile")
    public String compile2() {
        CallCompilerProgrammaticallyExample ccpe = new CallCompilerProgrammaticallyExample();
        String compiledCode = CallCompilerProgrammaticallyExample.compile(
                "function hello() {"
                + "for (i = arr.length; i > 0; i--) {alert(i)};"
                + "}"
                + "hello();");
        return compiledCode;
    }
}
