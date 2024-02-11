package com.gatepass.GatePass.securityconf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.json.JsonMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthorization extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                ArrayList<String> accepted = new ArrayList<>();
                accepted.add("/login");
                accepted.add("/frontend");
                accepted.add("/any");
//allow some requests through without authorization
                if(this.checkIfMatches(request.getServletPath(), accepted)){
                    log.info(request.getServletPath()+" has been let through");
                    filterChain.doFilter(request, response);
                }else{
                    String token = request.getHeader("Authorization");
                    if(token == null){
                        log.warn("No token provided");
                        this.myFailResponse(response);
                    }else if(!token.startsWith("Bearer ")){
                        log.warn("token does not start with bearer");
                        this.myFailResponse(response);
                    }else{
                        token = token.substring("Bearer ".length());
                        // verify token

                        Algorithm algorithm = Algorithm.HMAC256("mercy".getBytes());
                        JWTVerifier verifier = JWT.require(algorithm).build();

                        try{
                            DecodedJWT decodedJWT = verifier.verify(token);
                            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                            String[] privileges = decodedJWT.getClaim("privileges").asArray(String.class);
                            for(String privilege: privileges){
                                authorities.add(new SimpleGrantedAuthority(privilege));
                            }
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null,authorities);
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                            log.info("token authorized");
                            filterChain.doFilter(request, response);
                        }catch(Exception e){
                            log.warn("token expired");
                            this.myFailResponse(response);
                        }
                    }
                }
       
    }
    
    public boolean checkIfMatches(String route, ArrayList<String> accepted){
        Iterator iterator = accepted.iterator();

        while (iterator.hasNext()) {
            if(route.startsWith(iterator.next().toString())){
                return true;
            }
        }
        return false;
    }

    public void myFailResponse( HttpServletResponse response){
        response.setContentType("application/json");
        Map<String, String> map = new HashMap<>();
        map.put("status", "fail");
        map.put("message", "fail authorization");
        JsonMapper jsonMapper = new JsonMapper();
        try{
            jsonMapper.writeValue(response.getOutputStream(), map);
        }catch(Exception e){
            log.warn(e.getLocalizedMessage());
        }
    }
}
