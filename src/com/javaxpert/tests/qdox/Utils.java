package com.javaxpert.tests.qdox;

import java.lang.reflect.Method;
import java.util.Optional;

public class Utils {
    /**
     * helper method for loading class
     * @param classname
     * @return
     */
    public static Optional<Class> loadClazz(String classname){
        Optional<Class> result ;
        Class clazz = null;
        try{
            clazz = Class.forName(classname);
            result=Optional.of(clazz);
        }catch(Exception e){
            result=Optional.empty();
        }
        return result;
    }



}
