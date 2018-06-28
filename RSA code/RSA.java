import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.io.*;

public class RSA{
	
	private PublicKey pubKey;
	private PrivateKey secretKey;
	private Cipher cipher ;
	
	public RSA(){
		try{
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); // RSA algorithm initialized
			keyGen.initialize(1024); // Initializing with key size
			KeyPair keyPair = keyGen.genKeyPair();
			pubKey = keyPair.getPublic();
			secretKey = keyPair.getPrivate();
			cipher = Cipher.getInstance("RSA");
		}catch(NoSuchAlgorithmException nse){
			nse.printStackTrace();
		}catch(NoSuchPaddingException nspe){
			nspe.printStackTrace();
		}
	}

	public byte[] encrypt(byte[] plainBytes) throws InvalidKeyException,IllegalBlockSizeException,BadPaddingException{
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return cipher.doFinal(plainBytes);
	}

	public byte[] decrypt(byte[] encryptedBytes) throws InvalidKeyException,IllegalBlockSizeException,BadPaddingException{
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(encryptedBytes);
	}

}

class Test{
	static RSA cryptoObject;

	public static void main(String[] args) {
		cryptoObject = new RSA();
		String input_file_name = args[0];
		String encrypted_file_name = args[1];
		String decrypted_file_name = args[2];
		FileInputStream fin =null;
		FileInputStream en_fin =null;
		FileOutputStream en_out= null; 
		FileOutputStream dec_out= null;
		try{
			fin = new FileInputStream(input_file_name);
			en_out = new FileOutputStream(encrypted_file_name);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		byte[] buffer = new byte[64];
		byte[] write_buffer;
		int num_bytes_read ;
		System.out.println("ENCRYPTING................................");
		while(true){

			try{
				num_bytes_read = fin.read(buffer);
				if(num_bytes_read == -1){
					fin.close();
					en_out.close();
					break;
				}
				if(num_bytes_read == 64){
					write_buffer = cryptoObject.encrypt(buffer);
					en_out.write(write_buffer);
					//System.out.println("Write buffer length : "+ write_buffer.length);
				}else{
					
					write_buffer = cryptoObject.encrypt(buffer);
					en_out.write(write_buffer);
					//System.out.println("Write buffer length : "+ write_buffer.length);
				}

			}catch(Exception e){
				e.printStackTrace();
			}
		}

		byte[] en_read_buffer = new byte[128];
		byte[] dec_write_buffer ;

		try{
			en_fin = new FileInputStream(encrypted_file_name);
			dec_out = new FileOutputStream(decrypted_file_name);
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		System.out.println("DECRYPTING................................");
		while(true){
			try{
				num_bytes_read = en_fin.read(en_read_buffer);
				if(num_bytes_read == -1){
					en_fin.close();
					dec_out.close();
					break;
				}

				if(num_bytes_read == 128){
					
					dec_write_buffer = cryptoObject.decrypt(en_read_buffer);
					dec_out.write(dec_write_buffer);
				}else{
					
					dec_write_buffer = cryptoObject.decrypt(en_read_buffer);
					dec_out.write(dec_write_buffer);
				}

			}catch(Exception e){
				e.printStackTrace();
			}
		}




	}
}

