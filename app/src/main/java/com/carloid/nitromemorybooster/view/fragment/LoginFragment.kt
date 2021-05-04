package com.carloid.nitromemorybooster.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.carloid.nitromemorybooster.R
import com.carloid.nitromemorybooster.databinding.FragmentLoginBinding
import com.carloid.nitromemorybooster.util.AccountManager

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private var manager: AccountManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        components()

        return binding!!.root
    }

    fun components() {
        manager = AccountManager(context)

        binding!!.btnLogin.setOnClickListener {
            manager?.let {
                if (it.isUsernameAvailable(getUsername())) {
                    binding!!.editUsername.isErrorEnabled = true
                    binding!!.editUsername.error = "Usuário não encontrado!"
                } else {
                    if (it.login(getUsername(), getPassword())) {
                        gotoMainFragment()
                    } else {
                        binding!!.editPassword.isErrorEnabled = true
                        binding!!.editPassword.error = "Senha inválida"
                    }
                }
            }
        }

        binding!!.btnSignUp.setOnClickListener {
            gotoSignupFragment()
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

    private fun gotoSignupFragment() {
        val signupFragment = SignupFragment()

        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.replace(R.id.main_fragment_area, signupFragment)
        transaction.addToBackStack("loginFragment");
        transaction.commit()
    }
}