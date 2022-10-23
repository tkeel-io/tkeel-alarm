//package com.alarm.tkeel.controller;
//
//
//import com.alarm.tkeel.pojo.User;
//import com.alarm.tkeel.result.ResultData;
//import com.alarm.tkeel.service.UserService;
//import com.alarm.tkeel.utils.PageInfo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/v1")
//public class UserController {
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    private UserService userService;
//
//    @RequestMapping("/pageHelperTest/{pageNum}/{pageSize}")
//    public ResultData pageHelperTest(@PathVariable Integer pageNum, @PathVariable Integer pageSize){
//        //调用方法进行分页查询
//        return ResultData.success(userService.findAllUser(pageNum,pageSize));
//    }
//
//}
