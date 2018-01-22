/*
* @author Sara Bilich and Makenna Johnstone
*
* This class contains functions that allow arrays of Student and Teacher objects
* to be queried.
*/

import java.util.*;


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
      double average = getAverage(matchingStudents);

      System.out.println("Average for grade " + grade + ": " + average + "\n");
    } else {
      System.out.println("No students were found with the grade \""
        + grade + "\"\n");
    }
  }

  private static double getAverage(ArrayList<Student> students) {
    double total = 0, average = 0;

    /* find the total gpa for all students in specified grade */
    for (Student stu : students) {
      total += stu.getGpa();
    }

    average = total / students.size();
    return average;
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
    boolean validClassroom = false;
    int num = Integer.parseInt(userInput[1]);

    if (userInput.length > 2) {
      if (userInput[2].equals("T") || userInput[2].equals("Teacher")) {
        for (Teacher t : teachers) {
          if (t.getClassroom() == num) {
            validClassroom = true;
            System.out.println("Teacher Name: " + t.getLastName()
              + ", " + t.getFirstName() + "\n");
          }
        }
        if (!validClassroom) {
          System.out.println("There are no teachers in classroom: " + num + "\n");
        }
      }
    } else {
      for (Student s : students) {
        if (s.getClassroom() == num) {
          validClassroom = true;
          System.out.println("Student Name: " + s.getLastName()
            + ", " + s.getFirstName() + "\n");
        }
      }
      if (!validClassroom) {
        System.out.println("There are no students in classroom: " + num + "\n");
      }
    }

  }

  /*
  * Prints out the enrollments broken down by classroom
  * (ie., output a list of classrooms ordered by classroom number,
  * with a total number of students in each of the classrooms)
  */
  public static void reportEnrollment(ArrayList<Student> students) {
    TreeMap<Integer, Integer> rooms = new TreeMap<Integer, Integer>();

    for (Student s : students) {
      int room = s.getClassroom();
      if (rooms.containsKey(room)) {
        Integer i = rooms.get(room);
        rooms.put(room, i + 1);
      } else {
        rooms.put(room, 1);
      }
    }

    for (Map.Entry<Integer, Integer> i : rooms.entrySet()) {
      System.out.println("Classroom " + i.getKey() + ": " + i.getValue() +
        " students enrolled\n");
    }
  }

  private static TreeMap<String, ArrayList<Student>> getStudentsByTeacher(
   ArrayList<Student> students, ArrayList<Teacher> teachers) {
    TreeMap<String, ArrayList<Student>> m =
     new TreeMap<String, ArrayList<Student>>();

    for (Student s : students) {
      Teacher t = s.findTeacher(teachers);
      String name = t.getLastName() + "," + t.getFirstName();

      ArrayList<Student> lis = null;
      if (!m.containsKey(name)) {
        lis = new ArrayList<Student>();
      } else {
        lis = m.get(name);
      }
      lis.add(s);
      m.put(name, lis);
    }

    return m;
  }

  private static TreeMap<String, ArrayList<Student>> getStudentsByGrade(
   ArrayList<Student> students) {
    TreeMap<String, ArrayList<Student>> m =
      new TreeMap<String, ArrayList<Student>>();

    for (Student s : students) {
      String grade = s.getGrade() + "";
      ArrayList<Student> lis = null;
      if (!m.containsKey(grade)) {
        lis = new ArrayList<Student>();
      } else {
        lis = m.get(grade);
      }
      lis.add(s);
      m.put(grade, lis);
    }

    return m;
  }

  private static TreeMap<String, ArrayList<Student>> getStudentsByBus(
   ArrayList<Student> students) {
    TreeMap<String, ArrayList<Student>> m =
      new TreeMap<String, ArrayList<Student>>();

    for (Student s : students) {
      String bus = s.getBus() + "";
      ArrayList<Student> lis = null;
      if (!m.containsKey(bus)) {
        lis = new ArrayList<Student>();
      } else {
        lis = m.get(bus);
      }
      lis.add(s);
      m.put(bus, lis);
    }

    return m;
  }

  /*
  * Returns the performance based on the specified factor {[G[rade]], [T[eacher]]
  * [B[us]]}. Also accepts the optional flag [L[ist]]. Refer to the README for
  * more in-depth functional details.
  */
  public static void getPerformance(String[] userInput,
   ArrayList<Teacher> teachers, ArrayList<Student> students) {
     if (userInput.length > 3) {
       System.out.println("Usage: \"P[erformance]: [G[rade]] [T[eacher]]" +
        " [B[us]] [L[ist]]\"\n");
       return;
     }

     /* check if the "List" flag is specified */
     boolean list = false;
     if (userInput.length == 2) {
       if (userInput[1].equals("L") || userInput[1].equals("List")) {
         list = true;
       }
     } else if (userInput.length == 3) {
       if (userInput[2].equals("L") || userInput[2].equals("List")) {
         list = true;
       }
     }

     TreeMap<String, ArrayList<Student>> matched;
     if (userInput.length == 1 || userInput[1].equals("G")
      || userInput[1].equals("Grade") || (userInput.length == 2 && list)) { /* defualt behavior */
        matched = getStudentsByGrade(students);
      } else if (userInput[1].equals("T") || userInput[1].equals("Teacher")) {
        matched = getStudentsByTeacher(students, teachers);
      } else if (userInput[1].equals("B") || userInput[1].equals("Bus")) {
        matched = getStudentsByBus(students);
      } else { /* invalid flags, output usage */
        System.out.println("Usage: \"P[erformance]: [G[rade]] [T[eacher]]" +
         " [B[us]] [L[ist]]\"\n");
        return;
      }

      outputPerformance(matched, list);
  }

  private static void outputPerformance(
   TreeMap<String, ArrayList<Student>> matched, boolean list) {
     if (matched.isEmpty()) {
       System.out.println("No students were found matching the given criteria\n");
     } else {
       for (Map.Entry<String, ArrayList<Student>> e : matched.entrySet()) {
         double average = getAverage(e.getValue());
         System.out.println(e.getKey() + ": " + average + "\n");

         if (list) {
           for (Student s : e.getValue()) {
             System.out.println(s);
           }
           System.out.print("\n");
         }
       }
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
    System.out.println("G[rade]: <number> [H[igh] | L[ow]] [T[eacher]]");
    System.out.println("A[verage]: <number>");
    System.out.println("C[lassroom]: [S[tudent]] [T[eacher]]");
    System.out.println("E[nrollments]"); 
    System.out.println("P[erformance]: [G[rade]] [T[eacher]] [B[us]] [L[ist]]");
    System.out.println("I[nfo]");
    System.out.println("Q[uit]\n");
  }
}
