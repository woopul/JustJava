package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int price = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    /**
     * This method is called when the plus button is pressed, it increment quantity value
     *
     * @param v
     */
    public void increment(View v) {
        if (getQuantity() == 100) {
            Toast.makeText(getApplicationContext(), "Order can not be greater then 100", Toast.LENGTH_SHORT).show();
        } else {
            setQuantity(getQuantity() + 1);
            displayQuantity(getQuantity());
        }
    }


    /**
     * This method is called when the minus button is pressed, it decrement quantity value
     *
     * @param v
     */
    public void decrement(View v) {
        if (getQuantity() < 1) {
            Toast.makeText(getApplicationContext(), "Order can not be less than 1", Toast.LENGTH_SHORT).show();
        } else {
            setQuantity(getQuantity() - 1);
            displayQuantity(getQuantity());
        }
    }


    /**
     * This method calculates the price of ordered coffes
     *
     * @param quantity        is the total number of ordered coffes
     * @param addWhippedCream is whether or not user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @return
     */

    private int calculatePrice(int quantity, boolean addWhippedCream, boolean addChocolate) {

        int basePrice = price;

        if (addWhippedCream) {
            basePrice += 1;
        }
        if (addChocolate) {
            basePrice += 2;
        }
        return quantity * basePrice;
    }

    public void displayQuantity(int value) {
        TextView quantity = (TextView) findViewById(R.id.quantity);
        quantity.setText(String.valueOf(value));
    }

    public void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method creates order summary
     *
     * @param number        The total price of order
     * @param whippeadCream is whether whipped cream was ordered or not
     * @param chocolate     is wheter chocolate was ordered or not
     * @param name          is the name of the person
     * @return
     */
    public String createOrderSummary(int number, boolean whippeadCream, boolean chocolate, String name) {

        String orderSummary = "Name: " + name;
        orderSummary += "\nAdd whipped cream? " + whippeadCream;
        orderSummary += "\nAdd chocolate? " + chocolate;
        orderSummary += "\nQuantity: " + getQuantity();
        orderSummary += "\nTotal $: " + number;
        orderSummary += "\nThank you!";
        return orderSummary;
    }

    /**
     * This method is called when the order button is pressed
     *
     * @param view
     */
    public void submitOrder(View view) {

        // Checking if the whippedCreamCheackbox is pressed, then store value in the variable
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Checking if the chocolateCheckBox is pressed, then store value in the variable
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        // Find the user's name
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String name = nameEditText.getText().toString();

        int price = calculatePrice(quantity, hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, name);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for: " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        //displayMessage(priceMessage);
    }
}
