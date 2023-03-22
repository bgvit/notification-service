package com.github.bgvit.notificationservice.notificationservice.thirdparty.response

data class NtfyResponse(
    val id: String = "",
    val time: Long = 0,
    val expires: Long = 0,
    val event: String = "",
    val topic: String = "",
    val title: String = "",
    val message: String = ""
) {
    fun hasId() = this.id.isNotBlank()
}
