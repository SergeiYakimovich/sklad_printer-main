package site.shaerware.store.api.print.dto

import site.shaerware.store.api.contracts.constraints.RQ
import site.shaerware.store.api.contracts.dto.CreateSubDTO
import site.shaerware.store.api.contracts.dto.UpdateDTO
import site.shaerware.store.api.print.PrintAPI
import site.shaerware.store.db.models.print.Host
import site.shaerware.store.db.models.print.Printer

class PrinterRq(
    val name: String,
    val pageWidth: Float?,
    val pageHeight: Float?,
    val horizontalMargins: Float?,
    val verticalMargins: Float?,
    val gap: Float?,
    val colCount: Int?,
    val rowCount: Int?,
) : RQ<PrintAPI.Id.PrinterAPI>, CreateSubDTO<Printer, Host>, UpdateDTO<Printer> {

    override fun create(owner: Host): Printer {
        return Printer(owner, name, pageWidth, pageHeight, horizontalMargins, verticalMargins, gap, colCount, rowCount)
    }

    override fun apply(model: Printer) {
        model.name = name
        model.pageWidth = pageWidth
        model.pageHeight = pageHeight
        model.horizontalMargins = horizontalMargins
        model.verticalMargins = verticalMargins
        model.gap = gap
        model.colCount = colCount
        model.rowCount = rowCount
    }
}