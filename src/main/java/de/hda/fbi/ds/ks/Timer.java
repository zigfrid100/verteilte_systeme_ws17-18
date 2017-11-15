package de.hda.fbi.ds.ks;

/**
 * Created by zigfrid on 13.11.17.
 */
public class Timer {

    private double startTime;
    private double endTime;
    private double isTime;

    Timer(){
        startTime = System.currentTimeMillis();
    }

    public double getStartTime(){
        return this.startTime;
    }

    public double getEndTime(){
        return this.endTime = System.currentTimeMillis();
    }

    public double getIsTime(){
        return this.isTime;
    }

    public void setEndTime(double value){
        this.endTime = value;
    }

    public void setIsTime(double value){
        this.isTime = value;
    }

    public double counting(){
        return this.endTime - this.startTime;
    }

}
