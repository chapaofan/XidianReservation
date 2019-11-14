package com.xidian.reservation;

import com.xidian.reservation.dao.ReserveMapper;
import com.xidian.reservation.entity.Reserve;
import com.xidian.reservation.utils.DateAndTimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationApplicationTests {

    @Resource
    private ReserveMapper reserveMapper;
    @Test
    public void contextLoads() {
    }

    @Test
    public void timeTest()throws ParseException {
        //System.out.println(DateAndTimeUtil.compareToCurTime("2019-11-13 20:50:00"));//t
        //System.out.println(new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(new Date(new (new Date()).getTime()+12000)));//t
        //System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(new Date().getTime()+120000)));
        System.out.println(new Date(new Date().getTime()+120000));
    }

}
