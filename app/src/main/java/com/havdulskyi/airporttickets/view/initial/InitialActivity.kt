package com.havdulskyi.airporttickets.view.initial

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.common.SignInButton
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.havdulskyi.airporttickets.R
import com.havdulskyi.airporttickets.view.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

class InitialActivity : AppCompatActivity() {

    companion object {
        fun newInstance() = InitialActivity()

        private const val RC_SIGN_IN = 123

    }

    private val viewModel: InitialViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.initial_activity)
        val mainView = findViewById<ConstraintLayout>(R.id.mainView)
        val signInWithGoogleBtn = findViewById<SignInButton>(R.id.signInWithGoogle)
        val signInAsGuestBtn = findViewById<MaterialButton>(R.id.signInAsGuest)
        signInWithGoogleBtn.setSize(SignInButton.SIZE_WIDE)

        signInWithGoogleBtn.setOnClickListener {
            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build()
            )

            // Create and launch sign-in intent
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN)
        }

        signInAsGuestBtn.setOnClickListener {
            viewModel.loginAsGuest()
        }
        viewModel.navigateToMainActivity.observe(this, { event ->
            if (!event.hasBeenHandled) {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        })
        viewModel.initialViewVisible.observe(this, { visible ->
            mainView.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                if (user != null) {
                    viewModel.loginWithFirebase(user)
                }
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

}