package com.pan.pion.codegen.cfg;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;


/**
 * TODO
 * 
 * @author: guohm
 * @date:2015年1月1日 下午4:34:54
 * @since 1.0.0
 */
public class ResourceUtil {

	/**
	 * 加载classpath路径下的文件到流中。
	 * <p>
	 * <b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 * @author: guohm
	 * @date:2015年1月1日 下午4:38:32
	 * @since 1.0.0
	 */
	public static InputStream loadInputStreamFromClassPath(String filePath)
			throws Exception {
		return Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(filePath);
	}

	/**
	 * 加载classpath路径的属性文件。
	 * <p>
	 * <b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * 
	 * @param file_path
	 * @return
	 * @throws Exception
	 * @author: guohm
	 * @date:2015年1月1日 下午4:39:58
	 * @since 1.0.0
	 */
	public static Properties loadPropertiesFromClassPath(String filePath)
			throws Exception {
		Properties pc = new Properties();
		pc.load(loadInputStreamFromClassPath(filePath));
		return pc;
	}

	/**
	 * 向文件中追加一行内容，如果文件不存在，则创建新的文件。
	 * 
	 * <p>
	 * <b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * 
	 * @param fileName
	 * @param content
	 * @author: guohm
	 * @date:2015年1月1日 下午4:44:28
	 * @since 1.0.0
	 */
	public static void appendContent(String fileName, String content) {
		RandomAccessFile randomFile = null;
		try {
			// 打开一个随机访问文件流，按读写方式
			File file = new File(fileName);
			File pFile = file.getParentFile();
			if (!pFile.exists()) {
				pFile.mkdirs();
			}
			content = content + "\r\n";
			randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			content = new String(content.getBytes("UTF-8"), "ISO8859_1");
			randomFile.writeBytes(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 向文件中写入文件内容，如果文件目录不存在就创建之，如果文件存在就删除之。
	 * <p>
	 * <b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * 
	 * @param fileName
	 * @param content
	 * @author: guohm
	 * @date:2015年1月1日 下午5:00:55
	 * @since 1.0.0
	 */
	public static void writeContent(String fileName, String content) {
		FileWriter fw = null;
		try {
			File file = new File(fileName);
			File pFile = file.getParentFile();
			if (!pFile.exists()) {
				pFile.mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			fw = new FileWriter(file);
			fw.write(content, 0, content.length());
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将InputStream对象转化为byte数组。
	 * <p>
	 * <b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * 
	 * @param inputStream
	 * @return
	 * @author: guohm
	 * @date:2015年1月1日 下午5:07:11
	 * @since 1.0.0
	 */
	public static byte[] readStream(InputStream inputStream) {
		ByteArrayOutputStream outputSteam = null;
		try {
			outputSteam = new ByteArrayOutputStream();// 往内存中写字节数据
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = inputStream.read(buffer)) != -1) {
				outputSteam.write(buffer, 0, len);
			}
			return outputSteam.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputSteam != null) {
					outputSteam.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 读取文件内容。
	 * <p>
	 * <b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * 
	 * @param fileNeme
	 * @return
	 * @author: guohm
	 * @date:2015年1月1日 下午5:09:56
	 * @since 1.0.0
	 */
	public static String readFile(String fileNeme) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(fileNeme);
			byte[] data = readStream(inputStream);
			if (data == null || data.length == 0) {
				return null;
			}
			String content = new String(data, "UTF-8");
			return content;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 读取某一行的文件内容。
	 * <p>
	 * <b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * 
	 * @param fileName
	 * @param line
	 * @return
	 * @author: guohm
	 * @date:2015年1月1日 下午5:13:43
	 * @since 1.0.0
	 */
	public static String readLine(String fileName, int line) {
		RandomAccessFile raf = null;
		try {
			StringBuffer result = new StringBuffer();
			raf = new RandomAccessFile(fileName, "rw");
			int i = 1;
			String str = null;
			while (i++ == line && (str = raf.readLine()) != null) {
				result.append(new String(str.getBytes("ISO8859_1"), "UTF-8"));
				break;
			}
			return result.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 根据文件路径获取文件对象。
	 * <p>
	 * <b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * 
	 * @param path
	 * @return
	 * @author: guohm
	 * @date:2015年1月1日 下午5:17:48
	 * @since 1.0.0
	 */
	public static File getFileByPath(String path) {
		try {
			if (StringUtils.isBlank(path)) {
				return null;
			}
			File file = new File(path);
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将文件保存到其他目录下。
	 * <p>
	 * <b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * 
	 * @param path
	 * @param fileName
	 * @param file
	 * @author: guohm
	 * @date:2015年1月1日 下午5:26:41
	 * @since 1.0.0
	 */
	public static boolean saveFile(String path, String fileName, File file) {
		boolean flag = false;
		try {
			File newsFileRoot = new File(path);
			if (!newsFileRoot.exists()) {
				newsFileRoot.mkdirs();
			}
			String fullFileName = null;
			if (!path.endsWith(File.pathSeparator)) {
				fullFileName = path + File.pathSeparator + fileName;
			} else {
				fullFileName = path + fileName;
			}
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try {
				fos = new FileOutputStream(fullFileName);
				fis = new FileInputStream(file);
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = fis.read(buf)) > 0) {
					fos.write(buf, 0, len);
				}
				flag = true;
				return flag;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					fis.close();
				}
				if (fos != null) {
					fos.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	/**
	 * 删除文件。
	 * <p>
	 * <b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * 
	 * @param filePath
	 * @return
	 * @author: guohm
	 * @date:2015年1月1日 下午5:28:41
	 * @since 1.0.0
	 */
	public static boolean deleteFile(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录与文件。
	 * <p>
	 * <b>注意：</b><br>
	 * </p>
	 * <p>
	 * <b>变更记录：</b><br>
	 * </p>
	 * 
	 * @param filePath
	 * @return
	 * @author: guohm
	 * @date:2015年1月1日 下午5:28:59
	 * @since 1.0.0
	 */
	public static boolean deleteFolder(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(filePath);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(filePath);
			}
		}
	}

	// 删除目录
	public static boolean deleteDirectory(String filePath) {
		if (StringUtils.isBlank(filePath)) {
			return false;
		}
		boolean flag = false;
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!filePath.endsWith(File.separator)) {
			filePath = filePath + File.separator;
		}
		File dirFile = new File(filePath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 保存BufferedImage对象到指定路径
	 * 
	 * @param bufferedImage
	 *            图片对象
	 * @param path
	 *            保存到的文件夹
	 * @param imageName
	 *            保存后的文件名称
	 * @return
	 */
	public static boolean saveBufferedImage(BufferedImage bufferedImage,
			String path, String imageName) {
		try {
			/*Image big = bufferedImage.getScaledInstance(256, 256,
					Image.SCALE_DEFAULT);*/
			BufferedImage inputbig = new BufferedImage(256, 256,
					BufferedImage.TYPE_INT_BGR);
			inputbig.getGraphics().drawImage(bufferedImage, 0, 0, 256, 256,
					null); // 画图

			File pathfile = new File(path); // 此目录保存缩小后的关键图
			if (pathfile.exists()) {
				//e.printStackTrace();
			} else {
				// 如果要创建的多级目录不存在才需要创建。
				pathfile.mkdirs();
			}
			ImageIO.write(inputbig, "jpg", new File(path + "/" + imageName)); // 将其保存在C:/imageSort/targetPIC/下
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
