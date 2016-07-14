


package com.example.android.probando1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Number of cups of coffee ordered
    int quantity = 2;


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, R.string.no_more, Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, R.string.no_less, Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Quiere crema?
        CheckBox whippedCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean whipped = whippedCheckbox.isChecked();

        //Quiere chocolate?
        CheckBox chocoCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean conChocolate = chocoCheckbox.isChecked();

        //Cual es el nombre?
        EditText nameBox = (EditText) findViewById(R.id.nameBox);
        String nombre = nameBox.getText().toString();

        //calcular precio teniendo en cuenta los toppings
        int price = calculatePrice(whipped, conChocolate);

        //Armar mensaje
        String priceMessage = createOrderSummary(price, whipped, conChocolate, nombre);


        //Completar el email
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:")); // only email apps should handle this
        email.putExtra(Intent.EXTRA_TEXT, priceMessage);
        email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_for) + nombre);
        if (email.resolveActivity(getPackageManager()) != null) {
            startActivity(email);
        }


    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }




    private int calculatePrice(boolean whipped, boolean conChocolate) {
        int basePrice = 5;

        if (whipped) {
            basePrice += 1;
        }

        if (conChocolate) {
            basePrice += 2;
        }

        return quantity * basePrice;
    }

    private String createOrderSummary(int price, boolean whipped, boolean chocolate, String nombre) {
        String priceMessage = getString(R.string.Name) +": " + nombre;
        priceMessage += "\n" + getString(R.string.add_whipped_cream) + whipped;
        priceMessage += "\n" + getString(R.string.add_chocolate) + chocolate;
        priceMessage += "\n"+ getString(R.string.Quantity) +": " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n" + getString(R.string.thankyou);
        return priceMessage;
    }
}
