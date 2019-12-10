package com.example.wantlifu.service;


import com.alipay.api.AlipayApiException;

/**
 * @author 王志坚
 * @createTime 2019.12.09.15:41
 */
public interface IAliPayService {
    String genPayPic();

    void updateById(String id);

    String genPage();

    String refund(String outTradeNo) throws AlipayApiException;
}
