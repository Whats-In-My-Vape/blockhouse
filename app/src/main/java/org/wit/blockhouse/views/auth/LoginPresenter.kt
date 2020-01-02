package org.wit.blockhouse.views.auth

import org.wit.blockhouse.views.BasePresenter
import org.wit.blockhouse.views.BaseView
import org.wit.blockhouse.views.VIEW

class LoginPresenter(view: BaseView) : BasePresenter(view) {

    fun doLogin(email: String, password: String) {
        view?.navigateTo(VIEW.LIST)
    }

    fun doSignUp(email: String, password: String) {
        view?.navigateTo(VIEW.LIST)
    }
}