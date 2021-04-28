package com.example.charhoplayout;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.charhoplayout.EarconManager.deleteChar;
import static com.example.charhoplayout.EarconManager.flowshift;
import static com.example.charhoplayout.EarconManager.selectEarcon;

// Alphabet Mode Class
public class AlphabetMode {

    /*
     * Variable Declaration for Alphabetical Mode
     * */
    Context context;

    ArrayList<String> suggestions;
    ArrayAdapter<String> suggestionsAdapter;

    static int head_point;

    boolean front,back,prev,hopping;

    String searchValue;

    String del_char;

    //Alphabet Mode Class Constructor
    public AlphabetMode(Context context)
    {
        this.context = context;
    }

    // Initialize Alphabets
    public void alModeInitialise()
    {
        String s = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z, ,";
        //String qwerty = "q,w,e,r,t,y,u,i,o,p,a,s,d,f,g,h,j,k,l,z,x,c,v,b,n,m, ,";
        suggestions = new ArrayList<>(Arrays.asList(s.split(",")));   // Suggested Characters from A - Z and Space
        suggestionsAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, suggestions);
        suggestionsAdapter.notifyDataSetChanged();

        head_point = 0;
        front = false;
        back = false;
        prev=false;
        hopping=false;
    }

    /*
    * Alphabet Mode : Forward Navigation
    * a -> b -> c ..... -> z -> 'space' ----> circle back to a
    * */
    public void alModeForward(TextToSpeech tts)
    {
        if(front & hopping==false)
        {
            head_point = head_point + 1;
            front=false;
        }
        if(prev==true & hopping==true)
        {
            head_point = head_point + 1;
            prev=false;
        }

        if(head_point== 27)
        {
            head_point = 0;
        }

        if(suggestions.get(head_point).equals(" "))
        {
            tts.speak("space",TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else
        {
            tts.speak(suggestions.get(head_point),TextToSpeech.QUEUE_FLUSH,null,null);
        }

        head_point++;
        back=true;
        //hopping=false;
    }


    /*
    * Alphabet Mode : Backward Navigation
    * eg: ...z <- 'space' <- a <- b <- c <- d...
    * */
    public void alModeBackward(TextToSpeech tts)
    {
        if (back) {
            head_point = head_point - 2;
            back = false;
        }
        else
        {
            head_point--;
        }

        if(head_point < 0 )
        {
            head_point =26;
        }

        if(suggestions.get(head_point).equals(" "))
        {
            tts.speak("space",TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else
        {
            tts.speak(suggestions.get(head_point),TextToSpeech.QUEUE_FLUSH,null,null);
            Log.d("backward h_p",""+head_point);
        }
        front = true;
        prev = true;
    }

    /*
    * Alphabet Mode : Hopping Navigation
    * a -> f -> k -> p -> u -> 'space' -> a ........
    * */
    public void alModeHopping(TextToSpeech tts)
    {
        if(head_point > 0 & head_point < 5)
        {
            head_point=5;
        }
        else if(head_point > 5 & head_point < 10)
        {
            head_point=10;
        }
        else if(head_point > 10 & head_point < 15)
        {
            head_point=15;
        }
        else if(head_point >15 & head_point < 20)
        {
            head_point=20;
        }
        else if(head_point > 20 & head_point <= 26)
        {
            head_point = 26;
        }
        else
        {
            head_point = 0;
        }

        if(suggestions.get(head_point).charAt(0) == ' ')
        {
            tts.speak("space",TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else
        {
            tts.speak(suggestions.get(head_point),TextToSpeech.QUEUE_FLUSH,null,null);
        }
        head_point++;
        Log.d("3 Finger h_p",""+head_point);
        back=true;
        prev=false;
        hopping=true;
    }

    /*
     *  data parameter plays major role : Gestures are mapped to numbers
     * front, back and prev variables are used to Manipulate the index over suggestions
     *
     * When moving forward using Index Finger : Make back = true
     *
     * When moving backward using Middle Finger : Make front = true and prev = true
     *
     * As we start the character scanning we need to character a to be spoken out so we cannot increment first : if that is done then a will never be considered
     * So first a is spoken out and then header is incremented. So always its : header =0 a -> header 1 -> b --> header 2 ...
     *
     * As we have started forward navigation and now we want to go backward : eg. spoken is b but header=2 (c) so we need 2 steps back which is done at Backward navigation code
     * that is the special case rest of the backward navigation requires header--;
     *
     * Similar header Manipulation is required for Selection of Characters :
     *
     * The Selection Code Follows the below code strucuture :-
     *
     * if (prev == false) --> !prev --> !false (true)
     * {
     *           Character Scanning started and never done backward navigation Required get(header - 1)
     * }
     *else(prev will be true)
     * {
     *      if(front == false) --> !front --> !false (true)
     *      {
     *          Backward navigation and then forward navigation : eg: a b c d : c spoken but header at d so get(header - 1)
     *      }
     *      else
     *      {
     *          In backward direction the character need to be selected where the header is so its just get**(header)** and not get(header-1)
     *      }
     * }
     * */
    public String alModeSelect(TextToSpeech tts, String alreadyTyped)
    {
        if (!prev)
        {
            searchValue = suggestions.get(head_point-1);

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
                    alreadyTyped = addAtEnd(tts,alreadyTyped,searchValue); //Here we will right the code for appending the character

                }
            }
            prev = false;
        }
        else
        {
            if(!front)
            {
                searchValue = suggestions.get(head_point-1);

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

                    MainActivity.allowSearchScan = true;
                }
                else
                {
                    if(searchValue.equals(" "))
                    {
                        alreadyTyped = addSpaceAtEnd(tts,alreadyTyped,searchValue);
                    }
                    else
                    {
                        alreadyTyped = addAtEnd(tts,alreadyTyped, searchValue);//Here we will right the code for appending the character

                    }
                }
            }
            else
            {
                searchValue = suggestions.get(head_point);

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

                    MainActivity.allowSearchScan = true;
                }
                else
                {
                    if(searchValue.equals(" "))
                    {
                        alreadyTyped = addSpaceAtEnd(tts,alreadyTyped/*,word*/,searchValue);
                    }
                    else
                    {
                        alreadyTyped = addAtEnd(tts,alreadyTyped, searchValue);//Here we will right the code for appending the character
                    }
                }
            }
        }

        return alreadyTyped;
    }


    /*
    * Alphabet Mode : Speak Out Typed String
    * Typed String: 'Fish'
    * on Index + Middle + Ring + Pinky Finger Gesture; 'Fish' is spoken out in high pitch voice
    * */
    public void alModeSpeakOut(TextToSpeech tts, String alredyTyped)
    {

        speakOutSelection(tts,alredyTyped);
    }

    /*
    * Alphabet Mode : Reset
    * Reset the Navigation back to 'a'
    * */
    public void alModeReset(TextToSpeech tts)
    {
        tts.speak("Stop",TextToSpeech.QUEUE_FLUSH,null,null);
        tts.playEarcon(flowshift,TextToSpeech.QUEUE_FLUSH,null,null);
        head_point = 0;
        front=false;
        prev=false;
    }

    /*
    * Alphabet Mode : Delete a character at the end of Typed String
    * eg: 'Fish' --> 'h' will be deleted
    * */
    public String alModeDelete(TextToSpeech tts, String alreadyTyped)
    {
        if(alreadyTyped.isEmpty())
        {
            tts.speak("No Character to Delete", TextToSpeech.QUEUE_FLUSH, null, null);
            EditMode.nav_index=0;//After Deleting Entirely a word if you select a new word then it should start from first character
        }
        else
        {
            del_char = alreadyTyped.substring(alreadyTyped.length()-1);

            if(del_char.equals(" "))
            {
                tts.speak("Space", TextToSpeech.QUEUE_ADD, null, null);
                tts.playEarcon(deleteChar,TextToSpeech.QUEUE_ADD,null,null);
            }
            else
            {
                tts.speak(del_char+"", TextToSpeech.QUEUE_ADD, null, null);
                tts.playEarcon(deleteChar,TextToSpeech.QUEUE_ADD,null,null);
            }
            alreadyTyped = alreadyTyped.substring(0,alreadyTyped.length()-1);
        }
        return alreadyTyped;
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
    public String addSpaceAtEnd(TextToSpeech tts,String alreadyTyped,String searchValue)
    {
        alreadyTyped = alreadyTyped + searchValue;
        tts.setPitch(2.0f);
        tts.speak("space", TextToSpeech.QUEUE_ADD, null, null);
        tts.setPitch(1.0f);
        return alreadyTyped;
    }

    /*
    * Append 'ch' character to Typed String
    * */
    public String addAtEnd(TextToSpeech tts,String alreadyTyped, String searchValue)
    {
        tts.setPitch(2.0f);
        if(searchValue.equals("#"))
        {
            tts.playEarcon(selectEarcon,TextToSpeech.QUEUE_FLUSH,null,null);
            tts.speak("Hash",TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else if(searchValue.equals("$"))
        {
            tts.playEarcon(selectEarcon,TextToSpeech.QUEUE_FLUSH,null,null);
            tts.speak("Dollar",TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else
        {
            tts.playEarcon(selectEarcon,TextToSpeech.QUEUE_FLUSH,null,null);
            tts.speak(searchValue, TextToSpeech.QUEUE_ADD, null, null);
        }
        tts.setPitch(1.0f);
        alreadyTyped = alreadyTyped + searchValue;
        return alreadyTyped;
    }

}
