package com.jinax.adweb_backend.Controller;

import com.jinax.adweb_backend.Component.exception.UserNotExistException;
import com.jinax.adweb_backend.Entity.User;
import com.jinax.adweb_backend.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Map<String,String>> login(String username, String password){
        LOGGER.info("UserController.login username is {},password is {}",username,password);
        Map<String, String> loginMap = userService.login(username, password);
        if(loginMap == null){
            throw new UserNotExistException("用户名或密码错误");
        }
        return new ResponseEntity<>(loginMap, HttpStatus.OK);
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    @ResponseBody
    public ResponseEntity<Map<String,String>> register(User user){
        LOGGER.info("UserController.register username is {},password is {},avatar is {}",user.getUsername(),user.getPassword(),user.getAvatar());
        try{
            Map<String, String> map = userService.insertNewUser(user);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch (RuntimeException e){
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("message","注册失败");
            return new ResponseEntity<>(resultMap, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/avatar/{username}")
    @ApiOperation("获取 avatar")
    @ResponseBody
    public ResponseEntity<User> getAvatar(@PathVariable("username") String username){
        LOGGER.info("UserController.getAvatar username is {}",username);
        User byUserName = userService.findByUserName(username);
        if(byUserName == null){
            throw new UserNotExistException("用户不存在");
        }else{
            return new ResponseEntity<>(byUserName, HttpStatus.OK);
        }
    }
}
