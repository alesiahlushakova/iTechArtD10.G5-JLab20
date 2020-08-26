package com.itechart.lab.service;



import com.itechart.lab.model.Period;

import java.sql.Date;


public class PeriodCalculator {

    private static final long MONTH_MILLI_SECONDS_COUNT = 2_592_000_000L;
    private static final long TWO_MONTH_MILLI_SECONDS_COUNT = 2L*2_592_000_000L;
    private static final long THREE_MONTH_MILLI_SECONDS_COUNT = 3L*2_592_000_000L;
    private static final long HALF_YEAR_MILLI_SECONDS_COUNT = 31_536_000_000L/2L;
    private static final long YEAR_MILLI_SECONDS_COUNT = 31_536_000_000L;


    public Date calculateExpirationDate(Period period, Date borrowDate) {
        long time = borrowDate.getTime();
        Date endDate = null;

        switch (period) {
            case ONE: {
                long result = time + MONTH_MILLI_SECONDS_COUNT;
                endDate = new Date(result);
                break;
            }
            case TWO: {
                long result = time + TWO_MONTH_MILLI_SECONDS_COUNT;
                endDate = new Date(result);
                break;
            }
            case THREE: {
                long result = time + THREE_MONTH_MILLI_SECONDS_COUNT;
                endDate = new Date(result);
                break;
            }
            case SIX: {
                long result = time + HALF_YEAR_MILLI_SECONDS_COUNT;
                endDate = new Date(result);
                break;
            }
            case TWELVE: {
                long result = time + YEAR_MILLI_SECONDS_COUNT;
                endDate = new Date(result);
                break;
            }
        }

        return endDate;
    }

}
