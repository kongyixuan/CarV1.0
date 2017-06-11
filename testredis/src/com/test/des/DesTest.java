package com.test.des;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import org.junit.Test;

import java.security.Key;
import java.security.Security;

public class DesTest {

	/**
	 * @�ַ����ļ��ܽ��ܣ���Ҫ���ڲ�������
	 */
	// private static Log log = LogFactory.getLog(RealNameMsDesPlus.class);
	private static final String strDefaultKey = "RNQDK";

	private Cipher encryptCipher = null;

	private Cipher decryptCipher = null;

	/**
	 * ��byte����ת��Ϊ��ʾ16����ֵ���ַ����� �磺byte[]{8,18}ת��Ϊ��0813�� ��public static byte[]
	 * hexStr2ByteArr(String strIn) ��Ϊ�����ת������
	 * 
	 * @param arrB
	 *            ��Ҫת����byte����
	 * @return ת������ַ���
	 * @throws BusiException
	 *             �������������κ��쳣�������쳣ȫ���׳�
	 */
	private String byteArrToHexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// ÿ��byte�������ַ����ܱ�ʾ�������ַ����ĳ��������鳤�ȵ�����
		StringBuilder sb = new StringBuilder(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// �Ѹ���ת��Ϊ����
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// С��0F������Ҫ��ǰ�油0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * ����ʾ16����ֵ���ַ���ת��Ϊbyte���飬 ��public static String byteArr2HexStr(byte[] arrB)
	 * ��Ϊ�����ת������
	 * 
	 * @param strIn
	 *            ��Ҫת�����ַ���
	 * @return ת�����byte����
	 * @throws BusiException
	 *             �������������κ��쳣�������쳣ȫ���׳�
	 */
	private byte[] hexStrToByteArr(String strIn) {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// �����ַ���ʾһ���ֽڣ������ֽ����鳤�����ַ������ȳ���2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * Ĭ�Ϲ��췽����ʹ��Ĭ����Կ
	 * 
	 * @throws BusiException
	 */
	public DesTest() throws Exception {
		this(strDefaultKey);
	}

	/**
	 * ָ����Կ���췽��
	 * 
	 * @param strKey
	 *            ָ������Կ
	 * @throws BusiException
	 */
	public DesTest(String strKey) throws Exception {
		try {
			Security.addProvider(new com.sun.crypto.provider.SunJCE());
			Key key = getKey(strKey.getBytes());

			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);

			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}

	}

	/**
	 * �����ֽ�����
	 * 
	 * @param arrB
	 *            ����ܵ��ֽ�����
	 * @return ���ܺ���ֽ�����
	 * @throws BusiException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	private byte[] encrypt(byte[] arrB) throws Exception {
		try {
			return encryptCipher.doFinal(arrB);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	/**
	 * �����ַ���
	 * 
	 * @param strIn
	 *            ����ܵ��ַ���
	 * @return ���ܺ���ַ���
	 * @throws BusiException
	 */
	public String encrypt(String strIn) throws Exception {
		try {
			return byteArrToHexStr(encrypt(strIn.getBytes("UTF-8")));
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	/**
	 * �����ֽ�����
	 * 
	 * @param arrB
	 *            ����ܵ��ֽ�����
	 * @return ���ܺ���ֽ�����
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	private byte[] decrypt(byte[] arrB) throws IllegalBlockSizeException,
			BadPaddingException {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * �����ַ���
	 * 
	 * @param strIn
	 *            ����ܵ��ַ���
	 * @return ���ܺ���ַ���,��utf-8��ʽ����
	 * @throws BusiException
	 */
	public String decrypt(String strIn) throws Exception {
		try {
			return new String(decrypt(hexStrToByteArr(strIn)), "utf-8");
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	/**
	 * @param strIn
	 *            ����ܵ��ַ���
	 * @param encode
	 *            ���ܺ���ַ�����
	 * @return
	 * @throws BusiException
	 */
	public String decrypt(String strIn, String encode) throws Exception {
		try {
			return new String(decrypt(hexStrToByteArr(strIn)), encode);
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	/**
	 * ��ָ���ַ���������Կ����Կ������ֽ����鳤��Ϊ8λ ����8λʱ���油0������8λֻȡǰ8λ
	 * 
	 * @param arrBTmp
	 *            ���ɸ��ַ������ֽ�����
	 * @return ���ɵ���Կ
	 * @throws java.lang.Exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// ����һ���յ�8λ�ֽ����飨Ĭ��ֵΪ0��
		byte[] arrB = new byte[8];

		// ��ԭʼ�ֽ�����ת��Ϊ8λ
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// ������Կ
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

	@Test
	public void TestPlus() {
		try {
			DesTest test = new DesTest();
			String testEncrypt = test.encrypt("12345556");
			System.out.print(testEncrypt);
			String testDerypt = test.decrypt(testEncrypt);
			System.out.print(testDerypt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			DesTest test = new DesTest();
			String testEncrypt = test.encrypt("12345556");
			System.out.print(testEncrypt);
			String testDerypt = test.decrypt(testEncrypt);
			System.out.print(testDerypt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
