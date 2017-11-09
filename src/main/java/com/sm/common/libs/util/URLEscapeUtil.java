/**
 * 
 */
package com.sm.common.libs.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.BitSet;

/**
 * URLEscapeUtil
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2017年9月6日 下午3:42:52
 */
public abstract class URLEscapeUtil {

  // ==========================================================================
  // URL/URI encoding/decoding。
  // 根据RFC2396：http://www.ietf.org/rfc/rfc2396.txt
  // ==========================================================================

  /** "Alpha" characters from RFC 2396. */
  private static final BitSet ALPHA = new BitSet(256);

  static {
    for (int i = 'a'; i <= 'z'; i++) {
      ALPHA.set(i);
    }

    for (int i = 'A'; i <= 'Z'; i++) {
      ALPHA.set(i);
    }
  }

  /** "Alphanum" characters from RFC 2396. */
  private static final BitSet ALPHANUM = new BitSet(256);

  static {
    ALPHANUM.or(ALPHA);

    for (int i = '0'; i <= '9'; i++) {
      ALPHANUM.set(i);
    }
  }

  /** "Mark" characters from RFC 2396. */
  private static final BitSet MARK = new BitSet(256);

  static {
    MARK.set('-');
    MARK.set('_');
    MARK.set('.');
    MARK.set('!');
    MARK.set('~');
    MARK.set('*');
    MARK.set('\'');
    MARK.set('(');
    MARK.set(')');
  }

  /** "Reserved" characters from RFC 2396. */
  private static final BitSet RESERVED = new BitSet(256);

  static {
    RESERVED.set(';');
    RESERVED.set('/');
    RESERVED.set('?');
    RESERVED.set(':');
    RESERVED.set('@');
    RESERVED.set('&');
    RESERVED.set('=');
    RESERVED.set('+');
    RESERVED.set('$');
    RESERVED.set(',');
  }

  /** "Unreserved" characters from RFC 2396. */
  private static final BitSet UNRESERVED = new BitSet(256);

  static {
    UNRESERVED.or(ALPHANUM);
    UNRESERVED.or(MARK);
  }

  /** 将一个数字转换成16进制的转换表。 */
  private static char[] HEXADECIMAL = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

  /**
   * 将指定字符串编码成<code>application/x-www-form-urlencoded</code>格式。
   * <p>
   * 除了RFC2396中的<code>unreserved</code>字符之外的所有字符，都将被转换成URL编码<code>%xx</code>。
   * 根据RFC2396，<code>unreserved</code>的定义如下：
   * <p/>
   * 
   * <pre>
   * &lt;![CDATA
   *  unreserved  = alphanum | mark
   *  alphanum    = 大小写英文字母 | 数字
   *  mark        = &quot;-&quot; | &quot;_&quot; | &quot;.&quot; | &quot;!&quot; | &quot;&tilde;&quot; | &quot;*&quot; | &quot;'&quot; | &quot;(&quot; | &quot;)&quot;
   * ]]&gt;
   * </pre>
   * <p/>
   * </p>
   * <p>
   * 警告：该方法使用当前线程默认的字符编码来编码URL，因此该方法在不同的上下文中可能会产生不同的结果。
   * </p>
   * 
   * @param str 要编码的字符串，可以是<code>null</code>
   * @return URL编码后的字符串
   */
  public static String escapeURL(String str) {
    try {
      return escapeURLInternal(str, null, true);
    } catch (UnsupportedEncodingException e) {
      return str; // 不可能发生这个异常
    }
  }

  /**
   * 将指定字符串编码成<code>application/x-www-form-urlencoded</code>格式。
   * <p>
   * 除了RFC2396中的<code>unreserved</code>字符之外的所有字符，都将被转换成URL编码<code>%xx</code>。
   * 根据RFC2396，<code>unreserved</code>的定义如下：
   * <p/>
   * 
   * <pre>
   * &lt;![CDATA
   *  unreserved  = alphanum | mark
   *  alphanum    = 大小写英文字母 | 数字
   *  mark        = &quot;-&quot; | &quot;_&quot; | &quot;.&quot; | &quot;!&quot; | &quot;&tilde;&quot; | &quot;*&quot; | &quot;'&quot; | &quot;(&quot; | &quot;)&quot;
   * ]]&gt;
   * </pre>
   * <p/>
   * </p>
   * <p>
   * 该方法使用指定的字符编码来编码URL。
   * </p>
   * 
   * @param str 要编码的字符串，可以是<code>null</code>
   * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
   * @return URL编码后的字符串
   * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
   */
  public static String escapeURL(String str, String encoding) throws UnsupportedEncodingException {
    return escapeURLInternal(str, encoding, true);
  }

  /**
   * 将指定字符串编码成<code>application/x-www-form-urlencoded</code>格式。
   * <p>
   * 如果指定参数<code>strict</code>为<code>true</code>，则按严格的方式编码URL。 除了RFC2396中的 <code>unreserved</code>
   * 字符之外的所有字符，都将被转换成URL编码<code>%xx</code>。 根据RFC2396， <code>unreserved</code>的定义如下：
   * <p/>
   * 
   * <pre>
   * &lt;![CDATA
   *  unreserved  = alphanum | mark
   *  alphanum    = 大小写英文字母 | 数字
   *  mark        = &quot;-&quot; | &quot;_&quot; | &quot;.&quot; | &quot;!&quot; | &quot;&tilde;&quot; | &quot;*&quot; | &quot;'&quot; | &quot;(&quot; | &quot;)&quot;
   * ]]&gt;
   * </pre>
   * <p/>
   * </p>
   * <p>
   * 如果指定参数<code>strict</code>为<code>false</code>，则使用宽松的方式编码URL。
   * 除了控制字符、空白字符以及RFC2396中的<code>reserved</code> 字符之外的所有字符，都将被保留不变。
   * 根据RFC2396，只有控制字符、空白字符以及符合下列<code>reserved</code>定义的字符才被转换成 <code>%xx</code>格式：
   * <p/>
   * 
   * <pre>
   * &lt;![CDATA
   *  reserved      = &quot;;&quot; | &quot;/&quot; | &quot;?&quot; | &quot;:&quot; | &quot;@&quot; | &quot;&amp;&quot; | &quot;=&quot; | &quot;+&quot; | &quot;$&quot; | &quot;,&quot;
   * ]]&gt;
   * </pre>
   * <p/>
   * </p>
   * <p>
   * 该方法使用指定的字符编码来编码URL。
   * </p>
   * 
   * @param str 要编码的字符串，可以是<code>null</code>
   * @param encoding 输出字符编码，如果为<code>null</code>，则使用当前线程默认的编码
   * @param strict 是否以严格的方式编码URL
   * @return URL编码后的字符串
   * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
   */
  public static String escapeURL(String str, String encoding, boolean strict) throws UnsupportedEncodingException {
    return escapeURLInternal(str, encoding, strict);
  }

  /**
   * 将指定字符串编码成<code>application/x-www-form-urlencoded</code>格式。
   * <p>
   * 除了RFC2396中的<code>unreserved</code>字符之外的所有字符，都将被转换成URL编码<code>%xx</code>。
   * 根据RFC2396，<code>unreserved</code>的定义如下：
   * <p/>
   * 
   * <pre>
   * &lt;![CDATA
   *  unreserved  = alphanum | mark
   *  alphanum    = 大小写英文字母 | 数字
   *  mark        = &quot;-&quot; | &quot;_&quot; | &quot;.&quot; | &quot;!&quot; | &quot;&tilde;&quot; | &quot;*&quot; | &quot;'&quot; | &quot;(&quot; | &quot;)&quot;
   * ]]&gt;
   * </pre>
   * <p/>
   * </p>
   * <p>
   * 该方法使用指定的字符编码来编码URL。
   * </p>
   * 
   * @param str 要编码的字符串，可以是<code>null</code>
   * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
   * @param out 输出到指定字符流
   * @throws IOException 如果输出到<code>out</code>失败
   * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
   * @throws IllegalArgumentException <code>out</code>为<code>null</code>
   */
  public static void escapeURL(String str, String encoding, Appendable out) throws IOException {
    escapeURLInternal(str, encoding, out, true);
  }

  /**
   * 将指定字符串编码成<code>application/x-www-form-urlencoded</code>格式。
   * <p>
   * 如果指定参数<code>strict</code>为<code>true</code>，则按严格的方式编码URL。 除了RFC2396中的 <code>unreserved</code>
   * 字符之外的所有字符，都将被转换成URL编码<code>%xx</code>。 根据RFC2396， <code>unreserved</code>的定义如下：
   * <p/>
   * 
   * <pre>
   * &lt;![CDATA
   *  unreserved  = alphanum | mark
   *  alphanum    = 大小写英文字母 | 数字
   *  mark        = &quot;-&quot; | &quot;_&quot; | &quot;.&quot; | &quot;!&quot; | &quot;&tilde;&quot; | &quot;*&quot; | &quot;'&quot; | &quot;(&quot; | &quot;)&quot;
   * ]]&gt;
   * </pre>
   * <p/>
   * </p>
   * <p>
   * 如果指定参数<code>strict</code>为<code>false</code>，则使用宽松的方式编码URL。
   * 除了控制字符、空白字符以及RFC2396中的<code>reserved</code> 字符之外的所有字符，都将被保留不变。
   * 根据RFC2396，只有控制字符、空白字符以及符合下列<code>reserved</code>定义的字符才被转换成 <code>%xx</code>格式：
   * <p/>
   * 
   * <pre>
   * &lt;![CDATA
   *  reserved      = &quot;;&quot; | &quot;/&quot; | &quot;?&quot; | &quot;:&quot; | &quot;@&quot; | &quot;&amp;&quot; | &quot;=&quot; | &quot;+&quot; | &quot;$&quot; | &quot;,&quot;
   * ]]&gt;
   * </pre>
   * <p/>
   * </p>
   * <p>
   * 该方法使用指定的字符编码来编码URL。
   * </p>
   * 
   * @param str 要编码的字符串，可以是<code>null</code>
   * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
   * @param out 输出到指定字符流
   * @param strict 是否以严格的方式编码URL
   * @throws IOException 如果输出到<code>out</code>失败
   * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
   * @throws IllegalArgumentException <code>out</code>为<code>null</code>
   */
  public static void escapeURL(String str, String encoding, Appendable out, boolean strict) throws IOException {
    escapeURLInternal(str, encoding, out, strict);
  }

  /**
   * 将指定字符串编码成<code>application/x-www-form-urlencoded</code>格式。
   * 
   * @param str 要编码的字符串，可以是<code>null</code>
   * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
   * @param strict 是否以严格的方式编码URL
   * @return URL编码后的字符串
   * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
   */
  private static String escapeURLInternal(String str, String encoding, boolean strict)
      throws UnsupportedEncodingException {
    if (str == null) {
      return null;
    }

    try {
      StringBuilder out = new StringBuilder(64);

      if (escapeURLInternal(str, encoding, out, strict)) {
        return out.toString();
      }

      return str;
    } catch (UnsupportedEncodingException e) {
      throw e;
    } catch (IOException e) {
      return str; // StringBuilder不可能发生这个异常
    }
  }

  /**
   * 将指定字符串编码成<code>application/x-www-form-urlencoded</code>格式。
   * 
   * @param str 要编码的字符串，可以是<code>null</code>
   * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
   * @param strict 是否以严格的方式编码URL
   * @param out 输出流
   * @return 如果字符串被改变，则返回<code>true</code>
   * @throws IOException 如果输出到<code>out</code>失败
   * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
   * @throws IllegalArgumentException <code>out</code>为<code>null</code>
   */
  private static boolean escapeURLInternal(String str, String encoding, Appendable out, boolean strict)
      throws IOException {
    if (encoding == null) {
      encoding = LocaleUtil.getContext().getCharset().name();
    }

    boolean needToChange = false;

    if (out == null) {
      throw new IllegalArgumentException("The Appendable must not be null");
    }

    if (str == null) {
      return needToChange;
    }

    char[] charArray = str.toCharArray();
    int length = charArray.length;

    for (int i = 0; i < length; i++) {
      int ch = charArray[i];

      if (isSafeCharacter(ch, strict)) {
        // “安全”的字符，直接输出
        out.append((char) ch);
      } else if (ch == ' ') {
        // 特殊情况：空格（0x20）转换成'+'
        out.append('+');

        // 设置改变标志
        needToChange = true;
      } else {
        // 对ch进行URL编码。
        // 首先按指定encoding取得该字符的字节码。
        byte[] bytes = String.valueOf((char) ch).getBytes(encoding);

        for (byte toEscape : bytes) {
          out.append('%');

          int low = toEscape & 0x0F;
          int high = (toEscape & 0xF0) >> 4;

          out.append(HEXADECIMAL[high]);
          out.append(HEXADECIMAL[low]);
        }

        // 设置改变标志
        needToChange = true;
      }
    }

    return needToChange;
  }

  /**
   * 判断指定字符是否是“安全”的，这个字符将不被转换成URL编码。
   * 
   * @param ch 要判断的字符
   * @param strict 是否以严格的方式编码
   * @return 如果是“安全”的，则返回<code>true</code>
   */
  private static boolean isSafeCharacter(int ch, boolean strict) {
    if (strict) {
      return UNRESERVED.get(ch);
    }
    return ch > ' ' && !RESERVED.get(ch) && !Character.isWhitespace((char) ch);
  }

  /**
   * 解码<code>application/x-www-form-urlencoded</code>格式的字符串。
   * <p>
   * 警告：该方法使用系统字符编码来解码URL，因此该方法在不同的系统中可能会产生不同的结果。
   * </p>
   * 
   * @param str 要解码的字符串，可以是<code>null</code>
   * @return URL解码后的字符串
   */
  public static String unescapeURL(String str) {
    try {
      return unescapeURLInternal(str, null);
    } catch (UnsupportedEncodingException e) {
      return str; // 不可能发生这个异常
    }
  }

  /**
   * 解码<code>application/x-www-form-urlencoded</code>格式的字符串。
   * 
   * @param str 要解码的字符串，可以是<code>null</code>
   * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
   * @return URL解码后的字符串
   * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
   */
  public static String unescapeURL(String str, String encoding) throws UnsupportedEncodingException {
    return unescapeURLInternal(str, encoding);
  }

  /**
   * 解码<code>application/x-www-form-urlencoded</code>格式的字符串。
   * 
   * @param str 要解码的字符串，可以是<code>null</code>
   * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
   * @param out 输出流
   * @throws IOException 如果输出到<code>out</code>失败
   * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
   * @throws IllegalArgumentException <code>out</code>为<code>null</code>
   */
  public static void unescapeURL(String str, String encoding, Appendable out) throws IOException {
    unescapeURLInternal(str, encoding, out);
  }

  /**
   * 解码<code>application/x-www-form-urlencoded</code>格式的字符串。
   * 
   * @param str 要解码的字符串，可以是<code>null</code>
   * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
   * @return URL解码后的字符串
   * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
   */
  private static String unescapeURLInternal(String str, String encoding) throws UnsupportedEncodingException {
    if (str == null) {
      return null;
    }

    try {
      StringBuilder out = new StringBuilder(str.length());

      if (unescapeURLInternal(str, encoding, out)) {
        return out.toString();
      }

      return str;
    } catch (UnsupportedEncodingException e) {
      throw e;
    } catch (IOException e) {
      return str; // StringBuilder不可能发生这个异常
    }
  }

  /**
   * 解码<code>application/x-www-form-urlencoded</code>格式的字符串。FIXME，此方法恶心
   * 
   * @param str 要解码的字符串，可以是<code>null</code>
   * @param encoding 输出字符编码，如果为<code>null</code>，则使用系统默认编码
   * @param out 输出流
   * @return 如果字符串被改变，则返回<code>true</code>
   * @throws IOException 如果输出到<code>out</code>失败
   * @throws UnsupportedEncodingException 如果指定的<code>encoding</code>为非法的
   * @throws IllegalArgumentException <code>out</code>为<code>null</code>
   */
  private static boolean unescapeURLInternal(String str, String encoding, Appendable out) throws IOException {
    if (encoding == null) {
      encoding = LocaleUtil.getContext().getCharset().name();
    }

    boolean needToChange = false;

    if (out == null) {
      throw new IllegalArgumentException("The Appendable must not be null");
    }

    byte[] buffer = null;
    int pos = 0;
    int startIndex = 0;

    char[] charArray = str.toCharArray();
    int length = charArray.length;

    for (int i = 0; i < length; i++) {
      int ch = charArray[i];

      if (ch < 256) {
        // 读取连续的字节，并将它按指定编码转换成字符。
        if (buffer == null) {
          buffer = new byte[length - i]; // 最长只需要length - i
        }

        if (pos == 0) {
          startIndex = i;
        }

        switch (ch) {
          case '+':

            // 将'+'转换成' '
            buffer[pos++] = ' ';

            // 设置改变标志
            needToChange = true;
            break;

          case '%':

            if (i + 2 < length) {
              try {
                byte b = (byte) Integer.parseInt(str.substring(i + 1, i + 3), 16);

                buffer[pos++] = b;
                i += 2;

                // 设置改变标志
                needToChange = true;
              } catch (NumberFormatException e) {
                // 如果%xx不是合法的16进制数，则原样输出
                buffer[pos++] = (byte) ch;
              }
            } else {
              buffer[pos++] = (byte) ch;
            }

            break;

          default:

            // 写到bytes中，到时一起输出。
            buffer[pos++] = (byte) ch;
            break;
        }
        continue;
      }
      // 先将buffer中的字节串转换成字符串。
      if (pos > 0) {
        String s = new String(buffer, 0, pos, encoding);

        out.append(s);

        if (!needToChange && !s.equals(new String(charArray, startIndex, pos))) {
          needToChange = true;
        }

        pos = 0;
      }

      // 如果ch是ISO-8859-1以外的字符，直接输出即可
      out.append((char) ch);
    }

    // 先将buffer中的字节串转换成字符串。
    if (pos > 0) {
      String s = new String(buffer, 0, pos, encoding);

      out.append(s);

      if (!needToChange && !s.equals(new String(charArray, startIndex, pos))) {
        needToChange = true;
      }

      pos = 0;
    }

    return needToChange;
  }

}
