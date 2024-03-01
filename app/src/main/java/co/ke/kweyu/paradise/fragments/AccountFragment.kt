package co.ke.kweyu.paradise.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import co.ke.kweyu.paradise.R
import co.ke.kweyu.paradise.activities.BankAccountInfoActivity
import co.ke.kweyu.paradise.activities.EditProfileActivity
import co.ke.kweyu.paradise.fragments.HomeFragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val accountFragmentView: View = inflater.inflate(R.layout.fragment_account, container, false)

        val toolbar: Toolbar = accountFragmentView.findViewById(R.id.toolbarAccountFragment)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        (activity as? AppCompatActivity)?.supportActionBar?.title = ""

        val contactInfoBtn: ConstraintLayout = accountFragmentView.findViewById(R.id.contactInfoBtn)
        val bankAccountInfoBtn: ConstraintLayout = accountFragmentView.findViewById(R.id.bankAccountInfoBtn)

        contactInfoBtn.setOnClickListener {
            startActivity(Intent(requireActivity(), EditProfileActivity::class.java))
        }
        bankAccountInfoBtn.setOnClickListener {
            startActivity(Intent(requireActivity(), BankAccountInfoActivity::class.java))
        }

        toolbar.setNavigationOnClickListener {
            replaceFragment(HomeFragment())
        }
        return accountFragmentView
    }

    private fun replaceFragment(homeFragment: Fragment) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainActivityFragmentContainerView,homeFragment)
        fragmentTransaction.commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}