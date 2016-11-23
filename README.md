# kafka-throughput-test

These are my results, run with: java -jar benchmarks.jar -wi 500 -i 1000 -f 1
```
# Run complete. Total time: 00:50:15

Benchmark                            Mode   Cnt    Score    Error  Units
KafkaBenchmark.testFinalSerializer  thrpt  1000  324,180 ± 30,099  ops/s
KafkaBenchmark.testSerializer       thrpt  1000  323,327 ± 30,110  ops/s
```
That makes a minor difference of about 0.26% increase in throughput above non final classes.

Results of the second producer test with & without flush() calls:

```
# Run complete. Total time: 01:42:15

Benchmark                                   Mode   Cnt       Score       Error  Units
KafkaBenchmark.testFinalSerializer         thrpt  1000     257,029 ±    21,388  ops/s
KafkaBenchmark.testFinalSerializerNoFlush  thrpt  1000  542017,088 ± 31279,553  ops/s
KafkaBenchmark.testSerializer              thrpt  1000     240,659 ±    19,212  ops/s
KafkaBenchmark.testSerializerNoFlush       thrpt  1000  524422,547 ± 33349,532  ops/s
```

Without flush(), final classes over non-final: +5.55% throughput<br>
With flush(), final classes over non-final: +3.35% throughput<br>
Note: in this test I changed the Kafka broker log directory from tmpfs to disk, hence less overall throughput.

My system: Windows 8.1 Pro, i7 6800K @ 3,4GHz, 32GB RAM Quad Channel - JDK: x64 Oracle 1.8u102<br>
Kafka Broker 2.11-0.10.1.0 running on the same machine on Arch Linux 4.8.10 in VirtualBox 5.10 JDK: OpenJDK 1.8u112<br>
