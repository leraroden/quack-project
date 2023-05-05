package de.ls5.wt2.conf.auth.jwt;

import java.text.ParseException;
import java.util.Map;
import java.util.Objects;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nimbusds.jose.JWSObject;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

public class JWTAuthenticationFilter extends AuthenticatingFilter {

    private final static String AUTHORIZATION = "Authorization";

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {

        final HttpServletRequest httpRequest = WebUtils.toHttp(request);
        final String authzHeader = httpRequest.getHeader(AUTHORIZATION);

        if (authzHeader != null && authzHeader.startsWith("Bearer ")) {
            return buildShiroToken(authzHeader.split(" ")[1]);
        }

        // return empty token, that will fail authentication
        return new UsernamePasswordToken();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        // if shiro doesn't recognizes us as logged in, try login in
        final boolean loggedIn = executeLogin(request, response);

        if (!loggedIn) {
            HttpServletResponse httpResponse = WebUtils.toHttp(response);
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return false;
        }

        return true;
    }

    private JWTShiroToken buildShiroToken(String token) {
        try {
            final JWSObject jwsObject = JWSObject.parse(token);
            final Map<String, Object> payload = jwsObject.getPayload().toJSONObject();

            final String username = Objects.toString(payload.get("sub"));

            return new JWTShiroToken(username, token);
        } catch (ParseException ex) {
            throw new AuthenticationException(ex);
        }

    }
}
