package com.ysoft.geecon.dto;

import com.ysoft.geecon.repo.SecureRandomStrings;

import java.util.List;

public record AuthorizationSession(AuthParams params,
                                   OAuthClient client,
                                   User user,
                                   List<String> acceptedScopes,
                                   AccessTokenResponse tokens) {
    public AuthorizationSession(AuthParams params, OAuthClient client) {
        this(params, client, null, null, null);
    }

    public AuthorizationSession withUser(User user) {
        return new AuthorizationSession(params, client, user, acceptedScopes, tokens);
    }

    public AuthorizationSession withScopes(List<String> acceptedScopes) {
        return new AuthorizationSession(params, client, user, acceptedScopes, tokens);
    }

    public AuthorizationSession withGeneratedTokens() {
        String idToken = null;
        var tokens = new AccessTokenResponse("Bearer",
                8400,
                SecureRandomStrings.alphanumeric(50),
                scope(),
                SecureRandomStrings.alphanumeric(50),
                idToken
        );
        return new AuthorizationSession(params, client, user, acceptedScopes, tokens);
    }

    public String scope() {
        return acceptedScopes == null ? null : String.join(" ", acceptedScopes);
    }
}
