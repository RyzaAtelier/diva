package org.diva.trident.systemcontracts;

import org.tron.trident.core.*;
import org.tron.trident.proto.*;

import com.google.protobuf.ByteString;

/**
 * This is an example for broadcasting a transaction with its hex string.
 * 
 */

public class BroadCastHex {

    static final String PRIV_KEY = "private key";
    static final String OWNER_ADDR = new KeyPair(PRIV_KEY).toBase58CheckAddress();
    static final ApiWrapper aw = ApiWrapper.ofMainnet(PRIV_KEY);
    //ofShasta(PRIV_KEY)
    //ofNile(PRIV_KEY)
    //new ApiWrapper("fullnode", "solidityNode", PRIV_KEY)

    /**
     * This tutorial shows the steps of convertions between transactions and hexadecimal string.
     *
     * You may using "wrapper.broadcastTransaction(Transaction.parseFrom(wrapper.parseHex(yourHexString)))"
     * to broadcast your transaction with a hex String
     */
    public static void broadcastHexTutorial() {
        try {
            // generate a signed transaction
            TransactionExtention txnExt = client.transfer("from", "to", 1_000_000);
            Transaction txn = txnExt.getTransaction();
            Transaction signedTxn = client.signTransaction(txn); 
            // parse the transaction to a hex string
            ByteString data = signedTxn.toByteString(); 
            String hex = client.toHex(data);
            // parse the hex string to a transaction object
            Transaction fromHex = Transaction.parseFrom(client.parseHex(hex));
            //broadcast the transaction
            String txnHash = client.broadcastTransaction(fromHex);

            System.out.println("Estimated bandwidth consumption: " + client.estimateBandwidth(signedTxn));

            System.out.println("Transaction hash: " + txnHash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}