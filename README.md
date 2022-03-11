# Advanced Dialog View Android - Customizable Dialog View

<br />

[![](https://jitpack.io/v/nurujjamanpollob/Advanced-Dialog-View-Android-Customizable-Dialog-View.svg)](https://jitpack.io/#nurujjamanpollob/Advanced-Dialog-View-Android-Customizable-Dialog-View) [![CodeFactor](https://www.codefactor.io/repository/github/nurujjamanpollob/advanced-dialog-view-android-customizable-dialog-view/badge)](https://www.codefactor.io/repository/github/nurujjamanpollob/advanced-dialog-view-android-customizable-dialog-view)

<br />

This library is simplyfying Dialog Viewing experience inside your application. 
This simple library to help you save your time. 
Anyway, this library will be come with more extra features in future, if needed.

<br />

This library intended to show your designed layout resource file to show in dialog view with given parameter and control. 
Currently, it supports draw content from bottom, center, top with minimal typing and configurable options come out of box.

<br />

This Readme.md will be improved later. Anyway, in this project I have a sample app with some code to show it's feature, so be sure to check the app module.

Here is some basic comparison with standard AlertDialog class:


<table style="width:100%">
  <tr>
    <th>AlertDialog Class</th>
    <th>AdvancedDialogView Class</th>
  </tr>
  <tr>
    <td>Has fixed direction</td>
    <td>Can be Flexible</td>
  </tr>
  <tr>
    <td>Comes with pre-designed layout</td>
    <td>No pre-designed Layout</td>
  </tr>
  
   <tr>
    <td>Never support draw Custom Layout Src View</td>
    <td>Has support</td>
  </tr>
  
  <tr>
  <td> No Null pointer safety </td>
  <td> Designed to avoid Null Pointer </td>
 </tr>
 
  <tr>
    <td>Never support View Animation</td>
    <td>Has support for Animation</td>
  </tr>
  
   <tr>
    <td>Limited Customization</td>
    <td>Highly Customizable</td>
  </tr>
 
 
</table>

<br />
<br />

So as far, it's good if you are looking for customizable Dialog View with full support for View Events. Okay, let's into the implementation:

<br />

<h3> Implementation: </h3>

<br />


Open project level <b>build.gradle</b> file, and add this following content:

<br />

<pre>
<code> 	
allprojects {
    repositories {
    ...
    maven { url 'https://jitpack.io' }
		
    }

}
  
 </code>
 </pre>
 
 <br />
 
<b>Note:</b><i>Newer gradle android project prefer settings.gradle repository configuration over project level build.gradle configuration.
In that case, you should add following code in your settings.gradle file: </i>
	
<br />
<br />
	
<pre>
<code> 
pluginManagement {
	repositories {
        ...
        maven { url 'https://jitpack.io' }
              
    }
}
 
</code>
</pre>
 
 <br />
 <br />
 
Then, in your app module level <b>build.gradle</b> add this following content in dependencies:
 
<br />
 
 
<pre>
<code> 	
   dependencies {
	        
        implementation 'com.github.nurujjamanpollob:Advanced-Dialog-View-Android-Customizable-Dialog-View:1.3'
   
   }

  
 </code>
 </pre>
 
 
 <br />
 <br />
 
 
So, your project configuration is done.
 
 
<br />

Now, you need to go your app module and create a layout file for draw on screen as a Dialog. 
<br />
In this example, I will simply create a layout file named <b><i> dialog_layout </i></b> and add this following content:

<br />


<?xml version="1.0" encoding="utf-8"?>
<!--
  ~ Copyright (c) 2022 Nurujjaman Pollob, All Right Reserved.
  ~        Licensed under the Apache License, Version 2.0 (the "License");
  ~        you may not use this file except in compliance with the License.
  ~        You may obtain a copy of the License at
  ~
  ~             http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~        Unless required by applicable law or agreed to in writing, software
  ~        distributed under the License is distributed on an "AS IS" BASIS,
  ~        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~        See the License for the specific language governing permissions and
  ~        limitations under the License.
  -->

<androidx.constraintlayout.widget.ConstraintLayout

    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:background="@color/black"
    android:layout_height="wrap_content">


    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:id="@+id/dialog_text"
        android:padding="20dp"
        android:textColor="@color/white"
        android:textSize="18sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />


<br />
<br />

<p>
Okay, open your activity where you gonna show your dialog, 
<br />And now I am gonna show you how I simply gonna create a dialog with this layout file :) <br />
<br />
Add following import:
</p>
<pre>
<code> 
import dev.nurujjamanpollob.uicollection.advanceddialogview.AdvancedDialogView;
import dev.nurujjamanpollob.uicollection.advanceddialogview.DialogOptions;
</code>
</pre>

<br />

<p>Lets head to the method you gonna create this Dialog by. And then add following code:
</p>
<pre>
<code>
AdvancedDialogView dialogView = new AdvancedDialogView(context);
dialogView.setDialogResourceByLayoutId(R.layout.dialog_view, DialogOptions.DIALOG_GRAVITY_BOTTOM, false);
</code>
</pre>
<br />
<p>
This code is enough for draw a custom dialog with draw content from bottom. 
<br />
Anyway, you can use DialogOptions.DIALOG_GRAVITY_TOP for draw content from top, 
<br />And DialogOptions.DIALOG_GRAVITY_CENTER for draw content in the center.
<br />

</p>

<br />

<h2> What if I need to interact with Layout Object Views?</h2>

<br />

This is simple. You need to add following code to your existing code, so the final code will be like this:

<pre>
<code>

AdvancedDialogView dialogView = new AdvancedDialogView(context);
dialogView.setDialogResourceByLayoutId(R.layout.dialog_view, DialogOptions.DIALOG_GRAVITY_BOTTOM, false);
dialogView..setUILoadListener(new AdvancedDialogView.OnDialogUiLoadListener() {
            /**
             * This method will be invoked when the passed Layout File has been loaded
             */
            @Override
            public void onUiLoaded() {

                // Get textView, you can get your own view here :) that you have passed
                TextView dialogTextView = dialog.getViewByIdentity(R.id.dialog_text, new TextView(MainActivity.this));

                // Set the text
                dialogTextView.setText("Hello, isn't AdvancedDialogView simple and cool?");


            }
        });
	
</code>
</pre>

<br />
<br />


<b>Okay, how to dismiss the Dialog UI?</b>

<pre>
<code> dialogView.closeDialogView();</code>
</pre>
<br />
<br />

## How to use dialog timeout?
<br />
<pre>
<code>
dialogView.closeDialogUiAfterMillis(int timeoutAfterMillis);
</code>
</pre>

<br />
<b>For implementation guide, please see this sample Android activity file from here: <a href="https://github.com/nurujjamanpollob/Advanced-Dialog-View-Android-Customizable-Dialog-View/blob/master/app/src/main/java/com/example/advanceddialogtest/advanceddialogview/MainActivity.java">MainActivity.Java</a>
<br />

<h2> Demo </h2>

<br />

<image src="https://raw.githubusercontent.com/nurujjamanpollob/Advanced-Dialog-View-Android-Customizable-Dialog-View/master/advanced%20dialog%20view.gif"
style="width: 40%; height: 40%;"></img>

<h2> Documentation </h2>


<b>Coming SOOOOOOOOOOOn!</b>
