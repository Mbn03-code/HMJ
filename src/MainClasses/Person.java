package model;

public class Person {
    private String nationalID;
    private String name;
    private String lastName;
    private int age;
    private String gender;
    private String phone;

    public Person(String nationalID, String name, String lastName, int age, String gender, String phone) {
        setNationalID(nationalID);
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        setGender(gender);
        setPhone(phone);
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        if (nationalID !=null && nationalID.matches("\\d{10}"))this.nationalID=nationalID;
        else throw new IllegalArgumentException("national id must be exactly 10 digits.");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender.equalsIgnoreCase("male")||gender.equalsIgnoreCase("female"))this.gender=gender;
        else throw new  IllegalArgumentException("Gender must be 'male' or 'female' ");
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone !=null && phone.matches("\\d{11}"))this.phone=phone;
        else throw new IllegalArgumentException("phone number must be exactly 11 digits.");
    }

    public String getInfo() {
        return "National ID: " + nationalID + ", Name: " + name + " " + lastName + ", Age: " + age + ", Gender: " + gender + ", Phone: " + phone ;
    }
}
