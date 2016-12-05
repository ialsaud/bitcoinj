package org.bitcoinj.examples;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.utils.BriefLogFormatter;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.wallet.FileReaderChecksum;
import org.bitcoinj.wallet.SendRequest;
import org.spongycastle.util.encoders.Hex;
import org.bitcoinj.examples.customTx.*;

import static org.bitcoinj.script.ScriptOpCodes.OP_CHECKSIG;
import static org.bitcoinj.script.ScriptOpCodes.OP_DUP;
import static org.bitcoinj.script.ScriptOpCodes.OP_EQUAL;
import static org.bitcoinj.script.ScriptOpCodes.OP_EQUALVERIFY;
import static org.bitcoinj.script.ScriptOpCodes.OP_HASH160;
import static org.bitcoinj.script.ScriptOpCodes.OP_VERIFY;

import java.io.File;
import java.util.*;
import java.math.*;
import java.sql.Timestamp;
public class sendCustom {


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
		int OP_IF = 0x63;
		int OP_CHECKLOCKTIMEVERIFY = 0xb1;
		int OP_ELSE = 0x67;
		int OP_ENDIF = 0x68;
		int OP_DROP = 0x75;
		int OP_VERIFYLOCKTIME=0xba;

		BriefLogFormatter.init();
		final RegTestParams params = RegTestParams.get();
		int i;



		byte[] checksum1 = Hex.decode((new FileReaderChecksum("C:\\Users\\Ibrahim AlSaud\\Documents\\part1.txt")).checksums.get(0));


		WalletAppKit wallet2 = new WalletAppKit(params, new File("."), "wallet2");
		WalletAppKit wallet3 = new WalletAppKit(params, new File("."), "wallet3");
		wallet2.connectToLocalHost();
		wallet2.setAutoSave(false);
		wallet2.startAsync();
		wallet2.awaitRunning();
		wallet3.connectToLocalHost();
		wallet3.setAutoSave(false);
		wallet3.startAsync();
		wallet3.awaitRunning();
		Address recWalletAddress = wallet3.wallet().currentReceiveAddress();    
		Address sendWalletAddress = wallet2.wallet().currentReceiveAddress();    

		Coin amount =Coin.COIN.multiply(10);
		Transaction tx = new Transaction(params); 
		
		
		
		long timestamp = new Timestamp(System.currentTimeMillis()).getTime();
		BigInteger currentTime = new BigInteger(Long.toString(timestamp).substring(0, 10));
		BigInteger time = BigInteger.valueOf(200).add(currentTime);//roughly 3 minutes = 200
		System.out.println(time);
		
		byte[] timeBytes = Utils.reverseBytes(Utils.encodeMPI(time, false));


		// script responsible for checksum
		// Sender: sig pubk [dup() hash() pubk equver() chksig() OP_VERIFY() file opsha() equchksm()]
		// Receiver: chksm* sig pubk [dup() hash() pubk equ() chksig()]
		// note chksm: sha hash of the file.

		// script responsible for timelock
		// Sender: sig pubk [opif() 
						// dup() hash() pubk equver() chksig() OP_VERIFY() checksum equchksm()
				// opelse() 
						// time opcltv() opdrop() opdup() hash() pubk eqver() chsig()]
		
		// Receiver1: file sha256() sig pubk ff [dup() hash() pubk equ() chksig()]
		// Receiver2: nop* nop* sig pubk 00 [dup() hash() pubk equ() chksig()]
		// *to assure the functionality of Script.getScriptSigWithSignature


		Script locking = new ScriptBuilder()
				/*1 */.op(OP_IF)
					/*2 */.op(OP_DUP)
					/*3 */.op(OP_HASH160)
					/*4 */.data(recWalletAddress.getHash160())
					/*5 */.op(OP_EQUALVERIFY)
					/*6 */.op(OP_CHECKSIG)
					/*7 */.op(OP_VERIFY)
					/*8 */.data(checksum1)
					/*9 */.op(OP_EQUAL)
				/*10*/.op(OP_ELSE)
					/*11*/.data(timeBytes)
					/*12*/.op(OP_CHECKLOCKTIMEVERIFY)
					/*13*/.op(OP_DROP)
					/*14*/.op(OP_DUP)
					/*15*/.op(OP_HASH160)
					/*16*/.data(sendWalletAddress.getHash160())
					/*17*/.op(OP_EQUALVERIFY)
					/*18*/.op(OP_CHECKSIG)
				/*19*/.op(OP_ENDIF) 
				.build(); 


		tx.addOutput(amount, locking);


		SendRequest req = SendRequest.forTx(tx);
		wallet2.wallet().completeTx(req);
		wallet2.peerGroup().broadcastTransaction(req.tx);
		final Peer peer = wallet2.peerGroup().getConnectedPeers().get(0);
		peer.sendMessage(tx);
		System.out.println("Transaction:\n"+req.tx);

		Thread.sleep(5000);
		wallet3.stopAsync();
		wallet3.awaitTerminated();
		wallet2.stopAsync();
		wallet2.awaitTerminated();
	}
}
