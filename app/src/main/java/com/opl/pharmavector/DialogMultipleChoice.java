package com.opl.pharmavector;

import android.content.Context;
import android.content.DialogInterface;

import android.app.AlertDialog;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class DialogMultipleChoice {
    Context mContext;
    public DialogMultipleChoice(Context context) {
        mContext = context;
    }

    List<Item> itemList = new ArrayList<>();

    public void show(final TextView s_time) {

        if (itemList.isEmpty()) {
            itemList.add(new Item("GM",1));
            itemList.add(new Item("GMm",2));
            itemList.add(new Item("SM", 3));
            itemList.add(new Item("DSM", 4));
            itemList.add(new Item("ASM", 5));

        }

        final DialogMultipleChoiceAdapter adapter =
                new DialogMultipleChoiceAdapter(mContext, itemList);

        new AlertDialog.Builder(mContext).setTitle("Visited with")
                .setAdapter(adapter, null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(mContext, "Visited with " + adapter.getCheckedItem().size(), Toast.LENGTH_SHORT).show();
                        final List<Item> value_1 = adapter.getCheckedItem();
                        String tr= adapter.getCheckedItem().get(0).toString();
                        String arr_value_1 = value_1.toString();
                        s_time.requestFocus();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();


    }
}