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

    private TextView mPlaceOfOriginTv;
    private TextView mAlsoKnownTv;
    private TextView mDescriptionTv;
    private TextView mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mPlaceOfOriginTv = findViewById(R.id.origin_tv);
        mAlsoKnownTv = findViewById(R.id.also_known_tv);
        mDescriptionTv = findViewById(R.id.description_tv);
        mIngredients = findViewById(R.id.ingredients_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        mPlaceOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        // check for empty string and if so replace it with more helpful text (ie "Unknown")
        if (sandwich.getAlsoKnownAs().isEmpty()) {
            mAlsoKnownTv.setText("Unknown");
        } else {
            mAlsoKnownTv.setText(" - " + android.text.TextUtils.join("\n - ", sandwich.getAlsoKnownAs()));
        }
        mDescriptionTv.setText(sandwich.getDescription());
        // make ingredients look like a list by starting with a dash and forcing a new line after each string
        mIngredients.setText(" - " + android.text.TextUtils.join("\n - ", sandwich.getIngredients()));

    }

}
