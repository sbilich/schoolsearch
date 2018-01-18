/*
* @author Sara Bilich and Makenna Johnstone
*
* This class contains functions that allow arrays of Student and Teacher objects
* to be queried.
*/

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchUtils {

  /*
  * Given a student's last name, find the student's grade, classroom and teacher
  * or bus route.
  */
  public static void searchByLastName(String[] userInput,
    ArrayList<Teacher> teachers, ArrayList<Student> students) {
    if (userInput.length < 2) {
      System.out.println("Usage: \"S[tudent]: <lastName> B[us]\"\n");
      return;
    }
    String lastName = userInput[1];

    ArrayList<Student> matchingStudents = new ArrayList<Student>();
    for (Student stu: students) {
      if (stu.getLastName().equals(lastName.toUpperCase())) {
        matchingStudents.add(stu);
      }
    }

    if (!matchingStudents.isEmpty()) {
      if (userInput.length >= 3) {
        if (userInput[2].equals("B") || userInput[2].equals("Bus")) {
          for (Student matchingStu: matchingStudents) {
            System.out.println("Student Name: " + matchingStu.getLastName()
              + ", " + matchingStu.getFirstName());
            System.out.println("Bus Route: " + matchingStu.getBus() + "\n");
          }
        } else {
          System.out.println("Expected \"B[us]\", got "
            + userInput[2] + "\n");
        }
      }
      else {
        for (Student matchingStu: matchingStudents) {
          Teacher teacher = matchingStu.findTeacher(teachers);
          if (teacher == null) {
            System.out.println("No teacher found for"
            + " student with last name" + matchingStu.getLastName() + "\n");
            return;
          }

          System.out.println("Student Name: " + matchingStu.getLastName() + ", "
            + matchingStu.getFirstName());
          System.out.println("Grade: " + matchingStu.getGrade());
          System.out.println("Classroom: " + matchingStu.getClassroom());
          System.out.println("Teacher Name: " + teacher.getLastName()
            + ", " + teacher.getFirstName() + "\n");
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
  public static void searchByTeacher(String[] input,
   ArrayList<Teacher> teachers, ArrayList<Student> students) {
    if (input.length < 2) {
      System.out.println("Usage: \"T[eacher]: <lastName>\"\n");
      return;
    }

    String tLastName = input[1];
    Teacher teacher = Teacher.findTeacher(tLastName, teachers);
    if (teacher == null) { System.out.println("No teachers were found " +
    "matching the last name \"" + tLastName + "\"\n"); return; }

    ArrayList<Student> matchingStudents = new ArrayList<Student>();

    for (Student stu: students) {
      if (teacher.getClassroom() == stu.getClassroom()) {
        matchingStudents.add(stu);
      }
    }

    if (!matchingStudents.isEmpty()) {
      for (Student matchingStu: matchingStudents) {
        System.out.println("Student: " + matchingStu.getLastName()
          + ", " + matchingStu.getFirstName() + "\n");
      }
    } else {
        System.out.println("No students were found with a teacher "
        + "matching the last name \"" + tLastName + "\"\n");
    }
  }

  /*
  * Given a bus route, find the list of students who take it
  */
  public static void searchByBus(String[] userInput,
   ArrayList<Student> students) {
    if (userInput.length < 2) {
      System.out.println("Usage: \"B[us]: <Number>\"\n");
      return;
    }

    String bus = userInput[1];
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
  public static void searchByGrade(String[] userInput,
   ArrayList<Teacher> teachers, ArrayList<Student> students) {
    if (userInput.length < 2) {
      System.out.println("Usage: \"G[rade]: <Number>\"\n");
      return;
    }

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
      String flag = userInput[2].toUpperCase();
      if (flag.equals("HIGH") || flag.equals("H")) {
        findHighestGpa(matchingStudents, teachers, grade);
      }
      else if (flag.equals("LOW") || flag.equals("L")) {
        findLowestGpa(matchingStudents, teachers, grade);
      } else if (flag.equals("TEACHER") || flag.equals("T")) {
        findTeachersForGrade(students, teachers, grade);
      } else {
        System.out.println("Unrecognized flag \""
          + flag + "\"\n");
      }
    }
  }

  /*
  * Finds and prints out all teachers that teach |grade|.
  */
  public static void findTeachersForGrade(ArrayList<Student> students,
   ArrayList<Teacher> teachers, String grade) {
     ArrayList<Student> grade_students = findStudentsInGrade(grade, students);
     Set<Teacher> matched = new HashSet<Teacher>();

     for (Student s : grade_students) {
       int num = s.getClassroom();

       for (Teacher t : teachers) {
         if (t.getClassroom() == num) {
           matched.add(t);
         }
       }
     }

     if (!matched.isEmpty()) {
       for (Teacher t : matched) {
         System.out.println("Teacher Name: " + t.getLastName()
           + ", " + t.getFirstName() + "\n");
       }
     } else {
       System.out.println("No teachers were found that teach the grade \""
         + grade + "\"\n");
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
  public static void findHighestGpa(ArrayList<Student> matchingStudents,
   ArrayList<Teacher> teachers, String grade) {
    if (!matchingStudents.isEmpty()) {
      Student highestGpaStu = matchingStudents.get(0);
      for (Student stu: matchingStudents) {
        if (stu.getGpa() > highestGpaStu.getGpa())
          highestGpaStu = stu;
      }

      printExtremeGpaStu(highestGpaStu, teachers);
    } else {
      System.out.println("No students were found with the grade \""
        + grade + "\"\n");
    }
  }

  /*
  * Finds student with the lowest gpa in a specified grade
  */
  public static void findLowestGpa(ArrayList<Student> matchingStudents,
   ArrayList<Teacher> teachers, String grade) {
    if (!matchingStudents.isEmpty()) {
      Student lowestGpaStu = matchingStudents.get(0);
      for (Student stu: matchingStudents) {
        if (stu.getGpa() < lowestGpaStu.getGpa())
          lowestGpaStu = stu;
      }

      printExtremeGpaStu(lowestGpaStu, teachers);
    } else {
      System.out.println("No students were found with the grade \""
        + grade + "\"\n");
    }
  }

  /*
  * Prints out information about the student with the highest or lowest GPA
  */
  public static void printExtremeGpaStu(Student stu,
   ArrayList<Teacher> teachers) {
    Teacher t = stu.findTeacher(teachers);
    if (t == null) {
      System.out.println("No teacher found for"
      + " student with last name" + stu.getLastName() + "\n");
      return;
    }

    System.out.println("Student Name: " + stu.getLastName()
      + ", " + stu.getFirstName());
    System.out.println("GPA: " + stu.getGpa());
    System.out.println("Teacher Name: " + t.getLastName()
      + ", " + t.getFirstName() + "\n");
  }

  /*
  * Computes the average GPA for the specified grade
  */
  public static void findAverage(String[] input, ArrayList<Student> students) {
    if (input.length < 2) {
      System.out.println("Usage: \"A[verage]: <Number>\"\n");
      return;
    }

    String grade = input[1];
    ArrayList<Student> matchingStudents = findStudentsInGrade(grade, students);

    if (!matchingStudents.isEmpty()) {
      double total = 0, average = 0;

      /* find the total gpa for all students in specified grade */
      for (Student stu : matchingStudents) {
        total += stu.getGpa();
      }
      //TODO: should the average value be rounded?
      average = total / matchingStudents.size();

      System.out.println("Average for grade " + grade + ": " + average + "\n");
    } else {
      System.out.println("No students were found with the grade \""
        + grade + "\"\n");
    }
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
    System.out.print("\n");
  }

  /*
  * Given a classroom number, you can either list all students assigned
  * to it or find the teacher teaching in it.
  */
  public static void searchByClassroom(String[] userInput,
   ArrayList<Teacher> teachers, ArrayList<Student> students) {
    int num = Integer.parseInt(userInput[1]);

    if (userInput.length > 2) {
      if (userInput[2].equals("T") || userInput[2].equals("Teacher")) {
        for (Teacher t : teachers) {
          if (t.getClassroom() == num) {
            System.out.println("Teacher Name: " + t.getLastName()
              + ", " + t.getFirstName());
          }
        }
      }
    } else {
      for (Student s : students) {
        if (s.getClassroom() == num) {
          System.out.println("Student Name: " + s.getLastName()
            + ", " + s.getFirstName());
        }
      }
    }
  }


  /*
  * Prints out the enrollments broken down by classroom
  * (ie., output a list of classrooms ordered by classroom number,
  * with a total number of students in each of the classrooms)
  */
  public static void reportEnrollment(ArrayList<Student> students) {

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
