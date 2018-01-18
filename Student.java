/*
* @author Makenna Johnstone and Sara Bilich
* @date 1/8/18
*/

import java.util.ArrayList;

public class Student {

  String last;
  String first;
  int grade;
  int classroom;
  int bus;
  double gpa;
  String tlast;
  String tfirst;

  public Student(String last, String first, int grade, int classroom, int bus,
    double gpa) {
      this.last = last;
      this.first = first;
      this.grade = grade;
      this.classroom = classroom;
      this.bus = bus;
      this.gpa = gpa;
  }

  public Student(Student other) {
    this.last = other.last;
    this.first = other.first;
    this.grade = other.grade;
    this.classroom = other.classroom;
    this.bus = other.bus;
    this.gpa = other.gpa;
  }

  public String getLastName() {
    return this.last;
  }

  public String getFirstName() {
    return this.first;
  }

  public int getGrade() {
    return this.grade;
  }

  public int getClassroom() {
    return this.classroom;
  }

  public int getBus() {
    return this.bus;
  }

  public double getGpa() {
    return this.gpa;
  }

  public String toString() {
    return this.last + " " + this.first + " " + this.grade + " " + this.classroom + " " + this.bus + " " + this.gpa + " " + this.tlast + " " + this.tfirst;
  }

  /*
  * Returns the teacher that teaches the student or returns null
  * if the student's teacher isn't in |lis|.
  */
  public Teacher findTeacher(ArrayList<Teacher> lis) {
    Teacher teacher = null;

    for (Teacher t : lis) {
      if (t.getClassroom() == this.classroom) {
        teacher = t;
        break;
      }
    }

    return teacher;
  }
}
