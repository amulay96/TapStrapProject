package com.example.charhoplayout;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AutoSuggestionsParser {

    /*
    * Find and Filter Auto Suggestions from given Dictionary
    * */
    public List<String> autoSuggestions(Context context,String alreadyTyped)
    {
        ArrayList<String> allsuggestions = new ArrayList<String>();
        ArrayList<String> filteredSuggestions = new ArrayList<String>();

        List<String> fl_temp = new ArrayList<>();
        List<String> fl = new ArrayList<>();

        try
        {
            //InputStream file = context.getAssets().open("5000words.txt");
            InputStream file_google = context.getAssets().open("google-10000-english-no-swears.txt");
            InputStreamReader isr = new InputStreamReader(file_google);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while(line != null)
            {
                allsuggestions.add(line.toLowerCase());
                line = br.readLine();
            }

            for (String string : allsuggestions) {
                if(string.startsWith(alreadyTyped))
                {
                    filteredSuggestions.add(string);
                }
            }

            Collections.sort(filteredSuggestions);

            fl_temp = filteredSuggestions;
            fl = sortStringListByLength(fl_temp);
            fl = fl.stream().distinct().collect(Collectors.toList());
        }


        catch(IOException e)
        {
            e.printStackTrace();
        }
        return fl;
    }

    /*
    * Sort the Generated Auto Suggestions as per Length
    * */
    private static List<String> sortStringListByLength(List<String> list) {
        System.out.println("-- sorting list of string --");
        Collections.sort(list, Comparator.comparing(String::length));
        return list;
    }

}
