package com.azhaguvel.dam.chitfund;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aninterface.root.dam.R;

public class HomeChitfund_Activity extends AppCompatActivity {
    Button fifty,hundered,twohundred,fiftyinsert,hunderedindert,twohunderdinsert,overallreport,repayment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_chitfund_);

        fifty=(Button)findViewById(R.id.fivek);
        hundered=(Button)findViewById(R.id.hunk);
        twohundred=(Button)findViewById(R.id.twohunk);
        fiftyinsert=(Button)findViewById(R.id.fifty_insert);
//        overallreport=(Button)findViewById(R.id.overall_report);
//        repayment=(Button)findViewById(R.id.repayments);

            fifty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i= new Intent(HomeChitfund_Activity.this, FiftyListview_Activity.class);
              startActivity(i);
    }
});
        hundered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(HomeChitfund_Activity.this, HunderedkListiview_Activity.class);
                startActivity(i);
            }
        });
        twohundred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(HomeChitfund_Activity.this, TwhunderedListview_Activity.class);
                startActivity(i);
            }
        });
        fiftyinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(HomeChitfund_Activity.this, Fiftykentry_Activity.class);
                startActivity(i);
            }
        });
//        hunderedindert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i= new Intent(HomeChitfund_Activity.this, Twohunderedkone.class);
//                startActivity(i);
//            }
//        });
//        twohunderdinsert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i= new Intent(HomeChitfund_Activity.this, twohundredktwo.class);
//                startActivity(i);
//            }
//        });
//        overallreport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i= new Intent(HomeChitfund_Activity.this, TwhunderedListview_Activity.class);
//                startActivity(i);
//            }
//        });
//        repayment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i= new Intent(HomeChitfund_Activity.this, TwhunderedListview_Activity.class);
//                startActivity(i);
//            }
//        });

    }
}
