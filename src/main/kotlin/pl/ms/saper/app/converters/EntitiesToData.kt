package pl.ms.saper.app.converters

import pl.ms.saper.app.configuration.ConfigEntryImpl
import pl.ms.saper.app.configuration.ConfigKeyImpl
import pl.ms.saper.app.data.embeddable.ConfigEntryEmbeddable

//TODO : create converters from dat entity to business implemented entity and reverse

fun ConfigEntryEmbeddable.toEntity() = ConfigEntryImpl(ConfigKeyImpl.valueOf(entryName), value)
fun ConfigEntryImpl.toData() = ConfigEntryEmbeddable(key.configName, value)