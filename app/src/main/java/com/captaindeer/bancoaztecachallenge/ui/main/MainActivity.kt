package com.captaindeer.bancoaztecachallenge.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.captaindeer.bancoaztecachallenge.R
import com.captaindeer.bancoaztecachallenge.databinding.ActivityMainBinding
import com.captaindeer.bancoaztecachallenge.ui.login.LoginActivity
import com.captaindeer.bancoaztecachallenge.ui.maps.MapsFragment
import com.captaindeer.bancoaztecachallenge.ui.movies.MoviesFragment
import com.google.firebase.auth.FirebaseAuth
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var auth: FirebaseAuth? = null
    private lateinit var toggle: ActionBarDrawerToggle
    private var name: TextView? = null
    private var email: TextView? = null
    private var img: CircleImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializer
        auth = FirebaseAuth.getInstance()
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        replaceFragment(MoviesFragment())

        //Buttons or clicks
        binding.drawerLayout.addDrawerListener(toggle)

        //User data on navegation view
        //looking the components inside nav view
        val header = binding.navView.getHeaderView(0)
        name = header.findViewById(R.id.tvNameHeader) as TextView
        email = header.findViewById(R.id.tvEmail) as TextView
        img = header.findViewById(R.id.civ_userPP) as CircleImageView

        //Set data on navigation drawer
        Glide.with(this).load(auth!!.currentUser!!.photoUrl.toString()).placeholder(R.drawable.pp).into(img!!)
        if (auth!!.currentUser!!.displayName != null) {
            email!!.text = auth!!.currentUser!!.email.toString()
            name!!.text = auth!!.currentUser!!.displayName.toString()
        } else {
            name!!.text = "Logged with email"
            email!!.text = auth!!.currentUser!!.email.toString()
        }

        //Navegation Drawer
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.movies -> replaceFragment(MoviesFragment())
                R.id.map -> replaceFragment(MapsFragment())

                R.id.logOut -> logOut()
            }
            true
        }
    }

    override fun onBackPressed() {
        if (this.binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            this.binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        auth?.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainFl, fragment)
        transaction.commit()
    }
}