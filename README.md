<h1> Screenless Typing : KeyflowStandAlone</h1>

This work is part of a larger research project in the area of Human-Computer Interaction at Indiana University (PI: Davide Bolchini) focused on the exploration of <b>‘Screenless Typing’</b> approaches to text entry. The goal is to make typing efficient and easy for the blind and visually impaired through the development of novel, entirely auditory keyboards that completely circumvent the use of the screen and are controllable by off-the-shelf finger worn devices. 

<b>‘KeyflowStandAlone’</b> is a manifestation of one of our auditory keyboard systems. It presents an auditory keyboard layout containing Alphabetical characters (A-Z), numbers (0-9) and Selected Special Characters, and controllable by TapStrap for input and navigation.  

The above codebase is modularized into definitions and actions associated with alphabets, numbers and special characters along with their syntagmatic alterations and suggestions. 

 

<b>Configuration Details:</b>  

Android Studio: 3.5.2 

Android SDK Tool: 26.1.1  

Android API 29 (10.0) 

External SDK: Tap With Us https://github.com/TapWithUs/tap-android-sdk  

JAVA JDK 8  

Android Dependency: 'com.tapwithus:tap-android-sdk:0.3.3' 

This research is based upon work supported by NSF grant IIS-1909845 and a Google Faculty Research Award. 

<b>System Componenets:</b>

<b>1.Android Device:</b> The android app implemented based on android 10.o and tapstrap – android SDK and integrated in android mobile device.

<b>2.Tapstrap:</b> A physical device for visually impaired user to perform screenless typing

<b>3.Gesture Data:</b> Collected from tapstrap and converted into corresponding value as per the performed gesture 

<b>4.Google Text-to-speech Engine:</b> Service to connvert text into audio

<b>5.ArrayLists:</b> List data structure to store alphabets, numbers and special characters

<b>6.Tap Listener:</b> Action listerner to map the physical gestures to corresponding keyflow actions

<b>7.Keyflow Modes:</b> Modularized definitions and action for alphabets, numbers and special characters

<b> Functionalities:</b>

<table>
 <tr>
  <td>Sr No.</td>
  <td>Gesture Input</td>
  <td>Actions</td>
  </tr>
 
 <tr>
 <td></td>
 <td><b>Primary Gestures</b></td>
 <td></td>
  </tr>
  
 <tr>
 <td>1.</td>
 <td>Thumb</td>
 <td>Select a Character</td>
  </tr>
  
 <tr>
 <td>2.</td>
 <td>Index Finger</td>
 <td>Move forward by one character</td>
 </tr>

 <tr>
 <td>3.</td>
 <td>Middle Finger</td>
 <td>Move backward by one character</td>
 </tr>

 <tr>
 <td>4.</td>
 <td>Index + Middle Finger</td>
 <td>Skip Character by 5</td>
 </tr>

 <tr>
 <td>5.</td>
 <td>Pinky Finger</td>
 <td>Delete Character</td>
 </tr>

 <tr>
 <td>6.</td>
 <td>Ring Finger</td>
 <td>Reset</td>
 </tr>

 <tr>
 <td>7.</td>
 <td>Index + Middle + Ring Finger</td>
 <td>Edit Mode</td>
 </tr>

 <tr>
 <td>8.</td>
 <td>Index + Middle + Ring + Pinky Finger</td>
 <td>Speak Out</td>
 </tr>

 <tr>
 <td></td>
 <td><b>Edit Mode</b></td>
 <td></td>
 </tr>

 <tr>
 <td>9.</td>
 <td>Ring Finger</td>
 <td>Toggle in Edit Mode</td>
 </tr>

 <tr>
 <td>10.</td>
 <td>Ring finger tap followed by Selection tap on spoken ‘Insert’ cue </td>
 <td>Insert a character at a character</td>
 </tr>

 <tr>
 <td>11.</td>
 <td>Ring finger tap followed by Selection tap on spoken ‘Replace’ cue </td>
 <td>Replace a character at a character</td>
 </tr>

 <tr>
 <td></td>
 <td><b>Micro-keyflows</b></td>
 <td></td>
  </tr>

 <tr>
 <td>12.</td>
 <td>Thumb + Index Finger</td>
 <td>Numbers Mode</td>
 </tr>

 <tr>
 <td>13.</td>
 <td>Thumb + Middle Finger</td>
 <td>Special Characters Mode</td>
 </tr>

 <tr>
 <td>14.</td>
 <td>Thumb + Ring Finger</td>
 <td>Autosuggestions Mode</td>
 </tr>

 </table>

<b>(Principal Investigator: Dr. Davide Bolchini: https://soic.iupui.edu/people/davide-bolchini/) 

Graduate Researchers:</b> 

 Pooja Vazirani (Human Computer Interaction - IUPUI) 

Sharvari Jalit (Human Computer Interaction - IUPUI) 

Anup A. Mulay (Computer and Information Science - IUPUI) 

Vidhi Patel (Computer and Information Science - IUPUI) 

