package app.config;

import jakarta.servlet.SessionTrackingMode;
import org.eclipse.jetty.server.session.SessionHandler;

import java.util.EnumSet;

public class SessionConfig {

    public static SessionHandler sessionConfig() {
        SessionHandler sessionHandler = new SessionHandler();
        sessionHandler.setUsingCookies(true); // Enable cookie-based sessions
        sessionHandler.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
        sessionHandler.setHttpOnly(true); // Ensure session cookies are HTTP-only for security
        sessionHandler.setMaxInactiveInterval(30 * 60); // Session timeout set to 30 minutes
        return sessionHandler;
    }
}
