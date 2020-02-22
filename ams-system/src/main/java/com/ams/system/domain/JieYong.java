package com.ams.system.domain;

import lombok.Data;

@Data
public class JieYong {

    private String borrowId;
    private String groupByDay;
    private String groupByYear;
    private String groupByMonth;
    private String dayTotal;
    private String yearTotal;
    private String monthTotal;
}
