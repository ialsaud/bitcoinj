package org.bitcoinj.examples;
import static org.bitcoinj.core.Coin.COIN;

import java.io.File;
import java.util.Set;

import org.bitcoinj.core.*;
import org.bitcoinj.core.ECKey.ECDSASignature;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.utils.BriefLogFormatter;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.wallet.SendRequest;
import org.spongycastle.util.encoders.Hex;

import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
public class reqtx2 {

	public static void main(String[] args) throws Exception {
        BriefLogFormatter.init();
        final RegTestParams params = RegTestParams.get();
        Hex hex = new Hex();
        byte[] checksum1 = hex.decode("e04fd020ea3a6910a2d808002b30309d");
        byte[] checksum2 = hex.decode("e04fd020ea3a6910a2d808002b30309e");

        
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
     
       
        Address address4 = wallet4.wallet().currentReceiveAddress();
        SendRequest req = SendRequest.to(address4, COIN.multiply(8));
        req.tx.setLockTime(1480919005);
        wallet2.wallet().completeTx(req);
        

        System.out.println(req.tx);
        final Peer peer = wallet2.peerGroup().getConnectedPeers().get(0);
        peer.sendMessage(req.tx);
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