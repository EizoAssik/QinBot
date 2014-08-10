package com.sssta.qinbot.test;

import com.sssta.qinbot.util.Cyrpt;
import junit.framework.TestCase;

import java.util.Random;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class UTCyrpt extends TestCase {
    static Random random;
    static final String EMPTY = "";
    static final String B64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public UTCyrpt() {
        random = new Random();
    }

    static String jsEncryption(String psw, String uni, String vcode) {
        psw = null == psw ? "" : psw;
        uni = null == uni ? "" : uni;
        vcode = null == vcode ? "" : vcode;
        String p = null;
        ScriptEngineManager m = new ScriptEngineManager();
        ScriptEngine se = m.getEngineByName("javascript");

        try {
            se.eval(new FileReader(
                    new File("src/com/sssta/qinbot/util/pass.js")));
            Object t = se.eval("getEncryption(\"" + psw + "\",\"" + uni
                    + "\",\"" + vcode + "\");");
            p = t.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return p;
//        return FunnyHash.getPswHash(psw, uni, vcode);
    }

    static String jsMD5(String s) {
        if (s == null)
            s = "";
        String p = null;
        ScriptEngineManager m = new ScriptEngineManager();
        ScriptEngine se = m.getEngineByName("javascript");
        try {
            se.eval(new FileReader(
                    new File("src/com/sssta/qinbot/util/pass.js")));
            Object t = se.eval("md5(\"" + s + "\");");
            p = t.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return p;
    }

    static String jsC2H(String hex) {
        String p = null;
        ScriptEngineManager m = new ScriptEngineManager();
        ScriptEngine se = m.getEngineByName("javascript");
        try {
            se.eval(new FileReader(
                    new File("src/com/sssta/qinbot/util/pass.js")));
            Object t = se.eval("hexchar2bin(\"" + hex + "\");");
            p = t.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return p;
    }

    static String nextRandomString(int len) {
//        int len = random.nextInt(max_len);
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = B64.charAt(random.nextInt(64));
        }
        return String.valueOf(chars);
    }

    static String nextRandomUin() {
        long value = random.nextLong();
        String retval="";
        long mask = 0xFF;
        for (int i = 0; i < 8; i++) {
            retval = String.format("\\x%02x", ((mask & value) >> (8 * i))).concat(retval);
            mask <<= 8;
        }
        return retval;
    }

    public void testC2H() {

        for (int i = 0; i < 10; i++) {
            String rs = nextRandomString(1024);
            String md = jsMD5(rs);
            String msg = "RandomString@" + md;
            assertEquals(msg, jsC2H(md), Cyrpt.hexchar2bin(md));
        }
    }

    public void testMD5() {
        assertEquals("Empty", jsMD5(EMPTY), Cyrpt.md5(EMPTY));
        assertEquals("null", jsMD5(null), Cyrpt.md5(null));
        assertEquals("Base64", jsMD5(B64), Cyrpt.md5(B64));
        for (int i = 0; i < 10; i++) {
            String rs = nextRandomString(1024);
            String msg = "RandomString@" + rs;
            assertEquals(msg, jsMD5(rs), Cyrpt.md5(rs));
        }
    }

    public void testEncryption() {
//        assertEquals("Empty", jsEncryption(EMPTY, EMPTY, EMPTY), Cyrpt.getEncryption(EMPTY, EMPTY, EMPTY));
//        assertEquals("null", jsEncryption(null, null, null), Cyrpt.getEncryption(null, null, null));
//        assertEquals("B64", jsEncryption(B64, B64, B64), Cyrpt.getEncryption(B64, B64, B64));
        for (int i = 0; i < 10; i++) {
            String pw = nextRandomString(32);
            String uin = nextRandomUin();
            String v = nextRandomString(4);
            String msg = String.format("RandomString@%s-%s-%s", pw, uin, v);
            assertEquals(msg, jsEncryption(pw, uin, v), Cyrpt.getEncryption(pw, uin, v));
        }
    }
}
