package org.springframework.security.config.web.servlet

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.PortMapperConfigurer
import org.springframework.security.web.PortMapper

/**
 * A Kotlin DSL to configure a [PortMapper] for [HttpSecurity] using idiomatic
 * Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 * @property portMapper allows specifying the [PortMapper] instance.
 */
@SecurityMarker
class PortMapperDsl {
    private val mappings = mutableListOf<Pair<Int, Int>>()

    var portMapper: PortMapper? = null

    /**
     * Adds a mapping to the port mapper.
     *
     * @param fromPort the HTTP port number to map from
     * @param toPort the HTTPS port number to map to
     */
    fun map(fromPort: Int, toPort: Int) {
        mappings.add(Pair(fromPort, toPort))
    }

    internal fun get(): (PortMapperConfigurer<HttpSecurity>) -> Unit {
        return { portMapperConfig ->
            portMapper?.also {
                portMapperConfig.portMapper(portMapper)
            }
            this.mappings.forEach {
                portMapperConfig.http(it.first).mapsTo(it.second)
            }
        }
    }
}
