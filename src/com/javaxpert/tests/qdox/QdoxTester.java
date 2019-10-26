package com.javaxpert.tests.qdox;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;

import java.io.File;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QdoxTester {

    public static void main(String[] args){
        JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.addSourceTree(new File(
                "."
                //"~/IdeaProjects/TestQdox/src"
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

        // dum classes filtered
        filtered_classes.stream().forEach(System.out::println);

    }
}
