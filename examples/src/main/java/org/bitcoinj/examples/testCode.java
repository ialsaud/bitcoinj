package org.bitcoinj.examples;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.bitcoinj.wallet.FileReaderChecksum;
import org.spongycastle.util.encoders.Hex;

public class testCode{


	public static void main(String[] args){
	
			FileReaderChecksum t1 = new FileReaderChecksum("C:\\Users\\Ibrahim AlSaud\\Documents\\part1.txt");
			Hex hex = new Hex();
			
	
			System.out.println(t1.checksums.get(0));
			System.out.println("8663289b786c1562c94d3ff60a693788d2e2dcfc9f002393bd25322604ca1cb4");
	
			System.out.println(t1.getPortion(Hex.decode("8663289b786c1562c94d3ff60a693788d2e2dcfc9f002393bd25322604ca1cb4")));
			
			System.out.println(comparebytes( Hex.decode(t1.checksums.get(0)) , hex.decode("8663289b786c1562c94d3ff60a693788d2e2dcfc9f002393bd25322604ca1cb4")));
			
			
			printbytes(Hex.decode(t1.checksums.get(0)));
			printbytes(hex.decode("8663289b786c1562c94d3ff60a693788d2e2dcfc9f002393bd25322604ca1cb4"));
			
	}

	
	private static void printbytes(byte[] bs) {
		System.out.print("{");
		for(byte a: bs)
			System.out.print(a+",");
			
		System.out.print("}\n");
	}


	public static Boolean comparebytes(byte[] a, byte[] b){
		int i=0;
		if(a.length != b.length)
			return false;
		for(byte ab: a){
			if(ab != b[i++]){
				return false;
			}
		}
		return true;
	}
}