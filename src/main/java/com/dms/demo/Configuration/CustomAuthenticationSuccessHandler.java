package com.dms.demo.Configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // Set the response type to JSON
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        // Prepare the response JSON
        String jsonResponse = "{\"message\": \"Authentication successful\", \"redirectUrl\": \"/home\"}";

        // Write the JSON response
        PrintWriter out = response.getWriter();
        out.print(jsonResponse);
        out.flush();
    }
}
