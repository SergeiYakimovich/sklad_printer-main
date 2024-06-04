package site.shaerware.store.utils

import io.ktor.http.*

object MIME {
    fun type_by_mime(mime: ContentType): String {
        if (mime.contentType == "image") {
            when (mime.contentSubtype) {
                "svg+xml" -> return "svg"
                "x-icon" -> return "ico"
            }
            return mime.contentSubtype
        }
        return ""
    }

    fun mime_by_type(type: String): ContentType {
        when (type.lowercase()) {
            "jpeg", "jpg" -> return ContentType.Image.JPEG
            "gif" -> return ContentType.Image.GIF
            "png" -> return ContentType.Image.PNG
            "svg" -> return ContentType.Image.SVG
            "ico" -> return ContentType.Image.XIcon
            "webp" -> return ContentType("image", "webp")
            else -> return ContentType.Application.OctetStream
        }
    }
}