package com.fl.mfs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.fl.mfs.pojo.Info;
import com.fl.mfs.service.NoticeService;
import com.rabbitmq.client.Connection;

public class Utils {
	private static Map<String,Object> ipInfo = null;
	private static Boolean isIpEnable = false;
	private static String IpUrl = "http://http.9vps.com/getip.asp?username=fuling&pwd=8a0cd7020b4d534580bb02590f6ef1a0&geshi=1&fenge=1&fengefu=&getnum=1";
	private static String[] UserAgents = new String[] {
			"Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1"
//			"Mozilla/4.0 (Windows; MSIE 6.0; Windows NT 5.2)",
//			"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)",
//			"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
//			"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
//			"Mozilla/5.0 (compatible; WOW64; MSIE 10.0; Windows NT 6.2)",
//			"Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv 11.0) like Gecko",
//			"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36Edge/13.10586",
//			"Mozilla/4.0 (compatible; MSIE 7.0; Windows Phone OS 7.0; Trident/3.1; IEMobile/7.0; LG; GW910)",
//			"Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0; SAMSUNG; SGH-i917)",
//			"Mozilla/5.0 (compatible; MSIE 10.0; Windows Phone 8.0; Trident/6.0; IEMobile/10.0; ARM; Touch; NOKIA; Lumia 920)",
//			"Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.94 Safari/537.36",
//			"Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) CriOS/27.0.1453.10 Mobile/10B350 Safari/8536.25",
//			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.93 Safari/537.36",
//			"Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19",
//			"Mozilla/5.0 (Linux; Android 4.1.2; Nexus 7 Build/JZ054K) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Safari/535.19",
//			"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_6; en-US) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27",
//			"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US) AppleWebKit/533.20.25 (KHTML, like Gecko) Version/5.0.4 Safari/533.20.27",
//			"Mozilla/5.0 (iPad; CPU OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3",
//			"Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3",
//			"Mozilla/5.0 (iPad; CPU OS 8_1_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12B466 Safari/600.1.4",
//			"Mozilla/5.0 (iPhone; CPU iPhone OS 8_0_2 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) Version/8.0 Mobile/12A366 Safari/600.1.4",
//			"Mozilla/5.0 (iPod; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3",
//			"Mozilla/5.0 (Android; Mobile; rv:14.0) Gecko/14.0 Firefox/14.0",
//			"Mozilla/5.0 (Linux; Android 4.1.2; Nexus 7 Build/JZ054K) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Safari/535.19",
//			"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:21.0) Gecko/20100101 Firefox/21.0",
//			"Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:21.0) Gecko/20130331 Firefox/21.0",
//			"Mozilla/5.0 (Windows NT 6.2; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0",
//			"Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.9.168 Version/11.52",
//			"Opera/9.80 (Windows NT 6.1; WOW64; U; en) Presto/2.10.229 Version/11.62"
	};
	
	private static String[] Referer = new String[] {
			"https://www.douban.com/"
//			"https://www.douban.com/group/explore/travel",
//			"https://www.douban.com/group/explore",
//			"https://read.douban.com/",
//			"https://www.douban.com/group/nanshanzufang/",
//			"https://img3.doubanio.com/f/accounts/1757b2517ca53f38d61f5c584bb82061f8a8af7c/passport/build/login/base.css",
	};
	
	public static int pc(String url) throws IOException {
		String str = request(url);
		if(str == null || str.equals("")) {
			isIpEnable = false;
			return -1;
		}
		Document document = Jsoup.parse(str);
		int length = document.select("#content").html().length();
		if(length == 0) {
			isIpEnable = false;
			return -1;
		}
		return length;
		
		
		
//*		Document document = Jsoup.connect(url)
//				.ignoreContentType(true)
//				.userAgent("")
//				.get();
		
		
		
//		document.select("#content>p").forEach(e->{
//			System.out.println(e.html());
//		});
//		document.select("#content .image-wrapper").forEach(e->{
//			System.out.println(e.html());
//		});
//		System.out.print(document.select("h1.title").html());
		
		
//*		return document.select("#content").html().length();
		
		
//		System.out.print(document.html());
		
	}
	
	public static Info getInfo(String url) throws IOException {
		String str = request(url);
		int count = 1;
		if((str == null || str.equals("")) && count < 5) {
			isIpEnable = false;
			str = request(url);
			count++;
		}
		if(count >=5)return null;
		Info info = new Info();
		Document document = Jsoup.parse(str);
		info.title = document.select("h1.title").html();
		info.topic = url.substring(url.substring(0, url.lastIndexOf("/")).lastIndexOf("/")+1, url.lastIndexOf("/"));
		info.oldContentLength = document.select("#content").html().length();
		info.url = url;
		info.subscribeCount = 1;
		return info;
	}
	
	private static String request(String url) {
		if(url==null||url.equals(""))return null;
		InputStream in = null;
		BufferedReader br = null;
		try {
			if(ipInfo == null || !isIpEnable) {
				int cc = 1;
				Response conn = Jsoup.connect(IpUrl).timeout(2000).execute();
				while(conn.statusCode() != HttpURLConnection.HTTP_OK && cc < 5) {
					Thread.sleep(1100);
					conn = Jsoup.connect(IpUrl).execute();
					cc++;
				}
				if(cc >= 5) {
					System.exit(-1);
				}
				
				
				
				String ipaddre = conn
						.body();
				String[] addressInfo = ipaddre.split(":");
				
				cc = 1;
				while(addressInfo.length!=2 && cc < 5) {
						Thread.sleep(1100);
						conn = Jsoup.connect(IpUrl).timeout(2000).execute();
						ipaddre = conn
								.body();
						addressInfo = ipaddre.split(":");
						cc++;
				}
				
				if(cc >= 5) {
					System.exit(-1);
				}
						
				ipInfo = new HashMap<>();
				ipInfo.put("ip", addressInfo[0].trim());
				ipInfo.put("port", Integer.valueOf(addressInfo[1].trim()));
				isIpEnable = true;		

				System.out.println("data:"+ipaddre);
				System.out.println("ip:"+addressInfo[0].trim()+",port:"+addressInfo[1].trim());
			}
			
			URL url1 = new URL(url);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress((String)ipInfo.get("ip"),(int)ipInfo.get("port")));
			HttpURLConnection uc = (HttpURLConnection)url1.openConnection(proxy);
			uc.setRequestProperty("User-Agent", UserAgents[(int)(Math.random()*UserAgents.length)]);
			uc.setRequestProperty("Referer", Referer[(int)(Math.random()*Referer.length)]);
			uc.setConnectTimeout(3000);
			uc.setReadTimeout(3000);
			uc.connect();
			if(uc.getResponseCode()!=HttpURLConnection.HTTP_OK) {
				isIpEnable = false;
				return null;
			}
			in = uc.getInputStream();
			br = new BufferedReader(new InputStreamReader(in));
			StringBuffer sb = new StringBuffer();
			String str = null;
			while((str=br.readLine())!=null) {
				sb.append(str);
			}
			System.out.println(sb.toString().substring(0,20));
			return sb.toString();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(br!=null)br.close();
				if(in!=null)in.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
