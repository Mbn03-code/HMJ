package model;

public class Staff extends Person{
    private String position;
    private String salary;

    public Staff(String nationalID, String name, String lastName, int age, String gender, String phone, String position, String salary) {
        super(nationalID, name, lastName, age, gender, phone);
        this.position = position;
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", Position: " + position + ", Salary: " + salary;

    }
}
