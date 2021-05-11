package com.example.haushaltsapp.slideshow;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.haushaltsapp.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddTransactionActivity extends AppCompatActivity {
    private Button entryButton;
    private TextInputEditText nameET;
    private Spinner spinner;
    private TextInputEditText priceET;
private FinanceViewModel financeViewModel;
    String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        Bundle b = getIntent().getExtras();
        if (b != null)
            value = b.getString("key");
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
                String con = priceET.getText().toString().replaceAll("[^0-9]", "");


                spinner.getSelectedItem();
                String text = spinner.getSelectedItem().toString();
                int test = ((spinner.getSelectedItemPosition() * 2) - 1) * Integer.parseInt(con);
                int spintest = spinner.getSelectedItemPosition();
                financeViewModel.addEntryByID(value, nameET.getText().toString(), Double.valueOf(test));
                //financeViewModel.addEntry(nameET.getText().toString(), Double.valueOf(test));
                finish();
            }
        });

    }
}