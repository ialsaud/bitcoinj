package org.bitcoinj.examples;
import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.utils.BriefLogFormatter;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.wallet.SendRequest;
import static org.bitcoinj.script.ScriptOpCodes.*;
import java.io.File;
import java.util.*;
import static org.bitcoinj.core.Coin.*;

public class customTx {
	public customTx(){
		
	}
	  public static byte[] hexStringToByteArray(String s) {
		  int len = s.length();
		  byte[] data = new byte[len / 2];
		  for (int i = 0; i < len; i += 2) {
			  data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					  				+ Character.digit(s.charAt(i+1), 16));
		  }
		  return data;
	  } 
	public void createSendCustom(Coin amountPerTx, String checkSum,Address recWalletAddress, WalletAppKit sendKit) throws InsufficientMoneyException {
        final RegTestParams params = RegTestParams.get();
		Transaction tx = new Transaction(params);    
		byte[] temp=hexStringToByteArray(checkSum);
		Script locking = new ScriptBuilder()
					.op(OP_DUP)
					.op(OP_HASH160)
					.data(recWalletAddress.getHash160())
					.op(OP_EQUALVERIFY)
					.op(OP_CHECKSIG)
					.op(OP_VERIFY)
					.data(temp)
					.op(OP_EQUAL)
					.build();
		tx.addOutput(amountPerTx, locking);
		SendRequest req = SendRequest.forTx(tx);
		sendKit.wallet().completeTx(req);
		sendKit.peerGroup().broadcastTransaction(req.tx);
	}
}