package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        // initiate the Strings that will store the values we want
        String mainName = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        JSONArray alsoKnownAs = null;
        JSONArray ingredients = null;

        try {
            // create a new JSONObject and pass in the json string
            JSONObject sandwichObject = new JSONObject(json);
            // create another JSON object and assign it to the "name" object from the json string
            JSONObject nameObject = sandwichObject.getJSONObject("name");
            // get the needed values from the sandwichObject and assign it to each String variable
            mainName = nameObject.getString("mainName");
            placeOfOrigin = sandwichObject.getString("placeOfOrigin");
            description = sandwichObject.getString("description");
            image = sandwichObject.getString("image");
            // add each item from the JSONArray to an ArrayList<String>
            alsoKnownAs = nameObject.getJSONArray("alsoKnownAs");
            ArrayList<String> alsoKnownAsList = new ArrayList<>();
            for (int i=0; i <alsoKnownAs.length();i++) {
                alsoKnownAsList.add(alsoKnownAs.getString(i));
            }

            ingredients = sandwichObject.getJSONArray("ingredients");
            ArrayList<String> ingredientsList = new ArrayList<>();
            for (int i=0; i<ingredients.length();i++) {
                ingredientsList.add(ingredients.getString(i));
            }

            // build a new sandwich with the obtained information
            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
