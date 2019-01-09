package com.hendraanggrian.generating.r.configuration

data class CssConfiguration(

    /** When set to true, will remove `.` prefix in JavaFX CSS style classes. */
    var isJavaFx: Boolean = false
) {

    /** Groovy-friendly alias of [isJavaFx]. */
    fun javaFx(isJavaFx: Boolean) {
        this.isJavaFx = isJavaFx
    }
}