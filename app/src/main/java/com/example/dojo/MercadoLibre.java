package com.example.dojo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MercadoLibre extends AppCompatActivity {
    public Button botondemercado;
    public TextView results;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado_checkout);
        initViews();
        /*
        botondemercado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit(view);
            }
        });
    }

    public void initViews() {
        botondemercado = findViewById(R.id.botonmercado);
        results = findViewById(R.id.mppodes2);
    }

    public void submit(View view) {
        // Iniciar el checkout de Mercado Pago
        new MercadoPago.StartActivityBuilder()
                .setActivity(this)
                .setPublicKey("TEST-ad365c37-8012-4014-84f5-6c895b3f8e0a")
                .setCheckoutPreferenceId("150216849-ceed1ee4-8ab9-4449-869f-f4a8565d386f")
                .startCheckoutActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MercadoPago.CHECKOUT_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                Payment payment = JsonUtil.getInstance()
                        .fromJson(data.getStringExtra("payment"), Payment.class);
                if (payment != null) {
                    results.setText("PaymentID:" + payment.getId() + "- PaymentStatus:" + payment.getStatus());
                } else {
                    results.setText("El usuario no concretó el pago.");
                }
            } else {
                if ((data != null) && (data.hasExtra("mpException"))) {
                    MPException mpException = JsonUtil.getInstance()
                            .fromJson(data.getStringExtra("mpException"), MPException.class);
                }
            }
        }
    }
}

*/
    }
    public void initViews(){

    }
}

