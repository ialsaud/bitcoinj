package org.bitcoinj.examples;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.utils.BriefLogFormatter;
import org.bitcoinj.wallet.Wallet.BalanceType;

import java.io.File;

public class printWallet {
	  public static void main(String[] args) throws Exception {
		  BriefLogFormatter.init();
	      final RegTestParams params = RegTestParams.get();
	      
//		  File file1 = new File("/Users/Ibrahim/Google Drive/College/FALL16/ENEE457/Research/bitcoinj-master/wallet1.wallet");
//		  File file2 = new File("/Users/Ibrahim/Google Drive/College/FALL16/ENEE457/Research/bitcoinj-master/wallet2.wallet");
//		  File file3 = new File("/Users/Ibrahim/Google Drive/College/FALL16/ENEE457/Research/bitcoinj-master/wallet3.wallet");
//	      Wallet wallet1 = Wallet.loadFromFile(file1);
//	      Wallet wallet2 = Wallet.loadFromFile(file2);
//	      Wallet wallet3 = Wallet.loadFromFile(file3);
	      
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
	      
//	      wallet3.wallet().addTransactionSigner(signer);
	      
	      wallet4.connectToLocalHost();
	      wallet4.setAutoSave(false);
	      wallet4.startAsync();
	      wallet4.awaitRunning();
	      
	      System.out.println("Wallet 1  ++++++++++++++++++++++++++++++++++++++++++");
	      System.out.println(wallet1.wallet());
	      System.out.println("Wallet 2  ++++++++++++++++++++++++++++++++++++++++++");
	      System.out.println(wallet2.wallet());
	      System.out.println("Wallet 3  ++++++++++++++++++++++++++++++++++++++++++");
	      System.out.println(wallet3.wallet());
	      System.out.println("Wallet 4  ++++++++++++++++++++++++++++++++++++++++++");
	      System.out.println(wallet4.wallet());
	      
	      
	      

	      System.out.println("++++++++++++++++++++++++++++++++++++++++++");
	      
	      System.out.println(wallet3.wallet().getTransactionSigners());
	      
	      
	  }
}