package com.iua.agustinpereyra.view

// Used by Fragments to access and modify Activity's action bar
interface ActionBarModifier {
    fun setActionBarTitle(title: String) : Unit
    // Set action bar home button as back arrow
    fun setActionBarHomeButtonAsUp() : Unit
}