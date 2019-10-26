package com.javaxpert.tests.qdox;

//import org.jooq.lambda.Unchecked;
import io.vavr.control.Try;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

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
        Arrays.asList(methods).stream().forEach(System.out::println);
        int total;
        total = Arrays.asList(methods).stream()
                .filter( m -> m.isAnnotationPresent(Mark.class) && m.isAnnotationPresent(Test.class))

              //  .filter(m -> Arrays.asList(m.getDeclaredAnnotations()).contains(Mark.class))
                .map(method -> {
                    Try.of( () ->{
                        System.out.println("invoking method =" + method.getName() );
                        method.invoke(grade);
                        return true;
                    } );
                            int value = method.getDeclaredAnnotation(Mark.class).value();
                            return Optional.of(value);
                }
                )
                .filter( option -> option.isPresent())
                .map( option -> option.get() )
                .map(Integer.class::cast)
                .reduce( 0, (val1, val2) -> {
                    return val1 + val2;
                });
        System.out.println("Total computed for grade = " + total );
    }
}
