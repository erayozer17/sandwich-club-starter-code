package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();

        JSONObject jsonObject = null;
        String mainName = null;
        List<String> alsoKnownAs = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        List<String> ingredients = null;

        try {
            jsonObject = new JSONObject(json);
            mainName = jsonObject.getJSONObject(NAME).optString(MAIN_NAME);
            alsoKnownAs = prepareJsonArray(jsonObject.getJSONObject(NAME), ALSO_KNOWN_AS);
            placeOfOrigin = jsonObject.optString(PLACE_OF_ORIGIN);
            description = jsonObject.optString(DESCRIPTION);
            image = jsonObject.optString(IMAGE);
            ingredients = prepareJsonArray(jsonObject, INGREDIENTS);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonObject != null) {
            sandwich.setMainName(mainName);
            sandwich.setAlsoKnownAs(alsoKnownAs);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setDescription(description);
            sandwich.setImage(image);
            sandwich.setIngredients(ingredients);
        }

        return sandwich;
    }

    private static List<String> prepareJsonArray(JSONObject jsonObject, String nameOfArray) {
        List<String> list = new ArrayList<>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(nameOfArray);
            for (int i = 0; i < jsonArray.length(); i++) {
                String str = jsonArray.getString(i);
                list.add(str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
