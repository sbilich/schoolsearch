/*
* @author Sara Bilich and Makenna Johnstone
* @date 1/8/18
*/

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class schoolsearch {
  public static ArrayList<Student> students;
  public static ArrayList<Teacher> teachers;
  public static final String FILENAME = "students.txt";
  public static final int STUDENT_SIZE = 8;

  public static void main(String args[]) {
    parseStudents();
    parseTeachers();
    SearchUtils.promptSearchOptions();
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

        Student student = new Student(last, first, grade, classroom, bus,
          gpa, tlast, tfirst);
        students.add(student);
      }

      sc.close();
    }
    catch (InvalidStudentException | NumberFormatException e)
    {
      System.out.println("Invalid students file: " + e);
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
  * Continuously prompts the user for a search operation
  * and performs the specified operation.
  */
  private static void initiateSearch() {
    Scanner sc = new Scanner(System.in);
    String [] userInput;

    while (sc.hasNext()) {
      userInput = sc.nextLine().split(" ");
      switch (userInput[0]) {
        case "S:":
        case "Student:":
          SearchUtils.searchByLastName(userInput, students);
          break;
        case "T:":
        case "Teacher:":
            SearchUtils.searchByTeacher(userInput, students);
            break;
        case "B:":
        case "Bus:":
            SearchUtils.searchByBus(userInput, students);
            break;
        case "G:":
        case "Grade:":
            SearchUtils.searchByGrade(userInput, students);
            break;
        case "A:":
        case "Average:":
            SearchUtils.findAverage(userInput, students);
            break;
        case "I":
        case "Info":
            SearchUtils.getInfo(students);
            break;
        case "Q":
        case "Quit":
            System.exit(0);
        default:
            System.out.println("Unrecognized query, please try again.\n");
            break;
      }
      System.out.println("Choose another search operation: \n");
    }
  }
}
