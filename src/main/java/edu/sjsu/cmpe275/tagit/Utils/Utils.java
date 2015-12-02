package edu.sjsu.cmpe275.tagit.Utils;


import org.joda.time.DateTime;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String passwordEncrypter(String password)
    {
        String generatedDigest = null;

        try{
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());

            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            generatedDigest = sb.toString();
        }catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return generatedDigest;
    }

    public static Integer sessionTokenGenerator()
    {
        DateTime dt = new DateTime();
        Integer year = dt.getYear();
        Integer month = dt.getMonthOfYear();
        Integer day = dt.getDayOfYear();
        Integer hour = dt.getHourOfDay();
        Integer milli = dt.getMillisOfDay();
        Integer id = 999 + year + month + day + hour + milli;
        return id;
    }
}
