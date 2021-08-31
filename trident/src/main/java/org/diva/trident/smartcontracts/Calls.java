package org.diva.trident.smartcontracts;

import org.tron.trident.abi.datatypes.Function;
import org.tron.trident.abi.datatypes.generated.Uint256;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.contract.Contract;
import org.tron.trident.core.contract.Trc20Contract;
import org.tron.trident.core.key.KeyPair;
import org.tron.trident.core.transaction.TransactionBuilder;
import org.tron.trident.proto.Chain.Transaction;
import org.tron.trident.proto.Response.TransactionExtention;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

/**
 * This file contains demos for trigger and constant calls.
 * 
 * java-tron has two interfaces to call a smart contract:
 * TriggerConstantContract and TriggerSmartContract. trident-java
 * uses TriggerConstantContract for all calls, as this function 
 * returns constant results as well as an estimated energy cost.
 * 
 * The TRC-20 token in this file is: TE6jJsb1PwvwvKfWK1RUXTXYETo3nj7tGq
 * on Nile test net.
 */

public class Calls {

    static final String PRIV_KEY = "private key";
    static final String OWNER_ADDR = new KeyPair(PRIV_KEY).toBase58CheckAddress();
    static final ApiWrapper aw = ApiWrapper.ofMainnet(PRIV_KEY);
    //ofShasta(PRIV_KEY)
    //ofNile(PRIV_KEY)
    //new ApiWrapper("fullnode", "solidityNode", PRIV_KEY)

    /**
     * Build the contract function you want to call in java.
     * 
     * Take function burn(uint256 amount) as an example.
     * 
     * @return The function with parameters set.
     */
    public Function buildFunction() {
        long amount = 666666;

        return new Function(
            "burn", //function name
            Arrays.asList(new Uint256(amount)), // input params
            Collections.emptyList() // returns
        );
    }

    /**
     * Call function function burn(uint256 amount).
     * 
     * This will not be successful, as your account
     * does not have any token.
     * 
     * @param function @see buildFunction.
     * @return The transaction hash.
     */
    public String triggerCall(Function function) {
        try {
            TransactionBuilder builder = aw.triggerCall(
                OWNER_ADDR, //owner address
                "TE6jJsb1PwvwvKfWK1RUXTXYETo3nj7tGq", //contract address
                function
            );
            //set a appropriate fee limit
            builder.setFeeLimit(100_000_000L);
            //then, sign and broadcast like other transactions
            Transaction signedTxn = aw.signTransaction(builder.build());
            return aw.broadcastTransaction(signedTxn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Trident-java has encapsulate TRC-20 related functions.
     * 
     * This is a demo for calling function totalSupply()
     */
    public BigInteger totalSupply() {
        try {
            Contract cntr = c.getContract("TE6jJsb1PwvwvKfWK1RUXTXYETo3nj7tGq");
            Trc20Contract token = new Trc20Contract(cntr, OWNER_ADDR, aw);
            return token.totalSupply();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}