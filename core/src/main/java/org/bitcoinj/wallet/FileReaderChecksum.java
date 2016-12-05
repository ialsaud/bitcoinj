package org.bitcoinj.wallet;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.utils.BriefLogFormatter;
import org.spongycastle.util.encoders.Hex;

import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;




public class FileReaderChecksum {
	
	public String file;
	public ArrayList<String> portions;
	public ArrayList<String> checksums;
	public Hashtable<String, String> lookup;

	public FileReaderChecksum(String file){
		this.file = file;
		this.portions = new ArrayList<String>(readfile(this.file));//256 portions
		this.checksums = new ArrayList<String>(computeShaChecksums());
		this.lookup = new Hashtable<String/*checksum*/, String/*portion*/>();
		
		int index = 0;
		for(String checksum: this.checksums){
			this.lookup.put(checksum, this.portions.get(index));
			index++;
		}
	}

	public static String getChecksumHexString(byte[] checksum){
		return Hex.toHexString(checksum);
	}

	public String getPortion(String checksum){
		return this.lookup.get(checksum);
	}
	
	public String getPortion(byte[] checksum){
		String temp = Hex.toHexString(checksum);
		return this.lookup.get(temp);
	}

	public ArrayList<String> computeShaChecksums(){

		MessageDigest digest;
		ArrayList<String> checksums = new ArrayList<String>();

		for(String portion: portions){
			try {
				
				
				digest = MessageDigest.getInstance("SHA-256");
				checksums.add(Hex.toHexString(digest.digest(portion.getBytes(StandardCharsets.UTF_8))));
				
				
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return checksums;
	}


	public static ArrayList<String> readfile(String file){

		FileReader fr = null;	
		ArrayList<String> parts = new ArrayList<String>();

		try {

			fr = new FileReader(file);
			StringBuffer buf = new StringBuffer();
			int temp, count=0;


			while ((temp = fr.read()) != -1) {
				count++;
				buf.append((char)temp);

				if(count >= 256){
					parts.add(new String(buf.toString()));
					buf.delete(0, count);
					count =0;
				}
			}

			if(count>0 && count<256){
				while(count < 256){
					buf.append('I');
					count++;
				}
				parts.add(new String(buf.toString()));
			}



		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}




		return parts;
	}	


}