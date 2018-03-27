package org.ns1.gather.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

public class Utils {

    public static Optional<String> readConfig(String filepath) {
        JSONParser parser = new JSONParser();
        Optional<String> token = Optional.empty();

        try (Reader is = new FileReader(filepath)) {
            Object obj = parser.parse(is);

            JSONObject jsonObject = (JSONObject) obj;

            token = Optional.of((String) jsonObject.get("token"));

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return token;
    }

}
