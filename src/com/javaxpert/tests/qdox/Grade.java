package com.javaxpert.tests.qdox;

import static org.junit.Assert.*;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import org.junit.Test;

import javax.tools.*;
import java.io.File;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public void isClassesRequiredPresent(){
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
        System.out.println("classes present control finished...");
    }

    @Test
    @Mark(5)
    public  void isCompilationOk(){
        // setup a Java compiler environent
        // 1- get a compiler
        // 2 - setup files to be compiled
        // 3 - defines a file manager & diagnostics
        // 4 - launch the
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics =
                new DiagnosticCollector<>();

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


        // @TODO : does not work on windows
        Set<File> java_files = filtered_classes.stream()
                .map( java -> new File(java.getSource().getURL().getPath()))
                .collect(Collectors.toSet());

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics,null,null);
        Iterable<? extends JavaFileObject> javaFileObjects =
                fileManager.getJavaFileObjects(java_files.toArray(new File[java_files.size()]));
        JavaCompiler.CompilationTask task = compiler.getTask(
                null, fileManager, diagnostics, null,
                null, javaFileObjects);
        System.out.println("Compiling : "+ java_files.size() + " Java source files");
        Boolean compil_result = task.call();
        System.out.println("Compilation succeeded ?"+ compil_result);
        assertTrue("Compilation succeeded?",compil_result==true);

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
