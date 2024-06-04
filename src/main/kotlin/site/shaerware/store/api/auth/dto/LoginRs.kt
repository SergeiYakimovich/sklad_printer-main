package site.shaerware.store.api.auth.dto

import site.shaerware.store.api.auth.LoginAPI
import site.shaerware.store.api.contracts.constraints.RS

data class LoginRs(val token: String) : RS<LoginAPI>
