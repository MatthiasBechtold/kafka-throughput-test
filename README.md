# kafka-throughput-test

These are my results, run with: java -jar benchmarks.jar -wi 500 -i 1000 -f 1
```
# Run complete. Total time: 00:50:15

Benchmark                            Mode   Cnt    Score    Error  Units
KafkaBenchmark.testFinalSerializer  thrpt  1000  324,180 ± 30,099  ops/s
KafkaBenchmark.testSerializer       thrpt  1000  323,327 ± 30,110  ops/s
```
That makes a minor difference of about 0,26 % increase above non final classes.
