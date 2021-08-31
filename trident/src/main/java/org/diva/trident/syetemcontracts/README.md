# System Contract

Each transaction on the TRON network has a system contract.

System contracts can be seen as "types of the transaction", which include `TransferContract` for TRX transfers, `TriggerSmartContract` for smart contract calls, and many other types of transactions.

## Sending Transactions

Before going on the TRON network, All non-query transactions have three steps: construction, signature and broadcast.

Construction and signature can be done offline, however, to save works(lazy, examples in this package use RPC calls to construct transactions.
