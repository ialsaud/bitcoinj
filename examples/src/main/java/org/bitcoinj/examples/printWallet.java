package org.bitcoinj.examples;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.utils.BriefLogFormatter;
import org.bitcoinj.wallet.Wallet.BalanceType;

import java.io.File;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 
 * This class is only concerned with printing the four wallets that are used for
 * developing the checksum contract transaction.
 *
 */
public class printWallet {

	public static void main(String[] args) throws Exception {

		BriefLogFormatter.init();

		final RegTestParams params = RegTestParams.get();

		// print current time
		long timestamp = new Timestamp(System.currentTimeMillis()).getTime();
		BigInteger currentTime = new BigInteger(Long.toString(timestamp).substring(0, 10));
		System.out.println(currentTime);



		// creating an syncing wallets
		WalletAppKit wallet1 = new WalletAppKit(params, new File("."), "wallet1");
		WalletAppKit wallet2 = new WalletAppKit(params, new File("."), "wallet2");
		WalletAppKit wallet3 = new WalletAppKit(params, new File("."), "wallet3");
		WalletAppKit wallet4 = new WalletAppKit(params, new File("."), "wallet4");


		wallet1.setBlockingStartup(true);
		wallet1.connectToLocalHost();
		wallet1.setAutoSave(true);
		wallet1.startAsync();  
		wallet1.awaitRunning();
		wallet1.wallet().setAcceptRiskyTransactions(true);

		wallet2.setBlockingStartup(true);
		wallet2.connectToLocalHost();
		wallet2.setAutoSave(true);
		wallet2.startAsync();
		wallet2.awaitRunning();
		wallet2.wallet().setAcceptRiskyTransactions(true);

		wallet3.setBlockingStartup(true);
		wallet3.connectToLocalHost();
		wallet3.setAutoSave(true);
		wallet3.startAsync();
		wallet3.awaitRunning();
		wallet3.wallet().setAcceptRiskyTransactions(true);


		wallet4.setBlockingStartup(true);
		wallet4.connectToLocalHost();
		wallet4.setAutoSave(true);
		wallet4.startAsync();
		wallet4.awaitRunning();
		wallet4.wallet().setAcceptRiskyTransactions(true);
		Thread.sleep(5000);
		
		
		
		// print wallets
		System.out.println("Wallet 1  ++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(wallet1.wallet());
		System.out.println("Wallet 2  ++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(wallet2.wallet());
		System.out.println("Wallet 3  ++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(wallet3.wallet());
		System.out.println("Wallet 4  ++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(wallet4.wallet());



		// disconnecting from network
		wallet1.stopAsync();
		wallet1.awaitTerminated();
		wallet4.stopAsync();
		wallet4.awaitTerminated();
		wallet3.stopAsync();
		wallet3.awaitTerminated();
		wallet2.stopAsync();
		wallet2.awaitTerminated();

	}
}