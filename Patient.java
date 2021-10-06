package com.nass;

import java.util.*;

public class Patient {
    long snils;
    String name;
    String address;

    @Override
    public String toString() {
        return new StringJoiner(", ", Patient.class.getSimpleName() + "[", "]")
                .add("snils=" + snils)
                .add("name='" + name + "'")
                .add("address='" + address + "'")
                .toString();
    }

    public Patient(long snils, String name, String address) {
        this.snils = snils;
        this.name = name;
        this.address = address;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return snils == patient.snils;
    }

    @Override
    public int hashCode() {
        return Objects.hash(snils);
    }


    public static void main(String[] args) {

        ArrayList<Patient> arrayList = new ArrayList<>();
        HashSet<Patient> hash = new HashSet<>();


        Patient patient1 = new Patient(123456789, "Anna", "Mira, 22");
        Patient patient2 = new Patient(123456789, "Gleb", "Mira, 32");
        Patient patient3 = new Patient(987654321, "Polina", "Mira, 42");

        arrayList.add(patient1);
        arrayList.add(patient2);
        arrayList.add(patient3);

        hash.add(patient1);
        hash.add(patient2);
        hash.add(patient3);

       System.out.println(arrayList);
       System.out.println(hash);

        HashMap<Integer, Patient> map = new HashMap<>();

        map.put(1, patient1);
        map.put(2, patient2);
        map.put(3, patient3);

        System.out.println(map);



    }
}