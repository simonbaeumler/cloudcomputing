/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.benutzer.rest.controller.v0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Profile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller for request against the swagger-ui api documentation.
 * <p>
 * Created by christoph.schauer on 22.10.2015.
 */
@RestController
@RequestMapping(value = "/", method = RequestMethod.GET)
@Profile({"development", "test", "itest"})
public class SwaggerController {

    /**
     * Redirects to 'swagger-ui.html'.
     *
     * @param response the {@link HttpServletResponse}
     * @throws IOException if the redirect fails
     */
    @RequestMapping(method = RequestMethod.GET)
    public void root(HttpServletResponse response) throws IOException {
        response.sendRedirect("swagger-ui.html");
    }
}
