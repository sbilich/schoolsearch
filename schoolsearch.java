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

  public static void main(String args[]) {
    parseStudents();
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
}
