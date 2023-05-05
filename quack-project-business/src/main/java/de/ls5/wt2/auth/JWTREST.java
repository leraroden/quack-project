package de.ls5.wt2.auth;

import com.nimbusds.jose.JOSEException;
import de.ls5.wt2.conf.auth.jwt.JWTLoginData;
import de.ls5.wt2.conf.auth.jwt.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
@RequestMapping(path = "rest/auth/jwt")
public class JWTREST {

    @PostMapping(path = "authenticate",
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createJWToken(@RequestBody JWTLoginData credentials) throws JOSEException {

        // do some proper lookup
        final String user = credentials.getUsername();
        final String pwd = credentials.getPassword();

        if (!user.equals(pwd)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(JWTUtil.createJWToken(credentials));
    }

}

