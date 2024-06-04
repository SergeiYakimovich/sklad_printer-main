package site.shaerware.store.api.print.dto

import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.CreateDTO
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.api.print.PrintAPI
import site.shaerware.store.db.models.print.Host

data class HostRq(
    val name: String
): RQ<PrintAPI>, CreateDTO<Host>, UpdateDTO<Host> {
    override fun create(): Host {
        val dbo = Host(name)
        return dbo
    }

    override fun apply(model: Host) {
        model.name = name
    }
}
