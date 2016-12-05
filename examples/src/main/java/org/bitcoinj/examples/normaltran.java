package org.bitcoinj.examples;


import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.utils.BriefLogFormatter;

import java.io.File;


import static org.bitcoinj.core.Coin.*;
public class normaltran {
	  public static void main(String[] args) throws Exception {
	        BriefLogFormatter.init();
	        final RegTestParams params = RegTestParams.get();

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

	        
	        Address address2 = wallet2.wallet().currentReceiveAddress();
	        Transaction tx = wallet1.wallet().createSend(address2, COIN.multiply(15));
	        final Peer peer = wallet1.peerGroup().getConnectedPeers().get(0);
	        peer.sendMessage(tx);
	        
	        
	        
	        // TODO just tried spending custom transaction from wallet3 to wallet4 
	        // but it seems to be throwing an insufficient money exception. 
	        // Theories:
	        //		- wallet3 needs a new signer.
	        //		- tx3 needs a special redeem script.
	        
	        
	        Thread.sleep(1000);
	        wallet1.stopAsync();
	        wallet1.awaitTerminated();
	        wallet2.stopAsync();
	        wallet2.awaitTerminated();
	       
	       
	}      
	   
}