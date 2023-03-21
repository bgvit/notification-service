package com.github.bgvit.notificationservice.notificationservice.model

data class NtfyNotification(
    val topic: String,
    val message: String?
)

/*
Later I will improve this API, because I really want to
use all features of Ntfy. More information: https://docs.ntfy.sh/publish/

# Add this fields to work with full power of ntfy on this data class and integrate API better.

val title: String?,
val tags: List<String>?,
val priority: Integer?,
val attach: String?,
val filename: String?,
val click: String?,
val actions: List<Map<String, String>>?
 */
