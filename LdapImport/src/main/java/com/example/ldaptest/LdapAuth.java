package com.example.ldap;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

import java.util.Arrays;

public class LdapAuth {
    public static void main(String[] args) {
        DefaultSpringSecurityContextSource contextSource =
                new DefaultSpringSecurityContextSource(Arrays.asList("ldap://localhost:8389"), "dc=springframework,dc=org");
        contextSource.afterPropertiesSet();

        BindAuthenticator authenticator = new BindAuthenticator(contextSource);
        authenticator.setUserDnPatterns(new String[]{"uid={0},ou=people"});

        LdapAuthenticationProvider provider = new LdapAuthenticationProvider(authenticator);

        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken("bob", "password");

        try {
            var result = provider.authenticate(authRequest);
            System.out.println("Authentication successful: " + result.isAuthenticated());
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
        }
    }
}
