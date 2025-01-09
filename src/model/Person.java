package model;

public class Person {
    private int nationalID;
    private String name;
    private String lastName;
    private int age;
    private String gender;
    private String phone;
    private String address;

    public Person(int nationalID, String name, String lastName, int age, String gender, String phone, String address) {
        this.nationalID = nationalID;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        setGender(gender);
        setPhone(phone);
        this.address = address;
    }

    public int getNationalID() {
        return nationalID;
    }

    public void setNationalID(int nationalID) {
        this.nationalID = nationalID;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
