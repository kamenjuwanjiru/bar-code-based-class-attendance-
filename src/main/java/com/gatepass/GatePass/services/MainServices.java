package com.gatepass.GatePass.services;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.gatepass.GatePass.barqrgenerator.Generator;
import com.gatepass.GatePass.dtos.Auth;
import com.gatepass.GatePass.dtos.MajorQuery;
import com.gatepass.GatePass.dtos.StoredData;
import com.gatepass.GatePass.entities.History;
import com.gatepass.GatePass.entities.MyUser;
import com.gatepass.GatePass.entities.Personel;
import com.gatepass.GatePass.entities.Privilege;
import com.gatepass.GatePass.interfaces.ServicesGuide;
import com.gatepass.GatePass.repo.HistoryCriteria;
import com.gatepass.GatePass.repo.HistoryRepo;
import com.gatepass.GatePass.repo.MyUserRepo;
import com.gatepass.GatePass.repo.PersonelRepo;
import com.gatepass.GatePass.utilities.MyMapper;
import com.gatepass.GatePass.utilities.MyTime;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MainServices implements ServicesGuide, UserDetailsService{
    @Autowired
    Generator generator;

JsonMapper jsonMapper = new JsonMapper();
    public void response(String result, String message, HttpServletResponse response){
        response.setContentType("application/json");
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
				.withClaim("privileges", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algo);
	
	}

    @Override
    public void login(Auth auth, HttpServletResponse response) {
        response.setContentType("application/json");
        MyUser myUser = this.getMyUser(auth.getUsername());
        if(myUser == null){
            log.warn("USER DOESNT EXIST");
            this.response("fail", "wrong credentials", response);
        }else{
            if(!encoder.matches(auth.getPassword(), myUser.getPassword())){
                log.warn("PASSWORD WRONG");
                this.response("fail", "wrong credentials", response);
            }else{
                log.info("success login attempt");
                Map<String, String> res = new HashMap<>();
                res.put("username", auth.getUsername());
                User user = (User) this.loadUserByUsername(auth.getUsername());
                String token = this.createAccessToken(user);
                res.put("token", token);
                res.put("privilege", myUser.getPrivilege().toString());
                try{
                    jsonMapper.writeValue(response.getOutputStream(), res);
                }catch(Exception e){
                    log.warn(e.getLocalizedMessage());
                }
            }
        }
    }

    @Autowired
    PersonelRepo personelRepo;
    @Autowired
    HistoryRepo historyRepo;
    @Autowired
    MyMapper mapper;
    

    @Value("${pics.store}")
    String filePath;
    @Override
    public Personel addPersonel(MultipartFile file, String data, HttpServletResponse response) {
        Personel personel = mapper.StringToJson(data, Personel.class);
        if(personelRepo.findByUid(personel.getUid())!= null){
            this.response("fail", "exists", response);
            return null;
        }else{
            StoredData storedData = new StoredData();
            storedData.setEmail(personel.getEmail());
            storedData.setUid(personel.getUid());
            try{
                file.transferTo(Path.of(filePath+"\\"+personel.getEmail()+"-PRP.jpg"));
                generator.generateBarAndQrcode(storedData);
                personelRepo.save(personel);
                return personel;
            }catch(Exception e){
                log.warn(e.getLocalizedMessage());
                this.response("fail", e.getMessage(), response);
                return null;
            }
        }
    }

    @Override
    public Personel getPersonel(MajorQuery majorQuery) {
        return personelRepo.findByUid(majorQuery.getUid());
    }

    @Autowired
    MyTime myTime;
    @Override
    public History addHistory(History history) {
        history.setDate(myTime.dateToday());
        history.setTimeStamp(myTime.getTimeStamp());
        MajorQuery majorQuery = new MajorQuery();
        majorQuery.setUid(history.getUid());
        Personel personel = this.getPersonel(majorQuery);
        history.setEmail(personel.getEmail());
        history.setFullName(personel.getFullName());
        return historyRepo.save(history);
    }
    @Autowired
    HistoryCriteria historyCriteria;

    @Override
    public List<History> getHistory(MajorQuery majorQuery) {
        return historyCriteria.getHistory(majorQuery);
    }

    @Override
    public void removePersonel(MajorQuery majorQuery, HttpServletResponse response) {
        Personel personel = this.getPersonel(majorQuery);
        if(personel != null){
            //delete pictures
            String profilePic = filePath+"\\"+personel.getEmail()+"-PRP.jpg";
            String barcode = filePath+"\\"+personel.getEmail()+"-BAR.jpg";
            String qrcode = filePath+"\\"+personel.getEmail()+"-QR.jpg";

            ArrayList<String> files = new ArrayList<>();
            files.add(profilePic);
            files.add(barcode);
            files.add(qrcode);

            Iterator iterator = files.iterator();

            while (iterator.hasNext()) {
                try{
                    File file = new File(iterator.next().toString());
                    file.delete();
                }catch(Exception e){
                    log.info(e.getLocalizedMessage());
                }
            }

            personelRepo.delete(personel);
            
        }
        this.response("success", "deleted", response);
    }

    
}
