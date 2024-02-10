package com.gatepass.GatePass.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.gatepass.GatePass.barqrgenerator.Generator;
import com.gatepass.GatePass.dtos.Auth;
import com.gatepass.GatePass.entities.MyUser;
import com.gatepass.GatePass.entities.Privilege;
import com.gatepass.GatePass.interfaces.ServicesGuide;
import com.gatepass.GatePass.repo.MyUserRepo;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MainServices implements ServicesGuide, UserDetailsService{
    @Autowired
    Generator generator;

JsonMapper jsonMapper = new JsonMapper();
    public void response(String result, String message, HttpServletResponse response){
        
        Map<String, String> map = new HashMap<>();
        map.put("result", result);
        map.put("message", message);
        try{
            jsonMapper.writeValue(response.getOutputStream(), map);
        }catch(Exception e){
            log.warn(e.getLocalizedMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser = this.getMyUser(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(myUser.getPrivilege().toString()));

        return new User(myUser.getUsername(), myUser.getPassword(), authorities);
    }

    @Autowired
    MyUserRepo myUserRepo;
    @Override
    public MyUser getMyUser(String username) {
       return myUserRepo.findByUsername(username);
    }
    @Autowired
    PasswordEncoder encoder;

    @Override
    public MyUser registerAdminMyUser(MyUser myUser, HttpServletResponse response) {
       if(this.getMyUser(myUser.getUsername()) != null){
        this.response("fail", "user exist", response);
        return null;
       }else{
            myUser.setPassword(encoder.encode(myUser.getPassword()));
            myUser.setPrivilege(Privilege.ADMIN);
            return myUserRepo.save(myUser);
       }
    }

    @Override
    public MyUser registerRepMyUser(MyUser myUser, HttpServletResponse response) {
        if(this.getMyUser(myUser.getUsername()) != null){
            this.response("fail", "user exist", response);
            return null;
           }else{
                myUser.setPassword(encoder.encode(myUser.getPassword()));
                myUser.setPrivilege(Privilege.REP);
                return myUserRepo.save(myUser);
           }
    }
    Algorithm algo = Algorithm.HMAC256("mercy".getBytes());


    	public String createAccessToken(User user) {
		
		return JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+24*60*60*1000))
				.withIssuer("GATESYSTEM")
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algo);
	
	}

    @Override
    public void login(Auth auth, HttpServletResponse response) {
        MyUser myUser = this.getMyUser(auth.getUsername());
        if(myUser == null){
            log.warn("USER DOESNT EXIST");
            this.response("fail", "wrong credentials", response);
        }else{
            if(encoder.matches(auth.getPassword(), myUser.getPassword())){
                log.warn("PASSWORD WRONG");
                this.response("fail", "wrong credentials", response);
            }else{
                log.info("success login attempt");
                Map<String, String> res = new HashMap<>();
                res.put("username", auth.getUsername());
                User user = (User) this.loadUserByUsername(auth.getUsername());
                String token = this.createAccessToken(user);
                res.put("token", token);
                try{
                    jsonMapper.writeValue(response.getOutputStream(), res);
                }catch(Exception e){
                    log.warn(e.getLocalizedMessage());
                }
            }
        }
    }

    
}
