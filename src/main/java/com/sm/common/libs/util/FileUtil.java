/**
 * 
 */
package com.sm.common.libs.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * 有关<code>File</code>处理的工具类。
 * 
 * <p>
 * 这个类中的每个方法都可以“安全”地处理 <code>null</code> ，而不会抛出 <code>NullPointerException</code>。
 * </p>
 * 
 * @author <a href="chenxu.xc@alibaba-inc.com">xc</a>
 * @version create on 2016年12月30日 下午3:39:12
 */
public abstract class FileUtil {

  /**
   * 判断文件是否存在，如果<code>path</code>为<code>null</code>，则返回<code>false</code>
   * 
   * @param path 文件路径
   * @return 如果存在返回<code>true</code>
   */
  public static boolean exist(String path) {
    return (path == null) ? false : new File(path).exists();
  }

  /**
   * 判断文件是否存在，如果<code>file</code>为<code>null</code>，则返回<code>false</code>
   * 
   * @param file 文件
   * @return 如果存在返回<code>true</code>
   */
  public static boolean exist(File file) {
    return (file == null) ? false : file.exists();
  }

  /**
   * 是否存在匹配文件
   * 
   * @param directory 文件夹路径
   * @param regexp 文件夹中所包含文件名的正则表达式
   * @return 如果存在匹配文件返回<code>true</code>
   */
  public static boolean exist(String directory, String regexp) {
    File file = new File(directory);
    if (!file.exists()) {
      return false;
    }

    String[] fileList = file.list();
    if (fileList == null) {
      return false;
    }

    for (String fileName : fileList) {
      if (fileName.matches(regexp)) {
        return true;
      }

    }
    return false;
  }

  /**
   * 创建文件，不管文件层级，均可创建
   * 
   * @param path 文件路径
   * @return 是否创建成功，如果<code>path</code>为空或者<code>path</code>为<code>null</code>
   *         ,则返回<code>false</code>
   * @throws IOException
   */
  public static boolean createFile(String path) throws IOException {
    return createFile(path, false);
  }

  /**
   * 创建文件，不管文件层级，均可创建
   * 
   * @param path 文件路径
   * @param override 是否覆盖
   * @return 是否创建成功，如果<code>path</code>为空或者<code>path</code>为<code>null</code>
   *         ,则返回<code>false</code>
   * @throws IOException
   */
  public static boolean createFile(String path, boolean override) throws IOException {
    if (path == null) {
      return false;
    }

    File file = new File(path);
    if (file.exists() && !override) {
      return false;
    }

    if (file.isDirectory()) {
      return file.mkdirs();
    }

    if (file.getParentFile() != null) {
      file.getParentFile().mkdirs();
    }

    return file.createNewFile();
  }

  /**
   * 创建文件夹，不管文件层级，均可创建
   * 
   * @param path 文件路径
   * @param override 是否覆盖
   * @return 是否创建成功，如果<code>path</code>为空或者<code>path</code>为<code>null</code>
   *         ,则返回<code>false</code>
   */
  public static boolean createDir(String path, boolean override) {
    if (path == null) {
      return false;
    }

    File file = new File(path);
    if (file.exists() && !override) {
      return false;
    }

    return file.mkdirs();
  }

  /**
   * 创建文件夹，不管文件层级，均可创建
   * 
   * @param path 文件路径
   * @return 是否创建成功，如果<code>path</code>为空或者<code>path</code>为<code>null</code>
   *         ,则返回<code>false</code>
   */
  public static boolean createDir(String path) {
    return createDir(path, false);
  }

  /**
   * 创建文件路径的父文件夹，不管文件层级，均可创建
   * 
   * @param path 文件路径
   * @return 是否创建成功，如果<code>path</code>为空或者<code>path</code>为<code>null</code>
   *         ,则返回<code>false</code>
   */
  public static boolean createParentDir(String path) {
    return createParentDir(path, false);
  }

  public static boolean createParentDir(File file) {
    return createParentDir(file, false);
  }

  /**
   * 创建文件路径的父文件夹，不管文件层级，均可创建
   * 
   * @param path 文件路径
   * @param override 是否覆盖
   * @return 是否创建成功，如果<code>path</code>为空或者<code>path</code>为<code>null</code>
   *         ,则返回<code>false</code>
   */
  public static boolean createParentDir(String path, boolean override) {
    if (path == null) {
      return false;
    }

    return createDir(new File(path).getParent(), override);
  }

  public static boolean createParentDir(File file, boolean override) {
    if (file == null) {
      return false;
    }

    return createDir(file.getParent(), override);
  }

  /**
   * 刪除文件
   * 
   * @param file 文件
   * @return 删除成功返回<code>true</code>，否则返回<code>false</code>
   */
  public static boolean delete(File file) {
    if (file == null) {
      return false;
    }

    return file.delete();
  }

  /**
   * 刪除文件
   * 
   * @param path 文件路径
   * @return 删除成功返回<code>true</code>，否则返回<code>false</code>
   */
  public static boolean delete(String path) {
    if (path == null) {
      return false;
    }

    return new File(path).delete();
  }

  /**
   * 删除文件及子文件
   * 
   * @param dir 文件夹
   * @return 删除成功返回<code>true</code>，否则返回<code>false</code>
   */
  public static boolean deleteDir(File dir) {
    if (dir == null) {
      return false;
    }

    if (dir.isDirectory()) {
      String[] children = dir.list();
      for (int i = 0; i < children.length; i++) {
        boolean success = deleteDir(new File(dir, children[i]));
        if (!success) {
          return false;
        }
      }
    }
    // The directory is now empty so delete it
    return dir.delete();
  }

  /**
   * 删除文件及子文件
   * 
   * @param path 文件路径
   * @return 删除成功返回<code>true</code>，否则返回<code>false</code>
   */
  public static boolean deleteDir(String path) {
    if (path == null) {
      return false;
    }

    return deleteDir(new File(path));
  }

  /**
   * 判断是否为目录，如果<code>path</code>为<code>null</code>，则返回<code>false</code>
   * 
   * @param path 文件路径
   * @return 如果为目录<code>true</code>
   */
  public static boolean isDirectory(String path) {
    return (path == null) ? false : new File(path).isDirectory();
  }

  /**
   * 判断是否为目录，如果<code>file</code>为<code>null</code>，则返回<code>false</code>
   * 
   * @param file 文件
   * @return 如果为目录<code>true</code>
   */
  public static boolean isDirectory(File file) {
    return (file == null) ? false : file.isDirectory();
  }

  /**
   * 判断是否为文件，如果<code>path</code>为<code>null</code>，则返回<code>false</code>
   * 
   * @param path 文件路径
   * @return 如果为文件<code>true</code>
   */
  public static boolean isFile(String path) {
    return (path == null) ? false : new File(path).isFile();
  }

  /**
   * 判断是否为文件，如果<code>file</code>为<code>null</code>，则返回<code>false</code>
   * 
   * @param file 文件
   * @return 如果为文件<code>true</code>
   */
  public static boolean isFile(File file) {
    return (file == null) ? false : file.isFile();
  }

  /**
   * 从URL中取得文件，如果URL为空，或不代表一个文件, 则返回<code>null</code>
   * 
   * @param url URL
   * 
   * @return 文件, 如果URL为空，或不代表一个文件, 则返回<code>null</code>
   */
  public static File toFile(URL url) {
    if (url == null) {
      return null;
    }

    if (!"file".equals(url.getProtocol())) {
      return null;
    }

    String path = url.getPath();

    return (path != null) ? new File(URLEscapeUtil.unescapeURL(path)) : null;

  }

}
