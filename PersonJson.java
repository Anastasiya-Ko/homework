import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;


public class PersonJson {
    private String name;
    private String surName;
    private String speciality;
    private int salary;
    private boolean partTimeWorker;
    private double rate;
    private List <String> accessRoles = new ArrayList<>();


    public PersonJson(String name, String surName, String speciality, int salary, boolean partTimeWorker, double rate, List<String> accessRoles) {
        this.name = name;
        this.surName = surName;
        this.speciality = speciality;
        this.salary = salary;
        this.partTimeWorker = partTimeWorker;
        this.rate = rate;
        this.accessRoles = accessRoles;

    }

    public PersonJson() {
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getSurName() {

        return surName;
    }

    public void setSurName(String surName) {

        this.surName = surName;
    }

    public String getSpeciality() {

        return speciality;
    }

    public void setSpeciality(String speciality) {

        this.speciality = speciality;
    }

    public int getSalary() {

        return salary;
    }

    public void setSalary(int salary) {

        this.salary = salary;
    }

    public boolean isPartTimeWorker() {

        return partTimeWorker;
    }

    public void setPartTimeWorker(boolean partTimeWorker) {

        this.partTimeWorker = partTimeWorker;
    }

    public double getRate() {

        return rate;
    }

    public void setRate(double rate) {

        this.rate = rate;

    }

    public List<String> getAccessRoles() {
        return accessRoles;
    }

    public void setAccessRoles(List<String> accessRoles) {
        this.accessRoles = accessRoles;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PersonJson.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("surName='" + surName + "'")
                .add("speciality='" + speciality + "'")
                .add("salary=" + salary)
                .add("partTimeWorker=" + partTimeWorker)
                .add("rate=" + rate)
                .add("accessRoles=" + accessRoles)
                .toString();
    }
}

