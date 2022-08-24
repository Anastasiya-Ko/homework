package main;

import main.female.FemaleFactory;
import main.male.MaleFactory;

public class Solution {
    public static void main(String[] args) {
        MaleFactory maleFactory = new MaleFactory();
        System.out.println(maleFactory.getPerson(99));
        System.out.println(maleFactory.getPerson(12));
        System.out.println(maleFactory.getPerson(19));

        System.out.println("=================================");

        FemaleFactory femaleFactory = new FemaleFactory();
        System.out.println(femaleFactory.getPerson(99));
        System.out.println(femaleFactory.getPerson(12));
        System.out.println(femaleFactory.getPerson(19));
    }
}
