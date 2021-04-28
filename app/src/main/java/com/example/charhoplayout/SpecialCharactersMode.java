package com.example.charhoplayout;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.charhoplayout.EarconManager.deleteChar;


// Special Char Mode Class
public class SpecialCharactersMode {

    /*
     * Variable Declaration for Special Char Mode
     * */
    Context context;

    ArrayList<String> spSuggestions;
    ArrayAdapter<String> spSuggestionsAdapter;

    boolean spFront,spBack,spPrev;
    int spHeadPoint;
    String searchValue;

    String del_char;

    //Special Class Constructor
    public SpecialCharactersMode(Context context)
    {

        this.context = context;
    }

    // Initialize Special Characters
    public void spModeInitialise()
    {
        //Adding Special Characters into Different Plane
        String spCharacters = "&,.,@,!,*,#,$,%,?";
        spSuggestions = new ArrayList<>(Arrays.asList(spCharacters.split(",")));
        spSuggestionsAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,spSuggestions);
        spSuggestionsAdapter.notifyDataSetChanged();

        spFront=false;
        spBack=false;
        spHeadPoint=0;
        spPrev=false; //#############
    }

    /*
     * Number Mode : Forward Navigation
     * & -> . -> @ ..... -> ? -> & ----> circle back to &
     * */
    public void spModeForward(TextToSpeech tts)
    {
        if(spFront)
        {
            spHeadPoint = spHeadPoint + 1;
            spFront=false;
        }

        if(spHeadPoint==spSuggestions.size())
        {
            spHeadPoint = 0;
        }

        if(spSuggestions.get(spHeadPoint).equals("#"))
        {
            tts.speak("Hash", TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else if(spSuggestions.get(spHeadPoint).equals("$"))
        {
            tts.speak("Dollar",TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else
        {
            tts.speak(spSuggestions.get(spHeadPoint),TextToSpeech.QUEUE_FLUSH,null,null);
        }

        //tts.speak(spSuggestions.get(spHeadPoint),TextToSpeech.QUEUE_FLUSH,null,null);
        spHeadPoint++;
        spBack=true;
    }

    /*
     * Number Mode : Backward Navigation
     * ...& <- . <- @  ... <- &
     * */
    public void spModeBackward(TextToSpeech tts)
    {
        if (spBack) {
            spHeadPoint = spHeadPoint - 2;
            spBack = false;
        }
        else
        {
            spHeadPoint--;
        }

        if(spHeadPoint < 0 )
        {
            spHeadPoint =spSuggestions.size()-1;
        }


        if(spSuggestions.get(spHeadPoint).equals("#"))
        {
            tts.speak("Hash",TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else if(spSuggestions.get(spHeadPoint).equals("$"))
        {
            tts.speak("Dollar",TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else
        {
            tts.speak(spSuggestions.get(spHeadPoint),TextToSpeech.QUEUE_FLUSH,null,null);
        }
        //tts.speak(spSuggestions.get(spHeadPoint),TextToSpeech.QUEUE_FLUSH,null,null);
        spFront=true;
        spPrev=true;
    }

    /*
     * Special Char Mode : Select a Special Character
     * '@' is selected and spoken high pitch
     * */
    public String spModeSelection(TextToSpeech tts, String alreadyTyped/*, String word*/)
    {
        if(!spPrev)
        {
            searchValue = spSuggestions.get(spHeadPoint-1);

            if(EditMode.editMode & EditMode.decision.equals("Insert"))
            {
                if(alreadyTyped.charAt(EditMode.insertion_index) == ' ')
                {
                    EditMode.speakInsertedAtSpace(tts,searchValue);
                }
                else
                {
                    EditMode.speakReplacedAtCharacter(tts,searchValue,alreadyTyped.charAt(EditMode.insertion_index));
                }
                alreadyTyped = EditMode.insertInEdit(tts,alreadyTyped,searchValue,EditMode.insertion_index);

                if(MainActivity.isNumberMode == true | MainActivity.numberModeToggle == 1 | MainActivity.isspecialCharMode == true | MainActivity.spModeToggle==1)
                {
                    MainActivity.isNumberMode = false;
                    MainActivity.numberModeToggle = 0;

                    MainActivity.isspecialCharMode = false;
                    MainActivity.spModeToggle = 0;

                }
                MainActivity.allowSearchScan = true;
            }
            else if(EditMode.editMode & EditMode.decision.equals("Replace"))
            {
                if(alreadyTyped.charAt(EditMode.insertion_index) == ' ')
                {
                    EditMode.speakReplacedAtSpace(tts,searchValue);
                }
                else
                {
                    EditMode.speakReplacedAtCharacter(tts,searchValue,alreadyTyped.charAt(EditMode.insertion_index));
                }
                alreadyTyped = EditMode.replaceInEdit(tts,alreadyTyped,searchValue,EditMode.insertion_index);

                if(MainActivity.isNumberMode == true | MainActivity.numberModeToggle == 1 | MainActivity.isspecialCharMode == true | MainActivity.spModeToggle==1)
                {
                    MainActivity.isNumberMode = false;
                    MainActivity.numberModeToggle = 0;

                    MainActivity.isspecialCharMode = false;
                    MainActivity.spModeToggle = 0;

                }
                MainActivity.allowSearchScan = true;
            }
            else
            {
                if(searchValue.equals(" "))
                {
                    alreadyTyped = addSpaceAtEnd(tts, alreadyTyped, searchValue);
                }
                else
                {
                    alreadyTyped = addAtEnd(tts,alreadyTyped, searchValue);
                }
            }
            spPrev=false;
        }
        else
        {
            if(!spFront)
            {
                searchValue = spSuggestions.get(spHeadPoint-1);

                if(EditMode.editMode & EditMode.decision.equals("Insert"))
                {
                    if(alreadyTyped.charAt(EditMode.insertion_index) == ' ')
                    {
                        EditMode.speakInsertedAtSpace(tts,searchValue);
                    }
                    else
                    {
                        EditMode.speakInsertedAtCharacter(tts,searchValue,alreadyTyped.charAt(EditMode.insertion_index));
                    }
                    alreadyTyped = EditMode.insertInEdit(tts,alreadyTyped,searchValue,EditMode.insertion_index);

                    if(MainActivity.isNumberMode == true | MainActivity.numberModeToggle == 1 | MainActivity.isspecialCharMode == true | MainActivity.spModeToggle==1)
                    {
                        MainActivity.isNumberMode = false;
                        MainActivity.numberModeToggle = 0;

                        MainActivity.isspecialCharMode = false;
                        MainActivity.spModeToggle = 0;

                    }
                    MainActivity.allowSearchScan = true;
                }
                else if(EditMode.editMode & EditMode.decision.equals("Replace"))
                {
                    if(alreadyTyped.charAt(EditMode.insertion_index) == ' ')
                    {
                        EditMode.speakReplacedAtSpace(tts,searchValue);
                    }
                    else
                    {
                        EditMode.speakReplacedAtCharacter(tts,searchValue,alreadyTyped.charAt(EditMode.insertion_index));
                    }
                    alreadyTyped = EditMode.replaceInEdit(tts,alreadyTyped,searchValue,EditMode.insertion_index);

                    if(MainActivity.isNumberMode == true | MainActivity.numberModeToggle == 1 | MainActivity.isspecialCharMode == true | MainActivity.spModeToggle==1)
                    {
                        MainActivity.isNumberMode = false;
                        MainActivity.numberModeToggle = 0;

                        MainActivity.isspecialCharMode = false;
                        MainActivity.spModeToggle = 0;

                    }
                    MainActivity.allowSearchScan = true;
                }
                else
                {
                    if(searchValue.equals(" "))
                    {
                        alreadyTyped = addSpaceAtEnd(tts, alreadyTyped, searchValue);
                    }
                    else
                    {
                        alreadyTyped = addAtEnd(tts,alreadyTyped, searchValue);
                    }
                }
            }
            else
            {
                searchValue=spSuggestions.get(spHeadPoint);

                if(EditMode.editMode & EditMode.decision.equals("Insert"))
                {
                    if(alreadyTyped.charAt(EditMode.insertion_index) == ' ')
                    {
                        EditMode.speakInsertedAtSpace(tts,searchValue);
                    }
                    else
                    {
                        EditMode.speakInsertedAtCharacter(tts,searchValue,alreadyTyped.charAt(EditMode.insertion_index));
                    }
                    alreadyTyped = EditMode.insertInEdit(tts,alreadyTyped,searchValue,EditMode.insertion_index);

                    if(MainActivity.isNumberMode == true | MainActivity.numberModeToggle == 1 | MainActivity.isspecialCharMode == true | MainActivity.spModeToggle==1)
                    {
                        MainActivity.isNumberMode = false;
                        MainActivity.numberModeToggle = 0;

                        MainActivity.isspecialCharMode = false;
                        MainActivity.spModeToggle = 0;

                    }
                    MainActivity.allowSearchScan = true;
                }
                else if(EditMode.editMode & EditMode.decision.equals("Replace"))
                {
                    if(alreadyTyped.charAt(EditMode.insertion_index) == ' ')
                    {
                        EditMode.speakReplacedAtSpace(tts,searchValue);
                    }
                    else
                    {
                        EditMode.speakReplacedAtCharacter(tts,searchValue,alreadyTyped.charAt(EditMode.insertion_index));
                    }
                    alreadyTyped = EditMode.replaceInEdit(tts,alreadyTyped,searchValue,EditMode.insertion_index);

                    if(MainActivity.isNumberMode == true | MainActivity.numberModeToggle == 1 | MainActivity.isspecialCharMode == true | MainActivity.spModeToggle==1)
                    {
                        MainActivity.isNumberMode = false;
                        MainActivity.numberModeToggle = 0;

                        MainActivity.isspecialCharMode = false;
                        MainActivity.spModeToggle = 0;

                    }
                    MainActivity.allowSearchScan = true;
                }
                else
                {
                    if(searchValue.equals(" "))
                    {
                        alreadyTyped = addSpaceAtEnd(tts, alreadyTyped, searchValue);
                    }
                    else
                    {
                        alreadyTyped = addAtEnd(tts, alreadyTyped, searchValue);
                    }
                }
            }
        }
        return alreadyTyped;
    }

    /*
     * Special Char Mode : Delete a character at the end of Typed String
     * Typed String: 'Fish@'
     * eg: 'Fish@' --> '@' will be deleted
     * */
    public String spModeDeletion(TextToSpeech tts,String alreadyTyped/*,String word*/)
    {
        if(alreadyTyped.isEmpty())
        {
            tts.speak("No Character to Delete", TextToSpeech.QUEUE_FLUSH, null, null);
            //nav_index=0;//After Deleting Entirely a word if you select a new word then it should start from first character
        }
        else
        {
            //Normal Deletion from End of the String in Number Mode
            del_char = alreadyTyped.substring(alreadyTyped.length()-1);

            if(del_char.equals(" "))
            {
                tts.speak("Space", TextToSpeech.QUEUE_ADD, null, null);
                tts.playEarcon(deleteChar,TextToSpeech.QUEUE_ADD,null,null);
            }
            else {
                tts.speak(del_char+"", TextToSpeech.QUEUE_ADD, null, null);
                tts.playEarcon(deleteChar,TextToSpeech.QUEUE_ADD,null,null);
            }
            alreadyTyped = alreadyTyped.substring(0,alreadyTyped.length()-1);
        }
        return alreadyTyped;
    }

    /*
     * Special Char Mode : Speak Out Typed String
     * Typed String: 'Fish@'
     * on Index + Middle + Ring + Pinky Finger Gesture; 'Fish@' is spoken out in high pitch voice
     * */
    public void spModeSpeakOut(TextToSpeech tts, String alredyTyped)
    {

        speakOutSelection(tts,alredyTyped);
    }

    /*
     * Speak Out Pitch Adjustments
     * */
    public void speakOutSelection(TextToSpeech tts,String text)
    {
        if(text.length() == 1 & text.charAt(0) == ' ')
        {
            tts.setPitch(1.5f);
            speakOut(tts,"No Character Selected to Speak Out");
            tts.setPitch(1.0f);
        }
        else
        {
            tts.setPitch(1.5f);
            speakOut(tts,text);
            tts.setPitch(1.0f);
        }
    }

    /*
     * Speak out Using TextToSpeech
     * */
    public void speakOut(TextToSpeech tts,String text)
    {
        if(!text.equals("STOP"))
        {
            tts.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);
        }
    }

    /*
     * Append ' ' (space) to Typed String
     * */
    public String addSpaceAtEnd(TextToSpeech tts,String alredyTyped/*,String word*/, String searchValue)
    {
        //alredyTyped = alredyTyped +" ";
        alredyTyped = alredyTyped + searchValue;
        tts.setPitch(2.0f);
        tts.speak("space", TextToSpeech.QUEUE_ADD, null, null);
        tts.setPitch(1.0f);
        return alredyTyped;
    }

    /*
     * Append 'ch' character to Typed String
     * */
    public String addAtEnd(TextToSpeech tts,String alreadyTyped,String searchValue)
    {
        tts.setPitch(2.0f);
        //tts.playEarcon(selectEarcon,TextToSpeech.QUEUE_FLUSH,null,null);
        if(searchValue.equals("#"))
        {
            //tts.playEarcon(selectEarcon,TextToSpeech.QUEUE_FLUSH,null,null);
            tts.speak("Hash",TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else if(searchValue.equals("$"))
        {
            //tts.playEarcon(selectEarcon,TextToSpeech.QUEUE_FLUSH,null,null);
            tts.speak("Dollar",TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else
        {
            //tts.playEarcon(selectEarcon,TextToSpeech.QUEUE_FLUSH,null,null);
            tts.speak(searchValue, TextToSpeech.QUEUE_ADD, null, null);
        }
        //tts.speak(searchValue, TextToSpeech.QUEUE_ADD, null, null);
        tts.setPitch(1.0f);
        alreadyTyped = alreadyTyped + searchValue;
        return alreadyTyped;
    }

}
