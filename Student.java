/*
* @author Makenna Johnstone and Sara Bilich
* @date 1/8/18
*/

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
    double gpa, String tlast, String tfirst) {
      this.last = last;
      this.first = first;
      this.grade = grade;
      this.classroom = classroom;
      this.bus = bus;
      this.gpa = gpa;
      this.tlast = tlast;
      this.tfirst = tfirst;
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

  public String getTLastName() {
    return this.tlast;
  }

  public String getTFirstName() {
    return this.tfirst;
  }
}
