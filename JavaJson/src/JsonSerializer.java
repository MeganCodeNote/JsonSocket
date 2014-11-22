/* Created by meganlee on 11/09/14. */

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * This class is for demonstrating how to convert any json-friendly Java Object
 * (Map, List, String, Number...) into a json string, and vice versa.
 *
 * Dependency used: jackson
 */

public class JsonSerializer {
    public static void main(String[] args) throws IOException{
        //=============  Java Object to Json String  ==============/
        // 1. Map to Json String
        System.out.println("----------------- Jackson: Map to Json -----------------------");
        ObjectMapper objectMapper = new ObjectMapper();
        Map data = new HashMap<String, Object>();
        List aData = Arrays.asList(1, 2, 3, 4);
        data.put("a", aData);
        data.put("b", "a string");
        Map cData = new HashMap<String, Float>();
        cData.put("item1", 1.1);
        cData.put("item1", 1.2);
        cData.put("item1", 1.3);
        data.put("c", cData);
        System.out.println(objectMapper.writeValueAsString(data) + "\n");

        // 2. List to Json String
        System.out.println("----------------- Jackson: List to Json -----------------------");
        List data2 = Arrays.asList(data, "another string");
        System.out.println(objectMapper.writeValueAsString(data2) + "\n");

        // 3. String to Json String
        System.out.println("----------------- Jackson: String to Json -----------------------");
        System.out.println(objectMapper.writeValueAsString("String String") + "\n");

        // 4. Numbers to Json String
        System.out.println("----------------- Jackson: Numbers to Json -----------------------");
        System.out.println(objectMapper.writeValueAsString(new BigInteger("122343535453")));
        System.out.println(objectMapper.writeValueAsString(2.3566) + "\n");



        //=============  Java Object to Json String  ==============/
        List<String> jsonStrings = Arrays.asList("{\"b\":\"a string\",\"c\":{\"item1\":1.3},\"a\":[1,2,3,4]}",
                                         "[{\"b\":\"a string\",\"c\":{\"item1\":1.3},\"a\":[1,2,3,4]},\"another string\"]",
                                         "\"String String\"",
                                         "122343535453",
                                         "true",
                                         "\"2014-01-12\"");
        List<Object> res = new ArrayList<Object>();
        for (String jsonString : jsonStrings) {
            Object item = objectMapper.readValue(jsonString, Object.class);
            res.add(item);
            System.out.println(item);
        }

        System.out.println(res); // make a break point here to view the content of res
    }
}
