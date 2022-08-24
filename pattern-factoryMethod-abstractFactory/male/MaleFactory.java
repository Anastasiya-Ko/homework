package main.male;

import main.AbstractFactory;
import main.Human;

public class MaleFactory implements AbstractFactory {

    public Human getPerson(int age){
        Human human = null;
        if (age <= KidBoy.MAX_AGE) {
            human = new KidBoy();
        }
        else if (age > KidBoy.MAX_AGE && age <= TeenBoy.MAX_AGE ) {
            human = new TeenBoy();
        } else
            human = new Man();
        return human;
    }
}
