package com.opl.pharmavector;

import java.util.ArrayList;
import java.util.List;

public class Utils
{

    private Utils()
    {
        //Its constructor should not exist.Hence this.
    }

    public static ArrayList<String> removeDuplicatesFromList(List<String> description)
    {
        ArrayList<String> tempList = new ArrayList<String>();
        for(String desc : description)
        {
            if(!tempList.contains(desc))
            {
                tempList.add(desc);
            }
        }
        description = tempList;
        tempList = null;
        return (ArrayList<String>) description;
    }

}