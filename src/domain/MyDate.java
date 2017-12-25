package domain;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * A custom Date class to easily compare all different Date types
 */
public class MyDate implements Comparable<MyDate>{
    
    private Calendar date = Calendar.getInstance();
    private final static SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    
    public MyDate(Calendar date){
        setDate(date);
    }
    public MyDate(int year, int month, int day){
        setDate(year, month, day);
    }
    public MyDate(LocalDate date){
        setDate(date);
    }
    public MyDate(Date date){
        setDate(date);
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getters/setters">
    
    public final void setDate(Calendar date){
        DomainHelper.checkForValue("date", date);
        this.date = date;
    }
    public Calendar getDate(){return date;}
    
    public final void setDate(int year, int month, int day){
        DomainHelper.checkForDate(year, month, day);
        this.date.set(year, month-1, day);
    }
    public int getYear(){return date.get(Calendar.YEAR);}
    public int getMonth(){return date.get(Calendar.MONTH)+1;}
    public int getDay(){return date.get(Calendar.DATE);}
    
    public final void setDate(LocalDate date){
        DomainHelper.checkForValue("date", date);
        setDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
    public LocalDate getDateAsLocalDate(){return date.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();}
    
    public final void setDate(Date date){
        DomainHelper.checkForValue("date", date);
        this.date.setTime(date);
    }
    public Date getDateAsDate(){return date.getTime();}
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    
    //<editor-fold defaultstate="collapsed" desc="comparing methods">
    
    @Override
    public int compareTo(MyDate date) {
        DomainHelper.checkForValue("date", date);
        return this.date.compareTo(date.getDate());
    }

    public boolean before(MyDate date) {
        DomainHelper.checkForValue("date", date);
        return this.date.before(date.getDate());
    }

    public boolean after(MyDate date) {
        DomainHelper.checkForValue("date", date);
        return this.date.after(date.getDate());
    }

    //</editor-fold>

    @Override
    public String toString() {
        return FORMAT.format(date.getTime());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MyDate other = (MyDate) obj;
        return compareTo(other)==0;
    }
    
    //</editor-fold>

}

