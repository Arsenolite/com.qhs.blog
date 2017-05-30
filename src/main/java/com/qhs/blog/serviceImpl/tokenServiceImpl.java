package com.qhs.blog.serviceImpl;


import com.qhs.blog.service.tokenService;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;


/**
 * 生成和验证JWT的核心类
 */
@Service
public class tokenServiceImpl implements tokenService {
    //指定JWT的密钥
    private static final byte[] secret = "CC39FB36D18C89C39B2F7A7991E46CD1".getBytes();
    //指定JWT的头部分
    private static final JWSHeader header = new JWSHeader(JWSAlgorithm.HS256, JOSEObjectType.JWT, null, null, null, null, null, null, null, null, null, null, null);

    //    @Autowired
//    private redisDao redisDao;
//      将token存储进redis不在这层做。
//      Map中存储用户ID，Token生成时间，过期时间等
    @Override
    public String createToken(Map<String, Object> payload) {
        String token = null;
        //将载荷加上之前指定的头部编码为jwsObject
        JWSObject jwsObject = new JWSObject(header, new Payload(new JSONObject(payload)));
        try {
            //将编码进行签名
            jwsObject.sign(new MACSigner(secret));
            token = jwsObject.serialize();
        } catch (JOSEException e) {
            System.err.println("签名失败，" + e.getMessage());
            e.printStackTrace();
        }
        return token;
    }

    @Override
    public Map<String, Object> validToken(String token) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            //将字符串解析成JWSObject
            JWSObject jwsObject = JWSObject.parse(token);
            //解析出载荷
            Payload payload = jwsObject.getPayload();
            //检验签名
            JWSVerifier verifier = new MACVerifier(secret);
            //验证密钥通过
            if (jwsObject.verify(verifier)) {
                //拿到Token中的信息
                JSONObject jsonOBj = payload.toJSONObject();
                // token校验成功（此时没有校验是否过期）
                resultMap.put("state", "VALID");
                // 若payload包含ext字段，则校验是否过期
                if (jsonOBj.containsKey("ext")) {
                    long extTime = Long.valueOf(jsonOBj.get("ext").toString());
                    long curTime = new Date().getTime();
                    // 过期了
                    if (curTime > extTime) {
                        resultMap.clear();
                        resultMap.put("state", "EXPIRED");
                    }
                }
                resultMap.put("data", jsonOBj);

            } else {
                // 校验失败
                resultMap.put("state", "INVALID");
            }


        } catch (Exception e) {
            //e.printStackTrace();
            resultMap.clear();
            resultMap.put("state", "INVALID");
        }

        return resultMap;
    }
}
