/**
 * 
 */

/**
 * @author mazharul.islam
 *
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



import java.util.Base64;




public class ECC {

	/**
	 * @param args
	 */
	PrivateKey privKey = null;
	PublicKey pubKey = null;
	public ECC() {
		
		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance("EC","SunEC");
			ECGenParameterSpec ecsp = new ECGenParameterSpec("secp192r1"); 
			kpg.initialize(ecsp);
			KeyPair kpU = kpg.genKeyPair();
			this.privKey = kpU.getPrivate();
			this.pubKey = kpU.getPublic();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public void encrypt(PublicKey publickey) {
		
		
		return ;
	}
	public static void main(String[] args) {
		ECC eccU = new ECC();
		ECC eccV = new ECC();
		
		
		// generate symmetric key
		try {
			KeyAgreement ecdhU = KeyAgreement.getInstance("ECDH"); // Elliptic Curve Diffie Hellman
			ecdhU.init(eccU.privKey);
			ecdhU.doPhase(eccV.pubKey,true); // V is the receiver
		//	BigInteger stringKey = new BigInteger(1, ecdhU.generateSecret());
			
		//	System.out.println(stringKey.toByteArray().length);
		//	byte[] encodedKey     = Base64.getEncoder().encode(stringKey.toByteArray());
		//	System.out.println("Length : " +encodedKey.length);
			byte [] symmetricKey = ecdhU.generateSecret();
			System.out.println("Length :" +symmetricKey.length);
			SecretKey key = new SecretKeySpec(symmetricKey, 0, symmetricKey.length, "AES");
			System.out.println(key.toString());
			Cipher cipher;
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			System.out.println(key.toString().length());
			cipher.init(Cipher.ENCRYPT_MODE, key);
			System.out.println("provider=" + cipher.getProvider());
			String cleartextFile = "cleartext.txt";
	        String ciphertextFile = "ciphertextECIES.txt";
	        byte[] block = new byte[64];
	        
	        FileInputStream fis = new FileInputStream(cleartextFile);
	        FileOutputStream fos = new FileOutputStream(ciphertextFile);
	        CipherOutputStream cos = new CipherOutputStream(fos, cipher);
	            int i;
	            while ((i = fis.read(block)) != -1) {
	                cos.write(block, 0, i);
	            }
	            cos.close();
	            fis.close();
	            fos.close();
	           
	        String cleartextAgainFile = "cleartextAgainECIES.txt";
	        cipher.init(Cipher.DECRYPT_MODE,key);
	        fis = new FileInputStream(ciphertextFile);
	        CipherInputStream cis = new CipherInputStream(fis, cipher);
	        fos = new FileOutputStream(cleartextAgainFile);
	         while ((i = cis.read(block)) != -1) {
	                fos.write(block, 0, i);
	        }
	        fos.close();
	        cis.close();
		}catch (Exception e) {            
			e.printStackTrace();
		}
	}	
}
