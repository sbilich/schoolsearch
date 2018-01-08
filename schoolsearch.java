/*
* @author Sara Bilich and Makenna Johnstone
* @date 1/8/18
*/

import Student;
import InvalidStudentException;

public static ArrayList<Student> students;
public static const String FILENAME = "students.txt";
public static int STUDENT_SIZE = 8;

public static void main(String args[]) {

}

/*
* Parses in the students contained within FILENAME and
* intializes |students|.
*/
private void parseStudents() {
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
        throw new
      }
    }

    sc.close();
  }
  catch ()
  {

  }
  catch (FileNotFoundException e)
  {
    System.out.println("Failed to open file: " + e);
    
  }
  catch (Exception e)
  {
    System.out.println("Scanning failed with exception " + e);
  }
}
