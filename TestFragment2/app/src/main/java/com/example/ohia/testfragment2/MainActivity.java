package com.example.ohia.testfragment2;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends FragmentActivity
{
    FragmentManager _fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _fragmentManager = getSupportFragmentManager();
        Button btnAddFragment = (Button)findViewById(R.id.button);
        btnAddFragment.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                FragmentTransaction fragmentTransaction = _fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.mainContainer, new Fragment1(), "Fragment1");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button btnReplaceFragment = (Button)findViewById(R.id.btnReplaceFragment);
        btnReplaceFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FragmentTransaction transaction = _fragmentManager.beginTransaction();
                transaction.replace(R.id.mainContainer, new Fragment2(), "Fragment2");
                transaction.commit();
            }
        });
    }

    public void btnShowNavActivity_Click(View view)
    {
        Intent i = new Intent(this, Main2Activity.class);
        startActivity(i);
    }
}
