package com.javaxpert.tests.qdox;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;

import javax.tools.*;
import java.io.File;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QdoxTester {

    public static void main(String[] args) throws Exception{
        JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.addSourceTree(new File(
                "."
        ));

        builder.getSources().stream().forEach(System.out::println);
        // get all classes fetched by QDOX
        // then filter them (drops all classes for non test code)
        Collection<JavaClass> all_classes = builder.getClasses();

        all_classes.stream().forEach(System.out::println);
        Stream<JavaClass> stream_classes = all_classes.stream();
        Set<JavaClass> filtered_classes =  stream_classes.filter(
                x -> (x.getPackage().getName().startsWith("com.javaxpert")!=true)
        ).collect(Collectors.toSet());

        // dump classes filtered
        filtered_classes.stream().forEach(System.out::println);

        // @TODO : does not work on windows
        Set<File> java_files = filtered_classes.stream()

                .map(clazz -> new File(clazz.getSource().getURL().getPath()))
                .collect(Collectors.toSet());

        // setup a Java compiler environent
        // 1- get a compiler
        // 2 - setup files to be compiled
        // 3 - defines a file manager & diagnostics
        // 4 - launch the
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics =
                new DiagnosticCollector<>();

        StandardJavaFileManager  fileManager = compiler.getStandardFileManager(diagnostics,null,null);
        Iterable<? extends JavaFileObject> javaFileObjects =
                fileManager.getJavaFileObjects(java_files.toArray(new File[java_files.size()]));
        JavaCompiler.CompilationTask task = compiler.getTask(
                null, fileManager, diagnostics, null,
                null, javaFileObjects);
        Future<Boolean> future = Executors.newFixedThreadPool(1).submit(task);
        Boolean result = future.get();
        System.out.println("Compilation succeeded ?"+ result);

        diagnostics.getDiagnostics().stream().forEach(System.err::println);

    }
}
