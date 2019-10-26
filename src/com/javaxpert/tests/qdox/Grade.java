package com.javaxpert.tests.qdox;

import static org.junit.Assert.*;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import org.junit.Test;

import javax.tools.*;
import java.io.File;
import java.util.*;
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
    private  String sourcePath;

    public Grade(String sourcepath){
        sourcePath=sourcepath;
        System.out.println("Setting up grade object with source path =" + sourcePath);
    }


    /**
     * search a class by name
     * @param name
     * @return  model for this Java class
     */
    private static JavaClass fetchClassByName(String name,JavaProjectBuilder builder){
        System.err.println("--------------------------------------");
        System.err.println("Debug for list of classes");
        System.err.println("--------------------------------------");

        builder.getClasses().stream().forEach(System.err::println);
        System.err.println("Class : " + name + " contained ? = "+ builder.getClasses().contains(name.trim()));
        System.err.flush();
        return builder.getClasses().stream()
                .filter(javaClass -> {
                    System.err.println("Current class is = " + javaClass.toString() + " Canonical  name = " + javaClass.getCanonicalName() + " Simple name : "+ javaClass.getName());
                    System.err.flush();
                    return javaClass.getCanonicalName().contains(name.trim());
                })
                .collect(Collectors.toList()).get(0);
    }

    /**
     *
     * @param name
     * @return
     */
    private static List<JavaMethod> fetchMethodByName(String name, JavaClass clazz){
        System.err.println("fetchMethodByName : " + name + " for class = " + clazz);
        System.err.flush();;
        return clazz.getMethods().stream()
                .filter(javaMethod -> javaMethod.getName().contains(name) )
                .collect(Collectors.toList());
    }


    @Test
    @Mark(5)
    public void isClassesRequiredPresent(){
        // assert that the required classes
        // are available in classpath

        // include University in this list ?
        String[] mandatory_classes={"course.Course","activity.Activity","course.Program","activity.Lecture"};
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
        JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.addSourceTree(new File(sourcePath));
        // setup a Java compiler environent
        // 1- get a compiler
        // 2 - setup files to be compiled
        // 3 - defines a file manager & diagnostics
        // 4 - launch the
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics =
                new DiagnosticCollector<>();



        //builder.getSources().stream().forEach(System.out::println);
        // get all classes fetched by QDOX
        // then filter them (drops all classes for non test code)
        Collection<JavaClass> all_classes = builder.getClasses();

        all_classes.stream().forEach(System.out::println);
        Stream<JavaClass> stream_classes = all_classes.stream();
        Set<JavaClass> filtered_classes =  stream_classes.filter(
                x -> (x.getPackage().getName().startsWith("com.javaxpert")!=true)
        ).collect(Collectors.toSet());


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
    @Mark(5)
    public void isGetTotalCostOk(){
        // setup minimal objects to check methods results
        // uses main program setup to launch the getTotalCost
//        Activity lect1 = new Lecture(16);
//        Activity lect2 = new Lecture(24);
//        Activity lab1 = new Lab(32, 20);
//        Activity lab2 = new Lab(16, 25);
//
//        Course[] courses = new Course[4];
//        courses[0] = new Course("Java programming", lect2, lab1);
//        courses[1] = new Course("Algorithms", lect1);
//        courses[2] = new Course("Open source software", lab1);
//        courses[3] = new Course("Web programming", lect2, lab2);
//
//        Program[] programs = new Program[3];
//        programs[0] = new Program("Program 1", 200, 180);
//        programs[1] = new Program("Program 2", 300, 75);
//        programs[2] = new Program("Program 3", 80, 30);
//
//        for (Course c : courses) {
//            System.out.println(c);
//        }
//        System.out.println();
//
//        for (Program p : programs) {
//            System.out.println("Making program for " + p.getTitle() + " (" + p.getMaxCost() + " hours for "
//                    + p.getNbStudents() + " students)");
//            for (Course c : courses) {
//                boolean added = false;
//                try {
//                    added = p.addCourse(c);
//                } catch (MaxSizeExceededException e) {
//                    System.out.println("Course " + c.getTitle() + " not added - maximum number of courses per program = " + e.getSize());
//                    break;
//                }
//                if (added)
//                    System.out.println("Course '" + c.getTitle() + "' added");
//                else {
//                    System.out.println("Not enough hours left for adding '" + c.getTitle() + "'");
//                }
//            }
//
//            System.out.println();
//        }
//
//        for (Program p : programs) {
//            int totalCost = p.getTotalCost();
//            System.out.println(p.getTitle() + " : cost/student " + totalCost / (double) p.getNbStudents()
//                    + " hours, margin = " + (p.getMaxCost() - totalCost));
//        }


        System.out.println("isGetTotal is over...");
        assertEquals(true,false);
        //Assert.assertEquals(expectedResult,);
    }

    @Test
    @Mark(2)
    public void isExceptionDeclared(){
        JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.addSourceTree(new File(sourcePath));

        JavaClass clazz = fetchClassByName("course.Program",builder);
        JavaMethod method = fetchMethodByName("addCourse", clazz).get(0);
        System.err.println("Listing exceptions fetched for class = "+ clazz.getName());
        method.getExceptions().stream().forEach(System.err::println);

        System.err.flush();
        boolean test_ok = (method.getExceptions().stream()
                .map( javaClass ->  javaClass.getName())
                .filter(name -> "course.MaxSizeExceededException".contains(name))
                .count()==1);
        System.err.println("isExceptionDeclared is over...");
        System.err.flush();;
        assertEquals(true,test_ok);
    }

    @Test
    @Mark(2)
    public void isExceptionClassCreated(){
        JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.addSourceTree(new File(sourcePath));

        JavaClass clazz = fetchClassByName("course.MaxSizeExceededException",builder);
        assertTrue("MaxSizeExceededException should inherit from Exception ",clazz.getSuperClass().getCanonicalName().equals("java.lang.Exception"));
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

    @Test
    @Mark(2)
    /**
     * check if the activity class is declared as abstract
     */
    public void isActivityAbstract(){
        JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.addSourceTree(new File("."));
        JavaClass activity = fetchClassByName("activity.Activity",builder);
        assertTrue("Activity should be declared as abstract",activity.isAbstract());
    }

    @Test
    @Mark(2)
    /**
     * check if the activity class is declared as abstract
     */
    public void isActivityAbstractMethodsOk(){
        JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.addSourceTree(new File("."));
        JavaClass activity = fetchClassByName("activity.Activity",builder);
        JavaMethod getType = fetchMethodByName("getType",activity).get(0);
        JavaMethod  getCost = fetchMethodByName("getCost",activity).get(0);
        assertTrue("Activity should have 2 abstract methods",getCost.isAbstract() && getType.isAbstract());
    }

}
