package com.ebookfrenzy.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DatabaseActivity extends AppCompatActivity {

    TextView idView;
    EditText productBox;
    EditText quantityBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        idView = (TextView) findViewById(R.id.productID);
        productBox = (EditText) findViewById(R.id.productName);
        quantityBox =
                (EditText) findViewById(R.id.productQuantity);
    }

    public void newProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        int quantity =
                Integer.parseInt(quantityBox.getText().toString());

        Product product =
                new Product(productBox.getText().toString(), quantity);

        dbHandler.addProduct(product);
        productBox.setText("");
        quantityBox.setText("");
    }

    public void lookupProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        Product product =
                dbHandler.findProduct(productBox.getText().toString());

        if (product != null) {
            idView.setText(String.valueOf(product.getID()));

            quantityBox.setText(String.valueOf(product.getQuantity()));
        } else {
            idView.setText("No Match Found");
        }
    }

    public void removeProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        boolean result = dbHandler.deleteProduct(
                productBox.getText().toString());

        if (result)
        {
            idView.setText("Record Deleted");
            productBox.setText("");
            quantityBox.setText("");
        }
        else
            idView.setText("No Match Found");
    }

    public void updateProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        try {

            int id = Integer.parseInt(idView.getText().toString());

            int quantity =
                    Integer.parseInt(quantityBox.getText().toString());

            Product product =
                    new Product(id, productBox.getText().toString(), quantity);

            dbHandler.updateProduct(product);
            productBox.setText("");
            quantityBox.setText("");

            boolean result = dbHandler.updateProduct(product);

            if (result) {
                idView.setText("Record Updated");
                productBox.setText("");
                quantityBox.setText("");
            } else
                idView.setText("Update Failed");
        } catch (IllegalStateException ise) {
            idView.setText("No Record");
        } catch (NumberFormatException nfe) {
            idView.setText("No Record");
        }
    }

    public void deleteAllProducts (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        dbHandler.deleteAllProducts();

        idView.setText("Records Deleted");
        productBox.setText("");
        quantityBox.setText("");
    }
}
