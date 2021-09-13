/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;


import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.SourceFile;

/**
 * An example of how to call the Closure Compiler programmatically.
 *
 * @author bolinfest@gmail.com (Michael Bolin)
 */
public class CallCompilerProgrammaticallyExample {

  /**
   * @param code JavaScript source code to compile.
   * @return The compiled version of the code.
   */
  public static String compile(String code) {
    Compiler compiler = new Compiler();

    CompilerOptions options = new CompilerOptions();
    // Advanced mode is used here, but additional options could be set, too.
    CompilationLevel.ADVANCED_OPTIMIZATIONS.setOptionsForCompilationLevel(
        options);

    // To get the complete set of externs, the logic in
    // CompilerRunner.getDefaultExterns() should be used here.
      SourceFile extern = SourceFile.fromCode("externs.js",
        "function alert(x) {}");

    // The dummy input name "input.js" is used here so that any warnings or
    // errors will cite line numbers in terms of input.js.
    SourceFile input = SourceFile.fromCode("input.js", code);

    // compile() returns a Result, but it is not needed here.
    compiler.compile(extern, input, options);

    // The compiler is responsible for generating the compiled code; it is not
    // accessible via the Result.
    return compiler.toSource();
  }

  public static void main(String[] args) {
    String compiledCode = compile(
        "function hello(name) {" +
          "alert('Hello guys, ' + name);" +
        "}" +
        "hello('Hikari');");
    System.out.println(compiledCode);
  }

}