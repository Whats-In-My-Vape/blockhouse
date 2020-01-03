package org.wit.blockhouse.views.auth

import com.google.firebase.auth.FirebaseAuth
import org.wit.blockhouse.models.firebase.BlockhouseFireStore
import org.jetbrains.anko.toast
import org.wit.blockhouse.views.BasePresenter
import org.wit.blockhouse.views.BaseView
import org.wit.blockhouse.views.VIEW

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: BlockhouseFireStore? = null

    init {
        if (app.blockhouses is BlockhouseFireStore) {
            fireStore = app.blockhouses as BlockhouseFireStore
        }
    }

    fun doLogin(email: String, password: String) {
        view?.showProgress()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    fireStore!!.fetchBlockhouses {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LIST)
                    }
                } else {
                    view?.hideProgress()
                    view?.navigateTo(VIEW.LIST)
                }
            } else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }

    fun doSignUp(email: String, password: String) {
        view?.showProgress()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
            if (task.isSuccessful) {
                if (fireStore != null) {
                    fireStore!!.fetchBlockhouses {
                        view?.hideProgress()
                        view?.navigateTo(VIEW.LIST)
                    }
                }
                view?.hideProgress()
                view?.navigateTo(VIEW.LIST)
            } else {
                view?.hideProgress()
                view?.toast("Sign Up Failed: ${task.exception?.message}")
            }
        }
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.blockhouses.clear()
        view?.navigateTo(VIEW.LOGIN)
    }
}