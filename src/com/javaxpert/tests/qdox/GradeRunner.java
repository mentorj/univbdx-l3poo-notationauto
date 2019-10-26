package com.javaxpert.tests.qdox;

//import org.jooq.lambda.Unchecked;
import io.vavr.control.Try;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * launches the grade computation..
 * runs all tests and computes the grade..
 */
public class GradeRunner {
    public static void main(String[] args){
        Grade grade = new Grade();
        grade.isCompilationOk();
        Class grade_class = grade.getClass();
        Method[] methods = grade_class.getDeclaredMethods();
        System.out.println("----------------------------------------\n Methods declared \n--------------");


        // filter the list of methods to the only test methods compatible with grading
        int total=0;
        Set<Method> compatible_methods = Arrays.asList(methods)
                .stream()
                .filter( m -> m.isAnnotationPresent(Mark.class) && m.isAnnotationPresent(Test.class))
                .collect(Collectors.toSet());

        for(Method m : compatible_methods){
            try {
                System.out.println("invoking test =" + m.getName());
                m.invoke(grade);
                Mark mark = m.getDeclaredAnnotation(Mark.class);
                total+=mark.value();
            }catch(Exception e){
                System.err.println("Exeption occured : " + e.getMessage()+ " while invokinng :"+ m.getName()+ "\n"+ e.toString());

            }
        }

        System.out.println("Total computed for grade = " + total );
    }
}
