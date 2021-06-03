package com.jinax.adweb_backend.Service;

import com.jinax.adweb_backend.Component.MyUserDetails;
import com.jinax.adweb_backend.Controller.UserController;
import com.jinax.adweb_backend.Entity.User;
import com.jinax.adweb_backend.Repository.UserRepository;
import com.jinax.adweb_backend.Utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author : chara
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public User findById(Integer id){
        Optional<User> byId = userRepository.findById(id);
        return byId.orElse(null);
    }

    public Map<String, String> login(String username, String password){
        LOGGER.info("UserService.login username is {},password is {}",username,password);
        Optional<User> user = userRepository.findUserByUsernameEquals(username);

        if (!user.isPresent()){
            return null;
        }
        if(passwordEncoder.matches(password,user.get().getPassword())){
            MyUserDetails userDetails = new MyUserDetails(user.get());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenUtil.generateToken(userDetails);
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            tokenMap.put("id", userDetails.getId() + "");
            return tokenMap;
        }
        return null;
    }

    public void insertNewUser(User user){
        LOGGER.info("UserService.insertNewUser username is {},password is {}",user.getUsername(),user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getAvatar() == null){
            user.setAvatar(1);
        }
        try{
            userRepository.save(user);
        }catch (RuntimeException e){
            LOGGER.debug("insertNewUser failed, reason is {}",e.getMessage());
            throw e;
        }

    }
}
