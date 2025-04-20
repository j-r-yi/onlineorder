package com.laioffer.onlineorder.model;

// This body is used for registering a new user
// Request in body so that it is not sent in the URL (which is insecure)
    // body itself is encrypted, but not the URL
public record RegisterBody(
        String email,
        String password,
        String firstName,
        String lastName
) {
}
