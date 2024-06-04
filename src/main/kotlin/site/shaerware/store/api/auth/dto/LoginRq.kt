package site.shaerware.store.api.auth.dto

import site.shaerware.store.api.auth.LoginAPI
import site.shaerware.store.api.contracts.constraints.RQ

data class LoginRq(val username: String, val password: String) : RQ<LoginAPI>
