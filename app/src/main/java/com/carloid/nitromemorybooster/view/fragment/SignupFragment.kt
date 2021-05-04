package com.carloid.nitromemorybooster.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.carloid.nitromemorybooster.R
import com.carloid.nitromemorybooster.databinding.FragmentSignupBinding
import com.carloid.nitromemorybooster.util.AccountManager

class SignupFragment : Fragment() {

    private var binding: FragmentSignupBinding? = null
    private var manager: AccountManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        components()

        return binding!!.root
    }

    fun components() {
        manager = AccountManager(context)

        binding!!.btnSignUp.setOnClickListener {
            manager?.let {
                if (!it.isUsernameAvailable(getUsername())) {
                    binding!!.editUsername.isErrorEnabled = true
                    binding!!.editUsername.error = "Usuário já cadastrado!"
                } else if (getUsername().isEmpty()) {
                    binding!!.editUsername.isErrorEnabled = true
                    binding!!.editUsername.error = "Usuário precisa ser preenchido!"
                } else if (getPassword().isEmpty()) {
                    binding!!.editPassword.isErrorEnabled = true
                    binding!!.editPassword.error = "Senha precisa ser preenchida!"
                } else {
                    it.register(getUsername(), getPassword())
                    Toast.makeText(context, "Usuário cadastrado com successo!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getUsername() : String {
        return binding!!.editUsername.editText!!.text.toString()
    }

    private fun getPassword() : String {
        return binding!!.editPassword.editText!!.text.toString()
    }

    private fun gotoMainFragment() {
        val mainFragment = MainFragment()

        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.replace(R.id.main_fragment_area, mainFragment)
        transaction.commit()
    }
}