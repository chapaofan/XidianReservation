package com.xidian.reservation.controller;

import com.xidian.reservation.annotation.ManagerLoginToken;
import com.xidian.reservation.annotation.UserLoginToken;
import com.xidian.reservation.entity.Reserve;
import com.xidian.reservation.entity.WxInformation;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;
import com.xidian.reservation.service.*;
import com.xidian.reservation.utils.EmojiCharacterUtil;
import com.xidian.reservation.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：Maolin
 * @className ：OrderController
 * @date ：Created in 2019/9/4 20:52
 * @description： order控制层
 * @version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/reserve")
public class ReserveController {

    @Resource
    private ReserveService reserveService;

    @Resource
    private LockService lockService;

    @Resource
    private WxInformationService wxInformationService;

    @Resource
    private RoomService roomService;

    /**
     * @Description: 已被预约时间段
     * @Date: 9:24 2019/9/6
     * @Param: [roomId, reserveDate]
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @UserLoginToken
    @RequestMapping(value = "/occupy", method = RequestMethod.POST)
    public UniversalResponseBody occupyTime(@RequestParam("roomId") int roomId, @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam("reserveDate") Date reserveDate) throws Exception {
        return reserveService.selectNotAllowTime(roomId, reserveDate);
    }


    /**
     * @Description: 预约教室
     * @Date: 9:24 2019/9/6
     * @Param: [httpServletRequest, reserve]
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @UserLoginToken
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public UniversalResponseBody order(HttpServletRequest httpServletRequest, @NotNull Reserve reserve, @NotNull @RequestParam("formId") String formId, @NotNull @RequestParam("code") String code) throws Exception {
        String token = httpServletRequest.getHeader("token");
        Long consumerId = Long.parseLong(TokenUtil.getAppUID(token));

        //过滤表情包
        reserve.setReservePurpose(EmojiCharacterUtil.filter(reserve.getReservePurpose()));
        reserve.setConsumerId(consumerId);
        reserve.setReserveStatus(0);
        //reserve.setOpenPwd(lockService.getPassword(reserve));
        return reserveService.reserveRoom(reserve, formId, code);
    }


    /**
     * @Description: 历史日程
     * @Date: 9:29 2019/9/6
     * @Param: [httpServletRequest, pageNum, pageSize]
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @UserLoginToken
    @RequestMapping(value = "/history/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public UniversalResponseBody findHistoryReserves(HttpServletRequest httpServletRequest,
                                                     @PathVariable("pageNum") int pageNum,
                                                     @PathVariable("pageSize") int pageSize) {
        String token = httpServletRequest.getHeader("token");
        Long consumerId = Long.parseLong(TokenUtil.getAppUID(token));
        return reserveService.findHistoryReserves(consumerId, pageNum, pageSize);
    }

    /**
     * @Description: 我的日程
     * @Date: 11:09 2019/9/6
     * @Param: [httpServletRequest, pageNum, pageSize]
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @UserLoginToken
    @RequestMapping(value = "/myReserve/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public UniversalResponseBody myReserve(HttpServletRequest httpServletRequest,
                                           @PathVariable("pageNum") int pageNum,
                                           @PathVariable("pageSize") int pageSize) {
        String token = httpServletRequest.getHeader("token");
        Long consumerId = Long.parseLong(TokenUtil.getAppUID(token));
        return reserveService.findNotStartReserves(consumerId, pageNum, pageSize);
    }

    /**
     * @Description: 申请队列
     * @Date: 11:34 2019/9/6
     * @Param: [pageNum, pageSize]
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @ManagerLoginToken
    @RequestMapping(value = "/apply/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public UniversalResponseBody applyReserve(@PathVariable("pageNum") int pageNum,
                                              @PathVariable("pageSize") int pageSize) {
        return reserveService.findNotStartAndStatus0(pageNum, pageSize);
    }

    /**
     * @Description: 按教室查找申请队列
     * @Date:        9:30 2019/9/20
     * @Param:       [roomName, pageNum, pageSize]
     * @return:      com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @ManagerLoginToken
    @RequestMapping(value = "/room/apply/{pageNum}/{pageSize}", method = RequestMethod.POST)
    public UniversalResponseBody applyByRoomReserve(@NotNull @RequestParam("roomName") String roomName,
                                                    @PathVariable("pageNum") int pageNum,
                                                    @PathVariable("pageSize") int pageSize) {
        return reserveService.findNotStartAndStatus0ByRoom(roomName, pageNum, pageSize);
    }


    /**
     * @Description: 申请详情页
     * @Date: 10:35 2019/9/7
     * @Param: [reserveId]
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @ManagerLoginToken
    @RequestMapping(value = "/apply/details/{reserveId}", method = RequestMethod.GET)
    public UniversalResponseBody applyDetails(@PathVariable("reserveId") Integer reserveId) throws Exception {
        String otherThing = "用完后请打扫！";
        //String customerPassword = lockService.getPassword(reserveId);
        String shortMessage = "密码仅能在距开始时间十分钟内显示";
        return reserveService.findReserveDetails(reserveId, otherThing, shortMessage);
    }


    /**
     * @Description: 管理员审核通过（启用）
     * @Date: 10:43 2019/9/7
     * @Param: [reserveId, otherThing, shortMessage]
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @ManagerLoginToken
    @RequestMapping(value = "/apply/agree", method = RequestMethod.POST)
    public UniversalResponseBody applyAgree(@NotNull Reserve reserveData,
                                            @NotNull @RequestParam("otherThing") String otherThing,
                                            @NotNull @RequestParam("shortMessage") String shortMessage) throws Exception {

        //reserveStatus:100审核通过 500审核不通过 200取消会议
        //String WxMSS = otherThing;
        Reserve reserve = reserveService.findReserveByReserveId(reserveData.getReserveId());


        WxInformation wxInformation = wxInformationService.findByConsumerId(reserveData.getReserveId());
        if (reserve == null || wxInformation == null) {
            return UniversalResponseBody.error("Query data is empty!");
        } else {
            //管理员更改教室和时间
            reserve.setRoomName(reserveData.getRoomName());

            reserve.setReserveStart(reserveData.getReserveStart());
            reserve.setReserveEnd(reserveData.getReserveEnd());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String reserveDate = formatter.format(reserve.getReserveDate());
            SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
            String startTime = formatter2.format(reserve.getReserveStart());
            String endTime = formatter2.format(reserve.getReserveEnd());
            String reserveDateTime = reserveDate + " " + startTime + "--" + endTime;

            reserve.setReserveStatus(100);
            return reserveService.changeReserve(reserve, wxInformation.getFormId(), reserveDateTime, "审核通过。密码请在预约开始前20分钟内在小程序查看", otherThing);
        }
    }

    /**
     * @Description: 管理员审核不通过（取消）
     * @Date: 10:58 2019/9/7
     * @Param: [reserveId]
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @ManagerLoginToken
    @RequestMapping(value = "/apply/disagree", method = RequestMethod.POST)
    public UniversalResponseBody applyAgree(@RequestParam("reserveId") Integer reserveId) throws Exception {
        //reserveStatus:100审核通过 500审核不通过 200取消会议

        Reserve reserve = reserveService.findReserveByReserveId(reserveId);
        WxInformation wxInformation = wxInformationService.findByConsumerId(reserveId);
        if (reserve == null || wxInformation == null) {
            return UniversalResponseBody.error("Query data is empty!");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String reserveDate = formatter.format(reserve.getReserveDate());
            SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
            String startTime = formatter2.format(reserve.getReserveStart());
            String endTime = formatter2.format(reserve.getReserveEnd());
            String reserveDateTime = reserveDate + " " + startTime + "--" + endTime;

            /*String result = wxPushService.wxPushOneUser(reserve.getReserveId(), wxInformation.getFormId(), reserve.getRoomName(), reserve.getReserveName(), "审核不通过",
                    reserveDateTime, "审核不通过，有问题请联系管理员");*/
            reserve.setReserveStatus(500);
            return reserveService.changeReserve(reserve, wxInformation.getFormId(), reserveDateTime, "审核不通过", "有问题请联系管理员重新申请");
        }
    }

    /**
     * @Description: 取消申请
     * @Date: 11:16 2019/9/7
     * @Param: [reserveId]
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @UserLoginToken
    @RequestMapping(value = "/apply/cancel/{reserveId}", method = RequestMethod.GET)
    public UniversalResponseBody applyCancel(@PathVariable("reserveId") Integer reserveId) {
        int status = 200;//100审核通过 500审核不通过 200取消会议
        if (reserveService.updateStatus(reserveId, status)) {
            return UniversalResponseBody.success();
        } else {
            return UniversalResponseBody.error("Data update failure!");
        }
    }


    /**
     * @Description: 查看所有教室
     * @Date: 22:36 2019/9/5
     * @Param: []
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @RequestMapping(value = "/view/room/all", method = RequestMethod.GET)
    public UniversalResponseBody viewAllRoom() {
        return roomService.findAllRoom();
    }


}
