package com.mh.entity;

import java.util.Date;


/**
 * TWebElectronicClass entity. @author MyEclipse Persistence Tools
 */
public class TWebElectronicClass  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private String eleFlat;
     private String eleType;
     private String eleTypeName;
     private Integer eleLines;
     private Integer eleStatus;
     private Integer eleIndex;
     private Date autoUpdateTime;


    // Constructors

    /** default constructor */
    public TWebElectronicClass() {
    }

    
    /** full constructor */
    public TWebElectronicClass(String eleFlat, String eleType, String eleTypeName, Integer eleLines, Integer eleStatus, Integer eleIndex, Date autoUpdateTime) {
        this.eleFlat = eleFlat;
        this.eleType = eleType;
        this.eleTypeName = eleTypeName;
        this.eleLines = eleLines;
        this.eleStatus = eleStatus;
        this.eleIndex = eleIndex;
        this.autoUpdateTime = autoUpdateTime;
    }

   
    // Property accessors
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    

    public String getEleFlat() {
        return this.eleFlat;
    }
    
    public void setEleFlat(String eleFlat) {
        this.eleFlat = eleFlat;
    }
    

    public String getEleType() {
        return this.eleType;
    }
    
    public void setEleType(String eleType) {
        this.eleType = eleType;
    }
    

    public String getEleTypeName() {
        return this.eleTypeName;
    }
    
    public void setEleTypeName(String eleTypeName) {
        this.eleTypeName = eleTypeName;
    }
    

    public Integer getEleLines() {
        return this.eleLines;
    }
    
    public void setEleLines(Integer eleLines) {
        this.eleLines = eleLines;
    }
    

    public Integer getEleStatus() {
        return this.eleStatus;
    }
    
    public void setEleStatus(Integer eleStatus) {
        this.eleStatus = eleStatus;
    }
    

    public Integer getEleIndex() {
        return this.eleIndex;
    }
    
    public void setEleIndex(Integer eleIndex) {
        this.eleIndex = eleIndex;
    }

    public Date getAutoUpdateTime() {
        return this.autoUpdateTime;
    }
    
    public void setAutoUpdateTime(Date autoUpdateTime) {
        this.autoUpdateTime = autoUpdateTime;
    }

}