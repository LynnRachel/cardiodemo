package com.example.cardiodemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.card.payment.CardIOActivity;
import io.card.payment.CardType;
import io.card.payment.CreditCard;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_AUTOTEST = 200;
    private TextView tv_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_num = (TextView) findViewById(R.id.tv_num);

    }

    public void scan(View view) {
        Intent intent = new Intent(this, CardIOActivity.class)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false)
                .putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true)//去除水印
                .putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true)//去除键盘
                .putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, "zh-Hans")//设置提示为中文
                .putExtra("debug_autoAcceptResult", true);
        startActivityForResult(intent, REQUEST_AUTOTEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String outStr = new String();
        if ((requestCode == REQUEST_AUTOTEST) && data != null
                && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
            CreditCard result = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
            if (result != null) {
                outStr += "Card number: " + result.cardNumber + "\n";
            }
        }
        tv_num.setText(outStr);
    }
}
