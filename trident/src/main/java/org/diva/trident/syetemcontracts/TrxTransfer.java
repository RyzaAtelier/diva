package org.diva.trident.systemcontracts;

import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.key.KeyPair;
import org.tron.trident.proto.Chain.Transaction;
import org.tron.trident.proto.Response.TransactionExtention;
import org.tron.trident.proto.TransferContract;

/**
 * This is an example of TRX transfer.
 * 
 * This is the simplest and mostly used non-smart contract
 * transaction. The construction steps of other types of transactions
 * are similar to this.
 * 
 * For non-smart contract transactions, going on chain = success.
 * Failures will be thrown during the step of broadcast.
 */

public class TrxTransfer {

    static final String PRIV_KEY = "private key";
    static final String OWNER_ADDR = new KeyPair(PRIV_KEY).toBase58CheckAddress();
    static final ApiWrapper aw = ApiWrapper.ofMainnet(PRIV_KEY);
    //ofShasta(PRIV_KEY)
    //ofNile(PRIV_KEY)
    //new ApiWrapper("fullnode", "solidityNode", PRIV_KEY)
    
    /**
     * Trx transfer.
     * @param recipient The address to receive TRX.
     * @param amount Amount, in sun(1 sun = 10^-6 TRX).
     * @return The transaction hash.
     */
    public String transferTrx(String recipient, long amount) {
        try {
            //transaction construction
            TransactionExtention txnExt = aw.transfer(OWNER_ADDR, recipient, amount);
            //signature
            Transaction signedTxn = aw.signTransaction(txnExt);
            //and finally, broadcast, and return the transaction hash
            return aw.broadcastTransaction(signedTxn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}