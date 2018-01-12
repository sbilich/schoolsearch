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
    System.out.println("Choose to search from the following options:");
    System.out.println("S[tudent]: <lastname> [B[us]]");
    System.out.println("T[eacher]: <lastname>");
    System.out.println("B[us]: <number>");
    System.out.println("G[rade]: <number> [H[igh] | L[ow]]");
    System.out.println("A[verage]: <number>");
    System.out.println("I[nfo]");
    System.out.println("Q[uit]");
  }

  /*
  * Continuously prompts the user for a search operation and performs the specified
  * operation. 
  */
  private static void initiateSearch() {
    Scanner sc = new Scanner(System.in);
    String [] userInput;

    while (sc.hasNext()) {
      userInput = sc.nextLine().split(" ");
      char firstLetter = userInput[0].charAt(0);

      switch (firstLetter) {
        case 'S':
          searchByLastName(userInput);
            break;
        case 'T':
            searchByTeacher(userInput[1]);
            break;
        case 'B':
            searchByBus(userInput[1]);
            break;
        case 'G':
            searchByGrade(userInput);
            break;
        case 'A':
            findAverage(userInput[1]);
            break;
        case 'I':
            getInfo();
            break;
        case 'Q':
            System.exit(0);
        default:
            break;
      }
    System.out.println("\nChoose another search operation: ");
    }
  }

  /*
  * Given a student's last name, find the student's grade, classroom and teacher
  * or bus route.
  */
  private static void searchByLastName(String [] userInput) {
    String lastName = userInput[1];
    ArrayList<Student> matchingStudents = new ArrayList<Student>();
    for (Student stu: students) {
      if (stu.getLastName().equals(lastName.toUpperCase())) {
        matchingStudents.add(stu);
      }
    }

    for (Student matchingStu: matchingStudents) {
      System.out.print(matchingStu.getLastName() + ", " + matchingStu.getFirstName() + " ");
      if (userInput.length >= 3) {
        System.out.println(matchingStu.getBus());
      }
      else {
        System.out.println(matchingStu.getGrade() + " " + matchingStu.getClassroom()
              + " " + matchingStu.getTLastName() + ", " + matchingStu.getTFirstName());
      }
    }
  }

  /*
  * Given a teacher, find the list of students in his/her class
  */
  private static void searchByTeacher(String tLastName) {
    ArrayList<Student> matchingStudents = new ArrayList<Student>();
    for (Student stu: students) {
      if (stu.getTLastName().equals(tLastName.toUpperCase())) {
        matchingStudents.add(stu);
      }
    }

    for (Student matchingStu: matchingStudents) {
      System.out.println(matchingStu.getLastName() + ", " + matchingStu.getFirstName());
    }
  }

  /*
  * Given a bus route, find the list of students who take it
  */
  private static void searchByBus(String bus) {
    ArrayList<Student> matchingStudents = new ArrayList<Student>();
    for (Student stu: students) {
      if (stu.getBus() == Integer.parseInt(bus)) {
        matchingStudents.add(stu);
      }
    }

    for (Student matchingStu: matchingStudents) {
      System.out.println(matchingStu.getLastName() + ", " + matchingStu.getFirstName()
            + " " + matchingStu.getGrade() + " " + matchingStu.getClassroom());
    }
  }

  /*
  * Find all students at a specified grade level
  */
  private static void searchByGrade(String [] userInput) {
    String grade = userInput[1];
    ArrayList<Student> matchingStudents = findStudentsInGrade(grade);

    //Only return the GPA
    if (userInput.length < 3) {
      for (Student matchingStu: matchingStudents) {
        System.out.println(matchingStu.getLastName() + ", " + matchingStu.getFirstName());
      }
    }
    else {
      char gpaRank = Character.toUpperCase(userInput[2].charAt(0));
      if (gpaRank == 'H') {
        findHighestGpa(matchingStudents);
      }
      else {
        findLowestGpa(matchingStudents);
      }
    }
  }

  /*
  * Gather all of the students at the specified grade level
  */
  private static ArrayList<Student> findStudentsInGrade(String grade) {
    ArrayList<Student> matchingStudents = new ArrayList<Student>();
    for (Student stu : students) {
      if (stu.getGrade() == Integer.parseInt(grade)) {
        matchingStudents.add(stu);
      }
    }
    return matchingStudents;
  }

  /*
  * Finds student with the highest gpa in a specified grade
  */
  private static void findHighestGpa(ArrayList<Student> matchingStudents) {
    Student highestGpaStu = matchingStudents.get(0);
    for (Student stu: matchingStudents) {
      if (stu.getGpa() > highestGpaStu.getGpa())
        highestGpaStu = new Student(stu);
    }
    printExtremeGpaStu(highestGpaStu);
  }
  /*
  * Finds student with the lowest gpa in a specified grade
  */
  private static void findLowestGpa(ArrayList<Student> matchingStudents) {

    Student lowestGpaStu = matchingStudents.get(0);
    for (Student stu: matchingStudents) {
      if (stu.getGpa() < lowestGpaStu.getGpa())
        lowestGpaStu = new Student(stu);
    }
    printExtremeGpaStu(lowestGpaStu);
  }
  /*
  * Prints out information about the student with the highest or lowest GPA
  */
  private static void printExtremeGpaStu(Student stu) {
    System.out.println(stu.getLastName() + ", " + stu.getFirstName()
          + " " + stu.getGpa() + " " + stu.getTLastName() + ", " +
          stu.getTFirstName());
  }

  /*
  * Computes the average GPA for the specified grade
  */
  private static void findAverage(String grade) {
    ArrayList<Student> matchingStudents = findStudentsInGrade(grade);
    double total = 0, average = 0;

    //find the total gpa for all students in specified grade
    for (Student stu : matchingStudents) {
      total += stu.getGpa();
    }
    //TODO: should the average value be rounded?
    average = total / matchingStudents.size();

    System.out.println(grade + " " + average);
  }

  /*
  * Prints number of students in grades 0-6
  */
  private static void getInfo() {
    int numStudentsInGrade;
    for (int i = 1; i <= 6; i++) {
      numStudentsInGrade = findStudentsInGrade(Integer.toString(i)).size();
      System.out.println(i + ": " + numStudentsInGrade);
    }
  }
}
