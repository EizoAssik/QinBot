package com.sssta.qinbot.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * A 'pure' Java fork of the pass.js
 */
public class Cyrpt {

    // This is the `hexcase` in pass.js
    static final boolean HEX_UPPERCASE = true;
    static final String B64PAD = "";
    // This is the `chrsz` in pass.js
    static final int CHARSIZE = 8;
    static final int HASHMOD = 32;
    static ScriptEngineManager m = new ScriptEngineManager();
	static ScriptEngine jsEngine ;

    /**
     * Just keep the interface
     *
     * @param password password
     * @param uin      uin
     * @param vcode    vcode
     * @return Hashed String
     */
    static public String getEncryption(String password, String uin, String vcode) {
        password = null == password ? "" : password;
        uin = null == uin ? "" : uin;
        vcode = null == vcode ? "" : vcode;
//        String pw_md5 = hexchar2bin(md5(password));
//        String pu_md5 = md5(pw_md5 + uin);
//        String hashed = md5(pu_md5 + vcode.toUpperCase());
//        return hashed;
        String p = null;
		

		try {
			if (jsEngine == null) {
				jsEngine = m.getEngineByName("javascript");
				jsEngine.eval(new FileReader(
						new File("src/com/sssta/qinbot/util/pass.js")));
			}
			
			Object t = jsEngine.eval("getEncryption(\"" + password + "\",\"" + uin
					+ "\",\"" + vcode + "\");");
			p = t.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return p;
        
    }
    
    public static String getHash(String ptwebqq,String uin){
    	ptwebqq = null == ptwebqq?"":ptwebqq;
    	uin = null == uin?"":uin;
    	String p = "";
		try {
			if (jsEngine == null) {
				jsEngine = m.getEngineByName("javascript");
				jsEngine.eval(new FileReader(
						new File("src/com/sssta/qinbot/util/pass.js")));
			}
			
			Object t = jsEngine.eval("hash_get(\"" + uin + "\",\"" + ptwebqq + "\");");
			p = t.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return p;
    }

    static public String md5(String s) {
        if (s == null)
            return hex_md5("");
        else
            return hex_md5(s);
    }

    static String hex_md5(String s) {
        return binl2hex(core_md5(str2binl(s), s.length() * CHARSIZE));
    }

    /**
     * This function maps the bits in str into a int[n],
     * which will be seen as a 32*n bits chunk by md5_core.
     *
     * @param str the original string
     * @return a int[n] as 32*n bits.
     */
    static int[] str2binl(String str) {
        int[] bin = new int[(str.length() * CHARSIZE >> 5) + 1];
        int mask = (1 << CHARSIZE) - 1;
        for (int i = 0; i < str.length() * CHARSIZE; i += CHARSIZE) {
            bin[i >> 5] |= (str.charAt(i / CHARSIZE) & mask) << (i % 32);
        }
        return bin;
    }

    /**
     * Core is core.
     * The implementation of this function in Java is a little bite different from pass.js
     * This is not a standard MD5 algorithm, Tencent had written their onw.
     *
     * @param x   the bits mapped into a int[]
     * @param len the length of bits in the `str` given to md5()
     * @return int[4] as a 32x4=128 bits chunk.
     */
    static int[] core_md5(int[] x, int len) {
//      append a '1' to x
        x[len >> 5] |= 128 << ((len) % 32);
//      pad with '0' bits
//      as MD5 will make bit-length ≡ 448 (mod 512)
//      it should be x.length ≡ 14 (mod 16)
//      but here needs x.length >= len and x.length ≡ 0 (mod 16)
        x = fit_with_zero(x);
//      append the len to x
        x[(((len + 64) >>> 9) << 4) + 14] = len;
//      and...yes, this is NOT a standard initialization
        int a = 1732584193;    // 0x67452301, same as standard
        int b = -271733879;    // 0x89ABCDEF
        int c = -1732584194;   // 0xFEDCBA98
        int d = 271733878;     // 0x8AABCDEF
        for (int i = 0; i < x.length; i += 16) {
            int olda = a;
            int oldb = b;
            int oldc = c;
            int oldd = d;
            a = md5_ff(a, b, c, d, x[i + 0], 7, -680876936);
            d = md5_ff(d, a, b, c, x[i + 1], 12, -389564586);
            c = md5_ff(c, d, a, b, x[i + 2], 17, 606105819);
            b = md5_ff(b, c, d, a, x[i + 3], 22, -1044525330);
            a = md5_ff(a, b, c, d, x[i + 4], 7, -176418897);
            d = md5_ff(d, a, b, c, x[i + 5], 12, 1200080426);
            c = md5_ff(c, d, a, b, x[i + 6], 17, -1473231341);
            b = md5_ff(b, c, d, a, x[i + 7], 22, -45705983);
            a = md5_ff(a, b, c, d, x[i + 8], 7, 1770035416);
            d = md5_ff(d, a, b, c, x[i + 9], 12, -1958414417);
            c = md5_ff(c, d, a, b, x[i + 10], 17, -42063);
            b = md5_ff(b, c, d, a, x[i + 11], 22, -1990404162);
            a = md5_ff(a, b, c, d, x[i + 12], 7, 1804603682);
            d = md5_ff(d, a, b, c, x[i + 13], 12, -40341101);
            c = md5_ff(c, d, a, b, x[i + 14], 17, -1502002290);
            b = md5_ff(b, c, d, a, x[i + 15], 22, 1236535329);
            a = md5_gg(a, b, c, d, x[i + 1], 5, -165796510);
            d = md5_gg(d, a, b, c, x[i + 6], 9, -1069501632);
            c = md5_gg(c, d, a, b, x[i + 11], 14, 643717713);
            b = md5_gg(b, c, d, a, x[i + 0], 20, -373897302);
            a = md5_gg(a, b, c, d, x[i + 5], 5, -701558691);
            d = md5_gg(d, a, b, c, x[i + 10], 9, 38016083);
            c = md5_gg(c, d, a, b, x[i + 15], 14, -660478335);
            b = md5_gg(b, c, d, a, x[i + 4], 20, -405537848);
            a = md5_gg(a, b, c, d, x[i + 9], 5, 568446438);
            d = md5_gg(d, a, b, c, x[i + 14], 9, -1019803690);
            c = md5_gg(c, d, a, b, x[i + 3], 14, -187363961);
            b = md5_gg(b, c, d, a, x[i + 8], 20, 1163531501);
            a = md5_gg(a, b, c, d, x[i + 13], 5, -1444681467);
            d = md5_gg(d, a, b, c, x[i + 2], 9, -51403784);
            c = md5_gg(c, d, a, b, x[i + 7], 14, 1735328473);
            b = md5_gg(b, c, d, a, x[i + 12], 20, -1926607734);
            a = md5_hh(a, b, c, d, x[i + 5], 4, -378558);
            d = md5_hh(d, a, b, c, x[i + 8], 11, -2022574463);
            c = md5_hh(c, d, a, b, x[i + 11], 16, 1839030562);
            b = md5_hh(b, c, d, a, x[i + 14], 23, -35309556);
            a = md5_hh(a, b, c, d, x[i + 1], 4, -1530992060);
            d = md5_hh(d, a, b, c, x[i + 4], 11, 1272893353);
            c = md5_hh(c, d, a, b, x[i + 7], 16, -155497632);
            b = md5_hh(b, c, d, a, x[i + 10], 23, -1094730640);
            a = md5_hh(a, b, c, d, x[i + 13], 4, 681279174);
            d = md5_hh(d, a, b, c, x[i + 0], 11, -358537222);
            c = md5_hh(c, d, a, b, x[i + 3], 16, -722521979);
            b = md5_hh(b, c, d, a, x[i + 6], 23, 76029189);
            a = md5_hh(a, b, c, d, x[i + 9], 4, -640364487);
            d = md5_hh(d, a, b, c, x[i + 12], 11, -421815835);
            c = md5_hh(c, d, a, b, x[i + 15], 16, 530742520);
            b = md5_hh(b, c, d, a, x[i + 2], 23, -995338651);
            a = md5_ii(a, b, c, d, x[i + 0], 6, -198630844);
            d = md5_ii(d, a, b, c, x[i + 7], 10, 1126891415);
            c = md5_ii(c, d, a, b, x[i + 14], 15, -1416354905);
            b = md5_ii(b, c, d, a, x[i + 5], 21, -57434055);
            a = md5_ii(a, b, c, d, x[i + 12], 6, 1700485571);
            d = md5_ii(d, a, b, c, x[i + 3], 10, -1894986606);
            c = md5_ii(c, d, a, b, x[i + 10], 15, -1051523);
            b = md5_ii(b, c, d, a, x[i + 1], 21, -2054922799);
            a = md5_ii(a, b, c, d, x[i + 8], 6, 1873313359);
            d = md5_ii(d, a, b, c, x[i + 15], 10, -30611744);
            c = md5_ii(c, d, a, b, x[i + 6], 15, -1560198380);
            b = md5_ii(b, c, d, a, x[i + 13], 21, 1309151649);
            a = md5_ii(a, b, c, d, x[i + 4], 6, -145523070);
            d = md5_ii(d, a, b, c, x[i + 11], 10, -1120210379);
            c = md5_ii(c, d, a, b, x[i + 2], 15, 718787259);
            b = md5_ii(b, c, d, a, x[i + 9], 21, -343485551);
            a = safe_add(a, olda);
            b = safe_add(b, oldb);
            c = safe_add(c, oldc);
            d = safe_add(d, oldd);
        }
        if (HASHMOD == 16) {
            return new int[]{b, c};
        } else {
            return new int[]{a, b, c, d};
        }
    }

    static int md5_cmn(int q, int a, int b, int x, int s, int t) {
        return safe_add(bit_rol(safe_add(safe_add(a, q), safe_add(x, t)), s), b);
    }

    static int md5_ff(int a, int b, int c, int d, int x, int s, int t) {
        return md5_cmn((b & c) | ((~b) & d), a, b, x, s, t);
    }

    static int md5_gg(int a, int b, int c, int d, int x, int s, int t) {
        return md5_cmn((b & d) | (c & (~d)), a, b, x, s, t);
    }

    static int md5_hh(int a, int b, int c, int d, int x, int s, int t) {
        return md5_cmn(b ^ c ^ d, a, b, x, s, t);
    }

    static int md5_ii(int a, int b, int c, int d, int x, int s, int t) {
        return md5_cmn(c ^ (b | (~d)), a, b, x, s, t);
    }

    static int safe_add(int x, int y) {
        int lsw = (x & 65535) + (y & 65535);
        int msw = (x >> 16) + (y >> 16) + (lsw >> 16);
        return (msw << 16) | (lsw & 65535);
    }

    static int bit_rol(int num, int cnt) {
        return (num << cnt) | (num >>> (32 - cnt));
    }

    static String binl2hex(int[] binarray) {
        String hex_tab = HEX_UPPERCASE ? "0123456789ABCDEF" : "0123456789abcdef";
        String str = "";
        for (int i = 0; i < binarray.length * 4; i++) {
            str += hex_tab.charAt((binarray[i >> 2] >> ((i % 4) * 8 + 4)) & 15);
            str += hex_tab.charAt((binarray[i >> 2] >> ((i % 4) * 8)) & 15);
        }
        return str;
    }

    static int[] fit_with_zero(int[] array) {
        int new_len = array.length;
        while (new_len % 16 != 0)
            new_len += 1;
        int[] new_array = new int[new_len];
        System.arraycopy(array, 0, new_array, 0, array.length);
        return new_array;
    }
    static String hexchar2bin(String str) {
        String retval="";
        for (int i = 0; i < str.length(); i += 2) {
            retval += "\\x" + str.substring(i, i+2);
        }
        return retval;
    }
}
