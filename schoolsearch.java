/*
* @author Sara Bilich and Makenna Johnstone
* @date 1/8/18
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SchoolSearch {
  public static ArrayList<Student> students;
  public static final String FILENAME = "students.txt";
  public static final int STUDENT_SIZE = 8;
  public static String [] userInput;

  public static void main(String args[]) {
    parseStudents();
    promptSearchOptions();
    initiateSearch();
  }

  /*
  * Parses in the students contained within FILENAME and
  * intializes |students|.
  */
  private static void parseStudents() {
    students = new ArrayList<Student>();

    File file = new File(FILENAME);

    try
    {
      Scanner sc = new Scanner(file);

      String line = null;

      while (sc.hasNextLine())
      {
        line = sc.nextLine();

        String[] strs = line.split(",");

        /* parse in student object */
        if (strs.length != STUDENT_SIZE) {
          throw new InvalidStudentException("incorrect number of fields");
        }

        String last = strs[0];
        String first = strs[1];
        int grade = Integer.parseInt(strs[2]);
        int classroom = Integer.parseInt(strs[3]);
        int bus = Integer.parseInt(strs[4]);
        double gpa = Double.parseDouble(strs[5]);
        String tlast = strs[6];
        String tfirst = strs[7];

        /* TODO should I check the strings for being not numbers? */
        Student student = new Student(last, first, grade, classroom, bus,
          gpa, tlast, tfirst);
        students.add(student);
        //System.out.println(student);
      }

      sc.close();
    }
    catch (InvalidStudentException | NumberFormatException e)
    {
      System.out.println("Invalid Student information: " + e);
      System.exit(0);
    }
    catch (FileNotFoundException e)
    {
      System.out.println("Failed to open file: " + e);
      System.exit(0);
    }
    catch (Exception e)
    {
      System.out.println("Scanning failed with exception " + e);
      System.exit(0);
    }
  }

  /*
  * Gives the user a prompt to begin searching the students.
  */
  private static void promptSearchOptions() {
    Scanner sc = new Scanner(System.in);

    System.out.println("Choose to search from the following options:");
    System.out.println("S[tudent]: <lastname> [B[us]]");
    System.out.println("T[eacher]: <lastname>");
    System.out.println("B[us]: <number>");
    System.out.println("G[rade]: <number> [H[igh] | L[ow]]");
    System.out.println("A[verage]: <number>");
    System.out.println("I[nfo]");
    System.out.println("Q[uit]");

    //Splits the input string into an array
    userInput = sc.nextLine().split(" ");
  }

  private static void initiateSearch() {
    char firstLetter = userInput[0].charAt(0);

    switch(firstLetter) {
      case 'S':
        if (userInput.length < 3) {
          searchByLastName(userInput[1]);
        }
        else {
          searchByLastNameFindBus(userInput[1]);
        }
    }
  }
  /*
  * Given a student's last name, find the student's grade, classroom and teacher.
  */
  private static void searchByLastName(String lastName) {
    ArrayList<Student> matchingStudents = new ArrayList<Student>();
    for (Student stu: students) {
      if (stu.getLastName().equals(lastName.toUpperCase())) {
        matchingStudents.add(stu);
      }
    }

    //print out student's grade, classroom and teacher
    for (Student matchingStu: matchingStudents) {
      System.out.println("Grade: " + matchingStu.getGrade());
      System.out.println("Classroom: " + matchingStu.getClassroom());
      System.out.println("Teacher: " + matchingStu.getTFirstName() + " " + matchingStu.getTLastName());
    }
  }
  private static void searchByLastNameFindBus(String lastName) {

  }
}
