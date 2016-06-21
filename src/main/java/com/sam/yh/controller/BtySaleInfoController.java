package com.sam.yh.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.req.bean.BtySaleInfoReq;
import com.sam.yh.resp.bean.BtySaleInfo;
import com.sam.yh.resp.bean.BtySaleInfoResp;
import com.sam.yh.resp.bean.ResponseUtils;
import com.sam.yh.resp.bean.SamResponse;
import com.sam.yh.service.UserBatteryService;


@RestController
@RequestMapping("/bty")

public class BtySaleInfoController {
	@Autowired
    UserBatteryService userBatteryService;

    private static final Logger logger = LoggerFactory.getLogger(BtySaleInfoController.class);

    @RequestMapping(value = "/saleinfo", method = RequestMethod.POST)
    public SamResponse signin(HttpServletRequest httpServletRequest, @RequestParam("jsonReq") String jsonReq) {

        logger.info("Request json String:" + jsonReq);
        
        BtySaleInfoReq req = JSON.parseObject(jsonReq, BtySaleInfoReq.class);
        try {
        	DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        	Date date=format1.parse(req.getDate());
           
         	List<BtySaleInfo> btySaleInfos= userBatteryService.fetchBtySaleInfo(date);
         	
         	BtySaleInfoResp respData=new BtySaleInfoResp();
           
            respData.setBtySaleInfos(btySaleInfos);

            return ResponseUtils.getNormalResp(respData);
        }  catch (Exception e) {
        	if(e instanceof CrudException){
        		 logger.error("fetch battery info exception, " +  e);
        		return ResponseUtils.getServiceErrorResp(e.getMessage());
        	}else{
        		return ResponseUtils.getSysErrorResp();
        	}
           
        }
    }

  
}

