package org.bitcoinj.examples;
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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;




public class BitcoinFileReader {
	public String file;
	public ArrayList<String> portions;
	public ArrayList<byte[]> checksums;
	public Hashtable<byte[], String> lookup;

	public BitcoinFileReader(String file){
		this.file = file;
		this.portions = new ArrayList<String>(readfile(this.file));//256 portions
		this.checksums = new ArrayList<byte[]>(computeShaChecksums());
		this.lookup = new Hashtable<byte[]/*checksum*/, String/*portion*/>();

		int index = 0;
		for(byte[] checksum: this.checksums){
			this.lookup.put(checksum, this.portions.get(index));
			index++;
		}
	}

	public String getChecksumHexString(int index){
		Hex hex = new Hex();
		return hex.toHexString(this.checksums.get(index));
	}

	public String getPortion(byte[] checksum){
		return this.lookup.get(checksum);
	}
	
	public String getPortion(String checksum){
		Hex hex = new Hex();
		byte[] temp = hex.decode(checksum);
		return this.lookup.get(temp);
	}

	public ArrayList<byte[]> computeShaChecksums(){

		MessageDigest digest;
		ArrayList<byte[]> checksums = new ArrayList<byte[]>();

		for(String portion: portions){
			try {
				
				
				digest = MessageDigest.getInstance("SHA-256");
				checksums.add(digest.digest(portion.getBytes(StandardCharsets.UTF_8)));
				
				
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



	public static void main(String[] args) throws Exception {


		// change to the file you want to upload to you input.
		BitcoinFileReader file1 = new BitcoinFileReader("C:\\Users\\Ibrahim AlSaud\\Documents\\part1.txt");
		BitcoinFileReader file2 = new BitcoinFileReader("C:\\Users\\Ibrahim AlSaud\\Documents\\part2.txt");

		System.out.println("file1");
		System.out.println("length:"+file1.portions.size());
		System.out.println("portions:\n");
		myprint(file1.portions);
		System.out.println("checksums:\n"+file1.checksums);
		System.out.println("file2");
		System.out.println("length:"+file2.portions.size());
		System.out.println("portions:\n");
		myprint(file2.portions);
		System.out.println("checksums:\n"+file2.checksums);

		Hex hex = new Hex();
		String temp = hex.toHexString(file1.checksums.get(0));
		System.out.println(temp);

//		String revSha = "5cc9e697837a3e903c05e99bd7f29f22109f7e1cf4d0af7353c8c6a368378871";
//		byte[] revShaBytes = hex.decode(revSha);
//
//		//not sure if this proves equality, but byte[].equal is saying they're not equal.
//		if(file1.getChecksumHexString().equals(hex.toHexString(revShaBytes)))
//			System.out.println("yeap");
//		else
//			System.out.println("nope!");

	}

	private static void myprint(ArrayList<String> portions) {
		System.out.println("{");
		for(String portion: portions){
			System.out.println(portion+"\n,");
		}
		System.out.println("}");
	}






}