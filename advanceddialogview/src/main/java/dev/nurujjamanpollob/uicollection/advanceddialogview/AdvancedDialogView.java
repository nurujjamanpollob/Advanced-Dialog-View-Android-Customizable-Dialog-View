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

package dev.nurujjamanpollob.uicollection.advanceddialogview;


import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.*;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;


/**
 * Advanced Dialog with full support for adding custom view, and to deal with custom views.
 * This class allow you to replace Dialog View without re-initiate them, and covers The issue with
 * that come with getting NullPointerException for View, that is Being erased from Android System,
 * And you try to listen for its event. which is logical, and impossible to fix without create a new instance
 * of this class and separately Query Views that returns Not-Null.
 * <p>
 * I fixed this issue, you are allowed to have Views, which may be Null if it's root is being erased,
 * and will give you correct callback, if you rollback to this view again.
 * <p>
 * Anyway Inflating custom View can be easy, but managing?
 * It may be easy but tricky also.
 * <p>
 * Also, If you have a class, that needs to show different kind of Dialogs with Views,
 * manage View callback from one place, tired of initiate instance of Dialog again and again, then, it's for you.
 * <p>
 * So, Anyway I have a deal for you. Just inflate a view with setDialogResourceByLayoutId(id),
 * Which will draw content based on default or your provided layout_gravity option.
 * <p>
 * So, your layout file will get inflated.
 * So, you might went to listen on View events like setOnClickListener, setOnLongClickListener.
 * Simply, calling findViewById(id) from your activity / fragment instance will give you NullPointerException.
 * Here is a fix:
 * Just set a listener like:
 * BottomViewDialog.setUILoadListener(new BottomDialogView.OnDialogUiLoadListener() {
 *
 * @Override public void onUiLoaded() {
 * <p>
 * // Try to query your view inside this call
 * <p>
 * <p>
 * }
 * });
 * <p>
 * <p>
 * Okay, this listener ensures that all View is 100% drawn by Android System.
 * So, you will be safely able to query your target View,
 * again findViewById(id) from your activity / fragment instance will give you NullPointerException.
 * Why? because, the View you have passed, it is inflated by the context of BottomDialogView, not by Context of
 * Your activity / fragment.
 * <p>
 * So, this come with a solution
 * just call getViewByIdentity(viewId, ExpectedViewType) from interface BottomDialogView.OnDialogUiLoadListener{ onUiLoaded(){
 * <p>
 * <p>
 * }} method.
 * <p>
 * <p>
 * Okay, here is code example:
 * <p>
 * BottomViewDialog.setUILoadListener(new BottomDialogView.OnDialogUiLoadListener() {
 * *                 @Override
 * *                 public void onUiLoaded() {
 * *
 * *                 // get signUp Button instance from inflated layout
 * Button signUp = BottomViewDialog.getViewByIdentity(R.id.sign_up_button, new Button(Context));
 * *
 * *                 }
 * *        });
 * <p>
 * Easy. No exception.
 */

public class AdvancedDialogView extends AppCompatDialog {

    private final Context context;
    private Boolean cancelDialogOnClickOuside = false;
    private CoordinatorLayout rootView;
    private boolean isShowing = false;
    private OnDialogUiLoadListener uiLoadListener;
    private int layoutId = 0;
    private DialogOptions dialogOptions;
    private boolean isCancelable = true;


    /**
     * Constructor parameter of AdvancedDialogView, this constructor expected to be created by class instance
     *
     * @param context activity context, which under the dialog view will be created.
     */
    public AdvancedDialogView(@NonNull Context context) {
        super(context);

        this.context = context;
    }


    {
        // Set window feature before drawing content on screen.
        requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    /**
     * Method to set Dialog Layout View programmatically.
     * This method should be called from MainThread, if you call it from Other than MainThread, you will get a Exception.
     * it either @link CalledFromWrongThreadException or either leaking View.
     * If you are getting exception, please pass drawInFixedMode true and update Constructor parameter with a Looper.getMainLooper() instance
     *
     * @param dialogLayoutID          layout ID of dialog view that needs to be drawn on screen.
     * @param dialogGravityOptions    Enum to set UI gravity option
     * @param drawInFixedModeIfNeeded A boolean to determine If the Dialog Ui should be draw in the fixed mode.
     *                                It's value simply ignored if there is no Exception.
     */

    @MainThread
    public void setDialogResourceByLayoutId(@LayoutRes int dialogLayoutID, DialogOptions dialogGravityOptions, Boolean drawInFixedModeIfNeeded) {

        this.layoutId = dialogLayoutID;
        this.dialogOptions = dialogGravityOptions;


        try {
            drawDialogUI(dialogLayoutID, false, dialogOptions);
        } catch (Exception ignored) {

            if (drawInFixedModeIfNeeded) {
                new Handler(Looper.getMainLooper()).post(() -> drawDialogUI(dialogLayoutID, false, dialogOptions));
            }

        }


    }

    /**
     * Method to set cancel Dialog On Click Outside of Dialog View
     *
     * @param isCancelOnClickOutside a Boolean to set If the dialog should be closed if clicked outside of root View
     */

    public void cancelDialogOnClickOutside(Boolean isCancelOnClickOutside) {

        this.cancelDialogOnClickOuside = isCancelOnClickOutside;
    }


    // Method to dismiss Dialog when clicked outside
    private void cancelDialogOnClickOutside() {

        rootView.setOnClickListener(v -> {

            if (cancelDialogOnClickOuside) {
                onBackPressed();
            }
        });


    }


    /**
     * Method to get status of If Dialog View is showing
     *
     * @return true if Dialog View visible on the Screen, False Otherwise.
     */

    public Boolean isDialogShowing() {

        return isShowing;
    }


    /**
     * This method ensures a View object that is being need to get.
     *
     * @param id           int to get a View by it's unique identifier.
     * @param viewExpected the kind of View, you expect to get, the passed Object will be returned,
     *                     If the requested View get by unique identifier is not found.
     * @param <T>          The kind of View instance, you are looking for.
     * @return either a resolved Not-Null View, or the View object from <T> viewExpected.
     * @throws ClassCastException if the View instance by unique identity number is null,
     *                            and your expectation <T> viewExpected, or initiation of <T extends View> is wrong.
     */
    @NonNull
    public <T extends View> T getViewByIdentity(@IdRes int id, T viewExpected) {

        T view = null;
        try {
            view = rootView.findViewById(id);
        } catch (Exception ignored) {
        }

        return view != null ? view : viewExpected;

    }

    /**
     * Interface for Callback for Dialog UI Listener
     */
    public interface OnDialogUiLoadListener {

        /**
         * This method will be invoked when the passed Layout File has been loaded
         */
        default void onUiLoaded() {
        }

        /**
         * When the Dialog UI got inflated, this method will be invoke with Dialog Root View instance.
         * So, you can further customize the Dialog UI if needed
         *
         * @param view the instance of Dialog Layout Placeholder.
         */
        default void rootUI(CoordinatorLayout view) {
        }

        /**
         * This method will be invoked, when you passed a animation resource to animate Dialog UI,
         * And the animation got finished Loading.
         *
         * @param animation   the Animation has been finished.
         * @param animationId the Identity number of Animation resource, you passed to.
         */
        default void onAnimationFinished(Animation animation, int animationId) {
        }

        /**
         * This method will be invoked, if you override this method in listener interface,
         * and your passed animation instance is being started creating by Android System
         *
         * @param animation   a instance of Animation class, that created by AdvancedDialogView class, that is about to start
         * @param animationId your passed animation resource ID
         */
        default void onAnimationStart(Animation animation, int animationId) {
        }

        /**
         * This method will be invoked, if you override this method in listener interface,
         * and your passed animation instance is already finished and it's going to be repeat again.
         *
         * @param animation   a instance of Animation class, that created by AdvancedDialogView class, that is about to start
         * @param animationId your passed animation resource ID
         */
        default void onAnimationRepeat(Animation animation, int animationId) {
        }


    }


    /**
     * Method to set listener on various Dialog Ui Load event.
     *
     * @param uiLoadListener a new Instance of @link OnDialogUiLoadListener to receive callback.
     */
    public void setUILoadListener(OnDialogUiLoadListener uiLoadListener) {

        this.uiLoadListener = uiLoadListener;

        drawDialogUI(layoutId, isShowing(), dialogOptions);


    }


    /**
     * Method to draw whole dialog UI
     *
     * @param id            layout resource ID
     * @param skipExisting  during inflate new View, If a existing Dialog UI is showing,
     *                      This parameter sets a flag to re-render new view or keep existing one.
     * @param dialogOptions the Dialog DRAW option. currently, it supports views draw from BOTTOM, CENTER, TOP
     */
    @MainThread
    private void drawDialogUI(int id, boolean skipExisting, DialogOptions dialogOptions) {


        if (!skipExisting) {
            // Set content view
            setContentView(R.layout.custom_dialog_layout);
            Window window = getWindow();
            window.getDecorView().setBackgroundColor(Color.TRANSPARENT);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            rootView = findViewById(R.id.custom_dialog_layout_root);
            // So, inflate the view and attach to CoordinatorLayout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            CoordinatorLayout cl = getViewByIdentity(R.id.bottom_view_dialog_root_coordinatorelayout, new CoordinatorLayout(context));


            //If Dialog Ui gravity mode is defined
            if (dialogOptions != null) {

                setDialogUiDirectionMode(cl, dialogOptions);


            } else {


                // Use default Ui gravity = bottom
                setDialogUiDirectionMode(cl, DialogOptions.DIALOG_GRAVITY_BOTTOM);

            }

            inflater.inflate(id, cl, true);
            // Set Dialog click outside listener
            cancelDialogOnClickOutside();
            isShowing = true;


            show();

        }


        if (isDialogShowing()) {

            if (uiLoadListener != null) {

                if (rootView != null) {

                    uiLoadListener.onUiLoaded();
                    uiLoadListener.rootUI(rootView);
                }

            }
        }


    }


    /**
     * Internal method to set Layout gravity Mode
     *
     * @param cl            instance of Root placeholder for Dialog View
     * @param dialogOptions Enum to determine the UI gravity mode to set.
     */
    private void setDialogUiDirectionMode(CoordinatorLayout cl, DialogOptions dialogOptions) {

        CoordinatorLayout.LayoutParams coOrdinatorParam = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);

        // Set Dialog Gravity
        coOrdinatorParam.gravity = dialogOptions == DialogOptions.DIALOG_GRAVITY_BOTTOM ? Gravity.BOTTOM :
                dialogOptions == DialogOptions.DIALOG_GRAVITY_TOP ? Gravity.TOP : Gravity.CENTER;

        cl.setLayoutParams(coOrdinatorParam);

    }

    /**
     * Method to set UI animation when Creating new Dialog View
     *
     * @param animationResId animation resource ID to set UI animation.
     */

    public void setUiAnimation(@AnimRes int animationResId) {

        if (rootView != null) {

            Animation dialogAnimation = AnimationUtils.loadAnimation(context, animationResId);

            // Animate The views
            rootView.setAnimation(dialogAnimation);

            dialogAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                    if (uiLoadListener != null) {
                        uiLoadListener.onAnimationStart(animation, animationResId);
                    }

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    if (uiLoadListener != null) {
                        uiLoadListener.onAnimationFinished(animation, animationResId);
                    }

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                    if (uiLoadListener != null) {
                        uiLoadListener.onAnimationRepeat(animation, animationResId);
                    }
                }
            });
        }

    }


    /**
     * Method to clear dialog interface, not work for fixed dialog
     */
    public void closeDialogView() {

        this.cancel();

    }


    /**
     * Method to set if the dialog ui can be can be cancellable or Not
     */
    @Override
    public void setCancelable(boolean flag) {
        isCancelable = flag;
        super.setCancelable(flag);
    }

    /**
     * Method to close dialog UI automatically after x milliseconds
     *
     * @param millis close created dialog after milliseconds
     */
    public void closeDialogUiAfterMillis(int millis) {

        new Handler(Looper.getMainLooper()).postDelayed(this::closeDialogView, millis);
    }

    /**
     * This method will help close this dialog view, if the call with closeDialogView() or super.cancel() call is unsuccessful
     * Now, it's used to close cancelable dialog view by press back button.
     */
    @Override
    public void onBackPressed() {

        if (!isCancelable) {
            closeDialogView();
            super.onBackPressed();
        }
    }

    /**
     * Method to cancel the dialog UI
     */
    @Override
    public void cancel() {
        if (!isCancelable) {
            super.cancel();
        }
    }
}
