package com.bingwallpaper.bingwallpaper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;


@RestController
@RequestMapping("/wallPaper")
public class BingWallPaperController {
	//前缀
	String bingUrl = "https://cn.bing.com";
	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * @return {@link org.springframework.http.ResponseEntity<byte[]>}
	 * @author JingKe
	 * @description 获取必应历史五天内的随机壁纸，返回JSON数据
	 * @date 2025/8/10 20:00
	 */
	@GetMapping("/getJson")
	private BingWallPaperEntity getJson() throws Exception {
		Random random = new Random();
		// 生成1到7之间的随机数
		int randomNumber = random.nextInt(7) + 1;
		String randomStr = String.valueOf(randomNumber);
		//请求必应官方接口
		ResponseEntity<String> response = restTemplate.getForEntity(bingUrl + "/HPImageArchive.aspx?format=js&idx=" + randomStr + "&n=1", String.class);
		//获取响应体
		String responseBody = response.getBody();
		//提取响应体中的图片List
		Map<String, Object> bodyMap = objectMapper.readValue(responseBody, Map.class);
		List<Map<String, Object>> imageList = (List<Map<String, Object>>) bodyMap.get("images");
		//获取单张图片实体
		BingWallPaperEntity bingWallPaperEntity = objectMapper.convertValue(imageList.get(0), BingWallPaperEntity.class);
		return bingWallPaperEntity;
	}

	/**
	 * @return {@link org.springframework.http.ResponseEntity<byte[]>}
	 * @author JingKe
	 * @description 获取必应历史五天内的随机壁纸，直接返回图片数据
	 * @date 2025/8/10 20:00
	 */
	@GetMapping("/getImg")
	private ResponseEntity<byte[]> getImg() throws Exception {
		BingWallPaperEntity bingWallPaperEntity = this.getJson();
		//下载url对应的数据
		URL url = new URL(bingUrl + bingWallPaperEntity.getUrl());
		InputStream inputStream = url.openStream();
		byte[] imageBytes = inputStream.readAllBytes();
		inputStream.close();
		//设置响应格式
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return ResponseEntity.ok().headers(headers).body(imageBytes);
	}

}
