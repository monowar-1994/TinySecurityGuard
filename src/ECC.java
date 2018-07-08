/**
 * 
 */

/**
 * @author mazharul.islam
 *
 */

import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;
import java.util.Random;
import javax.crypto.KeyAgreement;




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
			ECGenParameterSpec ecsp;
			ecsp = new ECGenParameterSpec("secp192k1");
			kpg.initialize(ecsp);
			KeyPair kpU = kpg.genKeyPair();
			this.privKey = kpU.getPrivate();
			this.pubKey = kpU.getPublic();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public static void main(String[] args) {
		ECC eccU = new ECC();
		ECC eccV = new ECC();
		
		
		// generate symmetric key
		try {
			KeyAgreement ecdhU = KeyAgreement.getInstance("ECDH");
			ecdhU.init(eccU.privKey);
			ecdhU.doPhase(eccV.pubKey,true); // V is the receiver
			System.out.println(new BigInteger (1,ecdhU.generateSecret()));
			
			// another key
			KeyAgreement ecdhV = KeyAgreement.getInstance("ECDH");
			ecdhV.init(eccV.privKey);
			ecdhV.doPhase(eccU.pubKey,true); // V is the receiver
			System.out.println(new BigInteger (1,ecdhV.generateSecret()));
			
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
}
