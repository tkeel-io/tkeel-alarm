package com.alarm.tkeel.controller;


import com.alarm.tkeel.dao.RuleMapper;
import com.alarm.tkeel.pojo.mail.BindingQuery;
import com.alarm.tkeel.pojo.mail.Email;
import com.alarm.tkeel.pojo.mail.EmailAddressVo;
import com.alarm.tkeel.pojo.mail.NoticeGroup;
import com.alarm.tkeel.result.ResultData;
import com.alarm.tkeel.service.MailService;
import com.alarm.tkeel.utils.EmailSendUtils;
import com.alarm.tkeel.utils.EncoderUtils;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v1")
@Api(value = "邮件接口",tags = "邮件配置接口", description = "邮箱配置与通知对象相关接口")
public class MailController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MailService mailService;
    @Autowired
    private EncoderUtils encoderUtils;
    @Autowired
    private RuleMapper ruleMapper;

    @Autowired
    private EmailSendUtils emailSendUtils;



    @PostMapping("/alarm/mailConfig")
    @ApiOperation(value = "邮箱配置",notes = "邮箱配置",produces = "application/json")
    public ResultData mailServer(@RequestBody Email email){
        if((email.getFromAddress() == null || email.getFromAddress().equals("") || (email.getSmtpAddress() ==null || email.getSmtpAddress().equals("")) ||
                (email.getPort() == null || email.getPort().equals("")) || (email.getSmtpUserName() == null || email.getSmtpUserName().equals("")) ||
                (email.getSmtpPassWord() == null || email.getSmtpPassWord().equals("")))){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","参数错误！");
        }
        log.debug(JSON.toJSONString(email));
        if(email.getId() == null || email.getId().equals("")){
            if(mailService.queryMailConfig() != null){
                return ResultData.fail("io.tkeel.INTERNAL_ERROR","邮件服务已配置，无法新增！");
            }
        }
        int result = mailService.insertMail(email);
        if(result == 0){
            ResultData.fail("500","保存邮箱配置失败！");
        }
        return ResultData.success(result);
    }

    @GetMapping("/alarm/mailConfig/query")
    @ApiOperation(value = "查询邮箱服务配置信息",notes = "查询邮箱服务配置信息",produces = "application/json")
    public ResultData queryMailServer(){
        return ResultData.success(mailService.queryMailConfig());
    }


    @GetMapping("/alarm/noticeGroup/query")
    @ApiOperation(value = "查询通知对象",notes = "查询通知对象",produces = "application/json")
    public ResultData queryNoticeGroup(@RequestHeader("x-tKeel-auth") String token,
                                      @RequestParam(value = "groupName",required = false) String groupName,
                                      @RequestParam(value = "pageNum",required = false) Integer pageNum,
                                      @RequestParam(value = "pageSize",required = false) Integer pageSize){
        System.out.println("/rule/query token ====="+token);
        if(pageNum == null || pageSize == null){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","分页参数不能为空！");
        }
        if (token == null || token.equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","token无效！");
        }
        if(encoderUtils.getTenantId(token).equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","解析租户ID失败！");
        }
        String tenantId = encoderUtils.getTenantId(token);
        NoticeGroup noticeGroup =new NoticeGroup();
        noticeGroup.setTenantId(tenantId);
        noticeGroup.setGroupName(groupName);
        noticeGroup.setPageNum(pageNum);
        noticeGroup.setPageSize(pageSize);
        return ResultData.success(mailService.queryNoticeGroupList(noticeGroup));
    }

    /**
     * 创建通知对象组
     * @param noticeGroup
     * @return
     */
    @PostMapping("/alarm/noticeGroup/add")
    @ApiOperation(value = "创建通知对象组",notes = "创建通知对象组",produces = "application/json")
    public ResultData insertNoticeGroup(@RequestBody NoticeGroup noticeGroup,@RequestHeader("x-tKeel-auth") String token){
        if((noticeGroup.getGroupName() == null || noticeGroup.getGroupName().equals("")) || (noticeGroup.getNoticeDesc() == null ||noticeGroup.getNoticeDesc().equals(""))){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","通知对象组名称或描述不能为空！");
        }
        if (token == null || token.equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","token无效！");
        }
        if(encoderUtils.getTenantId(token).equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","解析租户ID失败！");
        }
        noticeGroup.setTenantId(encoderUtils.getTenantId(token));
        log.debug(JSON.toJSONString(noticeGroup));
        return ResultData.success(mailService.createNoticeGroup(noticeGroup));
    }

//    /**
//     * 更新通知对象组邮件信息
//     * @param noticeGroup
//     * @return
//     */
//    @PutMapping("/alarm/noticeGroup/update")
//    @ApiOperation(value = "更新通知对象组",notes = "更新通知对象组",produces = "application/json")
//    public ResultData updateNoticeGroup(@RequestBody NoticeGroup noticeGroup){
//        if(noticeGroup.getNoticeId() == null){
//            return ResultData.fail("io.tkeel.INTERNAL_ERROR","通知邮箱地址或id不能为空！");
//        }
//        log.debug(JSON.toJSONString(noticeGroup));
//        return ResultData.success(mailService.updateEmailAddress(noticeGroup));
//    }

    /**
     * 删除通知对象组（假删）
     * @param noticeGroup
     * @return
     */
    @DeleteMapping("/alarm/noticeGroup/delete")
    @ApiOperation(value = "删除通知对象组",notes = "删除通知对象组",produces = "application/json")
    public ResultData deleteNoticeGroup(@RequestBody NoticeGroup noticeGroup){
        if(noticeGroup.getNoticeId() == null || noticeGroup.getDeleted() == null){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","参数不能为空！");
        }
        log.debug(JSON.toJSONString(noticeGroup));
        return ResultData.success(mailService.deleteNoticeGroup(noticeGroup));
    }

    @GetMapping("/alarm/noticeGroup/binding/query")
    @ApiOperation(value = "查询通知对象组是否绑定规则",notes = "查询通知对象组是否绑定规则",produces = "application/json")
    public ResultData bindingQuery(@RequestHeader("x-tKeel-auth") String token,
                                   @RequestParam(value = "noticeId",required = false) String noticeId){
        System.out.println("/rule/query token ====="+token);
        if (token == null || token.equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","token无效！");
        }
        if(encoderUtils.getTenantId(token).equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","解析租户ID失败！");
        }
        String tenantId = encoderUtils.getTenantId(token);
        BindingQuery bindingQuery =new BindingQuery();
        bindingQuery.setTenantId(tenantId);
        bindingQuery.setNoticeId(noticeId);
        return ResultData.success(ruleMapper.queryNoticeByNoticeId(bindingQuery));
    }

    @GetMapping("/alarm/noticeGroup/queryNoticeGroupByIds")
    @ApiOperation(value = "根据ID查询通知对象组",notes = "根据ID查询通知对象组",produces = "application/json")
    public ResultData queryNoticeGroupByIds(@RequestParam(value = "noticeId",required = false) String noticeId){
        if(noticeId == null || noticeId.equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","通知id参数不能为空！");
        }
        List<String> asList = Arrays.asList(noticeId.split(","));
        List<Long> noticeIds = new ArrayList<>();
        for(String id : asList){
            noticeIds.add(Long.parseLong(id));
        }
        Map<String,Object> map =new HashMap<>();
        map.put("list",mailService.queryNoticeGroupByIds(noticeIds));
        return ResultData.success(map);
    }

    @PostMapping("/alarm/email/test")
    @ApiOperation(value = "测试邮件发送",notes = "测试邮件发送",produces = "application/json")
    public ResultData testSendEmail(@RequestBody Email email){
        if(email == null){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","无效参数！");
        }
//        EmailSendUtils emailSendUtils =new EmailSendUtils();
        int code = emailSendUtils.sendWithText(email);
        if(code > 0){
            return ResultData.success(code);
        }
        return ResultData.fail("io.tkeel.ERROR","邮件发送失败，请检查邮件配置！");
    }

    @GetMapping("/alarm/email/query")
    @ApiOperation(value = "根据通知ID查询邮箱地址信息",notes = "根据通知ID查询邮箱地址信息",produces = "application/json")
    public ResultData queryAddressByNoticeId(@RequestParam(value = "noticeId",required = false) Long noticeId){
        if(noticeId == null || noticeId.equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","通知id参数不能为空！");
        }
        return ResultData.success(mailService.queryEmailAddressByNoticeId(noticeId));
    }

    @PostMapping("/alarm/email/create")
    @ApiOperation(value = "新建邮件地址",notes = "新建邮件地址",produces = "application/json")
    public ResultData createEmailAddress(@RequestBody EmailAddressVo emailAddressVo){
        if(emailAddressVo.getNoticeId() == null || emailAddressVo.getEmailAddress() == null|| emailAddressVo.getUserName() == null){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","无效参数！");
        }
        int code = mailService.createEmailAddress(emailAddressVo);
        if(code > 0){
            return ResultData.success(code);
        }
        return ResultData.fail("io.tkeel.ERROR","新增邮件地址失败！");
    }

    @PostMapping("/alarm/email/update")
    @ApiOperation(value = "更新邮件地址",notes = "更新邮件地址",produces = "application/json")
    public ResultData updateEmailAddress(@RequestBody EmailAddressVo emailAddressVo){
        if(emailAddressVo.getId()== null){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","ID不能为空！");
        }
        int code = mailService.updateEmailAddress(emailAddressVo);
        if(code > 0){
            return ResultData.success(code);
        }
        return ResultData.fail("io.tkeel.ERROR","更新邮件地址失败！");
    }

    @GetMapping("/alarm/email/queryCount")
    @ApiOperation(value = "根据通知ID查询邮箱地址信息",notes = "根据通知ID查询邮箱地址信息",produces = "application/json")
    public ResultData queryCount(@RequestHeader("x-tKeel-auth") String token){
        System.out.println("/alarm/email/queryCount ====="+token);
        if (token == null || token.equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","token无效！");
        }
        if(encoderUtils.getTenantId(token).equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","解析租户ID失败！");
        }
        String tenantId = encoderUtils.getTenantId(token);
        return ResultData.success(mailService.queryNoticeCount(tenantId));
    }

}
