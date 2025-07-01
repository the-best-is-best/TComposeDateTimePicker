package io.github.tcompose_date_picker.config.serializer

import io.github.tcompose_date_picker.extensions.toIsoStringWithOffset
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

object LocalDateTimeWithOffsetSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.toIsoStringWithOffset())
    }

    @OptIn(ExperimentalTime::class)
    override fun deserialize(decoder: Decoder): LocalDateTime {
        val instant = Instant.parse(decoder.decodeString()) // Parse Instant
        return instant.toLocalDateTime(TimeZone.currentSystemDefault()) // Convert back
    }
}
