package com.example.homediy.Fragments.Interfaces;

import java.util.ArrayList;

public interface ListFragmentInteraction<T>
{
    void onListFragmentInteraction(String Tag, T item);
    ArrayList<T> onNeedList();
}
