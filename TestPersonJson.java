import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;


public class TestPersonJson {
    public static void main(String[] args) throws JsonProcessingException {
        PersonJson person1 = new PersonJson ("Bill", "Gates", "System Administrator", 4500, true, 0.5, Arrays.asList("user", "administrator", "superuser"));
        System.out.println("Cериализация объекта Java");
        System.out.println(person1);
        System.out.println(" в JSON");

       ObjectMapper mapper = new ObjectMapper();

        String json = (String) mapper.writeValueAsString(person1);
        System.out.println(json);
    }
}