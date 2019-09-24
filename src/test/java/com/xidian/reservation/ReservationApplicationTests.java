package com.xidian.reservation;

import com.xidian.reservation.dao.ReserveMapper;
import com.xidian.reservation.entity.Reserve;
import com.xidian.reservation.utils.DateAndTimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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

}
