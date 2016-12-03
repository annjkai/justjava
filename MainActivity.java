package com.example.android.justjava20;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

//import static android.R.attr.name;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {


    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            //Toast message, quantity can't be exceeded
            Toast.makeText(this, "Maximum order quantity is 100", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            //Toast message, quantity can't be deceded
            Toast.makeText(this, "Minimum order quantity is 1", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //Allows user to input name
        EditText nameField = (EditText) findViewById(R.id.your_name);
        String name = nameField.getText().toString();

        //CheckBox to specify whipped cream
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //CheckBox to specify chocolate
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //Calculates price
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        //Display order summary on screen
        String message = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        //This intent launches an email app
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
        /**
         * This method calls the price.
         *
         * @param addWhippedCream adds whip
         * @param addChocolate    adds choc
         * @return total price
         */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        //Calculates the basic price for coffee
        int basePrice = 5;
        //Calculates basic price + whipped cream ($1)
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        //Calculates the basic price + chocolate ($2)
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private void calculatePrice(int quantity) {
        int price = quantity * 5;
    }

    /**
     * This method provides an order summary.
     *
     * @param price           of the order
     * @param addWhippedCream is whether user wants whipped cream or not
     * @param addChocolate    is whether the user wants chocolate or not
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }


}
