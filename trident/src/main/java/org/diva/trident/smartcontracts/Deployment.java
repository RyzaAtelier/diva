package org.diva.trident.smartcontracts;

import com.google.protobuf.ByteString;

import org.tron.trident.abi.datatypes.generated.Uint256;
import org.tron.trident.abi.datatypes.Type;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.contract.Contract;
import org.tron.trident.core.transaction.TransactionBuilder;
import org.tron.trident.proto.Chain.Transaction;
import org.tron.trident.utils.Numeric;

import java.util.List;
import java.util.ArrayList;

/**
 * You can use the bytecode and ABI to deploy a 
 * smart contract with trident-java.
 * 
 * To get your bytecode and ABI, use Remix, Tronbox or 
 * simply run "solc --bin --abi contract.sol > result".
 */

public class Deployment {

    static final String PRIV_KEY = "private key";
    static final String OWNER_ADDR = new KeyPair(PRIV_KEY).toBase58CheckAddress();
    static final ApiWrapper aw = ApiWrapper.ofMainnet(PRIV_KEY);
    //ofShasta(PRIV_KEY)
    //ofNile(PRIV_KEY)
    //new ApiWrapper("fullnode", "solidityNode", PRIV_KEY)

    static final String BYTECODE = "";
    static final String ABI = "";

    /**
     * This is an example of deploying a smart contract
     * with its constructor.
     * 
     * Take constructor(uint256) as an example. Note that
     * this constructor does not corrspond to any smart contract,
     * you need to generate your own bytecode and ABI.
     * 
     * @return The transaction hash.
     */
    public String deployContract() {
        try {
            //set constructor params
            List<Type> params = new ArrayList<>();
            params.add(new Uint256(1));
            //build the contract
            Contract cntr = new Contract.Builder()
                                .setOwnerAddr(aw.parseAddress(OWNER_ADDR))
                                .setOriginAddr(aw.parseAddress(OWNER_ADDR))
                                .setBytecode(ByteString.copyFrom(Numeric.hexStringToByteArray(bytecode)))
                                .setAbi(ABI)
                                .setWrapper(aw)
                                .build();

            //if there is not a constructor, use cntr.deploy()
            TransactionBuilder builder = cntr.deploy(params);
            //similar to trigger calls, set fee limit
            builder.setFeeLimit(1_000_000_000L);
            //sign and broadcast
            Transaction signedTxn = aw.signTransaction(builder.build());
            return aw.broadcastTransaction(signedTxn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}