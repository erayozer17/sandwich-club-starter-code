package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView imageView;
    private TextView tv_mainname;
    private TextView tv_alsoknownas;
    private TextView tv_placeoforigin;
    private TextView tv_description;
    private TextView tv_ingridients;
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.image_iv);
        tv_mainname = findViewById(R.id.tv_main_name);
        tv_alsoknownas = findViewById(R.id.tv_also_known_as);
        tv_placeoforigin = findViewById(R.id.tv_placeoforigin);
        tv_description = findViewById(R.id.tv_description);
        tv_ingridients = findViewById(R.id.tv_ingridients);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        tv_mainname.append(sandwich.getMainName());
        int counterForAlsoKnownAs = 0;
        for (String name : sandwich.getAlsoKnownAs()) {
            if (counterForAlsoKnownAs++ == sandwich.getAlsoKnownAs().size() - 1) {
                tv_alsoknownas.append(name);
            } else {
                tv_alsoknownas.append(name + "\n");
            }
        }
        tv_placeoforigin.append(sandwich.getPlaceOfOrigin());
        tv_description.append(sandwich.getDescription());
        int counterForIngridients = 0;
        for (String name : sandwich.getIngredients()) {
            if (counterForIngridients++ == sandwich.getIngredients().size() - 1) {
                tv_ingridients.append(name);
            } else {
                tv_ingridients.append(name + "\n");
            }
        }
    }
}
