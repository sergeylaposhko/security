package com.my;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

/**
 * Created by Sergey on 09.05.2015.
 */
@Controller
@RequestMapping
public class MController {

    private static final Logger logger = Logger.getLogger(MController.class);

    @RequestMapping(value = "/des", produces = {"application/json"})
    public
    @ResponseBody
    ResultBean test(@RequestParam(value = "password") String password,
                    @RequestParam(value = "text") String text) throws JsonProcessingException {
        logger.debug("Start handling request with text: " + text);
        logger.debug("Start handling request with password: " + password);

        DES des = new DES();

        ResultBean resultBean = new ResultBean();

        String encrypt = des.encrypt(password, DES.utfToBin(text));

        long[] encKeys = new long[16];
        System.arraycopy(des.getK(), 1, encKeys, 0, 16);
        resultBean.setEncryptKeys(encKeys);


        resultBean.setKeyHash(DES.hash(password));

        String decrypt = des.decrypt(password, encrypt);

        long[] decrKeys = new long[16];
        System.arraycopy(des.getK(), 1, decrKeys, 0, 16);
        resultBean.setDecryptKeys(decrKeys);

        fillWithStringKeys(resultBean);

        decrypt = DES.binToUTF(decrypt);

        resultBean.setDecrypted(decrypt);
        resultBean.setEncrypted(encrypt);

        return resultBean;
    }

    private void fillWithStringKeys(ResultBean resultBean) {
        long[] decryptKeys = resultBean.getDecryptKeys();
        String[] decryptKeysString = new String[16];
        for (int i = 0; i < decryptKeys.length; i++) {
            long decryptKey = decryptKeys[i];
            decryptKeysString[i] = Long.toString(decryptKey, 2);
        }
        resultBean.setDecryptKeysString(decryptKeysString);

        long[] encryptKeys = resultBean.getEncryptKeys();
        String[] encryptKeysString = new String[16];
        for (int i = 0; i < encryptKeys.length; i++) {
            long encryptKey = encryptKeys[i];
            encryptKeysString[i] = Long.toString(encryptKey, 2);
        }
        resultBean.setEncryptKeysStrings(encryptKeysString);
    }


    public static void main(String[] args) throws JsonProcessingException {
        MController mController = new MController();

        ResultBean result = mController.test("somePassasd", "someText");

        System.out.println("Encrypted:" + DES.binToHex(result.getEncrypted()));
        System.out.println("Decrypted:" + result.getDecrypted());
        System.out.println("Keys EK:" + Arrays.toString(result.getEncryptKeys()));
        System.out.println("Keys DK:" + Arrays.toString(result.getDecryptKeys()));
        System.out.println("Keys DKS:" + Arrays.toString(result.getDecryptKeysString()));
        System.out.println("Key hash:" + (result.getKeyHash()));
    }

}