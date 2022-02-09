# Advanced Dialog View Android - Customizable Dialog View

<br />

[![](https://jitpack.io/v/nurujjamanpollob/Advanced-Dialog-View-Android-Customizable-Dialog-View.svg)](https://jitpack.io/#nurujjamanpollob/Advanced-Dialog-View-Android-Customizable-Dialog-View)

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
    <td>Limited Customizable</td>
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
<code> 	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
 </code>
 </pre>
 
 
 Then, in your app module level <b>build.gradle</b> add this following content in dependencies:
 
 <br />
 
 
<pre>
<code> 	dependencies {
	        implementation 'com.github.nurujjamanpollob:Advanced-Dialog-View-Android-Customizable-Dialog-View:30b9e2050f'
	}

  
 </code>
 </pre>
