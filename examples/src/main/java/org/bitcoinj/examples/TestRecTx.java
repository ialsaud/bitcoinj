package org.bitcoinj.examples;


import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.utils.BriefLogFormatter;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.wallet.SendRequest;

import java.io.File;
import java.util.*;

import javax.annotation.Nullable;

import static org.bitcoinj.core.Coin.*;
public class TestRecTx {
    
	  public static byte[] hexStringToByteArray(String s) {
		  int len = s.length();
		  byte[] data = new byte[len / 2];
		  for (int i = 0; i < len; i += 2) {
			  data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					  				+ Character.digit(s.charAt(i+1), 16));
		  }
		  return data;
	  }
	
	  public static void main(String[] args) throws Exception {
	        BriefLogFormatter.init();
	        final RegTestParams params = RegTestParams.get();
	        byte[] checksum1 = hexStringToByteArray("e04fd020ea3a6910a2d808002b30309d");
	        byte[] checksum2 = hexStringToByteArray("e04fd020ea3a6910a2d808002b30309e");
	        boolean flag=true;
	        int OP_EQUAL=0x87;  
	        int OP_DUP=0x76;  
	        int OP_HASH160=0xa9;  
	        int OP_EQUALVERIFY=0x88;  
	        int OP_CHECKSIG=0xac;  
	        int OP_VERIFY=0x69;

	        WalletAppKit wallet1 = new WalletAppKit(params, new File("."), "wallet1");
	        WalletAppKit wallet2 = new WalletAppKit(params, new File("."), "wallet2");
	        WalletAppKit wallet3 = new WalletAppKit(params, new File("."), "wallet3");
	        WalletAppKit wallet4 = new WalletAppKit(params, new File("."), "wallet4");
		    
	        
	        wallet1.connectToLocalHost();
	        wallet1.setAutoSave(false);
	        wallet1.startAsync();
	        wallet1.awaitRunning();
	        wallet2.connectToLocalHost();
	        wallet2.setAutoSave(false);
	        wallet2.startAsync();
	        wallet2.awaitRunning();
	        wallet3.connectToLocalHost();
	        wallet3.setAutoSave(false);
	        wallet3.startAsync();
	        wallet3.awaitRunning();
	        wallet4.connectToLocalHost();
	        wallet4.setAutoSave(false);
	        wallet4.startAsync();
	        wallet4.awaitRunning();
        
	        TransactionOutput output;
	        Transaction temp;
	        Address address4 = wallet4.wallet().currentReceiveAddress();
	        Set<Transaction> transactions = wallet3.wallet().getTransactions(true);
	        temp = new Transaction(params, hexStringToByteArray("0100000001df64ad4051796dc616a16c798d3c0381219f4d4418c1d335e80077b233e33877010000006b483045022100f5664de61bd0c9576d7dd2aaf98f2ba4e60c1013e55a36e62bf1d47c38a0fb5c02206872b35821f05069df5d2302dadc55011e6e6d7569e2e46c655deb9265390f3f0121035562c04ff3ddb5a8ba566f4cd84c3e47478997ab8730bd923a4260fc9dfe246effffffff0200f90295000000002c76a914d713cf366138ad0d0aac8c148ca724c18a8c4e4d88ac6910e04fd020ea3a6910a2d808002b30309d87f4c80295000000001976a9142515ad4a14c15cc363796684149fd23ae1c3146e88ac00000000") );
	        output = temp.getOutput(0);	        
	        System.out.println("Tansaction output:\n"+output);	 
	        try{
		        ECKey signingKey = new ECKey();
		        Script unlocking = ScriptBuilder.createInputScript(null, signingKey, checksum1);		      
		        TransactionInput Input = new TransactionInput(params, temp, unlocking.getProgram(), 0);//invalid TODO find way to make TransactionInput.

		        Input.verify(output);
		        
	        }catch(Exception e){
	        	e.printStackTrace();
	        	System.out.println("Exception:\n"+e);
	        } 
	        
	        
	        
	        Thread.sleep(1000);
	        wallet1.stopAsync();
	        wallet1.awaitTerminated();
	        wallet2.stopAsync();
	        wallet2.awaitTerminated();
	        wallet3.stopAsync();
	        wallet3.awaitTerminated();
	        wallet4.stopAsync();
	        wallet4.awaitTerminated();
	       
	}      
	   
}
