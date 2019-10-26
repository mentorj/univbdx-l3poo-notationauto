package com.javaxpert.tests.qdox;

import static org.junit.Assert.*;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import org.junit.Test;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class as a tests suite and computes a rating for the evaluated
 * source code using marks associated with each test.
 * @author J.MOLIERE - October 2019
 */
public class Grade {
    private final static String[] EXAM_CLASSES_CHECKED=
            {"Course","Program","Lecture","Activity","Lab","MaxSizeExceededException"};

    @Test
    @Mark(5)
    public void isCompilationOk(){
        // assert that the required classes
        // are available in classpath

        // include University in this list ?
        String[] mandatory_classes={"com.foo.foobar.Foo1","com.foo.foobar.MyException"};//{"Course","Activity","Program","Lecture"};
        List<String> mandatory_classes_list = Arrays.asList(mandatory_classes);
        Set<Optional<Class>> classes_in_classpath = mandatory_classes_list.stream()
                .map( classname -> Utils.loadClazz(classname))
                        .filter(o -> o.isPresent() )
        .collect(Collectors.toSet());
        assertEquals(mandatory_classes_list.size() ,classes_in_classpath.size());
        System.out.println("isCompilationOk() is over...");
    }

    @Test
    @Mark(10)
    public void isExecutionOk(){
        System.out.println("isExecutionOk() is over...");
        assertEquals(true,false);
        //Assert.assertEquals(expectedResult,);
    }

    @Test
    @Mark(5)
    public void isExceptionCodeCorrect(){
        System.out.println("isExceptionCodecorrect is over...");
        assertEquals(true,true);
    }

    @Test
    @Mark(5)
    /**
     * check imports & packages
     */
    public void isImportsOk(){
        // read all classes from this project
        Set<String> expected_classes = new TreeSet();
        JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.addSourceTree(new File("."));

        // keep the only classes listed as checked by this program
        Collection<JavaClass> all_classes = builder.getClasses();
        Set<JavaClass> filtered_classes =  all_classes.stream().filter(
                clazz -> Arrays.asList(EXAM_CLASSES_CHECKED).contains(clazz.getName())
        ).collect(Collectors.toSet());



        // watch imports for these classes
        //JavaClass exception = filtered_classes.("MaxSizeExceededException")
    }
}
