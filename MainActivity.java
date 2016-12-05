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

        //CheckBox to specify whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //CheckBox to specify chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //CheckBox to specify caramel topping
        CheckBox caramelToppingCheckBox = (CheckBox) findViewById(R.id.caramel_topping_checkbox);
        boolean hasCaramelTopping = caramelToppingCheckBox.isChecked();


        //CheckBox to specif vanilla shot
        CheckBox vanillaShotCheckBox = (CheckBox) findViewById(R.id.vanilla_checkbox);
        boolean hasVanillaShot = vanillaShotCheckBox.isChecked();

        //CheckBox to specify caramel shot
        CheckBox caramelShotCheckBox = (CheckBox) findViewById(R.id.caramel_checkbox);
        boolean hasCaramelShot = caramelShotCheckBox.isChecked();

        //CheckBox to specif hazelnut shot
        CheckBox hazelnutShotCheckBox = (CheckBox) findViewById(R.id.hazelnut_checkbox);
        boolean hasHazelnutShot = hazelnutShotCheckBox.isChecked();

        //Calculates price
        int price = calculatePrice(hasWhippedCream, hasChocolate, hasCaramelTopping, hasVanillaShot,
                hasCaramelShot, hasHazelnutShot);
        //Display order summary on screen
        String message = createOrderSummary(price, hasWhippedCream, hasChocolate, hasCaramelTopping,
                hasVanillaShot, hasCaramelShot, hasHazelnutShot, name);

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
     * @param addWhippedCream adds whip topping
     * @param addChocolate    adds choc topping
     * @param addCaramel      adds caramel topping
     * @param addVanillaShot  adds vanilla shot
     * @param addCaramelShot  adds caramel shot
     * @param addHazelnutShot adds hazelnut shot
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate, boolean addCaramel,
                               boolean addVanillaShot, boolean addCaramelShot, boolean addHazelnutShot) {
        //Calculates the basic price for coffee
        int basePrice = 2;
        //Calculates basic price + whipped cream ($1)
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        //Calculates the basic price + chocolate ($1)
        if (addChocolate) {
            basePrice = basePrice + 1;
        }
        //Calculates the basic price + caramel ($1)
        if (addCaramel) {
            basePrice = basePrice + 1;
        }
        //Calculates the basic price + vanilla shot ($2)
        if (addVanillaShot) {
            basePrice = basePrice + 2;
        }
        //Calculates the basic price + caramel shot ($2)
        if (addCaramelShot) {
            basePrice = basePrice + 2;
        }
        //Calculates the basic price + hazelnut shot ($2)
        if (addHazelnutShot) {
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
        int price = quantity * 2;
    }

    /**
     * This method provides an order summary.
     *
     * @param price           of the order
     * @param addWhippedCream is whether user wants a whipped cream topping or not
     * @param addChocolate    is whether the user wants a chocolate topping or not
     * @param addCaramel      is whether the user wants a caramel topping or not
     * @param addVanillaShot  is whether the user wants a vanilla shot or not
     * @param addCaramelShot  is whether the user wants a caramel shot or not
     * @param addHazelnutShot is whether the user wants a hazelnut shot or not
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, boolean addCaramel,
                                      boolean addVanillaShot, boolean addCaramelShot, boolean addHazelnutShot, String name) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_caramel, addCaramel);
        priceMessage += "\n" + getString(R.string.order_summary_vanilla_shot, addVanillaShot);
        priceMessage += "\n" + getString(R.string.order_summary_caramel_shot, addCaramelShot);
        priceMessage += "\n" + getString(R.string.order_summary_hazelnut_shot, addHazelnutShot);
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
