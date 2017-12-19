package domain;

import java.util.Calendar;
import java.util.Objects;

public class MyDate implements Comparable<MyDate>{
    
    private Calendar date = Calendar.getInstance();
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    
    public MyDate(Calendar date){
        setDate(date);
    }
    public MyDate(int year, int month, int day){
        setDate(year, month, day);
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
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="methods">
    
    @Override
    public int compareTo(MyDate t) {
        return date.compareTo(t.getDate());
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

