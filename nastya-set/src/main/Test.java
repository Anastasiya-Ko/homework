package main;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        List<Integer> temp = Arrays.asList(2, 3, 5, 3, 5, 2, 1);
        getResult(temp);

    }

    public static int getResult(List<Integer> socks) {

        List<Integer> res = socks.stream().sorted().toList();
        int result = 0;

        for (int i = 0; i < socks.size(); i = i + 2) {
            int f = res.get(i);


            for (int j = i+1; j < socks.size(); j++) {
                int s = res.get(j);
                if (f == s){
                    break;
                }else if (i == 0){
                    result = f;
                }
                else {
                    result = f;
                }
            }
        }
        if (result == 0) {
            result = res.get(res.size()-1);
        }


        System.out.println(result);

        return 0;
    }

}
