# JMH Benchmark for custom JsonProvider for Logback

Custom Logback JsonProvider truncates etremely long log lines ('message' field)

## How to run benchmark

      cd ./test
      mvn clean verify
      java -jar target/benchmarks.jar

## Benchmark results

Benchmark is performed with max message length triggering truncation - 2097152 (2 * 1024 * 1024)

- In sec/op

      Benchmark                                                   (messageLen)  Mode  Cnt   Score    Error  Units
      TruncMessageJsonProviderBenchmark.writeWithDefaultProvider           500  avgt    3  ≈ 10⁻⁹            s/op
      TruncMessageJsonProviderBenchmark.writeWithDefaultProvider       2097151  avgt    3  ≈ 10⁻⁹            s/op
      TruncMessageJsonProviderBenchmark.writeWithDefaultProvider       2097153  avgt    3  ≈ 10⁻⁹            s/op
      TruncMessageJsonProviderBenchmark.writeWithDefaultProvider       4194304  avgt    3  ≈ 10⁻⁹            s/op
      TruncMessageJsonProviderBenchmark.writeWithTruncProvider             500  avgt    3  ≈ 10⁻⁹            s/op
      TruncMessageJsonProviderBenchmark.writeWithTruncProvider         2097151  avgt    3  ≈ 10⁻⁹            s/op
      TruncMessageJsonProviderBenchmark.writeWithTruncProvider         2097153  avgt    3   0.001 ±  0.001   s/op
      TruncMessageJsonProviderBenchmark.writeWithTruncProvider         4194304  avgt    3   0.001 ±  0.001   s/op

- In ops/sec

      Benchmark                                                   (messageLen)   Mode  Cnt          Score           Error  Units
      TruncMessageJsonProviderBenchmark.writeWithDefaultProvider           500  thrpt    3  326912343.864 ± 219833672.249  ops/s
      TruncMessageJsonProviderBenchmark.writeWithDefaultProvider       2097151  thrpt    3  325389640.278 ± 573380870.474  ops/s
      TruncMessageJsonProviderBenchmark.writeWithDefaultProvider       2097153  thrpt    3  364775481.547 ± 272053219.236  ops/s
      TruncMessageJsonProviderBenchmark.writeWithDefaultProvider       4194304  thrpt    3  366586592.880 ± 361282109.407  ops/s
      TruncMessageJsonProviderBenchmark.writeWithTruncProvider             500  thrpt    3  296779007.755 ± 311668429.904  ops/s
      TruncMessageJsonProviderBenchmark.writeWithTruncProvider         2097151  thrpt    3  271497748.882 ±  89627392.359  ops/s
      TruncMessageJsonProviderBenchmark.writeWithTruncProvider         2097153  thrpt    3        282.481 ±       218.805  ops/s
      TruncMessageJsonProviderBenchmark.writeWithTruncProvider         4194304  thrpt    3        282.822 ±       171.973  ops/s

## Conclusion

Performance is affected only when writing extremely long messages (of length > max length specified for truncating provider).
It's acceptable in order to avoid log shipping service failures.
