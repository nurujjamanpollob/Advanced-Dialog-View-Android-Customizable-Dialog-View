/*
 * Copyright (c) 2022 Nurujjaman Pollob, All Right Reserved.
 *        Licensed under the Apache License, Version 2.0 (the "License");
 *        you may not use this file except in compliance with the License.
 *        You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing, software
 *        distributed under the License is distributed on an "AS IS" BASIS,
 *        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *        See the License for the specific language governing permissions and
 *        limitations under the License.
 */

package com.example.advanceddialogtest.advanceddialogview;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.AnimRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;

import dev.nurujjamanpollob.uicollection.advanceddialogview.AdvancedDialogView;
import dev.nurujjamanpollob.uicollection.advanceddialogview.DialogOptions;

public class MainActivity extends AppCompatActivity {


   // Create Advanced Dialog View Instance
    AdvancedDialogView dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // buttons and listener
        Button bottomDialog = findViewById(R.id.bottom_dialog);
        Button centerDialog = findViewById(R.id.center_dialog);
        Button topDialog = findViewById(R.id.top_dialog);
        Button dialogWithAnimation = findViewById(R.id.dialog_with_anim);
        Button fixedDialog = findViewById(R.id.fixed_dialog);
        Button dialogCancelableOnClickOutside = findViewById(R.id.dialog_cancelable_on_click_outside);

        bottomDialog.setOnClickListener(view -> drawDialog(R.layout.dialog_layout,
                DialogOptions.DIALOG_GRAVITY_BOTTOM,
                "This is bottom Dialog",
                0,
                false,
                false));
        centerDialog.setOnClickListener(view -> drawDialog(R.layout.dialog_layout,
                DialogOptions.DIALOG_GRAVITY_CENTER,
                "This is Center Dialog",
                0,
                false,
                false));
        topDialog.setOnClickListener(view -> drawDialog(R.layout.dialog_layout,
                DialogOptions.DIALOG_GRAVITY_TOP,
                "This is Top Dialog",
                0,
                false,
                false));
        dialogWithAnimation.setOnClickListener(view -> drawDialog(R.layout.dialog_layout,
                DialogOptions.DIALOG_GRAVITY_BOTTOM,
                "This is Dialog with custom Animation",
                R.anim.popup_flyout_show,
                false,
                false));
        fixedDialog.setOnClickListener(view -> drawDialog(R.layout.dialog_layout,
                DialogOptions.DIALOG_GRAVITY_BOTTOM,
                "This is Fixed Dialog",
                0,
                true,
                false));
        dialogCancelableOnClickOutside.setOnClickListener(view -> drawDialog(R.layout.dialog_layout,
                DialogOptions.DIALOG_GRAVITY_BOTTOM,
                "This Dialog can be canceled by click surface area",
                0,
                false,
                true));



    }



    // Method to draw Dialog UI
    private void drawDialog(@LayoutRes int layoutId,
                            DialogOptions layoutGravityOption,
                            String dialogText,
                            @AnimRes int animationId,
                            boolean isFixed,
                            boolean cancelableOnClickOutside){


        dialog = new AdvancedDialogView(this);

        dialog.setCancelable(isFixed);


        dialog.cancelDialogOnClickOutside(cancelableOnClickOutside);
        dialog.setDialogResourceByLayoutId(layoutId, layoutGravityOption, false);




        // Listen on Dialog Load event, so we can safely query drawn view and to interact with it
        dialog.setUILoadListener(new AdvancedDialogView.OnDialogUiLoadListener() {
            /**
             * This method will be invoked when the passed Layout File has been loaded
             */
            @Override
            public void onUiLoaded() {

                // Get textView, you can get your own view here :) that you have passed
                TextView dialogTextView = dialog.getViewByIdentity(R.id.dialog_text, new TextView(MainActivity.this));

                // Set the text
                dialogTextView.setText(dialogText);


            }
        });


        // If passed animation ID to set animation
        if(animationId != 0){

            dialog.setUiAnimation(animationId);
        }


    }
}