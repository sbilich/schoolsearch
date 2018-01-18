/*
* @author Makenna Johnstone and Sara Bilich
* @date 1/8/18
*/

import java.util.ArrayList;

public class Teacher {

  String last;
  String first;
  int classroom;

  public Teacher(String last, String first, int classroom) {
      this.last = last;
      this.first = first;
      this.classroom = classroom;
  }
  public Teacher(Teacher other) {
    this.last = other.last;
    this.first = other.first;
    this.classroom = other.classroom;
  }

  public String getLastName() {
    return this.last;
  }

  public String getFirstName() {
    return this.first;
  }

  public int getClassroom() {
    return this.classroom;
  }

  public String toString() {
    return this.last + " " + this.first + " " + this.classroom;
  }

  public static Teacher findTeacher(String name, ArrayList<Teacher> lis) {
    Teacher teacher = null;

    for (Teacher t : lis) {
      if (t.getLastName().equals(name)) {
        teacher = t;
        break;
      }
    }

    return teacher;
  }
}
