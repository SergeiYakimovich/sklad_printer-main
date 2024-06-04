package site.shaerware.store.api.print.dto

import site.shaerware.store.api.contracts.constraints.RS
import site.shaerware.store.api.contracts.dto.ListDTO
import site.shaerware.store.api.contracts.dto.ReadDTO
import site.shaerware.store.api.contracts.dto.SubEntityDTO
import site.shaerware.store.api.print.PrintAPI
import site.shaerware.store.db.models.print.Printer
import java.util.UUID

data class PrinterRs(
    val id: UUID,
    override val ownerId: UUID,
    val name: String,
    val pageWidth: Float?,
    val pageHeight: Float?,
    val horizontalMargins: Float?,
    val verticalMargins: Float?,
    val gap: Float?,
    val colCount: Int?,
    val rowCount: Int?,
) :SubEntityDTO, RS<PrintAPI.Id.PrinterAPI>, ReadDTO<Printer>, ListDTO<Printer> {
    constructor(model : Printer): this(
        model.id,
        model.owner.id,
        model.name,
        model.pageWidth,
        model.pageHeight,
        model.horizontalMargins,
        model.verticalMargins,
        model.gap,
        model.colCount,
        model.rowCount
    )
}
