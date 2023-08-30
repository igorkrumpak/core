package si.iitech.util;

import java.util.Date;

public class Interval {

    public Date dateFrom;
    public Date dateUntil;

    public Interval(Date dateFrom, Date dateUntil) {
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
    }

    @Override
    public String toString() {
        return DateUtils.formatDate(dateFrom) + " : " +  DateUtils.formatDate(dateUntil);
    }
    
}
