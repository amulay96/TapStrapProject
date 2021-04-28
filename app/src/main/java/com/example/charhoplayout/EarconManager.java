package com.example.charhoplayout;

import android.speech.tts.TextToSpeech;

// Class for Earcons: Audio Queues
public class EarconManager {

    public static String selectEarcon = String.valueOf(R.string.select_earcon);
    public static String deleteChar = String.valueOf(R.string.delete_char);
    public static String flowshift = String.valueOf(R.string.flow_shift);

    // Setup the audio queues preset in RAW folder (app -> res -> raw)
    public void setupEarcons(TextToSpeech tts, String packageName)
    {
        tts.addEarcon(selectEarcon,packageName,R.raw.select);
        tts.addEarcon(deleteChar,packageName,R.raw.trash);
        tts.addEarcon(flowshift,packageName,R.raw.flowshift);
    }
}
