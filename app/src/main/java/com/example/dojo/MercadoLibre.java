package com.example.dojo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MercadoLibre extends AppCompatActivity {
    public Button botondemercado;
    public TextView results;
    public String PUBLIC_KEY_SANDBOX, ACCESS_TOKEN_SANDBOX;
    public int REQUEST_CODE;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercado_checkout);
        /*
        initViews();


        JSONObject jsonObject = new JSONObject();
        final JSONObject itemJSON = new JSONObject();
        final JSONObject payerJSON = new JSONObject();
        JSONArray itemJsonArray = new JSONArray();
        try {

            itemJSON.put("title", "Dummy Item");
            itemJSON.put("description", "Multicolor Item");
            itemJSON.put("quantity", 1);
            itemJSON.put("currency_id", "BRL");
            itemJSON.put("unit_price", 2.10);

            itemJsonArray.put(itemJSON);

            JSONObject phoneJSON = new JSONObject();
            phoneJSON.put("area_code", "");
            phoneJSON.put("number", "880.402.7555");
            JSONObject idJSON = new JSONObject();
            idJSON.put("type", "DNI");
            idJSON.put("number", "123456789");
            JSONObject addressJSON = new JSONObject();
            addressJSON.put("street_name", "Núbia Viela");
            addressJSON.put("street_number", 25598);
            addressJSON.put("zip_code", "8972");

            payerJSON.put("name", "Núbia");
            payerJSON.put("surname", "Macedo");
            payerJSON.put("email", "leann@gmail.com");
            payerJSON.put("date_created", "2015-06-02T12:58:41.425-04:00");
            payerJSON.put("phone", phoneJSON);
            payerJSON.put("identification", idJSON);
            payerJSON.put("address", addressJSON);


            jsonObject.put("items", itemJsonArray);
            jsonObject.put("payer", payerJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(MercadoLibre.this);
        final String url = "https://api.mercadopago.com/checkout/preferences?access_token="+ACCESS_TOKEN_SANDBOX;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("debinf MainAct", "response JSONObject: "+response);
                    String checkoutPreferenceId = response.getString("id");
                    new MercadoPagoCheckout.Builder(PUBLIC_KEY_SANDBOX, checkoutPreferenceId).build().startPayment(MercadoLibre.this,REQUEST_CODE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("debinf MainAct", "response ERROR: "+error.networkResponse.allHeaders);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
                final Payment payment = (Payment) data.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT);
                ((TextView) findViewById(R.id.mppodes)).setText("Resultado del pago: " + payment.getPaymentStatus());
                //Done!
            } else if (resultCode == RESULT_CANCELED) {
                if (data != null && data.getExtras() != null
                        && data.getExtras().containsKey(MercadoPagoCheckout.EXTRA_ERROR)) {
                    final MercadoPagoError mercadoPagoError =
                            (MercadoPagoError) data.getSerializableExtra(MercadoPagoCheckout.EXTRA_ERROR);
                    ((TextView) findViewById(R.id.mppodes)).setText("Error: " + mercadoPagoError.getMessage());
                    //Resolve error in checkout
                } else {
                    //Resolve canceled checkout
                }
            }
        }
    }
    
    public void initViews(){

    }
}

*/
    }
}