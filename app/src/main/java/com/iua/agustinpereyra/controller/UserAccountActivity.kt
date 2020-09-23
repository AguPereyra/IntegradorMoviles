package com.iua.agustinpereyra.controller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iua.agustinpereyra.R

class UserAccountActivity : AppCompatActivity(), UserAccountViewFragment.userAccountFragmentListener, UserAccountModifyPasswordFragment.userAccountModifyPasswordFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_account_activity)

        // Set fragment dinamically
        //1. Get a reference to fragment manager
        val fragmentManager = supportFragmentManager

        //2. Start a fragment transaction
        val fragmentTransaction = fragmentManager.beginTransaction()

        //3. Add the fragment to the container
        fragmentTransaction.replace(R.id.user_account_fragment_layout, UserAccountViewFragment())

        //4. Commit transaction
        fragmentTransaction.commit()
    }

    override fun onCancelUserAccountViewClick() {
        val cattleListReturnIntent = Intent()
        setResult(Activity.RESULT_OK, cattleListReturnIntent)
        finish()
    }

    override fun onSaveUserAccountViewClick() {
        val cattleListReturnIntent = Intent()
        setResult(Activity.RESULT_OK, cattleListReturnIntent)
        finish()
    }

    override fun navigateToChangePassword() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val userAccountModifyPasswdFragment = UserAccountModifyPasswordFragment()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.replace(R.id.user_account_fragment_layout, userAccountModifyPasswdFragment)
        fragmentTransaction.commit()
    }

    override fun onCancelModifyPasswordClick() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.popBackStack()
    }

    override fun onSaveModifyPasswordClick() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.popBackStack()
    }
}