package org.springframework.security.config.web.servlet.session

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession

/**
 * A Kotlin DSL to configure session fixation protection using idiomatic
 * Kotlin code.
 *
 * @author Eleftheria Stein
 * @since 5.3
 */
@SessionSecurityMarker
class SessionFixationDsl {
    private var strategy: SessionFixationStrategy? = null

    /**
     * Specifies that a new session should be created, but the session attributes from
     * the original [HttpSession] should not be retained.
     */
    fun newSession() {
        this.strategy = SessionFixationStrategy.NEW
    }

    /**
     * Specifies that a new session should be created and the session attributes from
     * the original [HttpSession] should be retained.
     */
    fun migrateSession() {
        this.strategy = SessionFixationStrategy.MIGRATE
    }

    /**
     * Specifies that the Servlet container-provided session fixation protection
     * should be used. When a session authenticates, the Servlet method
     * [HttpServletRequest.changeSessionId] is called to change the session ID
     * and retain all session attributes.
     */
    fun changeSessionId() {
        this.strategy = SessionFixationStrategy.CHANGE_ID
    }

    /**
     * Specifies that no session fixation protection should be enabled.
     */
    fun none() {
        this.strategy = SessionFixationStrategy.NONE
    }

    internal fun get(): (SessionManagementConfigurer<HttpSecurity>.SessionFixationConfigurer) -> Unit {
        return { sessionFixation ->
            strategy?.also {
                when (strategy) {
                    SessionFixationStrategy.NEW -> sessionFixation.newSession()
                    SessionFixationStrategy.MIGRATE -> sessionFixation.migrateSession()
                    SessionFixationStrategy.CHANGE_ID -> sessionFixation.changeSessionId()
                    SessionFixationStrategy.NONE -> sessionFixation.none()
                }
            }
        }
    }
}

private enum class SessionFixationStrategy {
    NEW, MIGRATE, CHANGE_ID, NONE
}
