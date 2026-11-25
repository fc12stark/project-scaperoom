package edu.upc.dsa.services;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@PreMatching
public class CORSFilter implements ContainerRequestFilter, ContainerResponseFilter {
    public void filter(ContainerRequestContext request) throws IOException {
        if (request.getMethod().equals("OPTIONS")) {
            request.abortWith(Response.ok().type(MediaType.TEXT_PLAIN_TYPE).build());
        }
    }

    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");
        response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
    }
}
