package com.example.charhoplayout;

import java.util.HashMap;

// Class : Counting Total Number of Taps
public class CountTotalTaps {

    /*
    * HashMap: (Key:Value) Pair for Counting the taps for the given task
    * eg: AlphabetMode Forward Navigation -> Performed using Index Finger -> count("alModeForward") is incremented by 1
    * */
    private HashMap<String, Integer> counts; // To Count the Taps

    public CountTotalTaps(HashMap<String, Integer> counts) {

        this.counts = counts;

        this.counts.put("alModeForward", 0);
        this.counts.put("alModeBackward", 0);
        this.counts.put("alModeHopping", 0);
        this.counts.put("alModeSelection", 0);
        this.counts.put("alModeSpeakOut", 0);
        this.counts.put("alMOdeDeletion", 0);
        this.counts.put("alModeReset", 0);

        this.counts.put("nmModeEnter", 0);
        this.counts.put("nmModeExit", 0);
        this.counts.put("nmModeForward", 0);
        this.counts.put("nmModeBackward", 0);
        this.counts.put("nmModeSelection", 0);
        this.counts.put("nmModeSpeakOut",0);
        this.counts.put("nmModeDeletion", 0);


        this.counts.put("specialCharModeEnter", 0);
        this.counts.put("specialCharModeExit", 0);
        this.counts.put("specialCharModeForward", 0);
        this.counts.put("specialCharModeBackward", 0);
        this.counts.put("specialCharModeSelection", 0);
        this.counts.put("specialCharModeSpeakOut",0);
        this.counts.put("specialCharModeDeletion",0);

        this.counts.put("editModeEnter", 0);
        this.counts.put("editModeExit", 0);
        this.counts.put("editModeForwardNav", 0);
        this.counts.put("editModeBackwardNav",0);
        this.counts.put("editModeDecisionNav", 0);
        this.counts.put("editModeDecisionSelection", 0);
        this.counts.put("editModeSpeakOut",0);
        this.counts.put("editModeDeletion", 0);

        this.counts.put("autoSuggestionModeFetch",0);
        this.counts.put("autoSuggestionModeExit",0);
        this.counts.put("autoSuggestionModeForwardNav",0);
        this.counts.put("autoSuggestionModeSelection",0);
    }

    /*
    * Increment the counter value for the corresponding key of HashMap
    * */
    public void performCounting(String val) {
        Integer i = counts.get(val);
        i++;
        counts.put(val, i);
    }

    /*
    * Reset count of gestures for respective action gesture
    * */
    public CountTotalTaps resetCounting()
    {
         CountTotalTaps countTotalTaps = new CountTotalTaps(new HashMap<String, Integer>());
         return  countTotalTaps;
    }


    /*
    * Calculate and Display the Total Number of Gesture Taps on the TextView (upon button action event btnGetInfo)
    * */
    public String displayTotalTapsCount()
    {
        String info = "";
//        info = "Taps Info:\nTotal Number Of Gestures: "+counts.get("TotalGestureCount");

        info = "Taps Info:\nAlphabetical Mode:\n\n"
                +"Forward Taps: "+counts.get("alModeForward")
                +"\nBackword Taps: "+counts.get("alModeBackward")
                +"\nSkipping Taps: "+counts.get("alModeHopping")
                +"\nSelection Taps: "+counts.get("alModeSelection")
                +"\nDeletion Taps: "+counts.get("alMOdeDeletion")
                +"\nSpeakOut Taps"+counts.get("alModeSpeakOut")
                +"\nStopping Taps: "+counts.get("alModeReset")
                +"\n\n"+"Number Mode Tapping Info:\n"
                +"Enter Number Mode Taps: "+counts.get("nmModeEnter")
                +"\nExit Number Mode Taps: "+counts.get("nmModeExit")
                +"Forward Taps: "+counts.get("nmModeForward")
                +"\nBackword Taps: "+counts.get("nmModeBackward")
                +"\nSelection Taps: "+counts.get("nmModeSelection")
                +"\nSpeakOut Taps: "+counts.get("nmModeSpeakOut")
                +"\nDeletion Taps: "+counts.get("nmModeDeletion")
                +"\n\n"+"Special Char Mode Tapping Info:\n"
                +"Enter Special Char Mode Taps: "+counts.get("specialCharModeEnter")
                +"\nExit Special Char Mode Taps: "+counts.get("specialCharModeExit")
                +"Forward Taps: "+counts.get("specialCharModeForward")
                +"\nBackword Taps: "+counts.get("specialCharModeBackward")
                +"\nSelection Taps: "+counts.get("specialCharModeSelection")
                +"\nSpeakOut Taps: "+counts.get("specialCharModeSpeakOut")
                +"\nDeletion Taps: "+counts.get("specialCharModeDeletion")
                +"\n\n"+"Edit Mode Tapping Info:\n"
                +"Enter Edit Mode Taps: "+counts.get("editModeEnter")
                +"\nExit Edit Mode Taps: "+counts.get("editModeExit")
                +"\nForward Nav Taps: "+counts.get("editModeForwardNav")
                +"\nBackward Nav Taps: "+counts.get("editModeBackwardNav")
                +"\nEdit Options Nav Taps: "+counts.get("editModeDecisionNav")
                +"\nEdit Options Selection Taps: "+counts.get("editModeDecisionSelection")
                +"\nEdit Mode Speak Out Taps: "+counts.get("editModeSpeakOut")
                +"\n\n"+"Auto Suggestion Mode Tapping Info:\n"
                +"Auto Suggestion Mode Fetch Taps: "+counts.get("autoSuggestionModeFetch")
                +"\nAuto Suggestion Mode Exit Taps: "+counts.get("autoSuggestionModeExit")
                +"\nAuto Suggestion Mode Forward Taps: "+counts.get("autoSuggestionModeForwardNav")
                +"\nAuto Suggestion Mode Selection Taps: "+counts.get("autoSuggestionModeSelection")
                /*+"\nSelection (Edit/Replace) Edit Mode Taps: "+counts.get("Selection in Edit")
                +"\n Deletion in Edit Taps:  "+counts.get("Deletion in Edit")*/;

        int total_taps;

        total_taps = counts.get("alModeForward")
                + counts.get("alModeBackward")
                + counts.get("alModeHopping")
                + counts.get("alModeSelection")
                + counts.get("alMOdeDeletion")
                + counts.get("alModeSpeakOut")
                + counts.get("alModeReset")
                + counts.get("nmModeEnter")
                + counts.get("nmModeExit")
                + counts.get("nmModeForward")
                + counts.get("nmModeBackward")
                + counts.get("nmModeSelection")
                + counts.get("nmModeSpeakOut")
                + counts.get("nmModeDeletion")
                + counts.get("specialCharModeEnter")
                + counts.get("specialCharModeExit")
                + counts.get("specialCharModeForward")
                + counts.get("specialCharModeBackward")
                + counts.get("specialCharModeSelection")
                + counts.get("specialCharModeSpeakOut")
                + counts.get("specialCharModeDeletion")
                + counts.get("editModeEnter")
                + counts.get("editModeExit")
                + counts.get("editModeForwardNav")
                + counts.get("editModeBackwardNav")
                + counts.get("editModeDecisionNav")
                + counts.get("editModeDecisionSelection")
                + counts.get("editModeSpeakOut")
                + counts.get("editModeDeletion")
                + counts.get("autoSuggestionModeFetch")
                + counts.get("autoSuggestionModeExit")
                + counts.get("autoSuggestionModeForwardNav")
                + counts.get("autoSuggestionModeSelection");

        return info+"\nTotal Number of Taps: "+total_taps;
    }
}

