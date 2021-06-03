package com.jinax.adweb_backend.Controller;

import com.jinax.adweb_backend.Entity.User;
import com.jinax.adweb_backend.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : chara
 */
@RestController
@Api("用户相关操作")
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> login(String username, String password){
        LOGGER.info("UserController.login username is {},password is {}",username,password);
        Map<String, String> loginMap = userService.login(username, password);
        Map<String, Object> resultMap = new HashMap<>();
        if(loginMap == null){
            resultMap.put("message","用户名或密码错误");
            return new ResponseEntity<>(resultMap, HttpStatus.FORBIDDEN);
        }
        resultMap.put("message",loginMap);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> register(User user){
        LOGGER.info("UserController.register username is {},password is {},avatar is {}",user.getUsername(),user.getPassword(),user.getAvatar());
        Map<String, Object> resultMap = new HashMap<>();
        try{
            Map<String, String> map = userService.insertNewUser(user);
            resultMap.put("message",map);
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }catch (RuntimeException e){
            resultMap.put("message","注册失败");
            return new ResponseEntity<>(resultMap, HttpStatus.FORBIDDEN);
        }
    }
}
