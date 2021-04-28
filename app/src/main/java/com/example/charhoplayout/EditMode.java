package com.example.charhoplayout;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import static com.example.charhoplayout.EarconManager.deleteChar;
import static com.example.charhoplayout.EarconManager.selectEarcon;


// Edit Mode Class
public class EditMode {

    /*
    * Variable Declarations for Edit Mode
    * */
    static boolean editMode=false;

    static int nav_index;

    int edit_suggestions_index;
    static int insertion_index;
    int deleteion_index;

    boolean goingForward;
    boolean goingBackward;

    char alpha;

    Context context;
    static String decision="";

    // Edit Mode Class Constructor
    public EditMode(Context context) {
        this.context = context;
    }

    ArrayList<String> edit_suggestions;
    ArrayAdapter<String> edit_suggestionsAdapter;

    /*
    * Initialize Edit Mode Variables
    * */
    public void edModeInitialise(TextToSpeech tts,String alreadyTyped,Context context)
    {
        if(alreadyTyped.length()==0) {
            tts.playEarcon(selectEarcon, TextToSpeech.QUEUE_FLUSH, null, null);
            tts.speak("No character selected nothing to edit", TextToSpeech.QUEUE_FLUSH, null, null);
            return;
        }

            edit_suggestions = new ArrayList<>();
            edit_suggestions.add("Insert");
            edit_suggestions.add("Replace");
            edit_suggestionsAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,edit_suggestions);
            edit_suggestionsAdapter.notifyDataSetChanged();

            edit_suggestions_index = 0;

            editMode=true;
            MainActivity.allowSearchScan=true;
            MainActivity.toggle = 1;
            nav_index=0;

            goingForward=false;
            goingBackward=false;

            tts.playEarcon(selectEarcon,TextToSpeech.QUEUE_FLUSH,null,null);
            tts.speak("Edit "+alreadyTyped,TextToSpeech.QUEUE_FLUSH,null,null);
    }

    /*
     * Edit Mode : Forward Navigation
     * Typed String: 'Fish'
     * Forward Navigation: 'F' -> 'i' -> 's' -> 'h' -> back to 'F' (Circular Loop)
     * */
    public void edModeForwardNav(TextToSpeech tts, String alreadyTyped)
    {
        if(alreadyTyped.isEmpty())
        {
            tts.speak("Nothing to edit in Text Field",TextToSpeech.QUEUE_FLUSH,null,null);
            editMode=false;
            MainActivity.allowSearchScan=false;
            MainActivity.toggle = 0;
            nav_index=0;
            return;
        }
        else
        {
            if(goingBackward)
            {
                nav_index = nav_index + 1;
                goingBackward = false;
            }

            if(nav_index==alreadyTyped.length())
            {
                nav_index=0;
            }
            if(nav_index<0)
            {
                nav_index=alreadyTyped.length()-1;
            }
            alpha = alreadyTyped.charAt(nav_index);
            if(alpha == ' ')
            {
                tts.speak("space",TextToSpeech.QUEUE_FLUSH,null,null);
            }
            else if(alpha == '#')
            {
                tts.speak("Hash",TextToSpeech.QUEUE_FLUSH,null,null);
            }
            else
            {
                tts.speak(Character.toString(alpha),TextToSpeech.QUEUE_FLUSH,null,null);
            }
            nav_index++;
            goingForward = true;
        }
    }

    /*
     * Edit Mode : Backward Navigation
     * Typed String: 'Fish'
     * ...'F' <- 'i' <- 's' <- 'h' ... (Circular Loop)
     * */
    public void edModeBackwardNav(TextToSpeech tts, String alreadyTyped)
    {
        if(goingForward)
        {
            nav_index = nav_index - 2;
            goingForward = false;
        }
        else
        {
            nav_index--;
        }
        if(nav_index < 0)
        {
            nav_index = alreadyTyped.length() - 1;
        }
        alpha = alreadyTyped.charAt(nav_index);
        if(alpha == ' ')
        {
            tts.speak("space",TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else if(alpha == '#')
        {
            tts.speak("Hash",TextToSpeech.QUEUE_FLUSH,null,null);
        }
        else
        {
            tts.speak(Character.toString(alpha),TextToSpeech.QUEUE_FLUSH,null,null);
        }
        goingBackward = true;
    }


    /*
     * Edit Mode : Decision Navigation
     * Typed String: 'Fish'
     * Any ch to Insert (Fish --> Finish) to Replace (Fish --> Dish)
     * Circular Navigation between "Insert" -> "Replace" -> "Insert" -> "Replace" .......
     * */
    public void edModeDecisionNav(TextToSpeech tts)
    {
        if(edit_suggestions_index==edit_suggestions.size())
        {
            edit_suggestions_index=0;
        }
        if(edit_suggestions_index<0)
        {
            edit_suggestions_index=edit_suggestions.size()-1;
        }
        tts.speak(edit_suggestions.get(edit_suggestions_index),TextToSpeech.QUEUE_FLUSH,null,null);
        edit_suggestions_index++;
    }

    /*
     * Edit Mode : Decision Selection
     * Typed String: 'Fish'
     * Any ch to Insert (Fish --> Finish) to Replace (Fish --> Dish)
     * Select between "Insert" -> "Replace" -> "Insert" -> "Replace" .......
     * */
    public void edModeDecisionSelection(TextToSpeech tts, String alredyTyped)
    {
        if(goingBackward)
        {
            insertion_index=nav_index;
        }
        else
        {
            insertion_index=nav_index-1;
        }

        decision = edit_suggestions.get(edit_suggestions_index-1);
        if(decision.equals("Insert"))
        {
            if(alredyTyped.charAt(insertion_index) == ' ')
            {
                tts.playEarcon(selectEarcon,TextToSpeech.QUEUE_FLUSH,null,null);
                tts.speak(" Insert at space" , TextToSpeech.QUEUE_ADD, null, null);
            }
            else
            {
                tts.playEarcon(selectEarcon,TextToSpeech.QUEUE_FLUSH,null,null);
                tts.speak(" Insert at \t"+alredyTyped.charAt(insertion_index) , TextToSpeech.QUEUE_ADD, null, null);
            }
            MainActivity.allowSearchScan=false;
            editMode=true;
        }
        else if(decision.equals("Replace")) {
            if (alredyTyped.charAt(insertion_index) == ' ') {
                tts.playEarcon(selectEarcon, TextToSpeech.QUEUE_FLUSH, null, null);
                tts.speak(" Replace at space", TextToSpeech.QUEUE_ADD, null, null);
            } else {
                tts.playEarcon(selectEarcon, TextToSpeech.QUEUE_FLUSH, null, null);
                tts.speak(" Replace at \t" + alredyTyped.charAt(insertion_index), TextToSpeech.QUEUE_ADD, null, null);
            }
            MainActivity.allowSearchScan = false;
            editMode = true;
        }

        AlphabetMode.head_point = 0;
    }

    /*
     * Edit Mode : Delete Char from Typed String
     * Required Typed String: 'Fish'
     * But Typed: 'Fissh' (one extra s)
     * Delete extra s
     * */
    public String edModeDeletion(TextToSpeech tts, String alredyTyped)
    {
        if(alredyTyped.isEmpty())
        {
            tts.speak("No Character to Delete", TextToSpeech.QUEUE_FLUSH, null, null);
        }
        else
        {
            deleteion_index=nav_index-1;
            if(alredyTyped.charAt(deleteion_index) == ' ')
            {
                StringBuilder sb_deletion = new StringBuilder(alredyTyped);
                sb_deletion.deleteCharAt(deleteion_index);

                tts.speak("Space Deleted between Characters",TextToSpeech.QUEUE_FLUSH,null,null);
                tts.playEarcon(deleteChar,TextToSpeech.QUEUE_FLUSH,null,null);
                alredyTyped=sb_deletion.toString();
            }
            else
            {
                char delete_char_between = alredyTyped.charAt(deleteion_index);

                StringBuilder sb_deletion = new StringBuilder(alredyTyped);

                String s = Character.toString(sb_deletion.charAt(deleteion_index));
                tts.speak(s,TextToSpeech.QUEUE_ADD,null,null);

                sb_deletion.deleteCharAt(deleteion_index);

                tts.playEarcon(deleteChar,TextToSpeech.QUEUE_FLUSH,null,null);
                alredyTyped=sb_deletion.toString();
            }
            nav_index=nav_index-1;//Adjust the Navigation --> Otherwise it skips one alphabet to navigate
        }
        return alredyTyped;
    }

    /*
     * Edit Mode : Call Speak Out Selection: Typed String
     * Typed String: 'Fish'
     * call Speak out 'Fish'
     * */
    public void edModeSpeakOut(TextToSpeech tts, String alredyTyped)
    {

        speakOutSelection(tts,alredyTyped);
    }

    /*
     * Edit Mode : Call Speak Out Selection: Typed String : Pitch Adjustment
     * Typed String: 'Fish'
     * Pitch Adjustment for Speak out 'Fish'
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
     * Edit Mode : Speak Out Selection: Typed String
     * Typed String: 'Fish'
     * Speak out 'Fish'
     * */
    public void speakOut(TextToSpeech tts,String text)
    {
        if(!text.equals("STOP"))
        {
            tts.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);
        }
    }

    /*
     * Edit Mode : Insert a char at certain index
     * Typed String: 'Fish' (0,1,2,3)
     * IF want to make 'Finish' then
     * 'n' @ 2 --> becomes 'Finsh' (0,1,2,3,4) -> 'i' @ 3 --> 'Finish'
     * */
    public static String insertInEdit(TextToSpeech tts,String alredyTyped,String searchValue, int insertion_index)
    {
        tts.setPitch(1.0f);
        StringBuilder sb = new StringBuilder(alredyTyped);
        sb.insert(insertion_index,searchValue);
        alredyTyped=sb.toString();
        Log.i("Word",alredyTyped);
        EditMode.nav_index=0;
        return alredyTyped;
    }

    /*
     * Edit Mode : Replace a char at certain index
     * Typed String: 'Fish' (0,1,2,3)
     * IF want to make 'Dish' then
     * 'F' @ 0 --> Replaced with 'D' becomes 'Dish'
     * */
    public static String replaceInEdit(TextToSpeech tts, String alredyTyped,String searchValue,int insertion_index)
    {
        tts.setPitch(1.0f);
        StringBuilder sb = new StringBuilder(alredyTyped);
        sb.setCharAt(insertion_index,searchValue.charAt(0));
        //alredyTyped = alredyTyped.replace(alredyTyped.charAt(insertion_index),searchValue.charAt(0));
        alredyTyped = sb.toString();
        Log.i("Word",alredyTyped);
        EditMode.nav_index=0;
        return alredyTyped;
    }

    /*
     * Edit Mode : Speak 'ch' inserted at ' ' (space)
     * */
    public static void speakInsertedAtSpace(TextToSpeech tts,String searchValue)
    {
        tts.playEarcon(selectEarcon,TextToSpeech.QUEUE_FLUSH,null,null);
        tts.speak(searchValue + " Inserted at space" , TextToSpeech.QUEUE_ADD, null, null);
    }

    /*
     * Edit Mode : Speak 'ch' inserted at 'ch'
     * Fish -> Finsh --> speak n inserted at s
     * */
    public static void speakInsertedAtCharacter(TextToSpeech tts,String searchValue,char ch)
    {
        tts.playEarcon(selectEarcon,TextToSpeech.QUEUE_FLUSH,null,null);
        tts.speak(searchValue + " Inserted at \t"+ch , TextToSpeech.QUEUE_ADD, null, null);
    }

    /*
     * Edit Mode : Speak 'ch' replaced at ' ' (space)
     * */
    public static void speakReplacedAtSpace(TextToSpeech tts,String searchValue)
    {
        tts.playEarcon(selectEarcon,TextToSpeech.QUEUE_FLUSH,null,null);
        tts.speak(searchValue + " Replaced at space" , TextToSpeech.QUEUE_ADD, null, null);
    }

    /*
    * Edit Mode 'ch' replaced at 'ch'
    * Fish --> Dish --> D replaced as F
    * */
    public static void speakReplacedAtCharacter(TextToSpeech tts,String searchValue,char ch)
    {
        tts.playEarcon(selectEarcon,TextToSpeech.QUEUE_FLUSH,null,null);
        tts.speak(searchValue + " Replaced at \t"+ch , TextToSpeech.QUEUE_ADD, null, null);
    }
}
