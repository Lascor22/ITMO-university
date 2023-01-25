package ru.itmo.sd.vk;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TxHandler {

    public UTXOPool ledger;

    public TxHandler(UTXOPool utxoPool) {
        this.ledger = new UTXOPool(utxoPool);
    }

    public boolean isValidTx(Transaction tx) {
        // check all tests
        return (allInputsInPool(tx) &&
                allInputSignaturesValid(tx) &&
                noInputsClaimedMultiple(tx) &&
                allOutputsNonNegative(tx) &&
                (transactionFee(tx) >= 0.0));
    }

    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        return Arrays.stream(possibleTxs)
                .filter(tx -> isValidTx(tx))
                .peek(this::updatePoolLedger)
                .toArray(Transaction[]::new);
    }

    private void updatePoolLedger(Transaction tx) {
        tx.getInputs().forEach(inp -> ledger.removeUTXO(new UTXO(inp.prevTxHash, inp.outputIndex)));

        IntStream.range(0, tx.numOutputs())
                .forEach(idx -> ledger.addUTXO(new UTXO(tx.getHash(), idx), tx.getOutput(idx)));

    }

    private boolean allInputsInPool(Transaction tx) {
        return tx.getInputs().stream()
                .map(inp -> new UTXO(inp.prevTxHash, inp.outputIndex))
                .allMatch(utxo -> ledger.contains(utxo));

    }

    private boolean allInputSignaturesValid(Transaction tx) {
        return IntStream.range(0, tx.numInputs())
                .allMatch(idx -> {
                    Transaction.Input inp = tx.getInput(idx);
                    byte[] msg = tx.getRawDataToSign(idx);
                    byte[] sig = inp.signature;
                    PublicKey pk = ledger.getTxOutput(new UTXO(inp.prevTxHash, inp.outputIndex)).address;
                    return Crypto.verifySignature(pk, msg, sig);
                });
    }

    private boolean noInputsClaimedMultiple(Transaction tx) {
        return tx.getInputs().stream()
                .map(inp -> new UTXO(inp.prevTxHash, inp.outputIndex))
                .collect(Collectors.toSet())
                .size() == tx.numInputs();
    }

    private boolean allOutputsNonNegative(Transaction tx) {
        return tx.getOutputs().stream()
                .allMatch(o -> o.value >= 0);
    }

    private double transactionFee(Transaction tx) {
        double inputSum = tx.getInputs().stream()
                .mapToDouble(inp -> ledger.getTxOutput(new UTXO(inp.prevTxHash, inp.outputIndex)).value)
                .sum();
        double outputSum = tx.getOutputs().stream()
                .mapToDouble(o -> o.value)
                .sum();
        return inputSum - outputSum;
    }


}