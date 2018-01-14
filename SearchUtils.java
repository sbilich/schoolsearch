import java.util.ArrayList;

public class SearchUtils {

    /*
    * Given a student's last name, find the student's grade, classroom and teacher
    * or bus route.
    */
    public static void searchByLastName(String [] userInput,
     ArrayList<Student> students) {
      String lastName = userInput[1];

      ArrayList<Student> matchingStudents = new ArrayList<Student>();
      for (Student stu: students) {
        if (stu.getLastName().equals(lastName.toUpperCase())) {
          matchingStudents.add(stu);
        }
      }

      if (!matchingStudents.isEmpty()) {
        for (Student matchingStu: matchingStudents) {

          if (userInput.length >= 3) {
            if (userInput[2].equals("B") || userInput[2].equals("Bus")) {
              System.out.println("Student Name: " + matchingStu.getLastName()
                + ", " + matchingStu.getFirstName());
              System.out.println("Bus Route: " + matchingStu.getBus() + "\n");
            } else {
              System.out.println("Expected \"B[us]\", got "
                + userInput[2] + "\n");
            }
          }
          else {
            System.out.println("Student Name: " + matchingStu.getLastName() + ", "
              + matchingStu.getFirstName());
            System.out.println("Grade: " + matchingStu.getGrade());
            System.out.println("Classroom: " + matchingStu.getClassroom());
            System.out.println("Teacher Name: " + matchingStu.getTLastName()
              + ", " + matchingStu.getTFirstName() + "\n");
          }
        }
      } else {
        System.out.println("No students were found matching the last name \""
          + lastName + "\"\n");
      }
    }

    /*
    * Given a teacher, find the list of students in his/her class
    */
    public static void searchByTeacher(String tLastName,
     ArrayList<Student> students) {
      ArrayList<Student> matchingStudents = new ArrayList<Student>();

      for (Student stu: students) {
        if (stu.getTLastName().equals(tLastName.toUpperCase())) {
          matchingStudents.add(stu);
        }
      }

      if (!matchingStudents.isEmpty()) {
        for (Student matchingStu: matchingStudents) {
          System.out.println("Student: " + matchingStu.getLastName()
            + ", " + matchingStu.getFirstName());
        }
      } else {
          System.out.println("No teachers were found matching the last name \""
            + tLastName + "\"\n");
      }
    }

    /*
    * Given a bus route, find the list of students who take it
    */
    public static void searchByBus(String bus, ArrayList<Student> students) {
      ArrayList<Student> matchingStudents = new ArrayList<Student>();
      for (Student stu: students) {
        if (stu.getBus() == Integer.parseInt(bus)) {
          matchingStudents.add(stu);
        }
      }

      if (!matchingStudents.isEmpty()) {
        for (Student matchingStu: matchingStudents) {
          System.out.println("Student Name: " + matchingStu.getLastName()
            + ", " + matchingStu.getFirstName());
          System.out.println("Grade: " + matchingStu.getGrade());
          System.out.println("Classroom: " + matchingStu.getClassroom() + "\n");
        }
      } else {
        System.out.println("No students were found with the bus route \""
          + bus + "\"\n");
      }
    }

    /*
    * Find all students at a specified grade level
    */
    public static void searchByGrade(String [] userInput,
     ArrayList<Student> students) {
      String grade = userInput[1];
      ArrayList<Student> matchingStudents = findStudentsInGrade(grade, students);

      if (userInput.length < 3) { /* Only return the GPA */
        if (!matchingStudents.isEmpty()) {
          for (Student matchingStu: matchingStudents) {
            System.out.println("Student Name: " + matchingStu.getLastName()
              + ", " + matchingStu.getFirstName() + "\n");
          }
        } else {
          System.out.println("No students were found with the grade \""
            + grade + "\"\n");
        }
      }
      else {
        String gpaRank = userInput[2].toUpperCase();
        if (gpaRank.equals("HIGH") || gpaRank.equals("H")) {
          findHighestGpa(matchingStudents);
        }
        else if (gpaRank.equals("LOW") || gpaRank.equals("L")) {
          findLowestGpa(matchingStudents);
        } else {

        }
      }
    }

    /*
    * Gather all of the students at the specified grade level
    */
    public static ArrayList<Student> findStudentsInGrade(String grade,
     ArrayList<Student> students) {
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
    public static void findHighestGpa(ArrayList<Student> matchingStudents) {
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
    public static void findLowestGpa(ArrayList<Student> matchingStudents) {

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
    public static void printExtremeGpaStu(Student stu) {
      System.out.println(stu.getLastName() + ", " + stu.getFirstName()
            + " " + stu.getGpa() + " " + stu.getTLastName() + ", " +
            stu.getTFirstName());
    }

    /*
    * Computes the average GPA for the specified grade
    */
    public static void findAverage(String grade, ArrayList<Student> students) {
      ArrayList<Student> matchingStudents = findStudentsInGrade(grade, students);
      double total = 0, average = 0;

      /* find the total gpa for all students in specified grade */
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
    public static void getInfo(ArrayList<Student> students) {
      int numStudentsInGrade;
      for (int i = 1; i <= 6; i++) {
        numStudentsInGrade =
          findStudentsInGrade(Integer.toString(i), students).size();
        System.out.println(i + ": " + numStudentsInGrade);
      }
    }

  /*
  * Gives the user a prompt to begin searching the students.
  */
  public static void promptSearchOptions() {
    System.out.println("Choose to search from the following options:");
    System.out.println("S[tudent]: <lastname> [B[us]]");
    System.out.println("T[eacher]: <lastname>");
    System.out.println("B[us]: <number>");
    System.out.println("G[rade]: <number> [H[igh] | L[ow]]");
    System.out.println("A[verage]: <number>");
    System.out.println("I[nfo]");
    System.out.println("Q[uit]\n");
  }
}
