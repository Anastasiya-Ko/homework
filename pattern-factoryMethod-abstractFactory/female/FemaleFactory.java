package main.female;

import main.AbstractFactory;
import main.Human;

import javax.swing.*;

public class FemaleFactory implements AbstractFactory {
    public Human getPerson(int age){
        Human human = null;
        if(age <= KidGirl.MAX_AGE) {
            human = new KidGirl();
        }
        else if (age > KidGirl.MAX_AGE && age <= TeenGirl.MAX_AGE ) {
            human = new TeenGirl();
        } else
            human = new Woman();
        return human;
    }

}
