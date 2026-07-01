package cn.master.northrend.dto;

/**
 * @author : 11's papa
 * @since : 2026/6/30, 星期二
 **/
public record AuthResponse(String accessToken,String username,long exp) {
}
