package com.example.haushaltsapp.slideshow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.haushaltsapp.R;
import com.example.haushaltsapp.shopping.ShoppingViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class AddTransactionActivity extends AppCompatActivity {
    private Button entryButton;
    private TextInputEditText nameET;
    private Spinner spinner;
    private TextInputEditText priceET;
private FinanceViewModel financeViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        financeViewModel = new ViewModelProvider(this).get(FinanceViewModel.class);

        nameET = findViewById(R.id.transaction_name_input);
        priceET = findViewById(R.id.transaction_amount_input);
        entryButton = findViewById(R.id.transaction_button);
        spinner = findViewById(R.id.transaction_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dropdown, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        entryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameET.getText().toString();
                priceET.getText().toString();
                spinner.getSelectedItem();
                String text = spinner.getSelectedItem().toString();

                financeViewModel.addEntry(nameET.getText().toString(),Double.parseDouble(priceET.getText().toString()));
            }
        });

    }
}