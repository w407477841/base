package com.zyiot.commonservice.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wyf
 * @since 2018-03-06
 */
@TableName("t_shangji_info")
public class ShangjiInfo extends Model<ShangjiInfo> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 申报时间
     */
	private String sbsj;
    /**
     * 部门
     */
	@TableField(exist=false)
	private String bmtxt;
	
  

	public String getBmtxt() {
		return bmtxt;
	}

	public void setBmtxt(String bmtxt) {
		this.bmtxt = bmtxt;
	}

	/**
     * 填报人
     */
	private String yg;
	@TableField(exist=false)
	private String ygtxt;
	
    public String getYgtxt() {
		return ygtxt;
	}

	public void setYgtxt(String ygtxt) {
		this.ygtxt = ygtxt;
	}

	/**
     * 项目编号
     */
	private String xmbh;
    /**
     * 客户全称
     */
	private String khqc;
    /**
     * 客户联系人
     */
	private String khlxr;
    /**
     * 联系方式
     */
	private String lxfs;
    /**
     * 项目名称
     */
	private String xmmc;
    /**
     * 商机来源
     */
	private String sjly;
    /**
     * 预算合同金额(元)
     */
	private String yshtje;
    /**
     * 审核状态 0不通过 1通过
     */
	private Integer shzt;
    /**
     * 公示状态 0未公示 1公示
     */
	private Integer gszt;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSbsj() {
		return sbsj;
	}

	public void setSbsj(String sbsj) {
		this.sbsj = sbsj;
	}


	public String getYg() {
		return yg;
	}

	public void setYg(String yg) {
		this.yg = yg;
	}

	public String getXmbh() {
		return xmbh;
	}

	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}

	public String getKhqc() {
		return khqc;
	}

	public void setKhqc(String khqc) {
		this.khqc = khqc;
	}

	public String getKhlxr() {
		return khlxr;
	}

	public void setKhlxr(String khlxr) {
		this.khlxr = khlxr;
	}

	public String getLxfs() {
		return lxfs;
	}

	public void setLxfs(String lxfs) {
		this.lxfs = lxfs;
	}

	public String getXmmc() {
		return xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	public String getSjly() {
		return sjly;
	}

	public void setSjly(String sjly) {
		this.sjly = sjly;
	}

	public String getYshtje() {
		return yshtje;
	}

	public void setYshtje(String yshtje) {
		this.yshtje = yshtje;
	}

	public Integer getShzt() {
		return shzt;
	}

	public void setShzt(Integer shzt) {
		this.shzt = shzt;
	}

	public Integer getGszt() {
		return gszt;
	}

	public void setGszt(Integer gszt) {
		this.gszt = gszt;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TShangjiInfo{" +
			"id=" + id +
			", sbsj=" + sbsj +
			", yg=" + yg +
			", xmbh=" + xmbh +
			", khqc=" + khqc +
			", khlxr=" + khlxr +
			", lxfs=" + lxfs +
			", xmmc=" + xmmc +
			", sjly=" + sjly +
			", yshtje=" + yshtje +
			", shzt=" + shzt +
			", gszt=" + gszt +
			"}";
	}
}
