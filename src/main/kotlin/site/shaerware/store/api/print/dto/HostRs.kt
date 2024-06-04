package site.shaerware.store.api.print.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.contracts.dto.ReadDTO
import site.shaerware.store.api.print.PrintAPI
import site.shaerware.store.db.models.print.Host
import java.util.*

data class HostRs(
    val id: UUID,
    val name: String,
    val printers: List<PrinterRs>
) : RS<PrintAPI>, ReadDTO<Host>, ListDTO<Host> {
    constructor(model: Host): this(
        model.id,
        model.name,
        model.printers.map { PrinterRs(it) }
    )
}
