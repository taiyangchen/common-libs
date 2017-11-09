/**
 * 
 */
package com.sm.common.libs.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.sm.common.libs.able.Transformer;

/**
 * * 有关<code>Reader</code>的工具类。
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年11月28日 下午1:09:09
 */
public abstract class ReaderUtil {

  /**
   * 获取所有行内容并关闭流
   * 
   * @param inputStream 输入流 @see InputStream
   * @return 数据集合 @see List
   * @throws IOException
   */
  public static List<String> readLinesAndClose(InputStream inputStream) throws IOException {
    return readLinesAndClose("UTF-8", inputStream);
  }

  public static <T> List<T> readLinesAndClose(InputStream inputStream, Transformer<List<String>, List<T>> transformer)
      throws IOException {
    List<String> lines = readLinesAndClose("UTF-8", inputStream);
    if (CollectionUtil.isEmpty(lines)) {
      return null;
    }

    return transformer.transform(lines);
  }

  /**
   * 获取所有行内容并关闭流
   * 
   * @param filePath 文件路径
   * @param charset 编码格式
   * @return 数据集合 @see List
   * @throws IOException
   */
  public static List<String> readLinesAndClose(String filePath, Charset charset) throws IOException {
    InputStream inputStream = getInputStream(filePath);

    return readLinesAndClose(charset.name(), inputStream);
  }

  /**
   * 获取所有行内容并关闭流
   * 
   * @param inputStream 输入流 @see InputStream
   * @return 数据集合 @see List
   * @throws IOException
   */
  public static List<String> readLinesAndClose(String charsetName, InputStream inputStream) throws IOException {
    if (inputStream == null) {
      return null;
    }

    BufferedReader reader = getBufferedReader(inputStream, charsetName);
    List<String> result = new ArrayList<>();
    try {
      String line = null;
      while ((line = reader.readLine()) != null) {
        result.add(line.trim());
      }
      return result;
    } finally {
      IOUtil.close(reader);
    }
  }

  /**
   * 获取所有行内容并关闭流
   * 
   * @param filePath 文件路径
   * @return 数据集合 @see List
   * @throws IOException
   */
  public static List<String> readLinesAndClose(String filePath) throws IOException {
    InputStream inputStream = getInputStream(filePath);

    return readLinesAndClose(inputStream);
  }

  /**
   * 获取所有行内容并关闭流
   * 
   * @param file 文件对象 @see File
   * @return 数据集合 @see List
   * @throws IOException
   */
  public static List<String> readLinesAndClose(File file) throws IOException {
    InputStream inputStream = getInputStream(file);

    return readLinesAndClose(inputStream);
  }

  /**
   * 获取所有行内容并关闭流
   * 
   * @param charsetName 编码格式
   * @param file 文件对象 @see File
   * @return 数据集合 @see List
   * @throws IOException
   */
  public static List<String> readLinesAndClose(String charsetName, File file) throws IOException {
    InputStream inputStream = getInputStream(file);

    return readLinesAndClose(charsetName, inputStream);
  }

  /**
   * 获取总记录数并关闭流
   * 
   * @param inputStream 输入流 @see InputStream
   * @return 总记录数
   * @throws IOException
   */
  public static int getCountAndClose(InputStream inputStream) throws IOException {
    return getCountAndClose(inputStream, "UTF-8");
  }

  /**
   * 获取总记录数并关闭流
   * 
   * @param inputStream 输入流 @see InputStream
   * @param charsetName 编码格式
   * @return 总记录数
   * @throws IOException
   */
  public static int getCountAndClose(InputStream inputStream, String charsetName) throws IOException {
    if (inputStream == null) {
      return 0;
    }

    int count = 0;
    BufferedReader reader = getBufferedReader(inputStream, charsetName);
    String line = null;
    try {
      while ((line = reader.readLine()) != null) {
        // 忽略空行
        if (StringUtils.isNotBlank(line))
          count++;
      }
      return count;
    } finally {
      IOUtil.close(reader);
    }
  }

  /**
   * 获取总记录数并关闭流
   * 
   * @param filePath 文件路径
   * @return 总记录数
   * @throws IOException
   */
  public static int getCountAndClose(String filePath) throws IOException {
    InputStream inputStream = getInputStream(filePath);

    return getCountAndClose(inputStream);
  }

  /**
   * 获取总记录数并关闭流
   * 
   * @param file 文件对象 @see File
   * @return 总记录数
   * @throws IOException
   */
  public static int getCountAndClose(File file) throws IOException {
    InputStream inputStream = getInputStream(file);

    return getCountAndClose(inputStream);
  }

  /**
   * 获取输入流
   * 
   * @param filePath 文件路径
   * @return <code>InputStream</code> 如果找不到。
   * @throws FileNotFoundException
   */
  private static InputStream getInputStream(String filePath) throws FileNotFoundException {
    return new FileInputStream(filePath);
  }

  /**
   * 获取输入流
   * 
   * @param file 文件对象 @see File
   * @return <code>InputStream</code> 如果<code>file</code>为<code>null</code>，返回 <code>null</code>
   * @throws FileNotFoundException
   */
  private static InputStream getInputStream(File file) throws FileNotFoundException {
    if (file == null) {
      return null;
    }

    return new FileInputStream(file);
  }

  /**
   * 获取<code>BufferedReader</code>
   * 
   * @param inputStream 输入流 @see InputStream
   * @param charsetName 编码格式
   * @return <code>BufferedReader</code>
   * @throws UnsupportedEncodingException
   */
  private static BufferedReader getBufferedReader(InputStream inputStream, String charsetName)
      throws UnsupportedEncodingException {
    return new BufferedReader(new InputStreamReader(inputStream, charsetName));
  }

}
