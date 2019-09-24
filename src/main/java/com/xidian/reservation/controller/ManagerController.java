package com.xidian.reservation.controller;

import com.xidian.reservation.annotation.ManagerLoginToken;
import com.xidian.reservation.entity.Consumer;
import com.xidian.reservation.entity.Room;
import com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody;
import com.xidian.reservation.service.ConsumerService;
import com.xidian.reservation.service.LockService;
import com.xidian.reservation.service.ReserveService;
import com.xidian.reservation.service.RoomService;
import com.xidian.reservation.utils.DateAndTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author ：Maolin
 * @className ：ManagerController
 * @date ：Created in 2019/9/3 16:15
 * @description： 管理员控制层
 * @version: 1.0
 */
@RestController
@Slf4j
@RequestMapping("/manager")
public class ManagerController {

    @Resource
    private ConsumerService consumerService;

    @Resource
    private RoomService roomService;

    @Resource
    private LockService lockService;

    @Resource
    private ReserveService reserveService;

    /**
     * @Description: 添加教室
     * @Date: 21:00 2019/9/3
     * @Param: [room]
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @ManagerLoginToken
    @RequestMapping(value = "/add/room", method = RequestMethod.POST)
    public UniversalResponseBody addRoom(@NotNull Room room) {
        return roomService.saveRoom(room);
    }

    /**
     * @Description: 添加用户
     * @Date: 21:00 2019/9/3
     * @Param: [consumer]
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @ManagerLoginToken
    @RequestMapping(value = "/add/consumer", method = RequestMethod.POST)
    public UniversalResponseBody addUser(@NotNull Consumer consumer) {
        return consumerService.saveConsumer(consumer);
    }


    /**
     * @Description: 删除用户
     * @Date: 21:14 2019/9/3
     * @Param: [consumerId]
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @ManagerLoginToken
    @RequestMapping(value = "/delete/consumer/{consumerId}", method = RequestMethod.GET)
    public UniversalResponseBody deleteUser(@NotNull @PathVariable("consumerId") Long consumerId) {
        return consumerService.deleteConsumer(consumerId);
    }

    /**
     * @Description: 分页查看用户
     * @Date:        20:57 2019/9/5
     * @Param:       [pageNo, pageSize]
     * @return:      com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @ManagerLoginToken
    @RequestMapping(value = "/view/consumer/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public UniversalResponseBody viewUser(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return consumerService.findAllConsumers(pageNum,pageSize);
    }

    /**
     * @Description: 删除教室
     * @Date: 21:28 2019/9/3
     * @Param: [roomId]
     * @return: com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @ManagerLoginToken
    @RequestMapping(value = "/delete/room/{roomId}", method = RequestMethod.GET)
    public UniversalResponseBody deleteRoom(@NotNull @PathVariable("roomId") Integer roomId) {
        return roomService.deleteRoom(roomId);
    }

    /**
     * @Description: 分页查看教室
     * @Date:        22:32 2019/9/5
     * @Param:       [pageNo, pageSize]
     * @return:      com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @ManagerLoginToken
    @RequestMapping(value = "/view/room/{pageNum}/{pageSize}", method = RequestMethod.GET)
    public UniversalResponseBody viewRoomsByPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return roomService.findRoomByPage(pageNum,pageSize);
    }


    /**
     * @Description: 获取锁的状态
     * @Date:        20:43 2019/9/20
     * @Param:       [roomId]
     * @return:      com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @ManagerLoginToken
    @RequestMapping(value = "/room/get/status/{roomId}", method = RequestMethod.GET)
    public UniversalResponseBody getLockStatus(@NotNull @PathVariable("roomId") Integer roomId) throws Exception{
            return UniversalResponseBody.success(lockService.getStatus(roomId));
    }


    /**
     * @Description: 远程开锁
     * @Date:        10:36 2019/9/20
     * @Param:       [roomId, managerName, managerTel]
     * @return:      com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @ManagerLoginToken
    @RequestMapping(value = "/room/open", method = RequestMethod.POST)
    public UniversalResponseBody openRoom(@NotNull @RequestParam("roomId") Integer roomId,
                                          @NotNull @RequestParam("managerName") String managerName,
                                          @NotNull @RequestParam("managerTel") String managerTel) throws Exception{
        if (lockService.getOpen(roomId,managerName,managerTel)){
            return UniversalResponseBody.success();
        }else {
            log.error("远程开锁失败!");
            return UniversalResponseBody.error("Failed open door!");
        }
    }


    /**
     * @Description: 返回一周预约表
     * @Date:        21:03 2019/9/21
     * @Param:       [weekNum]
     * @return:      com.xidian.reservation.exceptionHandler.Response.UniversalResponseBody
     */
    @ManagerLoginToken
    @RequestMapping(value = "/reserve/plan/{weekNum}", method = RequestMethod.POST)
    public UniversalResponseBody openRoom(@NotNull @PathVariable("weekNum") Integer weekNum,
                                          @NotNull @RequestParam("roomId") Integer roomId) throws Exception{
        return reserveService.findReserveByWeekAndDateTime(roomId,weekNum);
    }


    @ManagerLoginToken
    @RequestMapping(value = "/week/{weekNum}", method = RequestMethod.GET)
    public UniversalResponseBody weeks(@NotNull @PathVariable("weekNum") Integer weekNum) throws Exception{
        Date date = DateAndTimeUtil.addDay(new Date(System.currentTimeMillis()), weekNum * 7);
        return UniversalResponseBody.success(DateAndTimeUtil.getDateToWeek(date));
    }
}
