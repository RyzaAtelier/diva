package org.diva.trident.smartcontracts;

import org.tron.trident.abi.datatypes.Address;
import org.tron.trident.abi.datatypes.generated.Uint256;
import org.tron.trident.abi.TypeDecoder;

import java.math.BigInteger;

/**
 * This class contains ABI decoding examples.
 * 
 * Refer to https://docs.soliditylang.org/en/v0.8.7/abi-spec.html
 * for ABI encoding rules.
 * 
 * Note that there are differences between the encoding rules 
 * of fix-sized and dynamic data types.
 */

public class Decoder {

    static final String DATA = "a9059cbb00000000000000000000" + 
                               "00007fdf5157514bf89ffcb7ff36" +
                               "f34772afd4cdc744000000000000" +
                               "0000000000000000000000000000" +
                               "0000000000000000000186a0";

    /**
     * This is the simplest example for ABI data decoding,
     * also, it is a universal solution for any TRC-20 tokens.
     * 
     * The example transaction in this method is:
     * 8fe65f66cb5066c5854e2ce05ba849412a232579c31f81a7f40bd9f7a1425dea
     * 
     * on Nile test net, with the data: 
     * a9059cbb0000000000000000000000007fdf5157514bf89ffcb7ff36f34
     * 772afd4cdc74400000000000000000000000000000000000000000
     * 000000000000000000186a0
     * 
     * The first 4 bytes is the function signature, which is generated
     * by a hash function and the exact function cannot be deduced from
     * this, but it is possible to find it on https://www.4byte.directory/
     * 
     */
    public void trc20TransferDataDecoder() {
        String rawSignature = DATA.substring(0,8);
        String signature = "transfer(address,uint256)";
        //fix-size param takes up 32 bytes each
        Address rawRecipient = TypeDecoder.decodeAddress(DATA.substring(8,72));
        //Address.toString() has been rewritten to be compatible with TRON
        String recipient = rawRecipient.toString();
        //the amount is in hexadecimal characters
        Uint256 rawAmount = TypeDecoder.decodeNumeric(DATA.substring(72,136), Uint256.class);
        BigInteger amount = rawAmount.getValue();

        System.out.println(signature);
        System.out.println("Transfer " + amount + " to " + recipient);
    }
}