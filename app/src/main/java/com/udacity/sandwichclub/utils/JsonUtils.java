package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

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
            mainName = jsonObject.getJSONObject("name").getString("mainName");
            alsoKnownAs = prepareJsonArray(jsonObject.getJSONObject("name"), "alsoKnownAs");
            placeOfOrigin = jsonObject.getString("placeOfOrigin");
            description = jsonObject.getString("description");
            image = jsonObject.getString("image");
            ingredients = prepareJsonArray(jsonObject, "ingredients");
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
