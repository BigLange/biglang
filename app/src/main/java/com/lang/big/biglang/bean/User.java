package com.lang.big.biglang.bean;

<<<<<<< Updated upstream

import com.lang.big.biglang.R;
import com.lang.big.biglang.utils.MyOkHttp_util;
import com.squareup.picasso.Picasso;

public class User {
	private int id;
	private String userName;
	private String uSex;
	private String uemial;
	private String uArea;
	private String uTell;
	private String uAddress;
	private long longDate;
	private String head;
	/**
	 * @return the longDate
	 */
	public long getLongDate() {
		return longDate;
	}
	/**
	 * @param longDate the longDate to set
	 */
	public void setLongDate(long longDate) {
		this.longDate = longDate;
	}
	/**
	 * @return the userName
	 */
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the uSex
	 */
	public String getuSex() {
		return uSex;
	}
	/**
	 * @param uSex the uSex to set
	 */
	public void setuSex(String uSex) {
		this.uSex = uSex;
	}
	/**
	 * @return the uemial
	 */
	public String getUemial() {
		return uemial;
	}
	/**
	 * @param uemial the uemial to set
	 */
	public void setUemial(String uemial) {
		this.uemial = uemial;
	}
	/**
	 * @return the uArea
	 */
	public String getuArea() {
		return uArea;
	}
	/**
	 * @param uArea the uArea to set
	 */
	public void setuArea(String uArea) {
		this.uArea = uArea;
	}
	/**
	 * @return the uTell
	 */
	public String getuTell() {
		return uTell;
	}
	/**
	 * @param uTell the uTell to set
	 */
	public void setuTell(String uTell) {
		this.uTell = uTell;
	}
	/**
	 * @return the uAddress
	 */
	public String getuAddress() {
		return uAddress;
	}
	/**
	 * @param uAddress the uAddress to set
	 */
	public void setuAddress(String uAddress) {
		this.uAddress = uAddress;
	}
	
	public void setHead(String head) {
		this.head = head;
	}
	public String getHead() {
		return head;
	}

	
	public String getImagePath(){
		String mPath = MyOkHttp_util.ServicePath+"Myimg";
		if("n".equals(this.getHead())){
			mPath += "/hand.jpg";
		}else {
			mPath += "/UserHead/"+this.getId() + ".jpg";
		}
		return mPath;
	}
	
=======
import java.util.Date;

/**
 * Created by Administrator on 2016/4/26.
 */
public class User {
    private int id;
    private String userName;
    private String uSex;
    private String uemial;
    private String uArea;
    private String uTell;
    private String uAddress;
    private long longDate;
    private String head;
    public String getHead() {
        return head;
    }
    public void setHead(String head) {
        this.head = head;
    }

    public long getLongDate() {
        return longDate;
    }

    public void setLongDate(long longDate) {
        this.longDate = longDate;
    }


    /**
     * @return the userName
     */
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * @return the uSex
     */
    public String getuSex() {
        return uSex;
    }
    /**
     * @param uSex the uSex to set
     */
    public void setuSex(String uSex) {
        this.uSex = uSex;
    }
    /**
     * @return the uemial
     */
    public String getUemial() {
        return uemial;
    }
    /**
     * @param uemial the uemial to set
     */
    public void setUemial(String uemial) {
        this.uemial = uemial;
    }
    /**
     * @return the uArea
     */
    public String getuArea() {
        return uArea;
    }
    /**
     * @param uArea the uArea to set
     */
    public void setuArea(String uArea) {
        this.uArea = uArea;
    }
    /**
     * @return the uTell
     */
    public String getuTell() {
        return uTell;
    }
    /**
     * @param uTell the uTell to set
     */
    public void setuTell(String uTell) {
        this.uTell = uTell;
    }
    /**
     * @return the uAddress
     */
    public String getuAddress() {
        return uAddress;
    }
    /**
     * @param uAddress the uAddress to set
     */
    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }

    /**
     * @param udate the udate to set
     */

>>>>>>> Stashed changes
}
