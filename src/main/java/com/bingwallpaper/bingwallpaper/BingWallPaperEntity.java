package com.bingwallpaper.bingwallpaper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author JingKe
 * @version 1.0
 * @description 壁纸实体类
 * @date 2025/2/3 15:45:48
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BingWallPaperEntity {
	public String url;
	public String title;
	public String startdate;
	public String fullstartdate;
	public String enddate;
	public String urlbase;
	public String copyright;
	public String copyrightlink;
	public String quiz;
	public boolean wp;
	public String hsh;
	public Integer drk;
	public Integer top;
	public Integer bot;
	public List hs;

	public String getUrl() {
		return url;
	}
}
