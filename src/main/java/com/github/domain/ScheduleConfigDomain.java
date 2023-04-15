package com.github.domain;

import java.util.List;
import java.util.Map;

public class ScheduleConfigDomain {

    private Map<String, List<String>> dayWithScheduleTime;

    private List<Integer> freeDayOfMount;

    private Integer[] rangeScheduleOfDay = new Integer[2];

    /**
     * @return the dayWithScheduleTime
     */
    public Map<String, List<String>> getDayWithScheduleTime() {
        return dayWithScheduleTime;
    }

    /**
     * @param dayWithScheduleTime the dayWithScheduleTime to set
     */
    public void setDayWithScheduleTime(Map<String, List<String>> dayWithScheduleTime) {
        this.dayWithScheduleTime = dayWithScheduleTime;
    }

    /**
     * @return the freeDayOfMount
     */
    public List<Integer> getFreeDayOfMount() {
        return freeDayOfMount;
    }

    /**
     * @param freeDayOfMount the freeDayOfMount to set
     */
    public void setFreeDayOfMount(List<Integer> freeDayOfMount) {
        this.freeDayOfMount = freeDayOfMount;
    }

    /**
     * @return the rangeScheduleOfDay
     */
    public Integer[] getRangeScheduleOfDay() {
        return rangeScheduleOfDay;
    }

    /**
     * @param rangeScheduleOfDay the rangeScheduleOfDay to set
     */
    public void setRangeScheduleOfDay(Integer... rangeScheduleOfDay) {
        this.rangeScheduleOfDay[0] = rangeScheduleOfDay[0];
        this.rangeScheduleOfDay[1] = rangeScheduleOfDay[1];
    }

}
