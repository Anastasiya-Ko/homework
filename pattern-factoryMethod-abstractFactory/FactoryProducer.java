package main;

import main.female.FemaleFactory;
import main.male.MaleFactory;

public class FactoryProducer {
    public static enum HumanFactoryType{
        MALE,
        FEMALE
    }

    public static AbstractFactory getFactory(HumanFactoryType type){
        if (type == HumanFactoryType.MALE) {
            return new MaleFactory();
        } else return new FemaleFactory();
    }
}
