package com.baltan.springboot.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author Baltan
 * @date 2019-01-18 16:09
 */
@Component
public class ScheduledService {

    /**
     * second、minute、hour、day of month、month、day of week
     *
     * @Scheduled(cron = "* * * * * SUN-SAT") 周日到周六每秒执行
     * @Scheduled(cron = "* * * * * 0-7") 周日到周六每秒执行
     * @Scheduled(cron = "0,1,2,3,4 * * * * SUN-SAT") 周日到周六每分钟的0、1、2、3、4秒执行
     * @Scheduled(cron = "0-4 * * * * SUN-SAT") 周日到周六每分钟的0、1、2、3、4秒执行
     * @Scheduled(cron = "0/4 * * * * SUN-SAT") 周日到周六每分钟的0秒启动，每隔4秒执行
     * @Scheduled(cron = "* * * ？ * SUN") 每周日每秒执行，？解决day of month和day of week的冲突
     * @Scheduled(cron = "* * * 1 * ?") 每月1号每秒执行，？解决day of month和day of week的冲突
     * @Scheduled(cron = "* * * ？ * 6#2") 每月第2个星期六每秒执行，？解决day of month和day of week的冲突
     * @Scheduled(cron = "* * * ？ * 7L") 每月最后一个周日每秒执行，？解决day of month和day of week的冲突
     * @Scheduled(cron = "* * * LW * ？") 每月最后一个工作日每秒执行，？解决day of month和day of week的冲突
     */
    @Scheduled(cron = "* * * 1 * ?")
    public void greet() {
        System.out.println("飞向宇宙，浩瀚无垠……");
    }
}
